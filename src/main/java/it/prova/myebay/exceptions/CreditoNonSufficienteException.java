package it.prova.myebay.exceptions;

public class CreditoNonSufficienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CreditoNonSufficienteException(String message) {
		super(message);
	}
}