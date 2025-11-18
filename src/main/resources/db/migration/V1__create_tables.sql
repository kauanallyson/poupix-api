CREATE TABLE IF NOT EXISTS lojas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(100),
    favorito BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS compras  (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    loja_id INTEGER NOT NULL,
    preco DECIMAL NOT NULL,
    data DATE NOT NULL,
    formaDePagamento VARCHAR(16) NOT NULL CHECK (
        formaDePagamento IN ('PIX', 'DINHEIRO', 'DEBITO', 'CREDITO')
    ),
    FOREIGN KEY (loja_id) REFERENCES lojas(id)
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);