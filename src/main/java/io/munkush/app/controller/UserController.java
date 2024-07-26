package io.munkush.app.controller;

import io.munkush.app.config.security.JwtFactory;
import io.munkush.app.dto.UserRegistryResponse;
import io.munkush.app.dto.UserRequestDto;
import io.munkush.app.exception.UsernameIsEmptyException;
import io.munkush.app.model.User;
import io.munkush.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final JwtFactory jwtFactory;
    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> fetchAll(){
        log.info("all users are successfully fetched");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserRegistryResponse> registry(@RequestBody UserRequestDto request) throws UsernameIsEmptyException {

        if(request.getUsername().isBlank()){
            throw new UsernameIsEmptyException("пожалуйста заполните все поля (имя)");
        }


        if (request.getPassword().isBlank() || request.getUsername().isBlank()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserRegistryResponse.builder()
                    .build());
        } else {

            userService.save(request);

            return ResponseEntity.ok(UserRegistryResponse.builder()
                    .accessToken(jwtFactory.createAccess(request.getUsername()))
                    .refreshToken(jwtFactory.createRefresh(request.getUsername()))
                    .date(LocalDateTime.now())
                    .build());
        }
    }






}
