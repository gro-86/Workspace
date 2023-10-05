package edu.cta.academy.alumnos.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import edu.cta.academy.comun.entity.Alumno;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //Laza servidor en un puerto aleatorio
public class AlumnoControllerTest {
	
	@LocalServerPort
	private int port; //Para inyectar el puerto donde se realiza la prueba
	
	@Autowired
	TestRestTemplate template;
	
	@Test //Con esta anotación indico que es un caso de pruebas/test
	public void testGetAlumnos() {
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).doesNotContainNull();
	}
	
	@Test //Con esta anotación indico que es un caso de pruebas/test
	public void testGetAlumnosNull() {
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).containsOnlyNulls();
	}
	
	//TODO hacer método que compruebe que todos los ids sean distintos de 0 con una lambda
	@Test //Con esta anotación indico que es un caso de pruebas/test
	public void testGetAlumnosIdDistintos0() {
		Alumno[] arrayAl = this.template.withBasicAuth("admin", "zaragoza").getForObject("http://localhost:"+port+"/alumno", Alumno[].class);
		List<Alumno> la = Arrays.asList(arrayAl);
		assertThat(la).allSatisfy(alumno -> assertThat(alumno.getId()).isNotEqualTo(0l));
		//assertThat(la).allMatch(p -> p.getId() >=0);
	}
	
	
	@BeforeAll //Para ejecutar antes de todos los métodos @Test
	public static void antesDeTodosLosTests() {
		System.out.println("antesDeTodosLosTests()");
	}
	
	@AfterAll //Para ejecutar después de todos los métodos @Test
	public static void despuesDeTodosLosTests() {
		System.out.println("despuesDeTodosLosTests()");
	}
	
	@BeforeEach //Para ejecutar antes de cada test
	public void antesDeCadaTest() {
		System.out.println("antesDeCadaTest()");
	}
	
	@AfterEach //Para ejecutar después de cada test
	public void despuesDeCadaTest() {
		System.out.println("despuesDeCadaTest()");
	}

}
