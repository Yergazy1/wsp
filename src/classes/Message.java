package classes;

public class Message {
    private String fromWhom;
    private String content;

    public Message(String fromWhom, String content) {
        this.fromWhom = fromWhom;
        this.content = content;
    }

    public String getFromWhom() {
        return fromWhom;
    }

    public String getContent() {
        return content;
    }
}
