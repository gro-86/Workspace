package edu.cta.academy.alumnos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //Al arrancar, Spring tiene en cuenta esta clase y configuramos el contexto
public class SecurityConfig {

	@Bean //Hago que el contexto tenga un codificador
	public PasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder();
	};
	
	@Bean //En este método, configuramos el objeto que va a actuar
	public AuthenticationManager autenticationManager() {
		return null;	
	};
	
	@Bean //Este bean es la fuente de los usuarios registrados
	public UserDetailsService userDetailService() {
		return null;};
	
}
