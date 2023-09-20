package edu.cta.academy.service;

import java.util.Optional;

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
		
		 Optional <Alumno> alumnoNew = alumnoRepository.findById(id);
		 
		 return alumnoNew;
	}
	
}
