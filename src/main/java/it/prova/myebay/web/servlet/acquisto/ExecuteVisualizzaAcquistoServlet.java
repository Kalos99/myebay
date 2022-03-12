package it.prova.myebay.web.servlet.acquisto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.service.MyServiceFactory;

@WebServlet("/acquisto/ExecuteVisualizzaAcquistoServlet")
public class ExecuteVisualizzaAcquistoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteVisualizzaAcquistoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idAcquistoParam = request.getParameter("idAcquisto");

		if (!NumberUtils.isCreatable(idAcquistoParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non esiste un acquisto con questo id");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		try {
			Acquisto acquistoInstance = MyServiceFactory.getAcquistoServiceInstance().caricaSingoloElemento(Long.parseLong(idAcquistoParam));

			if (acquistoInstance == null) {
				request.setAttribute("errorMessage", "Elemento non trovato.");
				request.getRequestDispatcher("utente/home").forward(request, response);
				return;
			}
			request.setAttribute("visualizza_acquisto_attr", acquistoInstance);
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non è stato possibile caricare le informazioni dell'acquisto");
			request.getRequestDispatcher("acquisto/list.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("show.jsp").forward(request, response);
	}
}
