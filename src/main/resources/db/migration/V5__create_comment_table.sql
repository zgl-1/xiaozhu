CREATE TABLE `mylife`.`comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT NULL,
  `type` INT NULL,
  `commentator` BIGINT NULL,
  `gmt_create` BIGINT NULL,
  `gmt_modified` BIGINT NULL,
  `like_count` BIGINT NULL DEFAULT 0,
  PRIMARY KEY (`id`));