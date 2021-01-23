# Polson beershop backend

## Environment variables

### Database connection

|Key |Value |
|---------------------------|-----------------------------------|
|POLSON_DB_URL|jdbc:mysql://localhost/polson?createDatabaseIfNotExist=true&serverTimezone=UTC |
|POLSON_DB_USER|Your db username|
|POLSON_DB_PWD|Your db password|
|POLSON_DB_NAME|polson|
|POLSON_HIBERNATE_DIALECT|org.hibernate.dialect.MySQL5Dialect|

# Final Exam Homework Technical Requirements

Write an application with any features, that implements a REST API and stores data in a database. If you don't have any idea to create an application, you can follow [this specification](https://github.com/green-fox-academy/final-exam-homework).

## Common requirements

### Functional requirements

- Register a new user
- Login user
- Saving entries
- Listing entries by some query parameters
- Get entry by id
- Modify entry
- Delete entry

### Version control

- Use a separate git repository for the project
- Meaningful commits of the functionalities
- The repository is available on a git hosting service (GitHub, GitLab, ...)

### Formatting

- The code is well formatted, follows a styleguide (preferably Google's)
- Use linter for style checking

### (Optional) CI

- Use a CI tool (CircleCI, Jenkins, Github Actions, ...) to automatize your lint, build and test processes

### (Optional) Deployment

- Deploy your application to a hosting service

## Backend requirements

### API

- Create a REST API that satisfies the given functional requirements
- Optional: some actions are available only with certain roles (e.g. ADMIN)

### Auth

- Your API should contain protected endpoints that should be authorized with a valid JWT Token
- Use symmetric key to generate tokens with validation date
- The application is validating the token from the request
- The application endpoints return `401` when the token is invalid or missing
- Optional: the application endpoints return `403` when the token belongs to a user who doesn't have permission to execute the requested action

### Database

- The application should connect to an SQL database, and store its data there
- The database connection details should not be "hardcoded" into the application, it should be read from envrionment variables.
- The database schema should contain connections between tables (e.g. one-to-many, many-to-many)
- Use a data migration tool to handle the changes of your DB schema

### Tests

- You have a test environment where you connect to a test database (preferably inmemory database)
- You have unit tests
- You have integration tests
- You use mocking

### (Optional) Mapping

- Separate the Entity (DB) Models from the ones you gave back in the response.
- Create DTO models and use a mapper to conversion and vica-versa.

### (Optional) Docker

- Dockerize your application
- Your app should start with a single `docker-compose up` command

## Frontend requirements

### UI

- Use a JS library / framework (React, Angular, Vue, ...) to build your UI
- Create a UI that satisfies the given functional requirements
- Create and use reusable UI components

### Styling

- Use a preprocessor (scss, sass, less, ...) to write more maintainable stylesheets
- Optional: your design is responsive
- Optional: use a UI library (Bootstrap, Material UI, Ant Design, ...)

### Auth

- Implement a token based authentication flow
- Optional: use a 3rd party authentication provider (Google, Facebook, Github, ...)

### Client - server communication

- Your application is using REST API(s) to fetch all dynamic data of the application
- Your application is using REST API(s) to store all new user input
- Your application is using JWT token to reach the protected API endpoints

### State management

- Use Redux for state management
- Optional: Use a Redux middleware (Thunk, Saga, ...)

### Tests

- You have unit tests
- You have integration tests
- You have component tests
