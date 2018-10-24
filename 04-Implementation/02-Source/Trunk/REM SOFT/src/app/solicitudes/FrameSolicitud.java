package app.solicitudes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.toedter.calendar.JDateChooser;

import app.inmuebles.CtrlInmueble;
import app.ventas.FrameVenta;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import gui.FrameTablaPersonas;
import logica.Inmueble;
import logica.Persona;
import logica.Solicitud;
import logica.constant.ValidateConstants;

public class FrameSolicitud extends FrameDefault{


	private static final long serialVersionUID = 1L;
	
	
	/* Elements */
	private boolean isModificar;
	private CtrlSolicitudes ctrlSolicitudes = new CtrlSolicitudes();
	private CtrlInmueble ctrlInmueble=new CtrlInmueble();
	private Inmueble inmueble;
	private FrameCoincidencias frameCoincidencias;
	
	/* Panels */
	private JPanel panelHeader, panelSolicitante, panelNav, panelDynamic, panelSearch, panelFooter;
	private JPanel panelCarac, panelPrecio, panelDetalle;

	/* Panel Header */
	private JDateChooser dateChooserFecha;
	private JCheckBox checkBoxSolicitudAct;
	
	/* Panel Solicitante */
	private Persona solicitante;
	private JTextArea txtAreaSolicitante = new JTextArea("Nombre: \t ........................................ \n"
			+ "Direccion: \t ........................................ \n"
			+ "E-mail: \t ........................................ \n"
			+ "Telefono: \t ........................................ \n"
			+ "Celular: \t ........................................");
	private JButton btnSearch;
	
	/* Panel Footer */
	private ButtonDefaultABM btnCancelar, btnGuardar;
	
	/* Panel Caracteristicas */
	private JComboBox<String> cbxInmueble, cbxAlt1, cbxAlt2, cbxPais, cbxProvincia, cbxLocalidad, cbxOperacion;
	private String[] arrayTipoInmueble, arrayAlternativa1, arrayAlternativa2, arrayProvincia, arrayLocalidad;
	
	/* Panel Precio */
	private JCheckBox checkBoxRangoPrecio;
	private JTextField txtRangoDesde, txtRangoHasta;
	private JComboBox<String> cbxMoneda = new JComboBox<String>(new String[]{"ARS", "USD", "EUR", "BRL"});
	
	/* Panel Detalle */
	private JTextArea txtAreaDetalle;
	
	
	
	/* Constructor */
	public FrameSolicitud(boolean isModificar) {
		this.isModificar = isModificar;
		setTituloBarra(" REMSoft | Nueva Solicitud");
		setSizeFrame(700, 750);
		setLocationRelativeTo(null);
		
		initModelos();
		
		initPanelHeader();
		initPanelSolicitante();
		initPanelNavBar();
		initPanelDynamic();
		initPanelSearch();
		initPanelFooter();
	}
	
	
	/* Inits */
	private void initModelos() {
		arrayTipoInmueble = ctrlInmueble.getArrayTipoInmuebles();
		
		arrayAlternativa1 = new String[arrayTipoInmueble.length+1];
		arrayAlternativa2 = new String[arrayTipoInmueble.length+1];
		arrayLocalidad = ctrlInmueble.getLocalidadesByIdUbicacion();
		
		
		for (int i=-1; i < arrayAlternativa1.length-1; i++) {
			if (i==-1) {
				arrayAlternativa1[0] = "";
				arrayAlternativa2[0] = "";
			}
			else {
				arrayAlternativa1[i+1] = arrayTipoInmueble[i];
				arrayAlternativa2[i+1] = arrayTipoInmueble[i];
			}
		}
	}
	
	private void initPanelHeader() {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		panelHeader.setBounds(10, 30, getWidth()-20, 75);
		
		JLabel lblFecha = new JLabel("Fecha: ");
		setLabelDefault(lblFecha);
		
		dateChooserFecha = new JDateChooser(new Date());
		dateChooserFecha.setPreferredSize(new Dimension(150, 25));
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(230, 25));
		
		checkBoxSolicitudAct = new JCheckBox(" Solicitud ACTIVA");
		checkBoxSolicitudAct.setOpaque(false);
		checkBoxSolicitudAct.setForeground(Color.white);
		checkBoxSolicitudAct.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		panelHeader.add(lblFecha);
		panelHeader.add(dateChooserFecha);
		panelHeader.add(lblInvisible);
		panelHeader.add(checkBoxSolicitudAct);
		
