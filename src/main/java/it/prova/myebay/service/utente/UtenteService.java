package it.prova.myebay.service.utente;

import java.util.List;

import it.prova.myebay.dao.ruolo.RuoloDAO;
import it.prova.myebay.dao.utente.UtenteDAO;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.Utente;

public interface UtenteService {
	
	public List<Utente> listAll() throws Exception;

	public Utente caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Utente utenteInstance, String[] ruoli) throws Exception;

	public void inserisciNuovo(Utente utenteInstance) throws Exception;

	public void rimuovi(Utente utenteInstance) throws Exception;
	
	public void aggiungiRuolo(Utente utenteEsistente, Ruolo ruoloInstance) throws Exception;
	
	public Utente findByUsernameAndPassword(String username, String password) throws Exception;
	
	public Utente accedi(String username, String password) throws Exception;
	
	public List<Utente> findByExample(Utente example) throws Exception;
	
	public Utente caricaSingoloUtenteConRuoli(Long id) throws Exception;
	
	public void disabilitaUtente(Long id) throws Exception;
	
	public List<Utente> trovaUtentiInBaseAlRuolo(Ruolo input) throws Exception;

	//per injection
	public void setUtenteDAO(UtenteDAO utenteDAO);
	
	public void setRuoloDAO(RuoloDAO ruoloDAO);

}
