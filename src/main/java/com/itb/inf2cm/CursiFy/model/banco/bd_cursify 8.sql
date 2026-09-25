USE master;
GO
IF DB_ID('bd_cursify') IS NULL CREATE DATABASE bd_cursify;
GO
USE bd_cursify;
GO

CREATE TABLE Usuario
(
    id INT IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    nivel_acesso VARCHAR(10) NULL,
    foto VARBINARY(MAX) NULL,
    bio VARCHAR(2000) NULL,
    foto_capa VARBINARY(MAX) NULL,
    data_cadastro SMALLDATETIME NOT NULL,
    status_usuario VARCHAR(20) NOT NULL,
    professor_aprovado VARCHAR(20) NOT NULL DEFAULT 'Pendente',
    tema_preferido VARCHAR(10) NOT NULL DEFAULT 'light'
    );

CREATE TABLE Curso
(
    id INT IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(2000) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    data_criacao SMALLDATETIME NOT NULL,
    status_curso VARCHAR(20) NOT NULL,
    curso_aprovado VARCHAR(20) NOT NULL DEFAULT 'Pendente',
    motivo_recusa VARCHAR(2000) NULL,
    numero_alunos INT NOT NULL DEFAULT 0
    );

CREATE TABLE UsuarioCurso
(
    id INT IDENTITY PRIMARY KEY,
    usuario_id INT NOT NULL,
    curso_id INT NOT NULL,
    progresso INT NOT NULL DEFAULT 0,
    concluido BIT NOT NULL DEFAULT 0,
    
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (curso_id) REFERENCES Curso(id)
    );

CREATE TABLE Material
(
    id INT IDENTITY PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    subtitulo VARCHAR(100) NOT NULL,
    conteudo VARCHAR(500) NOT NULL,
    link_titulo VARCHAR(150) NULL,
    link VARCHAR(200) NULL,
    usuario_id INT NOT NULL,
    curso_id INT NOT NULL,
    status_material VARCHAR(20) NOT NULL,
    
    FOREIGN KEY (curso_id) REFERENCES Curso(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
    );

CREATE TABLE Material_links
(
    material_id INT NOT NULL,
    ordem INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    url VARCHAR(500) NOT NULL,
    FOREIGN KEY (material_id) REFERENCES Material(id) ON DELETE CASCADE
    );

CREATE TABLE Exercicios
(
    id INT IDENTITY PRIMARY KEY,
    enunciado VARCHAR(2000) NOT NULL,
    resposta_correta VARCHAR(500) NOT NULL,
    explicacao VARCHAR(2000) NULL,
    usuario_id INT NOT NULL,
    curso_id INT NOT NULL,
    status_exercicios VARCHAR(20) NOT NULL,
    
    FOREIGN KEY (curso_id) REFERENCES Curso(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
    );

CREATE TABLE Exercicios_alternativas
(
    exercicio_id INT NOT NULL,
    alternativa VARCHAR(500) NOT NULL,
    
    FOREIGN KEY (exercicio_id) REFERENCES Exercicios(id)
    );

CREATE TABLE Mensagem
(
    id INT IDENTITY PRIMARY KEY,
    remetente_id INT NULL,
    destinatario_id INT NULL,
    curso_id INT NULL,
    conteudo VARCHAR(500) NOT NULL,
    data_mensagem SMALLDATETIME NOT NULL,
    status_mensagem VARCHAR(20) NOT NULL
    );

CREATE TABLE Chat
(
    id INT IDENTITY PRIMARY KEY,
    Remetente VARCHAR(50) NOT NULL,
    usuario_id INT NOT NULL,
    mensagem_id INT NOT NULL,
    remetente_id INT NULL,
    destinatario_id INT NULL,
    curso_id INT NULL,
    data_chat SMALLDATETIME NOT NULL,
    status_chat VARCHAR(20) NOT NULL,
    
    FOREIGN KEY (mensagem_id) REFERENCES Mensagem(id)
    );

CREATE TABLE UsuarioChat
(
    id INT IDENTITY PRIMARY KEY,
    chat_id INT NOT NULL,
    usuario_id INT NOT NULL,
    
    FOREIGN KEY (chat_id) REFERENCES Chat(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
    );

CREATE TABLE recuperacaosenha
(
    id INT IDENTITY PRIMARY KEY,
    usuario_id INT NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    data_expiracao SMALLDATETIME NOT NULL,
    usado BIT NOT NULL DEFAULT 0,
    
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
    );

CREATE TABLE PreferenciaCurso
(
    id INT IDENTITY PRIMARY KEY,
    usuario_id INT NOT NULL,
    curso_id INT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    valor VARCHAR(2000) NULL,
    
    CONSTRAINT UQ_PreferenciaCurso UNIQUE (usuario_id, curso_id, tipo),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (curso_id) REFERENCES Curso(id)
    );

SELECT * FROM Usuario; SELECT * FROM Curso; SELECT * FROM UsuarioCurso; SELECT * FROM Material; SELECT * FROM Exercicios; SELECT * FROM Exercicios_alternativas; SELECT * FROM Mensagem; SELECT * FROM Chat; SELECT * FROM UsuarioChat; SELECT * FROM recuperacaosenha; SELECT * FROM PreferenciaCurso;
