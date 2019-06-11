package com.marcel.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.marcel.pontointeligente.api.domain.Lancamento;
import com.marcel.pontointeligente.api.repositories.LancamentoRepository;
import com.marcel.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(LancamentoServiceImpl.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@CachePut("lancamentoPorId")
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo lancamento: {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}
	
	@Cacheable("lancamentoPorId")
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lançamento para o ID {}", id);
		Optional<Lancamento> obj = this.lancamentoRepository.findById(id);
		return obj;
	}

	@Override
	public Page<Lancamento> buscaPorFuncionarioId(Long funcionarioId, PageRequest pageResquest) {
		log.info("Paginando lançamentos do funcionário de ID {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageResquest);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo um lançamento para o ID {}", id);
		this.lancamentoRepository.deleteById(id);
	}
}
