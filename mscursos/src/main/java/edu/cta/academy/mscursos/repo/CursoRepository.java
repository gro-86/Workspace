package edu.cta.academy.mscursos.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.comun.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>{

}
