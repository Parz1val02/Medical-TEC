-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema telesystem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema telesystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `telesystem` DEFAULT CHARACTER SET utf8 ;
USE `telesystem` ;

-- -----------------------------------------------------
-- Table `telesystem`.`especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`especialidades` (
  `id_especialidad` INT NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` VARCHAR(45) NULL,
  PRIMARY KEY (`id_especialidad`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`seguros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`seguros` (
  `id_seguro` INT NOT NULL AUTO_INCREMENT,
  `nombre_seguro` VARCHAR(45) NOT NULL,
  `porc_seguro` VARCHAR(45) NOT NULL,
  `porc_doctor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_seguro`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`examen_medico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`examen_medico` (
  `idexamen` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `descripcion` TEXT(120) NULL,
  PRIMARY KEY (`idexamen`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`cuestionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`cuestionario` (
  `idcuestionario` INT NOT NULL AUTO_INCREMENT,
  `nombrecuestionario` VARCHAR(45) NOT NULL,
  `activo` TINYINT NOT NULL,
  PRIMARY KEY (`idcuestionario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`historialmedico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`historialmedico` (
  `idhistorialmedico` INT NOT NULL AUTO_INCREMENT,
  `tratamiento` VARCHAR(500) NOT NULL,
  `validahistorial` TINYINT(1) NOT NULL,
  `seguros_id_seguro` INT NOT NULL,
  `cuestionario_idcuestionario` INT NOT NULL,
  PRIMARY KEY (`idhistorialmedico`),
  INDEX `fk_historialmedico_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  INDEX `fk_historialmedico_cuestionario1_idx` (`cuestionario_idcuestionario` ASC) VISIBLE,
  CONSTRAINT `fk_historialmedico_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem`.`seguros` (`id_seguro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_historialmedico_cuestionario1`
    FOREIGN KEY (`cuestionario_idcuestionario`)
    REFERENCES `telesystem`.`cuestionario` (`idcuestionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`sedes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`sedes` (
  `idsedes` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `latitud` DOUBLE NOT NULL,
  `longitud` DOUBLE NOT NULL,
  `torre` VARCHAR(45) NOT NULL,
  `piso` INT NOT NULL,
  `zona_horaria` VARCHAR(45) NOT NULL,
  `nombre_logo` VARCHAR(45) NOT NULL,
  `logo` LONGBLOB NOT NULL,
  PRIMARY KEY (`idsedes`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`estadoscita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`estadoscita` (
  `idestados` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestados`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`receta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`receta` (
  `idreceta` INT NOT NULL AUTO_INCREMENT,
  `observaciones` VARCHAR(100) NULL,
  PRIMARY KEY (`idreceta`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`tarjeta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`tarjeta` (
  `idtarjetas` INT NOT NULL AUTO_INCREMENT,
  `numero` BIGINT(8) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtarjetas`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`tipocita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`tipocita` (
  `idtipocita` INT NOT NULL AUTO_INCREMENT,
  `tipo_cita` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipocita`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`roles` (
  `idroles` INT NOT NULL AUTO_INCREMENT,
  `nombre_rol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idroles`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`estados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`estados` (
  `idestado` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`usuario` (
  `dni` VARCHAR(8) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `edad` INT NOT NULL,
  `telefono` VARCHAR(9) NOT NULL,
  `sexo` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `sedes_idsedes` INT NULL,
  `roles_idroles` INT NOT NULL,
  `historialmedico_idhistorialmedico` INT NULL,
  `especialidades_id_especialidad` INT NULL,
  `seguros_id_seguro` INT NULL,
  `estados_idestado` INT NULL,
  `modooscuro` TINYINT(1) NULL,
  `foto` LONGBLOB NULL,
  `modoregistro` VARCHAR(45) NULL,
  `ceduladoctor` VARCHAR(45) NULL,
  PRIMARY KEY (`dni`),
  INDEX `fk_usuario_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`roles_idroles` ASC) VISIBLE,
  INDEX `fk_usuario_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  INDEX `fk_usuario_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_usuario_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  INDEX `fk_usuario_estados1_idx` (`estados_idestado` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`roles_idroles`)
    REFERENCES `telesystem`.`roles` (`idroles`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem`.`historialmedico` (`idhistorialmedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem`.`especialidades` (`id_especialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem`.`seguros` (`id_seguro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_estados1`
    FOREIGN KEY (`estados_idestado`)
    REFERENCES `telesystem`.`estados` (`idestado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`cita` (
  `idcita` INT NOT NULL AUTO_INCREMENT,
  `citacancelada` TINYINT(1) NULL,
  `sedes_idsedes` INT NOT NULL,
  `especialidades_id_especialidad` INT NOT NULL,
  `estadoscita_idestados` INT NULL,
  `receta_idreceta` INT NULL,
  `tarjeta_idtarjetas` INT NULL,
  `formapago` VARCHAR(45) NOT NULL,
  `modalidad` VARCHAR(45) NOT NULL,
  `tipocita_idtipocita` INT NOT NULL,
  `fecha` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `paciente_dni` VARCHAR(8) NOT NULL,
  `doctor_dni1` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idcita`),
  INDEX `fk_cita_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  INDEX `fk_cita_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_cita_estadoscita1_idx` (`estadoscita_idestados` ASC) VISIBLE,
  INDEX `fk_cita_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  INDEX `fk_cita_tarjeta1_idx` (`tarjeta_idtarjetas` ASC) VISIBLE,
  INDEX `fk_cita_tipocita1_idx` (`tipocita_idtipocita` ASC) VISIBLE,
  INDEX `fk_cita_usuario1_idx` (`paciente_dni` ASC) VISIBLE,
  INDEX `fk_cita_usuario2_idx` (`doctor_dni1` ASC) VISIBLE,
  CONSTRAINT `fk_cita_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem`.`especialidades` (`id_especialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_estadoscita1`
    FOREIGN KEY (`estadoscita_idestados`)
    REFERENCES `telesystem`.`estadoscita` (`idestados`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem`.`receta` (`idreceta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_tarjeta1`
    FOREIGN KEY (`tarjeta_idtarjetas`)
    REFERENCES `telesystem`.`tarjeta` (`idtarjetas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_tipocita1`
    FOREIGN KEY (`tipocita_idtipocita`)
    REFERENCES `telesystem`.`tipocita` (`idtipocita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_usuario1`
    FOREIGN KEY (`paciente_dni`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cita_usuario2`
    FOREIGN KEY (`doctor_dni1`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`informe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`informe` (
  `idinforme` INT NOT NULL AUTO_INCREMENT,
  `diagnostico` VARCHAR(200) NOT NULL,
  `firma` LONGBLOB NOT NULL,
  `bitacora` VARCHAR(500) NULL,
  `historialmedico_idhistorialmedico` INT NOT NULL,
  `cita_idcita` INT NOT NULL,
  PRIMARY KEY (`idinforme`),
  INDEX `fk_reporte_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  INDEX `fk_reporte_cita1_idx` (`cita_idcita` ASC) VISIBLE,
  CONSTRAINT `fk_reporte_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem`.`historialmedico` (`idhistorialmedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reporte_cita1`
    FOREIGN KEY (`cita_idcita`)
    REFERENCES `telesystem`.`cita` (`idcita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`boletas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`boletas` (
  `idboletas` INT NOT NULL AUTO_INCREMENT,
  `conceptopago` VARCHAR(100) NOT NULL,
  `monto` DOUBLE NOT NULL,
  `seguros_id_seguro` INT NOT NULL,
  `receta_idreceta` INT NULL,
  `cita_idcita` INT NOT NULL,
  `examen_medico_idexamen` INT NOT NULL,
  PRIMARY KEY (`idboletas`),
  INDEX `fk_boletas_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  INDEX `fk_boletas_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  INDEX `fk_boletas_cita1_idx` (`cita_idcita` ASC) VISIBLE,
  INDEX `fk_boletas_examen_medico1_idx` (`examen_medico_idexamen` ASC) VISIBLE,
  CONSTRAINT `fk_boletas_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem`.`seguros` (`id_seguro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_boletas_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem`.`receta` (`idreceta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_boletas_cita1`
    FOREIGN KEY (`cita_idcita`)
    REFERENCES `telesystem`.`cita` (`idcita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_boletas_examen_medico1`
    FOREIGN KEY (`examen_medico_idexamen`)
    REFERENCES `telesystem`.`examen_medico` (`idexamen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`formularios_registro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`formularios_registro` (
  `idformularios` INT NOT NULL AUTO_INCREMENT,
  `nombrecompleto` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firma` LONGBLOB NOT NULL,
  PRIMARY KEY (`idformularios`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`sedes_has_especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`sedes_has_especialidades` (
  `sedes_idsedes` INT NOT NULL,
  `especialidades_id_especialidad` INT NOT NULL,
  INDEX `fk_sedes_has_especialidades_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_sedes_has_especialidades_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  PRIMARY KEY (`especialidades_id_especialidad`, `sedes_idsedes`),
  CONSTRAINT `fk_sedes_has_especialidades_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sedes_has_especialidades_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem`.`especialidades` (`id_especialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`deliverymedicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`deliverymedicamentos` (
  `iddeliverymedicamentos` INT NOT NULL AUTO_INCREMENT,
  `latitudinicial` FLOAT NOT NULL,
  `longitudinicial` FLOAT NOT NULL,
  `latitudfinal` FLOAT NOT NULL,
  `longitudfinal` FLOAT NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `receta_idreceta` INT NOT NULL,
  PRIMARY KEY (`iddeliverymedicamentos`),
  INDEX `fk_deliverymedicamentos_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  CONSTRAINT `fk_deliverymedicamentos_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem`.`receta` (`idreceta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`conversaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`conversaciones` (
  `idconversaciones` INT NOT NULL,
  `fechacreacion` DATETIME NOT NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  `usuario_dni1` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idconversaciones`),
  INDEX `fk_conversaciones_usuario1_idx` (`usuario_dni` ASC) VISIBLE,
  INDEX `fk_conversaciones_usuario2_idx` (`usuario_dni1` ASC) VISIBLE,
  CONSTRAINT `fk_conversaciones_usuario1`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conversaciones_usuario2`
    FOREIGN KEY (`usuario_dni1`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`notificaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`notificaciones` (
  `idnotificaciones` INT NOT NULL AUTO_INCREMENT,
  `contenido` VARCHAR(200) NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idnotificaciones`),
  INDEX `fk_notificaciones_usuario1_idx` (`usuario_dni` ASC) VISIBLE,
  CONSTRAINT `fk_notificaciones_usuario1`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`horasdoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`horasdoctor` (
  `idhorasdoctor` INT NOT NULL AUTO_INCREMENT,
  `horainicio` DATETIME NOT NULL,
  `horafin` DATETIME NOT NULL,
  `horalibre` DATETIME NOT NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idhorasdoctor`),
  INDEX `fk_horasdoctor_usuario1_idx` (`usuario_dni` ASC) VISIBLE,
  CONSTRAINT `fk_horasdoctor_usuario1`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`medicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`medicamentos` (
  `idmedicamentos` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `precio` FLOAT NOT NULL,
  `cantidad` INT NOT NULL,
  `frecuencia` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idmedicamentos`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`mensajes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`mensajes` (
  `idmensajes` INT NOT NULL AUTO_INCREMENT,
  `contenido` VARCHAR(500) NOT NULL,
  `fecha` VARCHAR(45) NOT NULL,
  `conversaciones_idconversaciones` INT NOT NULL,
  PRIMARY KEY (`idmensajes`),
  INDEX `fk_mensajes_conversaciones1_idx` (`conversaciones_idconversaciones` ASC) VISIBLE,
  CONSTRAINT `fk_mensajes_conversaciones1`
    FOREIGN KEY (`conversaciones_idconversaciones`)
    REFERENCES `telesystem`.`conversaciones` (`idconversaciones`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`receta_has_medicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`receta_has_medicamentos` (
  `receta_idreceta` INT NOT NULL,
  `medicamentos_idmedicamentos` INT NOT NULL,
  PRIMARY KEY (`receta_idreceta`, `medicamentos_idmedicamentos`),
  INDEX `fk_receta_has_medicamentos_medicamentos1_idx` (`medicamentos_idmedicamentos` ASC) VISIBLE,
  INDEX `fk_receta_has_medicamentos_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  CONSTRAINT `fk_receta_has_medicamentos_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem`.`receta` (`idreceta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receta_has_medicamentos_medicamentos1`
    FOREIGN KEY (`medicamentos_idmedicamentos`)
    REFERENCES `telesystem`.`medicamentos` (`idmedicamentos`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`documentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`documentos` (
  `iddocumentos` INT NOT NULL AUTO_INCREMENT,
  `historialmedico_idhistorialmedico` INT NOT NULL,
  PRIMARY KEY (`iddocumentos`),
  INDEX `fk_documentos_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_documentos_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem`.`historialmedico` (`idhistorialmedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`preguntas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`preguntas` (
  `idpreguntas` INT NOT NULL AUTO_INCREMENT,
  `pregunta` VARCHAR(200) NOT NULL,
  `cuestionario_idcuestionario` INT NOT NULL,
  `especialidades_id_especialidad` INT NOT NULL,
  PRIMARY KEY (`idpreguntas`),
  INDEX `fk_preguntas_cuestionario1_idx` (`cuestionario_idcuestionario` ASC) VISIBLE,
  INDEX `fk_preguntas_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  CONSTRAINT `fk_preguntas_cuestionario1`
    FOREIGN KEY (`cuestionario_idcuestionario`)
    REFERENCES `telesystem`.`cuestionario` (`idcuestionario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_preguntas_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem`.`especialidades` (`id_especialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`respuestas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`respuestas` (
  `idrespuestas` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(200) NOT NULL,
  `preguntas_idpreguntas` INT NOT NULL,
  `historialmedico_idhistorialmedico` INT NOT NULL,
  PRIMARY KEY (`idrespuestas`),
  INDEX `fk_respuestas_preguntas1_idx` (`preguntas_idpreguntas` ASC) VISIBLE,
  INDEX `fk_respuestas_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_respuestas_preguntas1`
    FOREIGN KEY (`preguntas_idpreguntas`)
    REFERENCES `telesystem`.`preguntas` (`idpreguntas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_respuestas_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem`.`historialmedico` (`idhistorialmedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`alergias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`alergias` (
  `idalergias` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idalergias`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`historialmedico_has_alergias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`historialmedico_has_alergias` (
  `historialmedico_idhistorialmedico` INT NOT NULL AUTO_INCREMENT,
  `alergias_idalergias` INT NOT NULL,
  PRIMARY KEY (`historialmedico_idhistorialmedico`, `alergias_idalergias`),
  INDEX `fk_historialmedico_has_alergias_alergias1_idx` (`alergias_idalergias` ASC) VISIBLE,
  INDEX `fk_historialmedico_has_alergias_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_historialmedico_has_alergias_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem`.`historialmedico` (`idhistorialmedico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_historialmedico_has_alergias_alergias1`
    FOREIGN KEY (`alergias_idalergias`)
    REFERENCES `telesystem`.`alergias` (`idalergias`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`examen_medico_has_cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`examen_medico_has_cita` (
  `examen_medico_idexamen` INT NOT NULL,
  `cita_idcita` INT NOT NULL,
  PRIMARY KEY (`examen_medico_idexamen`, `cita_idcita`),
  INDEX `fk_examen_medico_has_cita_cita1_idx` (`cita_idcita` ASC) VISIBLE,
  INDEX `fk_examen_medico_has_cita_examen_medico1_idx` (`examen_medico_idexamen` ASC) VISIBLE,
  CONSTRAINT `fk_examen_medico_has_cita_examen_medico1`
    FOREIGN KEY (`examen_medico_idexamen`)
    REFERENCES `telesystem`.`examen_medico` (`idexamen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_examen_medico_has_cita_cita1`
    FOREIGN KEY (`cita_idcita`)
    REFERENCES `telesystem`.`cita` (`idcita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem`.`consultorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem`.`consultorio` (
  `idconsultorio` VARCHAR(5) NOT NULL,
  `sedes_idsedes` INT NOT NULL,
  PRIMARY KEY (`idconsultorio`),
  INDEX `fk_consutorio_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  CONSTRAINT `fk_consutorio_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
