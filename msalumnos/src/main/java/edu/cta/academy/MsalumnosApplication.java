package edu.cta.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsalumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosApplication.class, args);
	}

}
