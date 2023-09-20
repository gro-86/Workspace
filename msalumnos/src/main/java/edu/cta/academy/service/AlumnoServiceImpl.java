package edu.cta.academy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.cta.academy.entity.Alumno;
import edu.cta.academy.repository.AlumnoRepository;

/**
 * Realiza las tareas de aplicacion
 * */

@Service
public class AlumnoServiceImpl implements AlumnoService{

	@Autowired //El alumnoRepository instanciado por spring, se queda en la variable.
	AlumnoRepository alumnoRepository;

	//GET
	@Override
	public Iterable<Alumno> consultarTodos() {
		return this.alumnoRepository.findAll();
	}

	//GETBYID
	@Override
	public Optional<Alumno> consultarPorId(Long id) {	
		return this.alumnoRepository.findById(id);
	}

	//DELETE
	@Override
	public void borrarAlumnoPorId(Long id) {
		this.alumnoRepository.deleteById(id);
	}
	
	//SAVE
	@Override
	public Alumno altaAlumno(Alumno alumno) {
		return	this.alumnoRepository.save(alumno);
	}

	//PUT
	@Override
	public Optional<Alumno> modificarPorId(Alumno alumno, Long id) {
		
		 Optional <Alumno> alumnoNew = alumnoRepository.findById(id);
		 
		 
		 return alumnoNew;
	}
	
}
