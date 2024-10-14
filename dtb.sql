-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS mailserver;

-- Sử dụng cơ sở dữ liệu
USE mailserver;

-- Tạo bảng 'users' để lưu thông tin người dùng
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
    );

-- Xóa dữ liệu cũ trong bảng 'users'
DELETE FROM `users`;

-- Chèn dữ liệu mẫu vào bảng 'users'
INSERT INTO `users` (`id`, `username`, `password`) VALUES
                                                       (1, 'cham@gmail.com', '123'),
                                                       (2, 'ngoc@gmail.com', '000'),
                                                       (3, 'dieu@gmail.com', '000');

-- Tạo bảng 'emails' để lưu thông tin email
CREATE TABLE IF NOT EXISTS `emails` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
    `sender_username` varchar(50) NOT NULL,
    `receiver_username` varchar(50) NOT NULL,
    `subject` varchar(100) DEFAULT NULL,
    `content` text DEFAULT NULL,
    `sent_time` timestamp NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `sender_username` (`sender_username`),
    KEY `receiver_username` (`receiver_username`),
    CONSTRAINT `emails_ibfk_1` FOREIGN KEY (`sender_username`) REFERENCES `users` (`username`),
    CONSTRAINT `emails_ibfk_2` FOREIGN KEY (`receiver_username`) REFERENCES `users` (`username`)
    );

-- Xóa dữ liệu cũ trong bảng 'emails'
DELETE FROM `emails`;

-- Chèn dữ liệu mẫu vào bảng 'emails'
INSERT INTO `emails` (`id`, `sender_username`, `receiver_username`, `subject`, `content`, `sent_time`) VALUES
                                                                                                           (1, 'dieu@gmail.com', 'cham@gmail.com', 'helo', 'hello', '2024-10-14 03:44:32'),
                                                                                                           (2, 'ngoc@gmail.com', 'cham@gmail.com', '123', '321', '2024-10-14 05:58:58'),
                                                                                                           (3, 'cham@gmail.com', 'ngoc@gmail.com', 'mimi', 'meomeo', '2024-10-14 06:11:19'),
                                                                                                           (4, 'cham@gmail.com', 'dieu@gmail.com', 'kk', 'kkkk', '2024-10-14 06:12:50'),
                                                                                                           (5, 'ngoc@gmail.com', 'dieu@gmail.com', 'ololo', 'olololloooo', '2024-10-14 06:13:13'),
                                                                                                           (6, 'dieu@gmail.com', 'ngoc@gmail.com', 'meme', 'văn học', '2024-10-14 06:13:35');
