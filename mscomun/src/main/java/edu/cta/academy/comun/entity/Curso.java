package edu.cta.academy.comun.entity;


import javax.persistence.Table;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@NotBlank
	private String nombre;
	
	@OneToMany (fetch = FetchType.LAZY)
	//@JsonIgnore
	private List<Alumno>listaAlumnos;
	
	public void addAlumno (Alumno alumno) {
		this.listaAlumnos.add(alumno);
	}
	
	public void eliminarAlumno (Alumno alumno) {
		this.listaAlumnos.remove(alumno);
	}
	

}
