package app.alquileres;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import app.inmuebles.CtrlInmueble;
import control.CtrlAddPersona;
import logica.Alquiler;
import logica.ConexionBD_msSQL;
import logica.Inmueble;
import logica.PagoAlquiler;
import logica.Persona;
import logica.constant.ConstantSQL_Alquiler;

public class CtrlAlquileres {
	
	//private Integer idAlquiler;
	private Connection conexion;
	
	/* Constructors */
	public CtrlAlquileres() {
		this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
	}
	
	
	/*****************
	 ***  INSERTS  ***
	 *****************
	 */
	
	
// Agregar un nuevo alquiler
	public Integer insertIntoNuevoAlquiler(Alquiler alquiler) {

		try {
			PreparedStatement pstmAlquiler = conexion.prepareStatement(ConstantSQL_Alquiler.SQL_INSERT_ALQUILER);
			pstmAlquiler.setInt(1, alquiler.getInmueble().getIdInmueble());
			pstmAlquiler.setInt(2, alquiler.getInquilino().getIdPersona());
			pstmAlquiler.setInt(3, alquiler.getGarante().getIdPersona());
			pstmAlquiler.setBigDecimal(4, alquiler.getMontoTotal());
			pstmAlquiler.setDate(5, new java.sql.Date(alquiler.getFechaInicio().getTime()));
			pstmAlquiler.setDate(6, new java.sql.Date(alquiler.getFechaFin().getTime()));
			pstmAlquiler.setInt(7, alquiler.getDiaVto());
			
			if (alquiler.getPorcentajeMora() == null)
				pstmAlquiler.setNull(8, Types.DECIMAL);
			else
				pstmAlquiler.setBigDecimal(8, alquiler.getPorcentajeMora());
			
			if (alquiler.getMesesDeposito() == null)
				pstmAlquiler.setNull(9, Types.INTEGER);
			else
				pstmAlquiler.setInt(9, alquiler.getMesesDeposito());
			
			if (alquiler.getPrecioDeposito() == null)
				pstmAlquiler.setNull(10, Types.DECIMAL);
			else
				pstmAlquiler.setBigDecimal(10, alquiler.getPrecioDeposito());
			
			pstmAlquiler.setString(11, alquiler.getObservaciones());
			pstmAlquiler.setString(12, alquiler.getEstadoAlquiler());
			//pstmAlquiler.setString(13, "SIN DEUDAS");
			
			pstmAlquiler.executeUpdate();
			
			
			// Actualizar estado inmueble
			CtrlInmueble ctrlInmueble = new CtrlInmueble();
			ctrlInmueble.updateInmueble_Estado("ALQUILADO", alquiler.getInmueble().getIdInmueble());
			
			return getUltimoIdContratoAlq();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public void insertIntoPagoAlquiler (PagoAlquiler pagoAlquiler, Integer idContrato) {
		
		try {
			PreparedStatement pstmPagoAlquiler = conexion.prepareStatement(ConstantSQL_Alquiler.SQL_INSERT_PAGO_CONTRATO);
			pstmPagoAlquiler.setInt(1, pagoAlquiler.getMesCorrespondiente());
			pstmPagoAlquiler.setInt(2, pagoAlquiler.getAnioCorrespondiente());
			pstmPagoAlquiler.setBigDecimal(3, pagoAlquiler.getMontoTotal());
			pstmPagoAlquiler.setBigDecimal(4, pagoAlquiler.getMontoPagado());
			pstmPagoAlquiler.setBoolean(5, false);
			pstmPagoAlquiler.setInt(6, idContrato);
			pstmPagoAlquiler.setString(7, pagoAlquiler.getEstadoPago());
			
			pstmPagoAlquiler.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*****************
	 ***  SEARCHS  ***
	 *****************
	 */
	
	private Integer getUltimoIdContratoAlq() {
        ResultSet resultSet;
        Integer idAlquiler = null;
        
        try {
            resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Alquiler.SQL_GET_IDALQUILER_LAST);
            if (resultSet.next())
                idAlquiler = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("No se pudo agregar el idInmueble");
            e.printStackTrace();
        }
        
        return idAlquiler;
    }
	
	/*****************
	 ***  LIST  ***
	 *****************
	 */
	
	public List<Alquiler> getListaAlquileres() {
		
		CtrlInmueble ctrlInmueble = new CtrlInmueble();
		CtrlAddPersona ctrlAddPersona = new CtrlAddPersona();
		
		ResultSet resultSet, resultSetPagos;
		List<Alquiler> listAlquiler = new ArrayList<>();
		
		try {
			/*
			 * "SELECT idContrato, idInmueble, idInquilino, idGarante, montoTotal, "
			+ "fechaInicio, fechaFin, porcentajeMora, diaVto, estadoAlquiler, "
			+ "mesesDeposito, precioDeposito, observaciones FROM Contracto_Alquiler"
			 */
			resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Alquiler.SQL_GET_LIST_ALQUILERES);
			
			while (resultSet.next()) {
				Alquiler alquiler = null;
				Integer idAlquiler = resultSet.getInt(1);
				Inmueble inmueble = ctrlInmueble.getInmuebleById(resultSet.getInt(2));
				Persona inquilino = ctrlAddPersona.getPersonaById(resultSet.getInt(3));
				Persona garante = ctrlAddPersona.getPersonaById(resultSet.getInt(4));
				
				BigDecimal montoTotal = resultSet.getBigDecimal(5);
				java.util.Date fechaInicio = resultSet.getDate(6);
				java.util.Date fechaFin = resultSet.getDate(7);
				BigDecimal porcentajeMora = resultSet.getBigDecimal(8);
				Integer diaVto = resultSet.getInt(9);
				String estadoAlquiler = resultSet.getString(10);
				Integer mesesDeposito = resultSet.getInt(11);
				BigDecimal precioDeposito = resultSet.getBigDecimal(12);
				String observaciones = resultSet.getString(13);
				
				alquiler = new Alquiler(inmueble, inquilino, garante, montoTotal, fechaInicio, fechaFin, porcentajeMora, diaVto, "", estadoAlquiler);
				alquiler.setIdAlquiler(idAlquiler);
				alquiler.setMesesDeposito(mesesDeposito);
				alquiler.setPrecioDeposito(precioDeposito);
				alquiler.setObservaciones(observaciones);
				
				// Generar pagos
				resultSetPagos = ((Statement) conexion.createStatement())
						.executeQuery(ConstantSQL_Alquiler.SQL_GET_LIST_PAGOS_BY_IDALQUILER+idAlquiler);

				while (resultSetPagos.next()) {
					Integer idPago = resultSetPagos.getInt(1);
					Integer mesCorrespondiente = resultSetPagos.getInt(2);
					Integer anioCorrespondiente = resultSetPagos.getInt(3);
					BigDecimal monto = resultSetPagos.getBigDecimal(4);
					BigDecimal montoPagado = resultSetPagos.getBigDecimal(5);
					Boolean pagado = resultSetPagos.getBoolean(6);
					String estadoPago = resultSetPagos.getString(7);
					java.util.Date fechaPago = resultSetPagos.getDate(8);
					
					PagoAlquiler pagoAlquiler = new PagoAlquiler(idPago, mesCorrespondiente, anioCorrespondiente, monto, montoPagado);
					pagoAlquiler.setPagado(pagado);
					pagoAlquiler.setEstadoPago(estadoPago);
					pagoAlquiler.setFechaPago(fechaPago);
					
					alquiler.getListPagos().add(pagoAlquiler);
				}
				
				listAlquiler.add(alquiler);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listAlquiler;
	}
	

	
	/*****************
	 ***  UPDATES  ***
	 *****************
	 */
	
	public void updateAsentarPago(PagoAlquiler pagoAlquiler) {
    	try {

			PreparedStatement pstm = conexion.prepareStatement(ConstantSQL_Alquiler.SQL_UPDATE_PAGO_ALQUILER);
			pstm.setInt(1, pagoAlquiler.getMesCorrespondiente());
			pstm.setInt(2, pagoAlquiler.getAnioCorrespondiente());
			pstm.setBigDecimal(3, pagoAlquiler.getMontoTotal());
			pstm.setBigDecimal(4, pagoAlquiler.getMontoPagado());
			pstm.setBoolean(5, pagoAlquiler.getPagado());
			pstm.setString(6, pagoAlquiler.getEstadoPago());
			pstm.setDate(7, new java.sql.Date(pagoAlquiler.getFechaPago().getTime()));
			pstm.setInt(8, pagoAlquiler.getNumPago());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
