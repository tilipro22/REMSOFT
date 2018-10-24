package logica;

public class Provincia {
	
	private Integer id;
	private String provincia;
	
	
	public Provincia(Integer id, String provincia) {
		this.id = id;
		this.provincia = provincia;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getProvincia() {
		return provincia;
	}


	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	
}
