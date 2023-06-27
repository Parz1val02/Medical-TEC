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
-- Table structure for table `form_invitacion`
--

DROP TABLE IF EXISTS `form_invitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_invitacion` (
  `idform_invitacion` int NOT NULL AUTO_INCREMENT,
  `nombres` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `apellidos` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_sede` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dni` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechanacimiento` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sexo` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `domicilio` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `correo` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_seguro` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `celular` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pendiente` tinyint DEFAULT '1',
  PRIMARY KEY (`idform_invitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_invitacion`
--

LOCK TABLES `form_invitacion` WRITE;
/*!40000 ALTER TABLE `form_invitacion` DISABLE KEYS */;
INSERT INTO `form_invitacion` VALUES (1,'Karim','Benzema','3','93446783','70','F','la paz 123','aaa@pucp.edu.pe','4','987654321',0),(2,'Nicole','Guillen','1','79585741','22','F','Avenida Bolivar 2150','nicguillen@example.com','3','942108977',1),(5,'Ana','López','1','11141111','99','M','Catolica 123','gzanabriaz28@gmail.com','1','987654321',0),(6,'Kylian','Mbappe','3','72222256','50','M','Catolica 123','mbappe@gmail.com','1','987654321',0),(7,'Leonardo','Abanto','3','23456789','19','M','456 Oak St','leo@example.com','2','912337887',0),(8,'Manuel ','Pallete','3','33333333','05-06-2000','M','Catolica 304','manu.pallete@gmail.com','3','912817963',0),(9,'Hiro','Guerrero','3','45678977','35','M','Catolica 123','notengoequipo@gmail.com','2','992585392',0),(10,'CARLOS EDUARDO','PISCO TEJADA','2','72513294','20','M','Bolivar 123','edupiscote.cept@gmail.com','1','964847121',0),(11,'Paolo','Guerrero','3','45678975','05-06-2000','M','Nacional 123','depredador@gmail.com','1','987654852',0);
/*!40000 ALTER TABLE `form_invitacion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-27  0:58:26
