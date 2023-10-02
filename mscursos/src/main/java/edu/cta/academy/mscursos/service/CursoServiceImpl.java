package edu.cta.academy.mscursos.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.cta.academy.mscursos.entity.Curso;
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

}
