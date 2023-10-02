package edu.cta.academy.mscursos.service;

import java.util.Optional;

import edu.cta.academy.mscursos.entity.Curso;

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
	

}