		JLabel lblSolicitante = new JLabel("  Solicitante  ");
		
		setLabelDefault(lblSolicitante);
		lblSolicitante.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblSolicitante.setBorder(new LineBorder(Color.white));
		lblSolicitante.setPreferredSize(new Dimension(panelHeader.getWidth()-20, 30));
		lblSolicitante.setHorizontalAlignment(SwingConstants.RIGHT);
		panelHeader.add(lblSolicitante);
		add(panelHeader);
	}
	
	private void initPanelSolicitante() {
		panelSolicitante = new JPanel();
		panelSolicitante.setBackground(ConfigGUI.COLOR_FONDO);
		panelSolicitante.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		panelSolicitante.setBounds(10, panelHeader.getHeight()+panelHeader.getY(), getWidth()-20, 150);
		panelSolicitante.setBorder(new LineBorder(Color.white));
		
		btnSearch = new JButton();
		setConfigButtonSearch(panelSolicitante, btnSearch, -1);
		
		txtAreaSolicitante.setOpaque(false);
		txtAreaSolicitante.setEditable(false);
		txtAreaSolicitante.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAreaSolicitante.setForeground(Color.WHITE);
		
		panelSolicitante.add(txtAreaSolicitante);

		
		add(panelSolicitante);
	}
	
	private void initPanelNavBar() {
		panelNav = new JPanel();
		panelNav.setBackground(ConfigGUI.COLOR_FONDO);
		panelNav.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 10));
		panelNav.setBounds(10, panelSolicitante.getHeight()+panelSolicitante.getY()+10, getWidth()-20, 50);
		panelNav.setBorder(new LineBorder(Color.white));
		
		addLabelToPanelNav(panelNav, new JLabel("CARACTERISTICAS"));
		addLabelToPanelNav(panelNav, new JLabel("PRECIOS"));
		addLabelToPanelNav(panelNav, new JLabel("DETALLES"));
		
		add(panelNav);
	}
	
	private void initPanelDynamic() {
		panelDynamic = new JPanel();
		panelDynamic.setBackground(ConfigGUI.COLOR_FONDO);
		panelDynamic.setLayout(null);
		panelDynamic.setBounds(10, panelNav.getHeight()+panelNav.getY(), getWidth()-20, 310);
		
		initPanelCarac();
		initPanelPrecio();
		initPanelDetalle();
		
		add(panelDynamic);
	}
	
	private void initPanelSearch() {
		panelSearch = new JPanel();
		panelSearch.setBackground(ConfigGUI.COLOR_FONDO);
		panelSearch.setLayout(new FlowLayout(FlowLayout.RIGHT, 35, 10));
		panelSearch.setBounds(10, panelDynamic.getHeight()+panelDynamic.getY(), getWidth()-20, 50);
		
		setConfigButtonSearch(panelSearch, new JButton(), 1);
		
		add(panelSearch);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 15));
		panelFooter.setBounds(10, panelSearch.getHeight()+panelSearch.getY(), getWidth()-20, 70);
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, Color.white));
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");
		
		btnGuardar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eventoAceptar();
			}
		});
		btnGuardar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnGuardar.setText("Guardar");
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnGuardar);
		add(panelFooter);
	}
	
	private void initPanelCarac() {
		panelCarac = new JPanel();
		panelCarac.setBackground(ConfigGUI.COLOR_FONDO);
		panelCarac.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		panelCarac.setBounds(120, 0, getWidth()-250, panelDynamic.getHeight());
		
		addCajaLblAndCbx(panelCarac, new JLabel("Tipo de Inmueble:"), cbxInmueble = new JComboBox<String>(), arrayTipoInmueble);
		cbxInmueble.setSelectedItem(1);
		addCajaLblAndCbx(panelCarac, new JLabel("Alternativa 1:"), cbxAlt1 = new JComboBox<String>(), arrayAlternativa1);
		//addCajaLblAndCbx(panelCarac, new JLabel("Alternativa 2:"), cbxInmueble = new JComboBox<String>(), arrayAlternativa2);
		addCajaLblAndCbx(panelCarac, new JLabel("Provincia:"), cbxProvincia = new JComboBox<String>(), new String[]{"TODAS", "Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero", "Tierra del Fuego", "Tucumán"});
		addCajaLblAndCbx(panelCarac, new JLabel("Localidad:"), cbxLocalidad = new JComboBox<String>(), arrayLocalidad);
		addCajaLblAndCbx(panelCarac, new JLabel("Operacion:"), cbxOperacion = new JComboBox<String>(), new String[]{"TODAS","Alquiler", "Venta"});
		
		panelDynamic.add(panelCarac);
	}
	
	private void initPanelPrecio() {
		panelPrecio = new JPanel();
		panelPrecio.setBackground(ConfigGUI.COLOR_FONDO);
		panelPrecio.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		panelPrecio.setBounds(120, 50, getWidth()-250, panelDynamic.getHeight()-100);
		panelPrecio.setVisible(false);
		
		checkBoxRangoPrecio = new JCheckBox("Indicar Rango de Precios");
		checkBoxRangoPrecio.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (checkBoxRangoPrecio.isSelected()) {
					txtRangoDesde.setEnabled(true);
					txtRangoHasta.setEnabled(true);
					cbxMoneda.setEnabled(true);
				}
			}
		});
		checkBoxRangoPrecio.setOpaque(false);
		checkBoxRangoPrecio.setForeground(Color.white);
		checkBoxRangoPrecio.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxRangoPrecio.setPreferredSize(new Dimension(250, 35));
		
		JLabel lblDesde = new JLabel("Precio desde: ");
		setLabelDefault(lblDesde);
		setTextfieldDefault(txtRangoDesde = new JTextField());
		
		JLabel lblHasta = new JLabel("Precio hasta: ");
		setLabelDefault(lblHasta);
		setTextfieldDefault(txtRangoHasta = new JTextField());
		
		panelPrecio.add(checkBoxRangoPrecio);
		addCaja(panelPrecio, lblDesde, txtRangoDesde);
		addCaja(panelPrecio, lblHasta, txtRangoHasta);
		JLabel lblMoneda = new JLabel("Moneda: ");
		addCajaLblAndCbx(panelPrecio, lblMoneda, cbxMoneda, new String[]{"ARS", "USD", "EUR", "BRL"});
		lblMoneda.setPreferredSize(new Dimension(200, 35));
		cbxMoneda.setPreferredSize(new Dimension(200, 35));
		
		txtRangoDesde.setEnabled(false);
		txtRangoHasta.setEnabled(false);
		cbxMoneda.setEnabled(false);
		
		panelDynamic.add(panelPrecio);
		
	}
	
	private void initPanelDetalle() {
		panelDetalle = new JPanel();
		panelDetalle.setLayout(new BorderLayout());
		panelDetalle.setBounds(0, 0, panelDynamic.getWidth(), panelDynamic.getHeight());
		panelDetalle.setVisible(false);
		
		txtAreaDetalle = new JTextArea();
		txtAreaDetalle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtAreaDetalle.setLineWrap(true);
		
		panelDetalle.add(txtAreaDetalle, BorderLayout.CENTER);
		
		panelDynamic.add(panelDetalle);
	}
	
	/* Configs */
	
	private void addCaja(JPanel panel, JLabel lbl, JTextField txt) {
		setLabelDefault(lbl);
		lbl.setPreferredSize(new Dimension(200, 35));
		setTextfieldDefault(txt);
		txt.setPreferredSize(new Dimension(200, 35));
		panel.add(lbl);
		panel.add(txt);
	}
	
	private void setConfigButtonSearch(JPanel panel, JButton btn, int option) {
		btn = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		
		if (option != 1) {
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setEnabled(false);
					FrameTablaPersonas frameTablaPersonas=new FrameTablaPersonas();
					frameTablaPersonas.setVisible(true);
					
				}
			});
		}
		
		btn.setPreferredSize(new Dimension(55, 35));
		btn.setBackground(new Color(78, 78, 78));
		btn.setVerticalTextPosition(SwingConstants.CENTER);
		btn.setIcon(new ImageIcon(FrameVenta.class.getResource("/images/search-24x24.png")));
		
		if (option == 1) {
			btn.setText("Buscar inmuebles con coincidencias");
			btn.setPreferredSize(new Dimension(350, 35));
			btn.setHorizontalTextPosition(SwingConstants.RIGHT);
			
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarRangoDePrecios()) {
						List<Inmueble> inmuebles = generarListInmuebles();
						
						frameCoincidencias = new FrameCoincidencias(inmuebles);
						setEnabled(false);
						frameCoincidencias.setVisible(true);
					}
				}
			});
		}
		
		panel.add(btn);
	}
	
	private void addLabelToPanelNav(JPanel panel, JLabel lbl) {
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		//events
				lbl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						lbl.setForeground(Color.WHITE);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// Manejar las visibilidades
						
						if (lbl.getText().equals("CARACTERISTICAS")) {
							panelDynamic.getComponent(0).setVisible(true);
							panelDynamic.getComponent(1).setVisible(false);
							panelDynamic.getComponent(2).setVisible(false);
						}
						
						if (lbl.getText().equals("PRECIOS")) {
							panelDynamic.getComponent(0).setVisible(false);
							panelDynamic.getComponent(1).setVisible(true);
							panelDynamic.getComponent(2).setVisible(false);
						}
						
						if (lbl.getText().equals("DETALLES")) {
							panelDynamic.getComponent(0).setVisible(false);
							panelDynamic.getComponent(1).setVisible(false);
							panelDynamic.getComponent(2).setVisible(true);
						}
					}
				});
		
		panel.add(lbl);
	}
	
	private void addCajaLblAndCbx(JPanel panel, JLabel label, JComboBox<String> cbx, String[] modelCbx) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setPreferredSize(new Dimension(150, 35));
		
		setConfigCbx(cbx, modelCbx);
		
		panel.add(label);
		panel.add(cbx);
	}
	
	private void setConfigCbx(JComboBox<String> cbx, String[] modelCbx) {
		cbx.setModel(new DefaultComboBoxModel<String>(modelCbx));
		cbx.setUI(new BasicComboBoxUI() {
		    @Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setBorder(new LineBorder(Color.WHITE));
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
	
	
	/* Functions */
	public void setDatosSolicitante(Persona p) {
		this.solicitante = p;
		String mail = "........................................";
		String telefono = "........................................";
		String celular = "........................................";
		
		if (p.getContacto().getMail() != null)
			mail = p.getContacto().getMail();
		if (p.getContacto().getTelefono() != null)
			telefono = p.getContacto().getTelefono().toString();
		if (p.getContacto().getCelular() != null)
			celular = p.getContacto().getCelular().toString();
		
		txtAreaSolicitante.setText("Nombre: \t "+p.getApellido()+", "+p.getNombre()+" \n"
				+ "Direccion: \t "+p.getDomicilio()+" \n"
				+ "E-mail: \t "+mail+" \n"
				+ "Telefono: \t "+telefono+" \n"
				+ "Celular: \t "+celular);
	}
	
	private boolean validarRangoDePrecios() {
		boolean esValido = true;
		
		if (checkBoxRangoPrecio.isSelected()) {
			BigDecimal rangoDesde = null, rangoHasta = null;
			
			if (ValidateConstants.validateMoney(txtRangoDesde.getText())) {
				rangoDesde = new BigDecimal(txtRangoDesde.getText());
				txtRangoDesde.setBorder(new LineBorder(Color.white));
			}
			else {
				txtRangoDesde.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
				esValido = false;
			}
			
			if (ValidateConstants.validateMoney(txtRangoHasta.getText())) {
				rangoHasta = new BigDecimal(txtRangoHasta.getText());
				txtRangoHasta.setBorder(new LineBorder(Color.white));
			}
			else {
				txtRangoHasta.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
				esValido = false;
			}
			
			if (esValido) {
				
				if (rangoDesde.compareTo(rangoHasta) == 1) {
					JOptionPane.showMessageDialog(null, "El rango Desde no puede ser mayor que el rango Hasta");
					esValido = false;
				}
			}
		}
		
		if (!esValido)
			((JLabel)panelNav.getComponent(1)).setForeground(ConfigGUI.COLOR_ERROR);
		
		return esValido;
	}
	
	private boolean validateSave() {
		boolean isValido = true;
		
		if (solicitante == null) {
			isValido = false;
			JOptionPane.showMessageDialog(null, "No agrego ningun Solicitante.");
		}
		
		if (!validarRangoDePrecios())
			isValido = false;
		
		return isValido;
	}
	
	private List<Inmueble> generarListInmuebles() {
		Integer tipoInmueble = cbxInmueble.getSelectedIndex()+1;
		Integer alternativa = cbxAlt1.getSelectedIndex();
		
		String provincia = null;
		if (cbxProvincia.getSelectedIndex() != 0)
			provincia = (String) cbxProvincia.getSelectedItem();
		
		String localidad = null;
		if (cbxLocalidad.getSelectedIndex() != 0)
			localidad = (String) cbxLocalidad.getSelectedItem();
		
		Integer operacion = cbxOperacion.getSelectedIndex();
		if (operacion == 0)
			operacion = null;
		
		
		if (alternativa == 0) { // BUSQUEDA BASICA
			
			if (provincia == null && localidad == null && operacion == null)
				return ctrlSolicitudes.getBasico(tipoInmueble);
			else if (provincia != null && localidad == null && operacion == null)
				return ctrlSolicitudes.getBasicoWithProvincia(tipoInmueble, provincia);
			else if (provincia == null && localidad != null && operacion == null)
				return ctrlSolicitudes.getBasicoWithCiudad(tipoInmueble, localidad);
			else if (provincia == null && localidad == null && operacion != null)
				return ctrlSolicitudes.getBasicoWithOperacion(tipoInmueble, operacion);
			else if (provincia != null && localidad != null && operacion == null)
				return ctrlSolicitudes.getBsicoWithPronviciaAndCiudad(tipoInmueble, provincia, localidad);
			else if (provincia != null && localidad == null && operacion != null)
				return ctrlSolicitudes.getBasicoWithPronviciaAndOperacion(tipoInmueble, provincia, operacion);
			else if (provincia == null && localidad != null && operacion != null)
				return ctrlSolicitudes.getBasicoWithCiudadAndOperacion(tipoInmueble, localidad, operacion);
			else
				return ctrlSolicitudes.getBasicoWithAll(tipoInmueble, provincia, localidad, operacion);
			
		}
		else { // BUSQUEDA CON ALTERNATIVA
			if (provincia == null && localidad == null && operacion == null)
				return ctrlSolicitudes.getDosAlt(tipoInmueble, alternativa);
			else if (provincia != null && localidad == null && operacion == null)
				return ctrlSolicitudes.getDosAltWithProvincia(tipoInmueble, alternativa, provincia);
			else if (provincia == null && localidad != null && operacion == null)
				return ctrlSolicitudes.getDosAltWithCiudad(tipoInmueble, alternativa, localidad);
			else if (provincia == null && localidad == null && operacion != null)
				return ctrlSolicitudes.getDosAltWithOperacion(tipoInmueble, alternativa, operacion);
			else if (provincia != null && localidad != null && operacion == null)
				return ctrlSolicitudes.getDosAltWithProvinciaAndCiudad(tipoInmueble, alternativa, provincia, localidad);
			else if (provincia != null && localidad == null && operacion != null)
				return ctrlSolicitudes.getDosAltWithProvinciaAndOperacion(tipoInmueble, alternativa, provincia, operacion);
			else if (provincia == null && localidad != null && operacion != null)
				return ctrlSolicitudes.getDosAltWithCiudadAndOperacion(tipoInmueble, alternativa, localidad, operacion);
			else
				return ctrlSolicitudes.getDosAltWithAll(tipoInmueble, alternativa, provincia, localidad, operacion);
		}
		
	}
	
	/* Events */
	@Override
	protected void eventoCerrar() {
		dispose();
		FramePpal.getFrameppal().setVisible(true);
		FramePpal.getFrameppal().setEnabled(true);
	}
	
	private void eventoAceptar() {
		if (validateSave()) {
			Solicitud solicitud = new Solicitud(null, dateChooserFecha.getDate(), checkBoxSolicitudAct.isSelected(), solicitante, (String)cbxInmueble.getSelectedItem(), (String)cbxAlt1.getSelectedItem(), (String)cbxProvincia.getSelectedItem(), (String)cbxLocalidad.getSelectedItem(), cbxOperacion.getSelectedIndex(), null, null, null, txtAreaDetalle.getText(), inmueble);
			((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).getModeloTabla().agregar(solicitud);
			eventoCerrar();
		}
		else {
			// Informar el error de que no se pudo agregar
		}
	}


	/* Getters & Setters */

	public Inmueble getInmueble() {
		return inmueble;
	}


	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}


	public FrameCoincidencias getFrameCoincidencias() {
		return frameCoincidencias;
	}
	
}
