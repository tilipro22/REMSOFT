package app.alquileres;

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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import app.alquileres.paneles.PanelGastosPagoAlq;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import logica.Alquiler;
import logica.GastoFijo;
import logica.Inmueble;
import logica.PagoAlquiler;
import logica.Ubicacion;
import logica.constant.EnumMeses;
import logica.constant.ValidateConstants;

public class FramePagoAlquiler extends FrameDefault {

	private static final long serialVersionUID = 1L;
	private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

	/* elements */
	private boolean isModificar;
	private Alquiler alquiler;
	private PagoAlquiler pagoAlquiler;
	private BigDecimal total, totalMora;

	/* Panels */
	private JPanel panelHeaderDatos, panelFechaCorresp, panelDynamic, panelLeftDatos, panelTotales, panelFooter;

	/* PanelHeaderDatos */
	private JDateChooser dateChooserFechaPago;

	/* PanelDynamic */
	private PanelGastosPagoAlq panelGastosPagoAlq;
	private JPanel panelObservaciones;
	private JTextArea txArea;

	/* PanelLeftDatos */
	private JCheckBox chkBoxMora, chkBoxPrecioPagado;

	/* PanelTotales */
	private JTextField txtMora, txtTotal, txtPagado;

	/* PanelFooter */
	private ButtonDefaultABM btnCancelar, btnAsentarPago;

