package org.project.ebankify_security.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthTokenResponseDTO {
    private String token;
}
