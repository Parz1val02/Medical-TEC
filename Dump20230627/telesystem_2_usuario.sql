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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `dni` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contrasena` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `apellido` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fechanacimiento` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefono` varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sexo` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `direccion` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sedes_idsedes` int DEFAULT NULL,
  `roles_idroles` int NOT NULL,
  `especialidades_id_especialidad` int DEFAULT NULL,
  `seguros_id_seguro` int DEFAULT NULL,
  `estados_idestado` int DEFAULT NULL,
  `modooscuro` tinyint(1) DEFAULT NULL,
  `modoregistro` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ceduladoctor` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
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
  CONSTRAINT `fk_usuario_roles1` FOREIGN KEY (`roles_idroles`) REFERENCES `roles` (`idroles`),
  CONSTRAINT `fk_usuario_sedes1` FOREIGN KEY (`sedes_idsedes`) REFERENCES `sedes` (`idsedes`),
  CONSTRAINT `fk_usuario_seguros1` FOREIGN KEY (`seguros_id_seguro`) REFERENCES `seguros` (`id_seguro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('11141111','$2a$10$a0kmzvgrMXCWXyC8OdhFQeGnMoJ3gOJV0dBuktuAGaOergCSSxgju','gzanabriaz28@gmail.com','Ana Mariana','López Buendia','30-07-1990','987654321','F','Catolica 123',1,2,NULL,2,1,0,'invitado',NULL,1,NULL),('12345678','$2a$10$nOCoA77g9M3TtnqtIrMBsusAwv/b0j29L1pLHfbIzNBAJluQShpZS','hiroshi@example.com','Ivan Hiroshi','Giotoku','23-08-1994','123456789','M','123 Main St',1,1,8,NULL,1,0,NULL,'AE32423',1,NULL),('14578934','$2a$10$Clu.7WdxY66UT2ueIVouUuDO70kR6auvya2V4Pmy0ZH6atHBoem7W','manuel@example.com','Manuel','Yarlequé','11-09-1986','123456789','M','321 Main St',1,1,2,NULL,1,0,NULL,'AE12345',1,NULL),('17845767','$2a$10$eBZLalZqzzaU6akkmMkNpu33oDaiBsyzY3eUWaXAdARN152MBUIj2','rojasz@example.com','Zaida','Rojas','14-02-1970','963485261','F','567 Main St',1,1,17,NULL,1,0,NULL,'AE57364',1,NULL),('22647853','$2a$10$f15cLQLhYu98NIvGgWiObew44qWKdFgwSNw0yrDsw0zZU5pwxSrS2','mionks@example.com','Julio','Perez','05-06-2000','909574324','M','123 CBA St',3,2,NULL,6,1,0,'autoregistro',NULL,1,5),('23456789','$2a$10$f15cLQLhYu98NIvGgWiObew44qWKdFgwSNw0yrDsw0zZU5pwxSrS2','leonardo@example.com','Leonardo','Abanto','05-06-2000','912337887','M','456 Oak St',1,2,NULL,3,1,0,'invitado',NULL,1,4),('23766788','Alfonso','taylor_enchanted@example.com','Taylor','Swift','05-06-2000','987654321','Femenino','890 ABC St',1,2,NULL,1,6,0,'invitado',NULL,1,3),('23892722','danaa','dana.nolasco@pucp.edu.pe','Dana Paola','Nolasco Vallejos','05-06-2000','951899507','F','La Paz 2130',1,2,NULL,NULL,1,0,NULL,NULL,1,NULL),('24324323','comsoc','leo.hurtado@example.com','Leonardo Alfonso','Abanto Hurtado','05-06-2000','999222111','M','ULima',2,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('28573467','Mario','mario.verastegui@example.com','Mario','Verastegui','05-06-2000','907473653','M','678 XYZ St',2,2,NULL,4,5,0,'autoregistro',NULL,1,2),('33333333','B/6N0Hyt','manuel.pallete@gmail.com','Manuel','Pallete','05-06-2000','912817234','M','dfbdgbdgfbdgbd@g',3,2,NULL,3,1,0,'invitado',NULL,1,NULL),('34185296','$2a$10$p4BhbiPhxp2aHR1gqUQRCeQQ0iYVYW9hqjY.Z9VlHyuj8CHFCeE2q','lucho@example.com','Lucho','Ramos','05-06-2000','775555566','M','234 Main Ave',3,3,NULL,NULL,1,0,NULL,NULL,1,NULL),('43638308','Hanselgod37@','aaaa@pucp.edu.pe','HANSEL JOUSSEF','MARTINEZ ODIAGA','05-06-2000','964852137','M','456 Oak aaa',1,2,NULL,3,1,0,'autoregistro',NULL,1,NULL),('45678975','WKE&!ZN]','paolo.guerrrero@example.com','Paolo','Guerrero','05-06-2000','992585392','Masculino','Racing',3,2,NULL,2,1,0,'invitado',NULL,1,5),('48764321','jose','jhon.rojas@example.com','Jhon Alexander','Rojas Zanabria','05-06-2000','987654321','M','456 Oak St',2,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('66666666','*02p<&^P','australia.zavaleta@example.com','Austria','Zavaleta','05-06-2000','12345678','M','calle pizza',1,2,NULL,4,1,0,'invitado',NULL,1,4),('71231001','clash321','jesus.gonzales@example.com','Jesus Augusto','Gonzalez Huarancca','05-06-2000','926880634','M','Cta SofÃ­a',1,3,NULL,NULL,1,0,NULL,NULL,1,NULL),('71231035','jesus','augusto.gonzalez@example.com','Christian Edgardo','Carrillo Hurtado','05-06-2000','945202825','M','789 Main St',3,2,NULL,5,3,0,'autoregistro',NULL,1,3),('71448628','$2a$10$dwCzXPs37eAgVz8V4ZPN2O5Hs1ZwVd5GhpdJ7bOqM2LLYO/cGKKM2','gil.zanabria@example.com','Gil Roberto','Zanabria Zuñiga','05-06-2000','910254488','M','Alfredo Benavides 544',1,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('72216647','luistelco','felipe.torres@example.com','Felipe ','Torres','05-06-2000','925771711','Masculino','Jiron Emilio de los Rios',2,2,NULL,1,1,0,'autoregistro',NULL,1,2),('72222256','$2a$10$XJJZnE1vilps9SS1NzFQ2e7xDZbh7ZwQra/7z/SwyW1oHAM3iF16e','kylian.mbappe@examplel.com','Kylian','Mbappe','05-06-1900','987654321','M','Catolica 123',1,2,NULL,5,1,0,'invitado',NULL,1,NULL),('72411492','fallguys','alonso.cespedes@example.com','Alonso','Cespedes','05-06-2000','999888773','M','Pueblo Libre',3,3,NULL,NULL,1,0,NULL,NULL,1,NULL),('72411493','valorant','alejandro.guti@example.com','Alejandro','Gutierrez','05-06-2000','992585352','M','Champagnat',2,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('73222256','$2a$10$iXhsqd7YsQ1BxM1Yhk/3BeX1v0Sc3T5WZs6VWW.AJGWcKHsChBuni','hiroshi.giotoku@pucp.edu.pe','IVAN HIROSHI','GIOTOKU REJAS','05-06-1990','965079527','M','adfaajf',1,2,NULL,1,1,0,'autoregistro',NULL,1,NULL),('73410317','lau29','laumar@gmail.com','Laura Pamela','Marano Martinez','05-06-2000','975956272','F','Avenida Tomas marsano 203',1,1,13,NULL,1,0,NULL,'AF32127',1,NULL),('73805724','doctorcitaaa','jimena.t@gmail.com','Jimena','Torres','20-05-2002','940158637','F','Unidad Vecinal Mirones',1,1,18,NULL,1,0,NULL,'LC32447',1,NULL),('73805736','$2a$10$34cTg9esf5Td8RMI9XgTBOQiuoGRqUrc/9iDp3eh.Xf8VjCZguufe','a20201696@pucp.edu.pe','Luis ',' Fernandez','05-06-2000','940158637','M','Unidad Vecinal Mirones',1,5,NULL,NULL,1,0,NULL,NULL,1,NULL),('75030121','agomez2','anag@example.com','Ana ','Zavaleta','05-06-1990','963042710','F','Avenida Circunvalacion',1,1,7,NULL,1,0,NULL,'AE83495',1,NULL),('75390618','WI12345@','as@hjgh.com','Pedri','Quispe','05-06-2000','938502560','M','Avenida Universitaria 123',1,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('77234233','$2a$10$CNu.r/fXixVNok3Wrf2vFOASqXQL4Aej1XHmykxbU2HhIpbguQBqe','rodrigo@gmail.com','Rodrigo','Barrios','05-06-2000','987643212','M','pucp',1,2,NULL,5,1,0,'invitado',NULL,1,NULL),('77312461','pag912','patygon@example.com','Patricia','González','05-06-2000','974289516','F','Avenida Abancay 4530',1,1,1,NULL,1,0,NULL,'LC35631',1,NULL),('77817288','ricsil35','rsilva@example.com','Ricardo ','Silva','05-06-2000','930478215','M','Avenida Alcazar 120',1,1,9,NULL,1,0,NULL,'LC34587',1,NULL),('88888882','CLASH123@','adawa@example.com','Pruéba','dós','05-06-2000','999999900','M','DADSADASD',3,4,NULL,NULL,1,0,NULL,NULL,1,NULL),('93446783','$2a$10$vnqU0nMph44ftCieNYe2v.7swCvhW7DZX0NlnudF9gnAP5dhj5xka','aaa@pucp.edu.pe','Karim','Benzema','05-06-2000','987654321','F','la paz 123',1,2,NULL,3,1,0,'invitado',NULL,1,NULL);
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

-- Dump completed on 2023-06-27  0:57:26
