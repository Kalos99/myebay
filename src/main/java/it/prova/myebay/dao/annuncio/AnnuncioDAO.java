package it.prova.myebay.dao.annuncio;

import java.util.List;

import it.prova.myebay.dao.IBaseDAO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public interface AnnuncioDAO extends IBaseDAO<Annuncio>{
	public List<Annuncio> findAllByCategoria(Categoria categoriaInput) throws Exception; 
	public Annuncio findByIdFetchingCategorie(Long id) throws Exception;
	public Annuncio findByIdFetchingCategorieAndCreatore(Long id) throws Exception;
	public List<Annuncio> findByExample(Annuncio example) throws Exception;
	public List<Annuncio> findByExampleConUtente(Annuncio example) throws Exception;
	public List<Annuncio> listOpened() throws Exception;
}
