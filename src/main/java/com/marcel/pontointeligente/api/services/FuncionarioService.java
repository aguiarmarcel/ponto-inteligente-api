package com.marcel.pontointeligente.api.services;

import java.util.Optional;

import com.marcel.pontointeligente.api.domain.Funcionario;

public interface FuncionarioService {

	/**
	 * Persistir um funcionario na base de dados.
	 *
	 *@param funcionario
	 *@return Funcionario
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Retorna um funcionario dado um CPF.
	 *
	 *@param cpf
	 *@return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorCpf(String cpf);
	
	/**
	 * Retorna um funcionario dado um email.
	 *
	 *@param email
	 *@return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorEmail(String email);
	
	/**
	 * Retorna um funcionario dado um Id.
	 *
	 *@param id
	 *@return Optional<Funcionario>
	 */
	Optional<Funcionario> buscarPorId(Long id);
	
}
