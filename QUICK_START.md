# Quick Start Guide

## Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose

## Setup (5 minutes)

### 1. Clone & Navigate
```bash
git clone https://github.com/00limited/task-management-app.git
cd task-management-app
```

### 2. Start Database
```bash
docker-compose up -d
```

### 3. Build & Run
```bash
mvn spring-boot:run
```

## Test the API

### Create an Employee
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@example.com",
    "department": "Engineering",
    "position": "Developer"
  }'
```

### Create a Project
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mobile App",
    "description": "New mobile application",
    "status": "PLANNING"
  }'
```

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Setup Repository",
    "description": "Initialize Git repository",
    "status": "TODO",
    "priority": "HIGH",
    "project": {"id": 1},
    "assignedTo": {"id": 1}
  }'
```

### Get All Tasks
```bash
curl http://localhost:8080/api/tasks
```

## Common Commands

### Database
```bash
# Start database
docker-compose up -d

# Stop database
docker-compose down

# View logs
docker-compose logs -f

# Connect to database
docker exec -it taskmanagement-postgres psql -U postgres -d taskmanagement
```

### Application
```bash
# Run application
mvn spring-boot:run

# Build JAR
mvn clean package

# Run JAR
java -jar target/task-management-app-1.0.0.jar

# Run tests
mvn test
```

## API Endpoints Overview

### Employees
- `GET /api/employees` - List all
- `GET /api/employees/{id}` - Get by ID
- `POST /api/employees` - Create
- `PUT /api/employees/{id}` - Update
- `DELETE /api/employees/{id}` - Delete

### Projects
- `GET /api/projects` - List all
- `GET /api/projects/{id}` - Get by ID
- `GET /api/projects/status/{status}` - Filter by status
- `POST /api/projects` - Create
- `PUT /api/projects/{id}` - Update
- `DELETE /api/projects/{id}` - Delete

### Tasks
- `GET /api/tasks` - List all
- `GET /api/tasks/{id}` - Get by ID
- `GET /api/tasks/project/{projectId}` - By project
- `GET /api/tasks/employee/{employeeId}` - By employee
- `GET /api/tasks/status/{status}` - By status
- `POST /api/tasks` - Create
- `PUT /api/tasks/{id}` - Update
- `DELETE /api/tasks/{id}` - Delete

## Enums

### Project Status
- `PLANNING`
- `IN_PROGRESS`
- `COMPLETED`
- `ON_HOLD`
- `CANCELLED`

### Task Status
- `TODO`
- `IN_PROGRESS`
- `IN_REVIEW`
- `COMPLETED`
- `BLOCKED`

### Task Priority
- `LOW`
- `MEDIUM`
- `HIGH`
- `CRITICAL`

## Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081
```

### Database Connection Failed
```bash
# Check if PostgreSQL is running
docker-compose ps

# Restart database
docker-compose restart
```

### Build Failed
```bash
# Clean and rebuild
mvn clean install
```

## Next Steps
- Read [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for detailed API info
- Read [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) for development setup
- Check [README.md](README.md) for full documentation
