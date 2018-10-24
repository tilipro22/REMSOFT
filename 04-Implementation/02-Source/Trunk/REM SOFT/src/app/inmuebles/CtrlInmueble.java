package app.inmuebles;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import logica.ConexionBD_msSQL;
import logica.ContactoPersona;
import logica.GastoFijo;
import logica.Imagen;
import logica.Inmueble;
import logica.Operacion;
import logica.Persona;
import logica.Ubicacion;
import logica.constant.ConstantSQL_Inmueble;
import logica.constant.ConstantSQL_Persona;

public class CtrlInmueble {
	
	private Integer idInmueble;
    private Connection conexion;

    public CtrlInmueble() {
        this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
        //System.out.println("Conexion con Inmueble [LISTA]");
    }
    
    // Obtener Tipo de Inmueble por Nombre
    public Integer getNameTipoInmueble (String nombre) {
    	ResultSet rsTipoInmueble;
    	Integer tipoInmueble = null;
    	
    	try {
			rsTipoInmueble = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_INMUEBLE_BY_TIPO+"'"+nombre+"'");
			rsTipoInmueble.next();
			
			return rsTipoInmueble.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return tipoInmueble;
    }

    // Obtener Tipo de Inmueble por ID
    public String getTipoInmueble(Integer id) {
    	ResultSet rsTipoInmueble;
    	String tipoInmueble = null;
    	
    	try {
			rsTipoInmueble = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_INMUEBLE_BY_ID+id);
			rsTipoInmueble.next();
			
			return rsTipoInmueble.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return tipoInmueble;
    }
    
// Obtener inmueble por id
    public Inmueble getInmuebleById(Integer id) {
    	ResultSet rsInmueble;
    	Inmueble inmueble = null;
    	
    	try {
			rsInmueble = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_INMUEBLE_BY_ID+id);
			inmueble = armarInmueble(rsInmueble);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return inmueble;
    }
    
 // Obtener lista de Inmuebles

    public List<Inmueble> getListaInmuebles() {
        List<Inmueble> inmuebles = new ArrayList<>();
        ResultSet rsInmuebles, rsPersona, rsUbicacion, rsContacto, rsOperaciones, rsGastos, rsImagenes;

        try {
            rsInmuebles = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_ALL_INMUEBLES);

            while (rsInmuebles.next()) {
                // Falta hacer esto
                Integer id = rsInmuebles.getInt(1);
                String tipo = rsInmuebles.getString(2);
                Date fecha = rsInmuebles.getDate(3);
                Integer idPropietario = rsInmuebles.getInt(4);
                String nomenclatura = rsInmuebles.getString(5);
                String estado = rsInmuebles.getString(6);
                Boolean cartelPublicado = rsInmuebles.getBoolean(7);
                String descripcion = rsInmuebles.getString(8);
                String condicion = rsInmuebles.getString(9);

                rsUbicacion = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_UBICACION+id);
                Ubicacion ubicacion = null;
                
                if (rsUbicacion.next()) {
                	String pais = rsUbicacion.getString(1);
                    String provincia = rsUbicacion.getString(2);
                    String ciudad = rsUbicacion.getString(3);
                    String barrio = rsUbicacion.getString(4);
                    String calle = rsUbicacion.getString(5);
                    Integer numero = rsUbicacion.getInt(6);
                    Integer piso = rsUbicacion.getInt(7);
                    String sDPTO = rsUbicacion.getString(8);
                    Character dpto = null;
                    if (sDPTO != null)
                        dpto = sDPTO.charAt(0);
                    Integer cp = rsUbicacion.getInt(9);
                    rsUbicacion.close();

                    ubicacion = new Ubicacion(pais, provincia, ciudad, barrio, calle, numero, piso, dpto, cp);
                }
                //rsUbicacion.next();
                

                // Agregar Propietario
                rsPersona = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_PERSONA_WID+idPropietario);
                rsPersona.next();
                String pNombre = rsPersona.getString(1);
                String pApellido = rsPersona.getString(2);
                BigInteger pDNI = BigInteger.valueOf((Long) rsPersona.getObject(3));
                String pDomicilio = rsPersona.getString(4);
                String pCiudad = rsPersona.getString(5);
                String pProvincia = rsPersona.getString(6);
                String pNacionalidad = rsPersona.getString(7);
                rsPersona.close();
                
