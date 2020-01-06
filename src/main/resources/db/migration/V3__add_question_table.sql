CREATE TABLE IF NOT EXISTS `question`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(50) NOT NULL,
    `description` TEXT,
    `gmt_create` DATETIME DEFAULT now(),
    `gmt_modify` DATETIME DEFAULT now(),
    `creator` INT NOT NULL,
    `comment_count` INT DEFAULT 0,
    `view_count` INT DEFAULT 0,
    `like_count` INT DEFAULT 0,
    `tag` VARCHAR(256)
) ENGINE=InnoDB default CHARSET=utf8;