package edu.cta.academy.mscursos.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.mscursos.entity.Curso;

@Repository
public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{

}
