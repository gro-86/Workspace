package edu.cta.academy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity //Tabla asociada a una BD
@Table(name = "alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremento de ID en Mysql
	private Long id;
	
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	
	@Column(name="creado_en")
	private LocalDateTime creadoEn;
	
	@PrePersist //Antes de que se inserte un alumno, se ejecuta este método
	private void generarFechaCreacion() {
		this.creadoEn= LocalDateTime.now(); //Fecha y hora actual
	};

}
