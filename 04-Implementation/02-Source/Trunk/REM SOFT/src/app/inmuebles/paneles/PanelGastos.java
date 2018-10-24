package app.inmuebles.paneles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
import logica.GastoFijo;
import logica.constant.ValidateConstants;

public class PanelGastos extends JPanel {


	private static final long serialVersionUID = 1L;

	private JPanel panelHeader, panelNavBar, panelTable, panelGastos, panelCenter;
	private int filaSeleccionada = -1;
	private static final int AGREGAR_CLICKED = 1, MODIFICAR_CLICKED = 2, OK_CLICKED = 3;
	private boolean agregarClicked = false, modificarClicked = false;
	
	/* Panel Table */
	private ModeloTabla modeloTabla;
	private JTable tabla;
	
	
	/* Panel Gastos */
	private JComboBox<String> cbxGastos = new JComboBox<>();
	private JTextField txtMonto = new JTextField();
	private CtrlInmueble ctrlInmueble = new CtrlInmueble();
	private JButton btnOK;
	
	
	
	private final String[] arrayMonths = {"Enero", "Febrero", "Marzo",
			"Abril", "Mayo", "Junio",
			"Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre"};
	
	
	public PanelGastos() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setLayout(null);
		setBounds(0, 0, 1280, 480); 
		setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
		
