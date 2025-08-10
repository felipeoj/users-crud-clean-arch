# users-crud-clean-arch
User management system with Spring Boot implementing Clean Architecture, featuring soft delete, validation and RESTful API

## Tecnologias
- Java 17
- Spring Boot 3.5.4  
- Spring Data JPA
- MySQL
- MapStruct
- Lombok
- Flyway

## Arquitetura
- **Domain**: Entidades e regras de negócio
- **Application**: Use Cases e Controllers  
- **Infrastructure**: Persistência e implementações técnicas

## Como executar
```bash
mvn spring-boot:run
