package io.munkush.app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private LocalDateTime date;
}
