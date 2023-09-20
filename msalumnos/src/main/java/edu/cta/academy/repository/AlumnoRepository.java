package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.entity.Alumno;

/**
 * Se dedica a interactuar con la base de datos
 * */

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno,Long> {

}
