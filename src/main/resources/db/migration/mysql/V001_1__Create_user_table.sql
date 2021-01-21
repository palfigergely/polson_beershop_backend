DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (

    `id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(50) NOT NULL,
    `brewery` varchar(50) NOT NULL,
    `password` char(60) NOT NULL,
    `email` varchar(100) NOT NULL,
    `city` varchar(100) NOT NULL,
    `country` varchar(100),
    `logo` varchar(100) NULL,
    `sortiment` int NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`username`)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8 DEFAULT COLLATE utf8_general_ci;