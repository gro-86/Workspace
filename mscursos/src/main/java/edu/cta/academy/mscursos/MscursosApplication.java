package edu.cta.academy.mscursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("edu.cta.academy.comun") //Spring busca las entidades en este paquete. Si no lo indicas, sólo busca en el paquete raíz
public class MscursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscursosApplication.class, args);
		
	}

}
