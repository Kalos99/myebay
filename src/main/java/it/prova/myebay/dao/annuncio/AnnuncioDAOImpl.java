package it.prova.myebay.dao.annuncio;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public class AnnuncioDAOImpl implements AnnuncioDAO {

	private EntityManager entityManager;

	@Override
	public List<Annuncio> list() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Annuncio", Annuncio.class).getResultList();
	}

	@Override
	public Optional<Annuncio> findOne(Long id) throws Exception {
		Annuncio result = entityManager.find(Annuncio.class, id);
		return result != null ? Optional.of(result) : Optional.empty();
	}

	@Override
	public void update(Annuncio annuncioInstance) throws Exception {
		if (annuncioInstance == null) {
			throw new Exception("Problema valore in input");
		}
		annuncioInstance = entityManager.merge(annuncioInstance);

	}

	@Override
	public void insert(Annuncio annuncioInstance) throws Exception {
		if (annuncioInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(annuncioInstance);	

	}

	@Override
	public void delete(Annuncio annuncioInstance) throws Exception {
		if (annuncioInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(annuncioInstance));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Annuncio> findAllByCategoria(Categoria categoriaInput) throws Exception {
		TypedQuery<Annuncio> query = entityManager.createQuery("select a FROM Annuncio a join a.categorie c where c = :categoria",
				Annuncio.class);
		query.setParameter("categoria", categoriaInput);
		return query.getResultList();
	}

	@Override
	public Annuncio findByIdFetchingCategorie(Long id) throws Exception {
		TypedQuery<Annuncio> query = entityManager
				.createQuery("select a FROM Annuncio a left join fetch a.categorie c where a.id = :idAnnuncio", Annuncio.class);
		query.setParameter("idArticolo", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

}
