package com.example.library2.library;

import com.example.library2.library.User.User;
import com.example.library2.library.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {


	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Value("${person.authority.admin}")
	private String ADMIN_AUTHORITY;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user= User.builder()
				.username("admin1")
				.password(passwordEncoder.encode("admin123"))
				.authorities(ADMIN_AUTHORITY)
				.build();

		userRepository.save(user);
	}
}
