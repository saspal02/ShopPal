-- v1__init.mysql
-- Initial schema for ShopPal Order Service

CREATE TABLE `orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_number` VARCHAR(255) DEFAULT NULL,
    `sku_code` VARCHAR(255),
    `price` DECIMAL(19,2),
    `quantity` INT,
    PRIMARY KEY (`id`)
);
