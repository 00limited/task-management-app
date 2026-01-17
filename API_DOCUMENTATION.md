# API Documentation

## Overview
This document provides detailed information about the Task Management API endpoints.

## Base URL
```
http://localhost:8080
```

## Entities

### Employee
Represents an employee in the system.

**Fields:**
- `id` (Long): Auto-generated unique identifier
- `firstName` (String): Employee's first name
- `lastName` (String): Employee's last name
- `email` (String): Employee's email address (unique)
- `department` (String): Employee's department
- `position` (String): Employee's position
- `createdAt` (LocalDateTime): Record creation timestamp
- `updatedAt` (LocalDateTime): Last update timestamp

### Project
Represents a project in the system.

**Fields:**
- `id` (Long): Auto-generated unique identifier
- `name` (String): Project name
- `description` (String): Project description (max 1000 chars)
- `status` (ProjectStatus): Current project status
- `startDate` (LocalDateTime): Project start date
- `endDate` (LocalDateTime): Project end date
- `tasks` (List<Task>): Associated tasks
- `createdAt` (LocalDateTime): Record creation timestamp
- `updatedAt` (LocalDateTime): Last update timestamp

**Project Status Values:**
- `PLANNING`: Project is in planning phase
- `IN_PROGRESS`: Project is actively being worked on
- `COMPLETED`: Project has been completed
- `ON_HOLD`: Project is temporarily paused
- `CANCELLED`: Project has been cancelled

### Task
Represents a task in the system.

**Fields:**
- `id` (Long): Auto-generated unique identifier
- `title` (String): Task title
- `description` (String): Task description (max 2000 chars)
- `status` (TaskStatus): Current task status
- `priority` (TaskPriority): Task priority level
- `project` (Project): Associated project
- `assignedTo` (Employee): Assigned employee
- `dueDate` (LocalDateTime): Task due date
- `createdAt` (LocalDateTime): Record creation timestamp
- `updatedAt` (LocalDateTime): Last update timestamp

**Task Status Values:**
- `TODO`: Task is pending
- `IN_PROGRESS`: Task is being worked on
- `IN_REVIEW`: Task is under review
- `COMPLETED`: Task has been completed
- `BLOCKED`: Task is blocked

**Task Priority Values:**
- `LOW`: Low priority
- `MEDIUM`: Medium priority (default)
- `HIGH`: High priority
- `CRITICAL`: Critical priority

## API Endpoints

### Employee Endpoints

#### Get All Employees
```http
GET /api/employees
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "position": "Software Developer",
    "createdAt": "2024-01-17T10:00:00",
    "updatedAt": "2024-01-17T10:00:00"
  }
]
```

#### Get Employee by ID
```http
GET /api/employees/{id}
```

**Parameters:**
- `id` (path): Employee ID

**Response:** `200 OK` or `404 Not Found`

#### Get Employee by Email
```http
GET /api/employees/email/{email}
```

**Parameters:**
- `email` (path): Employee email address

**Response:** `200 OK` or `404 Not Found`

#### Create Employee
```http
POST /api/employees
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "position": "Software Developer"
}
```

**Response:** `201 Created`

#### Update Employee
```http
PUT /api/employees/{id}
```

