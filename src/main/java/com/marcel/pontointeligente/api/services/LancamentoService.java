package com.marcel.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.marcel.pontointeligente.api.domain.Lancamento;

public interface LancamentoService {

	/**
	 * Persistir um lançamento na base de dados.
	 *
	 *@param lançamento
	 *@return Lancamento
	 */
	Lancamento persistir(Lancamento lancamento);
	
	/**
	 * Retorna um lancamento por um Id.
	 *
	 *@param id
	 *@return Optional<Lancamento>
	 */
	Optional<Lancamento> buscarPorId(Long id);
	
	/**
	 * Retorna uma lista paginada de lançamentos de um determinado funcionário.
	 *
	 *@param funcionarioId
	 *@param pageRequest
	 *@return Page<Lancamento>
	 */
	Page<Lancamento> buscaPorFuncionarioId(Long funcionarioId, PageRequest ageResquest);
	
	/**
	 * Remove um lançamento  da base de dados.
	 *
	 *@param id
	 */
	void remover(Long id);
}
