package edu.cta.academy.alumnos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //Al arrancar, Spring tiene en cuenta esta clase y configuramos el contexto
public class SecurityConfig {

	@Bean //Hago que el contexto tenga un codificador
	public PasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder();
	};
	
}
