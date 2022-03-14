package it.prova.myebay.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.myebay.model.Utente;

@WebFilter(filterName = "CheckAuthFilter", urlPatterns = { "/*" })
public class CheckAuthFilter implements Filter {
	
	private static final String[] EXCLUDED_URLS = {"ricerca.jsp", "footer.jsp", "header.jsp", "login.jsp", "navbar.jsp", "register.jsp", "/ExecuteRegisterServlet", "/ExecuteLoginServlet", "/LogoutServlet", "/PrepareLoginServlet", "/PrepareRegisterServlet", "/ExecuteVisualizzaAnnuncioServlet", "/ExecuteSearchAnnunciServlet", "/assets/"};
	private static final String[] CLASSIC_USER_URLS = {"/annuncio/", "/acquisto/", "home.jsp"};
	private static final String[] ADMIN_URLS = {"/utente/"};

	public CheckAuthFilter() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Nel filtro di check user in session");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		//prendo il path della request che sta passando in questo momento
		String pathAttuale = httpRequest.getServletPath();
		System.out.println("Invocazione di: " + pathAttuale);
		
		//vediamo se il path risulta tra quelli 'liberi di passare'
		boolean isInWhiteList = isPathInWhiteList(pathAttuale);
		
		//se non lo e' bisogna controllare sia sessione che percorsi protetti
		if (!isInWhiteList) {
			Utente utenteInSession = (Utente)httpRequest.getSession().getAttribute("userInfo");
			//intanto verifico se utente in sessione
			if (utenteInSession == null) {
				httpResponse.sendRedirect(httpRequest.getContextPath());
				return;
			}
			
			if(isPathForLoggedUsers(pathAttuale) && !utenteInSession.isClassicUser()) {
				httpRequest.setAttribute("errorMessage", "Non è stato effettuato il login");
				httpRequest.getRequestDispatcher("ricerca.jsp").forward(httpRequest, httpResponse);
				return;
			}
			//controllo che utente abbia ruolo admin se nel path risulta presente /admin/
			if(isPathForOnlyAdministrators(pathAttuale) && !utenteInSession.isAdmin()) {
				httpRequest.setAttribute("errorMessage", "Non si è autorizzati alla navigazione richiesta");
				httpRequest.getRequestDispatcher("home.jsp").forward(httpRequest, httpResponse);
				return;
			}
		}

		chain.doFilter(request, response);
	}
	
	private boolean isPathInWhiteList(String requestPath) {
		//bisogna controllare che se il path risulta proprio "" oppure se 
		//siamo in presenza un url 'libero'
		if(requestPath.equals(""))
			return true;
		
		for (String urlPatternItem : EXCLUDED_URLS) {
			if (requestPath.contains(urlPatternItem)) {
				System.out.println("url invocabile liberamente\n");
				return true;
			}
		}
		return false;
	}
	
	private boolean isPathForLoggedUsers(String requestPath) {
		for (String urlPatternItem : CLASSIC_USER_URLS) {
			if (requestPath.contains(urlPatternItem)) {
				System.out.println("url invocabile se si è effettuato il login\n");
				return true;
			}
		}
		return false;
	}

	
	private boolean isPathForOnlyAdministrators(String requestPath) {
		for (String urlPatternItem : ADMIN_URLS) {
			if (requestPath.contains(urlPatternItem)) {
				System.out.println("url invocabile solo se sei admin\n");
				return true;
			}
		}
		return false;
	}

	public void destroy() {
	}

}

