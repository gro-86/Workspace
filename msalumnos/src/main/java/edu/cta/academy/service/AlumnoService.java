package edu.cta.academy.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.cta.academy.entity.Alumno;
import edu.cta.academy.model.FraseChiquito;

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
	
	Iterable<Alumno>findByEdadBetween(int edadMin, int edadMax,Pageable pageable);
	
	//findByEdad
	Iterable<Alumno>findByNombreContaining(String nombre);
	
	//findByEdad
	Iterable<Alumno>findByNombreAndApellidoContaining(String patron);
	
	Iterable<Alumno>findByNombreAndApellidoContainingJPQL(String patron);
	
	Iterable<Alumno> procedimientoAltaAlumnosHoy();
	
	Map<String, Number> procedimientoEstadisticosEdad();
	
	Iterable<Alumno> findAll(Pageable pageable);
	
	//NOTA: No se define la función de la API de chiquito aquí
	Optional<FraseChiquito>obtenerFraseAleatoriaChiquito();
	
	
}
