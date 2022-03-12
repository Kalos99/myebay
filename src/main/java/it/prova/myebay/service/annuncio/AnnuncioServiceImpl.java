package it.prova.myebay.service.annuncio;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.dao.acquisto.AcquistoDAO;
import it.prova.myebay.dao.annuncio.AnnuncioDAO;
import it.prova.myebay.dao.categoria.CategoriaDAO;
import it.prova.myebay.dao.utente.UtenteDAO;
import it.prova.myebay.exceptions.AnnuncioChiusoException;
import it.prova.myebay.exceptions.CreditoNonSufficienteException;
import it.prova.myebay.exceptions.ElementNotFoundException;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.Utente;
import it.prova.myebay.web.listener.LocalEntityManagerFactoryListener;

public class AnnuncioServiceImpl implements AnnuncioService{
	
	private AnnuncioDAO annuncioDAO;
	private CategoriaDAO categoriaDAO;
	private UtenteDAO utenteDAO;
	private AcquistoDAO acquistoDAO;

	@Override
	public List<Annuncio> listAll() throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Annuncio caricaSingoloAnnuncio(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.findOne(id).get();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Annuncio annuncioInstance, String[] categorie) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);
			
			Annuncio annuncioDaModificare = annuncioDAO.findByIdFetchingCategorie(annuncioInstance.getId());
			
			annuncioDaModificare.setTestoAnnuncio(annuncioInstance.getTestoAnnuncio());
			annuncioDaModificare.setPrezzo(annuncioInstance.getPrezzo());
			annuncioDaModificare.getCategorie().clear();
			
			Set<Categoria> categorieAnnuncio = new HashSet<Categoria>();
			for(String categoriaId : categorie!=null?categorie:new String[] {}) {
				if(NumberUtils.isCreatable(categoriaId)) {
					Categoria categoriaDaInserire = new Categoria();
					categoriaDaInserire.setId(Long.parseLong(categoriaId));
					categorieAnnuncio.add(categoriaDaInserire);
				}
			}
			annuncioDaModificare.setCategorie(categorieAnnuncio);
			// eseguo quello che realmente devo fare
			annuncioDAO.update(annuncioDaModificare);

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
	public void inserisciNuovo(Annuncio annuncioInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			annuncioDAO.insert(annuncioInstance);

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
	public void rimuovi(Long idAnnuncioToRemove) throws Exception {
		
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			annuncioDAO.setEntityManager(entityManager);
			Annuncio annuncioToRemove = annuncioDAO.findByIdFetchingCategorie(idAnnuncioToRemove);
			if (annuncioToRemove == null)
				throw new ElementNotFoundException("Annuncio con id: " + idAnnuncioToRemove + " non trovato.");
			
			// eseguo quello che realmente devo fare
			if (!annuncioToRemove.getAperto())
				throw new AnnuncioChiusoException(
						"Impossibile rimuovere: l'annuncio è stato chiuso");

			annuncioDAO.delete(annuncioToRemove);
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
	public void collegaACategoriaEsistente(Categoria categoriaInstance, Annuncio annuncioInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// 'attacco' alla sessione di hibernate i due oggetti
			// così jpa capisce che se risulta presente quel cd non deve essere inserito
			annuncioInstance = entityManager.merge(annuncioInstance);
			// attenzione che genereInstance deve essere già presente (lo verifica dall'id)
			// se così non è viene lanciata un'eccezione
			categoriaInstance = entityManager.merge(categoriaInstance);

			annuncioInstance.addToCategorie(categoriaInstance);
			// l'update non viene richiamato a mano in quanto
			// risulta automatico, infatti il contesto di persistenza
			// rileva che cdInstance ora è dirty vale a dire che una sua
			// proprieta ha subito una modifica (vale anche per i Set ovviamente)
			// inoltre se risultano già collegati lo capisce automaticamente grazie agli id

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
	public void disassociaDaCategoriaEsistente(Categoria categoriaInstance, Annuncio annuncioInstance)
			throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);
			categoriaDAO.setEntityManager(entityManager);

			// 'attacco' alla sessione di hibernate i due oggetti
			// così jpa capisce che se risulta presente quel cd non deve essere inserito
			annuncioInstance = annuncioDAO.findOne(annuncioInstance.getId()).get();
			// attenzione che genereInstance deve essere già presente (lo verifica dall'id)
			// se così non è viene lanciata un'eccezione
			categoriaInstance = categoriaDAO.findOne(categoriaInstance.getId()).get();

			annuncioInstance.removeFromCategorie(categoriaInstance);
			// l'update non viene richiamato a mano in quanto
			// risulta automatico, infatti il contesto di persistenza
			// rileva che cdInstance ora è dirty vale a dire che una sua
			// proprieta ha subito una modifica (vale anche per i Set ovviamente)
			// inoltre se risultano già collegati lo capisce automaticamente grazie agli id

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
	public Annuncio caricaAnnuncioSingoloConCategorie(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.findByIdFetchingCategorie(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void setAnnuncioDAO(AnnuncioDAO annuncioDAO) {
		this.annuncioDAO = annuncioDAO;
	}

	@Override
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;	
	}
	
	@Override
	public void setUtenteDAO(UtenteDAO utenteDAO) {
		this.utenteDAO = utenteDAO;
		
	}

	@Override
	public void setAcquistoDAO(AcquistoDAO acquistoDAO) {
		this.acquistoDAO = acquistoDAO;
	}

	@Override
	public List<Annuncio> findByExample(Annuncio example) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.findByExample(example);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public Annuncio caricaAnnuncioSingoloConCategorieECreatore(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.findByIdFetchingCategorieAndCreatore(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<Annuncio> findByExampleConUtente(Annuncio example) throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.findByExampleConUtente(example);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}
	
	@Override
	public List<Annuncio> listAllOpened() throws Exception {
		// questo è come una connection
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return annuncioDAO.listOpened();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			LocalEntityManagerFactoryListener.closeEntityManager(entityManager);
		}
	}

	@Override
	public void eseguiAcquisto(Long idUtente, Long idAnnuncio) throws Exception {
		EntityManager entityManager = LocalEntityManagerFactoryListener.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			annuncioDAO.setEntityManager(entityManager);
			utenteDAO.setEntityManager(entityManager);
			acquistoDAO.setEntityManager(entityManager);

			
			Annuncio annuncioInstance = annuncioDAO.findOne(idAnnuncio).get();
			Utente utenteInstance = utenteDAO.findOne(idUtente).get();
			
			if(annuncioInstance.getPrezzo() > utenteInstance.getCreditoResiduo()) {
				throw new CreditoNonSufficienteException("Impossibile effettuare l'acquisto: il credito residuo non è sufficiente"); 
			}
			utenteInstance.setCreditoResiduo(utenteInstance.getCreditoResiduo() - annuncioInstance.getPrezzo());
			annuncioInstance.setAperto(false);
			
			Acquisto acquistoInstance = new Acquisto(annuncioInstance.getTestoAnnuncio(), Calendar.getInstance().getTime(), annuncioInstance.getPrezzo(), utenteInstance);
		
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
}
