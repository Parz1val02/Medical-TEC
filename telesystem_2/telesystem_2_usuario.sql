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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `dni` varchar(8) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `edad` int NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `sexo` varchar(45) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `sedes_idsedes` int DEFAULT NULL,
  `roles_idroles` int NOT NULL,
  `especialidades_id_especialidad` int DEFAULT NULL,
  `seguros_id_seguro` int DEFAULT NULL,
  `estados_idestado` int DEFAULT NULL,
  `modooscuro` tinyint(1) DEFAULT NULL,
  `foto` longblob,
  `modoregistro` varchar(45) DEFAULT NULL,
  `ceduladoctor` varchar(45) DEFAULT NULL,
  `fotonombre` varchar(255) DEFAULT NULL,
  `fotocontenttype` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `historialmedico_idhistorialmedico` int DEFAULT NULL,
  PRIMARY KEY (`dni`),
  KEY `fk_usuario_sedes1_idx` (`sedes_idsedes`),
  KEY `fk_usuario_roles1_idx` (`roles_idroles`),
  KEY `fk_usuario_especialidades1_idx` (`especialidades_id_especialidad`),
  KEY `fk_usuario_seguros1_idx` (`seguros_id_seguro`),
  KEY `fk_usuario_estados1_idx` (`estados_idestado`),
  KEY `fk_usuario_historialmedico1_idx` (`historialmedico_idhistorialmedico`),
  CONSTRAINT `fk_usuario_especialidades1` FOREIGN KEY (`especialidades_id_especialidad`) REFERENCES `especialidades` (`id_especialidad`),
  CONSTRAINT `fk_usuario_estados1` FOREIGN KEY (`estados_idestado`) REFERENCES `estados` (`idestado`),
  CONSTRAINT `fk_usuario_historialmedico1` FOREIGN KEY (`historialmedico_idhistorialmedico`) REFERENCES `historialmedico` (`idhistorialmedico`),
  CONSTRAINT `fk_usuario_roles1` FOREIGN KEY (`roles_idroles`) REFERENCES `roles` (`idroles`),
  CONSTRAINT `fk_usuario_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`),
  CONSTRAINT `fk_usuario_seguros1` FOREIGN KEY (`seguros_id_seguro`) REFERENCES `seguros` (`id_seguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('12345678','$2a$10$nOCoA77g9M3TtnqtIrMBsusAwv/b0j29L1pLHfbIzNBAJluQShpZS','jubilado@example.com','Hiro','Giotoku',30,'123456789','M','123 Main St',1,1,8,NULL,1,NULL,NULL,NULL,'AE32423',NULL,NULL,1,NULL),('14578934','$2a$10$Clu.7WdxY66UT2ueIVouUuDO70kR6auvya2V4Pmy0ZH6atHBoem7W','manuel@example.com','Manuel','Yarleque',40,'123456789','M','321 Main St',1,1,2,NULL,1,NULL,NULL,NULL,'AE32423',NULL,NULL,1,NULL),('17845767','$2a$10$eBZLalZqzzaU6akkmMkNpu33oDaiBsyzY3eUWaXAdARN152MBUIj2','rojasz@example.com','Zaida','Rojas',30,'963485261','M','567 Main St',1,1,17,NULL,1,NULL,NULL,NULL,'AE32423',NULL,NULL,1,NULL),('22647853','$2a$10$f15cLQLhYu98NIvGgWiObew44qWKdFgwSNw0yrDsw0zZU5pwxSrS2','mionks@example.com','Julio','Perez',15,'909574324','M','123 CBA St',3,2,NULL,5,1,NULL,'autoregistro',NULL,NULL,1,5),('23456789','$2a$10$eFNyP5UgmbilY/X4zdo9CeRQ1hzyCrefuUD98r9maNVs/gDrf5m4G','leonardo@example.com','Leonardo','Abanto',70,'912337887','M','456 Oak St',2,2,NULL,2,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,4),('23766788','Alfonso','taylor_enchanted@example.com','Taylor','Swift',25,'987654321','M','890 ABC St',1,2,NULL,1,6,NULL,NULL,'invitado',NULL,NULL,NULL,1,3),('23892722','danaa','dnolasco@pucp.edu.pe','Dana','Nolasco',19,'951899507','F','La Paz 2130',1,3,1,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('24324323','comsoc','hurtado@example.com','Leo','Hurtado',19,'999222111','M','ULima',2,4,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('28573467','Mario','mario@example.com','Mario','Verastegui',30,'907473653','M','678 XYZ St',2,2,NULL,4,5,NULL,NULL,'autoregistro',NULL,NULL,NULL,1,2),('33333333','B/6N0Hyt','manu.pallete@gmail.com','Manuel','Pallete',65,'912817234','M','dfbdgbdgfbdgbd@g',1,2,NULL,NULL,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,1),('33333533','b]K4AUH5','tini_tini_tini@gmail.com','Martina','Stoessel',100,'987455321','M','HTRHRTHRTHRTH',2,2,NULL,NULL,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,NULL),('34185296','$2b$12$yTnSybloFLAymL4H5hsF7Odxex1htYW21X/Uo8C2SfyBMaLhrEg/.','lucho@example.com','Lucho','Ramos',22,'775555566','M','234 Main Ave',3,3,1,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),('45678975','WKE&!ZN]','notengoequipo@gmail.com','Hiro','Guerrero',35,'992585392','M','Racing',1,2,NULL,NULL,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,NULL),('48764321','jose','jhon_rojas@example.com','Jhon','Rojas',28,'987654321','M','456 Oak St',2,4,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),('66666666','*02p<&^P','random@example.com','auu','ya',120,'12345678','M','calle pizza',1,2,NULL,NULL,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,NULL),('71231001','clash321','a20200870@pucp.edu.pe','Jesús','Gonzáles',25,'926880634','M','Cta Sofía',1,3,12,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('71231035','jesus','jesus@example.com','Augusto','Gonsales',25,'945202825','M','789 Main St',2,2,NULL,5,3,NULL,NULL,'autoregistro',NULL,NULL,NULL,1,NULL),('71448628','$2a$10$jA92B9s7KwoLZRMUwXtn5OjcjdFujuUyQJIufLT8Jn5RDCknurYgS','roberto@example.com','Gil ','Zanabria',20,'910254488','M','Alfredo Benavides 544',1,4,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),('72216647','luistelco','luis.ramost@gmail.com','Felipe ','Torres',23,'925771711','M','Jiron Emilio de los Rios',2,2,13,1,1,NULL,NULL,'autoregistro',NULL,NULL,NULL,1,NULL),('72411492','fallguys','algher@gmail.com','Alonso','Céspedes',23,'999888773','M','Pueblo Libre',3,3,3,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('72411493','valorant','alejandro.guti@pucp.edu.pe','Alejandro','Gutierrez',30,'992585352','M','Champagnat',2,4,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('73410317','lau29','laumar@gmail.com','Laura','Martínez',30,'975956272','F','Avenida Tomas marsano 203',2,1,13,NULL,1,NULL,NULL,NULL,'AF32127',NULL,NULL,1,NULL),('73805736','luchofer40','luis.fernan@gmail.com','Luis ',' Fernández',40,'940158637','M','Unidad Vecinal Mirones',3,1,15,NULL,1,NULL,NULL,NULL,'LC32447',NULL,NULL,1,NULL),('75030121','agomez2','anag@example.com','Ana ','Gómez',29,'963042710','F','Avenida Circunvalacion',2,1,7,NULL,1,NULL,NULL,NULL,'AE32345',NULL,NULL,1,NULL);
INSERT INTO `usuario` VALUES ('75210655','$2a$10$34cTg9esf5Td8RMI9XgTBOQiuoGRqUrc/9iDp3eh.Xf8VjCZguufe','a20201696@pucp.edu.pe','Leonardo','Abanto',40,'916182017','M','789 Maple Ave',1,5,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);
INSERT INTO `usuario` VALUES ('75390618','astronomia2024','christiancc@pucp.edu.pe','Christian','Carrillo',19,'938526886','M','Jirón Libertad',3,4,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('75749561','dana','dana@example.com','Dana','Nolasco',19,'951899507','F','789 Main St',2,2,NULL,6,3,NULL,NULL,'autoregistro',NULL,NULL,NULL,1,NULL),('76549810','cpisco','clash','Carlosass','Piscofff',20,'965342078','M','Senda Dorada 150',3,3,15,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),('77312461','pag912','patygon@example.com','Patricia','González',31,'974289516','M','Avenida Abancay 4530',3,1,1,NULL,1,NULL,NULL,NULL,'LC35631',NULL,NULL,1,NULL),('77657567','H6@?eFn3','notengoequipoXDXD@gmail.com','Alonso','Roman',76,'983654326','M','riodelAHSDHFCAJDC',1,2,NULL,NULL,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,NULL),('77817288','ricsil35','rsilva@example.com','Ricardo ','Silva',35,'930478215','M','Avenida Alcazar 120',3,1,9,NULL,1,NULL,NULL,NULL,'LC34587',NULL,NULL,1,NULL),('79356445','arami12','andrea.ramirez@gmail.com','Andrea','Ramírez',26,'911267426','F','Avenida Surco 641',2,1,9,NULL,1,NULL,NULL,NULL,'AF32230',NULL,NULL,1,NULL),('79505741','nicole','nicguillen@example.com','Nicole','Guillen',22,'942108977','F','Avenida Bolivar 2150',1,2,NULL,3,1,NULL,NULL,'invitado',NULL,NULL,NULL,1,NULL),('95175346','sevasevaseva','pedrinho@elultimo10.com','Pedri','Gonzales',45,'963852747','M','La Bombonera',1,4,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
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

-- Dump completed on 2023-05-30 16:21:10
