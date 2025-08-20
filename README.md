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

 ## Configuração de Autenticação JWT

### Gerando Chaves RSA

1. **Gerar par de chaves** (OpenSSL):
   ```bash
   openssl genpkey -algorithm RSA -out src/main/resources/app.key -pkeyopt rsa_keygen_bits:2048
   openssl rsa -pubout -in src/main/resources/app.key -out src/main/resources/app.pub

## Como executar
```bash
mvn spring-boot:run
