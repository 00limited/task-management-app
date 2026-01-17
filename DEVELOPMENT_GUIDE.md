# Development Guide

## Overview
This guide provides information for developers working with the Task Management application.

## Technology Stack

### Backend
- **Java 17**: Programming language
- **Spring Boot 3.2.1**: Application framework
- **Spring Data JPA**: Database abstraction layer
- **Hibernate**: ORM implementation
- **PostgreSQL 15**: Relational database
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code

### Tools
- **Docker & Docker Compose**: Database containerization
- **Git**: Version control

## Project Structure

```
task-management-app/
├── src/
│   ├── main/
│   │   ├── java/com/taskmanagement/app/
│   │   │   ├── controller/         # REST API controllers
│   │   │   │   ├── EmployeeController.java
│   │   │   │   ├── ProjectController.java
│   │   │   │   └── TaskController.java
│   │   │   ├── entity/             # Domain models
│   │   │   │   ├── Employee.java
│   │   │   │   ├── Project.java
│   │   │   │   └── Task.java
│   │   │   ├── repository/         # Data access layer
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   ├── ProjectRepository.java
│   │   │   │   └── TaskRepository.java
│   │   │   ├── service/            # Business logic layer
│   │   │   │   ├── EmployeeService.java
│   │   │   │   ├── ProjectService.java
│   │   │   │   └── TaskService.java
│   │   │   └── TaskManagementApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/taskmanagement/app/
│           └── TaskManagementApplicationTests.java
├── pom.xml
├── docker-compose.yml
├── README.md
├── API_DOCUMENTATION.md
└── DEVELOPMENT_GUIDE.md
```

## Development Setup

### Prerequisites
1. Install Java 17 or higher
2. Install Maven 3.6 or higher
3. Install Docker and Docker Compose
4. Install your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)

### IDE Setup

#### IntelliJ IDEA
1. Open the project as a Maven project
2. Enable annotation processing for Lombok:
   - Go to `Settings > Build, Execution, Deployment > Compiler > Annotation Processors`
   - Check "Enable annotation processing"
3. Install Lombok plugin if not already installed

#### Eclipse
1. Import the project as "Existing Maven Project"
2. Install Lombok:
   - Download lombok.jar
   - Run: `java -jar lombok.jar`
   - Select your Eclipse installation

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Install Lombok Annotations Support extension

### Running the Application

#### Start PostgreSQL Database
```bash
docker-compose up -d
```

#### Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or run the compiled JAR
mvn clean package
java -jar target/task-management-app-1.0.0.jar
```

#### Access the Application
- Application: http://localhost:8080
- H2 Console (if enabled): http://localhost:8080/h2-console

## Building the Application

### Clean Build
```bash
mvn clean install
```

### Skip Tests
```bash
mvn clean install -DskipTests
```

### Run Tests Only
```bash
mvn test
```

### Package without Tests
```bash
mvn clean package -DskipTests
```

## Database Management

### Connect to PostgreSQL
```bash
# Using Docker
docker exec -it taskmanagement-postgres psql -U postgres -d taskmanagement

# Using psql client
psql -h localhost -p 5432 -U postgres -d taskmanagement
```

### View Tables
```sql
\dt
```

### View Table Structure
```sql
\d employees
\d projects
\d tasks
```

### Sample Queries
```sql
-- View all employees
SELECT * FROM employees;

-- View projects with their tasks
SELECT p.name, t.title, t.status
FROM projects p
LEFT JOIN tasks t ON p.id = t.project_id;

-- View tasks assigned to an employee
SELECT e.first_name, e.last_name, t.title, t.status
FROM employees e
JOIN tasks t ON e.id = t.assigned_to;
```

## Code Style and Conventions

### Java Code Style
- Follow standard Java naming conventions
- Use camelCase for methods and variables
- Use PascalCase for class names
- Use UPPER_CASE for constants

### Lombok Usage
- Use `@Data` for entities and DTOs
- Use `@RequiredArgsConstructor` for dependency injection
- Use `@NoArgsConstructor` and `@AllArgsConstructor` for entities

### API Conventions
- Use RESTful naming conventions
- Use HTTP verbs correctly (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Use plural nouns for resource endpoints

## Adding New Features

### Adding a New Entity
1. Create entity class in `entity` package
2. Add validation annotations
3. Create repository interface in `repository` package
4. Create service class in `service` package
5. Create controller class in `controller` package
6. Update documentation

### Example: Adding a Comment Entity
```java
// 1. Create Entity
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Employee author;
    
    private LocalDateTime createdAt;
}

// 2. Create Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
}

// 3. Create Service
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    
    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}

// 4. Create Controller
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Comment>> getCommentsByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTaskId(taskId));
    }
}
```

## Testing

### Unit Tests
- Use JUnit 5 for testing
- Mock dependencies with Mockito
- Test service layer logic

### Integration Tests
- Use `@SpringBootTest` for integration tests
- Test complete API endpoints
- Use test database or H2 in-memory database

### Example Test
```java
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateEmployee() throws Exception {
        String employeeJson = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@example.com",
                "department": "Engineering",
                "position": "Developer"
            }
            """;
        
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated());
    }
}
```

## Common Issues and Solutions

### Issue: Database Connection Failed
**Solution:** Make sure PostgreSQL is running with Docker Compose
```bash
docker-compose ps
docker-compose up -d
```

### Issue: Lombok Not Working
**Solution:** Enable annotation processing in your IDE and install the Lombok plugin

### Issue: Port 8080 Already in Use
**Solution:** Change the port in `application.properties`
```properties
server.port=8081
```

### Issue: Maven Build Failed
**Solution:** Clear Maven cache and rebuild
```bash
mvn clean
mvn install -U
```

## Performance Optimization

### Database Optimization
- Add indexes on frequently queried columns
- Use pagination for large result sets
- Optimize N+1 query problems with JOIN FETCH

### Application Optimization
- Enable Spring Boot caching
- Use connection pooling
- Implement lazy loading for relationships

### Example: Adding Pagination
```java
@GetMapping
public Page<Employee> getAllEmployees(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return employeeService.getAllEmployees(pageable);
}
```

## Security Considerations

### Input Validation
- Always use `@Valid` annotation for request bodies
- Validate all user inputs
- Use constraint annotations from Jakarta Validation

### SQL Injection Prevention
- Spring Data JPA uses parameterized queries by default
- Never concatenate user input into queries

### Error Handling
- Don't expose sensitive information in error messages
- Log security-related events
- Implement global exception handling

## Deployment

### Building for Production
```bash
mvn clean package -Pprod
```

### Environment-Specific Configuration
Create `application-prod.properties` for production settings:
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

### Docker Deployment
Create a Dockerfile for the application:
```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/task-management-app-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Contributing

### Git Workflow
1. Create a feature branch from main
2. Make changes and commit frequently
3. Write descriptive commit messages
4. Create a pull request
5. Address code review comments
6. Merge after approval

### Commit Message Format
```
<type>: <subject>

<body>

<footer>
```

Types: feat, fix, docs, style, refactor, test, chore

## Additional Resources

### Spring Boot Documentation
- https://spring.io/projects/spring-boot
- https://docs.spring.io/spring-boot/docs/current/reference/html/

### Spring Data JPA
- https://spring.io/projects/spring-data-jpa
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

### PostgreSQL Documentation
- https://www.postgresql.org/docs/

### Lombok
- https://projectlombok.org/

## Support

For questions or issues:
1. Check the documentation
2. Review existing issues on GitHub
3. Create a new issue with detailed information
4. Contact the development team
