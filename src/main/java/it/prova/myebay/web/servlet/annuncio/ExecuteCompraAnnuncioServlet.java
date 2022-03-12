package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.service.MyServiceFactory;

@WebServlet("/annuncio/ExecuteCompraAnnuncioServlet")
public class ExecuteCompraAnnuncioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteCompraAnnuncioServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idAnnuncioParam = request.getParameter("idAnnuncio");
		String idUtenteParam = request.getParameter("idUtente");
		
		if (!NumberUtils.isCreatable(idUtenteParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non esiste un utente con questo id");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
		if (!NumberUtils.isCreatable(idAnnuncioParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non esiste un annuncio con questo id");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
		try {
			MyServiceFactory.getAnnuncioServiceInstance().eseguiAcquisto(Long.parseLong(idUtenteParam), Long.parseLong(idAnnuncioParam));
			request.setAttribute("acquisti_list_attribute", MyServiceFactory.getAcquistoServiceInstance().trovaAcquistiDiUtente(Long.parseLong(idUtenteParam)));
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non è stato possibile effettuare l'acquisto");
			request.getRequestDispatcher("utente/home.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect(request.getContextPath() + "/acquisto/list.jsp");
	}
}