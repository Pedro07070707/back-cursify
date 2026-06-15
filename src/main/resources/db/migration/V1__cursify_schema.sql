USE master IF EXISTS(select * from sys.databases where name='bd_cursify') 
--DROP DATABASE bd_cursify
GO
-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_cursify
GO
-- ACESSAR O BANCO DE DADOS
USE bd_cursify
GO

CREATE TABLE usuarios (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(100) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    senha_hash NVARCHAR(255) NOT NULL,
    foto_url NVARCHAR(500) NULL,
    bio NVARCHAR(500) NULL,
    role NVARCHAR(20) NOT NULL,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    atualizado_em DATETIME2 NULL
);

CREATE TABLE categorias (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(80) NOT NULL UNIQUE,
    slug NVARCHAR(80) NOT NULL UNIQUE,
    descricao NVARCHAR(255) NULL,
    icone_url NVARCHAR(255) NULL,
    ativo BIT NOT NULL DEFAULT 1
);

CREATE TABLE cursos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(150) NOT NULL,
    descricao NVARCHAR(MAX) NULL,
    slug NVARCHAR(150) NOT NULL UNIQUE,
    thumbnail_url NVARCHAR(500) NULL,
    nivel NVARCHAR(20) NOT NULL,
    categoria_id BIGINT NOT NULL,
    professor_id BIGINT NULL,
    preco DECIMAL(10,2) NOT NULL DEFAULT 0,
    gratuito BIT NOT NULL DEFAULT 1,
    publicado BIT NOT NULL DEFAULT 0,
    carga_horaria_minutos INT NULL,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    atualizado_em DATETIME2 NULL,
    CONSTRAINT fk_cursos_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    CONSTRAINT fk_cursos_professor FOREIGN KEY (professor_id) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE INDEX ix_cursos_categoria ON cursos(categoria_id);
CREATE INDEX ix_cursos_professor ON cursos(professor_id);
CREATE INDEX ix_cursos_publicado ON cursos(publicado);
CREATE INDEX ix_cursos_gratuito ON cursos(gratuito);

CREATE TABLE modulos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    curso_id BIGINT NOT NULL,
    titulo NVARCHAR(150) NOT NULL,
    ordem INT NOT NULL,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_modulos_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

CREATE TABLE aulas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    modulo_id BIGINT NOT NULL,
    titulo NVARCHAR(150) NOT NULL,
    tipo NVARCHAR(20) NOT NULL,
    conteudo_url NVARCHAR(500) NULL,
    conteudo_texto NVARCHAR(MAX) NULL,
    duracao_minutos INT NULL,
    ordem INT NOT NULL,
    gratuita BIT NOT NULL DEFAULT 0,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_aulas_modulo FOREIGN KEY (modulo_id) REFERENCES modulos(id) ON DELETE CASCADE
);

CREATE TABLE matriculas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    data_matricula DATETIME2 NOT NULL DEFAULT GETDATE(),
    concluido BIT NOT NULL DEFAULT 0,
    data_conclusao DATETIME2 NULL,
    CONSTRAINT uq_matriculas_usuario_curso UNIQUE (usuario_id, curso_id),
    CONSTRAINT fk_matriculas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_matriculas_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

CREATE TABLE progresso_aulas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    aula_id BIGINT NOT NULL,
    concluida BIT NOT NULL DEFAULT 0,
    data_conclusao DATETIME2 NULL,
    CONSTRAINT uq_progresso_aulas UNIQUE (usuario_id, aula_id),
    CONSTRAINT fk_progresso_aulas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_progresso_aulas_aula FOREIGN KEY (aula_id) REFERENCES aulas(id) ON DELETE CASCADE
);

CREATE TABLE avaliacoes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    nota TINYINT NOT NULL,
    comentario NVARCHAR(1000) NULL,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT uq_avaliacoes UNIQUE (usuario_id, curso_id),
    CONSTRAINT fk_avaliacoes_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacoes_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

CREATE TABLE materiais (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    aula_id BIGINT NOT NULL,
    titulo NVARCHAR(150) NOT NULL,
    tipo NVARCHAR(20) NOT NULL,
    url NVARCHAR(500) NULL,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_materiais_aula FOREIGN KEY (aula_id) REFERENCES aulas(id) ON DELETE CASCADE
);

