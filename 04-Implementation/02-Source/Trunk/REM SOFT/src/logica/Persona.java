package logica;

import java.math.BigInteger;
import java.util.ArrayList;

public class Persona {
	private Integer idPersona;
	private String nombre;
	private String apellido;
	private BigInteger dni;
	private String domicilio;
	private String ciudad;
	private String provincia;
	private String nacionalidad;
	private ContactoPersona contacto;
	private ArrayList<Seguimiento> seguimientos=new ArrayList<Seguimiento>();
	private String tipo;
	private String profesion;
	private Character sexo;
	private String observaciones;

	//CONSTRUCTORES
	public Persona(String nombre, String apellido, BigInteger dni, ContactoPersona contacto,
			ArrayList<Seguimiento> seguimientos, String profesion,Character sexo, String observaciones) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.contacto = contacto;
		this.seguimientos = seguimientos;
		this.profesion = profesion;
		this.sexo = sexo;
		this.observaciones = observaciones;
	}
	
	public Persona(String nombre, String apellido, BigInteger dni, 
			String domicilio, String ciudad, String provincia, String nacionalidad ) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.domicilio = domicilio;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.nacionalidad = nacionalidad;
		this.contacto = null;
		this.profesion = null;
		this.sexo = null;
		this.observaciones = null;
	}

	//GETTERS Y SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public BigInteger getDni() {
		return dni;
	}

	public void setDni(BigInteger dni) {
		this.dni = dni;
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public ContactoPersona getContacto() {
		return contacto;
	}

	public void setContacto(ContactoPersona contacto) {
		this.contacto = contacto;
	}

	public ArrayList<Seguimiento> getSeguimientos() {
		return seguimientos;
	}

	public void setSeguimientos(ArrayList<Seguimiento> seguimientos) {
		this.seguimientos = seguimientos;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	
}
