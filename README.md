# 📘 API - Gestão de Servidores

API RESTful desenvolvida em Java Spring Boot com PostgreSQL, MinIO, JWT e Specification para gerenciamento de Servidores Efetivos, Temporários, Unidades e Lotações.

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot 3.0.7
- Spring Data JPA
- Spring Security 6 (com JWT)
- PostgreSQL
- MinIO (Armazenamento de fotos)
- Docker + Docker Compose
- MapStruct (conversão entre DTO e Model)
- specification-arg-resolver (`net.kaczmarzyk`) para filtros dinâmicos e paginação
- Swagger OpenAPI 3 (documentação interativa)

---

## 🧱 Como executar o projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/nome-do-projeto.git
cd nome-do-projeto
```

### 2. Configurar `.env`
Crie um arquivo `.env` na raiz com o conteúdo:
```env
POSTGRES_DB=servidores_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_ADM_EMAIL=admin@admin.com
POSTGRES_ADM_PASSWORD=admin123

MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=minioadmin
```

### 3. Subir containers
```bash
docker-compose up -d
```
Acesse:
- MinIO: http://localhost:9001 (login: `minioadmin` / `minioadmin`)
- pgAdmin (opcional): http://localhost:16543

### 4. Rodar o projeto Spring Boot
Você pode usar sua IDE ou:
```bash
./mvnw spring-boot:run
```

### 5. Acessar a documentação da API (Swagger UI)
Acesse:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Autenticação com JWT

- A autenticação é feita via JWT (token expira em 5 minutos)
- Para gerar o token, use o endpoint:

### 🔹 `POST /auth/login`

```json
{
  "login": "admin",
  "senha": "admin"
}
```

> Um usuário padrão `admin` com senha `admin` é criado automaticamente ao iniciar a aplicação, caso não exista.

### 🔹 `POST /usuarios`
Cadastra um novo usuário com senha criptografada:

```json
{
  "login": "joao",
  "senha": "senha123",
  "role": "USER"
}
```

Use o token retornado no login para autenticar nas demais rotas:

```
Authorization: Bearer <seu_token_aqui>
```

---

## 📦 Endpoints disponíveis

### 🔹 `POST /servidores-efetivos`
Cria um novo servidor efetivo com imagem em Base64.

```json
{
  "nome": "João Silva",
  "matricula": "123456",
  "pesDataNascimento": "1990-05-12",
  "pesSexo": "M",
  "pesMae": "Maria da Silva",
  "pesPai": "José da Silva",
  "cidade": "Cuiabá",
  "uf": "MT",
  "tipoLogradouro": "Rua",
  "logradouro": "das Flores",
  "numero": 100,
  "bairro": "Centro",
  "unidadeId": 1,
  "fotoBase64": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABVYAAAJ..."
}
```

### 🔹 `PUT /servidores-efetivos/{id}`
Atualiza os dados de um servidor efetivo (também aceita nova imagem Base64).

### 🔹 `GET /servidores-efetivos/listar`
Lista os servidores efetivos com paginação e filtros.

### 🔹 `GET /servidores-efetivos/unidade/{unidId}`
Consulta servidores efetivos por unidade.

### 🔹 `GET /servidores-efetivos/endereco-funcional?nome={parteDoNome}`
Consulta endereço funcional da unidade por nome do servidor.

### 🔹 `DELETE /servidores-efetivos/{id}`
Remove um servidor efetivo por ID.

---

### 🔹 `POST /servidores-temporarios`
Cria um novo servidor temporário com imagem em Base64.

```json
{
  "nome": "Carlos da Silva",
  "matricula": "TEMP12345",
  "pesDataNascimento": "1985-02-14",
  "pesSexo": "M",
  "pesMae": "Maria da Silva",
  "pesPai": "José da Silva",
  "cidade": "Várzea Grande",
  "uf": "MT",
  "tipoLogradouro": "Avenida",
  "logradouro": "Dom Bosco",
  "numero": 1023,
  "bairro": "Centro",
  "unidadeId": 2,
  "dataAdmissao": "2023-01-01",
  "dataDemissao": "2023-12-31",
  "fotoBase64": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABVYAAAJ..."
}
```

### 🔹 `PUT /servidores-temporarios/{id}`
Atualiza um servidor temporário.

### 🔹 `GET /servidores-temporarios/listar`
Lista servidores temporários com paginação e filtros.

### 🔹 `GET /servidores-temporarios/{id}`
Consulta servidor temporário por ID.

### 🔹 `DELETE /servidores-temporarios/{id}`
Remove um servidor temporário por ID.

---

### 🔹 `POST /unidades`
Cria uma nova unidade:
```json
{ "unidNome": "Secretaria de Administração" }
```

### 🔹 `GET /unidades/listar`
Lista unidades com paginação e filtros.

### 🔹 `GET /unidades/{id}`
Consulta unidade por ID.

### 🔹 `PUT /unidades/{id}`
Atualiza dados da unidade.

### 🔹 `DELETE /unidades/{id}`
Remove uma unidade por ID.

---

### 🔹 `POST /lotacoes`
Cria uma nova lotação:
```json
{
  "pessoaId": 4,
  "unidadeId": 2,
  "lotDataLotacao": "2024-10-01",
  "lotDataRemocao": null,
  "lotPortaria": "PORTARIA Nº 1234"
}
```

### 🔹 `GET /lotacoes/listar`
Lista todas as lotações com paginação e filtros.

### 🔹 `GET /lotacoes/{id}`
Consulta uma lotação pelo ID.

### 🔹 `PUT /lotacoes/{id}`
Atualiza dados de uma lotação.

### 🔹 `DELETE /lotacoes/{id}`
Remove uma lotação por ID.

> Todos os endpoints podem ser testados diretamente via Swagger UI.

---

## ☁️ MinIO - Armazenamento de Fotos

Fotos são armazenadas no MinIO e acessadas por meio de links temporários (válidos por 5 minutos).

- O bucket padrão é `fotos`
- As imagens agora são enviadas em Base64 (campo `fotoBase64`)
- O hash do arquivo é salvo na tabela `foto_pessoa`
- A URL é gerada automaticamente no DTO usando o último registro da pessoa

---

## 🗃️ Estrutura dos diretórios principais

```bash
src/main/java
└── br/gov/mt/seplag/api
    ├── config
    ├── controller
    ├── dto
    ├── form
    ├── mapper
    ├── model
    ├── repository
    ├── security
    ├── services
    └── specification
```

---

## 📚 Documentação Swagger

O projeto já inclui o Swagger OpenAPI 3. Acesse:
```
http://localhost:8080/swagger-ui/index.html
```

Você pode testar todos os endpoints diretamente pela interface web.

---

## 🛠️ Autor e contato

Desenvolvido por **Arão Farias**

📧 Email: seu-email@dominio.com
🔗 GitHub: [github.com/seu-usuario](https://github.com/seu-usuario)

---

> Sinta-se à vontade para abrir issues ou contribuir com melhorias no projeto!
