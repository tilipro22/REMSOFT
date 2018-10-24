package gui;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import app.alquileres.PanelAlquiler;
import app.inmuebles.CtrlInmueble;
import app.ventas.PanelVenta;
import gui.tabla.ModeloTabla;
import logica.Inmueble;

public class FrameTablaInmuebles extends FrameDefault {

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private List<Inmueble> listInmuebles;
	private Inmueble inmuebleSelected = null;
	private JPanel panelTabla, panelFooter;
	private CtrlInmueble ctrlInmueble=new CtrlInmueble();

	/* Panel Table */
	private JTable tabla;
	private ModeloTabla modeloTabla;
	private int filaSeleccionada = -1;
	
	public FrameTablaInmuebles() {
		setTituloBarra(" REMSOFT | Lista de Inmuebles");
		setSizeFrame(600, 900);
		setLocationRelativeTo(null);
		
		initPanelTabla();
		initPanelFooter();
	}
	
	/* Inits */

	private void initPanelTabla() {
		this.panelTabla = new JPanel();
		panelTabla.setBackground(ConfigGUI.COLOR_ERROR);
		panelTabla.setBounds(25, 50, getWidth()-50, getHeight()-125);
		panelTabla.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(new Rectangle(0, 0, panelTabla.getWidth(), panelTabla.getHeight()));
		inicializarTabla();
		cargarTabla();
		scrollPane.setViewportView(tabla);
		
		panelTabla.add(scrollPane);
		getContentPane().add(panelTabla);
	}
	
	private void inicializarTabla() {
		String[] columnas = {"Tipo", "Ubicacion", "Direccion", "Propietario"};
		
		modeloTabla = new ModeloTabla(columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Inmueble inmueble = (Inmueble) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					Integer id = Integer.decode(inmueble.getTipoInmueble());
					String tipoInmueble = ctrlInmueble.getTipoInmueble(id);
					return tipoInmueble;
					
				case 1:
					return inmueble.getUbicacion().getPais() + ", " + inmueble.getUbicacion().getProvincia() + " " + inmueble.getUbicacion().getCiudad(); 
				case 2:
					return inmueble.getUbicacion().getCalle() + " " + inmueble.getUbicacion().getNumero();
					
				case 3:
					return inmueble.getPropietario().getApellido() + ", " + inmueble.getPropietario().getNombre();
					
				default:
					return inmueble;
				}
			}
		};
		
		tabla = new JTable(modeloTabla);
		modeloTabla.setConfigTable(tabla);
	}
	
	private void cargarTabla() {
		listInmuebles = ctrlInmueble.getListaInmuebles();
		
		if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
			for (Inmueble inmueble : listInmuebles) {
				
				for (int i=0; i < inmueble.getOperaciones().size(); i++) {
					if(inmueble.getOperaciones().get(i).getTipoOperacion().equals("1") 
							|| inmueble.getOperaciones().get(i).getTipoOperacion().equals("3")) {
						
						if (inmueble.getEstado()!=null && inmueble.getEstado().equals("DISPONIBLE"))
							modeloTabla.agregar(inmueble);
					}
						
				}
			}
		}
		else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
			for (Inmueble inmueble : listInmuebles) {
				for (int i=0; i < inmueble.getOperaciones().size(); i++) {
					if(inmueble.getOperaciones().get(i).getTipoOperacion().equals("2") 
							&& inmueble.getEstado().equals("DISPONIBLE")) {
						modeloTabla.agregar(inmueble);
					}
						
				}
			}
		}
		else {
			for (Inmueble inmueble : listInmuebles)
				modeloTabla.agregar(inmueble);
		}
		
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setBorder(new LineBorder(Color.WHITE));
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		panelFooter.setBounds(25, 50+panelTabla.getHeight(), getWidth()-50, getHeight()-panelTabla.getHeight()-60);
		
		ButtonDefaultABM btnCancelar=new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventCancelar();
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);

		ButtonDefaultABM btnAceptar=new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inmuebleSelected = eventAceptar();
			}
		});
		btnAceptar.setText("Aceptar");
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnAceptar);
		getContentPane().add(panelFooter);
	}
	
	
	/* Events */
	
	@Override
	protected void eventoCerrar() {
		if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
			eventoCerrar_FrameAlquiler();
		}
		else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
			eventoCerra_FrameVenta();
		}
	}
	
	private void eventCancelar() {
		if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
			eventoCerrar_FrameAlquiler();
		}
		else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
			eventoCerra_FrameVenta();
		}
	}
	
	private Inmueble eventAceptar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			
			this.inmuebleSelected = ((Inmueble) modeloTabla.getObjetos().get(filaSeleccionada));
			
			if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
				((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setDatosLocador(inmuebleSelected);
				eventoCerrar_FrameAlquiler();
			}
			
			if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setInmueble(inmuebleSelected);
				eventoCerra_FrameVenta();
			}
			
			return inmuebleSelected;
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
			return null;
		}
		
	}
	
	private void eventoCerrar_FrameAlquiler() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setEnabled(true);
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setVisible(true);
		
	}
	
	private void eventoCerra_FrameVenta() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setEnabled(true);
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setVisible(true);
	}

}
