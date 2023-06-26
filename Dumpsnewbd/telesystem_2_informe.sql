-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
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
-- Table structure for table `informe`
--

DROP TABLE IF EXISTS `informe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `informe` (
  `idinforme` int NOT NULL AUTO_INCREMENT,
  `diagnostico` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bitacora` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `historialmedico_idhistorialmedico` int NOT NULL,
  `activo` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idinforme`),
  KEY `fk_reporte_historialmedico1_idx` (`historialmedico_idhistorialmedico`),
  CONSTRAINT `fk_reporte_historialmedico1` FOREIGN KEY (`historialmedico_idhistorialmedico`) REFERENCES `historialmedico` (`idhistorialmedico`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informe`
--

LOCK TABLES `informe` WRITE;
/*!40000 ALTER TABLE `informe` DISABLE KEYS */;
INSERT INTO `informe` VALUES (1,'Hipertensión arterial','Fecha de la cita: 20 de abril de 2023\nHora de la cita: 10:00 a.m.\nMotivo de la consulta: Dolor abdominal y náuseas',1,'1'),(2,'Riesgo de lesion','Fecha de la cita: 20 de abril de 2023\nHora de la cita: 11:00 a.m.\nMotivo de la consulta: Dolor en la pierna',1,'1'),(3,'Acne','Fecha de la cita: 17 de mayo de 2023 ',2,'1'),(4,'Colicos','Fecha de la cita: 3 de mayo de 2023 Motivo de la consulta: Dolor de estomago',3,'1'),(5,'Dolor de garganta','Fecha de la cita: 23 de mayo de 2023 Motivo de la consulta: Dolor de estomago',4,'1'),(6,'Somnolencia extrema','Fecha de la cita: 4 de mayo de 2023 Motivo de la consulta: Dolor de estomago',4,'1'),(7,'Covid','Fecha de la cita: 13 de mayo de 2023 Motivo de la consulta: Dolor de estomago',4,'1'),(8,'Síndrome de Frégoli','Fecha de la cita: 30 de mayo de 2023 Motivo de la consulta: Dolor de estomago',4,'0');
/*!40000 ALTER TABLE `informe` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-26  0:04:05
