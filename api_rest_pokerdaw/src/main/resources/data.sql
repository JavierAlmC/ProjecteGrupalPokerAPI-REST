DROP TABLE IF EXISTS usuarios;
CREATE  TABLE IF NOT EXISTS usuarios(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL ,
  `nickname` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `saldo` INT,
  `estadisticas` TEXT,
  PRIMARY KEY (`id`),
  CONSTRAINT usuario_uk_nickname UNIQUE KEY (`nickname`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = latin1;

CREATE  TABLE IF NOT EXISTS `roles` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE  TABLE IF NOT EXISTS `usuarios_roles` (
  idUsuario BigInt  NOT NULL,  
  idRol INT(11)  NOT NULL,
  PRIMARY KEY (`idUsuario`,`idRol`),
  CONSTRAINT `usuarios_roles_fk_usuarios`
    FOREIGN KEY (`idUsuario` )
    REFERENCES `usuarios` (`id` ),
  CONSTRAINT `usuarios_roles_fk_roles`
    FOREIGN KEY (`idRol` )
    REFERENCES `roles` (`id` ) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

INSERT INTO `roles` (`id`, `nombre`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');


-- -----------------------------------------------------
-- Table `partidas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS partidas;
CREATE  TABLE IF NOT EXISTS partidas (
  idGame BIGINT,
  descripcion VARCHAR(100),
  gameState TEXT,
  CONSTRAINT pk_partidas PRIMARY KEY (idGame) );
  
DROP TABLE IF EXISTS usuarios_partidas;
CREATE  TABLE IF NOT EXISTS usuarios_partidas (
  idUsuario BIGINT,
  idPartida BIGINT,
  CONSTRAINT pk_usuarios_partidas PRIMARY KEY (idUsuario,idPartida),
  FOREIGN KEY (idUsuario) REFERENCES usuarios(id),
  FOREIGN KEY (idPartida) REFERENCES partidas(idGame) );


-- ---------------------------------------------------
-- INSERCIÓN DE DATOS
-- ---------------------------------------------------



