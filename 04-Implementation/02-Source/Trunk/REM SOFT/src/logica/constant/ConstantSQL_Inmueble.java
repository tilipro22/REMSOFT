package logica.constant;

public interface ConstantSQL_Inmueble {
	
	 /*
	    ******************
	    ***** INSERT *****
	    * ****************
	     */
	static final String SQL_INSERT_INMUEBLE = "INSERT INTO Inmueble (tipo, fecha, idPropetario, nomenclatura, " +
	            "estado, cartelPublicado, descripcion, condicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	static final String SQL_INSERT_UBICACION = "INSERT INTO Ubicacion_Inmueble (pais, provincia, ciudad, barrio, calle, numero, " +
	            "piso, dpto, cp, idInmueble) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	static final String SQL_INSERT_OPERACION = "INSERT INTO Operacion (tipoOperacion, precio, moneda, comision, total, observacion, idInmueble) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	static final String SQL_INSERT_GASTOFIJO = "INSERT INTO Gasto_Fijo (tipoGasto, monto, mesesAplica, liquidacion, idInmueble)" +
            "VALUES (?, ?, ?, ?, ?)";
	
	static final String SQL_INSERT_IMAGEN = "INSERT INTO Imagen (foto, principal, titulo, idInmueble) VALUES (?, ?, ?, ?)";
	
	/*
	    ******************
	    ***** SEARCH *****
	    ******************
	*/
	
	// Buscar inmueble por Id
	static final String SQL_SEARCH_INMUEBLE_BY_ID = "SELECT idInmueble, tipo, fecha, idPropetario, nomenclatura, estado, cartelPublicado, descripcion, condicion FROM Inmueble WHERE idInmueble=";
	
	//Buscar todos los Inmuebles
    static final String SQL_SEARCH_ALL_INMUEBLES = "SELECT idInmueble, tipo, fecha, idPropetario, nomenclatura, estado, cartelPublicado, descripcion, condicion FROM Inmueble";
	
	//Buscar Ubicacion de Inmueble segun IdInmueble
    static final String SQL_SEARCH_UBICACION = "SELECT pais, provincia, ciudad, barrio, calle, numero, piso, dpto, cp FROM Ubicacion_Inmueble WHERE idInmueble =";

    // ---------------------------------------

    static final String SQL_SEARCH_OPERACIONES_BY_IDINMUEBLE = "SELECT idOperacion, tipoOperacion, precio, moneda, comision, observacion FROM Operacion WHERE idInmueble=";
    
    // ---------------------------------------

    static final String SQL_SEARCH_GASTOSFIJOS_BY_IDINMUEBLE = "SELECT idGastoFijo, tipoGasto, monto, mesesAplica, liquidacion FROM Gasto_Fijo WHERE idInmueble=";
    
    // ---------------------------------------
    
    static final String SQL_SEARCH_IMAGENES_BY_IDINMUEBLE = "SELECT idImagen, foto, principal, titulo FROM Imagen WHERE idInmueble=";
    
    // ---------------------------------------

    //Buscar Tipos de Operaciones
    static final String SQL_SEARCH_TIPO_OPERACIONES = "SELECT * FROM Tipo_Operacion";
    
    //Buscar Tipo de Inmueble por ID
    static final String SQL_SEARCH_TIPO_INMUEBLE_BY_ID = "SELECT tipo FROM Tipo_Inmueble WHERE id =";

    //Obtener cantidad de Tipos de Inmuebles
    static final String SQL_SELECT_COUNT_TIPO_OPERACIONES = "SELECT COUNT (id) AS total FROM Tipo_Operacion";
    
    //Buscar Tipo de Inmueble por el nombre
    static final String SQL_SEARCH_TIPO_OPERACION_BY_TIPO = "SELECT id FROM Tipo_Operacion WHERE nombre LIKE ";
    
    //Buscar Tipo de Operacion por id
    static final String SQL_SEARCH_TIPO_OPERACION_BY_ID = "SELECT nombre FROM Tipo_Operacion WHERE id=";
    
    // -----------------------------------------

    //Buscar Tipos de Inmuebles
    static final String SQL_SEARCH_TIPO_INMUEBLES = "SELECT * FROM Tipo_Inmueble";
    
    //Buscar Tipo de Inmueble por el nombre
    static final  String SQL_SEARCH_TIPO_INMUEBLE_BY_TIPO = "SELECT id FROM Tipo_Inmueble WHERE tipo LIKE ";

    //Obtener cantidad de Tipos de Inmuebles
    static final String SQL_SELECT_COUNT_TIPO_INMUEBLES = "SELECT COUNT (id) AS total FROM Tipo_Inmueble";
    
    // ----------------------------------------
    
    //Buscar Tipos de Gastos Fijo
    static final String SQL_SEARCH_TIPO_GASTOS_FIJOS = "SELECT * FROM Tipo_GastoFijo";

    //Buscar Id de Gasto Fijo por Nombre
    static final String SQL_SEARCH_GASTOFIJOID_BY_NOMBRE = "SELECT id FROM Tipo_GastoFijo WHERE nombre LIKE ";
    
    // Buscar NOMBRE por el id gel Gasto Fijo
    static final String SQL_SEARCH_GASTOFIJO_NOMBRE_BY_ID = "SELECT nombre FROM Tipo_GastoFijo WHERE id=";
    
    //Obtener cantidad de Tipos de Operaciones
    static final String SQL_SELECT_COUNT_TIPO_GASTOFIJO = "SELECT COUNT (id) AS total FROM Tipo_GastoFijo";
    
    //Obtener cantidad de Localidades
    static final String SQL_SELECT_COUNT_LOCALIDADES = "SELECT COUNT (DISTINCT ciudad) FROM Ubicacion_Inmueble WHERE pais LIKE 'Argentina'";
    
    //Obtener todas las Localicades/Ciudades
    static final String SQL_SELECT_CIUDADES = "SELECT DISTINCT ciudad FROM Ubicacion_Inmueble WHERE pais LIKE 'Argentina'";

    // -------------------------------------


    // Trae el ultimo idInmueble agregado
    static final String SQL_GET_ID_MAX = "SELECT MAX(idInmueble) FROM Inmueble";
    
    /*
	    ******************
	    ***** UPDATE *****
	    ******************
	*/
    
    static final String SQL_UPDATE_INMUEBLE = "UPDATE Inmueble SET nomenclatura=?, cartelPublicado=?, descripcion=?, condicion=? WHERE idInmueble=";

    static final String SQL_UPDATE_INMUEBLE_UBICACION = "UPDATE Ubicacion_Inmueble SET pais=?, provincia=?, ciudad=?, barrio=?, calle=?, numero=?, piso=?, dpto=?, cp=? WHERE idInmueble=";
    
    static final String SQL_UPDATE_INMUEBLE_OPERACION = "UPDATE Operacion SET precio=?, moneda=?, comision=?, total=?, observacion=? WHERE idOperacion=";

    static final String SQL_UPDATE_INMUEBLE_GASTO = "UPDATE Gasto_Fijo SET tipoGasto=?, monto=?, mesesAplica=?, liquidacion=? WHERE idGastoFijo=";
    
    static final String SQL_UPDATE_INMUEBLE_ESTADO = "UPDATE Inmueble SET estado=? WHERE idInmueble=?";
    /*
	    ******************
	    ***** DELETE *****
	    ******************
	*/
    
    static final String SQL_DELETE_INMUEBLE_BY_ID = "DELETE FROM Inmueble WHERE idInmueble=?";
    
    static final String SQL_DELETE_UBICACION_BY_IDINMUEBLE = "DELETE FROM Ubicacion_Inmueble WHERE idInmueble=?";
    
    static final String SQL_DELETE_OPERACION_BY_IDINMUEBLE = "DELETE FROM Operacion WHERE idInmueble=?";
    
    static final String SQL_DELETE_GASTOS_BY_IDINMUEBLE = "DELETE FROM Gasto_Fijo WHERE idInmueble=?";
    
    static final String SQL_DELETE_IMAGENES_BY_IDINMUEBLE = "DELETE FROM Imagen WHERE idInmueble=?";
}
