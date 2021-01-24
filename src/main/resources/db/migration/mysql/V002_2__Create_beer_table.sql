DROP TABLE IF EXISTS `beers`;
CREATE TABLE `beers` (

    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `beer_name` VARCHAR(60) NOT NULL,
    `user_id` BIGINT NULL,
    `type` VARCHAR(40) NOT NULL,
    `ibu` INT NULL,
    `abv` FLOAT NULL,
    `ebc` FLOAT NULL,
    `stock` INTEGER NOT NULL,
    `rate` FLOAT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES users (id)

)ENGINE=InnoDB DEFAULT CHARSET=UTF8 DEFAULT COLLATE utf8_general_ci;