CREATE TABLE certificados (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    codigo_validacao NVARCHAR(50) NOT NULL UNIQUE,
    emitido_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT uq_certificados UNIQUE (usuario_id, curso_id),
    CONSTRAINT fk_certificados_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_certificados_curso FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

CREATE TABLE trilhas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(150) NOT NULL,
    descricao NVARCHAR(MAX) NULL,
    slug NVARCHAR(150) NOT NULL UNIQUE,
    thumbnail_url NVARCHAR(500) NULL,
    materia NVARCHAR(100) NULL,
    professor_id BIGINT NULL,
    xp_total INT NOT NULL DEFAULT 0,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_trilhas_professor FOREIGN KEY (professor_id) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE TABLE nos_trilha (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    trilha_id BIGINT NOT NULL,
    titulo NVARCHAR(150) NOT NULL,
    descricao NVARCHAR(MAX) NULL,
    tipo NVARCHAR(20) NOT NULL,
    ordem INT NOT NULL,
    xp_recompensa INT NOT NULL DEFAULT 10,
    nota_minima DECIMAL(5,2) NULL,
    max_tentativas INT NULL,
    icone NVARCHAR(50) NULL,
    ativo BIT NOT NULL DEFAULT 1,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_nos_trilha FOREIGN KEY (trilha_id) REFERENCES trilhas(id) ON DELETE CASCADE
);

CREATE TABLE conteudo_no (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    no_id BIGINT NOT NULL UNIQUE,
    tipo_conteudo NVARCHAR(20) NOT NULL,
    conteudo_url NVARCHAR(500) NULL,
    conteudo_texto NVARCHAR(MAX) NULL,
    CONSTRAINT fk_conteudo_no FOREIGN KEY (no_id) REFERENCES nos_trilha(id) ON DELETE CASCADE
);

CREATE TABLE questoes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    no_id BIGINT NOT NULL,
    enunciado NVARCHAR(MAX) NOT NULL,
    tipo NVARCHAR(30) NOT NULL,
    explicacao NVARCHAR(MAX) NULL,
    ordem INT NOT NULL,
    CONSTRAINT fk_questoes_no FOREIGN KEY (no_id) REFERENCES nos_trilha(id) ON DELETE CASCADE
);

CREATE TABLE alternativas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    questao_id BIGINT NOT NULL,
    texto NVARCHAR(500) NOT NULL,
    correta BIT NOT NULL DEFAULT 0,
    ordem INT NOT NULL,
    CONSTRAINT fk_alternativas_questao FOREIGN KEY (questao_id) REFERENCES questoes(id) ON DELETE CASCADE
);

CREATE TABLE progresso_nos (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    no_id BIGINT NOT NULL,
    status NVARCHAR(20) NOT NULL,
    nota_obtida DECIMAL(5,2) NULL,
    tentativas INT NOT NULL DEFAULT 0,
    xp_ganho INT NOT NULL DEFAULT 0,
    concluido_em DATETIME2 NULL,
    atualizado_em DATETIME2 NULL,
    CONSTRAINT uq_progresso_nos UNIQUE (usuario_id, no_id),
    CONSTRAINT fk_progresso_nos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_progresso_nos_no FOREIGN KEY (no_id) REFERENCES nos_trilha(id) ON DELETE CASCADE
);

CREATE INDEX ix_progresso_nos_usuario_status ON progresso_nos(usuario_id, status);

CREATE TABLE respostas_usuario (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    questao_id BIGINT NOT NULL,
    alternativa_id BIGINT NULL,
    resposta_texto NVARCHAR(500) NULL,
    correta BIT NOT NULL DEFAULT 0,
    tentativa_numero INT NOT NULL DEFAULT 1,
    respondido_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_respostas_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_respostas_usuario_questao FOREIGN KEY (questao_id) REFERENCES questoes(id) ON DELETE CASCADE,
    CONSTRAINT fk_respostas_usuario_alternativa FOREIGN KEY (alternativa_id) REFERENCES alternativas(id) ON DELETE NO ACTION
);

CREATE INDEX ix_respostas_usuario_questao ON respostas_usuario(usuario_id, questao_id);

