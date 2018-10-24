package app.inmuebles.paneles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Operacion;
import logica.constant.ValidateConstants;

public class PanelOperaciones extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JPanel panelHeader, panelNavBar, panelTable, panelOperacion;
	private boolean agregarClicked = false, modificarClicked = false;
	private static final int AGREGAR_CLICKED = 1, MODIFICAR_CLICKED = 2, OK_CLICKED = 3;
	private CtrlInmueble ctrlInmueble = new CtrlInmueble();
	
	/* Panel Table */
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private int filaSeleccionada = -1;

	/* Panel Operacion */
	String[] operaciones;
	JComboBox<String> cbxOperacion = new JComboBox<>(), cbxMoneda = new JComboBox<>();
	JTextField txtComisionPorc = new JTextField(), txtComisionTotal, txtOperacion, txtPrecio, txtMoneda;
	JTextArea txtaObservacion;
	JButton btnOK;
	
	public PanelOperaciones() {
		
		setBackground(ConfigGUI.COLOR_FONDO);
		setLayout(null);
		setBounds(0, 0, 1280, 480); //hardcodeado
		setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
		
		initPanelHeader();
		initPanelNavBar();
		initPanelTable();
		initPanelOperacion();

	}
	
	
	
	private void initPanelHeader () {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		panelHeader.setBounds(0, 0, this.getWidth(), this.getHeight()/8);
		panelHeader.setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
		
		JLabel lblTitle = new JLabel("Operaciones");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelHeader.add(lblTitle);
		
		this.add(panelHeader);
	}
	
	private void initPanelNavBar () {
		panelNavBar = new JPanel();
		panelNavBar.setBackground(ConfigGUI.COLOR_FONDO);
		panelNavBar.setBounds(0, panelHeader.getHeight(), (this.getWidth()/4)*2, (this.getHeight()/4)-20);
		panelNavBar.setBorder(new MatteBorder(0, 2, 0, 0, Color.cyan));
		
		ButtonDefaultABM btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_AGREGAR);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eventAgregar();
				
			}
		});
		ButtonDefaultABM btnModificar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_MODIFICAR);
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eventModificar();
				
			}
		});
		ButtonDefaultABM btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ELIMINAR);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eventEliminar();
				
			}
		});
		
		panelNavBar.add(btnAgregar); panelNavBar.add(btnModificar); panelNavBar.add(btnEliminar);
		
		this.add(panelNavBar);
		
	}
	
	private void eventAgregar() {
		eventClicked(AGREGAR_CLICKED);
	}
	
	private void eventModificar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			eventClicked(MODIFICAR_CLICKED);
			Operacion operacion = (Operacion) modeloTabla.getObjetos().get(filaSeleccionada);
			
			if (operacion.getTipoOperacion().equals("1"))
				cbxOperacion.setSelectedItem("Alquiler");
			else if (operacion.getTipoOperacion().equals("2"))
				cbxOperacion.setSelectedItem("Venta");
			else if (operacion.getTipoOperacion().equals("3"))
				cbxOperacion.setSelectedItem("Alquiler Venta");
			else
				cbxOperacion.setSelectedItem(operacion.getTipoOperacion());
			
			txtPrecio.setText(decimalFormat.format(operacion.getPrecio()));
			cbxMoneda.setSelectedItem(operacion.getMoneda());
			txtComisionPorc.setText(operacion.getComision().toString());
			txtComisionTotal.setText(decimalFormat.format(operacion.getTotal()));
			txtaObservacion.setText(operacion.getObservacion());
			
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	private void eventEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			modeloTabla.eliminar(filaSeleccionada);
			panelOperacion.setVisible(false);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	private void eventClicked(int clicked) {
		switch (clicked) {
		case 1:
			this.agregarClicked = true;
			this.modificarClicked = false;
			break;
			
		case 2:
			this.agregarClicked = false;
			this.modificarClicked = true;
			break;
			
		case 3:
			this.agregarClicked = false;
			this.modificarClicked = false;
			break;
		}
		
		if (agregarClicked) {
			cbxOperacion.setEnabled(true);
			cbxOperacion.setSelectedIndex(0);
			txtPrecio.setText("");
			cbxMoneda.setSelectedIndex(0);
			txtComisionPorc.setText("");
			txtComisionTotal.setText("");
			txtaObservacion.setText("");
			panelOperacion.setVisible(true);
		}
		else if (modificarClicked) {
			cbxOperacion.setEnabled(false);
			panelOperacion.setVisible(true);
		}
		
	}
	
	private void initPanelOperacion() {
		
		
		panelOperacion = new JPanel();
		panelOperacion.setBackground(ConfigGUI.COLOR_FONDO);
		panelOperacion.setLayout(null);
		panelOperacion.setBounds(panelTable.getWidth()+panelTable.getX()*4, panelNavBar.getY(), getWidth()/3, getHeight()-20*4); //hardcodeado
		panelOperacion.setVisible(false);
		
		JPanel panelHead = new JPanel();
		panelHead.setBounds(0, 0, panelOperacion.getWidth(), panelOperacion.getHeight()/2);
		panelHead.setBackground(ConfigGUI.COLOR_FONDO);
		panelHead.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		
		JPanel panelCenterComision = new JPanel();
		panelCenterComision.setBounds(0, panelHead.getHeight(), panelOperacion.getWidth(), (panelOperacion.getHeight()/2)-50);
		panelCenterComision.setBackground(ConfigGUI.COLOR_FONDO);
		panelCenterComision.setBorder(new LineBorder(Color.WHITE));
		
		JPanel panelCenterObservacion = new JPanel();
		panelCenterObservacion.setBounds(0, panelHead.getHeight(), panelOperacion.getWidth(), (panelOperacion.getHeight()/2)-50);
		panelCenterObservacion.setBackground(ConfigGUI.COLOR_FONDO);
		panelCenterObservacion.setBorder(new LineBorder(Color.WHITE));
		panelCenterObservacion.setLayout(null);
		panelCenterObservacion.setVisible(false);

		addCajaDatos(panelHead, new JLabel("Operacion:"), null);
		operaciones = ctrlInmueble.getArrayTipoOperacion();
		setConfigCbx(this.cbxOperacion, operaciones);
		panelHead.add(this.cbxOperacion);
		addCajaDatos(panelHead, new JLabel("   Precio:"), this.txtPrecio=new JTextField());
		addCajaDatos(panelHead, new JLabel("    Moneda: "), null);
		setConfigCbx(this.cbxMoneda, new String[] {"ARS", "USD", "EUR", "BRL"});
		panelHead.add(this.cbxMoneda);
		
		JLabel lblComision = new JLabel("Comision");
		lblComision.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblComision.setForeground(Color.WHITE);
				lblComision.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblComision.setForeground(ConfigGUI.COLOR_SINFOCO);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				panelCenterComision.setVisible(true);
				panelCenterObservacion.setVisible(false);
			}
			
		});
		lblComision.setHorizontalAlignment(SwingConstants.CENTER);
		lblComision.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblComision.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblComision.setPreferredSize(new Dimension(160, 35));
		
		JLabel lblObservacion = new JLabel("Observacion");
		lblObservacion.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblObservacion.setForeground(Color.WHITE);
				lblObservacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				lblObservacion.setForeground(ConfigGUI.COLOR_SINFOCO);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelCenterComision.setVisible(false);
				panelCenterObservacion.setVisible(true);
			}
			
			
		});
		lblObservacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblObservacion.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblObservacion.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblObservacion.setPreferredSize(new Dimension(180, 35));		
		
		addCajaDatos(panelCenterComision, new JLabel("              "), new JTextField());
		( (JTextField) panelCenterComision.getComponent(1)).setBorder(new LineBorder(ConfigGUI.COLOR_FONDO));
		( (JTextField) panelCenterComision.getComponent(1)).setEnabled(false);
		addCajaDatos(panelCenterComision, new JLabel("Comision (%): "), txtComisionPorc);
		addCajaDatos(panelCenterComision, new JLabel("Comision ($): "), txtComisionTotal = new JTextField());
		txtComisionTotal.setEnabled(false);
		
		JScrollPane scrollPaneObs=new JScrollPane();
		scrollPaneObs.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneObs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		txtaObservacion = new JTextArea();
		txtaObservacion.setWrapStyleWord(true);
		txtaObservacion.setLineWrap(true);
		txtaObservacion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPaneObs.setViewportView(txtaObservacion);
		scrollPaneObs.setBounds(0, 0, panelCenterObservacion.getWidth(), panelCenterObservacion.getHeight());
		
		panelCenterObservacion.add(scrollPaneObs);
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventOK();
			}
		});
		setConfigButtonOK(btnOK);
		btnOK.setBounds(panelOperacion.getWidth()/3, panelOperacion.getHeight()-45, 150, 40);
		panelOperacion.add(btnOK);
		
		
		panelHead.add(lblComision);
		panelHead.add(lblObservacion);
		panelOperacion.add(panelHead);
		panelOperacion.add(panelCenterComision);
		panelOperacion.add(panelCenterObservacion);
		add(panelOperacion);
	}
	
	private void eventOK() {
		
		if (agregarClicked) {
			System.out.println("Agregar operacion");
			
			if (validateAgregar(1)) {
				panelOperacion.setVisible(false);
				eventClicked(OK_CLICKED);
			}
				
		}
		
		if (modificarClicked) {
			System.out.println("Modificar ");
			
			if (validateModificacion()) {
				panelOperacion.setVisible(false);
				eventClicked(OK_CLICKED);
			}
		}
		
		
		
		/*
		if (!agregarClicked && !modificarClicked)
			panelOperacion.setVisible(false);
			*/
	}
	
	private boolean validateAgregar(int opcion) {
		BigDecimal precio = null;
		BigDecimal comision_porc = null;
		
		if (ValidateConstants.validateMoney(txtPrecio.getText())) {
			precio = new BigDecimal(txtPrecio.getText().replace(',', '.'));
		}
		else {
			txtPrecio.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			txtPrecio.setText("ERROR");
		}
		
		
		if (ValidateConstants.validatePercentage(txtComisionPorc.getText())) {
			comision_porc = new BigDecimal(txtComisionPorc.getText().replace(',', '.'));
		}
		else {
			txtComisionPorc.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			txtComisionPorc.setText("ERROR");
		}
		
		if (precio != null && comision_porc != null) {
			Operacion operacion = new Operacion(null, cbxOperacion.getSelectedItem().toString(), precio, cbxMoneda.getSelectedItem().toString(), comision_porc, txtaObservacion.getText());
			
			
			if (opcion == 1) {
				if (validarRepetido(null, operacion.getTipoOperacion(), 1))
					modeloTabla.agregar(operacion);
				else
					return false;
			}
			else {
				if (validarRepetido( ((Operacion)modeloTabla.getObjetos().get(filaSeleccionada)).getTipoOperacion(), operacion.getTipoOperacion(), 0))
					modeloTabla.modificarObjeto(filaSeleccionada, operacion);
				else
					return false;
			}
				
			
			return true;
		}
		
		return false;
	}
	
	private boolean validarRepetido(String operacionOriginal, String tipoOperacionActual, int opcion) {
		
		if (opcion == 1) {
			for (int i=0; i < modeloTabla.getObjetos().size(); i++) {
				String tipoOperacion = ((Operacion) modeloTabla.getObjetos().get(i)).getTipoOperacion();
				
				if (tipoOperacion.equals("1"))
					tipoOperacion = "Alquiler";
				if (tipoOperacion.equals("2"))
					tipoOperacion = "Venta";
				if (tipoOperacion.equals("3"))
					tipoOperacion = "Alquiler Temporal";
				
				if (tipoOperacionActual.equals(tipoOperacion)) {
					JOptionPane.showMessageDialog(null, "El tipo de operacion '"+tipoOperacion+"'.\n"
							+ "Ya esta agregado actualmente.");
					return false;
				}
			}
		}
		
		if (opcion == 0) {
			for (int i=0; i < modeloTabla.getObjetos().size(); i++) {
				String tipoOperacion = ((Operacion) modeloTabla.getObjetos().get(i)).getTipoOperacion();
				
				if (tipoOperacion.equals("1"))
					tipoOperacion = "Alquiler";
				if (tipoOperacion.equals("2"))
					tipoOperacion = "Venta";
				if (tipoOperacion.equals("3"))
					tipoOperacion = "Alquiler Temporal";
				
				
				System.out.println(tipoOperacionActual + " | " + tipoOperacion);
				
				
				if (tipoOperacionActual.equals(tipoOperacion) && !tipoOperacion.equals(operacionOriginal)) {
					JOptionPane.showMessageDialog(null, "El tipo de operacion '"+tipoOperacion+"'.\n"
							+ "Ya esta agregado actualmente.");
					return false;
				}
			}
		}
		
		
		return true;
	}
	
	private boolean validateModificacion() {
		
		return validateAgregar(2);
	}
	
	private void setConfigButtonOK(JButton btn) {
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setVerticalAlignment(SwingConstants.CENTER);
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn.setFocusable(false);
		btn.setBorderPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
	}
	
	private void setConfigCbx(JComboBox<String> cbx, String[] modelCbx) {
		cbx.setModel(new DefaultComboBoxModel<String>(modelCbx));
		cbx.setUI(new BasicComboBoxUI() {
		    @Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setBorder(new LineBorder(Color.WHITE));
		        JList<?> list = basicComboPopup.getList();
		        list.setBackground(new Color(71, 71, 71));
		        list.setForeground(Color.WHITE);
		        return basicComboPopup;
		    }
		});
		cbx.setForeground(Color.WHITE);
		cbx.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbx.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		cbx.setBackground(ConfigGUI.COLOR_FONDO);
		cbx.setPreferredSize(new Dimension(220, 35));
	}
	
	private void addCajaDatos (JPanel panel, JLabel label, JTextField textField) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 22));
		label.setPreferredSize(new Dimension(150, 35));
		
		if (textField != null) {
			textField.setForeground(Color.white);
			textField.setFont(new Font("Tahoma", Font.PLAIN, 21));
			//textField.setColumns(10);
			textField.setCaretColor(Color.white);
			textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
			textField.setBackground(ConfigGUI.COLOR_FONDO);
			textField.setHorizontalAlignment(SwingConstants.CENTER);
			textField.setPreferredSize(new Dimension(220, 35));
			textField.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
					
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
					
				}
			});
		}
		
		
		panel.add(label);
		
		if (textField != null)
			panel.add(textField);
	}
	
	private void initPanelTable () {
		panelTable = new JPanel();
		panelTable.setBackground(new Color(181, 36, 65));
		panelTable.setBounds(20, panelNavBar.getHeight()+panelNavBar.getY(), 
				((this.getWidth()/4)*2)+2, 50+this.getHeight()-panelNavBar.getHeight()-panelNavBar.getY()-panelHeader.getHeight());
		
		panelTable.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, (this.getWidth()/4)*2, panelTable.getHeight());
		scrollPane.setBorder(new LineBorder(Color.cyan));
		
		inicializarModeloTabla();
		tabla = new JTable();
		setConfigTable(tabla);
		scrollPane.setViewportView(tabla);
		
		panelTable.add(scrollPane);
		this.add(panelTable);
	}
	
	private void inicializarModeloTabla() {
		String[] columns = {"Operacion", "Precio", "Moneda", "Comision (%)", "Total Comision ($)"};
		
		modeloTabla = new ModeloTabla(columns) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				Operacion operacion = (Operacion) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					operacion.setTipoOperacion((String) valor);
					
				case 1:
					operacion.setPrecio((BigDecimal) valor);
					
				case 2:
					operacion.setMoneda((String) valor);
					
				case 3:
					operacion.setComision((BigDecimal) valor);
					
				case 4:
					operacion.setTotal((BigDecimal) valor);
				}
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Operacion operacion = (Operacion) getObjetos().get(fila);
				
				switch (columna) {
					case 0:
						if (operacion.getTipoOperacion().equals("1"))
							return "Alquiler";
						if (operacion.getTipoOperacion().equals("2"))
							return "Venta";
						if (operacion.getTipoOperacion().equals("3"))
							return "Alquiler Temporal";
						
						return operacion.getTipoOperacion();
						
					case 1:
						return operacion.getPrecio();
						
					case 2:
						return operacion.getMoneda();
						
					case 3:
						return operacion.getComision();
						
					case 4:
						return operacion.getTotal();
					
					default:
						return operacion;
				}
			}
		};
	}
	
	private void setConfigTable(JTable tabla) {
		tabla.setModel(modeloTabla);
		Render render = new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}


	/* Getter & Setters*/
	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

}
