package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/annuncio/ExecuteInsertAnnunciServlet")
public class ExecuteInsertAnnunciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteInsertAnnunciServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String testoParam = request.getParameter("testo");
		String prezzoParam = request.getParameter("prezzo");
		String[] categorieIdParam = request.getParameterValues("categorie");
		String idUtenteParam = request.getParameter("idUtente");
		
		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Annuncio annuncioInstance = UtilityForm.createAnnuncioFromParams(testoParam, prezzoParam, categorieIdParam, idUtenteParam);
		
		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateAnnuncioBean(annuncioInstance)) {
				request.setAttribute("insert_annuncio_attr", annuncioInstance);
				request.setAttribute("mappaCategorieConSelezionati_attr", UtilityForm.buildCheckedCategoriesForPages(MyServiceFactory.getCategoriaServiceInstance().listAll(), null));
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/annuncio/insert.jsp").forward(request, response);
				return;
			}

			// se sono qui i valori sono ok quindi posso creare l'oggetto da inserire
			// occupiamoci delle operazioni di business
			MyServiceFactory.getAnnuncioServiceInstance().inserisciNuovo(annuncioInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/annuncio/insert.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		// uso il sendRedirect con parametro per evitare il problema del double save on
		// refresh
		response.sendRedirect("ExecuteListAnnunciServlet?operationResult=SUCCESS");
	}
}