                rsContacto = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_CONTACTO_PERSONA_WID+idPropietario);
                rsContacto.next();
                
                BigInteger pTelefono = null;
                if (rsContacto.getObject(1) != null) {
                	pTelefono = BigInteger.valueOf((Long) rsContacto.getObject(1));
                }
                
                BigInteger pCelular = null;
                if (rsContacto.getObject(2) != null) {
                	pCelular = BigInteger.valueOf((Long) rsContacto.getObject(2));
                }
                
                String pMail = null;
                if (rsContacto.getString(3) != null) {
                	pMail = rsContacto.getString(3);
                }
                
                rsContacto.close();

                Persona persona = new Persona(pNombre, pApellido, pDNI, pDomicilio, pCiudad, pProvincia, pNacionalidad);
                persona.setContacto(new ContactoPersona(pTelefono, pCelular, pMail));

                Inmueble inmueble = new Inmueble(tipo, fecha, ubicacion, persona);
                inmueble.setIdInmueble(id);
                inmueble.setEstado(estado);
                inmueble.setNomenclatura(nomenclatura);
                inmueble.setCondicion(condicion);
                inmueble.setCartelPublicado(cartelPublicado);
                inmueble.setDescripcion(descripcion);
                
                //Agregar operaciones
                rsOperaciones = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_OPERACIONES_BY_IDINMUEBLE+id);
                while (rsOperaciones.next()) {
                	Integer oIdOperacion = rsOperaciones.getInt(1);
                	Integer oTipoOperacion = rsOperaciones.getInt(2);
                	BigDecimal oPrecio = rsOperaciones.getBigDecimal(3);
                	String oMoneda = rsOperaciones.getString(4);
                	BigDecimal oComision = rsOperaciones.getBigDecimal(5);
                	String oObservacion = rsOperaciones.getString(6);
                	
                	inmueble.getOperaciones().add(new Operacion(oIdOperacion, oTipoOperacion.toString(), oPrecio, oMoneda, oComision, oObservacion));
                }
                
                // Agregar Gastos Fijos
                rsGastos = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOSFIJOS_BY_IDINMUEBLE+id);
                while (rsGastos.next()) {
                	Integer idGastoFijo = rsGastos.getInt(1);
                	Integer idTipoGasto = rsGastos.getInt(2);
                	BigDecimal gMonto = rsGastos.getBigDecimal(3);
                	String mesesAplica = rsGastos.getString(4);
                	Boolean gLiquidacion = rsGastos.getBoolean(5);
                	
                	GastoFijo gastoFijo = new GastoFijo(id, idGastoFijo, idTipoGasto.toString(), gMonto, gLiquidacion);
                	
                	List<Integer> listMesesAplica = new ArrayList<>();
                	for (int i=0; i < mesesAplica.length(); i++) {
                		Character ch = mesesAplica.charAt(i);
                		
                		if (Character.isDigit(ch))
                			listMesesAplica.add(Integer.valueOf(ch.toString()));
                	}
                	
                	gastoFijo.setMesesAplica(listMesesAplica);
                	
                	inmueble.getGastoFijos().add(gastoFijo);
                }
                
                // Agregar Imagenes
                rsImagenes = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_IMAGENES_BY_IDINMUEBLE+id);
                while (rsImagenes.next()) {
                	Integer iIdImagen = rsImagenes.getInt(1);
                	byte[] iFoto = rsImagenes.getBytes(2);
                	Boolean iPrincipal = rsImagenes.getBoolean(3);
                	String iTitulo = rsImagenes.getString(4);
                	
                	inmueble.getImagenes().add(new Imagen(iIdImagen, null, iPrincipal, iFoto, iTitulo));
                }
                
                inmuebles.add(inmueble);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return inmuebles;
    }
    
    

