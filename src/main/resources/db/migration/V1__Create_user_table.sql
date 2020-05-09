CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `account_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1