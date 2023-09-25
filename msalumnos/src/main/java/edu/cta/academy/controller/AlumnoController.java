package edu.cta.academy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	
	Logger logger = LoggerFactory.getLogger(AlumnoController.class); //Instancia de log
	
	@Autowired
	AlumnoService alumnoService;
	
	private ResponseEntity<?>obtenerErrores(BindingResult br){
		ResponseEntity<?> responseEntity = null;
		
		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach((ObjectError error)-> //esta función en realidad es accept 
			{
				logger.error(error.toString());
			}
			);
		//Collections.sort(listaErrores, (o1, o2)-> o1.toString().compareTo(o2.toString()));
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
   return responseEntity;
	};
	
	//Post
	//@Valid indica que valide al alumno recibido.
	@PostMapping //http://localhost:8085/alumno
	public ResponseEntity<?>insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br){
		
		//BindingResult informa de cómo ha ido la carga. Si se ha superado o no la validación
		
		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;
		
			if (br.hasErrors())
			{
				responseEntity = obtenerErrores(br);
			} else {
				logger.debug("El alumno viene sin fallos");
				alumno_nuevo = this.alumnoService.altaAlumno(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
			}
			
		
		return responseEntity;
	}
	
	//Delete
	@DeleteMapping("/{id}") //http://localhost:8085/alumno/5
	public ResponseEntity<?>borrarAlumno(@PathVariable Long id){
		
		String saludo = null;
		saludo.length();
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
		/**
		var nombre = "HOLA";
		nombre.charAt(4);
		*/
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
	};
	
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br, @PathVariable Long id) {
		
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;//alumno
		
			if (br.hasErrors())
			{
				logger.debug("Alumno con errores en PUT");
				responseEntity = obtenerErrores(br);
				
			} else {
				
				logger.debug ("ALUMNO RX " + alumno);
				oa =  this.alumnoService.modificarPorId(alumno, id);
				
				if (oa.isEmpty())
				{
					//si no está--devolver el cuerpo vacío y 404 no content
					responseEntity = ResponseEntity.notFound().build();
				}  else {
					//el optional tiene un alumno //si está--devolver el alumno y 200 ok
					Alumno alumno_modificado = oa.get();
					responseEntity = ResponseEntity.ok(alumno_modificado);
				}
				
			}	
		
		return responseEntity;
	}

}
