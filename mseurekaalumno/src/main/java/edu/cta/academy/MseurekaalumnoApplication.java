package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //Activa Eureka
public class MseurekaalumnoApplication {
	
	/**
	 * 1. Creamos Sprint Starter y dependencia Eureka Server
	 * 2. Add dependencia JAXB
	 * 3. Anotación @EnableEurekaServer
	 * 4. Config properties
	 * */

	public static void main(String[] args) {
		SpringApplication.run(MseurekaalumnoApplication.class, args);
	}

}
