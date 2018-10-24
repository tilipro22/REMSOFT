package logica;

import java.math.BigDecimal;

public class Operacion {
	
	private Integer idOperacion;
    private String tipoOperacion;
    private BigDecimal precio;
    private String moneda;
    private BigDecimal comision;
    private BigDecimal total;
    private String observacion;

    //Constructor
    public Operacion(Integer idOperacion, String tipoOperacion, BigDecimal precio, String moneda, BigDecimal comision, String observacion) {
        this.idOperacion = idOperacion;
        this.tipoOperacion = tipoOperacion;
        this.precio = precio;
        this.moneda = moneda;
        this.comision = comision;
        this.total = precio.multiply(comision);
        this.total = this.total.divide(new BigDecimal("100"));
        this.observacion = observacion;
    }

    //Getters & Setters
    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
    
    

}
