package edu.cta.academy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.StoredProcedureParameter;

@Entity //Tabla asociada a una BD
@Table(name = "alumnos")
@NamedStoredProcedureQueries(
		{
			@NamedStoredProcedureQuery(name="Alumno.alumnosRegistradosHoy", procedureName = "obtenerAlumnosRegistradosHoy", resultClasses = edu.cta.academy.entity.Alumno.class),
			@NamedStoredProcedureQuery(name="Alumno.alumnosEdadMediaMinMax", procedureName = "calcular_max_min_media_edad",
			parameters = {
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadMax"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Integer.class , name ="edadMin"),
					@StoredProcedureParameter(mode = ParameterMode.INOUT , type = Float.class , name ="edadMedia")
			})
		}
		)
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremento de ID en Mysql
	private Long id;
	
	@Size(min = 3, max = 20) //tamaño mínimo y máximo
	private String nombre;
	
	@NotEmpty //no admite apellidos vacíos
	@NotBlank //no admite apellidos en blanco
	private String apellido;
	
	@Email //formato correcto para email
	private String email;
	
	@Min(0) //mínimo de 0 y máximo 130
	@Max(130)
	private int edad;
	
	@Column(name="creado_en")
	private LocalDateTime creadoEn;
	
	@Lob //Large Object Binary
	@JsonIgnore //Evita que, aunque tengas los getters y setter, se serialice el atributo JSON
	private byte[] foto;
	
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
	
	
	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	/**
	 * Se usa el método como flag. Si tiene una foto asociada, valdrá un número. Si no, será nulo.
	 * */
	public Integer getFotoHashCode() {
		
		Integer iDev = null;
		
		if (this.foto!=null) {
			iDev = this.foto.hashCode();
		}
		
		return iDev;
	}

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
