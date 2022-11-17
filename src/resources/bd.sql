CREATE DATABASE IF NOT EXISTS kestrel;
USE kestrel;
CREATE TABLE IF NOT EXISTS `users` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`username` varchar(255) NOT NULL,
`password` varchar(64) NOT NULL,
PRIMARY KEY (`id`)
);
