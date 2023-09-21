package edu.cta.academy.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.entity.Alumno;
import edu.cta.academy.service.AlumnoService;

/**
 * Recibe y contesta a las peticiones de los clientes
 * */

@RestController
@CrossOrigin
@RequestMapping("/alumno") //Le dices al servidor que todo lo que sea /alumno -prefijo de todos los métodos, es para esta clase
public class AlumnoController {
	
	@Autowired
	AlumnoService alumnoService;
	
	//Post
	@PostMapping //http://localhost:8085/alumno
	public ResponseEntity<?>insertarAlumno(@RequestBody Alumno alumno){
		
		Alumno alumno_nuevo = null;
		ResponseEntity<?> responseEntity = null;
		alumno_nuevo = this.alumnoService.altaAlumno(alumno);
		responseEntity=ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
			
		return responseEntity;
	}
	
	//Delete
	@DeleteMapping("/{id}") //http://localhost:8085/alumno/5
	public ResponseEntity<?>borrarAlumno(@PathVariable Long id){
		
		ResponseEntity<?> responseEntity = null;
		this.alumnoService.borrarAlumnoPorId(id);
		responseEntity=ResponseEntity.status(HttpStatus.OK).build();
			
		return responseEntity;
	}
	
	//Get
	@GetMapping //http://localhost:8085/alumno
	public ResponseEntity<?>listarAlumnos(){
		//? Devuelve cualquier cosa. En realidad es un iterable
		//ResponseEntity representa el mensaje HTTP de respuesta
		Iterable<Alumno> ita = null; //Lista de alumnos
		ResponseEntity<?> responseEntity = null;
		ita = this.alumnoService.consultarTodos();
		responseEntity=ResponseEntity.ok(ita);
		
		
		return responseEntity;
	}
	
	//GetById
	@GetMapping("/{id}") //http://localhost:8085/alumno/5
	public ResponseEntity<?>listarAlumnosPorId(@PathVariable Long id){
		
		Optional<Alumno> oa = null; //Lista de alumnos
		ResponseEntity<?> responseEntity = null;
		oa = this.alumnoService.consultarPorId(id);
		/**Si está devuelve ok (200). Si no está devuelve el cuerpo vacío y un no-content (204)*/
		if(oa.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build(); //Genera cuerpo vacío
		}else {
			//El optional tiene un alumno
			Alumno alumno_leido = oa.get();
			responseEntity = ResponseEntity.ok(alumno_leido);
		}
		
		return responseEntity;
	}
	

	//Método para devolver un alumno que no esté en la base de datos (alumno de prueba)
	@GetMapping("/obtener_alumno_test") //http://localhost:8085/alumno/obtener_alumno_test
	public Alumno obtenerAlumnoTest() {
		
		Alumno alumno = null;
		//Estado "Transient" -- no está asociado a la base de datos
		alumno = new Alumno(5l,"FERESTHTEH","LOPEZ","fere@oracle.es",18,LocalDateTime.now());
		
		return alumno;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		
		Optional<Alumno> oa = null; 
		ResponseEntity<?> responseEntity = null;
		oa = this.alumnoService.modificarPorId(alumno,id);
	
		if(oa.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build(); 
		}else {
			
			Alumno alumno_modificado = oa.get();
			responseEntity = ResponseEntity.ok(alumno_modificado);
		}
		
		
		return responseEntity;
	}

}
