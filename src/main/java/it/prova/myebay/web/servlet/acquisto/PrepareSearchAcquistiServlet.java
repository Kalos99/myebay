package it.prova.myebay.web.servlet.acquisto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/acquisto/PrepareSearchAcquistiServlet")
public class PrepareSearchAcquistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareSearchAcquistiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//metto un bean 'vuoto' in request perché per la pagina risulta necessario
			request.setAttribute("search_acquisto_attr", new Acquisto());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/acquisto/search.jsp").forward(request, response);
		return;
	}
}