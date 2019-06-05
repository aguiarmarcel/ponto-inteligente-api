package com.marcel.pontointeligente.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcel.pontointeligente.api.domain.Funcionario;
import com.marcel.pontointeligente.api.dtos.FuncionarioDto;
import com.marcel.pontointeligente.api.response.Response;
import com.marcel.pontointeligente.api.services.FuncionarioService;
import com.marcel.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public FuncionarioController() {
	}
	
	/**
	 * Atualiza os dados de um funcionário.
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @Throws NoSuchAlgorithmException
	 */
	@PutMapping(value="/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDto funcionarioDto, 
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Buscando funcionário: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		Optional<Funcionario> obj = funcionarioService.buscarPorId(id);
		
		if (!obj.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionario não encontrado."));
		}
		
		this.atualizarDadosFuncionario(obj.get(), funcionarioDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validando funcionário: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.persistir(obj.get());
		response.setData(this.converterFuncionarioDto(obj.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualizar dados do funcionário com base nos dados encontrados no DTO.
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @Throws NoSuchAlgorithmException
	 */
	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result) 
		throws NoSuchAlgorithmException {
		
		funcionario.setNome(funcionarioDto.getNome());
		if (!funcionario.getEmail().equals(funcionarioDto.getEmail())) {
			this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail()).ifPresent(
					func -> result.addError(new ObjectError("email", "Email já existente.")));
			funcionario.setEmail(funcionarioDto.getEmail());
		}
		funcionario.setQtdTrabalhoAlmoco(null);
		funcionarioDto.getValorHoraAlmoco().ifPresent(
				qtdHorasAlmoco -> funcionario.setQtdTrabalhoAlmoco(Float.valueOf(qtdHorasAlmoco)));
		funcionario.setQtdHorasTrabalhoDia(null);
		funcionarioDto.getValorHorasTrabalhoDia().ifPresent(
				qtdHorasTrabDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabDia)));
		funcionario.setValorHora(null);
		funcionarioDto.getValorHora().ifPresent(
				valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
		if (funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(PasswordUtils.gerarBCRYPT(funcionarioDto.getSenha().get()));
		}
	}
	
	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionario.getqtdTrabalhoAlmocoOpt().ifPresent(
				qtdValorHorasAlmoco -> funcionarioDto.setValorHoraAlmoco(Optional.of(Float.toString(qtdValorHorasAlmoco))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
				qtdHorasTrabDia -> funcionarioDto.setValorHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
		funcionario.getValorHoraOpt().ifPresent(
				valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
		
		return funcionarioDto;
	}
}
