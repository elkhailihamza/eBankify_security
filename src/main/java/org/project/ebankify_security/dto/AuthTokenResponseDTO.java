package org.project.ebankify_security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AuthTokenResponseDTO {
    private String token;
    private String refreshToken;
    private Date expirationDate;
}
