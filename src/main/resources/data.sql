CREATE TABLE tbPautas(
    idPauta INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);
CREATE TABLE tbSessoes(
    idSessao INTEGER PRIMARY KEY AUTO_INCREMENT,
    idPauta INTEGER REFERENCES tbPautas (idPauta),
    dtHrEncerramento TIMESTAMP
);
CREATE TABLE tbVotos(
    idVoto INTEGER PRIMARY KEY AUTO_INCREMENT,
    idSessao INTEGER REFERENCES tbSessoes (idSessao),
    cpfAssociado VARCHAR(11),
    voto INTEGER
);