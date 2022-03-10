package it.prova.myebay.dao.categoria;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public class CategoriaDAOImpl implements CategoriaDAO{
	
	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Optional<Categoria> findOne(Long id) throws Exception {
		Categoria result = entityManager.find(Categoria.class, id);
		return result != null ? Optional.of(result) : Optional.empty();
	}

	@Override
	public void update(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		categoriaInstance = entityManager.merge(categoriaInstance);
	}

	@Override
	public void insert(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(categoriaInstance);	
	}

	@Override
	public void delete(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(categoriaInstance));	
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;	
	}

	@Override
	public Categoria findByIdFetchingAnnunci(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select c FROM Categoria c left join fetch c.annunci a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}
}
