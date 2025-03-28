# 📘 API - Gestão de Servidores

API RESTful desenvolvida em Java Spring Boot com PostgreSQL, MinIO e Specification para gerenciamento de Servidores Efetivos, Temporários, Unidades e Lotações.

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
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

## 📦 Endpoints disponíveis

### 🔹 `POST /servidores-efetivos`
Cria um novo servidor efetivo com imagem em Base64.

**Exemplo de JSON:**
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

---

### 🔹 `PUT /servidores-efetivos/{id}`
Atualiza os dados de um servidor efetivo existente (aceita nova foto Base64).

### 🔹 `GET /servidores-efetivos/listar`
Lista os servidores efetivos com paginação e filtros dinâmicos.

### 🔹 `GET /servidores-efetivos/unidade/{unidId}`
Consulta todos os servidores efetivos lotados em uma unidade específica.

### 🔹 `GET /servidores-efetivos/endereco-funcional?nome={parteDoNome}`
Consulta o endereço funcional da unidade onde o servidor está lotado.

### 🔹 `DELETE /servidores-efetivos/{id}`
Remove um servidor efetivo por ID.

---

### 🔹 `POST /servidores-temporarios`
Cria um novo servidor temporário com imagem em Base64.

**Exemplo de JSON:**
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
Lista os servidores temporários com paginação e filtros.

### 🔹 `GET /servidores-temporarios/{id}`
Busca um servidor temporário por ID.

### 🔹 `DELETE /servidores-temporarios/{id}`
Remove um servidor temporário por ID.

> Todos os endpoints podem ser testados diretamente na interface Swagger.

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
    ├── services
    └── specification
```

---

## 🔒 Segurança

Autenticação via JWT será implementada com expiração a cada 5 minutos e suporte à renovação de token.

---

## 📚 Documentação Swagger

O projeto já inclui o Swagger OpenAPI 3. Acesse:
```
http://localhost:8080/swagger-ui/index.html
```

Você pode testar todos os endpoints diretamente pela interface web.

> Caso precise customizar título/descrição, edite a configuração da OpenAPI no arquivo `OpenAPIConfig` (caso tenha).

---

## 🛠️ Autor e contato

Desenvolvido por **Arão Farias**

📧 Email: seu-email@dominio.com
🔗 GitHub: [github.com/seu-usuario](https://github.com/seu-usuario)

---

> Sinta-se à vontade para abrir issues ou contribuir com melhorias no projeto!
