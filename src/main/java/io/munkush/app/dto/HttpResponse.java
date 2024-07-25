package io.munkush.app.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class HttpResponse {
    private HttpStatus httpStatus;
    private int statusCode;
    private String dateTime;
    private String message;
}
