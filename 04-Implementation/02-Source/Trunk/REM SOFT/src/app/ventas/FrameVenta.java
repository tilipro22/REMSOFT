package app.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import app.inmuebles.CtrlInmueble;
import app.ventas.paneles.PanelGenerarCuotas;
import app.ventas.paneles.PanelNuevaFormaPago;
import app.ventas.paneles.PanelVtaContrato;
import app.ventas.paneles.PanelVtaEscriturizacion;
import app.ventas.paneles.PanelVtaFormaPago;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import gui.FrameTablaInmuebles;
import gui.FrameTablaPersonas;
import gui.tabla.ModeloTabla;
import logica.FormaPagoVta;
import logica.Inmueble;
import logica.Operacion;
import logica.Persona;
import logica.Venta;
import logica.constant.ValidateConstants;
import util.Utils;

public class FrameVenta extends FrameDefault {

	private static final long serialVersionUID = 1L;
	private final DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	/* constants */
	public static final int SEARCH_INMUEBLE = 1;
	public static final int SEARCH_COMPRADOR= 2;
	public static final int SEARCH_CONYUGE = 3;
	public static final int SEARCH_MARTILLERO = 4;
	public static final int SEARCH_ESCRIBANO = 5;
	
	/* local constants */
	private static final int SEARCH = 1;
	private static final int NO_SEARCH = -1;
	
	
	private boolean isModificar;
	private int search_actual = -1;
	private CtrlInmueble ctrlInmueble=new CtrlInmueble();
	
	
	/* Elements */
	private JPanel panelInfo, panelNav, panelNavButtons, panelDynamic, panelFooter;
	private Integer filaSelecc;
	private Venta venta;
	
	
	/* Panel Info */
	private JTextField txtInmueble, txtPropietario, txtComprador, txtConyuge, txtMartillero, txtEscribano;
	private Inmueble inmueble;
	private Persona comprador, conyuge, martillero, escribano;
	
	/* Panel Nav */
	private JLabel lblFormaPago, lblEscriturizacion;
	
	/* Panel Dynamic */
	private JPanel panelVtaContrato, panelNuevaFormaPago, panelGenerarCuotas, panelNotas;
	private PanelVtaFormaPago panelVtaFormaPago;
	private PanelVtaEscriturizacion panelVtaEscriturizacion;
	private JTextArea txtAreaNotas;
	
	/* Panel footer */
	private ButtonDefaultABM btnAceptar, btnCancelar;
	
	public FrameVenta(boolean isModificar) {
		this.isModificar = isModificar;
		
		// Config FRAME
		setTituloBarra("   Contrato de Venta - REM Soft");
		setSizeFrame(800, 750);
		setLocationRelativeTo(null);
		
		initPanelInfo();
		initPanelNav();
		initPanelDynamic();
		initPanelFooter();
		
		if (isModificar) {
			setTituloBarra("   REMSOFT ~ Modificar Contrato de Venta");
			
			completeDatosModificar();
		}
	}
	
	public FrameVenta (ModeloTabla tablaReservas, boolean reservaMod) {
		setTituloBarra("   REMSOFT ~ Nueva Reserva");
		setSizeFrame(800, 750);
		setLocationRelativeTo(null);
		
		initPanelInfo();
		initPanelNav();
		initPanelDynamic();
		initPanelFooter();
	}
	
	private void completeDatosModificar () {
		this.filaSelecc = ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFilaSeleccionada();
		this.venta = (Venta) ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getModeloTabla().getObjetos().get(filaSelecc);
		
		txtInmueble.setText(venta.getInmueble().getTipoInmueble());
		txtPropietario.setText(venta.getInmueble().getPropietario().getApellido() + ", " + venta.getInmueble().getPropietario().getNombre());
		txtComprador.setText(venta.getComprador().getApellido() + ", " + venta.getComprador().getNombre());
		txtMartillero.setText(venta.getMartillero().getApellido() + ", " + venta.getMartillero().getNombre());
		
		((PanelVtaContrato) panelVtaContrato).getDateChoosFecha().setDate(venta.getFecha());
		((PanelVtaContrato) panelVtaContrato).getTxtPrecio().setText(Utils.DECIMAL_FORMAT.format(venta.getPrecioVenta()));
		//((PanelVtaContrato) panelVtaContrato).getTxtComisionTotal().setText(t);
	}
	
