package com.marcel.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.marcel.pontointeligente.api.domain.Empresa;
import com.marcel.pontointeligente.api.domain.Funcionario;
import com.marcel.pontointeligente.api.domain.Lancamento;
import com.marcel.pontointeligente.api.enums.PerfilEnum;
import com.marcel.pontointeligente.api.enums.TipoEnum;
import com.marcel.pontointeligente.api.utils.PasswordUtils;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class LancamentoRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private Long FuncionarioId;
	
	@Before
	public void setUp() throws Exception{
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.FuncionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Marcel");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCRYPT("123456"));
		funcionario.setCpf("03385220424");
		funcionario.setEmail("aguiar_marcel@hotmail.com");
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Java Enterprise");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

	private Lancamento obterDadosLancamento(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}

	@After
	public void tearDown() throws Exception{
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(FuncionarioId);
		assertEquals(2, lancamentos.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(FuncionarioId, page);
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	
}
