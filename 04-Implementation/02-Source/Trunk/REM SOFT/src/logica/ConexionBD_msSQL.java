package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD_msSQL {
	private static final ConexionBD_msSQL conexionMSSQL=new ConexionBD_msSQL();
	private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String nameBD = "Prueba";
	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName="+nameBD;
	private static final String login = "tili";  
	private static final String clave = "tili"; 
	private Connection conexion;
	
	private ConexionBD_msSQL() {
		try {
			Class.forName(driver);
			this.conexion = DriverManager.getConnection(url, login, clave);
			System.out.println("Conexion hecha");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el driver");
		} catch (SQLException e) {
			System.out.println("Usuario o pass: incorrecta");
		}
	}
	

	public static ConexionBD_msSQL getConexionMSSQL() {
		return conexionMSSQL;
	}
	

	public Connection getConexion() {
		return this.conexion;
	}

}
