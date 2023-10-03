package edu.cta.academy.mscursos.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.mscursos.controller.CursoController;
import edu.cta.academy.mscursos.service.CursoService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}) 
@RequestMapping("/curso")
public class CursoController {
	
	Logger logger = LoggerFactory.getLogger(CursoController.class); // Instancia de log

	
	@Autowired
	CursoService cursoService;
	
	private ResponseEntity<?> obtenerErrores(BindingResult br) {
		ResponseEntity<?> responseEntity = null;

		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach((ObjectError error) ->
		{
			logger.error(error.toString());
		});
		
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	};
	
	@PostMapping // http://localhost:8085/alumno
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Curso curso, BindingResult br) {
		
		Logger logger = LoggerFactory.getLogger(CursoController.class);

		ResponseEntity<?> responseEntity = null;
		Curso curso_nuevo = null;

		if (br.hasErrors()) {
			responseEntity = obtenerErrores(br);
		} else {
			logger.debug("El alumno viene sin fallos");
			curso_nuevo = this.cursoService.altaCurso(curso_nuevo);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(curso_nuevo);
		}

		return responseEntity;
	}
	
	// Delete
	
		@DeleteMapping("/{id}") // http://localhost:8086/curso/5
		public ResponseEntity<?> borrarAlumno(@PathVariable Long id) {

			String saludo = null;
			saludo.length();
			ResponseEntity<?> responseEntity = null;
			this.cursoService.borrarCursoPorId(id);
			responseEntity = ResponseEntity.status(HttpStatus.OK).build();

			return responseEntity;
		}
	
	//Get
	@GetMapping // http://localhost:8085/curso
	public ResponseEntity<?> listarAlumnos() {
	
		Iterable<Curso> ita = null; 

		ResponseEntity<?> responseEntity = null;
		
		//logger.debug("ATENDIDO POR "+ nombre_instancia + " PUERTO "+environment.getProperty("local.server.port"));
		ita = this.cursoService.consultarTodos();
		responseEntity = ResponseEntity.ok(ita);

		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarCursosPorId(@PathVariable Long id) {

		Optional<Curso> oc = null; //
		ResponseEntity<?> responseEntity = null;
		oc = this.cursoService.consultarPorId(id);
		
		if (oc.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build();
		} else {
			
			Curso curso_leido = oc.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		}

		return responseEntity;
	}
	
	//PUT
	@PutMapping("/agregarAlumnos/{idCurso}")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos,@PathVariable Long idCurso) {

		ResponseEntity<?> resp = null;
		var curso_modificado = this.cursoService.asignarAlumnos(alumnos, idCurso);
		if (curso_modificado.isPresent()) {
			resp = ResponseEntity.ok(curso_modificado.get());
		} else {
			resp = ResponseEntity.notFound().build();
		}
		return resp;
	}
	
	//PUT
	@PutMapping("/borrarAlumno/{idCurso}")
	public ResponseEntity<?> borrarAlumno(@RequestBody Alumno alumno,@PathVariable Long idCurso) {

		ResponseEntity<?> resp = null;
		var curso_modificado = this.cursoService.eliminarAlumno(alumno, idCurso);
		if (curso_modificado.isPresent()) {
			resp = ResponseEntity.ok(curso_modificado.get());
		} else {
			resp = ResponseEntity.notFound().build();
		}
		return resp;
	}

}
