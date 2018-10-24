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
import app.solicitudes.PanelSolicitudes;
import app.ventas.FrameVenta;
import app.ventas.PanelVenta;
import control.CtrlAddPersona;
import gui.tabla.ModeloTabla;
import logica.Persona;

public class FrameTablaPersonas extends FrameDefault {

	private static final long serialVersionUID = 1L;

	/* Elements */
	private List<Persona> listPersonas;
	private Persona personaSelect = null;
	private JPanel panelTabla, panelFooter;
	private CtrlAddPersona ctrlAddPersona=new CtrlAddPersona();

	
	/* Panel Tabla */
	private ModeloTabla modeloTabla;
	private JTable tabla;
	
	/* Panel Footer */
	private int filaSeleccionada;

	public FrameTablaPersonas() {
		setTituloBarra(" REMSOFT | Lista de Personas");
		setSizeFrame(600, 900);
		setLocationRelativeTo(null);
		
		initPanelTabla();
		initPanelFooter();
	}
	
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
		String[] columnas = {"Nombre", "Domicilio", "Ciudad", "Provincia"};
		
		modeloTabla = new ModeloTabla(columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Persona p = (Persona) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					return p.getApellido() + ", "+p.getNombre();
					
				case 1:
					return p.getDomicilio();
					
				case 2:
					return p.getCiudad();
					
				case 3:
					return p.getProvincia();
					
				default:
					return p;
				}
			}
		};
		
		tabla = new JTable(modeloTabla);
		modeloTabla.setConfigTable(tabla);
	}
	
	private void cargarTabla() {
		listPersonas = ctrlAddPersona.getListaPersonas();
		
		for (Persona persona : listPersonas) {
			modeloTabla.agregar(persona);
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
				personaSelect = eventAceptar();
			}
		});
		btnAceptar.setText("Aceptar");
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnAceptar);
		getContentPane().add(panelFooter);
	}
	
	
	/* Events */
	
	private Persona eventAceptar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			
			this.personaSelect = ((Persona) modeloTabla.getObjetos().get(filaSeleccionada));
			
			if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
				
				if (((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().isInquilino()) {
					((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setDatosInquilino(personaSelect);
					((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setInquilino(false);
				}
				else {
					((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().getPanelGarantiaAlq().setDatosGarante(personaSelect);
				}
				
				eventoCerrar_FrameAlquiler();
				
			}
			else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
				
				switch ( ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getSearch_actual() ) {
				case (FrameVenta.SEARCH_COMPRADOR):
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setComprador(personaSelect);
					break;
					
				case (FrameVenta.SEARCH_CONYUGE):
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setConyuge(personaSelect);
					break;
					
				case (FrameVenta.SEARCH_ESCRIBANO):
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setEscribano(personaSelect);
					break;
					
				case (FrameVenta.SEARCH_MARTILLERO):
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setMartillero(personaSelect);
					break;
				}
				
				eventoCerrar_FrameVenta();
			}
			else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_SOLICITUDES) {
				((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getFrameSolicitud().setDatosSolicitante(personaSelect);
				eventoCerrar_FrameSolicitud();
			}
			
			return personaSelect;
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
			return null;
		}
	}
	
	private void eventCancelar() {
		eventoCerrar();
	}

	@Override
	protected void eventoCerrar() {
		this.dispose();
		
		if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_ALQUILERES) {
			eventoCerrar_FrameAlquiler();
		}
		else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_VENTAS) {
			eventoCerrar_FrameVenta();
		}
		else if (FramePpal.getFrameppal().getPanelActual() == FramePpal.PANEL_RESERVAS) {
			eventoCerrar_FrameVenta();
		}
		
	}
	
	private void eventoCerrar_FrameAlquiler() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setEnabled(true);
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setVisible(true);
		
	}
	
	private void eventoCerrar_FrameVenta() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setEnabled(true);
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().setVisible(true);
	}
	
	private void eventoCerrar_FrameSolicitud() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getFrameSolicitud().setEnabled(true);
		((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getFrameSolicitud().setVisible(true);
	}
}