		initPanelHeader();
		initPanelNavBar();
		initPanelTable();
		initPanelGastos();
	}
	
	private void initPanelHeader () {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		panelHeader.setBounds(0, 0, this.getWidth(), this.getHeight()/8);
		panelHeader.setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
		
		JLabel lblTitle = new JLabel("Gastos Fijos");
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
		panelNavBar.setBorder(new MatteBorder(0, 2, 0, 0, Color.cyan));
		panelNavBar.setBounds(0, panelHeader.getHeight(), (this.getWidth()/4)*2, (this.getHeight()/4)-20);
		
		ButtonDefaultABM btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_AGREGAR);
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventAgregar();
			}
		});
		ButtonDefaultABM btnModificar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_MODIFICAR);
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventModificar();
				GastoFijo gastoFijo = (GastoFijo) modeloTabla.getObjetos().get(filaSeleccionada);
				cbxGastos.setSelectedItem(gastoFijo.getGasto());
				txtMonto.setText(gastoFijo.getMonto().toString());
				for (int i = 0; i < arrayMonths.length; i++) {
					if (gastoFijo.getMesesAplica().get(i) == 1)
						( (JButton) panelCenter.getComponent(i)).setBackground(ConfigGUI.COLOR_BTN_SUCESS);
					else
						( (JButton) panelCenter.getComponent(i)).setBackground(ConfigGUI.COLOR_FONDO);
				}
			}
		});
		ButtonDefaultABM btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ELIMINAR);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventEliminar();
			}
		});
		
		panelNavBar.add(btnAgregar); panelNavBar.add(btnModificar); panelNavBar.add(btnEliminar);
		
		this.add(panelNavBar);
	}
	
	
	
	
	private void initPanelTable () {
		panelTable = new JPanel();
		panelTable.setBackground(new Color(181, 36, 65));
		panelTable.setBounds(20, panelNavBar.getHeight()+panelNavBar.getY(), 
				((this.getWidth()/4)*2)+2, 50+this.getHeight()-panelNavBar.getHeight()-panelNavBar.getY()-panelHeader.getHeight());
		panelTable.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, panelTable.getWidth(), panelTable.getHeight());
		scrollPane.setBorder(new LineBorder(Color.cyan));
		
		inicializarModeloTabla();
		tabla = new JTable();
		setConfigTable(tabla);
		scrollPane.setViewportView(tabla);
		
		panelTable.add(scrollPane);
		this.add(panelTable);
	}
	
	private void inicializarModeloTabla() {
		String[] columns = {"Detalle", "Monto", "Trasladar a Liquidacion"};
		
		modeloTabla = new ModeloTabla(columns) {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				/*GastoFijo gastoFijo = (GastoFijo) getObjetos().get(fila);
				
				switch (columna) {
					case 0:
						gastoFijo.setGasto((String) valor);
						
					case 1:
						gastoFijo.setMonto((BigDecimal) valor);
						
					case 2:
						gastoFijo.setLiquidacion((Boolean) valor);
				}*/
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				GastoFijo gastoFijo = (GastoFijo) getObjetos().get(fila);
				
				switch (columna) {
					case 0:
						return gastoFijo.getGasto();
						
					case 1:
						return gastoFijo.getMonto();
						
					case 2:
						if (gastoFijo.getLiquidacion())
							return "Si";
						else
							return "No";
						
					default:
						return gastoFijo;
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
	
	private void initPanelGastos() {
		panelGastos = new JPanel();
		panelGastos.setBackground(ConfigGUI.COLOR_FONDO);
		panelGastos.setLayout(null);
		panelGastos.setBounds(panelTable.getWidth()+panelTable.getX()*4, panelNavBar.getY(), getWidth()/3, getHeight()-20*4); //hardcodeado
		panelGastos.setVisible(false);
		
		JPanel panelHead = new JPanel();
		panelHead.setBounds(0, 0, panelGastos.getWidth(), panelGastos.getHeight()/2);
		panelHead.setBackground(ConfigGUI.COLOR_FONDO);
		panelHead.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 15));
		
		addCajaDatos(panelHead, new JLabel("Gasto: "), null);
		String[] modelCbxGastos = ctrlInmueble.getArrayTipoGastoFijo();
		setConfigCbx(cbxGastos, modelCbxGastos );
		panelHead.add(cbxGastos);
		addCajaDatos(panelHead, new JLabel("Monto ($): "), txtMonto);
		
		
		addCajaDatos(panelHead, new JLabel("              "), new JTextField());
		( (JTextField) panelHead.getComponent(5)).setBorder(new LineBorder(ConfigGUI.COLOR_FONDO));
		( (JTextField) panelHead.getComponent(5)).setEnabled(false);
		
		JLabel lblMesesAplica = new JLabel("Meses que aplica: ");
		lblMesesAplica.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblMesesAplica.setForeground(Color.WHITE);
				lblMesesAplica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblMesesAplica.setForeground(ConfigGUI.COLOR_SINFOCO);
			}
		});
		lblMesesAplica.setHorizontalAlignment(SwingConstants.CENTER);
		lblMesesAplica.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblMesesAplica.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblMesesAplica.setPreferredSize(new Dimension(250, 35));
		
		panelCenter = new JPanel();
		panelCenter.setBounds(0, panelHead.getHeight(), panelGastos.getWidth(), (panelGastos.getHeight()/2)-50);
		panelCenter.setBackground(ConfigGUI.COLOR_FONDO);
		panelCenter.setBorder(new LineBorder(Color.WHITE));
		panelCenter.setLayout(new GridLayout(4, 3));
		
		
		
		for (int i = 0; i < arrayMonths.length; i++) {
			JButton btnDefault = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
			btnDefault.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (btnDefault.getBackground().equals(ConfigGUI.COLOR_FONDO)) {
						btnDefault.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
					}
					else {
						btnDefault.setBackground(ConfigGUI.COLOR_FONDO);
					}
				}
				
			});
			btnDefault.setText(arrayMonths[i]);
			btnDefault.setVerticalAlignment(SwingConstants.CENTER);
			btnDefault.setFont(new Font("Tahoma", Font.PLAIN, 18));
			panelCenter.add(btnDefault);
		}
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventOK();
			}
		});
		setConfigButtonOK(btnOK);
		btnOK.setBounds(panelGastos.getWidth()/3, panelGastos.getHeight()-45, 150, 40);
		panelGastos.add(btnOK);
		
		
		//System.out.println(new Date().getYear()+1900);
		panelHead.add(lblMesesAplica);
		panelGastos.add(panelCenter);
		
		panelGastos.add(panelHead);
		add(panelGastos);
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
	
	private void setConfigCbx(JComboBox<String> cbx, String[] modelCbx) {
		cbx.setModel(new DefaultComboBoxModel<String>(modelCbx));
		cbx.setUI(new BasicComboBoxUI() {
		    @Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setBorder(new LineBorder(Color.WHITE));
		        @SuppressWarnings("rawtypes")
				JList list = basicComboPopup.getList();
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
	
	/* Events */
	
	private void eventAgregar() {
		eventClicked(AGREGAR_CLICKED);
	}
	
	private void eventModificar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			eventClicked(MODIFICAR_CLICKED);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	private void eventEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			modeloTabla.eliminar(filaSeleccionada);
			
			/* Hacer visible el panel de Gastos */
			//panelOperacion.setVisible(false);
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
			cbxGastos.setSelectedIndex(0);
			txtMonto.setText("");
			for (int i = 0; i < arrayMonths.length; i++) {
				panelCenter.getComponent(i).setBackground(ConfigGUI.COLOR_FONDO);
			}
			
			panelGastos.setVisible(true);
		}
		else if (modificarClicked) {
			panelGastos.setVisible(true);
		}
		
	}
	
	private void eventOK() {
		
		if (agregarClicked) {
			if (validateAgregar(1)) {
				panelGastos.setVisible(false);
				eventClicked(OK_CLICKED);
			}
				
		}
		
		if (modificarClicked) {
			System.out.println("Modificar ");
			
			if (validateModificacion()) {
				panelGastos.setVisible(false);
				eventClicked(OK_CLICKED);
			}
		}
	}
	
	private boolean validateModificacion() {
		return validateAgregar(2);
	}
	
	private boolean validateAgregar(int opcion) {
		BigDecimal monto = null;
		
		if (ValidateConstants.validateMoney(txtMonto.getText())) {
			monto = new BigDecimal(txtMonto.getText().replace(',', '.'));
		}
		else {
			txtMonto.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			txtMonto.setText("ERROR");
		}
		
		if (monto != null) {
			GastoFijo gastoFijo = new GastoFijo(null, null, cbxGastos.getSelectedItem().toString(), monto, false);
			if (opcion == 1) {
				
				/*
				 * for (int i=0; i < modeloTabla.getObjetos().size(); i++) {
				String tipoOperacion = ((Operacion) modeloTabla.getObjetos().get(i)).getTipoOperacion();
				if (operacion.getTipoOperacion().equals(tipoOperacion)) {
					JOptionPane.showMessageDialog(null, "El tipo de operacion '"+tipoOperacion+"'.\n"
							+ "Ya esta agregado actualmente.");
					return false;
				}
			}
				 */
				
				List<Integer> list = new ArrayList<Integer>(arrayMonths.length);
				for (int i = 0; i < arrayMonths.length; i++) {
					if (panelCenter.getComponent(i).getBackground().equals(ConfigGUI.COLOR_BTN_SUCESS))
						list.add(1);
					else
						list.add(0);
				}
				gastoFijo.setMesesAplica(list);
				
				if (validarRepetido(null, gastoFijo.getGasto(), 1))
					modeloTabla.agregar(gastoFijo);
				else
					return false;
			}
			else {
				
				if (validarRepetido(((GastoFijo)modeloTabla.getObjetos().get(filaSeleccionada)).getGasto(), gastoFijo.getGasto(), 0))
				modeloTabla.modificarObjeto(filaSeleccionada, gastoFijo);
			}
				
			
			return true;
		}
		
		return false;
	}
	
	private boolean validarRepetido(String gastoOriginal, String tipoGastoActual, int opcion) {
		
		
		if (opcion == 1) {
			for (int i=0; i < modeloTabla.getObjetos().size(); i++) {
				String tipoGasto = ((GastoFijo) modeloTabla.getObjetos().get(i)).getGasto();
				
				if (tipoGastoActual.equals(tipoGasto)) {
					JOptionPane.showMessageDialog(null, "El tipo de operacion '"+tipoGasto+"'.\n"
							+ "Ya esta agregado actualmente.");
					return false;
				}
			}
		}
		
		if (opcion == 0) {
			for (int i=0; i < modeloTabla.getObjetos().size(); i++) {
				String tipoGasto = ((GastoFijo) modeloTabla.getObjetos().get(i)).getGasto();
				
				if (tipoGastoActual.equals(tipoGasto) && !tipoGasto.equals(gastoOriginal)) {
					JOptionPane.showMessageDialog(null, "El tipo de operacion '"+tipoGasto+"'.\n"
							+ "Ya esta agregado actualmente.");
					return false;
				}
			}
		}
		
		
		return true;
	}
	

	/* Getters & Setters */
	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}
	
	
	

}
