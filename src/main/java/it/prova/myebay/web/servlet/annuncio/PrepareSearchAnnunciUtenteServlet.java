package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

/**
 * Servlet implementation class PrepareSearchAnnunciUtenteServlet
 */
@WebServlet("/annuncio/PrepareSearchAnnunciUtenteServlet")
public class PrepareSearchAnnunciUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareSearchAnnunciUtenteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// questo mi serve per la ricerca in base al ruolo
			request.setAttribute("mappaCategorieConSelezionati_attr", UtilityForm.buildCheckedCategoriesForPages(MyServiceFactory.getCategoriaServiceInstance().listAll(), null));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/annuncio/search.jsp").forward(request, response);
		return;
	}

}
