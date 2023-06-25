-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema telesystem_2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema telesystem_2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `telesystem_2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `telesystem_2` ;

-- -----------------------------------------------------
-- Table `telesystem_2`.`alergias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`alergias` (
  `idalergias` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idalergias`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`api`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`api` (
  `dni` VARCHAR(45) NOT NULL,
  `Nombres` VARCHAR(45) NULL DEFAULT NULL,
  `Apellidos` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`especialidades` (
  `id_especialidad` INT NOT NULL AUTO_INCREMENT,
  `nombre_especialidad` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_especialidad`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`estadoscita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`estadoscita` (
  `idestados` INT NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestados`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`receta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`receta` (
  `idreceta` INT NOT NULL AUTO_INCREMENT,
  `comentario` VARCHAR(500) NULL,
  PRIMARY KEY (`idreceta`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`sedes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`sedes` (
  `idsedes` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `latitud` DOUBLE NOT NULL,
  `longitud` DOUBLE NOT NULL,
  `torres` INT NOT NULL,
  `pisos` INT NOT NULL,
  `consultorios` INT NOT NULL,
  `direccion` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`idsedes`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`tipocita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`tipocita` (
  `idtipocita` INT NOT NULL,
  `tipo_cita` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idtipocita`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`estados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`estados` (
  `idestado` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idestado`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`roles` (
  `idroles` INT NOT NULL,
  `nombre_rol` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idroles`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`seguros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`seguros` (
  `id_seguro` INT NOT NULL AUTO_INCREMENT,
  `nombre_seguro` VARCHAR(45) NOT NULL,
  `porc_seguro` VARCHAR(45) NOT NULL,
  `porc_doctor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_seguro`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`usuario` (
  `dni` VARCHAR(8) NOT NULL,
  `contrasena` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `fechanacimiento` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(9) NOT NULL,
  `sexo` VARCHAR(45) NOT NULL,
  `direccion` VARCHAR(200) NOT NULL,
  `sedes_idsedes` INT NULL DEFAULT NULL,
  `roles_idroles` INT NOT NULL,
  `especialidades_id_especialidad` INT NULL DEFAULT NULL,
  `seguros_id_seguro` INT NULL DEFAULT NULL,
  `estados_idestado` INT NULL DEFAULT NULL,
  `modooscuro` TINYINT(1) NULL DEFAULT NULL,
  `modoregistro` VARCHAR(45) NULL DEFAULT NULL,
  `ceduladoctor` VARCHAR(45) NULL DEFAULT NULL,
  `enabled` TINYINT(1) NULL DEFAULT NULL,
  `historialmedico_idhistorialmedico` INT NULL DEFAULT NULL,
  PRIMARY KEY (`dni`),
  INDEX `fk_usuario_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  INDEX `fk_usuario_roles1_idx` (`roles_idroles` ASC) VISIBLE,
  INDEX `fk_usuario_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_usuario_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  INDEX `fk_usuario_estados1_idx` (`estados_idestado` ASC) VISIBLE,
  INDEX `fk_usuario_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem_2`.`especialidades` (`id_especialidad`),
  CONSTRAINT `fk_usuario_estados1`
    FOREIGN KEY (`estados_idestado`)
    REFERENCES `telesystem_2`.`estados` (`idestado`),
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`roles_idroles`)
    REFERENCES `telesystem_2`.`roles` (`idroles`),
  CONSTRAINT `fk_usuario_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem_2`.`sedes` (`idsedes`),
  CONSTRAINT `fk_usuario_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem_2`.`seguros` (`id_seguro`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`examen_medico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`examen_medico` (
  `idexamen` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` TEXT NOT NULL,
  PRIMARY KEY (`idexamen`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`cita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`cita` (
  `idcita` INT NOT NULL AUTO_INCREMENT,
  `citacancelada` TINYINT(1) NOT NULL,
  `sedes_idsedes` INT NOT NULL,
  `especialidades_id_especialidad` INT NULL,
  `estadoscita_idestados` INT NOT NULL,
  `receta_idreceta` INT NULL DEFAULT NULL,
  `formapago` VARCHAR(45) NOT NULL,
  `modalidad` VARCHAR(45) NOT NULL,
  `tipocita_idtipocita` INT NOT NULL,
  `fecha` VARCHAR(45) NOT NULL,
  `hora` TIME NOT NULL,
  `paciente_dni` VARCHAR(8) NOT NULL,
  `doctor_dni1` VARCHAR(8) NOT NULL,
  `pagada` TINYINT(1) NOT NULL,
  `examen_medico_idexamen` INT NULL,
  PRIMARY KEY (`idcita`),
  INDEX `fk_cita_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  INDEX `fk_cita_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_cita_estadoscita1_idx` (`estadoscita_idestados` ASC) VISIBLE,
  INDEX `fk_cita_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  INDEX `fk_cita_tipocita1_idx` (`tipocita_idtipocita` ASC) VISIBLE,
  INDEX `fk_cita_usuario1_idx` (`paciente_dni` ASC) VISIBLE,
  INDEX `fk_cita_usuario2_idx` (`doctor_dni1` ASC) VISIBLE,
  INDEX `fk_cita_examen_medico1_idx` (`examen_medico_idexamen` ASC) VISIBLE,
  CONSTRAINT `fk_cita_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem_2`.`especialidades` (`id_especialidad`),
  CONSTRAINT `fk_cita_estadoscita1`
    FOREIGN KEY (`estadoscita_idestados`)
    REFERENCES `telesystem_2`.`estadoscita` (`idestados`),
  CONSTRAINT `fk_cita_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem_2`.`receta` (`idreceta`),
  CONSTRAINT `fk_cita_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem_2`.`sedes` (`idsedes`),
  CONSTRAINT `fk_cita_tipocita1`
    FOREIGN KEY (`tipocita_idtipocita`)
    REFERENCES `telesystem_2`.`tipocita` (`idtipocita`),
  CONSTRAINT `fk_cita_usuario1`
    FOREIGN KEY (`paciente_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`),
  CONSTRAINT `fk_cita_usuario2`
    FOREIGN KEY (`doctor_dni1`)
    REFERENCES `telesystem_2`.`usuario` (`dni`),
  CONSTRAINT `fk_cita_examen_medico1`
    FOREIGN KEY (`examen_medico_idexamen`)
    REFERENCES `telesystem_2`.`examen_medico` (`idexamen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 49
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`boletas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`boletas` (
  `idboletas` INT NOT NULL AUTO_INCREMENT,
  `conceptopago` VARCHAR(100) NOT NULL,
  `monto` DOUBLE NOT NULL,
  `seguros_id_seguro` INT NOT NULL,
  `receta_idreceta` INT NULL DEFAULT NULL,
  `cita_idcita` INT NOT NULL,
  PRIMARY KEY (`idboletas`),
  INDEX `fk_boletas_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  INDEX `fk_boletas_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  INDEX `fk_boletas_cita1_idx` (`cita_idcita` ASC) VISIBLE,
  CONSTRAINT `fk_boletas_cita1`
    FOREIGN KEY (`cita_idcita`)
    REFERENCES `telesystem_2`.`cita` (`idcita`),
  CONSTRAINT `fk_boletas_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem_2`.`receta` (`idreceta`),
  CONSTRAINT `fk_boletas_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem_2`.`seguros` (`id_seguro`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`consultorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`consultorio` (
  `idconsultorio` VARCHAR(5) NOT NULL,
  `sedes_idsedes` INT NOT NULL,
  INDEX `fk_consultorio_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  PRIMARY KEY (`idconsultorio`),
  CONSTRAINT `fk_consultorio_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem_2`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`conversaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`conversaciones` (
  `idconversaciones` INT NOT NULL AUTO_INCREMENT,
  `fechacreacion` DATETIME NOT NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  `usuario_dni1` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idconversaciones`),
  INDEX `fk_conversaciones_usuario1_idx` (`usuario_dni` ASC) VISIBLE,
  INDEX `fk_conversaciones_usuario2_idx` (`usuario_dni1` ASC) VISIBLE,
  CONSTRAINT `fk_conversaciones_usuario1`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`),
  CONSTRAINT `fk_conversaciones_usuario2`
    FOREIGN KEY (`usuario_dni1`)
    REFERENCES `telesystem_2`.`usuario` (`dni`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`cuestionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`cuestionario` (
  `idcuestionario` INT NOT NULL AUTO_INCREMENT,
  `nombrecuestionario` VARCHAR(45) NOT NULL,
  `activo` TINYINT NOT NULL,
  `paciente_dni` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idcuestionario`),
  INDEX `fk_cuestionario_usuario1_idx` (`paciente_dni` ASC) VISIBLE,
  CONSTRAINT `fk_cuestionario_usuario1`
    FOREIGN KEY (`paciente_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`cuestionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`cuestionarios` (
  `idcuestionario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `activo` TINYINT(1) NOT NULL,
  `preguntas` TEXT NOT NULL,
  PRIMARY KEY (`idcuestionario`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`cuestionarios_usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`cuestionarios_usuarios` (
  `idcuestionario` INT NOT NULL,
  `dnipaciente` VARCHAR(8) NOT NULL,
  `respuestas` TEXT NOT NULL,
  `respondido` TINYINT(1) NOT NULL,
  `dnidoctor` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idcuestionario`),
  INDEX `fk_cuestionarios_usuarios_cuestionarios_idx` (`idcuestionario` ASC) VISIBLE,
  INDEX `fk_cuestionarios_usuarios_usuario_idx` (`dnipaciente` ASC) VISIBLE,
  INDEX `fk_cuestionarios_usuarios_usuario1_idx` (`dnidoctor` ASC) VISIBLE,
  CONSTRAINT `fk_cuestionarios_usuarios_cuestionarios`
    FOREIGN KEY (`idcuestionario`)
    REFERENCES `telesystem_2`.`cuestionarios` (`idcuestionario`),
  CONSTRAINT `fk_cuestionarios_usuarios_usuario`
    FOREIGN KEY (`dnipaciente`)
    REFERENCES `telesystem_2`.`usuario` (`dni`),
  CONSTRAINT `fk_cuestionarios_usuarios_usuario1`
    FOREIGN KEY (`dnidoctor`)
    REFERENCES `telesystem_2`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`deliverymedicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`deliverymedicamentos` (
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
    REFERENCES `telesystem_2`.`receta` (`idreceta`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`form_autoregistro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`form_autoregistro` (
  `idautoregistro` INT NOT NULL AUTO_INCREMENT,
  `dni` VARCHAR(45) NULL DEFAULT NULL,
  `nombres` VARCHAR(45) NULL DEFAULT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `fechanacimiento` VARCHAR(45) NULL DEFAULT NULL,
  `domicilio` VARCHAR(45) NULL DEFAULT NULL,
  `sexo` VARCHAR(45) NULL DEFAULT NULL,
  `celular` VARCHAR(45) NULL DEFAULT NULL,
  `seguroid` INT NULL DEFAULT NULL,
  `sedeid` INT NULL DEFAULT NULL,
  `correo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idautoregistro`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`form_invitacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`form_invitacion` (
  `idform_invitacion` INT NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(45) NULL DEFAULT NULL,
  `apellidos` VARCHAR(45) NULL DEFAULT NULL,
  `id_sede` VARCHAR(45) NULL DEFAULT NULL,
  `dni` VARCHAR(45) NULL DEFAULT NULL,
  `fechanacimiento` VARCHAR(45) NULL DEFAULT NULL,
  `sexo` VARCHAR(45) NULL DEFAULT NULL,
  `domicilio` VARCHAR(45) NULL DEFAULT NULL,
  `correo` VARCHAR(45) NULL DEFAULT NULL,
  `id_seguro` VARCHAR(45) NULL DEFAULT NULL,
  `celular` VARCHAR(45) NULL DEFAULT NULL,
  `medicamentos` VARCHAR(200) NULL DEFAULT NULL,
  `alergias` VARCHAR(200) NULL DEFAULT NULL,
  `pendiente` TINYINT NULL DEFAULT '1',
  PRIMARY KEY (`idform_invitacion`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`formularios_registro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`formularios_registro` (
  `idformularios` INT NOT NULL AUTO_INCREMENT,
  `nombrecompleto` VARCHAR(45) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `activo` TINYINT(1) NULL,
  `nombre` VARCHAR(45) NULL,
  `preguntas` TEXT NULL,
  `respuestas` TEXT NULL,
  `respondido` TINYINT(1) NULL,
  PRIMARY KEY (`idformularios`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`historialmedico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`historialmedico` (
  `idhistorialmedico` INT NOT NULL AUTO_INCREMENT,
  `validahistorial` TINYINT(1) NOT NULL,
  `seguros_id_seguro` INT NOT NULL,
  PRIMARY KEY (`idhistorialmedico`),
  INDEX `fk_historialmedico_seguros1_idx` (`seguros_id_seguro` ASC) VISIBLE,
  CONSTRAINT `fk_historialmedico_seguros1`
    FOREIGN KEY (`seguros_id_seguro`)
    REFERENCES `telesystem_2`.`seguros` (`id_seguro`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`historialmedico_has_alergias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`historialmedico_has_alergias` (
  `historialmedico_idhistorialmedico` INT NOT NULL,
  `alergias_idalergias` INT NOT NULL,
  PRIMARY KEY (`historialmedico_idhistorialmedico`, `alergias_idalergias`),
  INDEX `fk_historialmedico_has_alergias_alergias1_idx` (`alergias_idalergias` ASC) VISIBLE,
  INDEX `fk_historialmedico_has_alergias_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_historialmedico_has_alergias_alergias1`
    FOREIGN KEY (`alergias_idalergias`)
    REFERENCES `telesystem_2`.`alergias` (`idalergias`),
  CONSTRAINT `fk_historialmedico_has_alergias_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem_2`.`historialmedico` (`idhistorialmedico`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`horasdoctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`horasdoctor` (
  `idhorasdoctor` INT NOT NULL,
  `horainicio` TIME NOT NULL,
  `horafin` TIME NOT NULL,
  `horalibre` TIME NOT NULL,
  `doctor_dni` VARCHAR(8) NOT NULL,
  `dias` VARCHAR(100) NOT NULL,
  `mes` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idhorasdoctor`),
  INDEX `fk_horasdoctor_usuario1_idx` (`doctor_dni` ASC) VISIBLE,
  CONSTRAINT `fk_horasdoctor_usuario1`
    FOREIGN KEY (`doctor_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`informe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`informe` (
  `idinforme` INT NOT NULL AUTO_INCREMENT,
  `diagnostico` VARCHAR(200) NOT NULL,
  `bitacora` VARCHAR(500) NULL DEFAULT NULL,
  `historialmedico_idhistorialmedico` INT NOT NULL,
  `activo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idinforme`),
  INDEX `fk_reporte_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_reporte_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem_2`.`historialmedico` (`idhistorialmedico`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`medicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`medicamentos` (
  `idmedicamentos` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `precio` FLOAT NOT NULL,
  PRIMARY KEY (`idmedicamentos`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`mensajes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`mensajes` (
  `idmensajes` INT NOT NULL AUTO_INCREMENT,
  `contenido` VARCHAR(500) NOT NULL,
  `conversaciones_idconversaciones` INT NOT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  `emisorDNI` VARCHAR(8) NULL DEFAULT NULL,
  `receptorDNI` VARCHAR(8) NULL DEFAULT NULL,
  PRIMARY KEY (`idmensajes`),
  INDEX `fk_mensajes_conversaciones1_idx` (`conversaciones_idconversaciones` ASC) VISIBLE,
  CONSTRAINT `fk_mensajes_conversaciones1`
    FOREIGN KEY (`conversaciones_idconversaciones`)
    REFERENCES `telesystem_2`.`conversaciones` (`idconversaciones`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`notificaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`notificaciones` (
  `idnotificaciones` INT NOT NULL AUTO_INCREMENT,
  `contenido` VARCHAR(200) NULL DEFAULT NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  `fecha` DATE NULL DEFAULT NULL,
  `hora` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`idnotificaciones`),
  INDEX `fk_notificaciones_usuario1_idx` (`usuario_dni` ASC) VISIBLE,
  CONSTRAINT `fk_notificaciones_usuario1`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`preguntas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`preguntas` (
  `idpreguntas` INT NOT NULL AUTO_INCREMENT,
  `pregunta` VARCHAR(200) NOT NULL,
  `cuestionario_idcuestionario` INT NOT NULL,
  `especialidades_id_especialidad` INT NOT NULL,
  PRIMARY KEY (`idpreguntas`),
  INDEX `fk_preguntas_cuestionario1_idx` (`cuestionario_idcuestionario` ASC) VISIBLE,
  INDEX `fk_preguntas_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  CONSTRAINT `fk_preguntas_cuestionario1`
    FOREIGN KEY (`cuestionario_idcuestionario`)
    REFERENCES `telesystem_2`.`cuestionario` (`idcuestionario`),
  CONSTRAINT `fk_preguntas_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem_2`.`especialidades` (`id_especialidad`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`receta_has_medicamentos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`receta_has_medicamentos` (
  `receta_idreceta` INT NOT NULL,
  `medicamentos_idmedicamentos` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `observaciones` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`receta_idreceta`, `medicamentos_idmedicamentos`),
  INDEX `fk_receta_has_medicamentos_medicamentos1_idx` (`medicamentos_idmedicamentos` ASC) VISIBLE,
  INDEX `fk_receta_has_medicamentos_receta1_idx` (`receta_idreceta` ASC) VISIBLE,
  CONSTRAINT `fk_receta_has_medicamentos_medicamentos1`
    FOREIGN KEY (`medicamentos_idmedicamentos`)
    REFERENCES `telesystem_2`.`medicamentos` (`idmedicamentos`),
  CONSTRAINT `fk_receta_has_medicamentos_receta1`
    FOREIGN KEY (`receta_idreceta`)
    REFERENCES `telesystem_2`.`receta` (`idreceta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`respuestas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`respuestas` (
  `idrespuestas` INT NOT NULL AUTO_INCREMENT,
  `respuesta` VARCHAR(200) NOT NULL,
  `preguntas_idpreguntas` INT NOT NULL,
  `historialmedico_idhistorialmedico` INT NOT NULL,
  PRIMARY KEY (`idrespuestas`),
  INDEX `fk_respuestas_preguntas1_idx` (`preguntas_idpreguntas` ASC) VISIBLE,
  INDEX `fk_respuestas_historialmedico1_idx` (`historialmedico_idhistorialmedico` ASC) VISIBLE,
  CONSTRAINT `fk_respuestas_historialmedico1`
    FOREIGN KEY (`historialmedico_idhistorialmedico`)
    REFERENCES `telesystem_2`.`historialmedico` (`idhistorialmedico`),
  CONSTRAINT `fk_respuestas_preguntas1`
    FOREIGN KEY (`preguntas_idpreguntas`)
    REFERENCES `telesystem_2`.`preguntas` (`idpreguntas`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`sedes_has_especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`sedes_has_especialidades` (
  `sedes_idsedes` INT NOT NULL,
  `especialidades_id_especialidad` INT NOT NULL,
  PRIMARY KEY (`sedes_idsedes`, `especialidades_id_especialidad`),
  INDEX `fk_sedes_has_especialidades_especialidades1_idx` (`especialidades_id_especialidad` ASC) VISIBLE,
  INDEX `fk_sedes_has_especialidades_sedes1_idx` (`sedes_idsedes` ASC) VISIBLE,
  CONSTRAINT `fk_sedes_has_especialidades_sedes1`
    FOREIGN KEY (`sedes_idsedes`)
    REFERENCES `telesystem_2`.`sedes` (`idsedes`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sedes_has_especialidades_especialidades1`
    FOREIGN KEY (`especialidades_id_especialidad`)
    REFERENCES `telesystem_2`.`especialidades` (`id_especialidad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `telesystem_2`.`reunion_virtual`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`reunion_virtual` (
  `idreunion_virtual` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `horainicio` DATE NOT NULL,
  `horafin` DATE NOT NULL,
  `token` VARCHAR(500) NULL,
  `room` VARCHAR(200) NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  `usuario_dni1` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idreunion_virtual`),
  INDEX `fk_reunion_virtual_usuario2_idx` (`usuario_dni` ASC) VISIBLE,
  INDEX `fk_reunion_virtual_usuario3_idx` (`usuario_dni1` ASC) VISIBLE,
  CONSTRAINT `fk_reunion_virtual_usuario2`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reunion_virtual_usuario3`
    FOREIGN KEY (`usuario_dni1`)
    REFERENCES `telesystem_2`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem_2`.`ux/ui`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`ux/ui` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `logo` LONGBLOB NOT NULL,
  `logonombre` VARCHAR(45) NOT NULL,
  `logocontenttype` VARCHAR(45) NOT NULL,
  `zonahoraria` VARCHAR(45) NOT NULL,
  `colorsidebar` VARCHAR(45) NOT NULL,
  `colortopbar` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem_2`.`reunion_virtual`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`reunion_virtual` (
  `idreunion_virtual` INT NOT NULL AUTO_INCREMENT,
  `fecha` VARCHAR(45) NOT NULL,
  `horainicio` DATE NOT NULL,
  `horafin` DATE NOT NULL,
  `token` VARCHAR(500) NULL,
  `room` VARCHAR(200) NULL,
  `usuario_dni` VARCHAR(8) NOT NULL,
  `usuario_dni1` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`idreunion_virtual`),
  INDEX `fk_reunion_virtual_usuario2_idx` (`usuario_dni` ASC) VISIBLE,
  INDEX `fk_reunion_virtual_usuario3_idx` (`usuario_dni1` ASC) VISIBLE,
  CONSTRAINT `fk_reunion_virtual_usuario2`
    FOREIGN KEY (`usuario_dni`)
    REFERENCES `telesystem_2`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reunion_virtual_usuario3`
    FOREIGN KEY (`usuario_dni1`)
    REFERENCES `telesystem_2`.`usuario` (`dni`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `telesystem_2`.`ux/ui`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `telesystem_2`.`ux/ui` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `logo` LONGBLOB NOT NULL,
  `logonombre` VARCHAR(45) NOT NULL,
  `logocontenttype` VARCHAR(45) NOT NULL,
  `zonahoraria` VARCHAR(45) NOT NULL,
  `colorsidebar` VARCHAR(45) NOT NULL,
  `colortopbar` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
