# Desafio API

This is a Spring Boot application that provides a RESTful API for the Desafio project.

## Features

- Account management
- Transaction processing
- Health check endpoints for monitoring

## Requirements

- Java 21
- MySQL 8.0+
- Maven 3.6+

## Setup

1. Clone the repository
2. Configure the database connection in `application.properties`
3. Run the application using Maven: `mvn spring-boot:run`

## API Documentation

The API documentation is available at `/swagger-ui.html` when the application is running. The documentation provides detailed information about all available endpoints, including:

- Request parameters and body schemas
- Response formats and status codes
- Example requests and responses
- Possible error responses

### Accessing the Documentation

1. Start the application using `mvn spring-boot:run`
2. Open a web browser and navigate to `http://localhost:8080/swagger-ui.html`

### API Endpoints Overview

The API is organized into the following categories:

#### Financial Management

- `POST /desafio/api/v1/user` - Create a new user
- `GET /desafio/api/v1/accountBalance/{accountID}` - Get account balance
- `POST /desafio/api/v1/account` - Create a new account
- `POST /desafio/api/v1/transaction/expense` - Create a new expense transaction
- `POST /desafio/api/v1/transaction/income` - Create a new income transaction
- `GET /desafio/api/v1/transactions/{accountID}` - Get all transactions for an account
- `GET /desafio/api/v1/transactionsByRange` - Get transactions by date range
- `GET /desafio/api/v1/improvements` - Get project improvement suggestions

#### Health Check

- `GET /health/liveness` - Liveness check
- `GET /health/readiness` - Readiness check

### Using the API

The API uses JSON for request and response bodies. All endpoints return appropriate HTTP status codes and structured error responses when applicable.

Example of creating a new user:

```bash
curl -X POST "http://localhost:8080/desafio/api/v1/user" \
     -H "Content-Type: application/json" \
     -d '{"name": "John Doe", "email": "john.doe@example.com"}'
```

Example of creating a new transaction:

```bash
curl -X POST "http://localhost:8080/desafio/api/v1/transaction/income" \
     -H "Content-Type: application/json" \
     -d '{"accountID": 1, "value": 100.00}'
```

## Health Check Endpoints

The application provides the following health check endpoints:

### Spring Boot Actuator Endpoints

- `/actuator/health` - Returns the overall health status of the application
- `/actuator/info` - Returns information about the application

### Custom Health Check Endpoints

- `/health/liveness` - Simple endpoint that returns 200 OK if the application is running
- `/health/readiness` - Returns the full health status, including database connectivity

These endpoints can be used for monitoring the application and for health checks in containerized environments like Kubernetes.

## Database Health Check

The application includes a custom database health indicator that checks the database connectivity. This is included in the `/actuator/health` and `/health/readiness` endpoints.

## Configuration

The health check endpoints can be configured in the `application.properties` file:

```properties
# Actuator Configuration
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
management.info.env.enabled=true
```

## Docker Support

The application includes a Dockerfile for containerization. To build and run the Docker image:

```bash
docker build -t desafio-api .
docker run -p 8080:8080 desafio-api
```

## Contributing

Please refer to the improvement suggestions in the `improvement_suggestions.md` file for areas that need improvement.
