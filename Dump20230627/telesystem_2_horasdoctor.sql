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
-- Table structure for table `horasdoctor`
--

DROP TABLE IF EXISTS `horasdoctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horasdoctor` (
  `idhorasdoctor` int NOT NULL,
  `horainicio` time NOT NULL,
  `horafin` time NOT NULL,
  `horalibre` time NOT NULL,
  `doctor_dni` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dias` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mes` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`idhorasdoctor`),
  KEY `fk_horasdoctor_usuario1_idx` (`doctor_dni`),
  CONSTRAINT `fk_horasdoctor_usuario1` FOREIGN KEY (`doctor_dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horasdoctor`
--

LOCK TABLES `horasdoctor` WRITE;
/*!40000 ALTER TABLE `horasdoctor` DISABLE KEYS */;
INSERT INTO `horasdoctor` VALUES (1,'08:00:00','17:00:00','12:00:00','12345678','Lunes,Martes,Viernes','Junio'),(2,'07:00:00','16:00:00','12:00:00','14578934','Lunes,Martes,Viernes','Junio'),(3,'09:00:00','18:00:00','13:00:00','73805724','Lunes,Martes,Viernes','Junio'),(4,'12:00:00','22:00:00','16:00:00','75030121','Lunes,Martes,Viernes','Junio');
/*!40000 ALTER TABLE `horasdoctor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:58:05
