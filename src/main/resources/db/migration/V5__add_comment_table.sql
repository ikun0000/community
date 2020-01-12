CREATE TABLE IF NOT EXISTS `comment`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `parent_id` INT NOT NULL,
    `type` INT,
    `commentator` INT,
    `gmt_create` DATETIME DEFAULT now(),
    `gmt_modify` DATETIME DEFAULT now(),
    `like_count` INT DEFAULT 0
) ENGINE=InnoDB default CHARSET=utf8;