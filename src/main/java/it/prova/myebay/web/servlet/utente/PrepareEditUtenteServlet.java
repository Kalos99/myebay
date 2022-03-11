package it.prova.myebay.web.servlet.utente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Utente;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/utente/PrepareEditUtenteServlet")
public class PrepareEditUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareEditUtenteServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String parametroIdDellUtenteDaAggiornare = request.getParameter("idUtente");
		
		if(!NumberUtils.isCreatable(parametroIdDellUtenteDaAggiornare)) {
			request.setAttribute("errorMessage", "Attenzione, non è stato trovato l'utente corrispondente.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		try{
			Utente utentePerAggiornamento = MyServiceFactory.getUtenteServiceInstance().caricaSingoloUtenteConRuoli(Long.parseLong(parametroIdDellUtenteDaAggiornare));
			request.setAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(MyServiceFactory.getRuoloServiceInstance().listAll(), null));
			request.setAttribute("utenteCheSiVuoleAggiornare", utentePerAggiornamento);
		} catch (Exception e){
			//qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/utente/edit.jsp").forward(request, response);	
	}

}
