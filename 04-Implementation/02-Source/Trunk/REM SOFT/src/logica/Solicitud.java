package logica;

import java.math.BigDecimal;
import java.util.Date;

public class Solicitud {
	
	private Integer idSolicitud;
	private Date fecha;
	private Boolean activa;
	private Persona solicitante;
	
	private String tipoInmueble;
	private String altInmueble;
	private String provincia;
	private String ciudad;
	private Integer tipoOperacion;
	
	private BigDecimal desdePrecio;
	private BigDecimal hastaPrecio;
	private String moneda;
	
	private String detalle;
	
	private Inmueble inmueble;
	

	/* Constructor */
	
	public Solicitud(Integer idSolicitud, Date fecha, Boolean activa, Persona solicitante, String tipoInmueble,
			String altInmueble, String provincia, String ciudad, Integer tipoOperacion, BigDecimal desdePrecio,
			BigDecimal hastaPrecio, String moneda, String detalle, Inmueble inmueble) {

		this.idSolicitud = idSolicitud;
		this.fecha = fecha;
		this.activa = activa;
		this.solicitante = solicitante;
		this.tipoInmueble = tipoInmueble;
		this.altInmueble = altInmueble;
		this.provincia = provincia;
		this.ciudad = ciudad;
		this.tipoOperacion = tipoOperacion;
		this.desdePrecio = desdePrecio;
		this.hastaPrecio = hastaPrecio;
		this.moneda = moneda;
		this.detalle = detalle;
		this.inmueble = inmueble;
	}
	
	
	/* Getters & Setters */

	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public Persona getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Persona solicitante) {
		this.solicitante = solicitante;
	}

	public String getTipoInmueble() {
		return tipoInmueble;
	}

	public void setTipoInmueble(String tipoInmueble) {
		this.tipoInmueble = tipoInmueble;
	}

	public String getAltInmueble() {
		return altInmueble;
	}

	public void setAltInmueble(String altInmueble) {
		this.altInmueble = altInmueble;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Integer tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BigDecimal getDesdePrecio() {
		return desdePrecio;
	}

	public void setDesdePrecio(BigDecimal desdePrecio) {
		this.desdePrecio = desdePrecio;
	}

	public BigDecimal getHastaPrecio() {
		return hastaPrecio;
	}

	public void setHastaPrecio(BigDecimal hastaPrecio) {
		this.hastaPrecio = hastaPrecio;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}
	
	

}
