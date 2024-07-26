package io.munkush.app.service;

import io.munkush.app.dto.UserRequestDto;
import io.munkush.app.model.Role;
import io.munkush.app.model.User;
import io.munkush.app.repository.RoleRepository;
import io.munkush.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void save(UserRequestDto request){

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        if (roleRepository.existsByName("USER")) {
            var roleFromDB = roleRepository.findByName("USER");
            user.setRole(List.of(roleFromDB));
        } else {
            var role = new Role("USER");
            role.setId(UUID.randomUUID().toString());
            user.setRole(List.of(role));
        }



        userRepository.save(user);
    }

}
