package dev.mvvasilev.model.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Valid
public class UserRegistrationDTO {

    @Size(min = 5, max = 50)
    private String username;

    @NotEmpty
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$")
    private String password;

    public UserRegistrationDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
