package app.solicitudes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.inmuebles.CtrlInmueble;
import logica.ConexionBD_msSQL;
import logica.Inmueble;
import logica.constant.ConstantSQL_Solicitud;

public class CtrlSolicitudes {
	
	//private Integer idAlquiler;
		private Connection conexion;
		
		/* Constructors */
		public CtrlSolicitudes() {
			this.conexion = ConexionBD_msSQL.getConexionMSSQL().getConexion();
		}
		
		/* UNA ALTERNATIVA */
		public List<Inmueble> getBasico(Integer alt1) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA);
				
				ps.setInt(1, alt1);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithProvincia(Integer alt1, String provincia) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA);
				
				ps.setInt(1, alt1);
				ps.setString(2, provincia);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithCiudad(Integer alt1, String ciudad) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA+ConstantSQL_Solicitud.SQL_ADD_CIUDAD);
				
				ps.setInt(1, alt1);
				ps.setString(2, ciudad);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithOperacion(Integer alt1, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setInt(2, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBsicoWithPronviciaAndCiudad(Integer alt1, String provincia, String ciudad) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_CIUDAD);
				
				ps.setInt(1, alt1);
				ps.setString(2, provincia);
				ps.setString(3, ciudad);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithPronviciaAndOperacion(Integer alt1, String provincia, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setString(2, provincia);
				ps.setInt(3, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithCiudadAndOperacion(Integer alt1, String ciudad, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA
						+ConstantSQL_Solicitud.SQL_ADD_CIUDAD+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setString(2, ciudad);
				ps.setInt(3, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getBasicoWithAll(Integer alt1, String provincia, String ciudad, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_BASICA
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_CIUDAD+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setString(2, provincia);
				ps.setString(3, ciudad);
				ps.setInt(4, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		
		/* DOS ALTERNATIVAS */
		public List<Inmueble> getDosAlt(Integer alt1, Integer alt2) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithProvincia(Integer alt1, Integer alt2, String provincia) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, provincia);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithCiudad(Integer alt1, Integer alt2, String ciudad) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS+ConstantSQL_Solicitud.SQL_ADD_CIUDAD);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, ciudad);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithOperacion(Integer alt1, Integer alt2, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setInt(3, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithProvinciaAndCiudad(Integer alt1, Integer alt2, String provincia, String ciudad) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_CIUDAD);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, provincia);
				ps.setString(4, ciudad);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithProvinciaAndOperacion(Integer alt1, Integer alt2, String provincia, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, provincia);
				ps.setInt(4, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithCiudadAndOperacion(Integer alt1, Integer alt2, String ciudad, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS
						+ConstantSQL_Solicitud.SQL_ADD_CIUDAD+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, ciudad);
				ps.setInt(4, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public List<Inmueble> getDosAltWithAll(Integer alt1, Integer alt2, String provincia, String ciudad, Integer operacion) {
			List<Inmueble> listInmuebles = new ArrayList<>();
			try {
				PreparedStatement ps = conexion.prepareStatement(ConstantSQL_Solicitud.SQL_CONSULTA_CON_DOS_ALTERNATIVAS
						+ConstantSQL_Solicitud.SQL_ADD_PROVINCIA+ConstantSQL_Solicitud.SQL_ADD_CIUDAD+ConstantSQL_Solicitud.SQL_ADD_OPERACION);
				
				ps.setInt(1, alt1);
				ps.setInt(2, alt2);
				ps.setString(3, provincia);
				ps.setString(4, ciudad);
				ps.setInt(5, operacion);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					Integer idInmueble = rs.getInt("idInmueble");
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					Inmueble inmueble = ctrlInmueble.getInmuebleById(idInmueble);
					listInmuebles.add(inmueble);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return listInmuebles;
		}
		
		public static void main(String[] args) {
			CtrlSolicitudes ctrlSolicitudes = new CtrlSolicitudes();
			//ctrlSolicitudes.prueba();
			List<Inmueble> inmuebles = ctrlSolicitudes.getBasicoWithAll(2, "Buenos Aires", "Lujan", 1);
			
			for (Inmueble inmueble : inmuebles) {
				System.out.println(inmueble.getIdInmueble());
			}
		}

}
