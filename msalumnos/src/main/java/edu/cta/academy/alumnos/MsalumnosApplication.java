package edu.cta.academy.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;


//@ComponentScan //Para indicar el paquete/ubicación de Service, Repository, Controller si estuvieran fuera del directorio raíz
@SpringBootApplication
@EnableEurekaClient
@EntityScan("edu.cta.academy.comun") //Spring busca las entidades en este paquete. Si no lo indicas, sólo busca en el paquete raíz
@EnableFeignClients //Activamos el FeignClient
public class MsalumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsalumnosApplication.class, args);
	}

}
