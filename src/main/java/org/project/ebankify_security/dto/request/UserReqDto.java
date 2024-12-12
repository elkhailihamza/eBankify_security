package org.project.ebankify_security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.ebankify_security.entity.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {

    @NotNull(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
    private String name;

    @NotNull(message = "Surname is required")
    @Size(min = 1, max = 100, message = "Surname should be between 1 and 100 characters")
    private String surname;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    private Set<Role> role;
}
