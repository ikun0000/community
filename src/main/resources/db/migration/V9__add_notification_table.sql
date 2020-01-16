CREATE TABLE IF NOT EXISTS `notification`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `notifier` INT,
    `receiver` INT,
    `outer_id` INT,
    `type` INT,
    `gmt_create` DATETIME DEFAULT now(),
    `status` INT DEFAULT 0
) ENGINE=InnoDB default CHARSET=utf8;