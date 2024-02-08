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


-- -----------------------------------------------------
-- Table `partidas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS partidas;
CREATE  TABLE IF NOT EXISTS partidas (
  idGame BIGINT,
  gameState TEXT(),
  CONSTRAINT pk_partidas PRIMARY KEY (idGame) );
  
DROP TABLE IF EXISTS usuarios_partidas;
CREATE  TABLE IF NOT EXISTS usuarios_partidas (
  idUsuario BIGINT,
  idPartida BIGINT,
  CONSTRAINT pk_usuarios_partidas PRIMARY KEY (idUsuario,idPartida),
  FOREIGN KEY (idUsuario) REFERENCES usuarios(id);
  FOREIGN KEY (idPartida) REFERENCES partidas(idGame) );


-- ---------------------------------------------------
-- INSERCIÓN DE DATOS
-- ---------------------------------------------------

INSERT INTO `usuarios` (`id`,`nombre`,`email`,`passwd`,`saldo`,`estadisticas`) VALUES (1,'Pepe','pepe@mail.com',NULL,NULL,NULL);
INSERT INTO `usuarios` (`id`,`nombre`,`email`,`passwd`,`saldo`,`estadisticas`) VALUES (2,'Paco','paco@mail.com',NULL,NULL,NULL);
INSERT INTO `usuarios` (`id`,`nombre`,`email`,`passwd`,`saldo`,`estadisticas`) VALUES (3,'Juan','juan@mail.com',NULL,NULL,NULL);
INSERT INTO `usuarios` (`id`,`nombre`,`email`,`passwd`,`saldo`,`estadisticas`) VALUES (4,'Marcos','marcos@mail.com',NULL,NULL,NULL);

INSERT INTO `partidas` (`idGame`,`state`) VALUES (1,NULL);

INSERT INTO `usuarios_partidas` (`idPartida`,`idUsuario`) VALUES (1,1);
INSERT INTO `usuarios_partidas` (`idPartida`,`idUsuario`) VALUES (1,2);
INSERT INTO `usuarios_partidas` (`idPartida`,`idUsuario`) VALUES (1,3);
INSERT INTO `usuarios_partidas` (`idPartida`,`idUsuario`) VALUES (1,4);

