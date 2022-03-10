package it.prova.myebay.dao.acquisto;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Utente;

public class AcquistoDAOImpl implements AcquistoDAO {
	
	private EntityManager entityManager;

	@Override
	public List<Acquisto> list() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Acquisto", Acquisto.class).getResultList();
	}

	@Override
	public Optional<Acquisto> findOne(Long id) throws Exception {
		Acquisto result = entityManager.find(Acquisto.class, id);
		return result != null ? Optional.of(result) : Optional.empty();
	}

	@Override
	public void update(Acquisto acquistoInstance) throws Exception {
		if (acquistoInstance == null) {
			throw new Exception("Problema valore in input");
		}
		acquistoInstance = entityManager.merge(acquistoInstance);
		
	}

	@Override
	public void insert(Acquisto acquistoInstance) throws Exception {
		if (acquistoInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(acquistoInstance);
	}

	@Override
	public void delete(Acquisto acquistoInstance) throws Exception {
		if (acquistoInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(acquistoInstance));	
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;	
	}
}