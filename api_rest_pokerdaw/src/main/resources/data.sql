DROP TABLE IF EXISTS usuarios;
CREATE  TABLE IF NOT EXISTS usuarios(
id IDENTITY,
nombre VARCHAR(50),
email VARCHAR(50) ,
passwd VARCHAR(50) ,
saldo INT,
estadisticas TEXT(),
 CONSTRAINT pk_usuarios PRIMARY KEY(id));


-- -----------------------------------------------------
-- Table `partidas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS partidas;
CREATE  TABLE IF NOT EXISTS partidas (
  idGame IDENTITY,
  state TEXT(),
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


