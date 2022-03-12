package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

/**
 * Servlet implementation class ExecuteEditAnnuncioUtenteServlet
 */
@WebServlet("/annuncio/ExecuteEditAnnuncioUtenteServlet")
public class ExecuteEditAnnuncioUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteEditAnnuncioUtenteServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// estraggo input
		String testoParam = request.getParameter("testo");
		String prezzoParam = request.getParameter("prezzo");
		String[] categorieIdParam = request.getParameterValues("categorie");
		String idUtenteParam = request.getParameter("idUtente");
		String idAnnuncioParam = request.getParameter("idAnnuncio");
 
		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Annuncio annuncioInstance = UtilityForm.createAnnuncioFromParamsForEdit(testoParam, prezzoParam, categorieIdParam, idUtenteParam);
		
		
		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateAnnuncioBeanForEdit(annuncioInstance) || !NumberUtils.isCreatable(idAnnuncioParam)){
				request.setAttribute("edit_annuncio_attr", annuncioInstance);
				request.setAttribute("mappaCategorieConSelezionati_attr", UtilityForm.buildCheckedCategoriesFromCategoriesAlreadyInAnnuncio(MyServiceFactory.getCategoriaServiceInstance().listAll(), annuncioInstance.getCategorie()));
				// questo mi serve per la select di registi in pagina
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/annuncio/edit.jsp").forward(request, response);
				return;
			}

			// se sono qui i valori sono ok quindi posso creare l'oggetto da inserire
			// occupiamoci delle operazioni di business
			annuncioInstance.setId(Long.parseLong(idAnnuncioParam));
			MyServiceFactory.getAnnuncioServiceInstance().aggiorna(annuncioInstance, categorieIdParam);
			request.setAttribute("annunci_list_attribute", MyServiceFactory.getAnnuncioServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/annuncio/edit.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		// uso il sendRedirect con parametro per evitare il problema del double save on
		// refresh
		response.sendRedirect(request.getContextPath() + "/utente/home.jsp");

	}

}
