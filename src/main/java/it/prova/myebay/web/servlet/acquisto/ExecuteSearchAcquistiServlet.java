package it.prova.myebay.web.servlet.acquisto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.service.MyServiceFactory;
import it.prova.myebay.utility.UtilityForm;

@WebServlet("/acquisto/ExecuteSearchAcquistiServlet")
public class ExecuteSearchAcquistiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteSearchAcquistiServlet() {
        super();
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String descrizioneParam = request.getParameter("descrizione");
		String prezzoParam = request.getParameter("prezzo");
		String dataAcquistoParam = request.getParameter("dataAcquisto");
		String idUtenteParam = request.getParameter("idUtente");

		try {
			Acquisto example = UtilityForm.createAcquistoFromParams(descrizioneParam, prezzoParam, dataAcquistoParam, idUtenteParam);

			request.setAttribute("acquisti_list_attribute", MyServiceFactory.getAcquistoServiceInstance().findByExample(example));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

}
