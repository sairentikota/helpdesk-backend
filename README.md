# Help Desk Backend

RESTful backend for the Help Desk & Ticket Management System built with Java 21, Spring Boot 3, Spring Security (JWT), Spring Data JPA, PostgreSQL, and Flyway.

## Features
- **Authentication & RBAC**: JWT-based stateless authentication with `USER`, `AGENT`, and `ADMIN` roles.
- **Ticket Lifecycle**: Create, view, update status (`OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`), assign agents, and add comments.
- **Search & Filtering**: Filter by status, priority, category, assigned agent, date range, and pagination.
- **Database Migrations**: Automatic schema creation and seed data using Flyway.
- **Swagger / OpenAPI**: Interactive API documentation at `/swagger-ui/index.html`.

## Prerequisites
- Java 21
- Maven (or use Maven wrapper)
- PostgreSQL

## Environment Variables
| Variable | Description | Default |
|----------|-------------|---------|
| `PORT` | HTTP Server port | `8080` |
| `DATABASE_URL` | JDBC connection URL | `jdbc:postgresql://localhost:5432/helpdesk` |
| `DATABASE_USERNAME` | Database username | `postgres` |
| `DATABASE_PASSWORD` | Database password | `postgres` |
| `JWT_SECRET` | Secret key for JWT signing (32+ chars) | *(Required in production)* |
| `FRONTEND_URL` | Allowed CORS origin | `http://localhost:5173` |

## Run Locally
```bash
./mvnw clean spring-boot:run
```

## Docker
```bash
docker build -t helpdesk-backend .
docker run -p 8080:8080 helpdesk-backend
```
