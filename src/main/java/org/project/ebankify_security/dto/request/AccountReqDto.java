package org.project.ebankify_security.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.ebankify_security.entity.User;

@Data
public class AccountReqDto {
    @NotNull(message = "Owner is required")
    private User owner;
}
