-- Tabela cidade
CREATE TABLE cidade (
                        cid_id SERIAL PRIMARY KEY,
                        cid_nome VARCHAR(200) NOT NULL,
                        cid_uf CHAR(2) NOT NULL
);

-- Tabela endereco
CREATE TABLE endereco (
                          end_id SERIAL PRIMARY KEY,
                          end_tipo_logradouro VARCHAR(50),
                          end_logradouro VARCHAR(200),
                          end_numero INT,
                          end_bairro VARCHAR(100),
                          cid_id INT REFERENCES cidade(cid_id)
);

-- Tabela pessoa
CREATE TABLE pessoa (
                        pes_id SERIAL PRIMARY KEY,
                        pes_nome VARCHAR(200),
                        pes_data_nascimento DATE,
                        pes_sexo VARCHAR(9),
                        pes_mae VARCHAR(200),
                        pes_pai VARCHAR(200)
);

-- Tabela foto_pessoa
CREATE TABLE foto_pessoa (
                             fp_id SERIAL PRIMARY KEY,
                             pes_id INT REFERENCES pessoa(pes_id),
                             fp_data DATE,
                             fp_bucket VARCHAR(50),
                             fp_hash VARCHAR(50)
);

-- Tabela pessoa_endereco
CREATE TABLE pessoa_endereco (
                                 pes_id INT REFERENCES pessoa(pes_id),
                                 end_id INT REFERENCES endereco(end_id),
                                 PRIMARY KEY (pes_id, end_id)
);

-- Tabela servidor_efetivo
CREATE TABLE servidor_efetivo (
                                  pes_id INT PRIMARY KEY REFERENCES pessoa(pes_id),
                                  se_matricula VARCHAR(20)
);

-- Tabela servidor_temporario
CREATE TABLE servidor_temporario (
                                     pes_id INT PRIMARY KEY REFERENCES pessoa(pes_id),
                                     st_data_admissao DATE,
                                     st_data_demissao DATE
);

-- Tabela unidade
CREATE TABLE unidade (
                         unid_id SERIAL PRIMARY KEY,
                         unid_nome VARCHAR(200),
                         unid_sigla VARCHAR(20)
);

-- Tabela unidade_endereco
CREATE TABLE unidade_endereco (
                                  unid_id INT REFERENCES unidade(unid_id),
                                  end_id INT REFERENCES endereco(end_id),
                                  PRIMARY KEY (unid_id, end_id)
);

-- Tabela lotacao
CREATE TABLE lotacao (
                         lot_id SERIAL PRIMARY KEY,
                         pes_id INT REFERENCES pessoa(pes_id),
                         unid_id INT REFERENCES unidade(unid_id),
                         lot_data_lotacao DATE,
                         lot_data_remocao DATE,
                         lot_portaria VARCHAR(100)
);

CREATE TABLE usuario (
                         id SERIAL PRIMARY KEY,
                         login VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         role VARCHAR(50) NOT NULL
);

INSERT INTO unidade (unid_nome, unid_sigla) VALUES ('UNIDADE SEPLAG','US');
INSERT INTO unidade (unid_nome, unid_sigla) VALUES ('UNIDADE TESTE','UT');

INSERT INTO cidade (cid_nome, cid_uf) VALUES ('CUIABÁ','MT');

INSERT INTO endereco (end_tipo_logradouro, end_logradouro, end_numero, end_bairro, cid_id) VALUES ('BLOCO','Bloco III - Complexo Paiaguás',3,'Centro Político Administrativo',1);
INSERT INTO unidade_endereco (unid_id, end_id) VALUES (1,1);
INSERT INTO unidade_endereco (unid_id, end_id) VALUES (2,1);

CREATE EXTENSION IF NOT EXISTS unaccent;
