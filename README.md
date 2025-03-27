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

---

## 📦 Endpoints disponíveis

### 🔹 `POST /servidores-efetivos`
Cria um novo servidor efetivo com envio de imagem.

**Body (form-data):**
| Campo              | Tipo  | Descrição                                      |
|--------------------|--------|-------------------------------------------------|
| nome               | Text  | Nome completo                                   |
| matricula          | Text  | Matrícula do servidor                           |
| pesDataNascimento  | Text  | Data de nascimento (formato yyyy-MM-dd)         |
| pesSexo            | Text  | Sexo (M/F)                                      |
| pesMae             | Text  | Nome da mãe                                     |
| pesPai             | Text  | Nome do pai                                     |
| cidade             | Text  | Cidade de endereço funcional                    |
| uf                 | Text  | UF da cidade                                    |
| tipoLogradouro     | Text  | Tipo do logradouro (ex: Rua, Avenida)           |
| logradouro         | Text  | Nome da rua                                     |
| numero             | Text  | Número do endereço                              |
| bairro             | Text  | Bairro do endereço                              |
| unidadeId          | Text  | ID da unidade de lotação                        |
| foto               | File  | Arquivo de imagem (opcional)                    |

**Exemplo no Postman:**

Preencha os campos na aba **Body > form-data**:

```
nome: João Silva
matricula: 123456
pesDataNascimento: 1990-05-12
pesSexo: M
pesMae: Maria da Silva
pesPai: José da Silva
cidade: Cuiabá
uf: MT
tipoLogradouro: Rua
logradouro: das Flores
numero: 100
bairro: Centro
unidadeId: 1
foto: (selecione uma imagem do seu computador)
```

---

### 🔹 `GET /servidores-efetivos/listar`
Lista os servidores efetivos com paginação e filtros dinâmicos.

**Parâmetros disponíveis:**
- `seMatricula` (LIKE)
- `pessoa.pesNome` (LIKE)

**Exemplo de chamada:**
```
GET /servidores-efetivos/listar?seMatricula=123&page=0&size=10
```

Cada item retornado inclui:
- Nome
- Matrícula
- Idade (calculada a partir da data de nascimento)
- URL temporária da foto (expira em 5 minutos)

---

## ☁️ MinIO - Armazenamento de Fotos

Fotos são armazenadas no MinIO e acessadas por meio de links temporários (válidos por 5 minutos).

- O bucket padrão é `fotos`
- Os arquivos são enviados via `MinioService`
- O hash do arquivo é salvo na tabela `foto_pessoa`
- A URL é gerada automaticamente no DTO usando o último registro da pessoa

---

## 🗃️ Estrutura dos diretórios principais

```bash
src/main/java
└── br/gov/mt/seplag/api
    ├── controller
    ├── dto
    ├── form
    ├── mapper
    ├── model
    ├── repository
    ├── service
    └── specifications
```

---

## 🔒 Segurança

Autenticação via JWT será implementada com expiração a cada 5 minutos e suporte à renovação de token.

---

## 🛠️ Autor e contato

Desenvolvido por **Arão Farias**

📧 Email: seu-email@dominio.com
🔗 GitHub: [github.com/seu-usuario](https://github.com/seu-usuario)

---

> Sinta-se à vontade para abrir issues ou contribuir com melhorias no projeto!
