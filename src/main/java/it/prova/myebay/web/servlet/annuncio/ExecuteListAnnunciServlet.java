package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import it.prova.myebay.service.MyServiceFactory;

@WebServlet("/ExecuteListAnnunciServlet")
public class ExecuteListAnnunciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteListAnnunciServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			//se nell'url della request è presente SUCCESS significa che devo mandare un 
			//messaggio di avvenuta operazione in pagina
			String operationResult = request.getParameter("operationResult");
			if(StringUtils.isNotBlank(operationResult) && operationResult.equalsIgnoreCase("SUCCESS"))
				request.setAttribute("successMessage", "Operazione effettuata con successo");
			
			request.setAttribute("annunci_list_attribute", MyServiceFactory.getAnnuncioServiceInstance().listAll());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		request.getRequestDispatcher("annuncio/list.jsp").forward(request, response);
	}

}
