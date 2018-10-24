package app.alquileres;


import java.awt.Color;
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
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;


import app.alquileres.paneles.PanelContratoAlq;
import app.alquileres.paneles.PanelGarantiaAlq;
import app.alquileres.paneles.PanelObservacionesAlq;
import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import gui.FrameTablaInmuebles;
import gui.FrameTablaPersonas;
import logica.Alquiler;
import logica.Inmueble;
import logica.PagoAlquiler;
import logica.Persona;
import logica.constant.ValidateConstants;
import util.Utils;


public class FrameAlquiler extends FrameDefault {

	private static final long serialVersionUID = 1L;
	
	/* Constants */
	public static final int WIDTH_PANEL_DYNAMIC = 1240;
	public static final int HEIGHT_PANEL_DYNAMIC = 246;

	/* Elements */
	private Integer idAlquiler;
	private Alquiler alquilerModify;
	private Integer filaSelecc;
	
	private boolean isModificar, isInquilino = false;
	
	private JPanel panelLocador, panelLocatario, panelNav, panelDynamic, panelFooter;
	private JButton btnClear;
	
	private CtrlInmueble ctrlInmueble = new CtrlInmueble();
	private CtrlAlquileres ctrlAlquileres = new CtrlAlquileres();
	
	private Persona propietario, inquilino, garante;
	private Inmueble inmueble;
	private String total;
	
	/* Panel NavBar */
	private JLabel lblGarantiaNav;
	
	/* Panel Dynamic */
	private PanelGarantiaAlq panelGarantiaAlq;
	private PanelContratoAlq panelContratoAlq;
	private PanelObservacionesAlq panelObservacionesAlq;
	
	/* Panel Locador*/
	JButton btnSearchInmueble = null, btnSearchPersona = null;
	JTextField txtInmueble, txtInmDireccion, txtInmUbicacion, txtInmPropietario;
	
	/* Panel Locatario */
	JTextField txtInquilino, txtNombre, txtDireccion, txtContacto;
	
	public FrameAlquiler(boolean isModificar) {
		this.isModificar = isModificar;
		
		// Config FRAME
		
		setTituloBarra("   Contrato de Alquiler - REM Soft");

		setSizeFrame(1280, 700);
		setLocationRelativeTo(null);
		
		// Inits
		initPanelLocador();
		initPanelLocatario();
		initPanelNav();
		initPanelDynamic();
		initPanelFooter();
		
		setConfigBtnClear();
		
		if (isModificar) {
			configIsModificar();
		}
	}
	
	private void configIsModificar() {
		setTituloBarra("   REMSOFT ~ Modificar Contrato de Alquiler");
		
		filaSelecc = ((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFilaSeleccionada();
		Alquiler alquiler = ((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getListAlquiler().get(filaSelecc);
		this.alquilerModify = alquiler;
		
		completarLocador(alquiler);
		completarLocatario(alquiler);
		completarContrato(alquiler);
		completarGarantia(alquiler);
		
		if (alquiler != null)
			panelObservacionesAlq.getTextArea().setText(alquiler.getObservaciones());

		
		btnSearchInmueble.setEnabled(false);
		btnSearchPersona.setEnabled(false);
	}
	
	private void setConfigBtnClear() {
		btnClear = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnClear = new JButton("Limpiar");
		btnClear.setIcon(new ImageIcon(FrameAlquiler.class.getResource("/images/clear64x64.png")));
		btnClear.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnClear.setVerticalAlignment(SwingConstants.TOP);
		btnClear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClear.setFocusable(false);
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		btnClear.setBounds((getWidth()/2)-64, (panelLocatario.getHeight()/2), 128, 96);
		
		// events
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i < panelLocador.getComponents().length; i++) {
					if (panelLocador.getComponent(i).getClass().getSimpleName().equals("JTextField"))
						((JTextField)panelLocador.getComponent(i)).setText("");
				}
				
				for (int i=0; i < panelLocatario.getComponents().length; i++) {
					if (panelLocatario.getComponent(i).getClass().getSimpleName().equals("JTextField"))
						((JTextField)panelLocatario.getComponent(i)).setText("");
				}
			}
		});
		
		getContentPane().add(btnClear);
	}
	
