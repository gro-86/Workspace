package edu.cta.academy.repository;

import org.springframework.data.jpa.repository.Query;
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
	Iterable<Alumno>findByNombreContaining(String nombre);
	
	//2. JPQL - HQL -- Similar a SQL- en vez de referirnos a las tablas, lo hace a las entidades
	@Query(value = "Select a FROM Alumno a where a.nombre like %?1% or a.apellido like %?1%" )
	Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
	
	//3. Native queries
	@Query(value = "SELECT * FROM ALUMNOS a WHERE a.NOMBRE LIKE %?1% OR a.APELLIDO LIKE %?1%", nativeQuery = true)
	Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	//4. Criteria API (Libreria para hacer consultas con este API específico de JPA)
	//5. Stored Procedures
}
