package dev.felipeoj.users_crud.domain.model;



import java.util.UUID;



// domínio puro e sem depender de frameworks
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private UUID id;
    private String password;


    public User(String username, String email, String firstName, String lastName, UUID id, String password) {
        validateUsername(username);
        validateEmail(email);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPassword() {
        return password;
    }

    public static final class Builder {
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private UUID id;
        private boolean deleted = false;
        private String password;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username não pode estar vazio");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email não pode estar vazio");
            }

            return new User(username, email, firstName, lastName, id, password);
        }
    }
        private void validateUsername(String username){
            if(username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username não pode estar vazio.");}
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("A Senha deve conter no minimo 8 caracteres.");
        }
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!password.matches(regex)) {
            throw new IllegalArgumentException(
                    """
                            A senha deve conter:
                            - 1 letra maiúscula (A-Z)
                            - 1 letra minúscula (a-z)
                            - 1 número (0-9)
                            - 1 símbolo especial (@$!%*?&)
                            - Mínimo de 8 caracteres"""
            );
        }
        this.password = password;
    }

    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public UUID getId(){return id;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    private boolean deleted = false;

    public void markAsDeleted(){
        this.deleted = true;
    }

    public boolean isDeleted(){
        return deleted;
    }


}


