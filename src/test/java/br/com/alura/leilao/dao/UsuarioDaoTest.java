package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;

class UsuarioDaoTest {

	private UsuarioDao dao;	
	private EntityManager em;	
	
	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();
	}
	
	@AfterEach
	public void afterEach() {		
		em.getTransaction().rollback();
	}	
	
	@Test
	void testBuscarEncontraUsuarioUserName() {
		Usuario encontrado = dao.buscarPorUsername(criarUsuario().getNome());
		Assert.assertNotNull(encontrado);
	}

	@Test
	void testBuscarNaoEncontraUsuarioUserName() {
		criarUsuario();		
		
		Assert.assertThrows(NoResultException.class, () -> dao.buscarPorUsername("Tadeu"));
	}
	
	@Test
	void testRemoverUsuario() {
		Usuario usuario = criarUsuario();
		dao.deletar(usuario);		
		
		Assert.assertThrows(NoResultException.class, () -> dao.buscarPorUsername(usuario.getNome()));
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Luis")
				.comEmail("luis@email.com")
				.comSenha("12345678")
				.criar();
		
		em.persist(usuario);
		return usuario;
	}
}