**Parameters:**
- `id` (path): Employee ID

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john.smith@example.com",
  "department": "Engineering",
  "position": "Senior Software Developer"
}
```

**Response:** `200 OK` or `404 Not Found`

#### Delete Employee
```http
DELETE /api/employees/{id}
```

**Parameters:**
- `id` (path): Employee ID

**Response:** `204 No Content`

### Project Endpoints

#### Get All Projects
```http
GET /api/projects
```

**Response:** `200 OK`

#### Get Project by ID
```http
GET /api/projects/{id}
```

**Parameters:**
- `id` (path): Project ID

**Response:** `200 OK` or `404 Not Found`

#### Get Projects by Status
```http
GET /api/projects/status/{status}
```

**Parameters:**
- `status` (path): Project status (PLANNING, IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED)

**Response:** `200 OK`

#### Create Project
```http
POST /api/projects
```

**Request Body:**
```json
{
  "name": "Website Redesign",
  "description": "Complete redesign of company website",
  "status": "PLANNING",
  "startDate": "2024-02-01T09:00:00",
  "endDate": "2024-06-30T17:00:00"
}
```

**Response:** `201 Created`

#### Update Project
```http
PUT /api/projects/{id}
```

**Parameters:**
- `id` (path): Project ID

**Request Body:**
```json
{
  "name": "Website Redesign",
  "description": "Complete redesign and modernization of company website",
  "status": "IN_PROGRESS",
  "startDate": "2024-02-01T09:00:00",
  "endDate": "2024-06-30T17:00:00"
}
```

**Response:** `200 OK` or `404 Not Found`

#### Delete Project
```http
DELETE /api/projects/{id}
```

**Parameters:**
- `id` (path): Project ID

**Response:** `204 No Content`

### Task Endpoints

#### Get All Tasks
```http
GET /api/tasks
```

**Response:** `200 OK`

#### Get Task by ID
```http
GET /api/tasks/{id}
```

**Parameters:**
- `id` (path): Task ID

**Response:** `200 OK` or `404 Not Found`

#### Get Tasks by Project
```http
GET /api/tasks/project/{projectId}
```

**Parameters:**
- `projectId` (path): Project ID

**Response:** `200 OK`

#### Get Tasks by Employee
```http
GET /api/tasks/employee/{employeeId}
```

**Parameters:**
- `employeeId` (path): Employee ID

**Response:** `200 OK`

#### Get Tasks by Status
```http
GET /api/tasks/status/{status}
```

**Parameters:**
- `status` (path): Task status (TODO, IN_PROGRESS, IN_REVIEW, COMPLETED, BLOCKED)

**Response:** `200 OK`

#### Create Task
```http
POST /api/tasks
```

**Request Body:**
```json
{
  "title": "Design homepage mockup",
  "description": "Create initial mockup for new homepage design",
  "status": "TODO",
  "priority": "HIGH",
  "project": {
    "id": 1
  },
  "assignedTo": {
    "id": 1
  },
  "dueDate": "2024-02-15T17:00:00"
}
```

**Response:** `201 Created` or `400 Bad Request`

#### Update Task
```http
PUT /api/tasks/{id}
```

**Parameters:**
- `id` (path): Task ID

**Request Body:**
```json
{
  "title": "Design homepage mockup",
  "description": "Create and finalize homepage mockup",
  "status": "IN_PROGRESS",
  "priority": "HIGH",
  "project": {
    "id": 1
  },
  "assignedTo": {
    "id": 1
  },
  "dueDate": "2024-02-15T17:00:00"
}
```

**Response:** `200 OK` or `404 Not Found`

#### Delete Task
```http
DELETE /api/tasks/{id}
```

**Parameters:**
- `id` (path): Task ID

**Response:** `204 No Content`

## Error Responses

All endpoints may return the following error responses:

### 400 Bad Request
Invalid request parameters or body.

### 404 Not Found
Requested resource not found.

### 500 Internal Server Error
Unexpected server error.

## Example Workflow

1. **Create an Employee:**
   ```bash
   curl -X POST http://localhost:8080/api/employees \
     -H "Content-Type: application/json" \
     -d '{"firstName":"Jane","lastName":"Smith","email":"jane.smith@example.com","department":"Engineering","position":"Team Lead"}'
   ```

2. **Create a Project:**
   ```bash
   curl -X POST http://localhost:8080/api/projects \
     -H "Content-Type: application/json" \
     -d '{"name":"Mobile App Development","description":"Develop iOS and Android apps","status":"PLANNING"}'
   ```

3. **Create a Task and Assign to Employee:**
   ```bash
   curl -X POST http://localhost:8080/api/tasks \
     -H "Content-Type: application/json" \
     -d '{"title":"Setup project repository","description":"Create and configure Git repository","status":"TODO","priority":"HIGH","project":{"id":1},"assignedTo":{"id":1},"dueDate":"2024-02-01T17:00:00"}'
   ```

4. **Update Task Status:**
   ```bash
   curl -X PUT http://localhost:8080/api/tasks/1 \
     -H "Content-Type: application/json" \
     -d '{"title":"Setup project repository","description":"Create and configure Git repository","status":"COMPLETED","priority":"HIGH","project":{"id":1},"assignedTo":{"id":1}}'
   ```

5. **Get All Tasks for an Employee:**
   ```bash
   curl http://localhost:8080/api/tasks/employee/1
   ```
