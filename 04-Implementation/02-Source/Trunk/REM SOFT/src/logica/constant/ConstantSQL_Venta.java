package logica.constant;

public interface ConstantSQL_Venta {
	
	/*
	    ******************
	    ***** INSERT *****
	    ******************
	*/
	
	static final String SQL_INSERT_NUEVA_VENTA = "INSERT INTO Contrato_Venta (idInmueble, idComprador, idConyuge, idMartillero, idEscribano, "
			+ "fecha, precio_vta, moneda, comision, diasEscrituracion, multaEscrituracion, observaciones, escriturizado, reservado, estado) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	static final String SQL_INSERT_NUEVO_PAGO = "INSERT INTO Pago_Venta (descripcion, honorarios, montoTotal, montoPagado, pagado, fechaDePago, "
			+ "tieneVto, fechaVto, interesVto, estadoVta, idContratoVta) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	
	
	/*
	    ******************
	    ***** SEARCH *****
	    ******************
	*/
	
	// Trae una lista con todas los Contratos de Venta
	static final String SQL_SELECT_LIST_VENTAS = "SELECT * FROM Contrato_Venta";
	
	// Trae una lista con todos los Pagos
	static final String SQL_SELECT_LIST_PAGOS_BY_IDCONTRATO = "SELECT * FROM Pago_Venta WHERE idContratoVta=";
	
	// Trae el ultimo id de Contrato de Vta agregado
	static final String SQL_GET_IDVENTA_LAST = "SELECT MAX(idVenta) FROM Contrato_Venta";
	
	
	/*
	    ******************
	    ***** UPDATE *****
	    ******************
	*/
	
	static final String SQL_UPDATE_VENTA = "UPDATE Contrato_Venta SET fecha=?, precio_vta=?, moneda=?, comision=?, diasEscrituracion=?,"
			+ " multaEscrituracion=?, observaciones=?, escriturizado=?, reservado=?, estado=? WHERE idVenta=?";
	
	static final String SQL_UPDATE_ESTADO_VENTA = "UPDATE Contrato_Venta SET estado=? WHERE idVenta=?";
	
	static final String SQL_UPDATE_ESCRITURAR = "UPDATE Contrato_Venta SET escriturizado=? WHERE idVenta=?";
	
	static final String SQL_UPDATE_PAGO_BY_ID = "UPDATE Pago_Venta SET descripcion=?, honorarios=?, montoTotal=?, montoPagado=?, pagado=?, "
			+ "fechaDePago=?, estadoVta=? WHERE idPago=?";
	

}
