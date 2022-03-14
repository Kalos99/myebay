package it.prova.myebay.dao.annuncio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.myebay.exceptions.InvalidUserException;
import it.prova.myebay.model.Acquisto;
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
	public List<Annuncio> listOpened() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Annuncio where aperto = true", Annuncio.class).getResultList();
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
		query.setParameter("idAnnuncio", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Annuncio> findByExample(Annuncio example) throws Exception {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct a from Annuncio a left join a.categorie c where a.id = a.id and a.utenteInserimento.stato = 'ATTIVO' ");

		if (StringUtils.isNotEmpty(example.getTestoAnnuncio())) {
			whereClauses.add(" a.testoAnnuncio  like :testo ");
			paramaterMap.put("testo", "%" + example.getTestoAnnuncio() + "%");
		}
		if (example.getPrezzo() != null) {
			whereClauses.add(" a.prezzo >= :prezzo");
			paramaterMap.put("prezzo", example.getPrezzo());
		}
		if (example.getAperto() != null) {
			whereClauses.add(" a.aperto = :aperto ");
			paramaterMap.put("aperto", true);
		}
		if (example.getDataPubblicazione() != null) {
			whereClauses.add(" a.dataPubblicazione >= :dataPubblicazione ");
			paramaterMap.put("dataPubblicazione", example.getDataPubblicazione());
		}
		if (example.getCategorie().size() != 0) {
			whereClauses.add(" c IN :categorie  ");
			paramaterMap.put("categorie", example.getCategorie());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Annuncio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Annuncio.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		
		System.out.println(typedQuery);

		return typedQuery.getResultList();
	}

	@Override
	public Annuncio findByIdFetchingCategorieAndCreatore(Long id) throws Exception {
		TypedQuery<Annuncio> query = entityManager
				.createQuery("select a FROM Annuncio a left join fetch a.categorie c left join fetch a.utenteInserimento u where a.id = :idAnnuncio", Annuncio.class);
		query.setParameter("idAnnuncio", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Annuncio> findByExampleConUtente(Annuncio example) throws Exception {
		if(example.getUtenteInserimento() == null || example.getUtenteInserimento().getId() == null) {
			throw new InvalidUserException("Errore: utente non trovato");
		}
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct a from Annuncio a left join a.categorie c where a.utenteInserimento.id = :idUtente ");
		

		if (StringUtils.isNotEmpty(example.getTestoAnnuncio())) {
			whereClauses.add(" a.testoAnnuncio like :testo ");
			paramaterMap.put("testo", "%" + example.getTestoAnnuncio() + "%");
		}
		if (example.getPrezzo() != null) {
			whereClauses.add(" a.prezzo >= :prezzo");
			paramaterMap.put("prezzo", example.getPrezzo());
		}
		if (example.getAperto() != null) {
			whereClauses.add(" a.aperto = :aperto ");
			paramaterMap.put("aperto", example.getAperto());
		}
		if (example.getDataPubblicazione() != null) {
			whereClauses.add(" a.dataPubblicazione >= :dataPubblicazione ");
			paramaterMap.put("dataPubblicazione", example.getDataPubblicazione());
		}
		if (example.getCategorie().size() != 0) {
			whereClauses.add(" c IN :categorie  ");
			paramaterMap.put("categorie", example.getCategorie());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Annuncio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Annuncio.class);
		typedQuery.setParameter("idUtente", example.getUtenteInserimento().getId());

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		
		System.out.println(typedQuery);

		return typedQuery.getResultList();
	}
	
	@Override
	public List<Annuncio> findAllByCreatoreAnnuncio(Long id) throws Exception {
		if(id == null) {
			throw new InvalidUserException("Errore: utente non trovato");
		}
		TypedQuery<Annuncio> query = entityManager.createQuery("select a FROM Annuncio a join a.utenteInserimento u where u.id = :idUtenteInserimento", Annuncio.class);
		query.setParameter("idUtenteInserimento", id);
		return query.getResultList();
	}

}
