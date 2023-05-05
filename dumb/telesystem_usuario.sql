-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: telesystem
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `dni` varchar(8) NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `edad` int NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `sedes_idsedes` int DEFAULT NULL,
  `roles_idroles` int NOT NULL,
  `historialmedico_idhistorialmedico` int DEFAULT NULL,
  `especialidades_id_especialidad` int DEFAULT NULL,
  `seguros_id_seguro` int DEFAULT NULL,
  `estados_idestado` int DEFAULT NULL,
  `modooscuro` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `fk_usuario_sedes1_idx` (`sedes_idsedes`),
  KEY `fk_usuario_roles1_idx` (`roles_idroles`),
  KEY `fk_usuario_historialmedico1_idx` (`historialmedico_idhistorialmedico`),
  KEY `fk_usuario_especialidades1_idx` (`especialidades_id_especialidad`),
  KEY `fk_usuario_seguros1_idx` (`seguros_id_seguro`),
  KEY `fk_usuario_estados1_idx` (`estados_idestado`),
  CONSTRAINT `fk_usuario_especialidades1` FOREIGN KEY (`especialidades_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`),
  CONSTRAINT `fk_usuario_estados1` FOREIGN KEY (`estados_idestado`) REFERENCES `estados` (`idestado`),
  CONSTRAINT `fk_usuario_historialmedico1` FOREIGN KEY (`historialmedico_idhistorialmedico`) REFERENCES `historialmedico` (`idhistorialmedico`),
  CONSTRAINT `fk_usuario_roles1` FOREIGN KEY (`roles_idroles`) REFERENCES `roles` (`idroles`),
  CONSTRAINT `fk_usuario_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`),
  CONSTRAINT `fk_usuario_seguros1` FOREIGN KEY (`seguros_id_seguro`) REFERENCES `seguros` (`id_seguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('12345678','hiroshi','hiroshi@example.com','hiroshi','Doe',30,'123456789','M','123 Main St',NULL,1,NULL,NULL,NULL,NULL,NULL),('23456789','leonardo','leonardo@example.com','leonardo','abanto',25,'987654321','M','456 Oak St',NULL,2,1,NULL,NULL,NULL,NULL),('34567890','mario','mario@example.com','mario','Smith',40,'555555555','M','789 Maple Ave',NULL,5,NULL,NULL,NULL,NULL,NULL),('74185296','lucho','lucho@example.com','lucho','Ramos',22,'775555566','M','234 Main Ave',NULL,3,NULL,1,NULL,NULL,NULL),('98764321','jose','jose@example.com','jose','perez',28,'987654321','M','456 Oak St',NULL,4,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-21 22:14:22
