package control;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logica.ConexionBD_msSQL;
import logica.ContactoPersona;
import logica.Persona;
import logica.Seguimiento;
import logica.constant.ConstantSQL_Inmueble;
import logica.constant.ConstantSQL_Persona;

public class CtrlAddPersona {
	
	//Insert 
	private final String sqlAgregarPersona = "INSERT INTO Persona (nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad, "
			+ "tipo_persona, profesion, sexo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String sqlAgregarContacto = "INSERT INTO Contacto (idPersona, telefono, celular, mail) VALUES (?, ?, ?, ?)";
	private final String sqlAgregarSeguimiento = "INSERT INTO Seguimiento (fecha, estado, detalle, idPersona) VALUES (?,?,?,?)";
	
	//Buscar persona por DNI
	private final String sqlQueryIdPersona = "SELECT idPersona FROM Persona WHERE dni LIKE '";
	
	private Connection conexion;
	
	
	public CtrlAddPersona () {
		this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
	}
	
	private void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo cerrar la conexion");
		}
	}
	
	private void abrirConexion() {
		this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
	}
	
	public boolean isDniRepetido (Persona p) {
		Integer idPersona = getIdPersona(p);
		
		if(idPersona > 0)
			return true;
		else
			return false;
	}
	
	//Agrega una persona
	public void agregarPersona(Persona p) {
		
		insertIntoPersona(p);
		Integer idPersona = insertIntoContacto(p);
		
		if (p.getSeguimientos().size() > 0)
			insertIntoSeguimientos(p, idPersona);
		
		
	}
	
	private void insertIntoPersona(Persona p) {
		try {
			PreparedStatement pstmAddPersona = conexion.prepareStatement(sqlAgregarPersona);
			pstmAddPersona.setString(1, p.getNombre());
			pstmAddPersona.setString(2, p.getApellido());
			pstmAddPersona.setObject(3, p.getDni());
			pstmAddPersona.setString(4, p.getDomicilio());
			pstmAddPersona.setString(5, p.getCiudad());
			pstmAddPersona.setString(6, p.getProvincia());
			pstmAddPersona.setString(7, p.getNacionalidad());
			pstmAddPersona.setString(8, p.getTipo());
			pstmAddPersona.setString(9, p.getProfesion());
			pstmAddPersona.setString(10, p.getSexo().toString());
			pstmAddPersona.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo realizar el alta");
		}
	}
	
	private Integer getIdPersona(Persona p) {
		ResultSet result;
		Integer idPersona = 0;
		
		try {
			result = ((Statement) conexion.createStatement()).executeQuery(sqlQueryIdPersona+p.getDni()+"'");
			if(result.next())
				idPersona = result.getInt("idPersona");
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idPersona;
	}
	
	private Integer insertIntoContacto(Persona p) {
		ResultSet result;
		Integer idPersona = null;
		try {
			result = ((Statement) conexion.createStatement()).executeQuery(sqlQueryIdPersona+p.getDni()+"'");
			result.next();
			idPersona = result.getInt("idPersona");
			
			PreparedStatement pstmAddContacto = conexion.prepareStatement(sqlAgregarContacto);
			pstmAddContacto.setInt(1, idPersona);
			pstmAddContacto.setObject(2, p.getContacto().getTelefono());
			pstmAddContacto.setObject(3, p.getContacto().getCelular());
			pstmAddContacto.setString(4, p.getContacto().getMail());
			pstmAddContacto.executeUpdate();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo realizar el alta del contacto");
		}
		
		
		return idPersona;
	}
	

	private void insertIntoSeguimientos(Persona p, Integer idPersona) {
		try {
			for (int i=0; i < p.getSeguimientos().size(); i++) {
				Seguimiento seguimiento = p.getSeguimientos().get(i);
				PreparedStatement pstmAddSeguimiento = conexion.prepareStatement(sqlAgregarSeguimiento);
				pstmAddSeguimiento.setDate(1, seguimiento.getFecha());
				pstmAddSeguimiento.setInt(2, seguimiento.getEstado().getNumero());
				pstmAddSeguimiento.setString(3, seguimiento.getDetalle());
				pstmAddSeguimiento.setInt(4, idPersona);
				pstmAddSeguimiento.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo realizar el alta de Seguimiento/s");
		}
	}
	
	public void modificarPersona(Persona p) {
		
		String queryUpdate_Persona = "UPDATE Persona SET nombre=?, apellido=?, domicilio=?, ciudad=?, provincia=?, nacionalidad=?, "
				+ "tipo_persona=?, profesion=?, sexo=?, observaciones=? WHERE idPersona = "+p.getIdPersona();
		
		try {
			PreparedStatement pstm = conexion.prepareStatement(queryUpdate_Persona);
			pstm.setString(1, p.getNombre());
			pstm.setString(2, p.getApellido());
			pstm.setString(3, p.getDomicilio());
			pstm.setString(4, p.getCiudad());
			pstm.setString(5, p.getProvincia());
			pstm.setString(6, p.getNacionalidad());
			pstm.setString(7, p.getTipo());
			pstm.setString(8, p.getProfesion());
			pstm.setString(9, p.getSexo().toString());
			pstm.setString(10, p.getObservaciones());
			pstm.executeUpdate();
			
			if (p.getContacto() != null) {
				String queryUpdate_ContPersona = "UPDATE Contacto SET telefono=?, celular=?, mail=? WHERE idPersona = "+p.getIdPersona();
				
				pstm = conexion.prepareStatement(queryUpdate_ContPersona);
				pstm.setObject(1, p.getContacto().getTelefono());
				pstm.setObject(2, p.getContacto().getCelular());
				pstm.setString(3, p.getContacto().getMail());
				pstm.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*if (p.getSeguimientos().size() > 0) {
			for (int i = 0; i < p.getSeguimientos().size(); i++) {
				
			}
		}*/
	}
	
	public void eliminarPersona() {
		
	}
	
	//Buscar lista de Personas
	private final String sqlQueryListaPersonas = "SELECT Persona.idPersona, nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad, "
			+ "tipo_persona, profesion, sexo, observaciones, telefono, celular, mail FROM Persona "
			+ "INNER JOIN Contacto ON Persona.idPersona = Contacto.idPersona ORDER BY apellido";
	
	//Filtrar seguimientos
	private final String sqlQuerySeguimientos = "SELECT * FROM Seguimiento WHERE idPersona = '";
	
	public ArrayList<Persona> getListaPersonas() {
		
		ArrayList<Persona> personas = new ArrayList<Persona>();
		
		ResultSet result, resultSeg;
		Integer idPersona = null;
		try {
			result = ((Statement) conexion.createStatement()).executeQuery(sqlQueryListaPersonas);
			
			while (result.next()) {
				// Crear persona basica
				idPersona = result.getInt(1);
				String nombre = result.getString(2);
				String apellido = result.getString(3);
				BigInteger dni = new BigInteger(result.getString(4));
				String domicilio = result.getString(5);
				String ciudad = result.getString(6);
				String provincia = result.getString(7);
				String nacionalidad = result.getString(8);
				
				Persona p=new Persona(nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad);
				
				p.setTipo(result.getString(9));
				p.setProfesion(result.getString(10));
				p.setSexo(result.getString(11).charAt(0));
				p.setObservaciones(result.getString(12));
				p.setIdPersona(idPersona);
				
				//Crear contacto
				BigInteger telefono = null;
				if (result.getString(13) != null)
					telefono= new BigInteger(result.getString(13));
				
				BigInteger celular = null; 
				if (result.getString(14) != null)
					celular = new BigInteger(result.getString(14));
				String mail = result.getString(15);
				
				p.setContacto(new ContactoPersona(telefono, celular, mail));
				
				ArrayList<Seguimiento> arraySeg = new ArrayList<Seguimiento>();
				//Crear Seguimientos
				resultSeg = ((Statement) conexion.createStatement()).executeQuery(sqlQuerySeguimientos+idPersona+"'");
				while (resultSeg.next()) {
					Date fecha = resultSeg.getDate("fecha");
					String detalle = resultSeg.getString("detalle");
					Integer estado = resultSeg.getInt("estado");
					Integer idSeguimiento = resultSeg.getInt("idSeguimiento");
					arraySeg.add(new Seguimiento(fecha, detalle, estado, idSeguimiento));
				}
				
				p.setSeguimientos(arraySeg);
				
				
				personas.add(p);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se pudo realizar el alta del contacto");
		}
		
		return personas;
	}
	
	public Persona getPersonaById (Integer id) {
		ResultSet result, resultContacto, resultSeg;
		Persona p = null;
		
		try {
			result = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_PERSONA_WID+id);
			
			while (result.next()) {
				// Crear persona basica
				Integer idPersona = id;
				String nombre = result.getString(1);
				String apellido = result.getString(2);
				BigInteger dni = new BigInteger(result.getString(3));
				String domicilio = result.getString(4);
				String ciudad = result.getString(5);
				String provincia = result.getString(6);
				String nacionalidad = result.getString(7);
				
				p=new Persona(nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad);
				resultContacto = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_CONTACTO_PERSONA_WID+id);
				resultContacto.next();
				
				p.setTipo(result.getString(8));
				p.setProfesion(result.getString(9));
				p.setSexo(result.getString(10).charAt(0));
				p.setObservaciones(result.getString(11));
				p.setIdPersona(idPersona);
				
				//Crear contacto
				BigInteger telefono = null;
				if (resultContacto.getString(1) != null)
					telefono= new BigInteger(resultContacto.getString(1));
				
				BigInteger celular = null; 
				if (resultContacto.getString(2) != null)
					celular = new BigInteger(resultContacto.getString(2));
				String mail = resultContacto.getString(3);
				
				p.setContacto(new ContactoPersona(telefono, celular, mail));
				
				ArrayList<Seguimiento> arraySeg = new ArrayList<Seguimiento>();
				//Crear Seguimientos
				resultSeg = ((Statement) conexion.createStatement()).executeQuery(sqlQuerySeguimientos+idPersona+"'");
				while (resultSeg.next()) {
					Date fecha = resultSeg.getDate("fecha");
					String detalle = resultSeg.getString("detalle");
					Integer estado = resultSeg.getInt("estado");
					Integer idSeguimiento = resultSeg.getInt("idSeguimiento");
					arraySeg.add(new Seguimiento(fecha, detalle, estado, idSeguimiento));
				}
				
				p.setSeguimientos(arraySeg);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return p;
	}

}
