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
-- Table structure for table `boletas`
--

DROP TABLE IF EXISTS `boletas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boletas` (
  `idboletas` int NOT NULL AUTO_INCREMENT,
  `conceptopago` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `monto` double NOT NULL,
  `seguros_id_seguro` int NOT NULL,
  `receta_idreceta` int DEFAULT NULL,
  `cita_idcita` int NOT NULL,
  PRIMARY KEY (`idboletas`),
  KEY `fk_boletas_seguros1_idx` (`seguros_id_seguro`),
  KEY `fk_boletas_receta1_idx` (`receta_idreceta`),
  KEY `fk_boletas_cita1_idx` (`cita_idcita`),
  CONSTRAINT `fk_boletas_cita1` FOREIGN KEY (`cita_idcita`) REFERENCES `cita` (`idcita`),
  CONSTRAINT `fk_boletas_receta1` FOREIGN KEY (`receta_idreceta`) REFERENCES `receta` (`idreceta`),
  CONSTRAINT `fk_boletas_seguros1` FOREIGN KEY (`seguros_id_seguro`) REFERENCES `seguros` (`id_seguro`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boletas`
--

LOCK TABLES `boletas` WRITE;
/*!40000 ALTER TABLE `boletas` DISABLE KEYS */;
INSERT INTO `boletas` VALUES (1,'consulta medica de especialidad',60,2,1,13),(2,'consulta medica de especialidad',60,3,7,17),(3,'consulta medica de especialidad',60,1,6,25),(4,'consulta medica de especialidad',60,6,4,37);
/*!40000 ALTER TABLE `boletas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:57:31
