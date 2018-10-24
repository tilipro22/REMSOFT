package logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GastoFijo {
	private Integer idInmueble;
	private Integer idGasto;
    private String gasto;
    private BigDecimal monto;
    private List<Integer> mesesAplica;
    private Boolean liquidacion;

    public GastoFijo(Integer idInmueble, Integer idGasto, String gasto, BigDecimal monto, Boolean liquidacion) {
        this.idInmueble = idInmueble;
        this.gasto = gasto;
        this.monto = monto;
        this.liquidacion = liquidacion;
        generateMesesAplica();
    }

    private void generateMesesAplica () {
        mesesAplica = new ArrayList<Integer>();

        for (int i=0; i < 12; i++)
            mesesAplica.add(0);

    }
    

	public Integer getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(Integer idInmueble) {
		this.idInmueble = idInmueble;
	}

	public String getGasto() {
		return gasto;
	}

	public void setGasto(String gasto) {
		this.gasto = gasto;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public List<Integer> getMesesAplica() {
		return mesesAplica;
	}

	public void setMesesAplica(List<Integer> mesesAplica) {
		this.mesesAplica = mesesAplica;
	}

	public Boolean getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Boolean liquidacion) {
		this.liquidacion = liquidacion;
	}

	public Integer getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}
	
	
    
    

}
