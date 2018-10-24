package app.ventas.paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;

import com.toedter.calendar.JDateChooser;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;

public class PanelVtaContrato extends JPanel {

	/* elements */
	JPanel panelLeft, panelCenter, panelRight;
	
	/* Panel Left */
	private JDateChooser dateChoosFecha;
	private JTextField txtPrecio, txtComisionPorcentaje, txtComisionTotal;
	private JComboBox<String> cbxMoneda;
	
	public PanelVtaContrato() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 750, 290);
		setLayout(null);

		initPanelLeft();
		initPanelCenter();
		initPanelRight();
	}
	
	private void initPanelLeft() {
		panelLeft = new JPanel();
		panelLeft.setBounds(0, 0, getWidth()/3, getHeight());
		panelLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelLeft.setLayout(new FlowLayout(SwingConstants.TRAILING, 15, 15));
		
		JLabel lblFecha = new JLabel("Fecha: ");
		setConfigLabel(lblFecha);
		lblFecha.setPreferredSize(new Dimension(75, 35));
		
		dateChoosFecha = new JDateChooser();
		dateChoosFecha.setPreferredSize(new Dimension(135, 30));
		
		JLabel lblPrecio = new JLabel("Precio Vta: ");
		setConfigLabel(lblPrecio);
		lblPrecio.setPreferredSize(new Dimension(75, 35));
		
		txtPrecio = new JTextField();
		setConfigTextField(txtPrecio);
		txtPrecio.setEnabled(false);
		txtPrecio.setDisabledTextColor(Color.WHITE);
		txtPrecio.setText("00.00");
		txtPrecio.setPreferredSize(new Dimension(135, 30));
		
		JLabel lblMoneda = new JLabel("Moneda: ");
		setConfigLabel(lblMoneda);
		lblMoneda.setPreferredSize(new Dimension(75, 35));
		
		cbxMoneda = new JComboBox<>();
		cbxMoneda.setBackground(Color.lightGray);
		cbxMoneda.setRequestFocusEnabled(false);
		cbxMoneda.setPreferredSize(new Dimension(135, 30));
		cbxMoneda.setFocusable(false);
		
		cbxMoneda.setModel(new DefaultComboBoxModel<>(new String[] {"ARS", "USD", "EUR", "BRL"}));
		cbxMoneda.setSelectedIndex(0);
		cbxMoneda.setEnabled(false);
		cbxMoneda.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint (Graphics g) {
				setForeground(Color.black);
				super.paint(g);
			}
		});

		panelLeft.add(lblFecha);
		panelLeft.add(dateChoosFecha);
		panelLeft.add(lblPrecio);
		panelLeft.add(txtPrecio);
		panelLeft.add(lblMoneda);
		panelLeft.add(cbxMoneda);
		add(panelLeft);
	}
	
	private void setConfigLabel(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	private void initPanelCenter() {
		panelCenter = new JPanel();
		panelCenter.setBounds(getWidth()/3, 0, getWidth()/3, getHeight());
		panelCenter.setBackground(ConfigGUI.COLOR_FONDO);
		panelCenter.setLayout(new FlowLayout(SwingConstants.TRAILING, 15, 15));
		
		JLabel lblComisionPorcentaje = new JLabel("Com. %: ");
		setConfigLabel(lblComisionPorcentaje);
		lblComisionPorcentaje.setPreferredSize(new Dimension(85, 35));
		
		txtComisionPorcentaje = new JTextField("");
		setConfigTextField(txtComisionPorcentaje);
		txtComisionPorcentaje.setPreferredSize(new Dimension(135, 30));
		
		JLabel lblTotalPorcentaje = new JLabel("Comision $: ");
		setConfigLabel(lblTotalPorcentaje);
		lblComisionPorcentaje.setPreferredSize(new Dimension(75, 35));
		
		txtComisionTotal = new JTextField("");
		setConfigTextField(txtComisionTotal);
		txtComisionTotal.setPreferredSize(new Dimension(135, 30));
		txtComisionTotal.setEnabled(false);
		
		JLabel lblInvisible = new JLabel("          ");
		lblInvisible.setPreferredSize(new Dimension(75, 35));
		
		ButtonDefaultABM btnCalcular = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCalcular.setPreferredSize(new Dimension(135, 30));
		btnCalcular.setText("Calcular");
		btnCalcular.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		
		panelCenter.add(lblComisionPorcentaje);
		panelCenter.add(txtComisionPorcentaje);
		panelCenter.add(lblTotalPorcentaje);
		panelCenter.add(txtComisionTotal);
		panelCenter.add(lblInvisible);
		panelCenter.add(btnCalcular);
		add(panelCenter);
	}
	
	private void initPanelRight() {
		panelRight = new JPanel();
		panelRight.setBounds(panelCenter.getWidth()+panelCenter.getX(), 0, getWidth()/3, getHeight());
		panelRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelRight.setLayout(new FlowLayout(SwingConstants.TRAILING, 15, 15));
		
		JLabel lblDetalle = new JLabel("Detalle: ");
		setConfigLabel(lblDetalle);
		lblDetalle.setPreferredSize(new Dimension(70, 30));
		lblDetalle.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		
		JTextArea txtareaDetalle = new JTextArea();
		txtareaDetalle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtareaDetalle.setLineWrap(true);
		txtareaDetalle.setPreferredSize(new Dimension(230, 200));
		
		panelRight.add(lblDetalle);
		panelRight.add(txtareaDetalle);
		add(panelRight);
	}

	/* Getters & Setters */
	
	public JDateChooser getDateChoosFecha() {
		return dateChoosFecha;
	}

	public JTextField getTxtPrecio() {
		return txtPrecio;
	}

	public JTextField getTxtComisionPorcentaje() {
		return txtComisionPorcentaje;
	}

	public JTextField getTxtComisionTotal() {
		return txtComisionTotal;
	}

	public JComboBox<String> getCbxMoneda() {
		return cbxMoneda;
	}

	
	
}
