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
-- Table structure for table `receta_has_medicamentos`
--

DROP TABLE IF EXISTS `receta_has_medicamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta_has_medicamentos` (
  `receta_idreceta` int NOT NULL,
  `medicamentos_idmedicamentos` int NOT NULL,
  `cantidad` int NOT NULL,
  `observaciones` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`receta_idreceta`,`medicamentos_idmedicamentos`),
  KEY `fk_receta_has_medicamentos_medicamentos1_idx` (`medicamentos_idmedicamentos`),
  KEY `fk_receta_has_medicamentos_receta1_idx` (`receta_idreceta`),
  CONSTRAINT `fk_receta_has_medicamentos_medicamentos1` FOREIGN KEY (`medicamentos_idmedicamentos`) REFERENCES `medicamentos` (`idmedicamentos`),
  CONSTRAINT `fk_receta_has_medicamentos_receta1` FOREIGN KEY (`receta_idreceta`) REFERENCES `receta` (`idreceta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta_has_medicamentos`
--

LOCK TABLES `receta_has_medicamentos` WRITE;
/*!40000 ALTER TABLE `receta_has_medicamentos` DISABLE KEYS */;
INSERT INTO `receta_has_medicamentos` VALUES (1,1,2,'Diario'),(1,6,1,'Durante 3 Dias'),(2,1,2,'Diario'),(4,2,1,'Interdiario'),(5,6,1,'8 dias');
/*!40000 ALTER TABLE `receta_has_medicamentos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:57:36
