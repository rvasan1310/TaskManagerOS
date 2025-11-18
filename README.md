# Task Manager (Spring Boot)

**Zero-setup**: Spring Boot 3.3, Java 17, no Lombok, no `@PostConstruct`, parent POM manages plugins.

## Run
1. Open IntelliJ → *Open* → select this folder (contains `pom.xml`).
2. Ensure Project SDK = **Java 17+**.
3. Run `TaskManagerApplication`.
4. Visit <http://localhost:8080/>.

## API
- GET `/api/stats`
- GET `/api/processes`
- POST `/api/kill/{pid}`
