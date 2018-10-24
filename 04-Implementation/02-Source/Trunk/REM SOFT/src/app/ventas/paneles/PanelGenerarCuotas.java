package app.ventas.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.ventas.PanelVenta;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.paneles.PanelConstants;
import logica.FormaPagoVta;
import logica.constant.ValidateConstants;
import util.Utils;

public class PanelGenerarCuotas extends PanelConstants{

	private static final long serialVersionUID = 1L;

	/* Elements */
	private JPanel panelHeader, panelDatos, panelVto;
	
	// Panel Datos
	private JSpinner spinCuotas;
	private JTextField txtDescripCuotas, txtMontoCuota, txtHonorarios;
	private JComboBox<String> comboBoxMoneda = new JComboBox<String>();
	
	// Panel Vto
	private JCheckBox checkBoxVto;
	private JSpinner spinDiaVto, spinMesVto;
	private JTextField txtInteres;
	
	/* Constructors */
	public PanelGenerarCuotas() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(25, 50, 750, 350);
		setLayout(null);

		initPanelHeader();
		initPanelDatos();
		initPanelVto();
	}
	
	/* inits */
	private void initPanelHeader() {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setBorder(new LineBorder(Color.white));
		panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		panelHeader.setBounds(10, 10, getWidth()-20, 30);
		
		JLabel lblCuotas = new JLabel("Generador de Cuotas");
		setLabelDefault(lblCuotas);
		
		panelHeader.add(lblCuotas);
		add(panelHeader);
	}
	
	private void initPanelDatos() {
		panelDatos = new JPanel();
		panelDatos.setBackground(ConfigGUI.COLOR_FONDO);
		panelDatos.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
		panelDatos.setBounds(10, panelHeader.getHeight()+panelHeader.getY(), getWidth()-20, 200);
		panelDatos.setBorder(new MatteBorder(0, 1, 0, 1, Color.white));
		
		spinCuotas = new JSpinner();
		spinCuotas.setModel(new SpinnerNumberModel(12, 1, 48, 1));
		
		addLabelAndSpinner(panelDatos, new JLabel("Cantidad total de Cuotas: "), spinCuotas);
		addLabelAndTxt(panelDatos, new JLabel("Descripcion de Cuota: "), txtDescripCuotas=new JTextField());
		txtDescripCuotas.setText("Cuota");
		addLabelAndTxt(panelDatos, new JLabel("Monto de la Cuota: "), txtMontoCuota = new JTextField());
		addCajaLblAndCbx(panelDatos, new JLabel("Moneda: "), comboBoxMoneda, new String[]{"ARS", "USD", "EUR", "BRL"});
		addLabelAndTxt(panelDatos, new JLabel("Honorarios: "), txtHonorarios = new JTextField());
		
		
		add(panelDatos);
	}
	
	private void initPanelVto() {
		panelVto = new JPanel();
		panelVto.setBackground(ConfigGUI.COLOR_FONDO);
		panelVto.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 5));
		panelVto.setBounds(10, panelDatos.getHeight()+panelDatos.getY(), getWidth()-20, 110);
		panelVto.setBorder(new MatteBorder(0, 1, 1, 1, Color.white));
		
		checkBoxVto = new JCheckBox("Indicar fecha de Vto. de la couta");
		checkBoxVto.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (checkBoxVto.isSelected()) {
					spinDiaVto.setEnabled(true);
					spinMesVto.setEnabled(true);
					txtInteres.setEnabled(true);
				}
				else {
					spinDiaVto.setEnabled(false);
					spinMesVto.setEnabled(false);
					txtInteres.setEnabled(false);
				}
			}
		});
		setConfigCheckBox(checkBoxVto);
		checkBoxVto.setPreferredSize(new Dimension(500, COMP_HEIGHT));
		
		JLabel lblLeyenda, lblLeyendaCont, lblInteresDiario, lblPorcentaje;
		
		setLabelDefault(lblLeyenda = new JLabel("Cada cuota vencera el dia: "));
		lblLeyenda.setPreferredSize(new Dimension(250, COMP_HEIGHT));
		
		spinDiaVto = new JSpinner(new SpinnerNumberModel(10, 1, 28, 1));
		spinDiaVto.setEnabled(false);
		spinDiaVto.setPreferredSize(new Dimension(50, COMP_HEIGHT));
		setConfigSpinner(spinDiaVto);
		
		setLabelDefault(lblLeyendaCont = new JLabel("comenzando por el mes: "));
		lblLeyendaCont.setHorizontalAlignment(SwingConstants.LEFT);
		lblLeyendaCont.setPreferredSize(new Dimension(200, COMP_HEIGHT));
		
		
		spinMesVto = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
		spinMesVto.setEnabled(false);
		spinMesVto.setPreferredSize(new Dimension(50, COMP_HEIGHT));
		setConfigSpinner(spinMesVto);
		
		setLabelDefault(lblInteresDiario = new JLabel("Interes diario: ")); 
		lblInteresDiario.setPreferredSize(new Dimension(250, COMP_HEIGHT));
		
		setTextfieldDefault(txtInteres = new JTextField());
		txtInteres.setEnabled(false);
		txtInteres.setPreferredSize(new Dimension(150, COMP_HEIGHT));
		
		setLabelDefault(lblPorcentaje = new JLabel("%"));

		panelVto.add(checkBoxVto);
		panelVto.add(lblLeyenda);
		panelVto.add(spinDiaVto);
		panelVto.add(lblLeyendaCont);
		panelVto.add(spinMesVto);
		panelVto.add(lblInteresDiario);
		panelVto.add(txtInteres);
		panelVto.add(lblPorcentaje);
		
		add(panelVto);
	}

	/* Functions */
	
	public void setPanelDatosDefault() {
		spinCuotas.setValue(12);
		txtDescripCuotas.setText("Cuota");
		txtMontoCuota.setText("");
		comboBoxMoneda.setSelectedItem("ARS");
		txtHonorarios.setText("");
		
		checkBoxVto.setSelected(false);
		spinDiaVto.setValue(10);
		txtInteres.setText("");
	}
	
	public boolean validarGenCuotas() {
		boolean esValido = true;
		
		// Validar descripcion
		if (txtDescripCuotas.getText().length() > 30) {
			esValido = false;
			txtDescripCuotas.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			
		}
		else 
			txtDescripCuotas.setBorder(new LineBorder(Color.white));
		
		// Validar monto
		if (!ValidateConstants.validateMoney(txtMontoCuota.getText())) {
			esValido = false;
			txtMontoCuota.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else
			txtMontoCuota.setBorder(new LineBorder(Color.white));
		
		// Validar honorarios
		if (!ValidateConstants.validateMoney(txtHonorarios.getText())) {
			esValido = false;
			txtHonorarios.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else
			txtHonorarios.setBorder(new LineBorder(Color.white));
		
		// Validar Vto
		if (checkBoxVto.isSelected()) {
			// Validar interes
			if (!ValidateConstants.validatePercentage(txtInteres.getText())) {
				esValido = false;
				txtInteres.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
			else
				txtInteres.setBorder(new LineBorder(Color.white));
		}
		
		return esValido;
	}
	
	public void generarCuotas() {
		Integer cantCuotas = (Integer) spinCuotas.getValue();
		
		Integer year = null, month = null, day = null;
		Date fechaVto = null;
		BigDecimal interesDiario = null;
		Boolean tieneVto = false;
		
		if (checkBoxVto.isSelected()) {
			year = 2017;
			month = (Integer) spinMesVto.getValue();
			day = (Integer) spinDiaVto.getValue();

			interesDiario = new BigDecimal(txtInteres.getText());
			tieneVto = true;
			
		}
		
		for (int i = 1; i <= cantCuotas; i++) {
			String estado = FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_NO_PAGO];
			String descripcion = "Cuota "+i+"/"+cantCuotas;
			BigDecimal honorarios = new BigDecimal(txtHonorarios.getText());
			BigDecimal montoTotal = new BigDecimal(txtMontoCuota.getText());
			String moneda = (String) comboBoxMoneda.getSelectedItem();
			
			if (! ValidateConstants.isTextEmpty(txtDescripCuotas.getText()))
				descripcion = txtDescripCuotas.getText()+" "+i+"/"+cantCuotas;
			
			DateFormat srcFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			try {
				fechaVto = srcFormat.parse(day+"/"+month+"/"+year);
				if (Utils.diferenciaDias(new Date(), fechaVto).intValue() > 0) {
					estado = FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_ADEUDA];
				}
			} catch (ParseException e) {
				System.out.println("El formato de la fecha es incorrecto");
				e.printStackTrace();
			}
			
			if (month != null) {
				if (month == 12) {
					month = 1; 
					year++;
				}
				else {
					month++;
				}
			}
			
			
			
			FormaPagoVta formaPagoVta = new FormaPagoVta(null, descripcion, honorarios, montoTotal, moneda, null, new BigDecimal(00.00), fechaVto, interesDiario);
			formaPagoVta.setTieneVto(tieneVto);
			formaPagoVta.setEstado(estado);
			formaPagoVta.setEstaPagado(false);
			((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).getModeloTabla().agregar(formaPagoVta);
		}
	}
	
	/* Overrides */
	
	private static final int COMP_HEIGHT = 30;
	@Override
	protected void addLabelAndSpinner(JPanel panel, JLabel lbl, JSpinner spinner) {
		super.addLabelAndSpinner(panel, lbl, spinner);
		lbl.setPreferredSize(new Dimension(350, COMP_HEIGHT));
		spinner.setPreferredSize(new Dimension(100, COMP_HEIGHT));
		
	}

	@Override
	protected void addLabelAndTxt(JPanel panel, JLabel lbl, JTextField txt) {
		super.addLabelAndTxt(panel, lbl, txt);
		lbl.setPreferredSize(new Dimension(350, COMP_HEIGHT));
		txt.setPreferredSize(new Dimension(250, COMP_HEIGHT));
	}

	@Override
	protected void addCajaLblAndCbx(JPanel panel, JLabel label, JComboBox<String> cbx, String[] modelCbx) {
		super.addCajaLblAndCbx(panel, label, cbx, modelCbx);
		label.setPreferredSize(new Dimension(350, COMP_HEIGHT));
		cbx.setPreferredSize(new Dimension(150, COMP_HEIGHT));
	}
	
	

}
