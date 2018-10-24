package app.inmuebles.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import control.CtrlAddPersona;
import gui.ConfigGUI;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Persona;

public class PanelDatosPrivados extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Panels de PanelDatosPublicos */
	private JPanel panelDivLeft, panelDivCenter, panelDivRight;
	
	/* Elements */
	private JTextField txtDNI, txtNombre, txtApellido, txtTelefono, txtCelular;
	
	/* Div Center */
	private JButton btnAgregar, btnEliminar;
	private JScrollPane scrollPane;
	
	/* Div Left */
	private JTextField txtNomenclatura;
	private final String[] columns = {"Muy Bueno (1)", "Bueno (2)", "Normal (3)", "Malo (4)", "Deplorable (5)"}; 
	private JComboBox<String> cbxEstado = new JComboBox<>(columns);
	private JCheckBox checkBoxCartel = new JCheckBox();

	/* Div Right */
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private ArrayList<Persona> arrayPersonas= new ArrayList<>();
	private int filaSeleccionada = -1;
	private Persona persona = null;
	
	public PanelDatosPrivados() {
		
		setBackground(ConfigGUI.COLOR_FONDO);
		setLayout(null);
		setBounds(0, 0, 1280, 480); //hardcodeado
		
		initPanelDivLeft();
		initPanelDivCenter();
		initPanelDivRight();

	}
	
	private void initPanelDivLeft() {
		panelDivLeft = new JPanel();
		panelDivLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivLeft.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 15));
		panelDivLeft.setBorder(new MatteBorder(0, 2, 0, 0, Color.cyan));
		panelDivLeft.setBounds(0, 0, 425, getHeight());
		
		JLabel lblUbicacion = new JLabel("Otros Datos");
		lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbicacion.setForeground(Color.WHITE);
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUbicacion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelDivLeft.add(lblUbicacion);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(250, 30));
		panelDivLeft.add(lblInvisible);
		
		addCajaDeDatos(panelDivLeft, new JLabel(" Nomenclatura: "), txtNomenclatura = new JTextField());
		txtNomenclatura.setEditable(true);
		
		addCajaDeDatos(panelDivLeft, new JLabel(" Condicion: "), null);
		cbxEstado.setPreferredSize(new Dimension(250, 35));
		panelDivLeft.add(cbxEstado);
		
		addCajaDeDatos(panelDivLeft, new JLabel(" Cartel Publicado: "), null);
		checkBoxCartel.setPreferredSize(new Dimension(250, 35));
		checkBoxCartel.setOpaque(false);
		panelDivLeft.add(checkBoxCartel);
		
		add(panelDivLeft);
	}
	
	private void initPanelDivCenter() {
		
		panelDivCenter = new JPanel();
		panelDivCenter.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		panelDivCenter.setBorder(new MatteBorder(0, 3, 0, 0, ConfigGUI.COLOR_FONDO));
		panelDivCenter.setBounds(panelDivLeft.getWidth(), 0, 430, getHeight());
		
		JLabel lblUbicacion = new JLabel("Datos del Propietario");
		lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbicacion.setForeground(Color.WHITE);
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUbicacion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelDivCenter.add(lblUbicacion);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(280, 30));
		panelDivCenter.add(lblInvisible);
		
		addCajaDeDatos(panelDivCenter, new JLabel("             DNI:"), txtDNI = new JTextField());
		addCajaDeDatos(panelDivCenter, new JLabel("       Nombre:"), txtNombre = new JTextField());
		addCajaDeDatos(panelDivCenter, new JLabel("       Apellido:"), txtApellido = new JTextField());
		addCajaDeDatos(panelDivCenter, new JLabel("      Telefono:"), txtTelefono = new JTextField());
		addCajaDeDatos(panelDivCenter, new JLabel("         Celular:"), txtCelular = new JTextField());
		
		addButtonSearch(panelDivCenter);
		
		add(panelDivCenter);
	}
	
	private void addButtonSearch(JPanel panel) {
		btnAgregar = new JButton(" Agregar Persona");
		btnAgregar.setIcon(new ImageIcon(PanelDatosPrivados.class.getResource("/images/add-white-24x24.png")));
		btnAgregar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				eventAgregarPersona();
			}
			
		});
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAgregar.setFocusable(false);
		btnAgregar.setBorderPainted(false);
		btnAgregar.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		btnAgregar.setPreferredSize(new Dimension(panel.getWidth()-30, 40));
		
		btnEliminar = new JButton(" Eliminar Persona");
		btnEliminar.setIcon(new ImageIcon(PanelDatosPrivados.class.getResource("/images/subtract-change-24x24.png")));
		btnEliminar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				eventEliminarPersona();
			}
			
		});
		btnEliminar.setForeground(Color.WHITE);
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnEliminar.setFocusable(false);
		btnEliminar.setBorderPainted(false);
		btnEliminar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnEliminar.setPreferredSize(new Dimension(panel.getWidth()-30, 40));
		
		panel.add(btnAgregar);
		panel.add(btnEliminar);
	}
	
	private void eventAgregarPersona() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada > -1) {
			if(persona == null) {
				this.persona = arrayPersonas.get(filaSeleccionada);
				
				txtNombre.setText(persona.getNombre());
				txtApellido.setText(persona.getApellido());
				txtDNI.setText(persona.getDni().toString());
				
				if (persona.getContacto().getTelefono() != null)
					txtTelefono.setText(persona.getContacto().getTelefono().toString());
				
				if (persona.getContacto().getCelular() != null)
					txtCelular.setText(persona.getContacto().getCelular().toString());
			}
			else
				JOptionPane.showMessageDialog(null, "Ya ha agregado una persona");
		}
		else
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
	}
	
	private void eventEliminarPersona() {
		
		if (this.persona != null) {
			this.persona = null;
			txtNombre.setText("");
			txtApellido.setText("");
			txtDNI.setText("");
			txtTelefono.setText("");
			txtCelular.setText("");
		}
		else {
			JOptionPane.showMessageDialog(null, "No hay persona para eliminar");
		}
	}
	
	private void initPanelDivRight() {
		panelDivRight = new JPanel();
		panelDivRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivRight.setLayout(null);
		panelDivRight.setBounds(panelDivLeft.getWidth()+panelDivCenter.getWidth(), 0, 425, getHeight());
		
		addHeaderPanelDivRight();
		
		add(panelDivRight);
	}
	
	private void addHeaderPanelDivRight() {
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setBounds(0, 0, panelDivRight.getWidth(), panelDivRight.getHeight()/8);
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		panelHeader.setBorder(new MatteBorder(0, 0, 0, 1, Color.cyan));
		
		JLabel lblPersonas = new JLabel("Lista de Personas: ");
		lblPersonas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonas.setForeground(Color.WHITE);
		lblPersonas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPersonas.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelHeader.add(lblPersonas);
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, panelHeader.getHeight(), panelDivRight.getWidth(), panelDivRight.getHeight()-panelHeader.getHeight());
		panelTable.setBorder(new MatteBorder(0, 0, 0, 1, Color.cyan));
		panelTable.setLayout(null);
		panelTable.setBackground(ConfigGUI.COLOR_FONDO);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, panelDivRight.getWidth(), panelDivRight.getHeight()-panelHeader.getHeight());
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 2, Color.cyan));
		
		inicializarModeloTabla();
		tabla = new JTable();
		setConfigTable(tabla);
		cargarPersonas();
		scrollPane.setViewportView(tabla);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelTable.add(scrollPane);
		
		
		
		panelDivRight.add(panelHeader);
		panelDivRight.add(panelTable);
	}
	
	private void inicializarModeloTabla() {
		String[] columnas = {"Nombre", "DNI", "Vive en"};
		
			modeloTabla = new ModeloTabla(columnas) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// NOTHING
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Persona p = (Persona) getObjetos().get(fila);
				
				switch (columna) {
					case 0:
						return p.getApellido()+", "+p.getNombre();
						
					case 1:
						return p.getDni();
						
					case 2:
						return p.getCiudad()+", "+p.getProvincia();
						
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
	
	private void cargarPersonas() {
		CtrlAddPersona ctrlPersonas=new CtrlAddPersona();
		arrayPersonas = ctrlPersonas.getListaPersonas();
		
		for (Persona persona : arrayPersonas) {
			modeloTabla.agregar(persona);
		}
			
	}
	
	private void addCajaDeDatos(JPanel panel, JLabel label, JTextField textField) {
		addLabelDefault(panel, label);
		if (textField != null)
			addTextfieldDefault(panel, textField);
	}
	
	private void addLabelDefault(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//label.setPreferredSize(new Dimension(30, 30));
		
		panel.add(label);
	}
	
	private void addTextfieldDefault(JPanel panel, JTextField textField) {
		
		textField.setForeground(Color.white);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		//textField.setColumns(10);
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setPreferredSize(new Dimension(250, 35));
		textField.setEditable(false);
		
		panel.add(textField);
	}
	
	
	/* Validates */
	public boolean isValidoPanelDatosPriv() {
		boolean panelValido = true;
		
		if (txtDNI.getText().length() == 0)
			return false;
		
		return panelValido;
	}

	
	/* Get & Setters */
	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public void setBtnAgregar(JButton btnAgregar) {
		this.btnAgregar = btnAgregar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public JTextField getTxtDNI() {
		return txtDNI;
	}

	public void setTxtDNI(JTextField txtDNI) {
		this.txtDNI = txtDNI;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtCelular() {
		return txtCelular;
	}

	public void setTxtCelular(JTextField txtCelular) {
		this.txtCelular = txtCelular;
	}

	public JTextField getTxtNomenclatura() {
		return txtNomenclatura;
	}

	public void setTxtNomenclatura(JTextField txtNomenclatura) {
		this.txtNomenclatura = txtNomenclatura;
	}

	public JComboBox<String> getCbxEstado() {
		return cbxEstado;
	}

	public void setCbxEstado(JComboBox<String> cbxEstado) {
		this.cbxEstado = cbxEstado;
	}

	public JCheckBox getCheckBoxCartel() {
		return checkBoxCartel;
	}

	public void setCheckBoxCartel(JCheckBox checkBoxCartel) {
		this.checkBoxCartel = checkBoxCartel;
	}
	
	
	
	
	
	

}
