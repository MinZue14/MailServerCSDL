import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MailClient {

    private MailServer mailServer;
    private JFrame frame; // Khai báo biến frame là thuộc tính của lớp

    public MailClient() {
        mailServer = new MailServer();
        createLoginGUI();
    }

    // Giao diện đăng nhập
    private void createLoginGUI() {
        frame = new JFrame("Login"); // Khởi tạo frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeLoginComponents(panel);

        frame.setVisible(true);
    }

    private void placeLoginComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 100, 25);
        panel.add(registerButton);

        // Đăng nhập
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (mailServer.login(username, password)) {
                    frame.dispose(); // Đóng frame khi đăng nhập thành công
                    createMailGUI(username); // Hiển thị giao diện gửi thư
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid login information");
                }
            }
        });

        // Đăng ký
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (mailServer.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "User registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "User already exists or registration failed.");
                }
            }
        });
    }

    // Giao diện gửi thư
    private void createMailGUI(String username) {
        JFrame mailFrame = new JFrame("Mail Client - " + username);
        mailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mailFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        mailFrame.add(panel);
        placeMailComponents(panel, username);

        mailFrame.setVisible(true);
    }

    private void placeMailComponents(JPanel panel, String username) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setBounds(10, 20, 300, 25);
        panel.add(userLabel);

        JLabel receiverLabel = new JLabel("Receiver:");
        receiverLabel.setBounds(10, 50, 80, 25);
        panel.add(receiverLabel);

        JTextField receiverText = new JTextField(20);
        receiverText.setBounds(100, 50, 165, 25);
        panel.add(receiverText);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(10, 80, 80, 25);
        panel.add(subjectLabel);

        JTextField subjectText = new JTextField(20);
        subjectText.setBounds(100, 80, 165, 25);
        panel.add(subjectText);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setBounds(10, 110, 80, 25);
        panel.add(contentLabel);

        JTextArea contentText = new JTextArea();
        contentText.setBounds(100, 110, 165, 75);
        panel.add(contentText);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(10, 200, 80, 25);
        panel.add(sendButton);

        JButton viewMailButton = new JButton("View Mail");
        viewMailButton.setBounds(100, 200, 100, 25);
        panel.add(viewMailButton);

        // Gửi thư
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String receiver = receiverText.getText();
                String subject = subjectText.getText();
                String content = contentText.getText();
                mailServer.sendEmail(username, receiver, subject, content);
                JOptionPane.showMessageDialog(panel, "Email sent successfully!"); // Sử dụng panel thay vì frame
            }
        });

        // Xem thư
        viewMailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewReceivedEmails(username);
            }
        });
    }

    private void viewReceivedEmails(String username) {
        StringBuilder emails = new StringBuilder("Received Emails:\n");
        for (Email email : mailServer.getReceivedEmails(username)) {
            emails.append("From: ").append(email.getSender())
                    .append("\nSubject: ").append(email.getSubject())
                    .append("\nContent: ").append(email.getContent())
                    .append("\nTime: ").append(email.getSentTime())
                    .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, emails.toString());
    }

    public static void main(String[] args) {
        new MailClient();
    }
}
