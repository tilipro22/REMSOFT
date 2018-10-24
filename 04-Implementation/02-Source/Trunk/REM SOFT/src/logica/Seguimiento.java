package logica;

import java.sql.Date;

public class Seguimiento {
	static final int EST_ACTIVO = 1;
	static final int EST_FINALIZADO = 2;
	static final int EST_ARCHIVADO = 3;
	
	private Integer idSeguimiento;
	private Date fecha;
	private String detalle;
	private Estado estado;
	
	public Seguimiento (Date fecha, String detalle, int estado) {
		this.fecha = fecha;
		this.detalle = detalle;
		this.estado = new Estado(estado);
	}
	
	public Seguimiento (Date fecha, String detalle, int estado, int idSeguimiento) {
		this.fecha = fecha;
		this.detalle = detalle;
		this.estado = new Estado(estado);
		this.idSeguimiento = idSeguimiento;
	}

	public Integer getIdSeguimiento() {
		return idSeguimiento;
	}



	public void setIdSeguimiento(Integer idSeguimiento) {
		this.idSeguimiento = idSeguimiento;
	}



	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public class Estado {
		private int numero;
		private String descripcion;
		
		public Estado (int num) {
			this.numero = num;
			
			switch (numero) {
			
			case EST_ACTIVO:
				this.descripcion = "Activo";
				break;
				
			case EST_FINALIZADO:
				this.descripcion = "Finalizado";
				break;
				
			case EST_ARCHIVADO:
				this.descripcion = "Archivado";
				break;
			}
		}

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
		
	}
	

}
