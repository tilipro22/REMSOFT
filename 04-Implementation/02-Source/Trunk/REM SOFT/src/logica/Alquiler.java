package logica;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import util.Utils;

public class Alquiler {
	
	/* Constantes */
	public static final String[] ESTADO_ALQUILER = {"AL DIA", "CON DEUDA","FINALIZADO"};
	
	/* Obligatorios */
	private Integer idAlquiler;
	private Inmueble inmueble;
	private Persona inquilino;
	private Persona garante;
	private BigDecimal montoTotal;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal porcentajeMora;
	private Integer diaVto;
	private String estadoPago;
	private String estadoAlquiler;
	private List<PagoAlquiler> listPagos = new ArrayList<>();
	
	/* No Obligatorios */
	private Integer mesesDeposito;
	private BigDecimal precioDeposito;
	private String observaciones;
	
	
	/* Constructor */
	
	public Alquiler(Inmueble inmueble, Persona inquilino, Persona garante, BigDecimal montoTotal,
			Date fechaInicio, Date fechaFin, BigDecimal porcentajeMora, Integer diaVto, String estadoPago,
			String estadoAlquiler) {
		this.inmueble = inmueble;
		this.inquilino = inquilino;
		this.garante = garante;
		this.montoTotal = montoTotal;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.porcentajeMora = porcentajeMora;
		this.diaVto = diaVto;
		this.estadoPago = estadoPago;
		this.estadoAlquiler = estadoAlquiler;
	}

	/* Functions */
	@SuppressWarnings("deprecation")
	public void generarPagos() {

		Calendar startFecha = new GregorianCalendar();
		startFecha.setTime(fechaInicio);
		
		Calendar endFecha = new GregorianCalendar();
		endFecha.setTime(fechaFin);
		
		int diffYear = endFecha.get(Calendar.YEAR)-startFecha.get(Calendar.YEAR);
		int diffMonths = diffYear * 12 + endFecha.get(Calendar.MONTH)-startFecha.get(Calendar.MONTH);
		
		int mes = startFecha.get(Calendar.MONTH)+1; 
		int anio = startFecha.get(Calendar.YEAR);
		
		for (int i=1; i <= diffMonths; i++) {

			PagoAlquiler pagoAlquiler = new PagoAlquiler(i, mes, anio, montoTotal, new BigDecimal(0.0));
			pagoAlquiler.setEstadoPago(PagoAlquiler.ESTADO_PAGO[0]);
			pagoAlquiler.setPagado(false);
			listPagos.add(pagoAlquiler);
			
			
			if (Utils.diferenciaDias(new Date(), new Date(anio-1900, mes-1, diaVto)).intValue() > 0) {
				pagoAlquiler.setEstadoPago(PagoAlquiler.ESTADO_PAGO[2]);
				setEstadoAlquiler(ESTADO_ALQUILER[1]);
			}
			
			if (mes == 12) {
				mes = 1;
				anio++;
			}
			else {
				mes++;
			}
			
		}
		
	}

	/* Getters & Setters */
	public Integer getIdAlquiler() {
		return idAlquiler;
	}


	public void setIdAlquiler(Integer idAlquiler) {
		this.idAlquiler = idAlquiler;
	}


	public Inmueble getInmueble() {
		return inmueble;
	}


	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}


	public Persona getInquilino() {
		return inquilino;
	}


	public void setInquilino(Persona inquilino) {
		this.inquilino = inquilino;
	}


	public Persona getGarante() {
		return garante;
	}


	public void setGarante(Persona garante) {
		this.garante = garante;
	}


	public BigDecimal getMontoTotal() {
		return montoTotal;
	}


	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public BigDecimal getPorcentajeMora() {
		return porcentajeMora;
	}


	public void setPorcentajeMora(BigDecimal porcentajeMora) {
		this.porcentajeMora = porcentajeMora;
	}


	public Integer getDiaVto() {
		return diaVto;
	}


	public void setDiaVto(Integer diaVto) {
		this.diaVto = diaVto;
	}


	public String getEstadoPago() {
		return estadoPago;
	}


	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}


	public String getEstadoAlquiler() {
		return estadoAlquiler;
	}


	public void setEstadoAlquiler(String estadoAlquiler) {
		this.estadoAlquiler = estadoAlquiler;
	}


	public List<PagoAlquiler> getListPagos() {
		return listPagos;
	}


	public void setListPagos(List<PagoAlquiler> listPagos) {
		this.listPagos = listPagos;
	}


	public Integer getMesesDeposito() {
		return mesesDeposito;
	}


	public void setMesesDeposito(Integer mesesDeposito) {
		this.mesesDeposito = mesesDeposito;
	}


	public BigDecimal getPrecioDeposito() {
		return precioDeposito;
	}


	public void setPrecioDeposito(BigDecimal precioDeposito) {
		this.precioDeposito = precioDeposito;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	
}
