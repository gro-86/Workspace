package edu.cta.academy.alumnos.controller;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.util.UtilTest;

@SpringBootTest
@AutoConfigureMockMvc
public class AlumnoControllerTest3 {
	
	@Autowired
	private MockMvc mockMvc;
	
	ObjectMapper om;
	
	@Test //Con esta anotación indico que es un caso de pruebas/test
	@WithMockUser(username = "admin", password = "zaragoza")
	public void insertarAlumno() throws Exception {
		Alumno alumno = new Alumno();
		alumno.setNombre("Julia");
		alumno.setApellido("Bárcenas");
		alumno.setEdad(23);
		alumno.setEmail("juba@proton.es");
		
		ObjectNode  objectNode = om.createObjectNode();
		objectNode.put("nombre", "Julia");
		objectNode.put("apellido", "Bárcenas");
		objectNode.put("edad", "23");
		objectNode.put("email", "juba@proton.es");
		
		String alumno_json = UtilTest.toJSON(alumno);
		
		this.mockMvc.perform(post("/alumno")
				.contentType(MediaType.APPLICATION_JSON)
				.content(alumno_json))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/hal+json"));
		
		
		
	}
	
	//HACER UN TEST EN EL QUE SE HAGA UN POST CON DATOS INCORRECTOS DEL ALUMNO (NO PASAN VALIDACION)

}
