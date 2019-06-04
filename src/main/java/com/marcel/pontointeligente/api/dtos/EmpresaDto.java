package com.marcel.pontointeligente.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CNPJ;

public class EmpresaDto {
	
	private Long id;
	
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ não pode se vazio.")
	@CNPJ(message = "CNPJ inválido.")
	private String cnpj;
	
	public EmpresaDto() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
	public String toString() {
		return "EmpresaDto [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
}
