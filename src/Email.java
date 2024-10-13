import java.sql.Timestamp;

public class Email {
    private int id;
    private String sender;
    private String subject;
    private String content;
    private Timestamp sentTime;

    public Email(int id, String sender, String subject, String content, Timestamp sentTime) {
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
        this.sentTime = sentTime;
    }

    // Getter và Setter cho các thuộc tính

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
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

//    @Override
//    public String toString() {
//        return "Email{" +
//                "id=" + id +
//                ", sender='" + sender + '\'' +
//                ", subject='" + subject + '\'' +
//                ", content='" + content + '\'' +
//                ", sentTime=" + sentTime +
//                '}';
//    }
}
