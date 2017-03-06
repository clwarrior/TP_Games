package es.ucm.fdi.tp.exceptions;

public class ParameterException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public ParameterException(String string) {
		this.message = string;
	}
	
	public String toString(){
		return "Error: " + message;
	}
	
}