// Obtener String Array de Lista de Tipo_Operaciones
    
    public String[] getArrayTipoOperacion() {
        String[] arrayTipoOperacion = null;
        ResultSet rsTipoOperacion, rsTipoOperacionSize;

        try {
            //
            rsTipoOperacionSize = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SELECT_COUNT_TIPO_OPERACIONES);
            rsTipoOperacionSize.next();

            arrayTipoOperacion = new String[rsTipoOperacionSize.getInt("total")];

            rsTipoOperacion = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_OPERACIONES);

            int i = 0;
            while (rsTipoOperacion.next()) {
                arrayTipoOperacion[i] = rsTipoOperacion.getString("nombre");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayTipoOperacion;
    }
    
 // Obtener String Array de Lista de Tipo_Inmuebles
    
    public String[] getArrayTipoInmuebles() {
        String[] arrayTipoInmuebles = null;
        ResultSet rsTipoInmuebles, rsTipoInmuebleSize;

        try {
            rsTipoInmuebleSize = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SELECT_COUNT_TIPO_INMUEBLES);
            rsTipoInmuebleSize.next();

            arrayTipoInmuebles = new String[rsTipoInmuebleSize.getInt("total")];

            rsTipoInmuebles = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_INMUEBLES);
            int i = 0;

            while (rsTipoInmuebles.next()) {
                arrayTipoInmuebles[i] = rsTipoInmuebles.getString("tipo");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayTipoInmuebles;
    }
    
// Obtener String Array de Lista de Tipo_GastoFijo
    
    public String[] getArrayTipoGastoFijo() {
        String[] arrayTipoGastoFijo = null;
        ResultSet rsTipoGastoFijo, rsTipoGastoFijoSize;

        try {
        	rsTipoGastoFijoSize = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SELECT_COUNT_TIPO_GASTOFIJO);
        	rsTipoGastoFijoSize.next();

        	arrayTipoGastoFijo = new String[rsTipoGastoFijoSize.getInt("total")];

        	rsTipoGastoFijo = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_GASTOS_FIJOS);
            int i = 0;

            while (rsTipoGastoFijo.next()) {
            	arrayTipoGastoFijo[i] = rsTipoGastoFijo.getString("nombre");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayTipoGastoFijo;
    }
    
