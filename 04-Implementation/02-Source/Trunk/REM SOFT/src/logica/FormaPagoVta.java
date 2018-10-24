package logica;

import java.math.BigDecimal;
import java.util.Date;

public class FormaPagoVta {
	
	public static final int ESTADO_NO_PAGO = 0;
	public static final int ESTADO_ADEUDA = 1;
	public static final int ESTADO_PAGADO = 2;
	
	public static final String[] ESTADOS_ARRAY = {"NO PAGO", "ADEUDA", "PAGADO"}; 
	
	/* Not null */
	private Integer idPago;
	private String descripcion;
	private BigDecimal honorarios;
	private BigDecimal montoTotal;
	private String moneda;
	
	/* null */
	private Date fechaPago;
	private BigDecimal montoPagado;
	private Date fechaVto;
	private BigDecimal interesDiario;
	
	private Boolean estaPagado;
	private Boolean tieneVto;
	private String estado;
	
	
	/* Constructors */
	public FormaPagoVta(Integer idFormaPagoVta, String descripcion, BigDecimal honorarios, BigDecimal montoTotal,
			String moneda) {
		this.idPago = idFormaPagoVta;
		this.descripcion = descripcion;
		this.honorarios = honorarios;
		this.montoTotal = montoTotal;
		this.moneda = moneda;
	}


	public FormaPagoVta(Integer idFormaPagoVta, String descripcion, BigDecimal honorarios, BigDecimal montoTotal,
			String moneda, Date fechaPago, BigDecimal montoPagado) {
		this.idPago = idFormaPagoVta;
		this.descripcion = descripcion;
		this.honorarios = honorarios;
		this.montoTotal = montoTotal;
		this.moneda = moneda;
		this.fechaPago = fechaPago;
		this.montoPagado = montoPagado;
	}


	public FormaPagoVta(Integer idFormaPagoVta, String descripcion, BigDecimal honorarios, BigDecimal montoTotal,
			String moneda, Date fechaVto, BigDecimal interesDiario, int empty) {
		this.idPago = idFormaPagoVta;
		this.descripcion = descripcion;
		this.honorarios = honorarios;
		this.montoTotal = montoTotal;
		this.moneda = moneda;
		this.fechaVto = fechaVto;
		this.interesDiario = interesDiario;
	}


	public FormaPagoVta(Integer idFormaPagoVta, String descripcion, BigDecimal honorarios, BigDecimal montoTotal,
			String moneda, Date fechaPago, BigDecimal montoPagado, Date fechaVto, BigDecimal interesDiario) {
		this.idPago = idFormaPagoVta;
		this.descripcion = descripcion;
		this.honorarios = honorarios;
		this.montoTotal = montoTotal;
		this.moneda = moneda;
		this.fechaPago = fechaPago;
		this.montoPagado = montoPagado;
		this.fechaVto = fechaVto;
		this.interesDiario = interesDiario;
	}


	/* Getters & Setters */
	public Integer getIdPago() {
		return idPago;
	}


	public void setIdPago(Integer idFormaPagoVta) {
		this.idPago = idFormaPagoVta;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public BigDecimal getHonorarios() {
		return honorarios;
	}


	public void setHonorarios(BigDecimal honorarios) {
		this.honorarios = honorarios;
	}


	public BigDecimal getMontoTotal() {
		return montoTotal;
	}


	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}


	public String getMoneda() {
		return moneda;
	}


	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}


	public Date getFechaPago() {
		return fechaPago;
	}


	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}


	public BigDecimal getMontoPagado() {
		return montoPagado;
	}


	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}


	public Date getFechaVto() {
		return fechaVto;
	}


	public void setFechaVto(Date fechaVto) {
		this.fechaVto = fechaVto;
	}


	public BigDecimal getInteresDiario() {
		return interesDiario;
	}


	public void setInteresDiario(BigDecimal interesDiario) {
		this.interesDiario = interesDiario;
	}


	public Boolean getEstaPagado() {
		return estaPagado;
	}


	public void setEstaPagado(Boolean estaPagado) {
		this.estaPagado = estaPagado;
	}


	public Boolean getTieneVto() {
		return tieneVto;
	}


	public void setTieneVto(Boolean tieneVto) {
		this.tieneVto = tieneVto;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	

	
}
