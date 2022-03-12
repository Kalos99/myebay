package it.prova.myebay.web.servlet.utente;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/utente/ExecuteSearchUtenteServlet")
public class ExecuteSearchUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteSearchUtenteServlet() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String usernameParam = request.getParameter("username");
		String dataCreazioneParam = request.getParameter("dateCreated");
		String[] ruoliIdParam = request.getParameterValues("ruoli");

		try {
			Utente example = new Utente(usernameParam, nomeParam, cognomeParam, UtilityForm.parseDateFromString(dataCreazioneParam));
			
			Set<Ruolo> ruoliUtente = new HashSet<Ruolo>();
			for(String ruoloId : ruoliIdParam!=null?ruoliIdParam:new String[] {}) {
				if(NumberUtils.isCreatable(ruoloId)) {
					Ruolo ruoloDaInserire = new Ruolo();
					ruoloDaInserire.setId(Long.parseLong(ruoloId));
					ruoliUtente.add(ruoloDaInserire);
				}
			}
			example.setRuoli(ruoliUtente);
			request.setAttribute("utenti_list_attribute", MyServiceFactory.getUtenteServiceInstance().findByExample(example));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("search.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

}
