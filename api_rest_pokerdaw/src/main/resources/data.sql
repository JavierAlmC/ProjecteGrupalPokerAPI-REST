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
  idGame BIGINT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(100) NOT NULL,
  gameState TEXT NOT NULL,
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
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (1,'descripcion1','gameState1');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (2,'descripcion2','gameState2');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (3,'descripcion3','gameState3');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (4,'descripcion4','gameState4');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (5,'descripcion5','gameState5');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (6,'descripcion6','gameState6');
INSERT INTO `partidas` (`idGame`,`descripcion`,`gameState`) VALUES (7,'descripcion7','gameState7');


