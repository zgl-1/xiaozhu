ALTER TABLE `mylife`.`notification` 
ADD COLUMN `notifier_name` VARCHAR(100) NULL AFTER `status`,
ADD COLUMN `outer_title` VARCHAR(256) NULL AFTER `notifier_name`;