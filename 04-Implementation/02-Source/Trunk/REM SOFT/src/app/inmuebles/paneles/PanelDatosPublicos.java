package app.inmuebles.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import gui.ConfigGUI;
import logica.constant.ValidateConstants;

public class PanelDatosPublicos extends JPanel {
	
	/* Constants */
	

	/* Panels de PanelDatosPublicos */
	private JPanel panelDivLeft, panelDivCenter, panelDivRight;
	
	/* Valores utilizados en PanelDatosPublicos */
	private JTextField txtPais, txtProvincia, txtCiudad, txtBarrio, txtCalle, txtNumero, txtPiso, txtDpto, txtCP;
	private JTextArea txtaDescripcion = new JTextArea(); 
	
	// Width left and right = 425   and   width center = 430
	
	public PanelDatosPublicos() {
		
		setBackground(ConfigGUI.COLOR_FONDO);
		setLayout(null);
		setBounds(0, 0, 1280, 480); //hardcodeado
		
		initPanelDivLeft();
		initPanelDivCenter();
		initPanelDivRight();
		
	}
	
	/* inits */
	
	private void initPanelDivLeft() {
		panelDivLeft = new JPanel();
		panelDivLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivLeft.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 15));
		panelDivLeft.setBorder(new MatteBorder(0, 2, 0, 0, Color.cyan));
		panelDivLeft.setBounds(0, 0, 425, getHeight());
		
		JLabel lblUbicacion = new JLabel("Ubicacion");
		lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbicacion.setForeground(Color.WHITE);
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUbicacion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelDivLeft.add(lblUbicacion);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(250, 30));
		panelDivLeft.add(lblInvisible);
		
		
		addCajaDeDatos(panelDivLeft, new JLabel("     Pais: "), txtPais = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Provincia: "), txtProvincia = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Ciudad: "), txtCiudad = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Barrio: "), txtBarrio = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Calle: "), txtCalle = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Num: "), txtNumero = new JTextField());
		addCajaDeDatos(panelDivLeft, new JLabel("     Piso: "), txtPiso = new JTextField());
		txtPiso.setPreferredSize(new Dimension(70, 35));
		addCajaDeDatos(panelDivLeft, new JLabel("     Dpto: "), txtDpto = new JTextField());
		txtDpto.setPreferredSize(new Dimension(70, 35));
		addCajaDeDatos(panelDivLeft, new JLabel("     CP: "), txtCP = new JTextField());
		
		
		add(panelDivLeft);
	}
	
	private void addCajaDeDatos(JPanel panel, JLabel label, JTextField textField) {
		addLabelDefault(panel, label);
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
		
		textField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				textField.setBorder(new LineBorder(Color.white));
			}
		});
		textField.setForeground(Color.white);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		//textField.setColumns(10);
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setPreferredSize(new Dimension(250, 35));
		
		panel.add(textField);
	}
	
	
	private void initPanelDivCenter() {
		panelDivCenter = new JPanel();
		panelDivCenter.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivCenter.setLayout(null);
		panelDivCenter.setBounds(panelDivLeft.getWidth(), 0, 430, getHeight());
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDescripcion.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		lblDescripcion.setBounds(430/8, ((getHeight()/2)), 130, 30);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(430/8, ((getHeight()/2)+50), 350, 150);
		
		txtaDescripcion.setWrapStyleWord(true);
		txtaDescripcion.setLineWrap(true);
		txtaDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(txtaDescripcion);
		
		panelDivCenter.add(lblDescripcion);
		panelDivCenter.add(scrollPane);
		
		add(panelDivCenter);
	}
	
	private void initPanelDivRight() {
		panelDivRight = new JPanel();
		panelDivRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivRight.setLayout(null);
		panelDivRight.setBorder(new MatteBorder(0, 0, 0, 2, Color.CYAN));
		panelDivRight.setBounds(panelDivLeft.getWidth()+panelDivCenter.getWidth(), 0, 425, getHeight());
		
		add(panelDivRight);
	}
	
	/* Validations */
	public boolean validarObligatorios() {
		
		//if (estaVacio(txtPais.getText()))
		
		return true;
	}
	
	private boolean estaVacio(String cadena) {
		for (int i=0; i < cadena.length(); i++) {
			Character character = cadena.charAt(i);
			
			if (! Character.isWhitespace(character)){
				return false;
			}
		}
		
		return true;
	}
	
	/* Getters & Setters */
	
	public JTextField getTxtPais() {
		return txtPais;
	}

	public void setTxtPais(JTextField txtPais) {
		this.txtPais = txtPais;
	}

	public JTextField getTxtProvincia() {
		return txtProvincia;
	}

	public void setTxtProvincia(JTextField txtProvincia) {
		this.txtProvincia = txtProvincia;
	}

	public JTextField getTxtCiudad() {
		return txtCiudad;
	}

	public void setTxtCiudad(JTextField txtCiudad) {
		this.txtCiudad = txtCiudad;
	}

	public JTextField getTxtBarrio() {
		return txtBarrio;
	}

	public void setTxtBarrio(JTextField txtBarrio) {
		this.txtBarrio = txtBarrio;
	}

	public JTextField getTxtCalle() {
		return txtCalle;
	}

	public void setTxtCalle(JTextField txtCalle) {
		this.txtCalle = txtCalle;
	}

	public JTextField getTxtNumero() {
		return txtNumero;
	}

	public void setTxtNumero(JTextField txtNumero) {
		this.txtNumero = txtNumero;
	}

	public JTextField getTxtPiso() {
		return txtPiso;
	}

	public void setTxtPiso(JTextField txtPiso) {
		this.txtPiso = txtPiso;
	}

	public JTextField getTxtDpto() {
		return txtDpto;
	}

	public void setTxtDpto(JTextField txtDpto) {
		this.txtDpto = txtDpto;
	}

	public JTextField getTxtCP() {
		return txtCP;
	}

	public void setTxtCP(JTextField txtCP) {
		this.txtCP = txtCP;
	}

	public JTextArea getTxtaDescripcion() {
		return txtaDescripcion;
	}

	public void setTxtaDescripcion(JTextArea txtaDescripcion) {
		this.txtaDescripcion = txtaDescripcion;
	}
	
	
	/* Validates */
	public boolean isValidoPanelDatosPub() {
		boolean panelValido = true;
		
		//Pais
		if (!ValidateConstants.validateTextAndLength(txtPais.getText(), 50)) {
			panelValido = false;
			txtPais.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Provincia
		if (!ValidateConstants.validateTextAndLength(txtProvincia.getText(), 50)) {
			panelValido = false;
			txtProvincia.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Ciudad
		if (!ValidateConstants.validateTextAndLength(txtCiudad.getText(), 50)) {
			panelValido = false;
			txtCiudad.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Barrio
		if (txtBarrio.getText().length() > 50) {
			panelValido = false;
			txtBarrio.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Calle
		if (!ValidateConstants.validateTextAndLength(txtCalle.getText(), 70)) {
			panelValido = false;
			txtCalle.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Numero
		if (!ValidateConstants.validateNumber(txtNumero.getText(), 7)) {
			panelValido = false;
			txtNumero.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		//Piso
		if (!ValidateConstants.validateNumber(txtPiso.getText(), 4)) {
			if (txtPiso.getText().length() != 0) {
				panelValido = false;
				txtPiso.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
		}
		
		//Dpto
		if (!ValidateConstants.validateTextAndLength(txtDpto.getText(), 1)) {
			if (txtDpto.getText().length() != 0) {
				panelValido = false;
				txtDpto.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
		}
		
		//CP
		if (!ValidateConstants.validateNumber(txtCP.getText(), 7)) {
			panelValido = false;
			txtCP.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		
		return panelValido;
	}

	
}
