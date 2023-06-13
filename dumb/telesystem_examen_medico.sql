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
-- Table structure for table `examen_medico`
--

DROP TABLE IF EXISTS `examen_medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examen_medico` (
  `idexamen` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `descripcion` text,
  PRIMARY KEY (`idexamen`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen_medico`
--

LOCK TABLES `examen_medico` WRITE;
/*!40000 ALTER TABLE `examen_medico` DISABLE KEYS */;
INSERT INTO `examen_medico` VALUES (1,'examen fisico','es un examen en el que el médico revisa el cuerpo del paciente para evaluar su estado de salud general'),(2,'Análisis de sangre','se utiliza para detectar y medir una amplia variedad de sustancias en la sangre, incluyendo glucosa, colesterol, triglicéridos, enzimas hepáticas, hormonas y células sanguíneas.'),(3,'Pruebas de orina','se utilizan para detectar infecciones del tracto urinario, enfermedades del riñón y otras afecciones'),(4,'Radiografía','es una imagen del interior del cuerpo que se obtiene mediante rayos X.'),(5,'Ecografía','es una técnica de imagen que utiliza ondas sonoras para producir imágenes de los órganos internos.'),(6,'Electrocardiograma (ECG)','se utiliza para registrar la actividad eléctrica del corazón y detectar problemas cardíacos.'),(7,'Mamografía','es una radiografía de las mamas que se utiliza para detectar el cáncer de mama.'),(8,'Colonoscopia','es un examen que se realiza para detectar cáncer de colon y otras afecciones del tracto intestinal.'),(9,'Prueba de esfuerzo','se utiliza para evaluar la salud del corazón durante el ejercicio'),(10,'Examen de la vista','se utiliza para evaluar la visión y detectar problemas como la miopía, la hipermetropía y el astigmatismo');
/*!40000 ALTER TABLE `examen_medico` ENABLE KEYS */;
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
