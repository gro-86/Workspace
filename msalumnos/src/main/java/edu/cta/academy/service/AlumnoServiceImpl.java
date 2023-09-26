package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.entity.Alumno;
import edu.cta.academy.repository.AlumnoRepository;

/**
 * Realiza las tareas de aplicacion
 * Transactional implementa un try catch
 * */

@Service
public class AlumnoServiceImpl implements AlumnoService{

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
		// TODO Auto-generated method stub
		return this.alumnoRepository.busquedaPorNombreOApellidoNativa(patron);
	}
	
}
