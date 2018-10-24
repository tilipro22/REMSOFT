package app.ventas;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.inmuebles.CtrlInmueble;
import control.CtrlAddPersona;
import logica.ConexionBD_msSQL;
import logica.FormaPagoVta;
import logica.Inmueble;
import logica.Persona;
import logica.Venta;
import logica.constant.ConstantSQL_Venta;

public class CtrlVentas {
	
	 private Connection conexion;

	    public CtrlVentas() {
	        this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
	    }
	    
	    
	    /*******************
	     **** INSERTS ******
	     *******************/
	    
	    public void insertNuevaVenta (Venta venta) {
	    	try {
				PreparedStatement preparedStatement = conexion.prepareStatement(ConstantSQL_Venta.SQL_INSERT_NUEVA_VENTA);
				preparedStatement.setInt(1, venta.getInmueble().getIdInmueble());
				preparedStatement.setInt(2, venta.getComprador().getIdPersona());
				
				if (venta.getConyuge() == null)
					preparedStatement.setNull(3, Types.INTEGER);
				else
					preparedStatement.setInt(3, venta.getConyuge().getIdPersona());
				
				
				preparedStatement.setInt(4, venta.getMartillero().getIdPersona());
				
				if (venta.getEscribano() == null)
					preparedStatement.setNull(5, Types.INTEGER);
				else
					preparedStatement.setInt(5, venta.getEscribano().getIdPersona());
					
				preparedStatement.setDate(6, new java.sql.Date(venta.getFecha().getTime()));
				preparedStatement.setBigDecimal(7, venta.getPrecioVenta());
				preparedStatement.setString(8, venta.getMoneda());
				preparedStatement.setBigDecimal(9, venta.getComisionPct());
				preparedStatement.setInt(10, venta.getDiasEscriturizacion());
				preparedStatement.setBigDecimal(11, venta.getMultaEscriturizacion());
				
				if (venta.getObservacion() == null)
					preparedStatement.setNull(12, Types.VARCHAR);
				else
					preparedStatement.setString(12, venta.getObservacion());
				
				preparedStatement.setBoolean(13, venta.getEscriturizado());
				preparedStatement.setBoolean(14, venta.getReservado());
				preparedStatement.setString(15, venta.getEstado());
				
				preparedStatement.executeUpdate();
				
				Integer idContratoVta = getIdContratoVenta();
				
				List<FormaPagoVta> formaPagoVtas = venta.getFormaPagoVtas();
				
				for (FormaPagoVta formaPagoVta : formaPagoVtas) {
					insertNuevoPago(formaPagoVta, idContratoVta);
				}
				
				CtrlInmueble ctrlInmueble = new CtrlInmueble();
				
				if (! venta.getReservado()) {
					ctrlInmueble.updateInmueble_Estado("VENDIDO", venta.getInmueble().getIdInmueble());
				}
				else {
					ctrlInmueble.updateInmueble_Estado("RESERVADO", venta.getInmueble().getIdInmueble());
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    
	    public void insertNuevoPago (FormaPagoVta pago, Integer idContratoVta) {
	    	
	    	try {
				PreparedStatement preparedStatement = conexion.prepareStatement(ConstantSQL_Venta.SQL_INSERT_NUEVO_PAGO);
				preparedStatement.setString(1, pago.getDescripcion());
				preparedStatement.setBigDecimal(2, pago.getHonorarios());
				preparedStatement.setBigDecimal(3, pago.getMontoTotal());
				preparedStatement.setBigDecimal(4, pago.getMontoPagado());
				preparedStatement.setBoolean(5, pago.getEstaPagado());
				
				if (pago.getFechaPago() == null)
					preparedStatement.setNull(6, Types.DATE);
				else
					preparedStatement.setDate(6, new java.sql.Date(pago.getFechaPago().getTime()));
				
				preparedStatement.setBoolean(7, pago.getTieneVto());
				
				if (pago.getFechaVto() == null)
					preparedStatement.setNull(8, Types.DATE);
				else
					preparedStatement.setDate(8, new java.sql.Date(pago.getFechaVto().getTime()));
				
				preparedStatement.setBigDecimal(9, pago.getInteresDiario());
				preparedStatement.setString(10, pago.getEstado());
				preparedStatement.setInt(11, idContratoVta);
				
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    
	    /******************
	     **** SELECT ******
	     ******************/
	    
	    public List<FormaPagoVta> getListFormaPagoVtaByIdContrato (Venta venta) {
	    	List<FormaPagoVta> lFormaPagoVtas = new ArrayList<>();
	    	
	    	try {
				ResultSet rs = ((Statement) conexion.createStatement())
						.executeQuery(ConstantSQL_Venta.SQL_SELECT_LIST_PAGOS_BY_IDCONTRATO+venta.getIdVenta());
				
				while (rs.next()) {
					Integer idFormaPagoVta = rs.getInt("idPago");
					String descripcion = rs.getString("descripcion");
					BigDecimal honorarios = rs.getBigDecimal("honorarios");
					BigDecimal montoTotal = rs.getBigDecimal("montoTotal");
					BigDecimal montoPagado = rs.getBigDecimal("montoPagado");
					Boolean pagado = rs.getBoolean("pagado");
					Date fechaPago = rs.getDate("fechaDePago");
					Boolean tieneVto = rs.getBoolean("tieneVto");
					Date fechaVto = rs.getDate("fechaVto");
					BigDecimal interesVto = rs.getBigDecimal("interesVto");
					String estado = rs.getString("estadoVta");
					
					FormaPagoVta formaPagoVta = new FormaPagoVta(idFormaPagoVta, descripcion, honorarios, montoTotal, null, fechaPago, montoPagado, fechaVto, interesVto);
					formaPagoVta.setEstaPagado(pagado);
					formaPagoVta.setTieneVto(tieneVto);
					formaPagoVta.setEstado(estado);
					formaPagoVta.setMoneda(venta.getMoneda());
					lFormaPagoVtas.add(formaPagoVta);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    	return lFormaPagoVtas;
	    }
	    
	    public List<Venta> getListVentas() {
	    	List<Venta> listVentas = new ArrayList<>();
	    	CtrlInmueble ctrlInmueble = new CtrlInmueble();
	    	CtrlAddPersona ctrlPersona = new CtrlAddPersona();
	    	
	    	try {
				ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Venta.SQL_SELECT_LIST_VENTAS);
				
				while (resultSet.next()) {
					Integer idVenta = resultSet.getInt("idVenta");
					Integer idInmueble = resultSet.getInt("idInmueble");
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					Integer idComprador = resultSet.getInt("idComprador");
					Persona comprador = ctrlPersona.getPersonaById(idComprador);
					
					Integer idConyuge = resultSet.getInt("idConyuge");
					Persona conyuge = null;
					if (idConyuge != null && idConyuge != 0)
						conyuge = ctrlPersona.getPersonaById(idConyuge);
					
					Integer idMartillero = resultSet.getInt("idMartillero");
					Persona martillero = ctrlPersona.getPersonaById(idMartillero);
					
					Integer idEscribano = resultSet.getInt("idEscribano");
					Persona escribano = null;
					if (idEscribano != null && idEscribano != 0)
						escribano = ctrlPersona.getPersonaById(idEscribano);
					
					Date fecha = resultSet.getDate("fecha");
					BigDecimal precioVenta = resultSet.getBigDecimal("precio_vta");
					String moneda = resultSet.getString("moneda");
					BigDecimal comision = resultSet.getBigDecimal("comision");
					Integer diasEscrituracion = resultSet.getInt("diasEscrituracion");
					BigDecimal multaEscrituracion = resultSet.getBigDecimal("multaEscrituracion");
					String observaciones = resultSet.getString("observaciones");
					Boolean escriturizado = resultSet.getBoolean("escriturizado");
					Boolean reservado = resultSet.getBoolean("reservado");
					String estado = resultSet.getString("estado");
					
					Venta venta = new Venta(idVenta, inmueble, comprador, conyuge, martillero, escribano, fecha, precioVenta, 
							moneda, comision, diasEscrituracion, multaEscrituracion, escriturizado, observaciones, estado, reservado);
					
					venta.setFormaPagoVtas(getListFormaPagoVtaByIdContrato(venta));
					
					listVentas.add(venta);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return listVentas;
	    }
	    
	    
	    /*******************
	     **** GETS ******
	     *******************/
	    
	    public Integer getIdContratoVenta() {
	    	try {
				ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Venta.SQL_GET_IDVENTA_LAST);
				resultSet.next();

				
				return resultSet.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return null;
	    }
	    
	    
	    /*******************
	     **** UPDATES ******
	     *******************/
	    
	    public void updateVenta(Venta venta) {
	    	try {
	    		/*
	    		 * "UPDATE Contrato_Venta SET fecha=?, precio_vta=?, moneda=?, comision=?, diasEscrituracion=?,"
			+ " multaEscrituracion=?, observaciones=?, escriturizado=?, reservado=?, estado=? WHERE idVenta=?"
	    		 */
	    		PreparedStatement preparedStatement = conexion.prepareStatement(ConstantSQL_Venta.SQL_UPDATE_VENTA);
	    		preparedStatement.setDate(1, (java.sql.Date) venta.getFecha());
	    		preparedStatement.setBigDecimal(2, venta.getPrecioVenta());
	    		preparedStatement.setString(3, venta.getMoneda());
	    		preparedStatement.setBigDecimal(4, venta.getComisionPct());
	    		preparedStatement.setInt(5, venta.getDiasEscriturizacion());
	    		preparedStatement.setBigDecimal(6, venta.getMultaEscriturizacion());
	    		preparedStatement.setString(7, venta.getObservacion());
	    		preparedStatement.setBoolean(8, venta.getEscriturizado());
	    		preparedStatement.setBoolean(9, venta.getReservado());
	    		preparedStatement.setString(10, venta.getEstado());
	    		preparedStatement.setInt(11, venta.getIdVenta());
	    		
	    		preparedStatement.executeUpdate();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    public void updateEstadoVenta (String estado, Integer idVenta) {
	    	try {
				PreparedStatement preparedStatement = conexion.prepareStatement(ConstantSQL_Venta.SQL_UPDATE_ESTADO_VENTA);
				preparedStatement.setString(1, estado);
				preparedStatement.setInt(2, idVenta);
				
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    public void updateEscriturarVenta (Integer idVenta) {
	    	try {
	    		PreparedStatement preparedStatement = conexion.prepareStatement(ConstantSQL_Venta.SQL_UPDATE_ESCRITURAR);
	    		preparedStatement.setBoolean(1, true);
	    		preparedStatement.setInt(2, idVenta);
	    		
	    		preparedStatement.executeUpdate();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	
	    	updateEstadoVenta(Venta.ESTADOS_ARRAY[Venta.ESTADO_ESCRITURIZADO], idVenta);
	    }
	    
	    public void updatePago (FormaPagoVta formaPagoVta) {
	    	/*
	    	 * "UPDATE Pago_Venta SET descripcion=?, honorarios=?, montoTotal=?, montoPagado=?, pagado=?, "
			+ "fechaDePago=?, estadoVta=? WHERE idPago=?"
	    	 */
	    	
	    	try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Venta.SQL_UPDATE_PAGO_BY_ID);
				ps.setString(1, formaPagoVta.getDescripcion());
				ps.setBigDecimal(2, formaPagoVta.getHonorarios());
				ps.setBigDecimal(3, formaPagoVta.getMontoTotal());
				ps.setBigDecimal(4, formaPagoVta.getMontoPagado());
				ps.setBoolean(5, formaPagoVta.getEstaPagado());
				ps.setDate(6, new java.sql.Date(formaPagoVta.getFechaPago().getTime()));
				ps.setString(7, formaPagoVta.getEstado());
				ps.setInt(8, formaPagoVta.getIdPago());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    }
	    
	    /*
	    public static void main(String[] args) {
	    	CtrlVentas ctrlVentas = new CtrlVentas();
	    	ctrlVentas.getListVentas();
	    }*/

}
