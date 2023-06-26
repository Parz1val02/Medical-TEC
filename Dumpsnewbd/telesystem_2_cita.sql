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
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `idcita` int NOT NULL AUTO_INCREMENT,
  `citacancelada` tinyint(1) NOT NULL,
  `sedes_idsedes` int NOT NULL,
  `especialidades_id_especialidad` int DEFAULT NULL,
  `estadoscita_idestados` int NOT NULL,
  `receta_idreceta` int DEFAULT NULL,
  `formapago` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modalidad` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tipocita_idtipocita` int NOT NULL,
  `fecha` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hora` time NOT NULL,
  `paciente_dni` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `doctor_dni1` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `pagada` tinyint(1) NOT NULL,
  `examen_medico_idexamen` int DEFAULT NULL,
  PRIMARY KEY (`idcita`),
  KEY `fk_cita_sedes1_idx` (`sedes_idsedes`),
  KEY `fk_cita_especialidades1_idx` (`especialidades_id_especialidad`),
  KEY `fk_cita_estadoscita1_idx` (`estadoscita_idestados`),
  KEY `fk_cita_receta1_idx` (`receta_idreceta`),
  KEY `fk_cita_tipocita1_idx` (`tipocita_idtipocita`),
  KEY `fk_cita_usuario1_idx` (`paciente_dni`),
  KEY `fk_cita_usuario2_idx` (`doctor_dni1`),
  KEY `fk_cita_examen_medico1_idx` (`examen_medico_idexamen`),
  CONSTRAINT `fk_cita_especialidades1` FOREIGN KEY (`especialidades_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`),
  CONSTRAINT `fk_cita_estadoscita1` FOREIGN KEY (`estadoscita_idestados`) REFERENCES `estadoscita` (`idestados`),
  CONSTRAINT `fk_cita_examen_medico1` FOREIGN KEY (`examen_medico_idexamen`) REFERENCES `examen_medico` (`idexamen`),
  CONSTRAINT `fk_cita_receta1` FOREIGN KEY (`receta_idreceta`) REFERENCES `receta` (`idreceta`),
  CONSTRAINT `fk_cita_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`),
  CONSTRAINT `fk_cita_tipocita1` FOREIGN KEY (`tipocita_idtipocita`) REFERENCES `tipocita` (`idtipocita`),
  CONSTRAINT `fk_cita_usuario1` FOREIGN KEY (`paciente_dni`) REFERENCES `usuario` (`dni`),
  CONSTRAINT `fk_cita_usuario2` FOREIGN KEY (`doctor_dni1`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,0,1,1,1,NULL,'Tarjeta','Virtual',1,'04-07-2023','20:00:00','23456789','12345678',0,NULL),(2,0,2,17,3,2,'En caja','Presencial',1,'17-05-2023','16:00:00','23456789','17845767',1,NULL),(3,0,2,13,3,5,'En Caja','Presencial',1,'03-05-2-23','20:30:00','72216647','73410317',1,NULL),(4,0,3,1,3,4,'En Caja','Presencial',1,'20-05-2023','10:00:00','22647853','77312461',1,NULL),(5,0,3,9,3,6,'En caja','Presencial',1,'22-05-2023','14:00:00','28573467','77817288',1,NULL),(6,0,3,9,3,7,'En Caja','Presencial',1,'22-05-2023','14:00:01','28573467','77817288',1,NULL),(7,0,1,6,3,2,'En caja','Presencial',1,'31-05-2023','06:00:00','28573467','77312461',1,NULL),(8,0,1,8,3,4,'En caja','Presencial',1,'25-05-2023','20:00:00','28573467','77312461',1,NULL),(10,0,1,1,3,2,'En caja','Presencial',1,'01-06-2023','04:10:00','22647853','73805736',1,NULL),(11,0,3,8,3,2,'Tarjeta','Virtual',1,'12-04-2023','10:00:00','23892722','12345678',1,NULL),(12,0,3,8,3,1,'Tarjeta','Virtual',1,'15-03-2023','11:00:56','22647853','12345678',1,NULL),(13,0,3,8,3,5,'Tarjeta','Virtual',1,'30-05-2023','08:31:02','23456789','12345678',1,NULL),(14,0,3,8,3,4,'Tarjeta','Virtual',1,'13-04-2023','12:04:00','23766788','12345678',1,NULL),(15,0,3,8,3,6,'En Caja','Presencial',1,'12-04-2023','17:00:00','33333333','12345678',1,NULL),(16,0,3,8,3,1,'Tarjeta','Virtual',1,'30-05-2023','11:45:00','22647853','12345678',1,NULL),(17,0,3,8,3,1,'Tarjeta','Virtual',1,'30-05-2023','15:30:00','23456789','12345678',1,NULL),(18,0,3,8,3,1,'Tarjeta','Virtual',1,'31-05-2023','10:45:00','23766788','12345678',1,NULL),(19,0,3,8,3,1,'Tarjeta','Virtual',1,'01-02-2023','14:15:00','23766788','12345678',1,NULL),(20,0,3,8,3,1,'Tarjeta','Virtual',1,'02-02-2023','11:30:00','45678975','12345678',1,NULL),(21,0,3,8,3,1,'En Caja','Presencial',1,'03-02-2023','09:00:00','45678975','12345678',1,NULL),(22,0,3,8,3,1,'En Caja','Presencial',1,'04-03-2023','16:45:00','71231035','12345678',1,NULL),(23,0,3,8,3,1,'En Caja','Presencial',1,'05-03-2023','12:00:00','72216647','12345678',1,NULL),(24,0,3,8,3,1,'En Caja','Presencial',1,'06-04-2023','14:30:00','22647853','12345678',1,NULL),(25,0,1,8,3,1,'En Caja','Presencial',1,'07-01-2023','17:15:00','23456789','12345678',1,NULL),(26,0,3,8,3,1,'En Caja','Presencial',1,'08-04-2023','13:45:00','23766788','12345678',1,NULL),(27,0,1,8,1,NULL,'Tarjeta','Virtual',1,'29-06-2023','10:00:00','22647853','12345678',0,NULL),(28,0,1,8,1,NULL,'Tarjeta','Presencial',1,'26-06-2023','14:30:00','23456789','12345678',0,NULL),(29,0,1,8,1,3,'Tarjeta','Virtual',1,'06-07-2023','19:30:00','23766788','12345678',1,NULL),(30,0,1,8,1,4,'En Caja','Presencial',1,'06-07-2023','16:00:00','45678975','12345678',1,NULL),(31,0,1,8,1,NULL,'Tarjeta','Virtual',1,'13-06-2023','19:34:00','71231035','12345678',1,NULL),(32,0,1,8,1,NULL,'En Caja','Presencial',1,'29-06-2023','13:00:00','72216647','12345678',1,NULL),(33,0,1,8,1,NULL,'Tarjeta','Virtual',1,'25-06-2023','13:30:00','22647853','12345678',0,NULL),(34,0,1,8,1,NULL,'En Caja','Presencial',1,'01-07-2023','17:30:00','23456789','12345678',0,NULL),(35,0,1,8,1,NULL,'Tarjeta','Virtual',1,'02-07-2023','10:30:00','23766788','12345678',1,NULL),(36,0,1,8,1,NULL,'Tarjeta','Presencial',1,'28-06-2023','12:15:00','45678975','12345678',1,NULL),(37,0,1,8,1,5,'En Caja','Presencial',1,'18-06-2023','22:00:00','23456789','12345678',1,NULL),(38,0,1,9,1,NULL,'En caja','Presencial',1,'30-06-2023','14:00:00','23456789','77817288',0,NULL),(39,0,1,9,1,NULL,'Tarjeta','Virtual',1,'16-06-2023','14:00:00','23456789','77817288',0,NULL),(40,0,1,1,1,NULL,'En caja','Presencial',1,'19-06-2023','18:00:00','23456789','77312461',0,NULL),(41,0,1,1,1,NULL,'En caja','Presencial',1,'29-06-2023','20:00:00','23456789','77312461',0,NULL),(42,0,1,18,1,NULL,'En caja','Presencial',1,'28-06-2023','04:30:00','23456789','73805724',0,NULL),(43,0,1,8,3,2,'En Caja','Presencial',1,'01-06-2023','16:00:00','71231035','12345678',1,NULL),(44,0,1,15,3,1,'Tarjeta','Virtual',1,'05-05-2023','14:00:00','71231035','77312461',1,NULL),(45,0,1,3,3,3,'En Caja','Presencial',1,'04-04-2023','10:00:00','71231035','75030121',1,NULL),(46,0,1,1,1,NULL,'En caja','Presencial',1,'02-07-2023','21:00:00','23456789','73410317',0,NULL),(47,0,1,9,1,NULL,'En Caja','Presencial',1,'23-06-2023','12:20:00','22647853','77817288',1,NULL),(48,0,1,9,1,NULL,'Tarjeta','Virtual',1,'23-06-2023','12:20:00','22647853','77817288',0,NULL);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-26  0:04:09
