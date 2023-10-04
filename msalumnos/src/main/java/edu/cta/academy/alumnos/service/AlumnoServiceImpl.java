package edu.cta.academy.alumnos.service;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.cta.academy.alumnos.cliente.CursoFeignClient;
import edu.cta.academy.alumnos.model.FraseChiquito;
import edu.cta.academy.alumnos.repository.AlumnoRepository;
import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

/**
 * Realiza las tareas de aplicacion
 * Transactional implementa un try catch
 * */

@Service
public class AlumnoServiceImpl implements AlumnoService{
	
	@PersistenceContext
	EntityManager em;
	
	/**Usamos este objeto para saber a qué curso pertenece un alumno.*/
	@Autowired
	CursoFeignClient cursoFeignClient;

	@Autowired //El alumnoRepository instanciado por spring, se queda en la variable.
	AlumnoRepository alumnoRepository;

	//GET
	@Override
	@Transactional(readOnly=true) //Optimizo para no bloquear la tabla y permitir el acceso concurrente "lectura sucia".
	public Iterable<Alumno> consultarTodos() {
		return this.alumnoRepository.findAll();
	}

	//GETBYID
	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> consultarPorId(Long id) {	
		return this.alumnoRepository.findById(id);
	}

	//DELETE
	@Override
	@Transactional
	public void borrarAlumnoPorId(Long id) {
		this.alumnoRepository.deleteById(id);
	}
	
	//SAVE
	@Override
	@Transactional
	public Alumno altaAlumno(Alumno alumno) {
		return	this.alumnoRepository.save(alumno);
	}

	//PUT
	@Override
	@Transactional
	public Optional<Alumno> modificarPorId(Alumno alumno, Long id) {
		
		 Optional <Alumno> optional = Optional.empty(); //Para no devolver null
		 /**Leer registro que puede existir o no.
		 Si existe actualizo, si no existe no hace nada*/
		 
		 optional = this.alumnoRepository.findById(id);
		 
		 if(optional.isPresent()) {
			 //Actualizo todos los campos menos el id y la fecha
			 Alumno alumnoLeido = optional.get();
			 //alumnoLeido.setNombre(alumno.getNombre());
			 //alumnoLeido está en estado Persistente -> si lo modifico, se modifica en la base de datos
			 BeanUtils.copyProperties(alumno, alumnoLeido, "id","creadoEn");
			 //this.alumnoRepository.save(alumnoLeido); No es encesario
			 
			 optional = Optional.of(alumnoLeido); //relleno
		 }
		 
		
		 return optional;
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByEdadBetween(int edadMin, int edadMax) {
		
		return this.alumnoRepository.findByEdadBetween(edadMin,edadMax);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByNombreContaining(String nombre) {
		
		return this.alumnoRepository.findByNombreContaining(nombre);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByNombreAndApellidoContaining(String patron) {
		
		return this.alumnoRepository.busquedaPorNombreOApellidoNativa(patron);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findByNombreAndApellidoContainingJPQL(String patron) {
		
		return this.alumnoRepository.busquedaPorNombreOApellidoJPQL(patron);
	}

	@Override
	@Transactional //no indicamos readOnly=true porque en los procedimientos no funciona
	public Iterable<Alumno> procedimientoAltaAlumnosHoy() {
		
		return this.alumnoRepository.procedimientoAltaAlumnosHoy();
	}

	@Override
	@Transactional
	public Map<String, Number> procedimientoEstadisticosEdad() {
		
		return this.alumnoRepository.procedimientoEstadisticosEdad(0, 0, 0);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findAll(Pageable pageable) {
		
		return this.alumnoRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Alumno> findByEdadBetween(int edadMin, int edadMax, Pageable pageable) {
		
		return this.alumnoRepository.findByEdadBetween(edadMin, edadMax, pageable);
	}

	@Override
	public Optional<FraseChiquito> obtenerFraseAleatoriaChiquito() {
		
		Optional<FraseChiquito> oc = Optional.empty();
		RestTemplate restTemplate = null;
		FraseChiquito frase = null;
		
		restTemplate = new RestTemplate();
		frase = restTemplate.getForObject("https://chiquitadas.es/api/quotes/avoleorrr", FraseChiquito.class);
		oc = Optional.of(frase);
		
		return oc;
	}

	@Override
	public Optional<Curso> obtenerCursoAlumno(Long idAlumno) {
		
		Optional<Curso>oc = Optional.empty();
		oc = this.cursoFeignClient.obtenerCursoAlumno(idAlumno);
		
		return oc;
	}
	
}
