package dev.felipeoj.users_crud.domain.model.valueobjects;

public class Password {
    private final String value;

    public Password(String value) {
        validatePassword(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validatePassword(String value) {
        if (value != null && value.startsWith("$2a$")) {
            return;
        }
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("A senha não pode ser vazia.");
        }
        if (value.length() < 8) {
            throw new IllegalArgumentException("A Senha deve conter no minimo 8 caracteres.");
        }
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (!value.matches(regex)) {
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return value.equals(password.value);
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
