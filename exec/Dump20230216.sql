-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: i8d106.p.ssafy.io    Database: naegagalga
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `building_index` int NOT NULL AUTO_INCREMENT,
  `building_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `building_elevator` bit(1) NOT NULL,
  `building_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `building_parking` bit(1) NOT NULL,
  `building_road_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`building_index`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'경북 영주시 봉현면 한천리 318-1',_binary '\0','12',_binary '\0','경북 영주시 봉현면 소백로 1320-3'),(2,'경북 구미시 인의동 702-8',_binary '\0','월드빌',_binary '','경북 구미시 인동23길 6'),(3,'전북 고창군 고창읍 읍내리 681-293',_binary '\0','123',_binary '\0','전북 고창군 고창읍 보릿골로 134'),(4,'경북 구미시 진평동 626-3',_binary '','싸피빌라',_binary '','경북 구미시 이계남로 144'),(5,'경북 구미시 남통동 270-6',_binary '\0','싸피 아파트',_binary '\0','경북 구미시 금오산상가길 9-4'),(6,'경북 구미시 황상동 274-6',_binary '','황상빌',_binary '','경북 구미시 수출대로25길 6-10'),(7,'경북 구미시 구평동 462-12',_binary '','황제빌라트',_binary '\0','경북 구미시 인동46길 5-46'),(8,'경북 구미시 진평동 498-2',_binary '\0','그레이빌',_binary '','경북 구미시 진평길 75-18');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `chat_message_index` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `is_read` bit(1) NOT NULL DEFAULT b'0',
  `message` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `chat_index` int DEFAULT NULL,
  `sender_index` int DEFAULT NULL,
  PRIMARY KEY (`chat_message_index`),
  KEY `FK6h8lp6e04i822eo99ivpnphtr` (`chat_index`),
  KEY `FK92w8r2vu141kcuyerf4ke555m` (`sender_index`),
  CONSTRAINT `FK6h8lp6e04i822eo99ivpnphtr` FOREIGN KEY (`chat_index`) REFERENCES `chat_room` (`chat_index`),
  CONSTRAINT `FK92w8r2vu141kcuyerf4ke555m` FOREIGN KEY (`sender_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES (1,'2023-02-16 21:47:17',_binary '\0','2023-02-22 07:30\n 예약이 완료 되었습니다.',1,4),(2,'2023-02-16 21:51:21',_binary '\0','asdf',1,3),(3,'2023-02-16 21:51:22',_binary '\0','asdf',1,3),(4,'2023-02-16 21:51:25',_binary '\0','asdf',1,3),(5,'2023-02-16 21:51:26',_binary '\0','efae',1,3),(6,'2023-02-16 21:51:45',_binary '\0','efef',1,3),(7,'2023-02-16 21:51:47',_binary '\0','efasdf',1,3),(8,'2023-02-16 21:51:51',_binary '\0','aaaaaaaaaaaaaaaaaaaaaaaa',1,3),(14,'2023-02-16 22:39:24',_binary '\0','안녕하세요~',4,2),(15,'2023-02-16 22:39:40',_binary '\0','이번주 일요일에 매물 보고싶은데 괜찮을까요?',4,2),(16,'2023-02-16 22:40:08',_binary '\0','네~ 가능합니다',4,6),(17,'2023-02-16 22:40:33',_binary '\0','일요일 오후에 가능하신 시간에 예약 걸어주세요',4,6),(18,'2023-02-16 22:41:22',_binary '\0','2023-02-19 07:00\n 예약이 완료 되었습니다.',4,2),(19,'2023-02-16 22:55:29',_binary '\0','안녕하세요!',3,1),(20,'2023-02-16 22:55:37',_binary '\0','안녕하세요~',3,5),(21,'2023-02-16 22:55:50',_binary '\0','다음주 금요일 오후 1시에 방 구경 가능할까요?',3,1),(22,'2023-02-16 22:55:59',_binary '\0','네 가능합니다.',3,5),(23,'2023-02-16 22:58:47',_binary '\0','2023-02-24 01:00\n 예약이 완료 되었습니다.',3,1),(24,'2023-02-17 00:45:04',_binary '\0','안녕하세요',5,3),(25,'2023-02-17 00:55:40',_binary '\0','2023-02-24 07:00\n 예약이 완료 되었습니다.',3,1),(26,'2023-02-17 02:21:16',_binary '\0','배고파',3,1),(27,'2023-02-17 02:21:31',_binary '\0','2023-02-23 12:30\n 예약이 완료 되었습니다.',3,1),(28,'2023-02-17 02:24:29',_binary '\0','야',3,1),(29,'2023-02-17 02:24:31',_binary '\0','이거줘',3,1);
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `chat_index` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `buyer_index` int DEFAULT NULL,
  `seller_index` int DEFAULT NULL,
  PRIMARY KEY (`chat_index`),
  KEY `FKmu6cxnfuhw5iqqjmwolhwdkl3` (`buyer_index`),
  KEY `FKspl2pnuolab013c0umm948r37` (`seller_index`),
  CONSTRAINT `FKmu6cxnfuhw5iqqjmwolhwdkl3` FOREIGN KEY (`buyer_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `FKspl2pnuolab013c0umm948r37` FOREIGN KEY (`seller_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,'2023-02-16 12:47:13',4,3),(2,'2023-02-16 13:23:17',5,6),(3,'2023-02-16 13:26:14',1,5),(4,'2023-02-16 13:39:09',2,6),(5,'2023-02-16 15:44:54',3,6);
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `explanation`
--

