package app.solicitudes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Inmueble;
import logica.Ubicacion;

public class FrameCoincidencias extends FrameDefault {


	private static final long serialVersionUID = 1L;
	
	private JPanel panelTable, panelFooter;
	private List<Inmueble> inmuebles;
	private int filaSeleccionada = -1;
	
	/* Panel Tabla */
	private final String columns[] = {"Tipo", "Direccion", "Ciudad", "Provincia", "Estado"};
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private JScrollPane scrollPane;
	
	/* Panel Footer */
	private ButtonDefaultABM btnCancelar, btnSeleccionar;
	
	public FrameCoincidencias(List<Inmueble> inmuebles) {
		setTituloBarra(" REMSOFT | Inmuebles con Coincidencias");
		setColorBackground(ConfigGUI.COLOR_FONDO_ALT);
		setSizeFrame(800, 700);
		setLocationRelativeTo(null);
		this.inmuebles = inmuebles;
		
		initPanelTable();
		initPanelFooter();
	}
	
	/* Inits */
	
	private void initPanelTable() {
		panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.setBounds(10, 35, getWidth()-20, getHeight()-105);
		
		modeloTabla = new ModeloTabla(columns) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {

			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Inmueble inmueble = (Inmueble) modeloTabla.getObjetos().get(fila);
				Ubicacion ubicacion = inmueble.getUbicacion();
				
				switch (columna) {
				case 0:
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					return ctrlInmueble.getTipoInmueble(Integer.decode(inmueble.getTipoInmueble()));
					
				case 1:
					return ubicacion.getCalle() + " " + ubicacion.getNumero();
					
				case 2:
					return ubicacion.getCiudad();
					
				case 3:
					return ubicacion.getProvincia();
					
				case 4:
					return inmueble.getEstado();
				}
				
				return null;
			}
		};
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setViewportView(tabla);
		
		cargarDatos();
		
		panelTable.add(scrollPane, BorderLayout.CENTER);
		add(panelTable);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, Color.white));
		panelFooter.setBounds(10, panelTable.getHeight()+panelTable.getY()+10, getWidth()-20, 50);
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCerrar();
			}
		});
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");
		
		btnSeleccionar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnSeleccionar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoSeleccionar();
			}
		});
		btnSeleccionar.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		btnSeleccionar.setText("Seleccionar");
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnSeleccionar);
		add(panelFooter);
	}
	
	/* Configs */
	private void setConfigTable() {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	private void setConfigScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setPreferredSize(new Dimension(panelTable.getWidth(), panelTable.getHeight()));
	}
	
	
	/* Functions */
	
	private void cargarDatos() {
		for (Inmueble inmueble : inmuebles) {
			modeloTabla.agregar(inmueble);
		}
	}

	/* Events */
	
	@Override
	protected void eventoCerrar() {
		dispose();
		((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getFrameSolicitud().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getFrameSolicitud().setVisible(true);
	}

	
	private void eventoSeleccionar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			Inmueble inmueble = inmuebles.get(filaSeleccionada);
			((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES))
				.getFrameSolicitud().setInmueble(inmueble);
			
			eventoCerrar();
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Inmueble.");
		}
	}
	
}