// Agregar un inmueble
    
    
    public Integer insertIntoInmueble (Inmueble inmueble) {
        try {
            ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_INMUEBLE_BY_TIPO+"'"+inmueble.getTipoInmueble()+"'");
            resultSet.next();
            int tipoID = resultSet.getInt(1);

            PreparedStatement pstmAddInmueble = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_INSERT_INMUEBLE);
                //Obligatorios
            pstmAddInmueble.setInt(1, tipoID);
            pstmAddInmueble.setDate(2, new java.sql.Date(inmueble.getFechaIngreso().getTime()));
            pstmAddInmueble.setInt(3, inmueble.getPropietario().getIdPersona());
                //No Obligatorios
            pstmAddInmueble.setString(4, inmueble.getNomenclatura());
            pstmAddInmueble.setString(5, inmueble.getEstado());
            if (inmueble.getCartelPublicado() == null)
            	pstmAddInmueble.setBoolean(6, false);
            else
            	pstmAddInmueble.setBoolean(6, inmueble.getCartelPublicado());
            pstmAddInmueble.setString(7, inmueble.getDescripcion());
            pstmAddInmueble.setString(8, inmueble.getCondicion());
            pstmAddInmueble.executeUpdate();

            setIdInmueble();

            insertIntoUbicacion(inmueble.getUbicacion());

            return this.idInmueble;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No se pudo agregar el inmueble a la BD");
        }

        return null;
    }

    private void setIdInmueble() {
        ResultSet resultSet;

        try {
            resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_GET_ID_MAX);
            if (resultSet.next())
                this.idInmueble = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("No se pudo agregar el idInmueble");
            e.printStackTrace();
        }
    }

    // Agregar ubicacion
    public void insertIntoUbicacion (Ubicacion ubicacion) {
        try {
            PreparedStatement pstmAddUbicacion = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_INSERT_UBICACION);
            pstmAddUbicacion.setString(1, ubicacion.getPais());
            pstmAddUbicacion.setString(2, ubicacion.getProvincia());
            pstmAddUbicacion.setString(3, ubicacion.getCiudad());
            pstmAddUbicacion.setString(4, ubicacion.getBarrio());

            pstmAddUbicacion.setString(5, ubicacion.getCalle());
            pstmAddUbicacion.setInt(6, ubicacion.getNumero());

            if (ubicacion.getPiso() == null)
                pstmAddUbicacion.setNull(7, Types.INTEGER);
            else
                pstmAddUbicacion.setInt(7, ubicacion.getPiso());

           if (ubicacion.getDpto() == null)
               pstmAddUbicacion.setNull(8, Types.CHAR);
           else
               pstmAddUbicacion.setString(8, ubicacion.getDpto().toString());

            pstmAddUbicacion.setInt(9, ubicacion.getCp());
            pstmAddUbicacion.setInt(10, this.idInmueble);
            pstmAddUbicacion.executeUpdate();
        } catch (SQLException e) {
            System.out.println("La ubicacion del inmueble no pudo ser agregada");
            e.printStackTrace();
        }
    }
    
    
    // Agregar una operacion
    public void insertIntoOperacion (Operacion operacion, int idInmueble) {
        try {
            ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_OPERACION_BY_TIPO+"'"+operacion.getTipoOperacion()+"'");
            resultSet.next();
            int tipoID = resultSet.getInt(1);

            PreparedStatement pstmAddOperacion = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_INSERT_OPERACION);
            pstmAddOperacion.setInt(1, tipoID);
            pstmAddOperacion.setBigDecimal(2, operacion.getPrecio());
            pstmAddOperacion.setString(3, operacion.getMoneda());
            pstmAddOperacion.setBigDecimal(4, operacion.getComision());
            pstmAddOperacion.setBigDecimal(5, operacion.getTotal());
            pstmAddOperacion.setString(6, operacion.getObservacion());
            pstmAddOperacion.setInt(7, idInmueble);
            pstmAddOperacion.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo agregar la operacion solicitada");
            e.printStackTrace();
        }
    }
    
    // Agregar un Gasto
    public void insertIntoGasto (GastoFijo gastoFijo, Integer idInmueble) {
        try {
            PreparedStatement pstmAddGasto = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_INSERT_GASTOFIJO);
            Integer tipoGasto = getIdTipoGastoFijo(gastoFijo.getGasto());
            pstmAddGasto.setInt(1,tipoGasto );
            pstmAddGasto.setBigDecimal(2, gastoFijo.getMonto());
            
            String mesesAplica = gastoFijo.getMesesAplica().toString().substring(1, 35);
            pstmAddGasto.setString(3, mesesAplica);
            pstmAddGasto.setBoolean(4, false);
            pstmAddGasto.setInt(5, idInmueble);
            pstmAddGasto.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No se pudo agregar la operacion solicitada");
            e.printStackTrace();
        }
    }
    
    public Integer getIdTipoGastoFijo (String nombre) {
    	
    	try {
			ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOFIJOID_BY_NOMBRE+"'"+nombre+"'");
			resultSet.next();
			
			return resultSet.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public String getNombreGastoFijoById (Integer id) {

    	try {
			ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOFIJO_NOMBRE_BY_ID+id);
			resultSet.next();
			
			return resultSet.getString("nombre");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    //SQL_SEARCH_TIPO_OPERACION_BY_ID
    
    public String getNombreTipoOperacionById (Integer id) {
    	
    	try {
			ResultSet resultSet = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_TIPO_OPERACION_BY_ID+id);
			resultSet.next();
			
			return resultSet.getString("nombre");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    //Agregar Imagenes
    public void insertIntoImagen (Imagen imagen, Integer idInmueble) {
        //INSERT INTO Imagen (foto, principal, titulo, idInmueble)
        try {
            PreparedStatement pstmAddImagen = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_INSERT_IMAGEN);
            pstmAddImagen.setBlob(1, new SerialBlob(imagen.getImage()));
            pstmAddImagen.setBoolean(2, imagen.getPrincipal());
            pstmAddImagen.setString(3, imagen.getTitulo());
            pstmAddImagen.setInt(4, idInmueble);
            pstmAddImagen.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /***************
     *** UPDATES ***
     ***************
     */
    
    public void updateInmueble (Inmueble inm) {
    	try {

			PreparedStatement pstm = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_UPDATE_INMUEBLE+inm.getIdInmueble());
			pstm.setString(1, inm.getNomenclatura());
			if (inm.getCartelPublicado() == null)
				pstm.setBoolean(2, false);
			else
				pstm.setBoolean(2, inm.getCartelPublicado());
			pstm.setString(3, inm.getDescripcion());
			pstm.setString(4, inm.getCondicion());
			
			pstm.executeUpdate();
			
			//UPDATE Ubicacion_Inmueble SET pais=?, provincia=?, ciudad=?, barrio=?, calle=?, numero=?, piso=?, dpto=?, cp=?";
			Ubicacion ubicacion = inm.getUbicacion();
			PreparedStatement pstmUbicacion = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_UPDATE_INMUEBLE_UBICACION+inm.getIdInmueble());
			pstmUbicacion.setString(1, ubicacion.getPais());
			pstmUbicacion.setString(2, ubicacion.getProvincia());
			pstmUbicacion.setString(3, ubicacion.getCiudad());
			pstmUbicacion.setString(4, ubicacion.getBarrio());
			pstmUbicacion.setString(5, ubicacion.getCalle());
			pstmUbicacion.setInt(6, ubicacion.getNumero());
			
			if (ubicacion.getPiso() == null)
				pstmUbicacion.setNull(7, Types.INTEGER);
			else
				pstmUbicacion.setInt(7, ubicacion.getPiso());
			
			if (ubicacion.getDpto() == null)
				pstmUbicacion.setNull(8, Types.CHAR);
			else
				pstmUbicacion.setString(8, ubicacion.getDpto().toString());
			
			pstmUbicacion.setInt(9, ubicacion.getCp());
			
			pstmUbicacion.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void updateInmueble_Operacion(Operacion operacion) {
    	try {
    		
    		//UPDATE Operacion SET precio=?, moneda=?, comision=?, total=?, observacion=? WHERE idOperacion=
			PreparedStatement pstm = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_UPDATE_INMUEBLE_OPERACION+operacion.getIdOperacion());
			pstm.setBigDecimal(1, operacion.getPrecio());
			pstm.setString(2, operacion.getMoneda());
			pstm.setBigDecimal(3, operacion.getComision());
			pstm.setBigDecimal(4, operacion.getTotal());
			pstm.setString(5, operacion.getObservacion());
			
			pstm.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void updateInmueble_Estado(String estado, Integer id) {
    	PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_UPDATE_INMUEBLE_ESTADO);
			pstm.setString(1, estado);
	    	pstm.setInt(2, id);
	    	
	    	pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    /***************
     *** DELETES ***
     ***************
     */
    
    public void deleteInmuebleById (Integer id) {
    	try {
			PreparedStatement pstmDelete = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_DELETE_INMUEBLE_BY_ID);
			pstmDelete.setInt(1, id);
			pstmDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void deleteInmueble_Ubicacion(Integer id) {
    	try {
			PreparedStatement pstmDelete = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_DELETE_UBICACION_BY_IDINMUEBLE);
			pstmDelete.setInt(1, id);
			pstmDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void deleteInmueble_Operacion (Integer idInm) {
    	
    	try {
			PreparedStatement pstmDelete = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_DELETE_OPERACION_BY_IDINMUEBLE);
			pstmDelete.setInt(1, idInm);
			pstmDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void deleteInmueble_Gasto (Integer idInm) {
    	
    	try {
			PreparedStatement pstmDelete = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_DELETE_GASTOS_BY_IDINMUEBLE);
			pstmDelete.setInt(1, idInm);
			pstmDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deleteInmueble_Imagenes (Integer id) {
    	try {
			PreparedStatement pstmDelete = conexion.prepareStatement(ConstantSQL_Inmueble.SQL_DELETE_IMAGENES_BY_IDINMUEBLE);
			pstmDelete.setInt(1, id);
			pstmDelete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    /*********************
     ***** OTROS *********
     ******************/
    
    public Inmueble armarInmueble (ResultSet rsInmuebles) {
    	ResultSet rsPersona, rsUbicacion, rsContacto, rsOperaciones, rsGastos, rsImagenes;
    	Inmueble inmueble = null;
    	
    	 try {
    	 while (rsInmuebles.next()) {
             // Falta hacer esto
             Integer id = rsInmuebles.getInt(1);
             String tipo = rsInmuebles.getString(2);
             Date fecha = rsInmuebles.getDate(3);
             Integer idPropietario = rsInmuebles.getInt(4);
             String nomenclatura = rsInmuebles.getString(5);
             String estado = rsInmuebles.getString(6);
             Boolean cartelPublicado = rsInmuebles.getBoolean(7);
             String descripcion = rsInmuebles.getString(8);
             String condicion = rsInmuebles.getString(9);

             rsUbicacion = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_UBICACION+id);
             Ubicacion ubicacion = null;
             
             if (rsUbicacion.next()) {
             	String pais = rsUbicacion.getString(1);
                 String provincia = rsUbicacion.getString(2);
                 String ciudad = rsUbicacion.getString(3);
                 String barrio = rsUbicacion.getString(4);
                 String calle = rsUbicacion.getString(5);
                 Integer numero = rsUbicacion.getInt(6);
                 Integer piso = rsUbicacion.getInt(7);
                 String sDPTO = rsUbicacion.getString(8);
                 Character dpto = null;
                 if (sDPTO != null)
                     dpto = sDPTO.charAt(0);
                 Integer cp = rsUbicacion.getInt(9);
                 rsUbicacion.close();

                 ubicacion = new Ubicacion(pais, provincia, ciudad, barrio, calle, numero, piso, dpto, cp);
             }
             //rsUbicacion.next();
             

             // Agregar Propietario
             rsPersona = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_PERSONA_WID+idPropietario);
             rsPersona.next();
             String pNombre = rsPersona.getString(1);
             String pApellido = rsPersona.getString(2);
             BigInteger pDNI = BigInteger.valueOf((Long) rsPersona.getObject(3));
             String pDomicilio = rsPersona.getString(4);
             String pCiudad = rsPersona.getString(5);
             String pProvincia = rsPersona.getString(6);
             String pNacionalidad = rsPersona.getString(7);
             rsPersona.close();
             
             rsContacto = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Persona.SQL_SEARCH_CONTACTO_PERSONA_WID+idPropietario);
             rsContacto.next();
             
             BigInteger pTelefono = null;
             if (rsContacto.getObject(1) != null) {
             	pTelefono = BigInteger.valueOf((Long) rsContacto.getObject(1));
             }
             
             BigInteger pCelular = null;
             if (rsContacto.getObject(2) != null) {
             	pCelular = BigInteger.valueOf((Long) rsContacto.getObject(2));
             }
             
             String pMail = null;
             if (rsContacto.getString(3) != null) {
             	pMail = rsContacto.getString(3);
             }
             
             rsContacto.close();

             Persona persona = new Persona(pNombre, pApellido, pDNI, pDomicilio, pCiudad, pProvincia, pNacionalidad);
             persona.setContacto(new ContactoPersona(pTelefono, pCelular, pMail));

             inmueble = new Inmueble(tipo, fecha, ubicacion, persona);
             inmueble.setIdInmueble(id);
             inmueble.setEstado(estado);
             inmueble.setNomenclatura(nomenclatura);
             inmueble.setCondicion(condicion);
             inmueble.setCartelPublicado(cartelPublicado);
             inmueble.setDescripcion(descripcion);
             
             //Agregar operaciones
             rsOperaciones = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_OPERACIONES_BY_IDINMUEBLE+id);
             while (rsOperaciones.next()) {
             	Integer oIdOperacion = rsOperaciones.getInt(1);
             	Integer oTipoOperacion = rsOperaciones.getInt(2);
             	BigDecimal oPrecio = rsOperaciones.getBigDecimal(3);
             	String oMoneda = rsOperaciones.getString(4);
             	BigDecimal oComision = rsOperaciones.getBigDecimal(5);
             	String oObservacion = rsOperaciones.getString(6);
             	
             	inmueble.getOperaciones().add(new Operacion(oIdOperacion, oTipoOperacion.toString(), oPrecio, oMoneda, oComision, oObservacion));
             }
             
             // Agregar Gastos Fijos
             rsGastos = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOSFIJOS_BY_IDINMUEBLE+id);
             while (rsGastos.next()) {
             	Integer idGastoFijo = rsGastos.getInt(1);
             	Integer idTipoGasto = rsGastos.getInt(2);
             	BigDecimal gMonto = rsGastos.getBigDecimal(3);
             	String mesesAplica = rsGastos.getString(4);
             	Boolean gLiquidacion = rsGastos.getBoolean(5);
             	
             	GastoFijo gastoFijo = new GastoFijo(id, idGastoFijo, idTipoGasto.toString(), gMonto, gLiquidacion);
             	
             	List<Integer> listMesesAplica = new ArrayList<>();
             	for (int i=0; i < mesesAplica.length(); i++) {
             		Character ch = mesesAplica.charAt(i);
             		
             		if (Character.isDigit(ch))
             			listMesesAplica.add(Integer.valueOf(ch.toString()));
             	}
             	
             	gastoFijo.setMesesAplica(listMesesAplica);
             	
             	inmueble.getGastoFijos().add(gastoFijo);
             }
             
             // Agregar Imagenes
             rsImagenes = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_IMAGENES_BY_IDINMUEBLE+id);
             while (rsImagenes.next()) {
             	Integer iIdImagen = rsImagenes.getInt(1);
             	byte[] iFoto = rsImagenes.getBytes(2);
             	Boolean iPrincipal = rsImagenes.getBoolean(3);
             	String iTitulo = rsImagenes.getString(4);
             	
             	inmueble.getImagenes().add(new Imagen(iIdImagen, null, iPrincipal, iFoto, iTitulo));
             }
             
             //inmuebles.add(inmueble);
         }
    	 }
    	 catch (SQLException e) {
    		 e.printStackTrace();
    	 }
    	 
    	 return inmueble;
    }
    
    
    // Trae todas las Localidades registradas en Ubicacion
    
    public String[] getLocalidadesByIdUbicacion() {
    	String[] arrayLocalidades = null;
    	ResultSet rsLocalidades, rsLocalidadesSize;
    	
    	try {
			rsLocalidadesSize = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SELECT_COUNT_LOCALIDADES);
			rsLocalidadesSize.next();
			
			arrayLocalidades = new String[rsLocalidadesSize.getInt(1)+1];
			
			rsLocalidades = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SELECT_CIUDADES);
			int i = 1;
			arrayLocalidades[0] = "TODAS";
			while (rsLocalidades.next()) {
				arrayLocalidades[i] = rsLocalidades.getString(1);
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return arrayLocalidades;
    }
    
    public List<GastoFijo> getListGastoFijoByIdInmueble (Integer idInmueble) {
    	List<GastoFijo> listGastoFijo = new ArrayList<>();
    	ResultSet rsGastos;
		
    	try {
			rsGastos = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOSFIJOS_BY_IDINMUEBLE+idInmueble);
			 while (rsGastos.next()) {
	         	Integer idGastoFijo = rsGastos.getInt(1);
	         	Integer idTipoGasto = rsGastos.getInt(2);
	         	BigDecimal gMonto = rsGastos.getBigDecimal(3);
	         	String mesesAplica = rsGastos.getString(4);
	         	Boolean gLiquidacion = rsGastos.getBoolean(5);
	         	
	         	GastoFijo gastoFijo = new GastoFijo(idInmueble, idGastoFijo, idTipoGasto.toString(), gMonto, gLiquidacion);
	         	
	         	List<Integer> listMesesAplica = new ArrayList<>();
	         	String[] mesesAplicaSplit = mesesAplica.split(",");
	         	
	         	for (int i = 0; i < mesesAplicaSplit.length; i++) {
	         		if (mesesAplicaSplit[i].length() > 1) {
	         			mesesAplicaSplit[i] = Character.toString(mesesAplicaSplit[i].charAt(mesesAplicaSplit[i].length()-1));
	         		}
					listMesesAplica.add(Integer.decode(mesesAplicaSplit[i]));
				}
	         	gastoFijo.setMesesAplica(listMesesAplica);
	         	
	         	ResultSet rsTipoGastoNombre = ((Statement) conexion.createStatement()).executeQuery(ConstantSQL_Inmueble.SQL_SEARCH_GASTOFIJO_NOMBRE_BY_ID+idTipoGasto);
	         	rsTipoGastoNombre.next();
				
				gastoFijo.setGasto(rsTipoGastoNombre.getString("nombre"));
	         	
	         	listGastoFijo.add(gastoFijo);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         
         return listGastoFijo;
    }
    
}
