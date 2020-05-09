## 小猪社区

## 资料
[Spring 文档](https://spring.io/guides)
[BootStrap](https://v3.bootcss.com/)
[GitHub OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

## 工具
[Git](https://git-scm.com/download)

## 脚本
```sql
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `account_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1
```