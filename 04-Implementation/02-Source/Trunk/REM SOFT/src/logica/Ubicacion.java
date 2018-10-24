package logica;

public class Ubicacion {

	 private String pais;
	    private String provincia;
	    private String ciudad;
	    private String barrio; //no requerido
	    private String calle;
	    private Integer numero;
	    private Integer piso; //no requerido
	    private Character dpto; //no requerido
	    private Integer cp;

	    //Constructor
	    public Ubicacion(String pais, String provincia, String ciudad, String barrio, String calle, Integer numero, Integer piso, Character dpto, Integer cp) {
	        this.pais = pais;
	        this.provincia = provincia;
	        this.ciudad = ciudad;
	        this.barrio = barrio;
	        this.calle = calle;
	        this.numero = numero;
	        this.piso = piso;
	        this.dpto = dpto;
	        this.cp = cp;
	    }

	    //Getters & Setters
	    public String getPais() {
	        return pais;
	    }

	    public void setPais(String pais) {
	        this.pais = pais;
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

	    public String getBarrio() {
	        return barrio;
	    }

	    public void setBarrio(String barrio) {
	        this.barrio = barrio;
	    }

	    public String getCalle() {
	        return calle;
	    }

	    public void setCalle(String calle) {
	        this.calle = calle;
	    }

	    public Integer getNumero() {
	        return numero;
	    }

	    public void setNumero(Integer numero) {
	        this.numero = numero;
	    }

	    public Integer getPiso() {
	        return piso;
	    }

	    public void setPiso(Integer piso) {
	        this.piso = piso;
	    }

	    public Character getDpto() {
	        return dpto;
	    }

	    public void setDpto(Character dpto) {
	        this.dpto = dpto;
	    }

	    public Integer getCp() {
	        return cp;
	    }

	    public void setCp(Integer cp) {
	        this.cp = cp;
	    }
}
