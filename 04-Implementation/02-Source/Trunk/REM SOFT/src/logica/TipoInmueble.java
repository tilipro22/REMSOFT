package logica;

public class TipoInmueble {
	
	static final String CASA = "CASA";
	static final String DPTO = "DPTO";
	static final String GARAGE = "GARAGE";
	
	private String codigo;
	
	public TipoInmueble (Integer option) {
        switch (option) {
            case 1:
                this.codigo = CASA;
                break;

            case 2:
                this.codigo = DPTO;
                break;

            case 3:
                this.codigo = GARAGE;
                break;
        }
    }

    public String getCodigo() {
        return codigo;
    }

}
