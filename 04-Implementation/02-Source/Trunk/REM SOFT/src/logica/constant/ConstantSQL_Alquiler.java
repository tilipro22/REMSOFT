package logica.constant;

public interface ConstantSQL_Alquiler {
	
	/*
	    ******************
	    ***** INSERT *****
	    ******************
	*/
	
	static final String SQL_INSERT_ALQUILER = "INSERT INTO Contrato_Alquiler (idInmueble, idInquilino, idGarante, montoTotal, "
			+ "fechaInicio, fechaFin, diaVto, porcentajeMora, mesesDeposito, precioDeposito, observaciones, estadoAlquiler) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	// ----------------------------------------------------------------------
	
	static final String SQL_INSERT_PAGO_CONTRATO = "INSERT INTO Pagos_Contrato (mesCorrespondiente, anioCorrespondiente,"
			+ "monto, montoPagado, pagado, idContrato, estadoPago)"
			+ "VALUES (?,?,?,?,?,?,?)";

	/*
	    ******************
	    ***** SEARCH *****
	    ******************
	*/
	
// Trae todos los alquileres de registrados
	static final String SQL_GET_LIST_ALQUILERES = "SELECT idContrato, idInmueble, idInquilino, idGarante, montoTotal, "
			+ "fechaInicio, fechaFin, porcentajeMora, diaVto, estadoAlquiler, "
			+ "mesesDeposito, precioDeposito, observaciones FROM Contrato_Alquiler";
	
// Trae todos los pagos por el Id del Alquiler
	static final String SQL_GET_LIST_PAGOS_BY_IDALQUILER = "SELECT idPago, mesCorrespondiente, anioCorrespondiente, monto, montoPagado, "
			+ "pagado, estadoPago, fechaPago FROM Pagos_Contrato WHERE idContrato=";
	
// Trae el ultimo idContrato agregado
	static final String SQL_GET_IDALQUILER_LAST = "SELECT MAX(idContrato) FROM Contrato_Alquiler";
	
	
	/*
	    ******************
	    ***** UPDATE *****
	    ******************
	*/
	
// Updatea un pago de alquiler segun el ID
    static final String SQL_UPDATE_PAGO_ALQUILER = "UPDATE Pagos_Contrato SET mesCorrespondiente=?, anioCorrespondiente=?, monto=?, montoPagado=?, pagado=?, estadoPago=?, fechaPago=? WHERE idPago=?";
}
