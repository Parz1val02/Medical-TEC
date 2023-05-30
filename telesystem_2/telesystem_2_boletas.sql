-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
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
-- Table structure for table `boletas`
--

DROP TABLE IF EXISTS `boletas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boletas` (
  `idboletas` int NOT NULL AUTO_INCREMENT,
  `conceptopago` varchar(100) NOT NULL,
  `monto` double NOT NULL,
  `seguros_id_seguro` int NOT NULL,
  `receta_idreceta` int DEFAULT NULL,
  `cita_idcita` int NOT NULL,
  `examen_medico_idexamen` int DEFAULT NULL,
  PRIMARY KEY (`idboletas`),
  KEY `fk_boletas_seguros1_idx` (`seguros_id_seguro`),
  KEY `fk_boletas_receta1_idx` (`receta_idreceta`),
  KEY `fk_boletas_cita1_idx` (`cita_idcita`),
  KEY `fk_boletas_examen_medico1_idx` (`examen_medico_idexamen`),
  CONSTRAINT `fk_boletas_cita1` FOREIGN KEY (`cita_idcita`) REFERENCES `cita` (`idcita`),
  CONSTRAINT `fk_boletas_examen_medico1` FOREIGN KEY (`examen_medico_idexamen`) REFERENCES `examen_medico` (`idexamen`),
  CONSTRAINT `fk_boletas_receta1` FOREIGN KEY (`receta_idreceta`) REFERENCES `receta` (`idreceta`),
  CONSTRAINT `fk_boletas_seguros1` FOREIGN KEY (`seguros_id_seguro`) REFERENCES `seguros` (`id_seguro`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boletas`
--

LOCK TABLES `boletas` WRITE;
/*!40000 ALTER TABLE `boletas` DISABLE KEYS */;
INSERT INTO `boletas` VALUES (1,'tarjeta',10,2,1,1,1),(2,'consulta medica de especialidad',60,3,7,6,11),(3,'consulta medica de especialidad',60,1,6,5,NULL),(4,'consulta medica de especialidad',60,6,4,4,NULL);
/*!40000 ALTER TABLE `boletas` ENABLE KEYS */;
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

-- Dump completed on 2023-05-30 16:22:38
