package classes;

import java.util.HashMap;
import java.util.Map;

public class Localization {
    private static String currentLanguage = "en";
    private static final Map<String, Map<String, String>> messages = new HashMap<>();

    static {
        // Сообщения на английском
        Map<String, String> enMessages = new HashMap<>();
        enMessages.put("welcome", "Welcome to the system!");
        enMessages.put("registration_prompt", "Please enter your registration details:");
        enMessages.put("login_prompt", "Please enter your login details:");
        enMessages.put("invalid_choice", "Invalid choice.");
        enMessages.put("exit", "Exiting...");

        // Сообщения на русском
        Map<String, String> ruMessages = new HashMap<>();
        ruMessages.put("welcome", "Добро пожаловать в систему!");
        ruMessages.put("registration_prompt", "Пожалуйста, введите данные для регистрации:");
        ruMessages.put("login_prompt", "Пожалуйста, введите данные для входа:");
        ruMessages.put("invalid_choice", "Неверный выбор.");
        ruMessages.put("exit", "Выход...");

        // Сообщения на казахском
        Map<String, String> kkMessages = new HashMap<>();
        kkMessages.put("welcome", "Жүйеге қош келдіңіз!");
        kkMessages.put("registration_prompt", "Тіркеу мәліметтерін енгізіңіз:");
        kkMessages.put("login_prompt", "Кіру мәліметтерін енгізіңіз:");
        kkMessages.put("invalid_choice", "Қате таңдау.");
        kkMessages.put("exit", "Шығу...");

        messages.put("en", enMessages);
        messages.put("ru", ruMessages);
        messages.put("kz", kkMessages);
    }

    public static void setLanguage(String language) {
        if (messages.containsKey(language)) {
            currentLanguage = language;
        } else {
            System.out.println("Language not supported, defaulting to English.");
        }
    }

    public static String getMessage(String key) {
        return messages.getOrDefault(currentLanguage, messages.get("en")).getOrDefault(key, "Message not found.");
    }
}