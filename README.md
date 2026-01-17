# Task Management App

A comprehensive task management application built with Java Spring Boot and PostgreSQL for monitoring employee tasks and projects.

## Features

- **Employee Management**: Create, read, update, and delete employee records
- **Project Management**: Organize work into projects with different statuses (Planning, In Progress, Completed, On Hold, Cancelled)
- **Task Management**: Create and assign tasks to employees with priorities and due dates
- **Task Tracking**: Track task statuses (To Do, In Progress, In Review, Completed, Blocked)
- **RESTful API**: Complete REST API for all operations

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **PostgreSQL 15**
- **Maven**
- **Lombok**

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for running PostgreSQL)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/00limited/task-management-app.git
cd task-management-app
```

### 2. Start PostgreSQL Database

Using Docker Compose:

```bash
docker-compose up -d
```

This will start a PostgreSQL database on port 5432 with:
- Database name: `taskmanagement`
- Username: `postgres`
- Password: `postgres`

### 3. Build the Application

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Employee Endpoints

- `GET /api/employees` - Get all employees
- `GET /api/employees/{id}` - Get employee by ID
- `GET /api/employees/email/{email}` - Get employee by email
- `POST /api/employees` - Create new employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### Project Endpoints

- `GET /api/projects` - Get all projects
- `GET /api/projects/{id}` - Get project by ID
- `GET /api/projects/status/{status}` - Get projects by status
- `POST /api/projects` - Create new project
- `PUT /api/projects/{id}` - Update project
- `DELETE /api/projects/{id}` - Delete project

### Task Endpoints

- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/project/{projectId}` - Get tasks by project
- `GET /api/tasks/employee/{employeeId}` - Get tasks by employee
- `GET /api/tasks/status/{status}` - Get tasks by status
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

## Example API Requests

### Create an Employee

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "position": "Software Developer"
  }'
```

### Create a Project

```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Website Redesign",
    "description": "Complete redesign of company website",
    "status": "PLANNING"
  }'
```

### Create a Task

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Design homepage mockup",
    "description": "Create initial mockup for new homepage",
    "status": "TODO",
    "priority": "HIGH",
    "project": {"id": 1},
    "assignedTo": {"id": 1}
  }'
```

## Database Schema

### Employee Table
- `id` (Primary Key)
- `first_name`
- `last_name`
- `email` (Unique)
- `department`
- `position`
- `created_at`
- `updated_at`

### Project Table
- `id` (Primary Key)
- `name`
- `description`
- `status` (PLANNING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED)
- `start_date`
- `end_date`
- `created_at`
- `updated_at`

### Task Table
- `id` (Primary Key)
- `title`
- `description`
- `status` (TODO, IN_PROGRESS, IN_REVIEW, COMPLETED, BLOCKED)
- `priority` (LOW, MEDIUM, HIGH, CRITICAL)
- `project_id` (Foreign Key)
- `assigned_to` (Foreign Key)
- `due_date`
- `created_at`
- `updated_at`

## Configuration

Database configuration can be modified in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanagement
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Development

The application uses Spring Boot DevTools for hot reload during development. Any changes to the code will automatically restart the application.

## Testing

Run tests with:

```bash
mvn test
```

## License

This project is open source and available under the MIT License.
