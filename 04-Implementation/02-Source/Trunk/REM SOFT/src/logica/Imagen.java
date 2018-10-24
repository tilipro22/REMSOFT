package logica;

public class Imagen {

	private Integer idImagen;
    private String path;
    private Boolean principal;
    private byte[] image;
    private String titulo;
    

    public Imagen(Integer idImagen, String path, Boolean principal, String titulo) {
        this.idImagen = idImagen;
        this.path = path;
        this.principal = principal;
        this.titulo = titulo;
    }
    
    

    public Imagen(Integer idImagen, String path, Boolean principal, byte[] image, String titulo) {
		this.idImagen = idImagen;
		this.path = path;
		this.principal = principal;
		this.image = image;
		this.titulo = titulo;
	}
    

    /* Getters & Setters */
    
   

	public String getPath() {
        return path;
    }

    public Integer getIdImagen() {
		return idImagen;
	}



	public void setIdImagen(Integer idImagen) {
		this.idImagen = idImagen;
	}



	public void setPath(String path) {
        this.path = path;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	@Override
	public String toString() {
		return this.idImagen+", "+this.titulo+". Principal: "+this.principal;
	}
    
	
    
    
}
