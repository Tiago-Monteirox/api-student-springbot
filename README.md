# API Student

API REST simples para gerenciamento de alunos, desenvolvida com **Java**, **Spring Boot**, **Spring Data JPA** e **MySQL**.

## Tecnologias

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- MySQL 8
- Maven
- JUnit 5
- Mockito
- MockMvc
- Docker Compose

## Objetivo

Este projeto foi criado para praticar operações CRUD de alunos:

- Cadastrar aluno
- Listar alunos
- Buscar aluno por id
- Atualizar aluno
- Deletar aluno

## Estrutura do projeto

```text
src/
 ├── main/
 │   ├── java/br/edu/imepac/api_student/
 │   │   ├── controllers/
 │   │   ├── dto/
 │   │   ├── entities/
 │   │   ├── exceptions/
 │   │   ├── repositories/
 │   │   ├── services/
 │   │   └── ApiStudentApplication.java
 │   └── resources/
 │       └── application.properties
 └── test/
     └── java/br/edu/imepac/api_student/
```

## Como executar o projeto

### 1. Subir o banco com Docker

```bash
docker compose up -d
```

Verifique se o container está rodando:

```bash
docker ps
```

### 2. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Configuração do banco

Exemplo de configuração no `application.properties`:

```properties
spring.application.name=api-student

spring.datasource.url=jdbc:mysql://localhost:4040/academico
spring.datasource.username=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Endpoints da API

### Cadastrar aluno

**POST** `/students`

#### Body

```json
{
  "nome": "Artur Monteiro",
  "email": "artur@email.com"
}
```

#### Resposta

```json
{
  "message": "Aluno cadastrado com sucesso.",
  "data": {
    "id": 1,
    "nome": "Artur Monteiro",
    "email": "artur@email.com"
  }
}
```

---

### Listar todos os alunos

**GET** `/students`

#### Resposta

```json
[
  {
    "id": 1,
    "nome": "Artur Monteiro",
    "email": "artur@email.com"
  }
]
```

---

### Buscar aluno por id

**GET** `/students/{id}`

#### Exemplo

```http
GET /students/1
```

#### Resposta

```json
{
  "id": 1,
  "nome": "Artur Monteiro",
  "email": "artur@email.com"
}
```

---

### Atualizar aluno

**PUT** `/students/{id}`

#### Body

```json
{
  "nome": "Artur Monteiro Atualizado",
  "email": "arturnovo@email.com"
}
```

#### Resposta

```json
{
  "message": "Aluno atualizado com sucesso.",
  "data": {
    "id": 1,
    "nome": "Artur Monteiro Atualizado",
    "email": "arturnovo@email.com"
  }
}
```

---

### Deletar aluno

**DELETE** `/students/{id}`

#### Resposta

```json
{
  "message": "Aluno Artur Monteiro foi deletado com sucesso.",
  "data": null
}
```

## Tratamento de erro

Quando um aluno não é encontrado, a API retorna **404 Not Found**.

### Exemplo de resposta

```json
{
  "message": "Aluno com id 99 Não foi encontrado.",
  "data": null
}
```

## Como testar no Insomnia

### Atualizar aluno

- **Método:** `PUT`
- **URL:** `http://localhost:8080/students/1`
- **Body JSON:**

```json
{
  "nome": "Novo Nome",
  "email": "novo@email.com"
}
```

### Deletar aluno

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/students/1`

## Como testar com curl

### Criar aluno

```bash
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"nome":"Artur","email":"artur@email.com"}'
```

### Listar alunos

```bash
curl http://localhost:8080/students
```

### Buscar por id

```bash
curl http://localhost:8080/students/1
```

### Atualizar aluno

```bash
curl -X PUT http://localhost:8080/students/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Artur Atualizado","email":"arturnovo@email.com"}'
```

### Deletar aluno

```bash
curl -X DELETE http://localhost:8080/students/1
```

## Testes automatizados

Para executar os testes unitários:

```bash
./mvnw test
```

A suíte cobre:

- Regras do service
- Fluxos do controller
- Casos de sucesso
- Casos de erro
- Respostas HTTP da API

## Autor

Projeto acadêmico para estudo de API REST com Spring Boot.
