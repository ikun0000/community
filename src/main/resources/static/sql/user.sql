
CREATE TABLE if NOT EXISTS `user` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `account_id` VARCHAR(100) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    `token` CHAR(36) UNIQUE,
    gmt_create DATETIME DEFAULT now(),
    gmt_modify DATETIME DEFAULT now()
) ENGINE=InnoDB default CHARSET=utf8;