	/**
	 * Launch the application.
	 */

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { PagoAlquiler pagoAlq = new
	 * PagoAlquiler(1, 4, 2017, new BigDecimal("22.22"), null);
	 * FramePagoAlquiler frame = new FramePagoAlquiler(false, null, pagoAlq);
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } }
	 * }); }
	 */

	public FramePagoAlquiler(Alquiler alquiler, PagoAlquiler pagoAlquiler) {
		this.alquiler = alquiler;
		this.pagoAlquiler = pagoAlquiler;
		
		if (isModificar)
			setTituloBarra(" REMSOFT | Modificar Pago de Alquiler");
		else
			setTituloBarra(" REMSOFT | Asentar Pago de Alquiler");

		getContentPane().setBackground(ConfigGUI.COLOR_FONDO_ALT);
		setSizeFrame(650, 750);
		super.setLocationRelativeTo(null);

		initPanelHeader();
		initPanelFechaCorresp();
		initPanelDynamic();
		initPanelLeftDatos();
		initPanelTotales();
		initPanelFooter();
	}

	/* Inits */
	private void initPanelHeader() {
		JLabel lblFecha, lblInmueble, lblInquilino, lblPropietario;
		panelHeaderDatos = new JPanel();
		panelHeaderDatos.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		panelHeaderDatos.setBounds(10, 30, getWidth() - 20, 135);
		panelHeaderDatos.setLayout(null);

		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelLeft.setBounds(0, 0, panelHeaderDatos.getWidth(), panelHeaderDatos.getHeight());
		panelLeft.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));

		/* init panelLeft */
		setLabelDefault(lblFecha = new JLabel("Fecha de Pago: "));
		lblFecha.setPreferredSize(new Dimension(panelHeaderDatos.getWidth() - 200, 30));
		dateChooserFechaPago = new JDateChooser(new Date());
		dateChooserFechaPago.setPreferredSize(new Dimension(150, 30));
		panelLeft.add(lblFecha);
		panelLeft.add(dateChooserFechaPago);

		Inmueble inmueble = this.alquiler.getInmueble();
		setLabelDefault(lblInmueble = new JLabel("Inmueble: " + getDireccionInmueble(inmueble)));
		lblInmueble.setPreferredSize(new Dimension(panelHeaderDatos.getWidth() - 50, 20));
		lblInmueble.setHorizontalTextPosition(SwingConstants.LEFT);
		setLabelDefault(lblInquilino = new JLabel(
				"Inquilino: " + alquiler.getInquilino().getApellido() + ", " + alquiler.getInquilino().getNombre()));
		lblInquilino.setPreferredSize(new Dimension(panelHeaderDatos.getWidth() - 50, 20));
		setLabelDefault(
				lblPropietario = new JLabel("Propietario: " + alquiler.getInmueble().getPropietario().getApellido()
						+ ", " + alquiler.getInmueble().getPropietario().getNombre()));
		lblPropietario.setPreferredSize(new Dimension(panelHeaderDatos.getWidth() - 50, 20));
		panelLeft.add(lblInquilino);
		panelLeft.add(lblPropietario);
		panelLeft.add(lblInmueble);

		/* init panelright */

		panelHeaderDatos.add(panelLeft);
		add(panelHeaderDatos);
	}

	private void initPanelFechaCorresp() {
		panelFechaCorresp = new JPanel();
		panelFechaCorresp.setBackground(ConfigGUI.COLOR_FONDO);
		panelFechaCorresp.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
		panelFechaCorresp.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
		panelFechaCorresp.setBounds(10, panelHeaderDatos.getHeight() + panelHeaderDatos.getY(), getWidth() - 20, 50);

		JLabel lblFechaCorrespondiente = new JLabel(
				"Pago correspondiente al Mes de: " + EnumMeses.values()[pagoAlquiler.getMesCorrespondiente() - 1]
						+ " de " + pagoAlquiler.getAnioCorrespondiente());
		setLabelDefault(lblFechaCorrespondiente);

		panelFechaCorresp.add(lblFechaCorrespondiente);
		add(panelFechaCorresp);
	}

	private void initPanelDynamic() {
		panelDynamic = new JPanel();
		panelDynamic.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		panelDynamic.setLayout(null);
		panelDynamic.setBounds(10, panelFechaCorresp.getHeight() + panelFechaCorresp.getY() + 10, getWidth() - 20, 300);

		/* Barra nav */
		JPanel panelNav = new JPanel();
		panelNav.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelNav.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelNav.setBorder(new LineBorder(Color.white));
		panelNav.setBounds(0, 0, panelDynamic.getWidth(), 50);

		JLabel lblGastos, lblObservaciones;
		setLabelToNav(lblGastos = new JLabel("GASTOS"), 25);
		setLabelToNav(lblObservaciones = new JLabel("OBSERVACIONES"), 25);

		lblGastos.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				eventoNavBar(lblGastos);
			}

		});

		lblObservaciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eventoNavBar(lblObservaciones);
			}
		});

		panelGastosPagoAlq = new PanelGastosPagoAlq(this.alquiler, this.pagoAlquiler);
		panelGastosPagoAlq.setLocation(0, panelNav.getHeight() + panelNav.getY());

		panelObservaciones = new JPanel();
		panelObservaciones.setLayout(new BorderLayout());
		panelObservaciones.setBounds(0, panelNav.getHeight() + panelNav.getY(), 630, 250);
		txArea = new JTextArea();
		txArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txArea.setLineWrap(true);
		panelObservaciones.add(txArea, BorderLayout.CENTER);
		panelObservaciones.setVisible(false);

		panelNav.add(lblGastos);
		panelNav.add(lblObservaciones);

		add(panelDynamic);
		panelDynamic.add(panelNav);
		panelDynamic.add(panelGastosPagoAlq);
		panelDynamic.add(panelObservaciones);
	}

	private void initPanelLeftDatos() {
		panelLeftDatos = new JPanel();
		panelLeftDatos.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelLeftDatos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelLeftDatos.setBounds(10, panelDynamic.getHeight() + panelDynamic.getY() + 10, (getWidth()) / 2, 150);

		JLabel lblPrecioAlq;
		JTextField txtPrecioAlq;

		setLabelDefault(lblPrecioAlq = new JLabel("Precio del Alquiler: "));
		lblPrecioAlq.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
		setTextfieldDefault(txtPrecioAlq = new JTextField());
		txtPrecioAlq.setText("$ " + alquiler.getMontoTotal().toString());
		txtPrecioAlq.setPreferredSize(new Dimension(150, 35));
		txtPrecioAlq.setBackground(ConfigGUI.COLOR_FONDO_ALT);

		setConfigCheckBox(chkBoxMora = new JCheckBox("Incluir precio de Mora"));
		chkBoxMora.setSelected(true);
		
		chkBoxMora.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkBoxMora.isSelected()) {
					if (totalMora.compareTo(new BigDecimal("00.00")) > 0)
						txtMora.setText("$" + decimalFormat.format(totalMora));
					else
						txtMora.setText("$ 00.00");
					
					cargarTotales();
				}
				else {
					txtMora.setText("$ 00.00");
	
					if (totalMora.compareTo(new BigDecimal(00.00)) > 0) {
						txtTotal.setText("$ " + decimalFormat.format(total));
						
						if (chkBoxPrecioPagado.isSelected()) {
							txtPagado.setText("$ " + decimalFormat.format(total));
						}
					}
				}
			}
		});

		setConfigCheckBox(chkBoxPrecioPagado = new JCheckBox("Pagar Total Final"));
		chkBoxPrecioPagado.setSelected(true);
		chkBoxPrecioPagado.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkBoxPrecioPagado.isSelected()) {
					StringBuilder sbPagado = new StringBuilder();
					for (int i=2; i < txtTotal.getText().length(); i++) {
						sbPagado.append(txtTotal.getText().charAt(i));
					}
					txtPagado.setText(sbPagado.toString());
					txtPagado.setEnabled(false);
				} else {
					txtPagado.setEnabled(true);
					txtPagado.setText("00.00");
				}
			}
		});

		panelLeftDatos.add(lblPrecioAlq);
		panelLeftDatos.add(txtPrecioAlq);
		panelLeftDatos.add(chkBoxMora);
		panelLeftDatos.add(chkBoxPrecioPagado);

		add(panelLeftDatos);
	}

	private void initPanelTotales() {
		panelTotales = new JPanel();
		panelTotales.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelTotales.setBorder(new LineBorder(Color.white));
		panelTotales.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelTotales.setBounds((getWidth() - 20) / 2, panelDynamic.getHeight() + panelDynamic.getY() + 10,
				(getWidth()) / 2, 150);
		JLabel lblMora, lblTotal, lblPagado;

		setLabelDefault(lblMora = new JLabel("Mora: "));
		setTextfieldDefault(txtMora = new JTextField());
		setConfigCajaTotales(lblMora, txtMora);
		txtMora.setBackground(Color.white);
		txtMora.setDisabledTextColor(Color.black);

		setLabelDefault(lblTotal = new JLabel("Total a pagar: "));
		setTextfieldDefault(txtTotal = new JTextField());
		setConfigCajaTotales(lblTotal, txtTotal);
		txtTotal.setBackground(Color.white);
		txtTotal.setDisabledTextColor(Color.black);

		setLabelDefault(lblPagado = new JLabel("Pagado: $"));
		setTextfieldDefault(txtPagado = new JTextField());
		setConfigCajaTotales(lblPagado, txtPagado);

		cargarTotales();

		panelTotales.add(lblMora);
		panelTotales.add(txtMora);

		panelTotales.add(lblTotal);
		panelTotales.add(txtTotal);

		panelTotales.add(lblPagado);
		panelTotales.add(txtPagado);

		add(panelTotales);
	}

	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, Color.white));
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelFooter.setBounds(10, getHeight() - 10 - 50, getWidth() - 20, 50);

		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCerrar();
			}
		});
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");

		btnAsentarPago = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAsentarPago.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (validarDatosPago()) {
					asentarPagoAlquiler();
				}
			}
		});
		btnAsentarPago.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAsentarPago.setText("Asentar Pago");

		panelFooter.add(btnCancelar);
		panelFooter.add(btnAsentarPago);
		add(panelFooter);
	}

	
	/* Functions */

	private boolean validarDatosPago() {
		boolean isValido = true;

		if (ValidateConstants.isTextEmpty(((JTextField) dateChooserFechaPago.getDateEditor().getUiComponent()).getText())
				|| ((JTextField) dateChooserFechaPago.getDateEditor().getUiComponent()).getForeground()
						.equals(Color.red)) {

			((JTextField) dateChooserFechaPago.getDateEditor().getUiComponent()).setText("ERROR");
			isValido = false;
		}
		
		if (! ValidateConstants.validateMoney(txtPagado.getText())) {
			txtPagado.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			isValido = false;
		}
		else {
			
			BigDecimal totalPagado = new BigDecimal(txtPagado.getText().replace(',', '.'));
			if (totalPagado.compareTo(total) > 0) {
				JOptionPane.showMessageDialog(null, "El monto a pagar, no puede ser mayor al total.");
				txtPagado.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
				isValido = false;
			}
		}

		return isValido;
	}
	
	private void asentarPagoAlquiler() {
		/*
		 * ARMAR EL NUEVO PAGO ALQUILER
		 */
		pagoAlquiler.setFechaPago(dateChooserFechaPago.getDate());
		pagoAlquiler.setMontoPagado(pagoAlquiler.getMontoPagado().add(new BigDecimal(txtPagado.getText().replace(',', '.'))));
		
		if (pagoAlquiler.getMontoTotal().compareTo(pagoAlquiler.getMontoPagado()) == 0) {
			pagoAlquiler.setEstadoPago(PagoAlquiler.ESTADO_PAGO[1]);
			pagoAlquiler.setPagado(true);
		}
		
		total = new BigDecimal(00.00);
		for (int i=0; i < panelGastosPagoAlq.getModeloTabla().getObjetos().size(); i++) {
			GastoFijo gastoFijo = (GastoFijo) panelGastosPagoAlq.getModeloTabla().getObjetos().get(i);
			
			if (! gastoFijo.getGasto().equals("Monto Pagado"))
				total = total.add(gastoFijo.getMonto());
		}
		
		pagoAlquiler.setMontoTotal(total);
		CtrlAlquileres ctrlAlquileres = new CtrlAlquileres();
		ctrlAlquileres.updateAsentarPago(pagoAlquiler);
		
		Integer filaSelecc = ((PanelAlquiler)FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFilaSeleccPagos();
		((PanelAlquiler)FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getPanelPagos().getModeloTabla().modificarObjeto(filaSelecc, pagoAlquiler);
		
		
		eventoCerrar();
	}

	private String getDireccionInmueble(Inmueble inmueble) {
		Ubicacion ubicacion = inmueble.getUbicacion();
		return ubicacion.getCalle() + " " + ubicacion.getNumero() + ", " + ubicacion.getCiudad() + ", "
				+ ubicacion.getProvincia();
	}

	public void cargarTotales() {
		total = new BigDecimal("00.00");
		totalMora = total = new BigDecimal("00.00");

		// Carga del TOTAL
		for (int i = 0; i < panelGastosPagoAlq.getModeloTabla().getObjetos().size(); i++) {
			GastoFijo gastoFijo = (GastoFijo) panelGastosPagoAlq.getModeloTabla().getObjetos().get(i);
			
			if (gastoFijo.getGasto().equals("Monto Pagado")) {
				total = total.subtract(gastoFijo.getMonto());
			}
			else {
				total = total.add(gastoFijo.getMonto());
			}
			
		}

		// Carga de la MORA
		Long diferenciaDias = calcularDuracion();

		if (diferenciaDias.equals(0)) {
			txtMora.setText("$ 00.00");
		} else {
			totalMora = total.multiply((alquiler.getPorcentajeMora().divide(new BigDecimal(100))))
					.multiply(new BigDecimal(diferenciaDias));
			if (totalMora.compareTo(new BigDecimal(00.00)) > 0) {
				txtMora.setText("$ " + decimalFormat.format(totalMora));
			}
			else {
				txtMora.setText("$ 00.00");
			}
		}

		txtTotal.setText("$ " + decimalFormat.format(total.add(totalMora)));
		
		if (chkBoxPrecioPagado.isSelected())
			txtPagado.setText(decimalFormat.format(total.add(totalMora)));

	}

	private Long calcularDuracion() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer diaActual = calendar.get(Calendar.DAY_OF_MONTH);
		Integer mesActual = calendar.get(Calendar.MONTH) + 1;
		Integer anioActual = calendar.get(Calendar.YEAR);

		java.util.GregorianCalendar fechaActual = new java.util.GregorianCalendar(anioActual, mesActual, diaActual);
		java.util.GregorianCalendar fechaVto = new java.util.GregorianCalendar(pagoAlquiler.getAnioCorrespondiente(),
				pagoAlquiler.getMesCorrespondiente(), alquiler.getDiaVto());

		Long difms = fechaActual.getTimeInMillis() - fechaVto.getTimeInMillis();
		Long difd = difms / (1000 * 60 * 60 * 24);

		if (difd < 1) {
			return new Long(0);
		} else {
			return difd;
		}

	}
	
	/* Configs */
	
	private void setConfigCajaTotales(JLabel lbl, JTextField txt) {
		lbl.setPreferredSize(new Dimension(120, 35));
		txt.setPreferredSize(new Dimension(150, 35));
		txt.setBackground(ConfigGUI.COLOR_FONDO_ALT);
	}

	private void setConfigCheckBox(JCheckBox checkBox) {
		checkBox.setOpaque(false);
		checkBox.setForeground(Color.white);
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBox.setPreferredSize(new Dimension(250, 35));
	}
	

	/* Events */

	@Override
	protected void eventoCerrar() {
		dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
	}

	private void eventoNavBar(JLabel lbl) {

		if (lbl.getText().equals("GASTOS")) {
			panelGastosPagoAlq.setVisible(true);
			panelObservaciones.setVisible(false);
		} else {
			panelGastosPagoAlq.setVisible(false);
			panelObservaciones.setVisible(true);
		}
	}

	private void eventoAceptar() {
		eventoCerrar();
		((PanelPagosAlquiler) ((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles()
				.get(FramePpal.PANEL_ALQUILERES)).getPanelPagos()).getListPagos().remove(0);
		((PanelPagosAlquiler) ((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles()
				.get(FramePpal.PANEL_ALQUILERES)).getPanelPagos()).getTabla().setVisible(false);
	}

}
