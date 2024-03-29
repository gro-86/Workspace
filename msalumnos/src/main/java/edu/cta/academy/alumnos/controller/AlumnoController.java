package edu.cta.academy.alumnos.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.cta.academy.alumnos.model.FraseChiquito;
import edu.cta.academy.alumnos.service.AlumnoService;
import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

/**
 * Recibe y contesta a las peticiones de los clientes
 */

@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}) //Permite todos los origenes y se aplica al GET
//Se puede poner un plugin en el navegador que se salta esta restricción sin tener que activar esta anotación
@RestController
@RequestMapping("/alumno") // Le dices al servidor que todo lo que sea /alumno -prefijo de todos los
							// métodos, es para esta clase
public class AlumnoController {

	Logger logger = LoggerFactory.getLogger(AlumnoController.class); // Instancia de log

	@Autowired
	AlumnoService alumnoService;
	
	//Cuando se arranque el microservicio, se pasa un nombre por parámetro
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment environment;

	private ResponseEntity<?> obtenerErrores(BindingResult br) {
		ResponseEntity<?> responseEntity = null;

		logger.debug("El alumno trae fallos");
		List<ObjectError> listaErrores = br.getAllErrors();
		listaErrores.forEach((ObjectError error) -> // esta función en realidad es accept
		{
			logger.error(error.toString());
		});
		// Collections.sort(listaErrores, (o1, o2)->
		// o1.toString().compareTo(o2.toString()));
		responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErrores);
		return responseEntity;
	};

	// Post
	// @Valid indica que valide al alumno recibido.
	@PostMapping // http://localhost:8085/alumno
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) {

		// BindingResult informa de cómo ha ido la carga. Si se ha superado o no la
		// validación

		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;

		if (br.hasErrors()) {
			responseEntity = obtenerErrores(br);
		} else {
			logger.debug("El alumno viene sin fallos");
			alumno_nuevo = this.alumnoService.altaAlumno(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
		}

		return responseEntity;
	}

	// Delete
	@DeleteMapping("/{id}") // http://localhost:8085/alumno/5
	public ResponseEntity<?> borrarAlumno(@PathVariable Long id) {

		String saludo = null;
		saludo.length();
		ResponseEntity<?> responseEntity = null;
		this.alumnoService.borrarAlumnoPorId(id);
		responseEntity = ResponseEntity.status(HttpStatus.OK).build();

		return responseEntity;
	}

	//Get
	@GetMapping // http://localhost:8085/alumno
	public ResponseEntity<?> listarAlumnos() {
		// ? Devuelve cualquier cosa. En realidad es un iterable
		// ResponseEntity representa el mensaje HTTP de respuesta
		Iterable<Alumno> ita = null; // Lista de alumnos

		ResponseEntity<?> responseEntity = null;
		/**
		 * var nombre = "HOLA"; nombre.charAt(4);
		 */
		logger.debug("ATENDIDO POR "+ nombre_instancia + " PUERTO "+environment.getProperty("local.server.port"));
		ita = this.alumnoService.consultarTodos();
		responseEntity = ResponseEntity.ok(ita);

		return responseEntity;
	}
	
	//Get
	@GetMapping("/hateoas") // http://localhost:8085/alumno/hateoas
	public ResponseEntity<?> listarAlumnosHateoas() {
		// ? Devuelve cualquier cosa. En realidad es un iterable
		// ResponseEntity representa el mensaje HTTP de respuesta
		Iterable<Alumno> ita = null; // Lista de alumnos

		ResponseEntity<?> responseEntity = null;
		/**
		 * var nombre = "HOLA"; nombre.charAt(4);
		 */
		
		for (Alumno a :ita)
		{
			a.add(linkTo(methodOn(AlumnoController.class).listarAlumnosPorId(a.getId())).withSelfRel());
		}
		
		logger.debug("ATENDIDO POR "+ nombre_instancia + " PUERTO "+environment.getProperty("local.server.port"));
		ita = this.alumnoService.consultarTodos();
		responseEntity = ResponseEntity.ok(ita);

		return responseEntity;
	}

	// GetById
	@GetMapping("/{id}") // http://localhost:8085/alumno/5
	public ResponseEntity<?> listarAlumnosPorId(@PathVariable Long id) {

		Optional<Alumno> oa = null; // Lista de alumnos
		ResponseEntity<?> responseEntity = null;
		oa = this.alumnoService.consultarPorId(id);
		/**
		 * Si está devuelve ok (200). Si no está devuelve el cuerpo vacío y un
		 * no-content (204)
		 */

		if (oa.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build(); // Genera cuerpo vacío
		} else {
			// El optional tiene un alumno
			Alumno alumno_leido = oa.get();
			responseEntity = ResponseEntity.ok(alumno_leido);
		}

		return responseEntity;
	}

	// Método para devolver un alumno que no esté en la base de datos (alumno de
	// prueba)
	//GET
	@GetMapping("/obtener_alumno_test") // http://localhost:8085/alumno/obtener_alumno_test
	public Alumno obtenerAlumnoTest() {

		Alumno alumno = null;
		// Estado "Transient" -- no está asociado a la base de datos
		alumno = new Alumno(5l, "FERESTHTEH", "LOPEZ", "fere@oracle.es", 18, LocalDateTime.now());

		return alumno;
	};

	//PUT
	@PutMapping("/{id}")
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br,
			@PathVariable Long id) {

		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		if (br.hasErrors()) {
			logger.debug("Alumno con errores en PUT");
			responseEntity = obtenerErrores(br);

		} else {

			logger.debug("ALUMNO RX " + alumno);
			oa = this.alumnoService.modificarPorId(alumno, id);

			if (oa.isEmpty()) {
				// si no está--devolver el cuerpo vacío y 404 no content
				responseEntity = ResponseEntity.notFound().build();
			} else {
				// el optional tiene un alumno //si está--devolver el alumno y 200 ok
				Alumno alumno_modificado = oa.get();
				responseEntity = ResponseEntity.ok(alumno_modificado);
			}

		}

		return responseEntity;
	}
	
	//GET
	@GetMapping("/buscarPorRangoEdad") /** http://localhost:8085/alumno/buscarPorRangoEdad?edadMin=3&edadMax=90 
	http://localhost:8085/alumno/buscarPorRangoEdad/3/90*/
	public ResponseEntity<?> listarAlumnosBetweenEdad(@RequestParam(required = true, name = "edadMin") int edadMin, @RequestParam(required = true, name = "edadMax") int edadMax) {
		
		Iterable<Alumno> ita = null;
		ResponseEntity<?> responseEntity = null;
		
		ita = this.alumnoService.findByEdadBetween(edadMin, edadMax);
		responseEntity = ResponseEntity.ok(ita);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/buscarPorRangoEdadPage") /** http://localhost:8085/alumno/buscarPorRangoEdad?edadMin=3&edadMax=90&page=0&sort=edad ASC */
	public ResponseEntity<?> listarAlumnosBetweenEdadPage(@RequestParam(required = true, name = "edadMin") int edadMin, @RequestParam(required = true, name = "edadMax") int edadMax , Pageable page) {
		
		Iterable<Alumno> ita = null;
		ResponseEntity<?> responseEntity = null;
		
		ita = this.alumnoService.findByEdadBetween(edadMin, edadMax, page);
		responseEntity = ResponseEntity.ok(ita);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/buscarPorRangoNombre/{nombre}") /** http://localhost:8085/alumno/buscarPorRangoNombre?nombre=R 
	http://localhost:8085/alumno/buscarPorRangoNombre/R */
	public ResponseEntity<?> listarAlumnosConteniendo(@PathVariable String nombre) {
		
		Iterable<Alumno> result = null;
		ResponseEntity<?> responseEntity = null;
		
		result = this.alumnoService.findByNombreContaining(nombre);
		responseEntity = ResponseEntity.ok(result);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/buscarPorRangoNombreApellido/{patron}") /** http://localhost:8085/alumno/buscarPorRangoNombre?nombre=R 
	http://localhost:8085/alumno/buscarPorRangoNombreApellido/ */
	public ResponseEntity<?> listarAlumnosConteniendoNombreApellido(@PathVariable String patron) {
		
		Iterable<Alumno> result = null;
		ResponseEntity<?> responseEntity = null;
		
		result = this.alumnoService.findByNombreAndApellidoContaining(patron);
		responseEntity = ResponseEntity.ok(result);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/buscarPorRangoNombreApellidoJPQL/{patron}") /** http://localhost:8085/alumno/buscarPorRangoNombre?nombre=R 
	http://localhost:8085/alumno/buscarPorRangoNombreApellidoJPQL/ */
	public ResponseEntity<?> listarAlumnosConteniendoNombreApellidoJPQL(@PathVariable String patron) {
		
		Iterable<Alumno> result = null;
		ResponseEntity<?> responseEntity = null;
		
		result = this.alumnoService.findByNombreAndApellidoContaining(patron);
		responseEntity = ResponseEntity.ok(result);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/obtenerAlumnosAltaHoy") /** http://localhost:8085/alumno/obtenerAltaAlumnosHoy
	http://localhost:8085/alumno/buscarPorRangoNombreApellidoJPQL/ */
	public ResponseEntity<?> obtenerAlumnosAltaHoy() {
		
		Iterable<Alumno> result = null;
		ResponseEntity<?> responseEntity = null;
		
		result = this.alumnoService.procedimientoAltaAlumnosHoy();
		responseEntity = ResponseEntity.ok(result);

		return responseEntity;
	}
		
	
	//GET
	@GetMapping("/obtenerEstadisticosEdad") /** http://localhost:8085/alumno/obtenerEstadisticosEdad*/
	public ResponseEntity<?> obtenerEstadisticosEdad() {
		
		Map<String, Number> mapStats = null;
		ResponseEntity<?> responseEntity = null;
		
		mapStats = this.alumnoService.procedimientoEstadisticosEdad();
		responseEntity = ResponseEntity.ok(mapStats);

		return responseEntity;
	}
	
	//GET PAGINA
	@GetMapping("/obtenerAlumnosPorPagina") /** http://localhost:8085/alumno/obtenerAlumnosPorPagina?page=0&size=2&sort=edad, ASC*/
	public ResponseEntity<?> obtenerAlumnosPorPagina(Pageable pageable) {
		
		Iterable<Alumno> listado = null;
		ResponseEntity<?> responseEntity = null;
		
		listado = this.alumnoService.findAll(pageable);
		responseEntity = ResponseEntity.ok(listado);

		return responseEntity;
	}
	
	//GET
	@GetMapping("/obtenerFraseChiquito") /** http://localhost:8085/alumno/obtenerFraseChiquito*/
	public ResponseEntity<?> obtenerFraseChiquito() {
	
	Optional<FraseChiquito> oFrase= null;
	ResponseEntity<?> responseEntity = null;
	
	oFrase = this.alumnoService.obtenerFraseAleatoriaChiquito();
	if (oFrase.isPresent()) {
		responseEntity = ResponseEntity.ok(oFrase.get());
	}else {
		responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

		return responseEntity;
	}
	
	//POST con foto
	@PostMapping("/insertar-con-foto") //POST http://localhost:8085/alumno/insertar-con-foto
	public ResponseEntity<?> insertarAlumnoConFoto(@Valid Alumno alumno, BindingResult br, MultipartFile archivo) throws Exception {

		ResponseEntity<?> responseEntity = null;
		Alumno alumno_nuevo = null;

		if (br.hasErrors()) {
			
			responseEntity = obtenerErrores(br);
			
		} else {
			
			logger.debug("VIENE CON FALLOS");
			
			if(!archivo.isEmpty()) {
				
				logger.debug("VIENE CON FOTO");
				
				try {
					
					alumno.setFoto(archivo.getBytes());
					
				} catch (IOException e) {
					
					e.printStackTrace();
					logger.error("Error al cargar la foto", e);
					throw e;
				}
				
				alumno_nuevo = this.alumnoService.altaAlumno(alumno);
				responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_nuevo);
				
			}
			
			
		}

		return responseEntity;
	}
	 
	
	// GetById
	@GetMapping("/obtenerFoto/{id}") // http://localhost:8085/obtenerFotoAlumno/5
	public ResponseEntity<?> obtenerFotoPorId(@PathVariable Long id) {

		Optional<Alumno> oa = null; // Lista de alumnos
		ResponseEntity<?> responseEntity = null;
		oa = this.alumnoService.consultarPorId(id);
		
		if (oa.isEmpty()) {
			
			responseEntity = ResponseEntity.noContent().build(); // Genera cuerpo vacío
			
		} else {
			
			Alumno alumno_leido = oa.get();
			
			if(alumno_leido.getFoto()!=null) {
				logger.debug("El alumno tiene foto");
				//Resource es cualquier tipo de archivo
				Resource imagen = new ByteArrayResource(alumno_leido.getFoto()); //obtengo la foto
				responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
			} else {
				logger.debug("El alumno existe pero no tiene foto");
				responseEntity = ResponseEntity.noContent().build(); // Genera cuerpo vacío
			}
			
		}

		return responseEntity;
	}
	
	//PUT con foto
	//NOTA: El nombre del parámetro debe coincidir con la clave en la petición
	@PutMapping("/modificar-con-foto/{id}")
	public ResponseEntity<?> modificarAlumnoConFoto(@Valid Alumno alumno, BindingResult br, MultipartFile archivo,
			@PathVariable Long id) throws Exception {

		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;// alumno

		if (br.hasErrors()) {
			logger.debug("Alumno con errores en PUT");
			responseEntity = obtenerErrores(br);

		} else {

			logger.debug("ALUMNO RX " + alumno);
			
			if(!archivo.isEmpty()) {
				try {
					alumno.setFoto(archivo.getBytes());
				} catch (IOException e) {
					
					logger.error("ERROR AL MODIFICAR CON FOTO",e);
					throw e;
				}
			}
			
			oa = this.alumnoService.modificarPorId(alumno, id);

			if (oa.isEmpty()) {
				// si no está--devolver el cuerpo vacío y 404 no content
				responseEntity = ResponseEntity.notFound().build();
			} else {
				// el optional tiene un alumno //si está--devolver el alumno y 200 ok
				Alumno alumno_modificado = oa.get();
				responseEntity = ResponseEntity.ok(alumno_modificado);
			}

		}

		return responseEntity;
	}
	
	// GetById
	@GetMapping("/obtenerCursoAlumnoPorFeign/{id}") // http://localhost:8085/alumno/obtenerCursoAlumnoPorFeign/5
	public ResponseEntity<?> obtenerCursoAlumnoPorFeign(@PathVariable Long id) {

		Optional<Curso> oc = null; 
		ResponseEntity<?> responseEntity = null;
		oc = this.alumnoService.obtenerCursoAlumno(id);
		
		if (oc.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build(); 
		} else {
			
			Curso curso_leido = oc.get();
			responseEntity = ResponseEntity.ok(curso_leido);
		}

		return responseEntity;
	}

}