	private void initPanelLocador() {
		panelLocador = new JPanel();
		panelLocador.setBackground(ConfigGUI.COLOR_FONDO);
		panelLocador.setBorder(new LineBorder(Color.white));
		int panelLocadorWidth = this.getWidth()/4;

		panelLocadorWidth += panelLocadorWidth/2;
		panelLocador.setBounds(20, 42, panelLocadorWidth+50, (this.getHeight()/2)-94);
		panelLocador.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 15));
		
		JLabel lblUbicacion = new JLabel("Datos del Locador");
		lblUbicacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblUbicacion.setForeground(Color.WHITE);
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUbicacion.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelLocador.add(lblUbicacion);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(250, 30));
		panelLocador.add(lblInvisible);
		
		addCajaDeDatos(panelLocador, new JLabel("Inmueble:"), txtInmueble=new JTextField());
		addCajaDeDatos(panelLocador, new JLabel("Direccion:"), txtInmDireccion=new JTextField("..."));
		addCajaDeDatos(panelLocador, new JLabel("Ubicacion:"), txtInmUbicacion=new JTextField("..."));
		addCajaDeDatos(panelLocador, new JLabel("Propietario:"), txtInmPropietario=new JTextField("..."));
		
		
		getContentPane().add(panelLocador);
	}
	
	private void initPanelLocatario() {
		panelLocatario = new JPanel();
		panelLocatario.setBackground(ConfigGUI.COLOR_FONDO);
		panelLocatario.setBorder(new LineBorder(Color.WHITE));
		int panelLocadorWidth = this.getWidth()/4;

		panelLocadorWidth += panelLocadorWidth/2;
		panelLocatario.setBounds(800-50-20, 42, panelLocadorWidth+50, (this.getHeight()/2)-94);
		panelLocatario.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 15));
		
		JLabel lblLocatario = new JLabel("Datos del Locatario");
		lblLocatario.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocatario.setForeground(Color.WHITE);
		lblLocatario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLocatario.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelLocatario.add(lblLocatario);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(250, 30));
		panelLocatario.add(lblInvisible);
		
		addCajaDeDatos(panelLocatario, new JLabel("Inquilino:"), txtInquilino=new JTextField());
		addCajaDeDatos(panelLocatario, new JLabel("Nombre:"), txtNombre=new JTextField("..."));
		addCajaDeDatos(panelLocatario, new JLabel("Direccion:"), txtDireccion=new JTextField("..."));
		addCajaDeDatos(panelLocatario, new JLabel("Contacto:"), txtContacto=new JTextField("..."));
		
		getContentPane().add(panelLocatario);
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
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEnabled(false);
		
		if (textField.getText().equals("...")){
			textField.setPreferredSize(new Dimension(400, 35));
		}
		else {
			textField.setPreferredSize(new Dimension(230, 35));
			JButton btn = null;
			if (btnSearchInmueble == null) {
				btnSearchInmueble = new JButton("Buscar Inmueble");
				btnSearchInmueble.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						eventoBuscarInmueble();
					}
				});
				
				btn = btnSearchInmueble;
			}
			else {
				btnSearchPersona = new JButton("Buscar Persona");
				btnSearchPersona.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						eventoBuscarPersona();
					}
				});
				btn = btnSearchPersona;
			}
			
			btn.setPreferredSize(new Dimension(150, 35));
			panel.add(textField);
			panel.add(btn);
			return;
		}
		
		
		panel.add(textField);
		
	}


	private void initPanelNav() {
		panelNav = new JPanel();
		panelNav.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 15));
		panelNav.setBorder(new LineBorder(Color.white));
		panelNav.setBackground(ConfigGUI.COLOR_FONDO);
		panelNav.setBounds(20, panelLocador.getHeight()+panelLocador.getY()+32, getWidth()-40, 64);
		
		addLabelToNav(new JLabel("DETALLES DEL CONTRATO"));
		addLabelToNav(lblGarantiaNav=new JLabel("GARANTIA"));
		addLabelToNav(new JLabel("OBSERVACIONES"));
		
		getContentPane().add(panelNav);
	}
	
	private void addLabelToNav(JLabel lbl) {
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
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
				
				if (lbl.getText().equals("DETALLES DEL CONTRATO")) {
					panelDynamic.getComponent(0).setVisible(true);
					panelDynamic.getComponent(1).setVisible(false);
					panelDynamic.getComponent(2).setVisible(false);
				}
				
				if (lbl.getText().equals("GARANTIA")) {
					panelDynamic.getComponent(0).setVisible(false);
					panelDynamic.getComponent(1).setVisible(true);
					panelDynamic.getComponent(2).setVisible(false);
				}
				
				if (lbl.getText().equals("OBSERVACIONES")) {
					panelDynamic.getComponent(0).setVisible(false);
					panelDynamic.getComponent(1).setVisible(false);
					panelDynamic.getComponent(2).setVisible(true);
				}
			}
		});
		
		panelNav.add(lbl);
	}
	
	private void initPanelDynamic() {
		panelDynamic = new JPanel();
		panelDynamic.setBackground(ConfigGUI.COLOR_FONDO);
		panelDynamic.setLayout(null);
		panelDynamic.setBounds(20, panelNav.getHeight()+panelNav.getY(), WIDTH_PANEL_DYNAMIC, HEIGHT_PANEL_DYNAMIC);
		
		panelContratoAlq = new PanelContratoAlq();
		panelContratoAlq.setVisible(true);
		
		panelGarantiaAlq = new PanelGarantiaAlq();
		panelGarantiaAlq.setVisible(false);
		
		panelObservacionesAlq = new PanelObservacionesAlq();
		panelObservacionesAlq.setVisible(false);
		
		panelDynamic.add(panelContratoAlq);
		panelDynamic.add(panelGarantiaAlq);
		panelDynamic.add(panelObservacionesAlq);
		getContentPane().add(panelDynamic);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setBorder(new MatteBorder(0, 0, 2, 0, Color.cyan));
		panelFooter.setLayout(null);
		panelFooter.setBounds(20, 640, getWidth()-40, 60);
		
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (! isModificar) {
					if (eventoAceptar()) {
						idAlquiler = createAlquiler().getIdAlquiler();
					}
				}
				else {
					if (eventoAceptar()) {
						alquilerModify.setMesesDeposito((Integer)panelContratoAlq.getSpinMesesDeposito().getValue());
						
						if (panelContratoAlq.getTxtPrecioDesposito().getText().equals(""))
							alquilerModify.setPrecioDeposito(new BigDecimal(00.00));
						else
							alquilerModify.setPrecioDeposito(new BigDecimal(panelContratoAlq.getTxtPrecioDesposito().getText()));
						alquilerModify.setPorcentajeMora(new BigDecimal(panelContratoAlq.getTxtMora().getText()));
						//alquilerModify.setGarante(garante);
						alquilerModify.setObservaciones(panelObservacionesAlq.getTextArea().getText());
						
						((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES))
							.getModeloTabla().modificarObjeto(filaSelecc, alquilerModify);
						
						eventoCerrar();
					}
				}
				
			}
		});
		btnAceptar.setHorizontalTextPosition(SwingConstants.LEADING);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAceptar.setFocusable(false);
		btnAceptar.setBorderPainted(false);
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAceptar.setBounds((panelFooter.getWidth()/2)+10, panelFooter.getHeight()/8, 200, 40);
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCancelar();
			}
		});
		btnCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setFocusable(false);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setBounds((panelFooter.getWidth()/2)-10-btnAceptar.getWidth(), panelFooter.getHeight()/8, 200, 40);
		
		panelFooter.add(btnAceptar);
		panelFooter.add(btnCancelar);
		
		getContentPane().add(panelFooter);
	}
	
	/* Functions */
	
	public void setDatosInquilino(Persona p) {
		this.inquilino = p;
		txtInquilino.setText(p.getDni().toString());
		txtInquilino.setBorder(new LineBorder(Color.white));
		txtNombre.setText(p.getApellido() + ", " + p.getNombre());
		txtDireccion.setText(p.getDomicilio() + ", " + p.getCiudad() + ", " + p.getProvincia());
		
		if (p.getContacto().getCelular() != null)
			txtContacto.setText(p.getContacto().getCelular().toString());
		else if (p.getContacto().getTelefono() != null)
			txtContacto.setText(p.getContacto().getTelefono().toString());
		else if (p.getContacto().getMail() != null)
			txtContacto.setText(p.getContacto().getMail());
	}
	
	public void setDatosLocador(Inmueble inm) {
		this.inmueble = inm;
		this.propietario = inm.getPropietario();
		
		Integer id = Integer.decode(inm.getTipoInmueble());
		String tipoInmueble = ctrlInmueble.getTipoInmueble(id);
		txtInmueble.setText(tipoInmueble);
		txtInmueble.setBorder(new LineBorder(Color.white));
		txtInmDireccion.setText(inm.getUbicacion().getCalle() + " " + inm.getUbicacion().getNumero());
		txtInmUbicacion.setText(inm.getUbicacion().getCiudad() + ", " + inm.getUbicacion().getProvincia() + ", " + inm.getUbicacion().getPais());
		txtInmPropietario.setText(inm.getPropietario().getApellido() + " " + inm.getPropietario().getNombre() + " (" + inm.getPropietario().getDni() + ")");
		
		for (int i=0; i < inm.getOperaciones().size(); i++) {
			if (inm.getOperaciones().get(i).getTipoOperacion().equals("1")) {
				DecimalFormat decimalFormat = new DecimalFormat("#.00");
				this.total = decimalFormat.format(inm.getOperaciones().get(i).getPrecio());
				panelContratoAlq.getTxtMontoTotal().setText("$ " + total);
			}
		}
		
	}
	
	private Alquiler createAlquiler() {
		this.garante = panelGarantiaAlq.getGarante();
		
		BigDecimal montoTotal = new BigDecimal(total.replace(',', '.'));
		Date fechaInicio = panelContratoAlq.getDateChooserInicioContrato().getDate();
		Date fechaFin = panelContratoAlq.getDateChooserFinContrato().getDate();
		
		BigDecimal porcentajeMora = null;
		if (panelContratoAlq.getTxtMora().getText().equals(""))
			porcentajeMora = new BigDecimal("00.00");
		else {
			porcentajeMora = new BigDecimal(panelContratoAlq.getTxtMora().getText());
		}
		
		Integer diaVto = (Integer) panelContratoAlq.getSpinDiaVto().getValue();
		
		Integer mesesDeposito = (Integer) panelContratoAlq.getSpinMesesDeposito().getValue();
		
		BigDecimal precioDeposito = null;
		if (! ValidateConstants.isTextEmpty(panelContratoAlq.getTxtPrecioDesposito().getText()))
			precioDeposito = new BigDecimal(panelContratoAlq.getTxtPrecioDesposito().getText());
		
		String observaciones = null;
		if (! ValidateConstants.isTextEmpty(panelObservacionesAlq.getTextArea().getText()))
			observaciones = panelObservacionesAlq.getTextArea().getText();
		
		Alquiler alquiler = new Alquiler(inmueble, inquilino, garante, montoTotal, fechaInicio, fechaFin, porcentajeMora, diaVto, PagoAlquiler.ESTADO_PAGO[1], Alquiler.ESTADO_ALQUILER[0]);
		alquiler.setMesesDeposito(mesesDeposito);
		alquiler.setPrecioDeposito(precioDeposito);
		alquiler.setObservaciones(observaciones);
		
		if (!isModificar)
			alquiler.generarPagos();
		
		Integer idContrato = null;
		if (!isModificar)
			idContrato = ctrlAlquileres.insertIntoNuevoAlquiler(alquiler);
		
		for (int i=0; i < alquiler.getListPagos().size(); i++) {
			PagoAlquiler pagoAlquiler = alquiler.getListPagos().get(i);
			ctrlAlquileres.insertIntoPagoAlquiler(pagoAlquiler, idContrato);
		}

		
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getModeloTabla().agregar(alquiler);
		((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getListAlquiler().add(alquiler);
		eventoCerrar();
		
		return alquiler;
	}

	
	/* Events */
	
	private void eventoBuscarPersona() {
		this.isInquilino = true;
		FrameTablaPersonas frameTablaPersonas = new FrameTablaPersonas();
		this.setEnabled(false);
		frameTablaPersonas.setVisible(true);
	}
	
	private void eventoBuscarInmueble() {
		FrameTablaInmuebles frameTablaInmuebles = new FrameTablaInmuebles();
		this.setEnabled(false);
		frameTablaInmuebles.setVisible(true);
	}
	
	private boolean eventoAceptar() {
		boolean valido = true;
		
		if (txtInmueble.getText().equals("")) {
			txtInmueble.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			valido = false;
		}
		
		if (txtInquilino.getText().equals("")) {
			txtInquilino.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			valido = false;
		}
		
		if (panelGarantiaAlq.getTxtGarante().getText().equals("")) {
			panelGarantiaAlq.getTxtGarante().setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			lblGarantiaNav.setForeground(ConfigGUI.COLOR_ERROR);
			valido = false;
		}
		
		// Validar que no sean el mismo Inquilino que Propietario
		
		if (inquilino != null && propietario != null) {
			if (inquilino.getDni().equals(propietario.getDni())) {
				JOptionPane.showMessageDialog(null, "El Propietario no puede ser la misma persona que el Inquilino.", "ERROR", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		
		// Validar Detalles del Contrato
		
			// Fechas
		if (((JTextField) panelContratoAlq.getDateChooserInicioContrato().getDateEditor().getUiComponent()).getText().equals("") ||
				((JTextField) panelContratoAlq.getDateChooserInicioContrato().getDateEditor().getUiComponent()).getForeground().equals(Color.red)) {
			((JTextField) panelContratoAlq.getDateChooserInicioContrato().getDateEditor().getUiComponent()).setText("ERROR");
			valido = false;
		}
		
		if (((JTextField) panelContratoAlq.getDateChooserFinContrato().getDateEditor().getUiComponent()).getText().equals("") ||
				((JTextField) panelContratoAlq.getDateChooserFinContrato().getDateEditor().getUiComponent()).getForeground().equals(Color.red)) {
			((JTextField) panelContratoAlq.getDateChooserFinContrato().getDateEditor().getUiComponent()).setText("ERROR");
			valido = false;
		}
		
		panelContratoAlq.calcularDuracion();
		
		if (valido != false) {
			if (panelContratoAlq.getTxtCantDias().getText().equals("") || panelContratoAlq.getTxtCantDias().getText().toUpperCase().equals("ERROR"))
				return false;
		}
		else {panelContratoAlq.getTxtCantDias().setBorder(new LineBorder(Color.WHITE));}
		

			// Mora
		String porcentajeMora = panelContratoAlq.getTxtMora().getText();
		
		if (!porcentajeMora.equals("") && !ValidateConstants.validatePercentage(porcentajeMora)) {
			panelContratoAlq.getTxtMora().setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			valido = false;
		}
		else {
			if (porcentajeMora.equals("")) {
				panelContratoAlq.getTxtMora().setBorder(new LineBorder(Color.white));
			}
		}
		
			// Deposito
		String deposito = panelContratoAlq.getTxtPrecioDesposito().getText();
		
		if (!deposito.equals("") && !ValidateConstants.validateMoney(deposito)) {
			panelContratoAlq.getTxtPrecioDesposito().setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			valido = false;
		}
		else {
			if (deposito.equals(""))
				panelContratoAlq.getTxtPrecioDesposito().setBorder(new LineBorder(Color.WHITE));
			else {

				Integer cantMeses = (Integer) panelContratoAlq.getSpinMesesDeposito().getValue();
					
				if (cantMeses == 0) {
					JOptionPane.showMessageDialog(null, "El/Los mes/es de deposito no puede ser 0 (cero).");
					valido = false;
				}	
			}
		}
		
		return valido;
	}
	
	private void eventoCancelar() {
		this.dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
	}
	
	@Override
	protected void eventoCerrar() {
		this.dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		
	}
	
	
	
	/* Configs Modificar */
	
	private void completarLocador(Alquiler alquiler) {
		Inmueble inmueble = alquiler.getInmueble();
		Persona propietario = inmueble.getPropietario();
		
		txtInmueble.setText(inmueble.getTipoInmueble());
		txtInmPropietario.setText(propietario.getApellido() + ", " + propietario.getNombre());
		txtInmDireccion.setText(inmueble.getUbicacion().getCalle() + " " + inmueble.getUbicacion().getNumero());
		txtInmUbicacion.setText(inmueble.getUbicacion().getCiudad() + ", " + inmueble.getUbicacion().getProvincia());
	}
	
	private void completarLocatario (Alquiler alquiler) {
		Persona pInquilino = alquiler.getInquilino();
		
		txtInquilino.setText(pInquilino.getDni().toString());
		txtNombre.setText(pInquilino.getApellido() + ", " + pInquilino.getNombre());
		txtDireccion.setText(pInquilino.getDomicilio() + ", " + pInquilino.getCiudad());
		
		if (pInquilino.getContacto().getCelular() != null)
			txtContacto.setText(pInquilino.getContacto().getCelular().toString());
	}
	
	private void completarContrato (Alquiler alquiler) {
		
		panelContratoAlq.getTxtMontoTotal().setText("$" + Utils.DECIMAL_FORMAT.format(alquiler.getMontoTotal()));
		
		panelContratoAlq.getDateChooserInicioContrato().setEnabled(false);
		panelContratoAlq.getDateChooserInicioContrato().setDate(alquiler.getFechaInicio());
		((JTextField) panelContratoAlq.getDateChooserInicioContrato().getDateEditor().getUiComponent()).setDisabledTextColor(Color.black);
		
		panelContratoAlq.getDateChooserFinContrato().setEnabled(false);
		panelContratoAlq.getDateChooserFinContrato().setDate(alquiler.getFechaFin());
		((JTextField) panelContratoAlq.getDateChooserFinContrato().getDateEditor().getUiComponent()).setDisabledTextColor(Color.black);
		
		panelContratoAlq.calcularDuracion();
		
		panelContratoAlq.getSpinDiaVto().setEnabled(true);
		panelContratoAlq.getSpinDiaVto().setValue(alquiler.getDiaVto());
		
		panelContratoAlq.getSpinMesesDeposito().setValue(alquiler.getMesesDeposito());
		
		if (alquiler.getPrecioDeposito() != null)
			panelContratoAlq.getTxtPrecioDesposito().setText(Utils.DECIMAL_FORMAT.format(alquiler.getPrecioDeposito()).replaceAll(",", "."));
		
		panelContratoAlq.getTxtMora().setText(Utils.DECIMAL_FORMAT.format(alquiler.getPorcentajeMora()).replaceAll(",", "."));
		
	}
	
	private void completarGarantia(Alquiler alquiler) {
		Persona pGarante = alquiler.getGarante();
		
		panelGarantiaAlq.getTxtGarante().setText(pGarante.getDni().toString());
		panelGarantiaAlq.getTxtGarante().setEnabled(false);
		
		panelGarantiaAlq.getTxtNombre().setText(pGarante.getApellido() + ", " + pGarante.getNombre());
		panelGarantiaAlq.getTxtNombre().setEnabled(false);
		
		panelGarantiaAlq.getTxtDireccion().setText(pGarante.getDomicilio() + ", " + pGarante.getCiudad());
		panelGarantiaAlq.getTxtDireccion().setEnabled(false);
		
		if (pGarante.getContacto().getCelular() != null)
			panelGarantiaAlq.getTxtContacto().setText(pGarante.getContacto().getCelular().toString());
		
//		panelGarantiaAlq.getTxtContacto().setEnabled(false);

	}
	
	
	/* Getters & Setters */
	
	public boolean isInquilino() {
		return isInquilino;
	}

	public void setInquilino(boolean isInquilino) {
		this.isInquilino = isInquilino;
	}

	public PanelGarantiaAlq getPanelGarantiaAlq() {
		return panelGarantiaAlq;
	}	
	
	
}
