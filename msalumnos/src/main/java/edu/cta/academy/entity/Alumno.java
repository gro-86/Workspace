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
	
	public Alumno() {
		
	}
	
	public Alumno(Long id, String nombre, String apellido, String email, int edad, LocalDateTime creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.creadoEn = creadoEn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}

	@Column(name="creado_en")
	private LocalDateTime creadoEn;
	
	@PrePersist //Antes de que se inserte un alumno, se ejecuta este método
	private void generarFechaCreacion() {
		this.creadoEn = LocalDateTime.now(); //Fecha y hora actual
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + "\n, nombre=" + nombre + "\n, apellido=" + apellido + "\n, email=" + email + "\n, edad="
				+ edad + "\n, creadoEn=" + creadoEn + "]";
	}
	
	

}
