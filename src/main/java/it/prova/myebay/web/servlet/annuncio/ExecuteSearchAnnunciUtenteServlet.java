package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

/**
 * Servlet implementation class ExecuteSearchAnnunciUtenteServlet
 */
@WebServlet("/annuncio/ExecuteSearchAnnunciUtenteServlet")
public class ExecuteSearchAnnunciUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteSearchAnnunciUtenteServlet() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String testoParam = request.getParameter("testo");
		String prezzoParam = request.getParameter("prezzo");
		String[] categorieIdParam = request.getParameterValues("categorie");
		String statoAnnuncioParam = request.getParameter("stato");
		String idUtenteParam = request.getParameter("idUtente");

		try {
			Annuncio example = UtilityForm.createAnnuncioFromParams(testoParam, prezzoParam, categorieIdParam);
			example.setAperto(Boolean.parseBoolean(statoAnnuncioParam));
			example.setUtenteInserimento(new Utente(Long.parseLong(idUtenteParam)));

			request.setAttribute("annunci_list_attribute", MyServiceFactory.getAnnuncioServiceInstance().findByExampleConUtente(example));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("listuser.jsp").forward(request, response);
	}
	
}