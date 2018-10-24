package logica;

import java.math.BigDecimal;
import java.util.Date;

public class PagoAlquiler {
	
	/* Constants */
	public static final int NO_PAGO = 0;
	public static final int PAGADO = 1;
	public static final int ADEUDA = 2;
	
	public static final String[] ESTADO_PAGO = {"NO PAGO", "PAGADO", "ADEUDA"};

	/* Variables */
	private Integer idPago;
	private Date fechaPago;
	private Integer numPago;
	private Integer mesCorrespondiente;
	private Integer anioCorrespondiente;
	private BigDecimal montoTotal;
	private BigDecimal montoPagado;
	private String estadoPago;
	private String observacion;
	private Boolean pagado;
	
	/* Constructor */
	public PagoAlquiler() {
		
	}
	
	public PagoAlquiler(Integer numPago, Integer mesCorrespondiente, Integer anioCorrespondiente, BigDecimal montoTotal,
			BigDecimal montoPagado) {
		this.numPago = numPago;
		this.mesCorrespondiente = mesCorrespondiente;
		this.anioCorrespondiente = anioCorrespondiente;
		this.montoTotal = montoTotal;
		this.montoPagado = montoPagado;
	}




	@Override
	public String toString() {
		return "[" + numPago + ", " + mesCorrespondiente + ", " + anioCorrespondiente + ", " + montoTotal + ", " +montoPagado + "]";
	}

	/* Getters & Setters */
	public Integer getIdPago() {
		return idPago;
	}

	public void setIdPago(Integer idPago) {
		this.idPago = idPago;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Integer getNumPago() {
		return numPago;
	}

	public void setNumPago(Integer numPago) {
		this.numPago = numPago;
	}

	public Integer getMesCorrespondiente() {
		return mesCorrespondiente;
	}

	public void setMesCorrespondiente(Integer mesCorrespondiente) {
		this.mesCorrespondiente = mesCorrespondiente;
	}

	public Integer getAnioCorrespondiente() {
		return anioCorrespondiente;
	}

	public void setAnioCorrespondiente(Integer anioCorrespondiente) {
		this.anioCorrespondiente = anioCorrespondiente;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public BigDecimal getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(BigDecimal montoPagado) {
		this.montoPagado = montoPagado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstadoPago() {
		return estadoPago;
	}

	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}
	
	
	
}
