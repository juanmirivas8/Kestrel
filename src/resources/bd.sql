CREATE DATABASE IF NOT EXISTS kestrel;
USE kestrel;
CREATE TABLE IF NOT EXISTS `user` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`nickname` varchar(255) NOT NULL,
`password` varchar(64) NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `nickname` (`nickname`)
);
