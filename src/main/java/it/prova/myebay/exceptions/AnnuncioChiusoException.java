package it.prova.myebay.exceptions;

public class AnnuncioChiusoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AnnuncioChiusoException(String message) {
		super(message);
	}
}