DROP TABLE IF EXISTS `explanation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `explanation` (
  `explanation_index` int NOT NULL AUTO_INCREMENT,
  `meeting_index` int DEFAULT NULL,
  `user_index` int DEFAULT NULL,
  PRIMARY KEY (`explanation_index`),
  KEY `FKdt0cc6yhpc1ro5aehmeha8s0e` (`meeting_index`),
  KEY `FKak0jttliwoppia0vmrt501j1r` (`user_index`),
  CONSTRAINT `FKak0jttliwoppia0vmrt501j1r` FOREIGN KEY (`user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `FKdt0cc6yhpc1ro5aehmeha8s0e` FOREIGN KEY (`meeting_index`) REFERENCES `meeting` (`meeting_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `explanation`
--

LOCK TABLES `explanation` WRITE;
/*!40000 ALTER TABLE `explanation` DISABLE KEYS */;
/*!40000 ALTER TABLE `explanation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting` (
  `meeting_index` int NOT NULL AUTO_INCREMENT,
  `meeting_url` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reserve_at` datetime DEFAULT NULL,
  `guest_index` int DEFAULT NULL,
  `owner_index` int NOT NULL,
  `product_index` int DEFAULT NULL,
  PRIMARY KEY (`meeting_index`),
  KEY `FKgb36gc0vnn4vudv9gqd62k31a` (`guest_index`),
  KEY `FKc1ie6iinsre9u9r5m2egww6jw` (`owner_index`),
  KEY `FKd4j8rv8voy6ctxywerdfdc66u` (`product_index`),
  CONSTRAINT `FKc1ie6iinsre9u9r5m2egww6jw` FOREIGN KEY (`owner_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `FKd4j8rv8voy6ctxywerdfdc66u` FOREIGN KEY (`product_index`) REFERENCES `product` (`product_index`),
  CONSTRAINT `FKgb36gc0vnn4vudv9gqd62k31a` FOREIGN KEY (`guest_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

LOCK TABLES `meeting` WRITE;
/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
INSERT INTO `meeting` VALUES (1,'/meeting/one-on-one/1','2023-02-16 16:30:00',3,3,NULL),(2,'/meeting/one-on-one/2','2023-02-22 16:30:00',3,4,NULL),(4,'/meeting/one-on-one/4','2023-02-19 16:00:00',6,2,NULL),(6,'/meeting/one-on-one/6','2023-02-24 10:00:00',5,1,NULL),(7,'/meeting/one-on-one/7','2023-02-24 16:00:00',5,1,NULL),(8,'/meeting/one-on-one/8','2023-02-23 21:30:00',5,1,NULL);
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `option_index` int NOT NULL AUTO_INCREMENT,
  `option_air_conditioner` bit(1) NOT NULL DEFAULT b'0',
  `option_bed` bit(1) NOT NULL DEFAULT b'0',
  `option_closet` bit(1) NOT NULL DEFAULT b'0',
  `option_desk` bit(1) NOT NULL DEFAULT b'0',
  `option_fridge` bit(1) NOT NULL DEFAULT b'0',
  `option_gas_stove` bit(1) NOT NULL DEFAULT b'0',
  `option_induction` bit(1) NOT NULL DEFAULT b'0',
  `option_micro_wave` bit(1) NOT NULL DEFAULT b'0',
  `option_washing_machine` bit(1) NOT NULL DEFAULT b'0',
  `option_wifi` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`option_index`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (2,_binary '',_binary '\0',_binary '\0',_binary '',_binary '',_binary '\0',_binary '\0',_binary '\0',_binary '',_binary ''),(5,_binary '',_binary '',_binary '',_binary '',_binary '',_binary '\0',_binary '',_binary '',_binary '\0',_binary ''),(7,_binary '',_binary '\0',_binary '',_binary '',_binary '',_binary '\0',_binary '',_binary '',_binary '',_binary ''),(8,_binary '',_binary '\0',_binary '',_binary '',_binary '',_binary '\0',_binary '',_binary '',_binary '',_binary ''),(9,_binary '',_binary '\0',_binary '',_binary '',_binary '',_binary '\0',_binary '',_binary '\0',_binary '',_binary '\0');
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_index` int NOT NULL AUTO_INCREMENT,
  `product_animal` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_detail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_direction` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_floor` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_manage_cost` int DEFAULT '0',
  `product_photo` text COLLATE utf8mb4_unicode_ci,
  `product_price` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_rooms` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_size` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `building_index` int DEFAULT NULL,
  `option_index` int DEFAULT NULL,
  `seller_index` int DEFAULT NULL,
  PRIMARY KEY (`product_index`),
  KEY `FK91j83s1aph66ru2ndw7f7asa3` (`building_index`),
  KEY `FKa0nvvvi1xrbknmh0ax92sf1r2` (`option_index`),
  KEY `FK4k2804tbse9h1almpauvjgped` (`seller_index`),
  CONSTRAINT `FK4k2804tbse9h1almpauvjgped` FOREIGN KEY (`seller_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `FK91j83s1aph66ru2ndw7f7asa3` FOREIGN KEY (`building_index`) REFERENCES `building` (`building_index`),
  CONSTRAINT `FKa0nvvvi1xrbknmh0ax92sf1r2` FOREIGN KEY (`option_index`) REFERENCES `options` (`option_index`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2,'불가능','201','남향','2층/4층',5,'경북 구미시 인동23길 6/201/0.jpeg','10000','원룸','32','전세',2,2,5),(5,'불가능','101','동남','10층/3층',5,'경북 구미시 이계남로 144/101/0.jpeg,경북 구미시 이계남로 144/101/1.jpeg','1000/20','원룸','11.3','월세',4,5,6),(7,'가능','503','남향','20층/5층',50,'경북 구미시 수출대로25길 6-10/503/0.jpeg','10000','원룸','40','전세',6,7,5),(8,'불가능','202','남서','4층/2층',5,'경북 구미시 인동46길 5-46/202/0.jpeg','5000/35','원룸','24','월세',7,8,5),(9,'확인필요','206','남향','4층/2층',5,'경북 구미시 진평길 75-18/206/0.png,경북 구미시 진평길 75-18/206/1.png,경북 구미시 진평길 75-18/206/2.png,경북 구미시 진평길 75-18/206/3.png,경북 구미시 진평길 75-18/206/4.png','200/25','원룸','33','월세',8,9,7);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_index` int NOT NULL AUTO_INCREMENT,
  `corporate_registration_number` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_address` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_phone` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_index`),
  UNIQUE KEY `UK_a3imlf41l37utmxiquukk8ajc` (`user_id`),
  UNIQUE KEY `UK_p150eamh0glg042j8x6nvbc48` (`corporate_registration_number`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'오민준','경북 구미시 진평길 75-18 그레이빌 206호','omj9512','$2a$10$.b58LRJhnhuiu3ikfuDpxuLFO4znperwIbv1EeEy/LyZc./bMxXoC','010-9169-5671'),(2,NULL,'박재희','경북 구미시 삼일로 12 싸피빌라 101호','jaehe0413','$2a$10$J.49pdL6QvcDYV/T/pLImuOmyN7l9FA9lh3K46EbC38cUA1ObLTu6','010-3091-1256'),(3,NULL,'이흥종','경북 영주시 봉현면 소백로 1320-3 456','avjg111','$2a$10$BZMYfe3.S48DfTAxVsbEBe4dqOFDiEr/BgWk01S2sdjx7n1RGb6uG','010-1234-5678'),(4,NULL,'아아','경북 경산시 하양읍 교리길 22 12','avjg000','$2a$10$JY5AiRbNMFtEU0MhSJtaletwP1PkK2d8vv2Nxp4o/uueSDcZq5vKu','010-1234-5679'),(5,NULL,'신동윤','경북 구미시 진평길 75-9 리와코 201호','olivedeko','$2a$10$mE71nbLFYuWCNdWU5Y4YiuJwOoXgADSRAJTN6rsW60XRvojwG2/au','010-1234-5678'),(6,NULL,'싸피부동산','경북 구미시 사곡로1길 8 삼성빌라 301호','ssafyd106','$2a$10$bKW5bbbnR5eNZDTs0PTCTenkelXTt8XP30qdkjJ.HaPqTTtnKVLfW','010-6041-2036'),(7,'1234567890','싸피부동산','경북 구미시 진평길 75-8 싸피부동산','ssafy1','$2a$10$zFFCK3tvaJBWno/nosS7jOmmOvOlMxo7ixsGRd4yA9brHr.L3VJwO','010-1234-5678');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_user_index` int NOT NULL,
  `roles` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY `FKb3wqx7gf7ehpkevjc4a9pj4e5` (`user_user_index`),
  CONSTRAINT `FKb3wqx7gf7ehpkevjc4a9pj4e5` FOREIGN KEY (`user_user_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'USER'),(2,'USER'),(3,'USER'),(4,'USER'),(5,'USER'),(6,'USER'),(7,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-16 17:46:57
