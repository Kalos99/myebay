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

@WebServlet("/utente/ExecuteEditUtenteServlet")
public class ExecuteEditUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteEditUtenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// estraggo input
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String usernameParam = request.getParameter("username");
		String statoParam = request.getParameter("stato");
		String[] ruoliIdParam = request.getParameterValues("ruoli");
		String idStringParam = request.getParameter("idUtente");
 
		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Utente utenteInstance = UtilityForm.createUtenteFromParamsForEdit(usernameParam, nomeParam, cognomeParam, statoParam, ruoliIdParam);
		
		try {
			// se la validazione non risulta ok
			if (!UtilityForm.validateUtenteBeanForEdit(utenteInstance) || !NumberUtils.isCreatable(idStringParam)){
				request.setAttribute("utenteCheSiVuoleAggiornare", utenteInstance);
				request.setAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(MyServiceFactory.getRuoloServiceInstance().listAll(), utenteInstance.getRuoli()));
				// questo mi serve per la select di registi in pagina
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.getRequestDispatcher("/utente/edit.jsp").forward(request, response);
				return;
			}

			// se sono qui i valori sono ok quindi posso creare l'oggetto da inserire
			// occupiamoci delle operazioni di business
			utenteInstance.setId(Long.parseLong(idStringParam));
			MyServiceFactory.getUtenteServiceInstance().aggiorna(utenteInstance, ruoliIdParam);
			request.setAttribute("registi_list_attribute", MyServiceFactory.getUtenteServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/utente/edit.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		// uso il sendRedirect con parametro per evitare il problema del double save on
		// refresh
		response.sendRedirect("ExecuteListUtentiServlet?operationResult=SUCCESS");

	}
}
