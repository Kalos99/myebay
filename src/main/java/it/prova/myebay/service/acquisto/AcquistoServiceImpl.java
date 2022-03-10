package it.prova.myebay.service.acquisto;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.myebay.dao.acquisto.AcquistoDAO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.web.listener.LocalEntityManagerFactoryListener;

public class AcquistoServiceImpl implements AcquistoService{
	
	private AcquistoDAO acquistoDAO;

	@Override
	public List<Acquisto> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			acquistoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return acquistoDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Acquisto caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			acquistoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return acquistoDAO.findOne(id).get();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Acquisto acquistoInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			acquistoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			acquistoDAO.update(acquistoInstance);

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
	public void inserisciNuovo(Acquisto acquistoInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			acquistoDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			acquistoDAO.insert(acquistoInstance);

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
	public void rimuovi(Acquisto acquistoInstance) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAcquistoDAO(AcquistoDAO acquistoDAO) {
		this.acquistoDAO = acquistoDAO;
	}

}