CREATE TABLE projetos_enviados (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    no_id BIGINT NOT NULL,
    resposta_texto NVARCHAR(MAX) NULL,
    arquivo_url NVARCHAR(500) NULL,
    status NVARCHAR(20) NOT NULL,
    feedback_professor NVARCHAR(MAX) NULL,
    avaliado_por BIGINT NULL,
    enviado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    avaliado_em DATETIME2 NULL,
    CONSTRAINT fk_projetos_enviados_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_projetos_enviados_no FOREIGN KEY (no_id) REFERENCES nos_trilha(id) ON DELETE CASCADE,
    CONSTRAINT fk_projetos_enviados_avaliado_por FOREIGN KEY (avaliado_por) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE TABLE xp_usuario (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    xp_total INT NOT NULL DEFAULT 0,
    nivel INT NOT NULL DEFAULT 1,
    streak_atual INT NOT NULL DEFAULT 0,
    streak_maximo INT NOT NULL DEFAULT 0,
    ultimo_acesso DATE NULL,
    CONSTRAINT fk_xp_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE INDEX ix_xp_usuario_xp_total ON xp_usuario(xp_total);

CREATE TABLE conquistas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(100) NOT NULL,
    descricao NVARCHAR(255) NOT NULL,
    icone NVARCHAR(50) NULL,
    tipo NVARCHAR(30) NOT NULL,
    valor_meta INT NOT NULL
);

CREATE TABLE conquistas_usuario (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    conquista_id BIGINT NOT NULL,
    desbloqueado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT uq_conquistas_usuario UNIQUE (usuario_id, conquista_id),
    CONSTRAINT fk_conquistas_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_conquistas_usuario_conquista FOREIGN KEY (conquista_id) REFERENCES conquistas(id) ON DELETE CASCADE
);

CREATE TABLE certificados_trilha (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    trilha_id BIGINT NOT NULL,
    codigo_validacao NVARCHAR(50) NOT NULL UNIQUE,
    emitido_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT uq_certificados_trilha UNIQUE (usuario_id, trilha_id),
    CONSTRAINT fk_certificados_trilha_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_certificados_trilha_trilha FOREIGN KEY (trilha_id) REFERENCES trilhas(id) ON DELETE CASCADE
);

CREATE TABLE notificacoes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    titulo NVARCHAR(150) NOT NULL,
    mensagem NVARCHAR(500) NOT NULL,
    tipo NVARCHAR(30) NOT NULL,
    lida BIT NOT NULL DEFAULT 0,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_notificacoes_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE conversas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    tipo NVARCHAR(10) NOT NULL,
    trilha_id BIGINT NULL,
    nome NVARCHAR(100) NULL,
    criado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_conversas_trilha FOREIGN KEY (trilha_id) REFERENCES trilhas(id) ON DELETE NO ACTION
);

