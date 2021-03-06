package it.prova.myebay.service.annuncio;

import java.util.List;

import it.prova.myebay.dao.acquisto.AcquistoDAO;
import it.prova.myebay.dao.annuncio.AnnuncioDAO;
import it.prova.myebay.dao.categoria.CategoriaDAO;
import it.prova.myebay.dao.utente.UtenteDAO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public interface AnnuncioService {
	
	public List<Annuncio> listAll() throws Exception;

	public Annuncio caricaSingoloAnnuncio(Long id) throws Exception;

	public void aggiorna(Annuncio annuncioInstance, String[] categorie) throws Exception;

	public void inserisciNuovo(Annuncio annuncioInstance) throws Exception;

	public void rimuovi(Long idAnnuncioToRemove) throws Exception;
	
	public void collegaACategoriaEsistente(Categoria categoriaInstance, Annuncio annuncioInstance) throws Exception;
	
	public void disassociaDaCategoriaEsistente(Categoria categoriaInstance, Annuncio annuncioInstance) throws Exception;
	
	public Annuncio caricaAnnuncioSingoloConCategorie(Long id) throws Exception;
	
	public Annuncio caricaAnnuncioSingoloConCategorieECreatore(Long id) throws Exception;
	
	public List<Annuncio> findByExample(Annuncio example) throws Exception;
	
	public List<Annuncio> findByExampleConUtente(Annuncio example) throws Exception;
	
	public List<Annuncio> listAllOpened() throws Exception;
	
	public List<Annuncio> trovaAnnunciDiUtente(Long id) throws Exception;
	
	public void eseguiAcquisto(Long idUtente, Long idAnnuncio) throws Exception;

	// per injection
	public void setAnnuncioDAO(AnnuncioDAO annuncioDAO);
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public void setUtenteDAO(UtenteDAO utenteDAO);
	
	public void setAcquistoDAO(AcquistoDAO acquistoDAO);

}
