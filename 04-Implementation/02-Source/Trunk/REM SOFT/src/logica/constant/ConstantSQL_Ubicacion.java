package logica.constant;

public interface ConstantSQL_Ubicacion {
	
							/* Paises */

	
		// Obtiene la lista de todos los paises ordenados alfabeticamente
	static final String SQL_PAISES_ORD_ALFAB = "SELECT DISTINCT (Pais) FROM Paises ORDER BY Pais ASC";
	
		// Obtiene la cantidad de paises que hay en la tabla
	static final String SQL_CANT_PAISES = "SELECT COUNT (DISTINCT Pais) AS total FROM Paises";

		// Obtiene el codigo de un pais segun su nombre
	static final String SQL_CODIGO_PAIS_BY_NOMBRE = "SELECT codigo FROM Paises WHERE Pais=?";
	
							/* Provincias */
	
		// Obtiene la cantidad de provincias que hay en un determinado pais
	static final String SQL_CANT_PROVINCIAS = "SELECT COUNT (provincia_nombre) as total FROM Provincia WHERE codigo=?";
	
		// Obtiene la lista de todas las provincias segun el pais
	static final String SQL_PROVINCIAS = "SELECT provincia_nombre FROM Provincia WHERE codigo=?";
	
	
							/* Ciudades */
	static final String SQL_CANT_CIUDADES = "SELECT COUNT (DISTINCT ciudad_nombre) as total FROM Ciudad WHERE provincia_id=?";
	
	static final String SQL_CIUDADES = "SELECT DISTINCT ciudad_nombre FROM Ciudad WHERE provincia_id=? ORDER BY ciudad_nombre ASC";
}
