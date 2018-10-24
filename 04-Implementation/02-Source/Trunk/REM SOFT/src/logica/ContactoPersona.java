package logica;

import java.math.BigInteger;

public class ContactoPersona {
	private BigInteger telefono;
	private BigInteger celular;
	private String mail;
	
	//CONSTRUCTOR
	public ContactoPersona(BigInteger telefono, BigInteger celular, String mail) {
		this.telefono = telefono;
		this.celular = celular;
		this.mail = mail;
	}

	//GETTERS Y SETTERS
	public BigInteger getTelefono() {
		return telefono;
	}

	public void setTelefono(BigInteger telefono) {
		this.telefono = telefono;
	}

	public BigInteger getCelular() {
		return celular;
	}

	public void setCelular(BigInteger celular) {
		this.celular = celular;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	

}
