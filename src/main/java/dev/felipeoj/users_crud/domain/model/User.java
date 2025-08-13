package dev.felipeoj.users_crud.domain.model;



import java.util.UUID;



// domínio puro e sem depender de frameworks
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private UUID id;


    public User(String username, String email, String firstName, String lastName, UUID id) {
        validateUsername(username);
        validateEmail(email);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

        private void validateUsername(String username){
            if(username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username não pode estar vazio.");}
    }

    private void validateEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }
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


