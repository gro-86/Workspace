package edu.cta.academy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.cta.academy.entity.Alumno;

/**
 * Se dedica a interactuar con la base de datos
 * */

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno,Long> {
	
	//Vamos a añadir nuevas operaciones en la base de datos
	
	//1. Keyword queries - Consultas por palabras clave
	//Consulta los alumnos que estén en un rango de edad
	Iterable<Alumno>findByEdadBetween(int edadMin, int edadMax);
	//consultar los alumnos que contengan un numbre dado
	Iterable<Alumno>findByFirstNameContaining(String nombre);
	
	//2. JPQL - HQL
	
	
	
	//3. Native queries
	//4. Criteria API (Libreria para hacer consultas con este API específico de JPA)
	//5. Stored Procedures
}
