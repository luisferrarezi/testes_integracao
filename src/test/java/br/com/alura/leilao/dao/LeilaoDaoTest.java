package br.com.alura.leilao.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

class LeilaoDaoTest {
	
	private LeilaoDao dao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {		
		em.getTransaction().rollback();
	}
	
	@Test
	void testCriarLeilao() {
		Leilao leilao = criarLeilao();
		
		Leilao salvo = dao.buscarPorId(leilao.getId());		
		Assert.assertNotNull(salvo);
	}

	@Test
	void testAtualizarLeilao() {
		Leilao leilao = criarLeilao();
		
		leilao.setNome("Cavalo");
		leilao.setValorInicial(new BigDecimal("100000"));
		leilao = dao.salvar(leilao);		
		Leilao salvo = dao.buscarPorId(leilao.getId());
		
		Assert.assertEquals("Cavalo", salvo.getNome());
		Assert.assertEquals(new BigDecimal("100000"), salvo.getValorInicial());
	}
	
	private Leilao criarLeilao() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Luis")
				.comEmail("luis@email.com")
				.comSenha("12345678")
				.criar();
		em.persist(usuario);
		
		Leilao leilao = new LeilaoBuilder()
				.comNome("Teste")
				.comValorInicial("200")
				.comDataAbertura(LocalDate.now())
				.comUsuario(usuario)
				.criar();
		return dao.salvar(leilao);
	}
}
