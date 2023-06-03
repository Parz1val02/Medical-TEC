-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 35.231.147.174    Database: telesystem_2
-- ------------------------------------------------------
-- Server version	8.0.26-google

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `form_invitacion`
--

DROP TABLE IF EXISTS `form_invitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_invitacion` (
  `idform_invitacion` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(45) DEFAULT NULL,
  `apellidos` varchar(45) DEFAULT NULL,
  `id_sede` varchar(45) DEFAULT NULL,
  `dni` varchar(45) DEFAULT NULL,
  `edad` varchar(45) DEFAULT NULL,
  `sexo` varchar(45) DEFAULT NULL,
  `domicilio` varchar(45) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `id_seguro` varchar(45) DEFAULT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `medicamentos` varchar(200) DEFAULT NULL,
  `alergias` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idform_invitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_invitacion`
--

LOCK TABLES `form_invitacion` WRITE;
/*!40000 ALTER TABLE `form_invitacion` DISABLE KEYS */;
INSERT INTO `form_invitacion` VALUES (1,'Leonardo','Abanto','1','23456789,45678912','70','M','la paz 123','aaa@pucp.edu.pe','4','987654321','-','-'),(2,'Nicole','Guillen','1','79505741','22','F','Avenida Bolivar 2150','nicguillen@example.com','3','942108977','-','-'),(5,'Ana','López','1','11111111','99','M','Catolica 123','wnsfd@example.com','1','987654321','Metanfetamina','Valorant'),(6,'Hiroshi','Giotoku','1','73222256','50','M','Catolica 123','hiroshigiotoku@gmail.com','1','987654321','-','-');
/*!40000 ALTER TABLE `form_invitacion` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-01 20:11:19
