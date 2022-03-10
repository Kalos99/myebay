package it.prova.myebay.dao.categoria;

import it.prova.myebay.dao.IBaseDAO;
import it.prova.myebay.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria>{
	public Categoria findByIdFetchingAnnunci(Long id) throws Exception;
}
