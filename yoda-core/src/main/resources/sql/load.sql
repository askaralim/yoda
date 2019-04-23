# HeidiSQL Dump 
#
# --------------------------------------------------------
# Host:                 127.0.0.1
# Database:             yoda
# Server version:       5.0.51a-community-nt
# Server OS:            Win32
# Target-Compatibility: Standard ANSI SQL
# HeidiSQL version:     3.2 Revision: 1129
# --------------------------------------------------------

/*!40100 SET CHARACTER SET latin1;*/
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ANSI';*/
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;*/

#
# Dumping data for table 'menu'
#

LOCK TABLES "menu" WRITE;
/*!40000 ALTER TABLE "menu" DISABLE KEYS;*/
INSERT INTO "menu" ("menu_id", "site_id", "set_name", "title", "name", "seq_num", "parent_id", "menu_type", "menu_url", "menu_window_target", "menu_window_mode", "published", "update_by", "update_date", "create_by", "create_date", "section_id", "content_id") VALUES
    (100,1,'MAIN','Home','Home',0,0,'HOME',' ',' ',' ',true,1,'2014-05-21 00:00:00',1,'2014-05-21 00:00:00',null,null);
/*!40000 ALTER TABLE "menu" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Dumping data for table 'section'
#

LOCK TABLES "section" WRITE;
/*!40000 ALTER TABLE "section" DISABLE KEYS;*/
INSERT INTO "section" ("section_id", "site_id", "natural_key", "title", "seq_num", "parent_id", "short_title", "description", "published", "update_by", "update_date", "create_by", "create_date") VALUES
    (100,1,'','Home',0,0,'Home',' ',1,1,'2014-05-21 00:00:00',1,'2014-05-21 00:00:00');
/*!40000 ALTER TABLE "section" ENABLE KEYS;*/
UNLOCK TABLES;


#
# Dumping data for table 'site'
#

LOCK TABLES "site" WRITE;
/*!40000 ALTER TABLE "site" DISABLE KEYS;*/
INSERT INTO "site" ("site_id", "site_name", "logo_value", "logo_content_type", "active", "update_by", "update_date", "create_by", "create_date", "public_port", "secure_port", "domain_name", "google_analytics_id", "secure_connection_enabled", "footer", "listing_page_size", "section_page_size", "theme_id") VALUES
    (1,'Default Site',NULL,NULL,'Y',1,'2014-05-21 00:00:00',1,'2014-05-21 00:00:00', '8080', '', 'localhost', '', TRUE, 'Copyright &copy; 2014 yodasite.&nbsp; All rights reserved.', '10', '10', null);
/*!40000 ALTER TABLE "site" ENABLE KEYS;*/
UNLOCK TABLES;

#
# Dumping data for table 'user_'
#

LOCK TABLES "user_" WRITE;
/*!40000 ALTER TABLE "user" DISABLE KEYS;*/
INSERT INTO "user_" ("user_id", "username", "password", "account_non_expired", "account_non_locked", "credentials_non_expired", "enabled", "address_line1", "address_line2", "city_name", "state_name", "country_name", "zip_code", "email", "phone", "user_type", "last_login_date", "last_visit_site_id", "active", "update_by", "update_date", "create_by", "create_date") VALUES
    (1,'admin','$2a$10$amX8DeVP1pk6BbLrcZBbFe89S4v6XtP2qYW1OpJcpDGuZ/Dfddz0q',TRUE,TRUE,TRUE,TRUE,'','','','Xinjiang','China','admin@test.com','','','S','2014-05-21 00:00:00',0,'Y',1,'2014-05-21 00:00:00',1,'2014-05-21 00:00:00');
/*!40000 ALTER TABLE "user" ENABLE KEYS;*/
UNLOCK TABLES;

#
# Dumping data for table 'authority'
#

LOCK TABLES "authority" WRITE;
/*!40000 ALTER TABLE "authority" DISABLE KEYS;*/
INSERT INTO "authority" ("id", "user_id", "authority_name") VALUES (1,1,'ROLE_ADMIN');
INSERT INTO "authority" ("id", "user_id", "authority_name") VALUES (2,1,'ROLE_USER');
/*!40000 ALTER TABLE "user" ENABLE KEYS;*/
UNLOCK TABLES;

#
# Dumping data for table 'user_site'
#

LOCK TABLES "user_site" WRITE;
/*!40000 ALTER TABLE "user_site" DISABLE KEYS;*/
INSERT INTO "user_site" ("user_id", "site_id") VALUES (1,1);
/*!40000 ALTER TABLE "user_site" ENABLE KEYS;*/
UNLOCK TABLES;

#
# Dumping data for table 'template'
#

LOCK TABLES "template" WRITE;
/*!40000 ALTER TABLE "template" DISABLE KEYS;*/
INSERT INTO "template" ("template_id", "site_id", "template_name", "template_desc", "rec_update_by", "rec_update_datetime", "rec_create_by", "rec_create_datetime") VALUES
    (0,1,'basic','Basic Template',1,'2014-05-21 00:00:00',1,'2014-05-21 00:00:00');
/*!40000 ALTER TABLE "template" ENABLE KEYS;*/
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE;*/
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;*/
