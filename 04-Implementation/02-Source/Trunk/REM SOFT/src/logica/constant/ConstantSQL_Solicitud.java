package logica.constant;

public interface ConstantSQL_Solicitud {
	
	// Constants 
	//static final String SQL_CONSULTA_BASICA = "SELECT * FROM Inmueble ";
			
	static final String SQL_CONSULTA_BASICA = "SELECT DISTINCT Inmueble.idInmueble FROM Inmueble " 
			+ "INNER JOIN Ubicacion_Inmueble ON Inmueble.idInmueble = Ubicacion_Inmueble.idInmueble "
			+ "INNER JOIN Operacion ON Inmueble.idInmueble = Operacion.idInmueble WHERE Inmueble.tipo=? ";
	
	static final String SQL_CONSULTA_CON_DOS_ALTERNATIVAS = "SELECT DISTINCT Inmueble.idInmueble FROM Inmueble " 
			+ "INNER JOIN Ubicacion_Inmueble ON Inmueble.idInmueble = Ubicacion_Inmueble.idInmueble "
			+ "INNER JOIN Operacion ON Inmueble.idInmueble = Operacion.idInmueble WHERE (Inmueble.tipo=? OR Inmueble.tipo=?)";
	
	static final String SQL_CONSULTA_CON_TRES_ALTERNATIVAS = "SELECT DISTINCT Inmueble.idInmueble FROM Inmueble " 
			+ "INNER JOIN Ubicacion_Inmueble ON Inmueble.idInmueble = Ubicacion_Inmueble.idInmueble "
			+ "WHERE (Inmueble.tipo=? OR Inmueble.tipo=? OR Inmueble.tipo=?) "
			+ "INNER JOIN Operacion ON Inmueble.idInmueble = Operacion.idInmueble";
	
	// Adds
	
	static final String SQL_ADD_PROVINCIA = " AND (provincia LIKE ?)";
	
	static final String SQL_ADD_CIUDAD = " AND (ciudad LIKE ?)";

	static final String SQL_ADD_OPERACION = " AND (tipoOperacion=?)";
	
	static final String SQL_ADD_PRECIO = " AND (precio BETWEEN ? AND ?)";

}
