package it.prova.myebay.web.servlet.annuncio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/annuncio/PrepareEditAnnuncioUtenteServlet")
public class PrepareEditAnnuncioUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PrepareEditAnnuncioUtenteServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
			String parametroIdDellAnnuncioDaAggiornare = request.getParameter("idAnnuncio");
					
					if(!NumberUtils.isCreatable(parametroIdDellAnnuncioDaAggiornare)) {
						request.setAttribute("errorMessage", "Attenzione, non è stato trovato l'annuncio corrispondente.");
						request.getRequestDispatcher("errore.jsp").forward(request, response);
						return;
					}
		try {
			// questo mi serve per la ricerca in base al ruolo
			Annuncio annuncioPerAggiornamento = MyServiceFactory.getAnnuncioServiceInstance().caricaAnnuncioSingoloConCategorie(Long.parseLong(parametroIdDellAnnuncioDaAggiornare));
			request.setAttribute("mappaCategorieConSelezionati_attr", UtilityForm.buildCheckedCategoriesFromCategoriesAlreadyInAnnuncio(MyServiceFactory.getCategoriaServiceInstance().listAll(), annuncioPerAggiornamento.getCategorie()));
			request.setAttribute("edit_annuncio_attr", annuncioPerAggiornamento);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/annuncio/edit.jsp").forward(request, response);
		return;
	}

}
