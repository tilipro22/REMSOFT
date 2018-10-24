package app.solicitudes;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import gui.FramePpal;
import gui.paneles.PanelDefault;
import gui.tabla.ModeloTabla;
import logica.Solicitud;
import util.Utils;

public class PanelSolicitudes extends PanelDefault{

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private FrameSolicitud frameSolicitud;
	
	public PanelSolicitudes() {
		lblTitle.setText("Solicitudes");
		lblTitle.setVisible(true);
		inicializarModeloTabla();
		
		add(scrollPane);
	}

	@Override
	protected void inicializarModeloTabla() {
		// Fecha, Solicitante, Celular, Otros Telefonos, Inmueble, Provincia, Localidad, Operacion, Precio Desde, Precio Hasta, Moneda, Atendido por
		String[] columns = {"Fecha", "Solicitante", "Contacto", "Inmueble", "Localidad", "Provincia", "Operacion", "$ Desde", "$ Hasta", "Moneda"};
		
		modeloTabla = new ModeloTabla(columns) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Solicitud solicitud = (Solicitud) modeloTabla.getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					return Utils.printFecha(solicitud.getFecha());
					
				case 1:
					return solicitud.getSolicitante().getApellido() + ", " + solicitud.getSolicitante().getNombre();
					
				case 2:
					return solicitud.getSolicitante().getContacto().getCelular();
					
				case 3:
					return solicitud.getTipoInmueble();
					
				case 4:
					return solicitud.getCiudad();
					
				case 5:
					solicitud.getProvincia();
				}
				return null;
			}
		};
		
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setViewportView(tabla);
	}

	/* Events */
	@Override
	protected void eventoAgregar() {
		FramePpal.getFrameppal().setEnabled(false);
		frameSolicitud = new FrameSolicitud(false);
		frameSolicitud.setVisible(true);
	}

	@Override
	protected void eventoModificar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void eventoEliminar() {
		// TODO Auto-generated method stub
		
	}

	
	/* Getters & Setters */ 
	
	public FrameSolicitud getFrameSolicitud() {
		return frameSolicitud;
	}

	
	public void setFrameSolicitud(FrameSolicitud frameSolicitud) {
		this.frameSolicitud = frameSolicitud;
	}

	public ModeloTabla getModeloTabla () {
		return modeloTabla;
	}
	
	
}
