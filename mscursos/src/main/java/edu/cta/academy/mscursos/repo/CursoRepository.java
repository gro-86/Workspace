package edu.cta.academy.mscursos.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import edu.cta.academy.mscursos.entity.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long>{

}
