package app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logica.ConexionBD_msSQL;
import logica.constant.ConstantSQL_Ubicacion;

public class CtrlUbicacion {
	
	private Connection conexion;
	
	public CtrlUbicacion () {
		this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
	}

	
	public String[] getListPaisesOrdenadosAlfab() {
		
		ResultSet resultSet;
		String[] arrayPaises = null;
		int i = 0;
		
		try {
			resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Ubicacion.SQL_CANT_PAISES);
			resultSet.next();
			
			arrayPaises = new String[resultSet.getInt("total")];
			
			resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Ubicacion.SQL_PAISES_ORD_ALFAB);
			
			while (resultSet.next()) {
				arrayPaises[i] = resultSet.getString("Pais");
				
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return arrayPaises;
	}
	
	
	public String[] getListaProvinciasByCodigoPais(String pais) {
		
		PreparedStatement psCodigoPais, psCantProvincias, psProvincias;
		
		String[] arrayProvincias = null;
		int i = 0;
		
		
		try {
				// Obtengo el codigo por el nombre del pais
			psCodigoPais = conexion.prepareStatement(ConstantSQL_Ubicacion.SQL_CODIGO_PAIS_BY_NOMBRE);
			psCodigoPais.setString(1, pais);
			
			ResultSet rsCodigoPais = psCodigoPais.executeQuery();
			rsCodigoPais.next();
			String codigoPais = rsCodigoPais.getString("codigo");
			
				// Obtengo la cantidad de provincias para definir el array
			psCantProvincias = conexion.prepareStatement(ConstantSQL_Ubicacion.SQL_CANT_PROVINCIAS);
			psCantProvincias.setString(1, codigoPais);
			
			ResultSet rsCantProvincias = psCantProvincias.executeQuery();
			rsCantProvincias.next();
			Integer cantProvincias = rsCantProvincias.getInt("total");
			
				// Creo la lista de provincias
			arrayProvincias = new String[cantProvincias];
			
			psProvincias = conexion.prepareStatement(ConstantSQL_Ubicacion.SQL_PROVINCIAS);
			psProvincias.setString(1, codigoPais);
			
			ResultSet rsProvincias  = psProvincias.executeQuery();
			
			while (rsProvincias.next()) {
				String provincia = rsProvincias.getString("provincia_nombre");
				arrayProvincias[i] = provincia;
				
				i++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return arrayProvincias;
	}
	
	
	public String[] getListaCiudadesByIdProvincia(int idProvincia) {
		
		PreparedStatement psCantCiudades, psCiudadesByProvincia;
		
		String[] arrayCiudades = null;
		int i = 0;
		
		try {
			psCantCiudades = conexion.prepareStatement(ConstantSQL_Ubicacion.SQL_CANT_CIUDADES);
			psCantCiudades.setInt(1, idProvincia);
			
			ResultSet rsCantCiudades = psCantCiudades.executeQuery();
			rsCantCiudades.next();
			
			int cantCiudades = rsCantCiudades.getInt("total");
			arrayCiudades = new String[cantCiudades];
			
			psCiudadesByProvincia = conexion.prepareStatement(ConstantSQL_Ubicacion.SQL_CIUDADES);
			psCiudadesByProvincia.setInt(1, idProvincia);
			
			ResultSet rsCiudades = psCiudadesByProvincia.executeQuery();
			
			while (rsCiudades.next()) {
				arrayCiudades[i] = rsCiudades.getString("ciudad_nombre");
				
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return arrayCiudades;
	}
	
	public static void main (String[] args) {
		CtrlUbicacion ctrlPaises = new CtrlUbicacion();
		String[] array = ctrlPaises.getListaCiudadesByIdProvincia(1);
		
		System.out.println(array.length);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
}
