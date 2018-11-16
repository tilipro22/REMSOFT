package gui.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import app.utils.CtrlUbicacion;
import gui.ConfigGUI;
import logica.ContactoPersona;
import logica.Persona;

public class PanelAddPersona_Datos extends PanelConstants {

	private static final long serialVersionUID = 1L;
	static final int TXT_NOMBRE = 0;
	static final int TXT_APELLIDO = 1;
	static final int TXT_DNI = 2;
	static final int TXT_DOMICILIO = 3;
	static final int TXT_CIUDAD = 4;
	static final int TXT_PROVINCIA = 5;
	//static final int TXT_NACIONALIDAD = 6;
	static final int TXT_TELEFONO = 6;
	static final int TXT_CELULAR = 7;
	static final int TXT_EMAIL = 8;
	
	static final int CBX_CIUDAD = 4;
	static final int CBX_PROVINCIA = 5;
	static final int CBX_NACIONALIDAD = 6;
	
	private JPanel panelDatosOblig, panelDatosNoOblig;
	private ArrayList<JTextField> txtDatos = new ArrayList<JTextField>();
	
	private JComboBox<String> cbxPaises, cbxProvincias, cbxCiudades;
	
	public PanelAddPersona_Datos() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 450, 450);
		setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		setLayout(null);
		
		panelDatosOblig = new JPanel();
		panelDatosOblig.setBackground(ConfigGUI.COLOR_FONDO);
		panelDatosOblig.setBounds(0, 0, 450, 300);
		panelDatosOblig.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		panelDatosOblig.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		
		agregarCajaDatos(new JLabel(), "Nombre:", new JTextField(), 1);
		agregarCajaDatos(new JLabel(), "Apellido:", new JTextField(),1 );
		agregarCajaDatos(new JLabel(), "DNI:", new JTextField(),1);
		agregarCajaDatos(new JLabel(), "Domicilio:", new JTextField(),1);
		agregarCajaDatos(new JLabel(), "Ciudad:", cbxCiudades = new JComboBox<String>(), CBX_CIUDAD);
		agregarCajaDatos(new JLabel(), "Ciudad:", new JTextField(),99);
		agregarCajaDatos(new JLabel(), "Provincia:", cbxProvincias = new JComboBox<String>(), CBX_PROVINCIA);
		agregarCajaDatos(new JLabel(), "Provincia:", new JTextField(),99);
		agregarCajaDatos(new JLabel(), "Nacionalidad:", cbxPaises = new JComboBox<String>(), CBX_NACIONALIDAD);
		
		add(panelDatosOblig);
		
		JLabel lblContacto = new JLabel("Contacto:");
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContacto.setHorizontalAlignment(SwingConstants.CENTER);
		lblContacto.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		lblContacto.setForeground(Color.WHITE);
		lblContacto.setBounds(10, 300, 71, 16);
		add(lblContacto);

		panelDatosNoOblig = new JPanel();
		panelDatosNoOblig.setBackground(ConfigGUI.COLOR_FONDO);
		panelDatosNoOblig.setBounds(0, 315, 450, 120);
		panelDatosNoOblig.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		panelDatosNoOblig.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		
		agregarCajaDatos(new JLabel(), "Telefono:", new JTextField(), 2);
		agregarCajaDatos(new JLabel(), "Celular:", new JTextField(), 2);
		agregarCajaDatos(new JLabel(), "E-Mail:", new JTextField(), 2);
		
		add(panelDatosNoOblig);

	}
	
	private void agregarCajaDatos(JLabel lbl, String nombreLbl, JTextField txt, int opcion) {
		
		if (opcion != 99)
			inicializarLabel(lbl, nombreLbl, opcion);
		
		inicializarTextField(txt, opcion);
	}
	
	private void agregarCajaDatos (JLabel lbl, String nombreLbl, JComboBox<String> comboBox, int option) {
		inicializarLabel(lbl, nombreLbl, 1);
		inicializarCbx(comboBox, option);
	}
	
	private void inicializarLabel(JLabel lbl ,String nombre, int opcion) {
		lbl.setText(nombre);
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		switch (opcion) {
		case 1:
			panelDatosOblig.add(lbl);
			break;
			
		case 2:
			panelDatosNoOblig.add(lbl);
			break;
		}
		
	}
	
	private void inicializarCbx(JComboBox<String> comboBox, int option) {
		CtrlUbicacion ctrlUbicacion = new CtrlUbicacion();
		
		switch (option) {
		case CBX_CIUDAD:
			setConfigCbx(comboBox, ctrlUbicacion.getListaCiudadesByIdProvincia(1));
			comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
			comboBox.setSelectedItem("LUJAN");
			break;
		
		case CBX_PROVINCIA:
			setConfigCbx(comboBox, ctrlUbicacion.getListaProvinciasByCodigoPais("Argentina"));
			comboBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					int idProvincia = comboBox.getSelectedIndex()+1;
					changeCiudades(idProvincia);
				}
			});
			break;
			
		case CBX_NACIONALIDAD:
			setConfigCbx(comboBox, ctrlUbicacion.getListPaisesOrdenadosAlfab());
			comboBox.setSelectedItem("Argentina");
			comboBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					paisChanged();
				}
			});
			break;
		}
		
		
	}
	
	private void inicializarTextField(JTextField txt, int opcion) {
		txt.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				txt.setBorder(new LineBorder(Color.WHITE));
			}
		});
		
		//txt.setBounds(0, 0, getWidth(), 550);
		txt.setForeground(Color.WHITE);
		txt.setFont(new Font("Tahoma", Font.PLAIN, 21));
		txt.setColumns(15);
		txt.setCaretColor(Color.WHITE);
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		txt.setBackground(ConfigGUI.COLOR_FONDO);
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		
		switch (opcion) {
		case 1:
			panelDatosOblig.add(txt);
			break;
			
		case 2:
			panelDatosNoOblig.add(txt);
			break;
			
		case 99:
			panelDatosOblig.add(txt);
			txt.setVisible(false);
			break;
		}
		
		txtDatos.add(txt);
	}
	
	@SuppressWarnings("deprecation")
	public boolean esDatosValido () {
		boolean esValido = true;
		
		
		for (int i=0; i < txtDatos.size(); i++) {
			String cadena = txtDatos.get(i).getText();
			
			switch (i) {
			case TXT_NOMBRE:
			case TXT_APELLIDO:
				//Valida el nombre y apellido, solo se admiten letras, letras con tilde, no se puede comenzar con un espacio y tener un tamaño de mas de 50 caracteres
				
				if (! cadena.matches("[aA-zZ, ñÑ, ,áÁ, éÉ, íÍ, óÓ, úÚ]+"))
					esValido = txtIncorrecto(txtDatos.get(i));
				else {
					
					Character primerCaracter = cadena.charAt(0);
					
					if (Character.isSpace(primerCaracter))
						esValido = txtIncorrecto(txtDatos.get(i));
					else {
						if (cadena.length() > 50)
							esValido = txtIncorrecto(txtDatos.get(i));
						else
							esValido = txtCorrecto(txtDatos.get(i));
					}
				}
						
				break;

			case TXT_DNI:
				//Valida el DNI, solo se admiten numeros y no mas de 10 caracteres
				
				if (cadena.matches("\\d*")) {
					
					if (cadena.length() > 10)
						esValido = txtIncorrecto(txtDatos.get(i));
					else {
						
						if (cadena.equals(""))
							esValido = txtIncorrecto(txtDatos.get(i));
						else
							esValido = txtCorrecto(txtDatos.get(i));
					}
				}
				else
					esValido = txtIncorrecto(txtDatos.get(i));
				
				break;
				
			case TXT_DOMICILIO:
				//Valida que no este en blanco y la cantidad maxima de caracteres
				
				if (!cadena.equals("") && !Character.isSpace(cadena.charAt(0)) && cadena.length() <= 60 )
					txtCorrecto(txtDatos.get(i));
				else
					esValido = txtIncorrecto(txtDatos.get(i));
					
				break;
				
			case TXT_CIUDAD:
			case TXT_PROVINCIA:
			//case TXT_NACIONALIDAD:
				//Valida que no este en blanco y la cantidad maxima de caracteres


				if (! cbxPaises.getSelectedItem().toString().toUpperCase().equals("ARGENTINA")) {

					//if (i != TXT_NACIONALIDAD) {
						if (!cadena.equals("") && !Character.isSpace(cadena.charAt(0)) && cadena.length() <= 30 )
							txtCorrecto(txtDatos.get(i));
						else
							esValido = txtIncorrecto(txtDatos.get(i));
					//}
				}

					
				break;
				
			case TXT_TELEFONO:
			case TXT_CELULAR:
			case TXT_EMAIL:
				
				//Valida que un mail sea verdadero
				
				if (!cadena.equals("")) {
					
					if (Character.isSpace(cadena.charAt(0))) {
						esValido = txtIncorrecto(txtDatos.get(i));
					}	
					else {
						
						if (i == TXT_TELEFONO || i == TXT_CELULAR) {
							if (cadena.length() > 30)
								esValido = txtIncorrecto(txtDatos.get(i));
							else {
								if (cadena.matches("\\d*"))
									txtCorrecto(txtDatos.get(i));
								else
									esValido = txtIncorrecto(txtDatos.get(i));
							}
						}
						else {
							if (cadena.length() > 70)
								esValido = txtIncorrecto(txtDatos.get(i));
							else {
								if (cadena.matches("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
									txtCorrecto(txtDatos.get(i));
								else
									esValido = txtIncorrecto(txtDatos.get(i));
							}
						}
					}
				}
				
				break;
			}
		}
		
		return esValido;
	}
	
	public Persona getDatosPersona() {

		String nombre = txtDatos.get(TXT_NOMBRE).getText();
		String apellido = txtDatos.get(TXT_APELLIDO).getText();
		BigInteger dni = new BigInteger(txtDatos.get(TXT_DNI).getText());
		String domicilio = txtDatos.get(TXT_DOMICILIO).getText();

		String ciudad = "";
		String provincia = "";
		if (cbxPaises.getSelectedItem().toString().toUpperCase().equals("ARGENTINA")) {
			ciudad = cbxCiudades.getSelectedItem().toString();
			provincia = cbxProvincias.getSelectedItem().toString();
		} else {
			ciudad = txtDatos.get(TXT_CIUDAD).getText();
			provincia = txtDatos.get(TXT_PROVINCIA).getText();
		}

		//String nacionalidad = txtDatos.get(TXT_NACIONALIDAD).getText();
		String nacionalidad = cbxPaises.getSelectedItem().toString();
		
		return new Persona(nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad);
	}
	
	
	public void setContactoPersona(Persona persona) {
		BigInteger telefono = null, celular=null;
		if (!txtDatos.get(TXT_TELEFONO).getText().equals("")) 
			telefono = new BigInteger(txtDatos.get(TXT_TELEFONO).getText());
		if (!txtDatos.get(TXT_CELULAR).getText().equals("")) 
			celular = new BigInteger(txtDatos.get(TXT_CELULAR).getText());
		String mail = txtDatos.get(TXT_EMAIL).getText();
		
		persona.setContacto(new ContactoPersona(telefono, celular, mail));
	}

	public JTextField getTextfieldEspecifico (int opcion) {
		return this.txtDatos.get(opcion);
	}
	
	public boolean txtCorrecto (JTextField txt) {
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		
		return true;
	}
	
	public boolean txtIncorrecto (JTextField txt) {
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		
		return false;
	}
	
	public void updatePanel(Persona p) {
		for (int i = 0; i < txtDatos.size(); i++) {
			if (i == TXT_NOMBRE)
				txtDatos.get(TXT_NOMBRE).setText(p.getNombre());
			if (i == TXT_APELLIDO)
				txtDatos.get(TXT_APELLIDO).setText(p.getApellido());
			if (i == TXT_DNI){
				txtDatos.get(TXT_DNI).setText(p.getDni().toString());
				txtDatos.get(TXT_DNI).setEditable(false);
			}

			if (i == TXT_DOMICILIO)
				txtDatos.get(TXT_DOMICILIO).setText(p.getDomicilio());

			if (i == TXT_CIUDAD && !p.getNacionalidad().toUpperCase().equals("ARGENTINA"))
				txtDatos.get(TXT_CIUDAD).setText(p.getCiudad());

			if (i == TXT_PROVINCIA && !p.getNacionalidad().toUpperCase().equals("ARGENTINA"))
				txtDatos.get(TXT_PROVINCIA).setText(p.getProvincia());
			//if (i == TXT_NACIONALIDAD)
				//txtDatos.get(TXT_NACIONALIDAD).setText(p.getNacionalidad());
			
			switch (i) {
			case TXT_TELEFONO:
				if (p.getContacto().getTelefono() != null)
					txtDatos.get(TXT_TELEFONO).setText(p.getContacto().getTelefono().toString());
				break;
				
			case TXT_CELULAR:
				if (p.getContacto().getCelular() != null)
					txtDatos.get(TXT_CELULAR).setText(p.getContacto().getCelular().toString());
				break;
				
			case TXT_EMAIL:
				if (p.getContacto().getMail() != null)
					txtDatos.get(TXT_EMAIL).setText(p.getContacto().getMail());
				break;
			}			
		}

		cbxPaises.setSelectedItem(p.getNacionalidad());

		if (p.getNacionalidad().toUpperCase().equals("ARGENTINA")) {
			cbxProvincias.setSelectedItem(p.getProvincia());
			cbxCiudades.setSelectedItem(p.getCiudad());
		}
	}
	
	public void setModificar(boolean modificar) {
		if (modificar)
			txtDatos.get(TXT_DNI).setEnabled(false);
		else
			txtDatos.get(TXT_DNI).setEnabled(true);
			
	}
	
	public JComboBox<String> getCbxPaises() {
		return cbxPaises;
	}

	public JComboBox<String> getCbxProvincias() {
		return cbxProvincias;
	}

	public JComboBox<String> getCbxCiudades() {
		return cbxCiudades;
	}

	/* Events */
	private void changeCiudades(int idProvincia) {
		CtrlUbicacion ctrlUbicacion = new CtrlUbicacion();
		cbxCiudades.setModel(new DefaultComboBoxModel<String>(ctrlUbicacion.getListaCiudadesByIdProvincia(idProvincia)));
	}
	
	private void paisChanged() {
		String pais = (String) cbxPaises.getSelectedItem();
		
		if (! pais.toUpperCase().equals("ARGENTINA")) {
			cbxCiudades.setVisible(false);
			cbxProvincias.setVisible(false);
			
			txtDatos.get(TXT_CIUDAD).setVisible(true);
			txtDatos.get(TXT_PROVINCIA).setVisible(true);
		}
		else {
			txtDatos.get(TXT_CIUDAD).setVisible(false);
			txtDatos.get(TXT_PROVINCIA).setVisible(false);
			
			cbxCiudades.setVisible(true);
			cbxProvincias.setVisible(true);
		}
	}

	
	/* Overrides */

	@Override
	protected void setConfigCbx(JComboBox<String> cbx, String[] modelCbx) {
		super.setConfigCbx(cbx, modelCbx);
		
		cbx.setForeground(Color.WHITE);
		cbx.setFont(new Font("Tahoma", Font.PLAIN, 21));
		cbx.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		cbx.setBackground(ConfigGUI.COLOR_FONDO);
		cbx.setPreferredSize(new Dimension(270, 35));
		
		panelDatosOblig.add(cbx);

	}
	
	
}
