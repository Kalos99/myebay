package it.prova.myebay.utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.model.Utente;

public class UtilityForm {
	
	public static Utente createUtenteFromParams( String userameInputParam, String passwordInputParam, String nomeInputParam, String cognomeInputParam, String[] ruoliInputParam) {
		Set<Ruolo> ruoliUtente = new HashSet<Ruolo>();
		Utente result = new Utente(userameInputParam, passwordInputParam, nomeInputParam, cognomeInputParam);
		result.setDateCreated(Calendar.getInstance().getTime());
		result.setStato(StatoUtente.CREATO);
		for(String ruoloId : ruoliInputParam) {
			if(NumberUtils.isCreatable(ruoloId)) {
				Ruolo ruoloDaInserire = new Ruolo();
				ruoloDaInserire.setId(Long.parseLong(ruoloId));
				ruoliUtente.add(ruoloDaInserire);
			}
		}
		result.setRuoli(ruoliUtente);
		return result;
	}
	
	public static boolean validateUtenteBean(Utente utenteToBeValidated) {
		// prima controlliamo che non siano vuoti i parametri
		if (StringUtils.isBlank(utenteToBeValidated.getNome())
				|| StringUtils.isBlank(utenteToBeValidated.getCognome())
				|| StringUtils.isBlank(utenteToBeValidated.getUsername()) 
				|| utenteToBeValidated.getPassword() == null) {
			return false;
		}
		return true;
	}
	
	public static Annuncio createAnnuncioFromParams( String testoInputParam, String prezzoInputParam, String[] categorieInputParam) {
		Set<Categoria> categorieAnnuncio = new HashSet<Categoria>();
		Annuncio result = new Annuncio();
		result.setTestoAnnuncio(testoInputParam.isBlank() ? null : testoInputParam);
		result.setPrezzo(NumberUtils.isCreatable(prezzoInputParam) ? Integer.parseInt(testoInputParam) : null);
		for(String categoriaId : categorieInputParam!=null?categorieInputParam:new String[] {}) {
			if(NumberUtils.isCreatable(categoriaId)) {
				Categoria categoriaDaInserire = new Categoria();
				categoriaDaInserire.setId(Long.parseLong(categoriaId));
				categorieAnnuncio.add(categoriaDaInserire);
			}
		}
		result.setCategorie(categorieAnnuncio);
		return result;
	}
	
	public static Map<Ruolo, Boolean> buildCheckedRolesForPages(List<Ruolo> listaTotaleRuoli,
			String[] ruoliFromParams) {
		Map<Ruolo, Boolean> result = new TreeMap<>();

		// converto array di string in List di Long
		List<Long> ruoliIdConvertiti = new ArrayList<>();
		for (String stringItem : ruoliFromParams != null ? ruoliFromParams : new String[] {}) {
			ruoliIdConvertiti.add(Long.valueOf(stringItem));
		}

		for (Ruolo ruoloItem : listaTotaleRuoli) {
			result.put(ruoloItem, ruoliIdConvertiti.contains(ruoloItem.getId()));
		}

		return result;
	}
	
	public static Map<Ruolo, Boolean> buildCheckedRolesFromRolesAlreadyInUtente(List<Ruolo> listaTotaleRuoli,
			Set<Ruolo> listaRuoliPossedutiDaUtente) {
		Map<Ruolo, Boolean> result = new TreeMap<>();

		// converto array di ruoli in List di Long
		List<Long> ruoliConvertitiInIds = new ArrayList<>();
		for (Ruolo ruoloDiUtenteItem : listaRuoliPossedutiDaUtente != null ? listaRuoliPossedutiDaUtente
				: new ArrayList<Ruolo>()) {
			ruoliConvertitiInIds.add(ruoloDiUtenteItem.getId());
		}

		for (Ruolo ruoloItem : listaTotaleRuoli) {
			result.put(ruoloItem, ruoliConvertitiInIds.contains(ruoloItem.getId()));
		}

		return result;
	}
	
	public static Map<Categoria, Boolean> buildCheckedCategoriesForPages(List<Categoria> listaTotaleCategorie, String[] categorieFromParams) {
		Map<Categoria, Boolean> result = new TreeMap<>();

		// converto array di string in List di Long
		List<Long> categorieIdConvertiti = new ArrayList<>();
		for (String stringItem : categorieFromParams != null ? categorieFromParams : new String[] {}) {
			categorieIdConvertiti.add(Long.valueOf(stringItem));
		}

		for (Categoria categoriaItem : listaTotaleCategorie) {
			result.put(categoriaItem, categorieIdConvertiti.contains(categoriaItem.getId()));
		}

		return result;
	}
}
