package classes;
import enums.Language;
import java.util.*;
import java.util.Vector;

public abstract class User  {
    protected static final Set<User> users = new HashSet<>();
    protected static final Map<String, HashMap<Language, String>> phrases;
    private String id;

    private String fullName;
    private String email;
    private String password;
    protected Language language;

    public User() {
    }

    static {
        phrases = new HashMap<>();
    }

    public User(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.language = Language.EN;
    }

    public User(String id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public boolean authenticate(String email, String password){
        return this.email.equals(email) && this.password.equals(password);
    }

    public static User addUser(User u){
        users.add(u);
        return u;
    }

    public static void removeUser(User u){
        users.remove(u);
    }

    public static User findUser(String userId) {
        for (User u : users) {
            if (u.getId().equals(userId)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullname='" + fullName + '\'' +
                '}';
    }
    
    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.fullName = fullName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public static <T extends User> Vector<T> getAllUsers(Class<T> classType) {
        return new Vector<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected void setPhrase(String phrase, Language lang, String content) {
        phrases.putIfAbsent(phrase, new HashMap<>());
        phrases.get(phrase).put(lang, content);
    }

    protected String getPhrase(String phrase) {
        return phrases.get(phrase).get(language);
    }

    public String logMessage() {
        return this.toString();
    }

}