	/* Inits */
	
	private void initPanelInfo() {
		panelInfo = new JPanel();
		panelInfo.setBounds(25, 50, getWidth()-50, 300);
		panelInfo.setBackground(ConfigGUI.COLOR_FONDO);
		panelInfo.setLayout(new FlowLayout(SwingConstants.TRAILING, 30, 15));
		
		addCaja(panelInfo, new JLabel("Inmueble: "), txtInmueble=new JTextField(""), SEARCH);
		addCaja(panelInfo, new JLabel("Propietario: "), txtPropietario=new JTextField(""), NO_SEARCH);
		addCaja(panelInfo, new JLabel("Comprador: "), txtComprador=new JTextField(""), SEARCH);
		addCaja(panelInfo, new JLabel("Conyuge: "), txtConyuge=new JTextField(""), SEARCH);
		addCaja(panelInfo, new JLabel("Martillero: "), txtMartillero=new JTextField(""), SEARCH);
		addCaja(panelInfo, new JLabel("Escribano: "), txtEscribano=new JTextField(""), SEARCH);
		
		panelNuevaFormaPago = new PanelNuevaFormaPago();
		panelNuevaFormaPago.setVisible(false);
		
		panelGenerarCuotas = new PanelGenerarCuotas();
		panelGenerarCuotas.setVisible(false);
		
		add(panelInfo);
		add(panelNuevaFormaPago);
		add(panelGenerarCuotas);
	}
	
	private void addCaja(JPanel panel, JLabel lbl, JTextField txt, int search) {
		setLabelDefault(lbl);
		lbl.setPreferredSize(new Dimension(215, 35));
		setTextfieldDefault(txt);
		txt.setPreferredSize(new Dimension(385, 35));
		panel.add(lbl);
		panel.add(txt);
		
		if (search == SEARCH) {
			txt.setPreferredSize(new Dimension(300, 35));
			
			if (lbl.getText().equals("Inmueble: "))
				setConfigButtonSearch(panel, new JButton(), SEARCH_INMUEBLE);
			else if (lbl.getText().equals("Comprador: "))
				setConfigButtonSearch(panel, new JButton(), SEARCH_COMPRADOR);
			else if (lbl.getText().equals("Conyuge: "))
				setConfigButtonSearch(panel, new JButton(), SEARCH_CONYUGE);
			else if (lbl.getText().equals("Martillero: "))
				setConfigButtonSearch(panel, new JButton(), SEARCH_MARTILLERO);
			else if (lbl.getText().equals("Escribano: "))
				setConfigButtonSearch(panel, new JButton(), SEARCH_ESCRIBANO);
		}
	}
	
