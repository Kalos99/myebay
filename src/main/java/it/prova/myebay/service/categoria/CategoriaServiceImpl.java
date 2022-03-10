package it.prova.myebay.service.categoria;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.myebay.dao.categoria.CategoriaDAO;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.web.listener.LocalEntityManagerFactoryListener;

public class CategoriaServiceImpl implements CategoriaService{
	
	private CategoriaDAO categoriaDAO;

	@Override
	public List<Categoria> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			categoriaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return categoriaDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Categoria caricaSingolaCategoria(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			categoriaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return categoriaDAO.findOne(id).get();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Categoria categoriaInstance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserisciNuovo(Categoria categoriaInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			categoriaDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			categoriaDAO.insert(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
		
	}

	@Override
	public void rimuovi(Categoria categoriaInstance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

}
