package app.inmuebles;



import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.FramePpal;
import gui.paneles.PanelDefault;
import gui.tabla.ModeloTabla;
import logica.Inmueble;
import logica.Persona;
import logica.Ubicacion;


public class PanelInmuebles extends PanelDefault {

	/**
	 * Elements
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Inmueble> inmuebles = new ArrayList<>();
	private int filaSeleccionada = -1;
	
/* Constructor */

	public PanelInmuebles() {
		
		
		lblTitle.setText("Inmuebles");
		lblTitle.setVisible(true);
		
		inicializarModeloTabla();
		add(scrollPane);
		
		cargarInmuebles();

	}
	
/* Functions */
	
	private void cargarInmuebles() {
		CtrlInmueble ctrlInmueble=new CtrlInmueble();
		inmuebles = ctrlInmueble.getListaInmuebles();
		

		for (Inmueble inmueble : inmuebles) {
			modeloTabla.agregar(inmueble);
		}
		
	}
	
	public void recargarInmuebles() {
		inmuebles = null;
		
		int size = modeloTabla.getObjetos().size();
		for (int i=0; i < size; i++) {
			modeloTabla.eliminar(0);
		}
		
		cargarInmuebles();
	}
	
	
	@Override
	public void inicializarModeloTabla() {
		String[] columnas = {"Tipo", "Ubicacion", "Domicilio", "Propietario"};
		super.modeloTabla = new ModeloTabla(columnas) {
			

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Inmueble inmueble = (Inmueble) getObjetos().get(fila);
				Ubicacion u = inmueble.getUbicacion();
				
				switch (columna) {
				case 0:
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					try {
						Integer id = Integer.decode(inmueble.getTipoInmueble());
						return ctrlInmueble.getTipoInmueble(id);
					}
					catch (NumberFormatException e) {
						return inmueble.getTipoInmueble();
					}
					
				case 1:
					if (u != null)
						return u.getCiudad()+", "+u.getProvincia()+", "+u.getPais();
					else
						return "vacio";
					
				case 2:
					if (u != null)
						return u.getCalle()+" "+u.getNumero();
					else
						return "";
				case 3:
					Persona p = inmueble.getPropietario();
					return p.getNombre()+" "+p.getApellido();
					
				default:
					return inmueble;
				}
				
			}
		};
		
		tabla = new JTable();
		tabla.setModel(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane.setViewportView(tabla);
	}

/* Events */
	
	@Override
	protected void eventoAgregar() {
		FramePpal.getFrameppal().setEnabled(false);
		FrameInmueble frameInmueble=new FrameInmueble(false);
		frameInmueble.setVisible(true);
	}

	@Override
	protected void eventoModificar() {
		filaSeleccionada = tabla.getSelectedRow();
		if (filaSeleccionada != -1) {
			FramePpal.getFrameppal().setEnabled(false);
			FrameInmueble frameInmueble=new FrameInmueble(true);
			frameInmueble.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
		
	}

	@Override
	protected void eventoEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {

			if (inmuebles.get(filaSeleccionada).getEstado().equals("DISPONIBLE")) {
				int option = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el Inmueble seleccionado?");
				
				if (option == 0) {
					Inmueble inmueble = (Inmueble) modeloTabla.getObjetos().get(filaSeleccionada);
					modeloTabla.eliminar(filaSeleccionada);
					inmuebles.remove(filaSeleccionada);
					
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					
					if (inmueble.getGastoFijos() != null && inmueble.getGastoFijos().size() > 0) {
						ctrlInmueble.deleteInmueble_Gasto(inmueble.getIdInmueble());
					}
					
					if (inmueble.getOperaciones() != null && inmueble.getOperaciones().size() > 0) {
						ctrlInmueble.deleteInmueble_Operacion(inmueble.getIdInmueble());
					}
					
					if (inmueble.getImagenes() != null && inmueble.getImagenes().size() > 0) {
						ctrlInmueble.deleteInmueble_Imagenes(inmueble.getIdInmueble());
					}
					
					ctrlInmueble.deleteInmueble_Ubicacion(inmueble.getIdInmueble());
					ctrlInmueble.deleteInmuebleById(inmueble.getIdInmueble());
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "No se puede eliminar el Inmueble seleccionado.\n "
						+ "Estado Inmueble: [" + inmuebles.get(filaSeleccionada).getEstado() + "].");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Inmueble para Eliminar.");
		}
		
	}
	
	
/* Getters & Setters */
	public Inmueble getInmuebleActual() {
		Inmueble inmueble = (Inmueble) modeloTabla.getObjetos().get(filaSeleccionada);
		return inmueble;
	}
	
	public ModeloTabla getModeloTabla() {
		return super.modeloTabla;
	}

	public List<Inmueble> getInmuebles() {
		return inmuebles;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}
	
	

}
