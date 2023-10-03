package edu.cta.academy.mscursos.service;

import java.util.List;
import java.util.Optional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;

public interface CursoService {
	
	//findAll
	Iterable <Curso> consultarTodos();
		
	//findById
	Optional <Curso> consultarPorId (Long id); 
		
	//deleteById
	void borrarCursoPorId(Long id);
		
	//insert
	Curso altaCurso (Curso curso);
		
	//updateById
	Optional<Curso> modificarPorId (Curso curso, Long id);
	
	Optional <Curso> asignarAlumnos (List<Alumno> alumnos, Long id);
	
	Optional<Curso> eliminarAlumno (Alumno alumno, Long id);

}
