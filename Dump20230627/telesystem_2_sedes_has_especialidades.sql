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
-- Table structure for table `sedes_has_especialidades`
--

DROP TABLE IF EXISTS `sedes_has_especialidades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sedes_has_especialidades` (
  `sedes_idsedes` int NOT NULL,
  `especialidades_id_especialidad` int NOT NULL,
  PRIMARY KEY (`sedes_idsedes`,`especialidades_id_especialidad`),
  KEY `fk_sedes_has_especialidades_especialidades1_idx` (`especialidades_id_especialidad`),
  KEY `fk_sedes_has_especialidades_sedes1_idx` (`sedes_idsedes`),
  CONSTRAINT `fk_sedes_has_especialidades_especialidades1` FOREIGN KEY (`especialidades_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`),
  CONSTRAINT `fk_sedes_has_especialidades_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sedes_has_especialidades`
--

LOCK TABLES `sedes_has_especialidades` WRITE;
/*!40000 ALTER TABLE `sedes_has_especialidades` DISABLE KEYS */;
INSERT INTO `sedes_has_especialidades` VALUES (1,1),(1,2),(1,7),(1,8),(1,9),(1,13),(1,17),(1,18);
/*!40000 ALTER TABLE `sedes_has_especialidades` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:57:53
