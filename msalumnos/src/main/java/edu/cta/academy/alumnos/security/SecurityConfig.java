package edu.cta.academy.alumnos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//Configuración de la seguridad de Spring Security

@EnableWebSecurity
@Configuration //Al arrancar, Spring tiene en cuenta esta clase y configuramos el contexto
public class SecurityConfig {

	@Bean //Hago que el contexto tenga un codificador
	public PasswordEncoder passwordEncoder () {
		
		//BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		//System.out.println( bc.encode("admin")); //Contraseña cifrada
		return new BCryptPasswordEncoder();
	};
	
	@Bean //En este método, configuramos el objeto que va a actuar
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) throws Exception {
		
		
		
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder).and().parentAuthenticationManager(null)// no use la cadena de
																							// Autenticadores
				.build();
	}
	
	@Bean //Este bean es la fuente de los usuarios registrados.
	public UserDetailsService userDetailService(PasswordEncoder pe) {
		
		//El objeto pe es el de la línea 16.
		
		InMemoryUserDetailsManager manager = null;
		
		manager = new InMemoryUserDetailsManager();
		
		//definimos las credenciales de los usuarios de nuestra app/servicio
		manager.createUser(User.withUsername("admin").password(pe.encode("zaragoza")).roles().build());
		
		return manager;}
		
	@Bean	
	public SecurityFilterChain filterChain (HttpSecurity httpSecurity,AuthenticationManager am) throws Exception {
		return httpSecurity
				//.authenticationManager(am)
				.csrf().disable()//SIN ESTADO, SIN COOKIES, NO LA NECESITAMOS. EXIGIMOS TOKEN
				.cors().disable()
				.authorizeHttpRequests((authorize) -> authorize
				        .anyRequest().authenticated()
				    )
				.httpBasic()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.build();
		
	}
	
}
