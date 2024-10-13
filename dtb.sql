-- Tạo cơ sở dữ liệu
CREATE DATABASE mailserver;

-- Sử dụng cơ sở dữ liệu
USE mailserver;

-- Tạo bảng 'users' để lưu thông tin người dùng
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL
);

-- Tạo bảng 'emails' để lưu thông tin email
CREATE TABLE emails (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        sender_username VARCHAR(50) NOT NULL,
                        receiver_username VARCHAR(50) NOT NULL,
                        subject VARCHAR(255),
                        content TEXT,
                        sent_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (sender_username) REFERENCES users(username),
                        FOREIGN KEY (receiver_username) REFERENCES users(username)
);
