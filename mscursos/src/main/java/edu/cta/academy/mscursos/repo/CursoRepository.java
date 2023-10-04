package edu.cta.academy.mscursos.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.comun.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>{

	//Añadimos una query nativa, que dado un ID de alumno, devuelve el curso al que está asignado
	@Query(value = "select * from cursos where id = (select curso_id from cursos_lista_alumnos where lista_alumnos_id = ?1)", nativeQuery = true)
	public Optional<Curso>obtenerCursoAlumnoNativa(Long id_alumno);
	
}
