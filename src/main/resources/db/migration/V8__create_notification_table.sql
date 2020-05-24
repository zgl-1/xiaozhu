CREATE TABLE `mylife`.`notification` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `notifier` BIGINT NULL,
  `receiver` BIGINT NULL,
  `outerId` BIGINT NULL,
  `type` INT NULL,
  `gmt_creater` BIGINT NULL,
  `status` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
