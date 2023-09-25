package edu.cta.academy.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**Actúa como un listener de excepciones que centraliza el tratamiento de excepciones 
 * de esta clase*/
/**Para que el programa sepa que es un sumidero de excepciones. Se puede delimitar el paquete donde
está escuchando.*/
/** Todas las excepciones derivadas en el paquete edu.cta.academy se derivan a esta clase. */

@RestControllerAdvice(basePackages = {"edu.cta.academy"}) 
public class GestionExcepciones {
	
	//Para cada tipo de excepción que quiera gestionar, hago un método
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public ResponseEntity<?> gestionStringIndexOutOfBoundsException (StringIndexOutOfBoundsException ex){
		ResponseEntity<?> responseEntity = null;
		String mensaje_error = ex.getMessage();
		responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> gestionEmptyResultDataAccessException (StringIndexOutOfBoundsException ex){
		ResponseEntity<?> responseEntity = null;
		String mensaje_error = ex.getMessage();
		responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> gestionErrorGenerico (Throwable ex){
		ResponseEntity<?> responseEntity = null;
		String mensaje_error = ex.getMessage();
		responseEntity = ResponseEntity.internalServerError().body(mensaje_error);
		
		return responseEntity;
	}

}
