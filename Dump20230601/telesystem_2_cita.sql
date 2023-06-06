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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,0,1,1,1,1,1,'tarjeta','virtual',1,'2023-06-22','20:00:00','23456789','12345678'),(2,0,2,17,1,2,1,'tarjeta','presencial',1,'2023-05-17','16:00:00','79505741','17845767'),(3,0,2,13,1,5,NULL,'efectivo','presencial',1,'2023-05-03','20:30:00','72216647','73410317'),(4,0,3,1,1,4,2,'tarjeta','presencial',1,'2023-05-20','10:00:00','22647853','77312461'),(5,0,3,9,NULL,6,NULL,'En caja','Presencial',1,'2023-05-22','14:00:00','28573467','77817288'),(6,0,3,9,NULL,7,1,'tarjeta','Presencial',1,'2023-05-22','14:00:01','28573467','77817288'),(7,0,1,6,NULL,NULL,NULL,'En caja','Presencial',1,'2023-05-31','06:00:00','28573467','77312461'),(8,0,1,8,NULL,NULL,NULL,'En caja','Presencial',1,'2023-05-25','20:00:00','28573467','77312461'),(10,0,1,1,NULL,NULL,NULL,'En caja','Presencial',1,'2023-06-01','04:10:00','22647853','73805736'),(11,0,3,8,3,2,1,'tarjeta','virtual',1,'2023-04-12','10:00:00','23892722','12345678'),(12,0,3,8,3,1,2,'tarjeta','virtual',1,'2023-03-15','11:00:56','22647853','12345678'),(13,0,3,8,3,5,2,'efectivo','virtual',1,'2023-05-30','08:31:02','23456789','12345678'),(14,0,3,8,3,4,2,'tarjeta','virtual',1,'2023-04-13','12:04:00','23766788','12345678'),(15,0,3,8,3,6,1,'tarjeta','presencial',1,'2023-04-12','17:00:00','33333333','12345678'),(16,0,3,8,3,1,2,'En caja','Virtual',1,'2023-05-30','11:45:00','22647853','12345678'),(17,0,3,8,3,1,2,'En caja','Virtual',1,'2023-05-30','15:30:00','23456789','12345678'),(18,0,3,8,3,1,2,'En caja','Virtual',1,'2023-05-31','10:45:00','23766788','12345678'),(19,0,3,8,3,1,2,'Efectivo','Virtual',1,'2023-02-01','14:15:00','23766788','12345678'),(20,0,3,8,3,1,2,'Efectivo','Virtual',1,'2023-02-02','11:30:00','45678975','12345678'),(21,0,3,8,3,1,2,'Efectivo','Presencial',1,'2023-02-03','09:00:00','45678975','12345678'),(22,0,3,8,3,1,2,'Efectivo','Presencial',1,'2023-03-04','16:45:00','71231035','12345678'),(23,0,3,8,3,1,2,'Tarjeta','Presencial',1,'2023-03-05','12:00:00','72216647','12345678'),(24,0,3,8,3,1,2,'Tarjeta','Presencial',1,'2023-04-06','14:30:00','22647853','12345678'),(25,0,3,8,3,1,2,'Tarjeta','Presencial',1,'2023-01-07','17:15:00','23456789','12345678'),(26,0,3,8,3,1,2,'Tarjeta','Presencial',1,'2023-04-08','13:45:00','23766788','12345678'),(27,0,3,8,1,1,1,'Efectivo','Virtual',1,'2023-06-19','10:00:00','22647853','12345678'),(28,0,3,8,1,2,2,'Tarjeta','Presencial',1,'2023-06-15','14:30:00','23456789','12345678'),(29,0,3,8,1,3,3,'En Caja','Virtual',1,'2023-06-11','16:45:00','23766788','12345678'),(30,0,3,8,1,4,1,'Efectivo','Presencial',1,'2023-06-12','09:30:00','45678975','12345678'),(31,0,3,8,1,5,2,'Tarjeta','Virtual',1,'2023-06-13','11:15:00','71231035','12345678'),(32,0,3,8,1,6,3,'En Caja','Presencial',1,'2023-06-14','13:00:00','72216647','12345678'),(33,0,3,8,1,7,1,'Efectivo','Virtual',1,'2023-06-15','15:45:00','22647853','12345678'),(34,0,3,8,1,8,2,'Tarjeta','Presencial',1,'2023-06-16','17:30:00','23456789','12345678'),(35,0,3,8,1,1,3,'En Caja','Virtual',1,'2023-06-17','10:30:00','23766788','12345678'),(36,0,3,8,1,2,1,'Efectivo','Presencial',1,'2023-06-18','12:15:00','45678975','12345678'),(37,0,3,8,1,5,1,'Efecitvo','Presencial',1,'2023-06-01','16:30:00','23456789','12345678');
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

-- Dump completed on 2023-06-01 20:12:19
