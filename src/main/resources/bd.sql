CREATE DATABASE IF NOT EXISTS kestrel;
USE kestrel;
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `nickname` varchar(255) NOT NULL,
                                      `password` varchar(64) NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `nickname` (`nickname`)
);

CREATE TABLE IF NOT EXISTS `post`(
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `user_id` int(11) NOT NULL,
                                     `content` varchar(255) NOT NULL,
                                     `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `edited` boolean NOT NULL DEFAULT FALSE,
                                     PRIMARY KEY (`id`),
                                     CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `comment`(
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `user_id` int(11) NOT NULL,
                                        `post_id` int(11) NOT NULL,
                                        `content` varchar(255) NOT NULL,
                                        `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`id`),
                                        CONSTRAINT `user_id_comment` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                        CONSTRAINT `post_id_comment` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `like`(
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `user_id` int(11) NOT NULL,
                                     `post_id` int(11) NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `likeID` (`user_id`,`post_id`),
                                     CONSTRAINT `user_id_like` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                     CONSTRAINT `post_id_like` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
);

CREATE TABLE IF NOT EXISTS `follow`(
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `user_id` int(11) NOT NULL,
                                       `follow_id` int(11) NOT NULL,
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `follow` (`user_id`,`follow_id`),
                                       CONSTRAINT `user_id_follow` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                       CONSTRAINT `follow_id_follow` FOREIGN KEY (`follow_id`) REFERENCES `user` (`id`)
);
