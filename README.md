# 🛒 API de Autenticação e Gestão de Produtos

Este projeto é uma API desenvolvida em **Java + Spring Boot**, com autenticação via **JWT** e um **CRUD completo de produtos**.  
O banco de dados utilizado é **PostgreSQL** e pode ser iniciado facilmente com **Docker Compose**.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker Compose**
- **Swagger/OpenAPI** para documentação

---

## 📦 Estrutura das Funcionalidades

### 1. Autenticação JWT
- **Login (`POST /api/auth/login`)**: autentica o usuário e retorna um token JWT
- **Middleware de validação JWT**: protege as rotas privadas
- **Registro de usuários (`POST /api/auth/register`)** com validação de e-mail duplicado

### 2. CRUD de Produtos
- **POST /api/produtos** → cria um novo produto
- **GET /api/produtos** → lista todos os produtos
- **GET /api/produtos/{id}** → busca um produto pelo ID
- **PUT /api/produtos/{id}** → atualiza um produto existente
- **DELETE /api/produtos/{id}** → remove um produto

---

## 🛠 Pré-requisitos

Antes de começar, instale:

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

---

## 🐘 Subindo o Banco de Dados com Docker Compose

Crie um arquivo chamado **`docker-compose.yml`** na raiz do projeto:

```yml
version: "3.8"

services:
  db:
    image: postgres:15
    container_name: apirest_db
    restart: always
    environment:
      POSTGRES_DB: apirest
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## Para subir o banco, execute:

```bash
docker-compose up -d
```

## ⚙️ Configuração do `application.properties`

```properties
spring.application.name=apirest
server.port=8085

spring.datasource.url=jdbc:postgresql://localhost:5432/apirest
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# JWT
jwt.secret=3E6G3f4oQfyFb3kgmF6tqL8a2Ow5mMObpgR4H1fw6u0DN+5oUpy2nB7vVQdK9eqUoDpFTZoxWWoH2u88TfIu0Q==
# 1 hora (em ms)
jwt.expiration-ms=3600000
```

## ▶️ Executando o Projeto

```bash
mvn spring-boot:run
```

## 📜 Documentação Swagger

Após rodar o projeto, acesse:

```bash
http://localhost:8085/swagger-ui.html
```

## 📄 Licença
Este projeto é de uso livre para fins de estudo e aprendizado.