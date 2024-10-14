import java.sql.Timestamp;

public class Email {
    private int id;
    private String sender_username;
    private String receive_username;
    private String subject;
    private String content;
    private Timestamp sentTime;

    public Email() {
    }

    public Email(int id, String sender_username, String receive_username, String subject, String content, Timestamp sentTime) {
        this.id = id;
        this.sender_username = sender_username;
        this.receive_username = receive_username;
        this.subject = subject;
        this.content = content;
        this.sentTime = sentTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender_username() {
        return sender_username;
    }

    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }

    public String getReceive_username() {
        return receive_username;
    }

    public void setReceive_username(String receive_username) {
        this.receive_username = receive_username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }
    @Override
    public String toString() {
        return subject; // Hiển thị subject của email trong JList
    }

}