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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examen_medico`
--

LOCK TABLES `examen_medico` WRITE;
/*!40000 ALTER TABLE `examen_medico` DISABLE KEYS */;
INSERT INTO `examen_medico` VALUES (1,'Examen físico','Un examen físico es una evaluación completa de tu salud general. Durante este examen, nuestro médico llevará a cabo una revisión de tus sistemas corporales, como el cardiovascular, respiratorio, musculoesquelético y neurológico, entre otros. Se realizarán mediciones como la presión arterial, el pulso y la auscultación del corazón y los pulmones.'),(2,'Análisis de sangre','Los análisis de sangre son pruebas que permiten evaluar diversos aspectos de tu salud mediante el análisis de una muestra de sangre. Estos exámenes pueden incluir pruebas para medir los niveles de glucosa, lípidos, hormonas, vitaminas y minerales, así como para detectar infecciones o enfermedades.'),(3,'Pruebas de orina','Las pruebas de orina son exámenes que analizan una muestra de tu orina para detectar posibles problemas de salud. Estas pruebas pueden revelar la presencia de infecciones del tracto urinario, enfermedades renales, diabetes, entre otros.'),(4,'Radiografía','La radiografía es una técnica de diagnóstico por imagen que utiliza rayos X para obtener imágenes de las estructuras internas del cuerpo. Este examen es útil para detectar fracturas óseas, enfermedades pulmonares, obstrucciones intestinales y otras condiciones médicas.'),(5,'Ecografía','La ecografía, también conocida como ultrasonido, utiliza ondas sonoras de alta frecuencia para crear imágenes en tiempo real de los órganos y tejidos internos del cuerpo. Este examen es seguro, no invasivo y se utiliza para evaluar el estado de órganos como el corazón, el hígado, los riñones, el útero y el ovario, entre otros.'),(6,'Electrocardiograma (ECG)','Un electrocardiograma (ECG) es un examen que registra la actividad eléctrica del corazón. Se colocan electrodos en el pecho, las extremidades y el abdomen para registrar la señal eléctrica y evaluar el ritmo cardíaco y la función cardíaca.'),(7,'Mamografía',' La mamografía es una prueba de diagnóstico por imagen que se utiliza para detectar posibles anomalías en los senos. Este examen es especialmente útil en la detección temprana del cáncer de mama, ya que puede mostrar la presencia de masas o calcificaciones en los tejidos mamarios.'),(8,'Colonoscopia','La colonoscopia es un procedimiento que permite examinar el interior del colon y el recto. Se utiliza un tubo flexible con una cámara en el extremo para detectar pólipos, inflamación, úlceras o signos de cáncer colorrectal.'),(9,'Prueba de esfuerzo','Una prueba de esfuerzo, también conocida como prueba de ejercicio, evalúa cómo funciona el corazón durante el ejercicio físico. Durante este examen, se registra el ritmo cardíaco, la presión arterial y los síntomas mientras te ejercitas en una cinta de correr o en una bicicleta estacionaria.'),(10,'Examen de la vista','El examen de la vista es una evaluación de la salud visual y la capacidad de enfoque. Durante este examen, el oftalmólogo realizará pruebas para medir la agudeza visual, la prescripción de lentes, la detección de enfermedades oculares y evaluará la coordinación ocular.'),(11,'Ninguno',NULL);
/*!40000 ALTER TABLE `examen_medico` ENABLE KEYS */;
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

-- Dump completed on 2023-06-18 17:41:06
