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
-- Table structure for table `form_autoregistro`
--

DROP TABLE IF EXISTS `form_autoregistro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_autoregistro` (
  `idautoregistro` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombres` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `apellidos` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechanacimiento` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `domicilio` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sexo` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `celular` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `seguroid` int DEFAULT NULL,
  `sedeid` int DEFAULT NULL,
  `correo` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pendiente` tinyint DEFAULT NULL,
  `contrasenia` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idautoregistro`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_autoregistro`
--

LOCK TABLES `form_autoregistro` WRITE;
/*!40000 ALTER TABLE `form_autoregistro` DISABLE KEYS */;
INSERT INTO `form_autoregistro` VALUES (3,'76543219','MERCI YOSELIN','MENDEZ AGUILAR','13-06-2002','456 Oak aaaeeeaaa','F','951732684',1,1,'mercibeaucoup@pucp.pe',1,'$2a$10$1kMjQ5yAxDdBxHu.lzI2RupYn.i.73XVjNZ4aKTQIynMPHSXYAHiK');
/*!40000 ALTER TABLE `form_autoregistro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-26  0:04:12
