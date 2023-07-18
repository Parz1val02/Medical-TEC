-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: midatabase.mysql.database.azure.com    Database: telesystem_2
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
-- Table structure for table `reunion_virtual`
--

DROP TABLE IF EXISTS `reunion_virtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reunion_virtual` (
  `idreunion_virtual` int NOT NULL AUTO_INCREMENT,
  `fecha` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `horainicio` date NOT NULL,
  `horafin` date NOT NULL,
  `token` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `room` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario_dni` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `usuario_dni1` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idreunion_virtual`),
  KEY `fk_reunion_virtual_usuario2_idx` (`usuario_dni`),
  KEY `fk_reunion_virtual_usuario3_idx` (`usuario_dni1`),
  CONSTRAINT `fk_reunion_virtual_usuario2` FOREIGN KEY (`usuario_dni`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `fk_reunion_virtual_usuario3` FOREIGN KEY (`usuario_dni1`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reunion_virtual`
--

LOCK TABLES `reunion_virtual` WRITE;
/*!40000 ALTER TABLE `reunion_virtual` DISABLE KEYS */;
/*!40000 ALTER TABLE `reunion_virtual` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:56:10
