package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inmueble {

    //Obligatorios
    private Integer idInmueble;
    private String tipoInmueble;
    private Date fechaIngreso;
    private Ubicacion ubicacion;
    private Persona propietario;
    private String codigo; //Se genera solo apartir del tipoInmueble+idInmueble
    private String estado;

    public Inmueble(String tipoInmueble, java.util.Date fecha, Ubicacion ubicacion, Persona propietario) {
        this.tipoInmueble = tipoInmueble;
        this.fechaIngreso = fecha;
        this.ubicacion = ubicacion;
        this.propietario = propietario;
        //generateCodigo();
    }

    //No obligatorios
    private String descripcion;
    private String nomenclatura;
    private String condicion;
    private Boolean cartelPublicado;
    private List<Operacion> operaciones = new ArrayList<Operacion>();
    private List<GastoFijo> gastoFijos = new ArrayList<>();
    private List<Imagen> imagenes = new ArrayList<Imagen>();


    private String generateCodigo() {
        return (tipoInmueble+codigo);
    }


    // Getters & Setters
    public Integer getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(Integer idInmueble) {
        this.idInmueble = idInmueble;
    }

    public String getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(String tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Persona getPropietario() {
        return propietario;
    }

    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNomenclatura() {
        return nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getCartelPublicado() {
        return cartelPublicado;
    }

    public void setCartelPublicado(Boolean cartelPublicado) {
        this.cartelPublicado = cartelPublicado;
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public List<GastoFijo> getGastoFijos() {
        return gastoFijos;
    }

    public void setGastoFijos(List<GastoFijo> gastoFijos) {
        this.gastoFijos = gastoFijos;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
    
    

}
