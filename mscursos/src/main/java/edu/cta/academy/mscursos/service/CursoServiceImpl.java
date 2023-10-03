package edu.cta.academy.mscursos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.comun.entity.Alumno;
import edu.cta.academy.comun.entity.Curso;
import edu.cta.academy.mscursos.repo.CursoRepository;

@Service
@Transactional //A nivel de clase afecta a todos los métodos de la clase
public class CursoServiceImpl implements CursoService{
	
	@Autowired 
	CursoRepository cursoRepository;

	@Override
	@Transactional(readOnly=true)
	public Iterable<Curso> consultarTodos() {
		
		return this.cursoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Curso> consultarPorId(Long id) {
		
		return this.cursoRepository.findById(id);
	}

	@Override
	@Transactional
	public void borrarCursoPorId(Long id) {
		this.cursoRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public Curso altaCurso(Curso curso) {
		
		return this.cursoRepository.save(curso);
	}

	@Override
	@Transactional
	public Optional<Curso> modificarPorId(Curso curso, Long id) {
		
		Optional <Curso> optional = Optional.empty(); 
		 
		 optional = this.cursoRepository.findById(id);
		 
		 if(optional.isPresent()) {
	
			 Curso cursoLeido = optional.get();
			 BeanUtils.copyProperties(curso, cursoLeido, "id","creadoEn");
			 optional = Optional.of(cursoLeido); 
			 
		 }
		
		return optional;
	}

	@Override
	public Optional<Curso> asignarAlumnos(List<Alumno> alumnos, Long id) {
		
		Optional<Curso> oc = Optional.empty();
		oc = this.cursoRepository.findById(id);
		 
		if(oc.isPresent()) {
			 Curso curso_leido = oc.get();
			 alumnos.forEach(a -> curso_leido.addAlumno(a));
			 oc = Optional.of(curso_leido);
		}	 
		 
		return oc;
	}

	@Override
	public Optional<Curso> eliminarAlumno(Alumno alumno, Long id) {
		Optional<Curso> oc = Optional.empty();
		
		oc = this.cursoRepository.findById(id);
		 
		if(oc.isPresent()) {
			 Curso curso_leido = oc.get();
			 curso_leido.eliminarAlumno(alumno);
			 oc = Optional.of(curso_leido);
		}	 
		
		return oc;
	}


}
