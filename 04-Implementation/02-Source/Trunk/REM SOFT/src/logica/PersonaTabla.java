package logica;

import java.math.BigInteger;

public class PersonaTabla {
	private String nombre;
	private BigInteger dni;
	private BigInteger celular;
	private String email;
	private BigInteger telefono;
	private String domicilio;
	private String ciudad;
	private String provincia;
	
	public PersonaTabla(String nombre,BigInteger dni, BigInteger celular, String email, BigInteger telefono, String domicilio,
			String ciudad, String provincia) {
		this.nombre = nombre;
		this.dni = dni;
		this.celular = celular;
		this.email = email;
		this.telefono = telefono;
		this.domicilio = domicilio;
		this.ciudad = ciudad;
		this.provincia = provincia;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public BigInteger getDni() {
		return dni;
	}
	public void setDni(BigInteger dni) {
		this.dni = dni;
	}
	public BigInteger getCelular() {
		return celular;
	}
	public void setCelular(BigInteger celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigInteger getTelefono() {
		return telefono;
	}
	public void setTelefono(BigInteger telefono) {
		this.telefono = telefono;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	
	

}
