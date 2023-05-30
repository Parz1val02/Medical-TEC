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
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `idcita` int NOT NULL AUTO_INCREMENT,
  `citacancelada` tinyint(1) DEFAULT NULL,
  `sedes_idsedes` int NOT NULL,
  `especialidades_id_especialidad` int NOT NULL,
  `estadoscita_idestados` int DEFAULT NULL,
  `receta_idreceta` int DEFAULT NULL,
  `tarjeta_idtarjetas` int DEFAULT NULL,
  `formapago` varchar(45) NOT NULL,
  `modalidad` varchar(45) NOT NULL,
  `tipocita_idtipocita` int NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `paciente_dni` varchar(8) NOT NULL,
  `doctor_dni1` varchar(8) NOT NULL,
  PRIMARY KEY (`idcita`),
  KEY `fk_cita_sedes1_idx` (`sedes_idsedes`),
  KEY `fk_cita_especialidades1_idx` (`especialidades_id_especialidad`),
  KEY `fk_cita_estadoscita1_idx` (`estadoscita_idestados`),
  KEY `fk_cita_receta1_idx` (`receta_idreceta`),
  KEY `fk_cita_tarjeta1_idx` (`tarjeta_idtarjetas`),
  KEY `fk_cita_tipocita1_idx` (`tipocita_idtipocita`),
  KEY `fk_cita_usuario1_idx` (`paciente_dni`),
  KEY `fk_cita_usuario2_idx` (`doctor_dni1`),
  CONSTRAINT `fk_cita_especialidades1` FOREIGN KEY (`especialidades_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`),
  CONSTRAINT `fk_cita_estadoscita1` FOREIGN KEY (`estadoscita_idestados`) REFERENCES `estadoscita` (`idestados`),
  CONSTRAINT `fk_cita_receta1` FOREIGN KEY (`receta_idreceta`) REFERENCES `receta` (`idreceta`),
  CONSTRAINT `fk_cita_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`),
  CONSTRAINT `fk_cita_tarjeta1` FOREIGN KEY (`tarjeta_idtarjetas`) REFERENCES `tarjeta` (`idtarjetas`),
  CONSTRAINT `fk_cita_tipocita1` FOREIGN KEY (`tipocita_idtipocita`) REFERENCES `tipocita` (`idtipocita`),
  CONSTRAINT `fk_cita_usuario1` FOREIGN KEY (`paciente_dni`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `fk_cita_usuario2` FOREIGN KEY (`doctor_dni1`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,0,1,1,3,1,1,'tarjeta','virtual',1,'2023-04-22','20:00:00','23456789','12345678'),(2,0,2,17,1,2,1,'tarjeta','presencial',1,'2023-05-17','16:00:00','79505741','17845767'),(3,0,2,13,1,5,NULL,'efectivo','presencial',1,'2023-05-03','20:30:00','72216647','73410317'),(4,0,3,1,1,4,2,'tarjeta','presencial',1,'2023-05-20','10:00:00','22647853','77312461'),(5,0,3,9,NULL,6,NULL,'En caja','Presencial',1,'2023-05-22','14:00:00','28573467','77817288'),(6,0,3,9,NULL,7,1,'tarjeta','Presencial',1,'2023-05-22','14:00:01','28573467','77817288'),(7,0,1,6,NULL,NULL,NULL,'En caja','Presencial',1,'2023-05-31','06:00:00','28573467','77312461'),(8,0,1,8,NULL,NULL,NULL,'En caja','Presencial',1,'2023-05-25','20:00:00','28573467','77312461'),(10,0,1,1,NULL,NULL,NULL,'En caja','Presencial',1,'2023-06-01','04:10:00','22647853','73805736');
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
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

-- Dump completed on 2023-05-30 16:21:39
