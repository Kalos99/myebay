package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.MyServiceFactory;

@WebServlet("/annuncio/ExecuteDeleteAnnuncioUtenteServlet")
public class ExecuteDeleteAnnuncioUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteAnnuncioUtenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idAnnuncioDaRimuovere = request.getParameter("idAnnuncio");
		
		if(!NumberUtils.isCreatable(idAnnuncioDaRimuovere)) {
			request.setAttribute("errorMessage", "Attenzione, non è stato trovato l'annuncio corrispondente.");
			request.getRequestDispatcher("/annuncio/delete.jsp").forward(request, response);
			return;
		}
		
		try{
			MyServiceFactory.getAnnuncioServiceInstance().rimuovi(Long.parseLong(idAnnuncioDaRimuovere));
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione, si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/utente/home.jsp");
	}

}
