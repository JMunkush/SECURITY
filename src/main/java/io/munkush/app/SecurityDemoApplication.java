package io.munkush.app;


import io.munkush.app.model.Role;
import io.munkush.app.model.User;
import io.munkush.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class SecurityDemoApplication {


	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}

	@Bean
	CommandLineRunner lineRunner(){
		return args -> {
			User user = new User();
			var role = new Role("USER");
			role.setId(UUID.randomUUID().toString());
			user.setRole(List.of(role));
			user.setUsername("Emin");
			user.setPassword(passwordEncoder.encode("asd"));

			userRepository.save(user);
		};
	}


}
