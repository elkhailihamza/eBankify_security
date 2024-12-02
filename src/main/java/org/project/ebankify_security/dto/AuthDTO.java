package org.project.ebankify_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    public interface register extends Default {};

    @NotNull(message = "Name field mustn't be null!", groups = register.class)
    @Size(message = "Name needs to be at least 3 characters long!", min = 3, groups = register.class)
    private String name;

    @NotNull(message = "Surname field mustn't be null!", groups = register.class)
    @Size(message = "Surname needs to be at least 3 characters long!", min = 3, groups = register.class)
    private String surname;

    @NotNull(message = "Email field mustn't be null!")
    @Email(message = "Email not valid!")
    private String email;

    @NotNull(message = "Password field mustn't be null!")
    @Size(message = "Password needs to be at least 8 characters long!", min = 8)
    private String password;
}
