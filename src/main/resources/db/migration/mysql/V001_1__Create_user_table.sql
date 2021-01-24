DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (

    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL,
    `brewery` VARCHAR(50) NOT NULL,
    `password` CHAR(60) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `city` VARCHAR(100) NOT NULL,
    `country` VARCHAR(100),
    `logo` VARCHAR(100) NULL,
    `sortiment` BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`username`)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8 DEFAULT COLLATE utf8_general_ci;