-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: stanicatuzla
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `rasporedsedmica`
--

DROP TABLE IF EXISTS `rasporedsedmica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rasporedsedmica` (
  `idrasporedSedmica` int NOT NULL AUTO_INCREMENT,
  `linijaFK` int NOT NULL,
  `vrPolaska` time NOT NULL,
  `vrPovratka` time DEFAULT NULL,
  `prevoznikFK` int DEFAULT NULL,
  `pon` char(1) DEFAULT '-',
  `uto` char(1) DEFAULT '-',
  `sri` char(1) DEFAULT '-',
  `cet` char(1) DEFAULT '-',
  `pet` char(1) DEFAULT '-',
  `sub` char(1) DEFAULT '-',
  `ned` char(1) DEFAULT '-',
  `praznik` char(1) DEFAULT '-',
  PRIMARY KEY (`idrasporedSedmica`),
  KEY `linija_idx` (`linijaFK`),
  KEY `prevoznik_idx` (`prevoznikFK`),
  CONSTRAINT `linija` FOREIGN KEY (`linijaFK`) REFERENCES `linije` (`idLinije`),
  CONSTRAINT `prevoznik` FOREIGN KEY (`prevoznikFK`) REFERENCES `prijevoznici` (`idprijevoznici`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rasporedsedmica`
--

LOCK TABLES `rasporedsedmica` WRITE;
/*!40000 ALTER TABLE `rasporedsedmica` DISABLE KEYS */;
INSERT INTO `rasporedsedmica` VALUES (2,2,'13:30:00','19:20:00',1,'X','-','X','-','X','-','X','-'),(4,4,'01:10:00','09:10:00',2,'X','-','X','-','X','-','X','-'),(5,4,'01:10:00','09:10:00',1,'-','X','-','X','-','X','-','X'),(8,1,'07:00:00','10:00:00',1,'X','X','X','X','X','X','X','-'),(9,1,'17:00:00','20:00:00',1,'X','X','X','X','X','X','X','-'),(10,1,'09:00:00','12:00:00',6,'X','-','X','-','X','-','-','-'),(11,2,'07:20:00','10:20:00',4,'-','X','-','X','-','X','-','-'),(12,2,'08:15:00','11:15:00',4,'X','X','X','X','X','X','X','-'),(13,13,'08:00:00','10:00:00',4,'X','X','X','X','X','X','X','X'),(14,23,'09:00:00','11:00:00',9,'X','X','X','X','X','X','X','X'),(15,24,'07:00:00','09:00:00',9,'X','X','X','X','X','X','X','-');
/*!40000 ALTER TABLE `rasporedsedmica` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-11 19:49:21
