# ACME API

ACME API is a Spring Boot application that provides RESTful endpoints for managing Widgets and Doodads for ACME Corporation.

## Prerequisites

- Java 21 or higher
- Gradle (or use the included Gradle wrapper)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/nspyke/acme-api.git
cd acme-api
```

### Build the Application

```bash
./gradlew build
```

### Run the Application

```bash
./gradlew bootRun
```

The application will start on port 8080 by default. You can access the API at `http://localhost:8080`.

## Database

The application uses an in-memory H2 database, which means data will be lost when the application is restarted. The H2 console is enabled and can be accessed at:

```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:acmedb`
- Username: `sa`
- Password: `password`

## API Documentation

The API is documented using OpenAPI 3.0. The full OpenAPI specification is available in the `openapi.yaml` file.

### API Endpoints

#### Widgets

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/v1/widgets` | Get all widgets (with pagination and filtering) |
| GET    | `/api/v1/widgets/{id}` | Get a widget by ID |
| POST   | `/api/v1/widgets` | Create a new widget |
| PUT    | `/api/v1/widgets/{id}` | Update an existing widget |

Query parameters for GET `/api/v1/widgets`:
- `name`: Filter widgets by name (case-insensitive, partial match)
- `description`: Filter widgets by description (case-insensitive, partial match)
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10, max: 100)

#### Doodads

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/v1/doodads` | Get all doodads (with pagination and filtering) |
| GET    | `/api/v1/doodads/{id}` | Get a doodad by ID |
| POST   | `/api/v1/doodads` | Create a new doodad |
| PUT    | `/api/v1/doodads/{id}` | Update an existing doodad |

Query parameters for GET `/api/v1/doodads`:
- `name`: Filter doodads by name (case-insensitive, partial match)
- `description`: Filter doodads by description (case-insensitive, partial match)
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10, max: 100)

### Example Requests

#### Create a Widget

```bash
curl -X POST http://localhost:8080/api/v1/widgets \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Super Widget",
    "description": "A fantastic widget for all your needs",
    "image": "widget.jpg"
  }'
```

#### Create a Doodad

```bash
curl -X POST http://localhost:8080/api/v1/doodads \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Amazing Doodad",
    "description": "The most amazing doodad you will ever see",
    "image": "doodad.jpg",
    "price": 19.99
  }'
```

## Error Handling

The API uses standard HTTP status codes and provides detailed error responses following RFC 7807 (Problem Details for HTTP APIs). Common status codes:

- 200 OK: The request was successful
- 201 Created: A new resource was created successfully
- 400 Bad Request: The request contains invalid parameters
- 404 Not Found: The requested resource could not be found
- 422 Unprocessable Entity: The request contains validation errors
- 500 Internal Server Error: An unexpected error occurred

## Development

### Code Formatting

The project uses Spotless with the Palantir Java formatter. To format the code:

```bash
./gradlew spotlessApply
```

### Running Tests

```bash
./gradlew test
```

## Continuous Integration

This project uses GitHub Actions for continuous integration. The CI workflow automatically builds and tests the application on all branches.

The workflow includes:
- Building the application
- Running all tests
- Uploading test results as artifacts

You can view the CI workflow configuration in the `.github/workflows/ci.yml` file.
