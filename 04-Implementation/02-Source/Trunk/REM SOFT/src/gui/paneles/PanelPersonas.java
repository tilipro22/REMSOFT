package gui.paneles;

import javax.swing.JPanel;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameAddPersona;
import gui.FramePpal;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Persona;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import control.CtrlAddPersona;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PanelPersonas extends JPanel implements ConfigGUI{

	/**
	 * Version 1.0
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Persona> arrayPersonas=new ArrayList<Persona>();
	private JScrollPane scrollPane;
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private int filaSeleccionada = -1;
	
	public PanelPersonas() {
		setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height-200);
		setBackground(COLOR_FONDO);
		setLayout(null);
		
		JLabel lblAtras = new JLabel("Menu Principal");
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS).setVisible(false);
				FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(true);
				repaint();
			}
		});
		
		lblAtras.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAtras.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAtras.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtras.setForeground(Color.WHITE);
		lblAtras.setIcon(new ImageIcon(PanelPersonas.class.getResource("/images/back_REM48x48.png")));
		lblAtras.setBounds(0, 0, 100, 100);
		
		add(lblAtras);
		
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(lblAtras.getWidth(), lblAtras.getHeight(), SCREEN_SIZE.width-200, SCREEN_SIZE.height-400);
		
		inicializarModeloTabla();
		tabla = new JTable();
		setConfigTable(tabla);
		
		scrollPane.setViewportView(tabla);
		add(scrollPane);
		
		cargarPersonas();
		
		JPanel panelAbajo = new JPanel();
		panelAbajo.setLayout(new FlowLayout());
		panelAbajo.setBounds(100, 780, SCREEN_SIZE.width-200, 100);
		panelAbajo.setBackground(ConfigGUI.COLOR_FONDO);
		
		
		ButtonDefaultABM btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_AGREGAR);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAgregar();
			}
		});
		
		ButtonDefaultABM btnModificar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_MODIFICAR);
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				eventoModificar();
			}
		});
		ButtonDefaultABM btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ELIMINAR);
		
		
		panelAbajo.add(btnAgregar);
		panelAbajo.add(btnModificar);
		panelAbajo.add(btnEliminar);
		
		
		add(panelAbajo);
	}
	
	private void inicializarModeloTabla() {
		String[] columnas = {"Nombre", "Celular", "E-mail", "Telefono", "Domicilio", "Ciudad", "Provincia"};
		
			modeloTabla = new ModeloTabla(columnas) {
			
			private static final long serialVersionUID = 1L;

			
			
			
			@Override
			public void setValueAt(Object valor, int fila, int columna) {
	
				Persona persona = (Persona) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					persona.setNombre((String) valor);
					
				case 1:
					persona.getContacto().setCelular((BigInteger) valor) ;
					
				case 2:
					persona.getContacto().setMail((String) valor);
					
				case 3:
					persona.getContacto().setTelefono((BigInteger) valor);
					
				case 4:
					persona.setDomicilio((String) valor);
					
				case 5:
					persona.setCiudad((String) valor);
					
				case 6:
					persona.setProvincia((String) valor);
				}
				
				//if (columna == 1 || columna == 2 || columna == 3 || columna == 5 || columna == 6)
					//fireTableCellUpdated(fila, columna);
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Persona p = (Persona) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					return p.getApellido()+", "+p.getNombre();
					
				case 1:
					return p.getContacto().getCelular();
					
				case 2:
					return p.getContacto().getMail();
					
				case 3:
					return p.getContacto().getTelefono();
					
				case 4:
					return p.getDomicilio();
					
				case 5:
					return p.getCiudad();
					
				case 6:
					return p.getProvincia();
					
				default:
					return p;
				}
			}
		};
	}
	
	private void setConfigTable(JTable tabla) {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	public void cargarPersonas() {
		CtrlAddPersona ctrlPersonas=new CtrlAddPersona();
		arrayPersonas = ctrlPersonas.getListaPersonas();
		
		completarArray();
	}
	
	public void recargarPersonas() {
		arrayPersonas = new ArrayList<>();
		
		int size =modeloTabla.getObjetos().size();
				
		for (int i=0; i < size; i++) {
			modeloTabla.eliminar(i);
		}
		
		cargarPersonas();
	}
	
	public void actualizarLista() {
		modeloTabla.vaciarTabla();
		ordenarArray();
		completarArray();
	}
	
	private void ordenarArray() {
		Collections.sort(arrayPersonas, new Comparator<Persona>() {

			@Override
			public int compare(Persona o1, Persona o2) {
				String n1 = o1.getApellido().toLowerCase()+" "+o1.getNombre().toLowerCase();
				String n2 = o2.getApellido().toLowerCase()+" "+o2.getNombre().toLowerCase();
				return n1.compareTo(n2);
			}
		});
	}
	
	private void completarArray() {
		for (Persona persona : arrayPersonas) {
			
			modeloTabla.agregar(persona);
		}
	}
	
	private void eventoAgregar() {
		FramePpal.getFrameppal().setEnabled(false);
		FrameAddPersona frameAddPersona = FrameAddPersona.getFrameAddPersona();
		frameAddPersona.setModificar(false, null);
		frameAddPersona.setVisible(true);
	}
	

	private void eventoModificar() {
		this.filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada > -1) {
			Persona p = arrayPersonas.get(filaSeleccionada);
			FramePpal.getFrameppal().setEnabled(false);
			FrameAddPersona frameAddPersona = FrameAddPersona.getFrameAddPersona();
			frameAddPersona.setModificar(true, p.getIdPersona());
			
			frameAddPersona.getPanelDatos().updatePanel(p);
			frameAddPersona.getPanelSeguimientos().updatePanel(p.getSeguimientos());
			frameAddPersona.getPanelNotas().updatePanel(p);
			
			frameAddPersona.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila", "Info.", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

	public ArrayList<Persona> getArrayPersonas() {
		return arrayPersonas;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}
	
	
	
}
