package dev.felipeoj.users_crud.domain.model.valueobjects;

public class Username {
    private final String value;
    public Username(String value) {
        validateUsername(value);
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    private void validateUsername(String value){
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username não pode estar vazio.");}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return value.equals(username.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
