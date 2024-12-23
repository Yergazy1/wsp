package main;

import classes.Localization;
import classes.Database;
import classes.Teacher;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Выбор языка
        System.out.println("Choose your language (en/ru/kk): ");
        String lang = scanner.nextLine();
        Localization.setLanguage(lang.equals("ru") ? "ru" : lang.equals("kk") ? "kk" : "en");

        System.out.println(Localization.getMessage("welcome"));

        while (true) {
            System.out.println("1. " + (lang.equals("ru") ? "Регистрация" : lang.equals("kk") ? "Тіркеу" : "Register"));
            System.out.println("2. " + (lang.equals("ru") ? "Войти" : lang.equals("kk") ? "Кіру" : "Login"));
            System.out.println("0. " + Localization.getMessage("exit"));
            System.out.print(Localization.getMessage("invalid_choice"));

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Очистить буфер
            } catch (InputMismatchException e) {
                System.out.println(Localization.getMessage("invalid_input"));
                scanner.nextLine(); // Очистить буфер
                continue;
            }

            if (choice == 1) {
                // Регистрация
                System.out.println(Localization.getMessage("registration_prompt"));
                System.out.print("Fullname: ");
                String fullname = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Кто вы? ");
                String userType = scanner.nextLine();

                if (Database.isEmailTaken(email)) {
                    System.out.println(lang.equals("ru") ? "Email уже занят." : lang.equals("kk") ? "Email бұрыннан бар." : "Email is already taken.");
                } else if (Database.registerUser(fullname, email, password, userType)) {
                    System.out.println(lang.equals("ru") ? "Регистрация успешна!" : lang.equals("kk") ? "Тіркеу сәтті!" : "Registration successful!");
                } else {
                    System.out.println(lang.equals("ru") ? "Ошибка при регистрации." : lang.equals("kk") ? "Тіркеу қатесі." : "Error during registration.");
                }

            } else if (choice == 2) {
                // Логин
                System.out.println(Localization.getMessage("login_prompt"));
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                if (Database.loginUser(email, password)) {
                    System.out.println((lang.equals("ru") ? "Добро пожаловать, " : lang.equals("kk") ? "Қош келдіңіз, " : "Welcome, ") + email);

                    // Получение роли пользователя из базы данных
                    String role = Database.getUserRole(email);
                    if (role == null) {
                        System.out.println(lang.equals("ru") ? "Ошибка при получении роли пользователя." : lang.equals("kk") ? "Қолданушының рөлін алу қатесі." : "Error retrieving user role.");
                    } else {
                        switch (role) {
                            case "student":
                                System.out.println(lang.equals("ru") ? "Вы вошли как студент." : lang.equals("kk") ? "Сіз пайдаланушы ретінде кірдіңіз." : "You logged in as a user.");
                                // Логика для студента (если нужно добавить)
                                break;case "teacher":
                                System.out.println(lang.equals("ru") ? "Вы вошли как учитель." : lang.equals("kk") ? "Сіз мұғалім ретінде кірдіңіз." : "You logged in as a teacher.");
                                Teacher teacher = new Teacher();
                                while (true) {
                                    System.out.println(lang.equals("ru") ? "Выберите действие:\n1. Отправить сообщение\n2. Посмотреть входящие сообщения\n3. Выйти" :
                                            lang.equals("kk") ? "Әрекетті таңдаңыз:\n1. Хабарлама жіберу\n2. Келген хабарламаларды көру\n3. Шығу" :
                                                    "Choose an action:\n1. Send a message\n2. View incoming messages\n3. Exit");
                                    System.out.print("Ваш выбор: ");
                                    int teacherChoice = Integer.parseInt(scanner.nextLine());

                                    if (teacherChoice == 1) {
                                        // Логика отправки сообщения
                                        System.out.print(lang.equals("ru") ? "Кому вы хотите отправить сообщение? (Email): " :
                                                lang.equals("kk") ? "Кімге хабарлама жібергіңіз келеді? (Email): " :
                                                        "Who do you want to send a message to? (Email): ");
                                        String toWhom = scanner.nextLine();

                                        System.out.print(lang.equals("ru") ? "Введите содержимое сообщения: " :
                                                lang.equals("kk") ? "Хабарламаның мазмұнын енгізіңіз: " :
                                                        "Enter the message content: ");
                                        String content = scanner.nextLine();

                                        // Сохранение сообщения в таблицу messages
                                        if (Database.sendMessage(email, toWhom, content)) {
                                            System.out.println(lang.equals("ru") ? "Сообщение успешно отправлено!" :
                                                    lang.equals("kk") ? "Хабарлама сәтті жіберілді!" :
                                                            "Message sent successfully!");
                                        } else {
                                            System.out.println(lang.equals("ru") ? "Ошибка при отправке сообщения." :
                                                    lang.equals("kk") ? "Хабарлама жіберу кезінде қате." :
                                                            "Error sending the message.");
                                        }
                                    } else if (teacherChoice == 2) {
                                        // Логика просмотра сообщений
                                        System.out.println(lang.equals("ru") ? "Ваши входящие сообщения:" :
                                                lang.equals("kk") ? "Сіздің келіп түскен хабарламаларыңыз:" :
                                                        "Your incoming messages:");
                                        List<Message> messages = Database.getMessages(email);
                                        if (messages.isEmpty()) {
                                            System.out.println(lang.equals("ru") ? "Нет входящих сообщений." :
                                                    lang.equals("kk") ? "Келген хабарламалар жоқ." :
                                                            "No incoming messages.");
                                        } else {
                                            for (Message message : messages) {
                                                System.out.println("From: " + message.getFromWhom());
                                                System.out.println("Content: " + message.getContent());
                                                System.out.println("----------");}
                                        }
                                    } else if (teacherChoice == 3) {
                                        teacher.viewCourses(null);

                                        break;
                                    } else if (teacherChoice == 4) {

                                    break;
                                    }
                                    else {
                                        System.out.println(lang.equals("ru") ? "Неверный выбор. Попробуйте снова." :
                                                lang.equals("kk") ? "Қате таңдау. Қайтадан көріңіз." :
                                                        "Invalid choice. Please try again.");
                                    }
                                }
                                break;

                            case "manager":
                                System.out.println(lang.equals("ru") ? "Вы вошли как менеджер." : lang.equals("kk") ? "Сіз менеджер ретінде кірдіңіз." : "You logged in as a manager.");
                                // Логика для менеджера
                                break;

                            default:
                                System.out.println(lang.equals("ru") ? "Неизвестная роль." : lang.equals("kk") ? "Белгісіз рөл." : "Unknown role.");
                                break;
                        }
                    }
                    break; // Завершить цикл после успешного логина
                } else {
                    System.out.println(lang.equals("ru") ? "Неверный email или пароль." : lang.equals("kk") ? "Қате email немесе құпиясөз." : "Invalid email or password.");
                }

            } else if (choice == 0) {
                System.out.println(Localization.getMessage("exit"));
                break;
            } else {
                System.out.println(Localization.getMessage("invalid_choice"));
            }
        }
    }
}