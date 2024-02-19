CREATE  TABLE IF NOT EXISTS gameState (
  idState BIGINT NOT NULL AUTO_INCREMENT,
  gameStateName VARCHAR(50),
  deal INT,
  round VARCHAR(50),
  whoIsDealer INT,
  blinds INT,
  idPlayingNow INT,
  minDealValue INT,
  deck TEXT,
  tableCards TEXT,
  CONSTRAINT pk_gameState PRIMARY KEY (idState) );

CREATE  TABLE IF NOT EXISTS usuarios(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL ,
  `nickname` VARCHAR(255) NOT NULL ,
  `email` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `saldo` INT,
  `estadisticas` TEXT,
  `idState` BIGINT,
  `idCreatedGame` BIGINT,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`idState`) REFERENCES gameState(idState),
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

INSERT INTO gameState (deal, round, whoIsDealer, blinds, idPlayingNow, minDealValue, deck, `table`, idPartida) 
VALUES 
(1, 'pre-flop', 1, 10, 2, 100, '[1, 2, 3, 4, 5]', '[6, 7, 8, 9, 10]', 1),
(2, 'flop', 2, 20, 3, 150, '[11, 12, 13, 14, 15]', '[16, 17, 18, 19, 20]', 2),
(3, 'turn', 3, 30, 4, 200, '[21, 22, 23, 24, 25]', '[26, 27, 28, 29, 30]', 3);

