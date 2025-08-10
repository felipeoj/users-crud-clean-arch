package dev.felipeoj.users_crud.domain.model;


import java.util.UUID;



// domínio puro e sem depender de frameworks
public class User {
    private UUID userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;

    public User(UUID userId,
                String userName,
                String firstName,
                String lastName,
                String email
                ) {
        validateUserName(userName);
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

        private void validateUserName(String userName){
            if(userName == null || userName.isBlank()) {
                throw new IllegalArgumentException("Username não pode estar vazio.");}
    }

    private void validateEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
    }

    public UUID getUserId(){return userId;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getUserName(){return userName;}
    public String getEmail(){return email;}


}