CREATE TABLE participantes_conversa (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    conversa_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    entrou_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    silenciado_ate DATETIME2 NULL,
    arquivado BIT NOT NULL DEFAULT 0,
    CONSTRAINT uq_participantes_conversa UNIQUE (conversa_id, usuario_id),
    CONSTRAINT fk_participantes_conversa_conversa FOREIGN KEY (conversa_id) REFERENCES conversas(id) ON DELETE CASCADE,
    CONSTRAINT fk_participantes_conversa_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE INDEX ix_participantes_conversa_usuario ON participantes_conversa(usuario_id);

CREATE TABLE mensagens (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    conversa_id BIGINT NOT NULL,
    remetente_id BIGINT NOT NULL,
    conteudo NVARCHAR(MAX) NOT NULL,
    tipo NVARCHAR(10) NOT NULL,
    anexo_url NVARCHAR(500) NULL,
    enviado_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    editado_em DATETIME2 NULL,
    deletado BIT NOT NULL DEFAULT 0,
    CONSTRAINT fk_mensagens_conversa FOREIGN KEY (conversa_id) REFERENCES conversas(id) ON DELETE CASCADE,
    CONSTRAINT fk_mensagens_remetente FOREIGN KEY (remetente_id) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE INDEX ix_mensagens_conversa_enviado_em ON mensagens(conversa_id, enviado_em DESC);
CREATE INDEX ix_mensagens_remetente ON mensagens(remetente_id);

CREATE TABLE mensagens_lidas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    mensagem_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    lido_em DATETIME2 NOT NULL DEFAULT GETDATE(),
    CONSTRAINT uq_mensagens_lidas UNIQUE (mensagem_id, usuario_id),
    CONSTRAINT fk_mensagens_lidas_mensagem FOREIGN KEY (mensagem_id) REFERENCES mensagens(id) ON DELETE CASCADE,
    CONSTRAINT fk_mensagens_lidas_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE NO ACTION
);

CREATE INDEX ix_mensagens_lidas_usuario ON mensagens_lidas(usuario_id);

CREATE TABLE presenca_usuarios (
    usuario_id BIGINT PRIMARY KEY,
    online BIT NOT NULL DEFAULT 0,
    ultimo_acesso_chat DATETIME2 NULL,
    CONSTRAINT fk_presenca_usuarios_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

INSERT INTO usuarios (nome, email, senha_hash, foto_url, bio, role, ativo) VALUES
('Joao Silva', 'joao@cursify.com', 'hash', NULL, 'Aluno da plataforma', 'USUARIO', 1),
('Maria Santos', 'maria@cursify.com', 'hash', NULL, 'Professora de matematica', 'PROFESSOR', 1),
('Ana Oliveira', 'ana@cursify.com', 'hash', NULL, 'Administradora da plataforma', 'ADMIN', 1);

INSERT INTO categorias (nome, slug, descricao, icone_url, ativo) VALUES
('Ciencias Exatas', 'ciencias-exatas', 'Matematica e logica', NULL, 1),
('Linguagens', 'linguagens', 'Leitura, escrita e interpretacao', NULL, 1);

INSERT INTO cursos (titulo, descricao, slug, thumbnail_url, nivel, categoria_id, professor_id, preco, gratuito, publicado, carga_horaria_minutos)
VALUES
('Matematica Basica', 'Curso completo para consolidar fundamentos', 'matematica-basica', NULL, 'INICIANTE', 1, 2, 0, 1, 1, 480),
('Escrita e Leitura', 'Curso de interpretacao e redacao', 'escrita-e-leitura', NULL, 'INTERMEDIARIO', 2, 2, 59.90, 0, 1, 360);

INSERT INTO trilhas (titulo, descricao, slug, thumbnail_url, materia, professor_id, xp_total, ativo)
VALUES
('Matematica Basica', 'Trilha gamificada de matematica', 'matematica-basica', NULL, 'Ciencias Exatas', 2, 900, 1),
('Escrita e Leitura', 'Trilha gamificada de linguagens', 'escrita-e-leitura', NULL, 'Linguagens', 2, 760, 1);

INSERT INTO modulos (curso_id, titulo, ordem) VALUES
(1, 'Fundamentos', 1),
(1, 'Aplicacoes', 2),
(2, 'Leitura critica', 1);

INSERT INTO aulas (modulo_id, titulo, tipo, conteudo_url, conteudo_texto, duracao_minutos, ordem, gratuita) VALUES
(1, 'Numeros e operacoes', 'VIDEO', NULL, NULL, 18, 1, 1),
(1, 'Fracoes e decimais', 'TEXTO', NULL, NULL, 22, 2, 0),
(2, 'Problemas do cotidiano', 'QUIZ', NULL, NULL, 20, 1, 0);

INSERT INTO nos_trilha (trilha_id, titulo, descricao, tipo, ordem, xp_recompensa, nota_minima, max_tentativas, icone, ativo) VALUES
(1, 'Numeros', NULL, 'LICAO', 1, 50, NULL, NULL, 'book', 1),
(1, 'Operacoes', NULL, 'EXERCICIO', 2, 70, NULL, NULL, 'pencil', 1),
(1, 'Fracoes', NULL, 'QUIZ', 3, 90, NULL, NULL, 'list-check', 1),
(1, 'Medidas', NULL, 'CHECKPOINT', 4, 120, 7.00, 3, 'star', 1),
(1, 'Geometria', NULL, 'PROJETO', 5, 140, NULL, NULL, 'clipboard', 1);

INSERT INTO conteudo_no (no_id, tipo_conteudo, conteudo_url, conteudo_texto) VALUES
(1, 'TEXTO', NULL, 'Introducao a numeros'),
(2, 'TEXTO', NULL, 'Exercicios de operacoes'),
(3, 'TEXTO', NULL, 'Quiz sobre fracoes');

INSERT INTO questoes (no_id, enunciado, tipo, explicacao, ordem) VALUES
(2, 'Quanto e 2 + 2?', 'MULTIPLA_ESCOLHA', 'A soma de dois mais dois e quatro.', 1),
(3, 'Escolha a fracao equivalente a 1/2.', 'MULTIPLA_ESCOLHA', 'Metade pode ser representada de varias formas equivalentes.', 1);

INSERT INTO alternativas (questao_id, texto, correta, ordem) VALUES
(1, '3', 0, 1),
(1, '4', 1, 2),
(2, '2/4', 1, 1),
(2, '3/5', 0, 2);

INSERT INTO xp_usuario (usuario_id, xp_total, nivel, streak_atual, streak_maximo, ultimo_acesso) VALUES
(1, 680, 4, 7, 12, CAST(GETDATE() AS DATE)),
(2, 1200, 7, 11, 15, CAST(GETDATE() AS DATE)),
(3, 2400, 10, 20, 20, CAST(GETDATE() AS DATE));

INSERT INTO conquistas (titulo, descricao, icone, tipo, valor_meta) VALUES
('Primeiro passo', 'Concluir a primeira licao', 'check', 'SEQUENCIA', 1),
('Ritmo constante', 'Manter streak de 7 dias', 'fire', 'STREAK', 7),
('Matematico', 'Ganhar 500 XP', 'star', 'XP', 500),
('Conversa ativa', 'Enviar 20 mensagens', 'messages', 'ESPECIAL', 20),
('Explorador', 'Concluir 3 trilhas', 'map', 'TRILHA', 3);

INSERT INTO conquistas_usuario (usuario_id, conquista_id) VALUES
(1, 1),
(1, 2);

INSERT INTO notificacoes (usuario_id, titulo, mensagem, tipo, lida) VALUES
(1, 'Nova mensagem', 'Maria respondeu na conversa da trilha', 'CHAT', 0),
(1, 'Streak mantida', 'Voce estudou por 7 dias seguidos', 'SISTEMA', 0),
(2, 'Novo curso', 'Matematica Basica foi publicado', 'CURSO', 1);

INSERT INTO conversas (tipo, trilha_id, nome) VALUES
('GRUPO', 1, 'Grupo da trilha de matematica'),
('DIRETO', NULL, 'Joao Silva x Maria Santos');

INSERT INTO participantes_conversa (conversa_id, usuario_id) VALUES
(1, 1), (1, 2), (2, 1), (2, 2);

INSERT INTO mensagens (conversa_id, remetente_id, conteudo, tipo) VALUES
(1, 2, 'Vamos fechar o checkpoint hoje?', 'TEXTO'),
(1, 1, 'Estou pronto para a ultima etapa.', 'TEXTO'),
(2, 1, 'Tenho duvida sobre fracoes.', 'TEXTO'),
(2, 2, 'Veja o nodo 3 e responda o quiz novamente.', 'TEXTO');

INSERT INTO mensagens_lidas (mensagem_id, usuario_id) VALUES
(1, 1),
(2, 2),
(3, 2);

INSERT INTO presenca_usuarios (usuario_id, online, ultimo_acesso_chat) VALUES
(1, 1, GETDATE()),
(2, 1, GETDATE()),
(3, 0, GETDATE());

SELECT * FROM usuarios
SELECT * FROM categorias
SELECT * FROM cursos
SELECT * FROM modulos
SELECT * FROM aulas
SELECT * FROM matriculas
SELECT * FROM progresso_aulas
SELECT * FROM avaliacoes
SELECT * FROM materiais
SELECT * FROM certificados
SELECT * FROM trilhas
SELECT * FROM nos_trilha
SELECT * FROM conteudo_no
SELECT * FROM questoes
SELECT * FROM alternativas
SELECT * FROM progresso_nos
SELECT * FROM respostas_usuario
SELECT * FROM projetos_enviados
SELECT * FROM xp_usuario
SELECT * FROM conquistas
SELECT * FROM conquistas_usuario
SELECT * FROM certificados_trilha
SELECT * FROM notificacoes
SELECT * FROM conversas
SELECT * FROM participantes_conversa
SELECT * FROM mensagens
SELECT * FROM mensagens_lidas
SELECT * FROM presenca_usuarios
