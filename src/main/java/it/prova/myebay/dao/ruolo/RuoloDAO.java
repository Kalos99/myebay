package it.prova.myebay.dao.ruolo;

import it.prova.myebay.dao.IBaseDAO;
import it.prova.myebay.model.Ruolo;

public interface RuoloDAO extends IBaseDAO<Ruolo> {
	
	public Ruolo findByDescrizioneAndCodice(String descrizione, String codice) throws Exception;

}