	private void setConfigButtonSearch(JPanel panel, JButton btn, int opcion) {
		btn = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btn.setPreferredSize(new Dimension(55, 35));
		btn.setBackground(new Color(78, 78, 78));
		btn.setVerticalTextPosition(SwingConstants.CENTER);
		btn.setIcon(new ImageIcon(FrameVenta.class.getResource("/images/search-24x24.png")));
		
		if (opcion == 1) {
			
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					search_actual = opcion;
					FrameTablaInmuebles frameTablaInmuebles = new FrameTablaInmuebles();
					setEnabled(false);
					frameTablaInmuebles.setVisible(true);
				}
			});
		}
		else {
			
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					search_actual = opcion;
					FrameTablaPersonas frameTablaPersonas = new FrameTablaPersonas();
					setEnabled(false);
					frameTablaPersonas.setVisible(true);
				}
			});
		}
		
		if (isModificar) {
			btn.setEnabled(false);
		}
		
		panel.add(btn);
	}

	private void initPanelNav() {
		panelNav = new JPanel();
		panelNav.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
		panelNav.setBorder(new LineBorder(Color.white));
		panelNav.setBackground(ConfigGUI.COLOR_FONDO);
		panelNav.setBounds(25, panelInfo.getHeight()+panelInfo.getY()+10, getWidth()-50, 50);
		
		panelNavButtons = new JPanel();
		panelNavButtons.setVisible(false);
		panelNavButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		panelNavButtons.setBackground(ConfigGUI.COLOR_FONDO);
		panelNavButtons.setBounds(25, panelNav.getHeight()+panelNav.getY()+10, getWidth()-50, 50);
		
		addLabelToPanelNav(panelNav, new JLabel("CONTRATO"));
		addLabelToPanelNav(panelNav, lblFormaPago = new JLabel("FORMA DE PAGO"));
		addLabelToPanelNav(panelNav, lblEscriturizacion = new JLabel("ESCRITURIZACION"));
		addLabelToPanelNav(panelNav, new JLabel("NOTAS"));
		
		ButtonDefaultABM btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCerrarGenCuotas();
			}
		});
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");
		
		ButtonDefaultABM btnAceptar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((PanelGenerarCuotas) panelGenerarCuotas).validarGenCuotas()) {
					((PanelGenerarCuotas) panelGenerarCuotas).generarCuotas();
					eventoCerrarGenCuotas();
				}
			}
		});
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAceptar.setText("Aceptar");
		
		panelNavButtons.add(btnCancelar);
		panelNavButtons.add(btnAceptar);
		
		add(panelNav);
		add(panelNavButtons);
		
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
						
						if (lbl.getText().equals("CONTRATO")) {
							panelDynamic.getComponent(0).setVisible(true);
							panelDynamic.getComponent(1).setVisible(false);
							panelDynamic.getComponent(2).setVisible(false);
							panelDynamic.getComponent(3).setVisible(false);
						}
						
						if (lbl.getText().equals("FORMA DE PAGO")) {
							panelDynamic.getComponent(0).setVisible(false);
							panelDynamic.getComponent(1).setVisible(true);
							panelDynamic.getComponent(2).setVisible(false);
							panelDynamic.getComponent(3).setVisible(false);
						}
						
						if (lbl.getText().equals("ESCRITURIZACION")) {
							panelDynamic.getComponent(0).setVisible(false);
							panelDynamic.getComponent(1).setVisible(false);
							panelDynamic.getComponent(2).setVisible(true);
							panelDynamic.getComponent(3).setVisible(false);
						}
						
						if (lbl.getText().equals("NOTAS")) {
							panelDynamic.getComponent(0).setVisible(false);
							panelDynamic.getComponent(1).setVisible(false);
							panelDynamic.getComponent(2).setVisible(false);
							panelDynamic.getComponent(3).setVisible(true);
						}
					}
				});
		
		panel.add(lbl);
	}

	private void initPanelDynamic() {
		panelDynamic = new JPanel();
		panelDynamic.setBounds(25, panelNav.getHeight()+panelNav.getY()+10, getWidth()-50, 275);
		panelDynamic.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		panelDynamic.setLayout(null);
		
		panelVtaContrato = new PanelVtaContrato();
		
		panelVtaFormaPago = new PanelVtaFormaPago();
		panelVtaFormaPago.setVisible(false);
		
		panelVtaEscriturizacion = new PanelVtaEscriturizacion();
		panelVtaEscriturizacion.setVisible(false);
		
		initPanelNotas();				
		
		panelDynamic.add(panelVtaContrato);
		panelDynamic.add(panelVtaFormaPago);
		panelDynamic.add(panelVtaEscriturizacion);
		panelDynamic.add(panelNotas);
		
		add(panelDynamic);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 0));
		panelFooter.setBounds(0, panelDynamic.getHeight()+panelDynamic.getY()+5, getWidth(), 50);
		panelFooter.setBorder(new MatteBorder(0, 2, 2, 2, Color.CYAN));
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCerrar();
			}
		});
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");
		
		btnAceptar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (! isModificar) {
					eventoAceptar();
				}
				else {
					eventoCerrar();
				}
				
			}
		});
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAceptar.setText("Aceptar");
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnAceptar);
		
		add(panelFooter);
	}
	
	// internos
	private void initPanelNotas() {
		panelNotas = new JPanel();
		panelNotas.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		panelNotas.setLayout(new BorderLayout());
		panelNotas.setBounds(0, 0, panelDynamic.getWidth(), panelDynamic.getHeight());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(txtAreaNotas=new JTextArea());
		txtAreaNotas.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtAreaNotas.setLineWrap(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		panelNotas.add(scrollPane, BorderLayout.CENTER);
		panelNotas.setVisible(false);
	}
	
	
	/* Functions */
	
	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
		Integer id = Integer.decode(inmueble.getTipoInmueble());
		String tipoInmueble = ctrlInmueble.getTipoInmueble(id);
		txtInmueble.setText(tipoInmueble);
		txtPropietario.setText(inmueble.getPropietario().getApellido() + ", " + inmueble.getPropietario().getNombre());
		
		List<Operacion> listOperaciones = inmueble.getOperaciones();
		
		for (int i = 0; i < listOperaciones.size(); i++) {

			if (Integer.decode(listOperaciones.get(i).getTipoOperacion()) == 2) {
				((PanelVtaContrato) panelVtaContrato).getTxtPrecio().setText(listOperaciones.get(i).getPrecio().toString());
				System.out.println(listOperaciones.get(i).getMoneda());
				((PanelVtaContrato) panelVtaContrato).getCbxMoneda().setSelectedItem(listOperaciones.get(i).getMoneda());
			}
			
		}
	}
	
	public void setComprador(Persona comprador) {
		this.comprador = comprador;
		txtComprador.setText(comprador.getApellido() + ", " + comprador.getNombre());
	}
	
	public void setConyuge(Persona conyuge) {
		this.conyuge = conyuge;
		txtConyuge.setText(conyuge.getApellido() + ", " + conyuge.getNombre());
	}

	public void setMartillero(Persona martillero) {
		this.martillero = martillero;
		txtMartillero.setText(martillero.getApellido() + ", " + martillero.getNombre());
	}

	public void setEscribano(Persona escribano) {
		this.escribano = escribano;
		txtEscribano.setText(escribano.getApellido() + ", " + escribano.getNombre());
	}
	
	private boolean validarDatosisOk() {
		
		boolean isValido = true;
		
		// Validar que no esten vacios los textfields Obligatorios 
		
		isValido = isTextfieldValido(txtInmueble);
		isValido = isTextfieldValido(txtPropietario);
		isValido = isTextfieldValido(txtComprador);
		isValido = isTextfieldValido(txtMartillero);
		
		// Validar Contrato: Fecha (no empty y fecha con formato correcto) y Comision (Constants.Percentages)
		
		JTextField txtFecha = ((JTextField) ((PanelVtaContrato) panelVtaContrato).getDateChoosFecha().getDateEditor().getUiComponent());
		
		if (txtFecha.getText().equals("") || txtFecha.getForeground().equals(Color.red)) {
			((JTextField) ((PanelVtaContrato) panelVtaContrato).getDateChoosFecha().getDateEditor().getUiComponent()).setText("ERROR");
			isValido = false;
		}
		
		if (! ValidateConstants.validatePercentage(((PanelVtaContrato) panelVtaContrato).getTxtComisionPorcentaje().getText()) ) {
			((PanelVtaContrato) panelVtaContrato).getTxtComisionPorcentaje().setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			isValido = false;
		}
		else {
			((PanelVtaContrato) panelVtaContrato).getTxtComisionPorcentaje().setBorder(new LineBorder(Color.white));
		}
		
		
		// Validar Forma de Pago: Debe haber al menos una. Si es < Precio Original || > PrecioOriginal, se debe informar en pantalla
		// y preguntarle al usuario si esta de acuerdo con esto (una vez aceptador).
		
		if (panelVtaFormaPago.getModeloTabla().getObjetos().size() == 0) {
			lblFormaPago.setForeground(ConfigGUI.COLOR_ERROR);
			isValido = false;
		}
		
		
		// Validar Escriturizacion: Multa diaria y Constants.Percentages.
		
		if (! ValidateConstants.validatePercentage(panelVtaEscriturizacion.getTxtMultaDiaria().getText())) {
			panelVtaEscriturizacion.getTxtMultaDiaria().setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			lblEscriturizacion.setForeground(ConfigGUI.COLOR_ERROR);
			isValido = false;
		}
		else {
			panelVtaEscriturizacion.getTxtMultaDiaria().setBorder(new LineBorder(Color.white));
		}
		
		if (txtAreaNotas.getText().length() > 1000) {
			JOptionPane.showMessageDialog(null, "La cantidad de caracteres permitido para las Notas es de 1000 (caracteres).");
		}
		
		
		// El propietario del inmueble, NO puede ser la misma persona que el COMPRADOR
		
		if (isValido) {
			System.out.println(inmueble.getPropietario().getDni() + " | " + comprador.getDni());
			if (inmueble.getPropietario().getDni().equals(comprador.getDni())) {
				JOptionPane.showMessageDialog(null, "El Propietario, no puede ser la misma persona que el Comprador");
				return false;
			}
			
			//System.out.println(conyuge.getDni() + " | " + comprador.getDni());
			if (conyuge != null && comprador.getDni().equals(conyuge.getDni())) {
				JOptionPane.showMessageDialog(null, "El/La Conyuge, no puede ser la misma persona que el Comprador");
				return false;
			}
			
			//Validar datos de pago
			BigDecimal precioOriginal = new BigDecimal(((PanelVtaContrato) panelVtaContrato).getTxtPrecio().getText());
			BigDecimal precioTotal = new BigDecimal(00.00);
			
			for (int i=0; i < panelVtaFormaPago.getModeloTabla().getObjetos().size(); i++) {
				FormaPagoVta formaPagoVta = (FormaPagoVta) panelVtaFormaPago.getModeloTabla().getObjetos().get(i);
				precioTotal = precioTotal.add(formaPagoVta.getMontoTotal());
			}
			
			if (precioTotal.compareTo(new BigDecimal(00.00)) > 0) {
				if (precioTotal.compareTo(precioOriginal) > 0) {
					int option = JOptionPane.showConfirmDialog(null, "El precio total ($" + precioTotal + ") es mayor que "
							+ "el precio original ($" + precioOriginal + ")\nDesea Continuar y reemplazar el precio?");
					
					if (option == 1 || option == 2) {
						isValido = false;
					}
				}
				else if (precioTotal.compareTo(precioOriginal) < 0) {
					int option = JOptionPane.showConfirmDialog(null, "El precio total ($" + precioTotal + ") es menor que "
							+ "el precio original ($" + precioOriginal + ").\n¿Desea Continuar y reemplazar el precio?");
					
					if (option == 1 || option == 2) {
						isValido = false;
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "El total de forma de pagos no puede ser $00.00");
				isValido = false;
			}
			
			System.out.println("Precio Original: $" + precioOriginal + ". Precio Total: $" + precioTotal);
			
		}
		
		return isValido;
	}
	
	private boolean isTextfieldValido (JTextField txt) {
		
		if (txt.getText().equals("")) {
			txt.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			return false;
		}
		else {
			txt.setBorder(new LineBorder(Color.white));
			return true;
		}
	}
	
	private void generarContratoVenta() {
		Date fecha = ((PanelVtaContrato) panelVtaContrato).getDateChoosFecha().getDate();
		BigDecimal precioVenta = new BigDecimal(((PanelVtaContrato) panelVtaContrato).getTxtPrecio().getText());
		String moneda = (String) ((PanelVtaContrato) panelVtaContrato).getCbxMoneda().getSelectedItem();
		BigDecimal comisionPct = new BigDecimal(((PanelVtaContrato) panelVtaContrato).getTxtComisionPorcentaje().getText());
		
		int diasEscriturizacion = (Integer) panelVtaEscriturizacion.getSpinDias().getValue();
		BigDecimal multaEscriturizacion = new BigDecimal(panelVtaEscriturizacion.getTxtMultaDiaria().getText());
		Boolean escriturizado = false;
		
		String observacion = null;
		if (! ValidateConstants.isTextEmpty(txtAreaNotas.getText())) {
			observacion = new String(txtAreaNotas.getText());
		}
		
		String estado = Venta.ESTADOS_ARRAY[Venta.ESTADO_PEN_ESCRITURACION];
		Boolean reservado = false;
		
		//generar pagos
		
		Venta venta = new Venta(null, inmueble, comprador, conyuge, martillero, escribano, fecha, precioVenta, moneda, comisionPct, diasEscriturizacion, multaEscriturizacion, escriturizado, observacion, estado, reservado);
		venta.setFormaPagoVtas(generarPagosVta());
		venta.setEstado(Venta.ESTADOS_ARRAY[Venta.ESTADO_PEN_ESCRITURACION]);
		venta.setPrecioVenta(generarTotalFinal(venta.getFormaPagoVtas()));
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getModeloTabla().agregar(venta);
		
		CtrlVentas ctrlVentas = new CtrlVentas();
		ctrlVentas.insertNuevaVenta(venta);
	}

	
	private List<FormaPagoVta> generarPagosVta() {
		List<FormaPagoVta> formaPagoVtas = new ArrayList<>();
		
		for (int i=0; i < panelVtaFormaPago.getModeloTabla().getObjetos().size(); i++) {
			FormaPagoVta formaPagoVta = (FormaPagoVta) panelVtaFormaPago.getModeloTabla().getObjetos().get(i);
			formaPagoVtas.add(formaPagoVta);
		}
		
		return formaPagoVtas;
	}
	
	private BigDecimal generarTotalFinal(List<FormaPagoVta> formaPagoVtas) {
		BigDecimal totalFinal = new BigDecimal(00.00);
		
		for (int i=0; i < formaPagoVtas.size(); i++) {
			totalFinal = totalFinal.add(formaPagoVtas.get(i).getMontoTotal());
		}
		
		return totalFinal;
	}
	
	/* Events */

	@Override
	protected void eventoCerrar() {
		this.dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		
	}

	public void eventoGenerarCuota() {
		panelNav.setVisible(false);
		panelNavButtons.setVisible(true);
	}
	
	public void eventoCerrarGenCuotas() {
		((PanelGenerarCuotas) panelGenerarCuotas).setPanelDatosDefault();
		panelGenerarCuotas.setVisible(false);
		panelNavButtons.setVisible(false);
		((PanelVtaFormaPago) panelVtaFormaPago).configButtons(1);
		panelInfo.setVisible(true);
		panelNav.setVisible(true);
	}
	
	private void eventoAceptar() {
		if (validarDatosisOk()) {
			generarContratoVenta();
			eventoCerrar();
		}
		else {
			JOptionPane.showMessageDialog(null, "No se realizar la operacion solicitada.");
		}
	}
	
	
	/* Getters & Setters */
	
	public int getSearch_actual() {
		return search_actual;
	}

	public JPanel getPanelInfo() {
		return panelInfo;
	}
	
	public JPanel getPanelNav() {
		return panelNav;
	}
	
	public JPanel getPanelVtaFormaPago() {
		return panelVtaFormaPago;
	}

	public JPanel getPanelNuevaFormaPago() {
		return panelNuevaFormaPago;
	}

	public void setPanelNuevaFormaPago(JPanel panelNuevaFormaPago) {
		this.panelNuevaFormaPago = panelNuevaFormaPago;
	}

	public JPanel getPanelGenerarCuotas() {
		return panelGenerarCuotas;
	}

	public void setPanelGenerarCuotas(JPanel panelGenerarCuotas) {
		this.panelGenerarCuotas = panelGenerarCuotas;
	}
	
	
	

}
