CREATE  TABLE IF NOT EXISTS gameState (
  idState BIGINT NOT NULL AUTO_INCREMENT,
  gameStateName VARCHAR(50),
  deal INT,
  round VARCHAR(50),
  whoIsDealer BIGINT,
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
  `totalApostado` INT,
  `cards` TEXT,
  `isDealer` VARCHAR,
  `playerState` VARCHAR,
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