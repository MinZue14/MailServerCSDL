import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MailClient {
    private static final int SERVER_PORT = 9876;
    private MailServer mailServer;
    private String currentUsername = null;
    private DefaultListModel<Email> inboxListModel;
    private JTextArea emailContentTextArea;

    public MailClient() {
        mailServer = new MailServer(); // Khởi tạo server
        initLoginUI();
    }

    // Giao diện đăng nhập
    private void initLoginUI() {
        JFrame frame = new JFrame("Mail Client - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 100, 25);
        frame.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(150, 50, 200, 25);
        frame.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        frame.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 100, 200, 25);
        frame.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 25);
        frame.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(250, 150, 100, 25);
        frame.add(registerButton);

        // Xử lý đăng nhập
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                currentUsername = username;
                if (mailServer.login(username, password)) {
                    frame.dispose();
                    initMailUI();
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed, please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
            }
        });

        // Xử lý đăng ký
        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                if (mailServer.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Account created successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Registration failed.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
            }
        });

        frame.setVisible(true);
    }

    // Giao diện chính sau khi đăng nhập
    private void initMailUI() {
        JFrame frame = new JFrame("Mail Client - Inbox");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel userLabel = new JLabel("User: " + currentUsername);
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(userLabel, BorderLayout.NORTH); // Đặt JLabel ở trên cùng bên phải

        JPanel inboxPanel = new JPanel();
        inboxPanel.setLayout(new BorderLayout());

        JLabel inboxLabel = new JLabel("Inbox:");
        inboxPanel.add(inboxLabel, BorderLayout.NORTH);

        inboxListModel = new DefaultListModel<>();
        JList<Email> inboxList = new JList<>(inboxListModel);
        inboxPanel.add(new JScrollPane(inboxList), BorderLayout.CENTER);

        JButton reloadButton = new JButton("Reload Inbox");
        inboxPanel.add(reloadButton, BorderLayout.SOUTH);

        frame.add(inboxPanel, BorderLayout.WEST);

        emailContentTextArea = new JTextArea();
        emailContentTextArea.setEditable(false);
        frame.add(new JScrollPane(emailContentTextArea), BorderLayout.CENTER);

        // Tải lại inbox khi bấm Reload
        reloadButton.addActionListener(e -> {
            try {
                reloadInbox();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Xử lý khi nhấp vào email để hiển thị chi tiết
        inboxList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Email selectedEmail = inboxList.getSelectedValue();
                if (selectedEmail != null) {
                    displayEmailDetails(selectedEmail); // Hiển thị chi tiết email
                }
            }
        });

        // Nút để gửi email
        JButton sendEmailButton = new JButton("Send Email");
        inboxPanel.add(sendEmailButton, BorderLayout.NORTH);

        sendEmailButton.addActionListener(e -> {
            // Mở cửa sổ gửi email
            openSendEmailFrame();
        });

        frame.setVisible(true);
    }

    private void reloadInbox() {
        inboxListModel.clear();
        List<Email> emails = mailServer.getReceivedEmails(currentUsername);
        for (Email email : emails) {
            inboxListModel.addElement(email); // Hiển thị subject qua toString()
        }
        emailContentTextArea.setText(""); // Xóa nội dung email khi tải lại
    }

    // Hiển thị chi tiết email
    private void displayEmailDetails(Email email) {
        StringBuilder details = new StringBuilder();
        details.append("From: ").append(email.getSender_username()).append("\n");
        details.append("To: ").append(email.getReceive_username()).append("\n");
        details.append("Subject: ").append(email.getSubject()).append("\n");
        details.append("Date: ").append(email.getSentTime()).append("\n\n");
        details.append("Content:\n").append(email.getContent());

        emailContentTextArea.setText(details.toString()); // Hiển thị chi tiết email
    }

    // Gửi email
    private void sendEmail(String sender, String recipient, String subject, String content) {
        mailServer.sendEmail(sender, recipient, subject, content);
        System.out.println("Email đã được gửi!");
    }

    private void openSendEmailFrame() {
        JFrame sendEmailFrame = new JFrame("Send Email");
        sendEmailFrame.setSize(500, 400);
        sendEmailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sendEmailFrame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fromLabel = new JLabel("From: " + currentUsername);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(fromLabel, gbc);

        // Recipient Label and TextField
        JLabel recipientLabel = new JLabel("To:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(recipientLabel, gbc);

        JTextField recipientText = new JTextField(30);
        gbc.gridx = 1;
        contentPanel.add(recipientText, gbc);

        // Subject Label and TextField
        JLabel subjectLabel = new JLabel("Subject:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(subjectLabel, gbc);

        JTextField subjectText = new JTextField(30);
        gbc.gridx = 1;
        contentPanel.add(subjectText, gbc);

        // Content Label and TextArea
        JLabel contentLabel = new JLabel("Content:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(contentLabel, gbc);

        JTextArea contentText = new JTextArea(8, 30);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        contentPanel.add(new JScrollPane(contentText), gbc);

        sendEmailFrame.add(contentPanel, BorderLayout.CENTER);

        // Nút Gửi email
        JButton sendButton = new JButton("Send");
        sendEmailFrame.add(sendButton, BorderLayout.SOUTH);

        sendButton.addActionListener(ev -> {
            String recipient = recipientText.getText();
            String subject = subjectText.getText();
            String content = contentText.getText();
            if (!recipient.isEmpty() && !subject.isEmpty() && !content.isEmpty()) {
                sendEmail(currentUsername, recipient, subject, content);
                sendEmailFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(sendEmailFrame, "Please fill all fields.");
            }
        });

        sendEmailFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MailClient::new);
    }
}
