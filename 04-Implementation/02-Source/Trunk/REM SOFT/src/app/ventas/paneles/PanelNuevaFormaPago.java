package app.ventas.paneles;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.toedter.calendar.JDateChooser;

import app.ventas.PanelVenta;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import logica.FormaPagoVta;
import logica.constant.ValidateConstants;
import util.Utils;

public class PanelNuevaFormaPago extends JPanel {
	

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private JPanel panelHeader, panelDatos, panelPagoLeft, panelVtoRight, panelFooter;
	
	/* Panel Footer */
	private ButtonDefaultABM btnAceptar, btnCancelar;
	
	/* Panel Datos */
	private JTextField txtDescripcion, txtHorarios, txtMonto;
	private JComboBox<String> cbxMoneda;
	
	/* Panel Pago Left */
	private Checkbox checkboxPago;
	private JDateChooser dateChooserPago;
	private JTextField txtMontoPago;
	
	/* Panel Vto Right */
	private Checkbox checkboxVto;
	private JDateChooser dateChooserVto;
	private JTextField txtInteresVto;

	public PanelNuevaFormaPago() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(25, 50, 750, 350);
		setLayout(null);
		
		initPanelHeader();
		initPanelDatos();
		initPanelPagoLeft();
		initPanelVtoRight();
		initPanelFooter();
	}
	
	/* Inits */
	private void initPanelHeader() {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setBorder(new LineBorder(Color.WHITE));
		panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 15));
		panelHeader.setBounds(0, 0, getWidth(), 50);
		
		JLabel lblTitle = new JLabel("Nueva Forma de Pago");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setForeground(Color.WHITE);
		
		
		panelHeader.add(lblTitle);
		add(panelHeader);
	}

	private void initPanelDatos() {
		panelDatos = new JPanel();
		panelDatos.setBackground(ConfigGUI.COLOR_FONDO);
		panelDatos.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		panelDatos.setBorder(new MatteBorder(0, 1, 0, 1, Color.WHITE));
		panelDatos.setBounds(0, 50, getWidth(), (getHeight()-100)/2);
		
			// Descripcion
		JLabel lblDescripcion = new JLabel(" Descripcion:");
		setConfigLabel(lblDescripcion);
		lblDescripcion.setPreferredSize(new Dimension(80, 35));
		setConfigTextField(txtDescripcion = new JTextField());
		txtDescripcion.setPreferredSize(new Dimension(350, 35));
		
			// Honorarios
		JLabel lblHonorarios = new JLabel(" Honorarios:");
		setConfigLabel(lblHonorarios);
		lblHonorarios.setPreferredSize(new Dimension(80, 35));
		setConfigTextField(txtHorarios = new JTextField());
		txtHorarios.setPreferredSize(new Dimension(100, 35));
		
			// Monto total
		JLabel lblMonto = new JLabel(" Monto:");
		setConfigLabel(lblMonto);
		setConfigTextField(txtMonto = new JTextField());
		txtMonto.setPreferredSize(new Dimension(250, 35));
		
			// Moneda
		JLabel lblMoneda = new JLabel(" Moneda:");
		setConfigLabel(lblMoneda);
		setConfigCbx(cbxMoneda = new JComboBox<String>(), new String[] {"ARS", "USD", "EUR", "BRL"});
		
		
		panelDatos.add(lblDescripcion);
		panelDatos.add(txtDescripcion);
		
		panelDatos.add(lblHonorarios);
		panelDatos.add(txtHorarios);
		
		panelDatos.add(lblMonto);
		panelDatos.add(txtMonto);
		
		panelDatos.add(lblMoneda);
		panelDatos.add(cbxMoneda);
		
		add(panelDatos);
	}
	
	private void initPanelPagoLeft() {
		panelPagoLeft = new JPanel();
		panelPagoLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelPagoLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
		panelPagoLeft.setBorder(new LineBorder(Color.WHITE));
		panelPagoLeft.setBounds(0, 50+panelDatos.getHeight(), panelDatos.getWidth()/2, (getHeight()-100)/2);
		
			// Indicar Pagado
		checkboxPago = new Checkbox();
		checkboxPago.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkboxPago.getState()) {
					dateChooserPago.setEnabled(true);
					txtMontoPago.setEnabled(true);
				}
				else {
					dateChooserPago.setEnabled(false);
					txtMontoPago.setEnabled(false);
				}
			}
		});
		JLabel lblCheckboxPago = new JLabel("Indicar Pagado");
		setConfigLabel(lblCheckboxPago);
		lblCheckboxPago.setHorizontalAlignment(SwingConstants.LEFT);
		lblCheckboxPago.setPreferredSize(new Dimension(200, 25));
		
			// Fecha de Pago
		JLabel lblFechaPago = new JLabel(" Fecha de Pago:");
		setConfigLabel(lblFechaPago);
		lblFechaPago.setPreferredSize(new Dimension(100, 30));
		dateChooserPago = new JDateChooser();
		dateChooserPago.setPreferredSize(new Dimension(175, 30));
		dateChooserPago.setEnabled(false);
		
			// Monto Pagado
		JLabel lblMontoPago = new JLabel(" Monto Pagado:");
		setConfigLabel(lblMontoPago);
		lblMontoPago.setPreferredSize(new Dimension(100, 30));
		setConfigTextField(txtMontoPago=new JTextField());
		txtMontoPago.setPreferredSize(new Dimension(175, 30));
		txtMontoPago.setEnabled(false);
		
			//adds
		panelPagoLeft.add(checkboxPago);
		panelPagoLeft.add(lblCheckboxPago);
		
		panelPagoLeft.add(lblFechaPago);
		panelPagoLeft.add(dateChooserPago);
		
		panelPagoLeft.add(lblMontoPago);
		panelPagoLeft.add(txtMontoPago);
		
		add(panelPagoLeft);
	}
	
	private void initPanelVtoRight() {
		panelVtoRight = new JPanel();
		panelVtoRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelVtoRight.setBorder(new LineBorder(Color.WHITE));
		panelVtoRight.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));
		panelVtoRight.setBounds(getWidth()/2, 50+panelDatos.getHeight(), panelDatos.getWidth()/2, (getHeight()-100)/2);
		
			// Indicar Vencimiento
		checkboxVto = new Checkbox();
		checkboxVto.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (checkboxVto.getState()) {
					dateChooserVto.setEnabled(true);
					txtInteresVto.setEnabled(true);
				}
				else {
					dateChooserVto.setEnabled(false);
					txtInteresVto.setEnabled(false);
				}
			}
		});
		JLabel lblCheckboxVto = new JLabel("Indicar Vencimiento");
		setConfigLabel(lblCheckboxVto);
		lblCheckboxVto.setHorizontalAlignment(SwingConstants.LEFT);
		lblCheckboxVto.setPreferredSize(new Dimension(200, 25));
		
			// Fecha de Vencimiento
		JLabel lblFechaVto = new JLabel(" Fecha de Vto.:");
		setConfigLabel(lblFechaVto);
		lblFechaVto.setPreferredSize(new Dimension(100, 30));
		dateChooserVto = new JDateChooser();
		dateChooserVto.setPreferredSize(new Dimension(175, 30));
		dateChooserVto.setEnabled(false);
		
			// Monto Pagado
		JLabel lblInteres = new JLabel(" Interes Diario:");
		setConfigLabel(lblInteres);
		lblInteres.setPreferredSize(new Dimension(100, 30));
		setConfigTextField(txtInteresVto=new JTextField());
		txtInteresVto.setPreferredSize(new Dimension(135, 30));
		txtInteresVto.setEnabled(false);
		
		JLabel lblPorc = new JLabel("%");
		setConfigLabel(lblPorc);
		lblPorc.setHorizontalAlignment(SwingConstants.LEFT);
		lblPorc.setPreferredSize(new Dimension(20, 30));
		
		
		panelVtoRight.add(checkboxVto);
		panelVtoRight.add(lblCheckboxVto);
		
		panelVtoRight.add(lblFechaVto);
		panelVtoRight.add(dateChooserVto);
		
		panelVtoRight.add(lblInteres);
		panelVtoRight.add(txtInteresVto);
		panelVtoRight.add(lblPorc);
		
		add(panelVtoRight);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 10));
		panelFooter.setBorder(new LineBorder(Color.WHITE));
		panelFooter.setBounds(0, getHeight()-50, getWidth(), 50);
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventCancelar();
				
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setPreferredSize(new Dimension(150, 30));
		
		btnAceptar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (eventAceptar()) {
					agregarFormaPago();
				}
			}
		});
		btnAceptar.setText("Agregar");
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAceptar.setPreferredSize(new Dimension(150, 30));
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnAceptar);
		add(panelFooter);
		// button x=? , y=35
		
	}
	
	
	/* Configs Components*/
	
	private void setConfigLabel(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(80, 35));
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
		cbx.setPreferredSize(new Dimension(200, 35));
	}
	
	private void clearDatos() {
		txtDescripcion.setText("");
		txtHorarios.setText("");
		txtInteresVto.setText("");
		txtMonto.setText("");
		txtMontoPago.setText("");
		cbxMoneda.setSelectedIndex(0);
		dateChooserPago.setDate(null);
		dateChooserVto.setDate(null);
		checkboxPago.setState(false);
		checkboxVto.setState(false);
	}
	
	
	/* Functions */
	private void agregarFormaPago() {
		String estado = FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_NO_PAGO];
		boolean isAgregar = ((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).isAgregarPago();
		
		String descripcion = txtDescripcion.getText();
		BigDecimal honorarios = new BigDecimal(txtHorarios.getText());
		BigDecimal montoTotal = new BigDecimal(txtMonto.getText());
		String moneda = (String) cbxMoneda.getSelectedItem();
		
		Date fechaPago = null;
		BigDecimal montoPagado  = new BigDecimal(00.00);
		Boolean estaPagado = false;
		if (checkboxPago.getState()) {
			fechaPago = dateChooserPago.getDate();
			montoPagado = new BigDecimal(txtMontoPago.getText());
			
			if (montoPagado.compareTo(montoTotal) == 0) {
				estado = FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_PAGADO];
				estaPagado = true;
			}
		}
		
		Date fechaVto = null;
		BigDecimal interesVto = null;
		Boolean tieneVto = false;
		if (checkboxVto.getState()) {
			fechaVto = dateChooserVto.getDate();
			interesVto = new BigDecimal(txtInteresVto.getText());
			tieneVto = true;
			
			if (! estaPagado && (Utils.diferenciaDias(new Date(), fechaVto) > 0) ) {
				estado = FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_ADEUDA];
			}
		}
		
		FormaPagoVta formaPagoVta = new FormaPagoVta(null, descripcion, honorarios, montoTotal, moneda, fechaPago, montoPagado, fechaVto, interesVto);
		formaPagoVta.setTieneVto(tieneVto);
		formaPagoVta.setEstaPagado(estaPagado);
		formaPagoVta.setEstado(estado);
		
		if (isAgregar) {
			((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).getModeloTabla().agregar(formaPagoVta);
			((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).setAgregarPago(false);
		}
		else {
			((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).modificarFormaPago(formaPagoVta);
			((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).setModificarPago(false);
		}
		
		((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).configButtons(1);
		eventClose();
	}
	
	public void setValoresModificar (FormaPagoVta formaPagoVta) {
		txtDescripcion.setText(formaPagoVta.getDescripcion());
		txtHorarios.setText(formaPagoVta.getHonorarios().toString());
		txtMonto.setText(formaPagoVta.getMontoTotal().toString());
		cbxMoneda.setSelectedItem(formaPagoVta.getMoneda());
		
		if (formaPagoVta.getFechaPago() != null) {
			checkboxPago.setState(true);
			checkboxPago.setEnabled(false);
			dateChooserPago.setDate(formaPagoVta.getFechaPago());
			txtMontoPago.setText(formaPagoVta.getMontoPagado().toString());
		}
		
		if (formaPagoVta.getFechaVto() != null) {
			checkboxVto.setState(true);
			checkboxVto.setEnabled(false);
			dateChooserVto.setDate(formaPagoVta.getFechaVto());
			dateChooserVto.setEnabled(true);
			txtInteresVto.setText(formaPagoVta.getInteresDiario().toString());
			txtInteresVto.setEnabled(true);
		}
	}
	
	/* Events */
	
	private void eventClose() {
		this.setVisible(false);
		
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNuevaFormaPago().setVisible(false);
		clearDatos();
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelInfo().setVisible(true);
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNav().setVisible(true);
		((PanelVtaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelVtaFormaPago()).configButtons(1);
	}
	
	private void eventCancelar() {
		eventClose();
	}
	
	private boolean eventAceptar() {
		boolean valido = true;
		BigDecimal montoTotal = null;
		
		
		if (ValidateConstants.isTextEmpty(txtDescripcion.getText()) || txtDescripcion.getText().length() > 150) {
			valido = false;
			txtDescripcion.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else { txtDescripcion.setBorder(new LineBorder(Color.white)); }
		
		
		if (ValidateConstants.isTextEmpty(txtHorarios.getText()) || !ValidateConstants.validateMoney(txtHorarios.getText())) {
			valido = false;
			txtHorarios.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else { txtHorarios.setBorder(new LineBorder(Color.white)); }
		
		
		if (ValidateConstants.isTextEmpty(txtMonto.getText()) || !ValidateConstants.validateMoney(txtMonto.getText())) {
			valido = false;
			txtMonto.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else { 
			txtMonto.setBorder(new LineBorder(Color.white)); 
			montoTotal = new BigDecimal(txtMonto.getText());
		}
		
		
		if (checkboxPago.getState()) {
			
			if (!ValidateConstants.validateDateOfDateChooser(dateChooserPago)) {
				valido = false;
				((JTextField) dateChooserPago.getDateEditor().getUiComponent()).setText("ERROR");
			}
			
			if (!ValidateConstants.validateMoney(txtMontoPago.getText())) {
				valido = false;
				txtMontoPago.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
			else if (montoTotal != null) {
				BigDecimal montoPagado = new BigDecimal(txtMontoPago.getText());
				
				//System.out.println(montoPagado.);
				
				if (montoTotal.compareTo(montoPagado) < 0) {
					valido = false;
					txtMontoPago.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
					JOptionPane.showMessageDialog(null, "El monto pagado no puede ser mayor al monto a pagar");
				}
				else {
					txtMontoPago.setBorder(new LineBorder(Color.white)); 
				}
			}
		}
		
		
		if (checkboxVto.getState()) {
			
			if (!ValidateConstants.validateDateOfDateChooser(dateChooserVto)) {
				valido = false;
				((JTextField) dateChooserVto.getDateEditor().getUiComponent()).setText("ERROR");
			}
			
			if (!ValidateConstants.validatePercentage(txtInteresVto.getText())) {
				valido = false;
				txtInteresVto.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			}
			else {txtInteresVto.setBorder(new LineBorder(Color.white));}
			
		}
		
		
		return valido;
	}

	
	
}
