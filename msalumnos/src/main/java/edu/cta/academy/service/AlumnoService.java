package edu.cta.academy.service;

import java.util.Optional;

import edu.cta.academy.entity.Alumno;

//Define lo que hacemos
public interface AlumnoService {

	
	//findAll
	Iterable <Alumno> consultarTodos();
	
	//findById
	Optional <Alumno> consultarPorId (Long id); //Si existe lo devuelve. Si no no lo devuelve, pero nunca nulo.
	
	//deleteById
	void borrarAlumnoPorId(Long id);
	
	//insert
	Alumno altaAlumno (Alumno alumno);
	
	//updateById
	Optional<Alumno> modificarPorId (Alumno alumno, Long id);
	
	//findByEdad
	Iterable<Alumno>findByEdadBetween(int edadMin, int edadMax);
	
	//findByEdad
	Iterable<Alumno>findByNombreContaining(String nombre);
	
	//findByEdad
		Iterable<Alumno>findByNombreAndApellidoContaining(String patron);
	
	
}
