ALTER TABLE `mylife`.`comment` 
ADD COLUMN `comment_count` INT NULL DEFAULT 0 AFTER `content`;
