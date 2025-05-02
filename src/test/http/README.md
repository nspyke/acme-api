# Testing the Widget API

This directory contains HTTP request files that can be used to test the Widget API endpoints manually.

## Prerequisites

- The Spring Boot application must be running
- An HTTP client that can execute HTTP request files (e.g., IntelliJ IDEA's HTTP Client, VS Code's REST Client, or Postman)

## Starting the Application

To start the Spring Boot application, run the following command from the project root directory:

```bash
./gradlew bootRun
```

Or if you're using Windows:

```cmd
gradlew.bat bootRun
```

The application will start on port 8080 by default.

## Testing the Endpoints

### Basic Tests

The `widget-api-tests.http` file contains requests for testing all the Widget API endpoints:

1. **Create a new widget** - POST request to `/api/v1/widgets`
2. **Get all widgets** - GET request to `/api/v1/widgets`
3. **Get widget by ID** - GET request to `/api/v1/widgets/{id}` (replace `{id}` with an actual ID)
4. **Update a widget** - PUT request to `/api/v1/widgets/{id}` (replace `{id}` with an actual ID)
5. **Search widgets by name** - GET request to `/api/v1/widgets?name={name}`
6. **Search widgets by description** - GET request to `/api/v1/widgets?description={description}`
7. **Search widgets by name and description** - GET request to `/api/v1/widgets?name={name}&description={description}`

### Advanced Tests

The `widget-api-advanced-tests.http` file contains more complex testing scenarios:

1. **Create multiple widgets** - Creates three different widgets with varying data
2. **Test search functionality** - Tests searching widgets using query parameters on the getAll endpoint
3. **Update and verify** - Updates a widget and verifies the changes
4. **Search updated data** - Tests search functionality with the updated data

### Execution Order

For best results, execute the requests in the following order:

1. Create a new widget (POST)
2. Get all widgets (GET) to verify the widget was created and to see its ID
3. Get the widget by ID (GET) using the ID from the previous response
4. Update the widget (PUT) using the same ID
5. Get all widgets again to verify the update
6. Search widgets by name using query parameters
7. Search widgets by description using query parameters
8. Search widgets by both name and description using query parameters

### Using IntelliJ IDEA's HTTP Client

If you're using IntelliJ IDEA:

1. Open the `widget-api-tests.http` file
2. Click the green "Run" button next to each request to execute it
3. View the response in the "Response" tab

### Using VS Code's REST Client

If you're using VS Code with the REST Client extension:

1. Open the `widget-api-tests.http` file
2. Click "Send Request" above each request to execute it
3. View the response in the response pane

### Using Postman

If you prefer Postman:

1. Import the requests from the HTTP file or create them manually
2. Execute each request and view the response
