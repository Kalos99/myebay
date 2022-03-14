package it.prova.myebay.web.servlet.acquisto;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.service.MyServiceFactory;

@WebServlet("/acquisto/ExecuteListAcquistiServlet")
public class ExecuteListAcquistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteListAcquistiServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			
			String idUtenteParam = request.getParameter("idUtente");

			if (!NumberUtils.isCreatable(idUtenteParam)) {
				// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
				request.setAttribute("errorMessage", "Attenzione si è verificato un errore: non esiste un annuncio con questo id");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}
			//se nell'url della request è presente SUCCESS significa che devo mandare un 
			//messaggio di avvenuta operazione in pagina
			String operationResult = request.getParameter("operationResult");
			if(StringUtils.isNotBlank(operationResult) && operationResult.equalsIgnoreCase("SUCCESS"))
				request.setAttribute("successMessage", "Operazione effettuata con successo");
			
			request.setAttribute("acquisti_list_attribute", MyServiceFactory.getAcquistoServiceInstance().trovaAcquistiDiUtente(Long.parseLong(idUtenteParam)));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

}
