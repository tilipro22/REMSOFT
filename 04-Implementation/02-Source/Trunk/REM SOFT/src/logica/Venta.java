package logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
	
	public static final int ESTADO_PEN_ESCRITURACION = 0;
	public static final int ESTADO_NO_PAGO = 1;
	public static final int ESTADO_ADEUDA = 2;
	public static final int ESTADO_FINALIZADO = 3;
	public static final int ESTADO_ESCRITURIZADO = 4;
	
	public static final String[] ESTADOS_ARRAY = {"PENDIENTE DE ESCRITURIZACION", "NO PAGO", "ADEUDA", "FINALIZADO", "ESCRITURIZADO"}; 
	
	private Integer idVenta;
	private Inmueble inmueble;
	private Persona comprador;
	private Persona conyuge;
	private Persona martillero;
	private Persona escribano;
	
	// Contrato
	private Date fecha;
	private BigDecimal precioVenta;
	private String moneda;
	private BigDecimal comisionPct;
	
	// Escriturizacion
	private int diasEscriturizacion;
	private BigDecimal multaEscriturizacion;
	private Boolean escriturizado;
	
	// Otros
	private String observacion;
	private String estado;
	private Boolean reservado;
	
	// Pagos
	List<FormaPagoVta> formaPagoVtas = new ArrayList<>();
	
	
	/* Constructors */
	
	public Venta() {}

	public Venta(Integer idVenta, Inmueble inmueble, Persona comprador, Persona conyuge, Persona martillero,
			Persona escribano, Date fecha, BigDecimal precioVenta, String moneda, BigDecimal comisionPct,
			int diasEscriturizacion, BigDecimal multaEscriturizacion, Boolean escriturizado, String observacion,
			String estado, Boolean reservado) {

		this.idVenta = idVenta;
		this.inmueble = inmueble;
		this.comprador = comprador;
		this.conyuge = conyuge;
		this.martillero = martillero;
		this.escribano = escribano;
		this.fecha = fecha;
		this.precioVenta = precioVenta;
		this.moneda = moneda;
		this.comisionPct = comisionPct;
		this.diasEscriturizacion = diasEscriturizacion;
		this.multaEscriturizacion = multaEscriturizacion;
		this.escriturizado = escriturizado;
		this.observacion = observacion;
		this.estado = estado;
		this.reservado = reservado;
	}

	
	/* Getters & Setters */
	
	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}

	public Persona getComprador() {
		return comprador;
	}

	public void setComprador(Persona comprador) {
		this.comprador = comprador;
	}

	public Persona getConyuge() {
		return conyuge;
	}

	public void setConyuge(Persona conyuge) {
		this.conyuge = conyuge;
	}

	public Persona getMartillero() {
		return martillero;
	}

	public void setMartillero(Persona martillero) {
		this.martillero = martillero;
	}

	public Persona getEscribano() {
		return escribano;
	}

	public void setEscribano(Persona escribano) {
		this.escribano = escribano;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getComisionPct() {
		return comisionPct;
	}

	public void setComisionPct(BigDecimal comisionPct) {
		this.comisionPct = comisionPct;
	}

	public int getDiasEscriturizacion() {
		return diasEscriturizacion;
	}

	public void setDiasEscriturizacion(int diasEscriturizacion) {
		this.diasEscriturizacion = diasEscriturizacion;
	}

	public BigDecimal getMultaEscriturizacion() {
		return multaEscriturizacion;
	}

	public void setMultaEscriturizacion(BigDecimal multaEscriturizacion) {
		this.multaEscriturizacion = multaEscriturizacion;
	}

	public Boolean getEscriturizado() {
		return escriturizado;
	}

	public void setEscriturizado(Boolean escriturizado) {
		this.escriturizado = escriturizado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getReservado() {
		return reservado;
	}

	public void setReservado(Boolean reservado) {
		this.reservado = reservado;
	}

	public List<FormaPagoVta> getFormaPagoVtas() {
		return formaPagoVtas;
	}

	public void setFormaPagoVtas(List<FormaPagoVta> formaPagoVtas) {
		this.formaPagoVtas = formaPagoVtas;
	}
	
	
}
