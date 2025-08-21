package dev.felipeoj.users_crud.domain.model;



import dev.felipeoj.users_crud.domain.model.valueobjects.Email;
import dev.felipeoj.users_crud.domain.model.valueobjects.Password;
import dev.felipeoj.users_crud.domain.model.valueobjects.Username;

import java.util.UUID;



// domínio puro e sem depender de frameworks
public class User {
    private final Username username;
    private String firstName;
    private String lastName;
    private final Email email;
    private UUID id;
    private final Password password;


    public User(Username username, Email email, String firstName, String lastName, UUID id, Password password) {
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

    public Password getPassword() {
        return password;
    }

    public static final class Builder {
        private Username username;
        private String firstName;
        private String lastName;
        private Email email;
        private UUID id;
        private boolean deleted = false;
        private Password password;

        public Builder username(Username username) {
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

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public User build() {
            if (username == null || firstName == null || lastName == null || email == null || password == null) {
                throw new IllegalArgumentException("Todos os Campos São Obrigarorios");
            }
            return new User(username, email, firstName, lastName, id, password);
        }
    }


    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public Username getUsername(){return username;}
    public Email getEmail(){return email;}
    public UUID getId(){return id;}

    private boolean deleted = false;

    public void markAsDeleted(){
        this.deleted = true;
    }

    public boolean isDeleted(){
        return deleted;
    }


}


