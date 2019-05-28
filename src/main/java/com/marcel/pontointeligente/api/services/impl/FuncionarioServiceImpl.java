package com.marcel.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcel.pontointeligente.api.domain.Funcionario;
import com.marcel.pontointeligente.api.repositories.FuncionarioRepository;
import com.marcel.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(FuncionarioServiceImpl.class);
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionario: {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}
	
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando um funcionario para o CPF {}", cpf);
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}
	
	
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando um funcionario para o EMAIL {}", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}
	
	
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando um funcionario para o ID {}", id);
		Optional<Funcionario> obj = funcionarioRepository.findById(id);
		return obj;
	}
}
