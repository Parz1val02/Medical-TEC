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
-- Table structure for table `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificaciones` (
  `idnotificaciones` int NOT NULL AUTO_INCREMENT,
  `contenido` varchar(200) DEFAULT NULL,
  `usuario_dni` varchar(8) NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  PRIMARY KEY (`idnotificaciones`),
  KEY `fk_notificaciones_usuario1_idx` (`usuario_dni`),
  CONSTRAINT `fk_notificaciones_usuario1` FOREIGN KEY (`usuario_dni`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notificaciones`
--

LOCK TABLES `notificaciones` WRITE;
/*!40000 ALTER TABLE `notificaciones` DISABLE KEYS */;
INSERT INTO `notificaciones` VALUES (1,'Recordatorio de cita: Recuerda que tienes una cita con el Dr. García el martes 27 de abril a las 2 p.m. en nuestra clínica. Por favor, confirma tu asistencia ','23766788','2023-05-21','12:00:00'),(2,'Resultados de exámenes: Los resultados de tus análisis de sangre están disponibles. Por favor, llama a nuestra clínica para programar una cita para revisarlos con el Dr. Sánchez.  ','23456789','2023-05-20','12:00:00'),(3,'Cambio de horario: Queremos informarte que debido a una emergencia médica, el Dr. Rodríguez no estará disponible hoy. Tu cita ha sido reprogramada para el próximo martes a la misma hora.','22647853','2023-05-19','12:00:00'),(4,'Notificación de facturación: Te informamos que tienes un saldo pendiente en nuestra clínica. Por favor, llama a nuestro departamento de facturación para solucionar este asunto.','28573467','2023-05-18','12:00:00'),(5,'Se ha agendado una nueva cita para usted. Por favor, revise los detalles','12345678','2023-05-22','20:00:00'),(6,'Le recordamos que su cita está programada para dentro de 10 minutos. Por favor, asegúrese de estar preparado y disponible para atender a su paciente puntualmente.','12345678','2023-05-23','20:00:00'),(7,'Se ha generado una facturación (boleta) correspondiente a una cita previamente agendada.','12345678','2023-05-24','20:00:00');
/*!40000 ALTER TABLE `notificaciones` ENABLE KEYS */;
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

-- Dump completed on 2023-06-18 17:42:12
