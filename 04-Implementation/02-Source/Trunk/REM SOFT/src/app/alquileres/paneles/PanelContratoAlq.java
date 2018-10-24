package app.alquileres.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import app.alquileres.FrameAlquiler;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;

public class PanelContratoAlq extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelLeft, panelRight;
	
	/* Panel Left*/
	JTextField txtMontoTotal, txtCantDias;
	JDateChooser dateChooserInicioContrato, dateChooserFinContrato;
	
	/* Panel Right*/
	JSpinner spinDiaVto, spinMesesDeposito;
	JTextField txtPrecioDesposito, txtMora;
	
	public PanelContratoAlq() {

		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, FrameAlquiler.WIDTH_PANEL_DYNAMIC, FrameAlquiler.HEIGHT_PANEL_DYNAMIC);
		setLayout(null);
		
		initPanelLeft();
		initPanelRight();
	}

	private void initPanelLeft() {
		panelLeft = new JPanel();
		panelLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelLeft.setLayout(new FlowLayout(FlowLayout.TRAILING, 32, 12));
		panelLeft.setBounds(0, 0, getWidth()/2, getHeight());
		panelLeft.setBorder(new MatteBorder(0, 1, 1, 0, Color.WHITE));
		
		
		// Monto Total
		JLabel lblMontoTotal = new JLabel("Monto Total: ");
		setConfigLabel(lblMontoTotal);
		lblMontoTotal.setPreferredSize(new Dimension(170, 35));
		
		txtMontoTotal = new JTextField("");
		txtMontoTotal.setEnabled(false);
		setConfigTextField(txtMontoTotal);
		txtMontoTotal.setPreferredSize(new Dimension(250, 35));;
		
		// Fecha Inicio
		JLabel lblFechaInicio = new JLabel("Fecha de Inicio: ");
		setConfigLabel(lblFechaInicio);
		lblFechaInicio.setPreferredSize(new Dimension(170, 35));
		
		dateChooserInicioContrato = new JDateChooser();
		dateChooserInicioContrato.setPreferredSize(new Dimension(250, 35));

		// Fecha Fin
		JLabel lblFechaFin = new JLabel("Fecha de Finalizacion: ");
		setConfigLabel(lblFechaFin);
		lblFechaFin.setPreferredSize(new Dimension(170, 35));
		
		dateChooserFinContrato = new JDateChooser();
		dateChooserFinContrato.setPreferredSize(new Dimension(250, 35));
		
		// Cantidad de Dias
		JLabel lblCantDias = new JLabel("Cantidad de Dias: ");
		setConfigLabel(lblCantDias);
		lblCantDias.setPreferredSize(new Dimension(170, 35));
		
		txtCantDias = new JTextField("");
		txtCantDias.setEnabled(false);
		setConfigTextField(txtCantDias);
		txtCantDias.setPreferredSize(new Dimension(250, 35));
		
		// Button Calcular Dias
	
		ButtonDefaultABM btnCalcularDias = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCalcularDias.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				calcularDuracion();
			}
			
		});
		btnCalcularDias.setText("Calcular Dias");
		btnCalcularDias.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCalcularDias.setVerticalAlignment(SwingConstants.CENTER);
		btnCalcularDias.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		btnCalcularDias.setPreferredSize(new Dimension(150, 35));
	
		
		panelLeft.add(lblMontoTotal);
		panelLeft.add(txtMontoTotal);
		panelLeft.add(lblFechaInicio);
		panelLeft.add(dateChooserInicioContrato);
		panelLeft.add(lblFechaFin);
		panelLeft.add(dateChooserFinContrato);
		panelLeft.add(lblCantDias);
		panelLeft.add(txtCantDias);
		panelLeft.add(btnCalcularDias);
		add(panelLeft);
	}
	
	private void initPanelRight() {
		panelRight = new JPanel();
		panelRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelRight.setLayout(new FlowLayout(FlowLayout.LEFT, 32, 12));
		panelRight.setBounds(getWidth()/2, 0, getWidth()/2, getHeight());
		panelRight.setBorder(new MatteBorder(0, 0, 1, 1, Color.WHITE));
		//panelLeft.setBorder(new MatteBorder(top, left, bottom, right, matteColor));
		
		// Dia de Vencimiento
		JLabel lblMontoVto = new JLabel("Dia Vto: ");
		setConfigLabel(lblMontoVto);
		lblMontoVto.setPreferredSize(new Dimension(170, 35));
		
		spinDiaVto = new JSpinner();
		spinDiaVto.setModel(new SpinnerNumberModel(10, 1, 28, 1));
		setConfigSpinner(spinDiaVto);
		spinDiaVto.setPreferredSize(new Dimension(50, 35));
		
		// Cantidad de Meses de Deposito
		JLabel lblMesesDeposito = new JLabel(" Meses de Deposito: ");
		setConfigLabel(lblMesesDeposito);
		
		spinMesesDeposito = new JSpinner();
		spinMesesDeposito.setModel(new SpinnerNumberModel(0, 0, 12, 1));
		setConfigSpinner(spinMesesDeposito);
		spinMesesDeposito.setPreferredSize(new Dimension(50, 35));
		
		// Precio del Deposito
		JLabel lblPrecioDeposito = new JLabel(" Deposito ($): ");
		setConfigLabel(lblPrecioDeposito);
		lblPrecioDeposito.setPreferredSize(new Dimension(170, 35));
		
		txtPrecioDesposito = new JTextField();
		setConfigTextField(txtPrecioDesposito);
		txtPrecioDesposito.setPreferredSize(new Dimension(250, 35));
		
		// Porcentajer de Mora Diario
		JLabel lblMora = new JLabel(" Mora Diario (%): ");
		setConfigLabel(lblMora);
		lblMora.setPreferredSize(new Dimension(170, 35));
		
		txtMora = new JTextField();
		setConfigTextField(txtMora);
		txtMora.setPreferredSize(new Dimension(250, 35));
		
		
		panelRight.add(lblMontoVto);
		panelRight.add(spinDiaVto);
		panelRight.add(lblMesesDeposito);
		panelRight.add(spinMesesDeposito);
		panelRight.add(lblPrecioDeposito);
		panelRight.add(txtPrecioDesposito);
		panelRight.add(lblMora);
		panelRight.add(txtMora);
		add(panelRight);
	}
	
	private void setConfigLabel(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
	}
	
	private void setConfigTextField(JTextField textField) {
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
		textField.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	private void setConfigSpinner(JSpinner spinner) {
		spinner.setRequestFocusEnabled(false);
		spinner.setOpaque(false);
		spinner.setFocusable(false);
		spinner.setFocusTraversalKeysEnabled(false);
		
		JFormattedTextField formattedTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		formattedTextField.setEditable(false);
		formattedTextField.setBackground(ConfigGUI.COLOR_FONDO);
		formattedTextField.setForeground(Color.WHITE);
	}
	
	/* Functions */
	
	public void calcularDuracion() {
		String fechaInicio = ((JTextField) dateChooserInicioContrato.getDateEditor().getUiComponent()).getText();
		String fechaFin = ((JTextField) dateChooserFinContrato.getDateEditor().getUiComponent()).getText();

		if (fechaInicio.equals("") || fechaFin.equals("") || fechaInicio.toUpperCase().equals("ERROR") || fechaFin.toUpperCase().equals("ERROR") ||
				dateChooserInicioContrato.getForeground().equals(Color.red) || dateChooserFinContrato.equals(Color.red)) {
			txtCantDias.setText("");
		}
		else {
			String[] splitFechaInicio = fechaInicio.split("/");
			String[] splitFechaFin = fechaFin.split("/");
			
			java.util.GregorianCalendar date1=new java.util.GregorianCalendar(Integer.parseInt(splitFechaInicio[2]),
					Integer.parseInt(splitFechaInicio[1]),Integer.parseInt(splitFechaInicio[0]));
			java.util.GregorianCalendar date2=new java.util.GregorianCalendar(Integer.parseInt(splitFechaFin[2]),
					Integer.parseInt(splitFechaFin[1]),Integer.parseInt(splitFechaFin[0]));
			
			Long difms=date2.getTimeInMillis() - date1.getTimeInMillis();
			Long difd=difms / (1000 * 60 * 60 * 24);
			
			if (difd < 1) {
				txtCantDias.setText("ERROR");
				txtCantDias.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
			else {
				txtCantDias.setText(difd+"");
				txtCantDias.setBorder(new LineBorder(Color.WHITE));
			}
		}
	}
	
	/* Getters & Setters */
	
	public JTextField getTxtMontoTotal() {
		return txtMontoTotal;
	}

	public JDateChooser getDateChooserInicioContrato() {
		return dateChooserInicioContrato;
	}

	public JDateChooser getDateChooserFinContrato() {
		return dateChooserFinContrato;
	}
	
	public void setDateChooserInicioContrato(JDateChooser dateChooserInicioContrato) {
		this.dateChooserInicioContrato = dateChooserInicioContrato;
	}

	public void setDateChooserFinContrato(JDateChooser dateChooserFinContrato) {
		this.dateChooserFinContrato = dateChooserFinContrato;
	}

	public JTextField getTxtCantDias() {
		return txtCantDias;
	}

	public JSpinner getSpinDiaVto() {
		return spinDiaVto;
	}

	public JSpinner getSpinMesesDeposito() {
		return spinMesesDeposito;
	}

	public JTextField getTxtPrecioDesposito() {
		return txtPrecioDesposito;
	}

	public JTextField getTxtMora() {
		return txtMora;
	}
	
	

	
}
