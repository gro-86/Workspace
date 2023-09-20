package edu.cta.academy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.entity.Alumno;

/**
 * Recibe y contesta a las peticiones de los clientes
 * */

@RestController
@CrossOrigin
@RequestMapping("/alumno") //Le dices al servidor que todo lo que sea /alumno, es para esta clase
public class AlumnoController {
	
	//CREATE
	
	//DELETE
	
	//GETALL
	@GetMapping //http://localhost:8085/alumno
	public ResponseEntity<?>listarAlumnos(){
		// ? Devuelve cualquier cosa. En realidad es un iterable
		//ResponseEntity representa el mensaje HTTP de respuesta
		Iterable<Alumno> ita = null;
		ResponseEntity<?> responseEntity = null;
		
		return responseEntity;
	}
	
	//GETById
	
	//PUT

}
