package app.ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.FormaPagoVta;
import logica.Venta;
import logica.constant.ValidateConstants;
import util.Utils;

public class FramePagoVenta extends FrameDefault {

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private List<FormaPagoVta> listFormaPagos;
	private Venta venta;
	private JPanel panelDatos, panelNav, panelTable;
	private int filaSelecc = -1;
	
	/* Panel Nav */
	private ButtonDefaultABM btnAsentarPago, btnModificarPago;
	
	/* Panel Table */
	private final String columns[] = {"Fecha de Pago", "Descripcion", "Fecha Vto.", "Monto", "Pagado"};
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private JScrollPane scrollPane;
	
	/* Panel Asentar PAgo*/
	private JPanel panelAsentarPago, panelFooterPago;
	private JTextField txtDescripcion, txtMonto, txtHonorarios, txtMoneda, txtMontoPagado, txtMontoPagar;
	private JLabel lblVto;
	private JDateChooser dateChoosFechaPago;
	private ButtonDefaultABM btnPagar, btnCancelar;

	
	public FramePagoVenta(Venta venta) {
		setTituloBarra(" REMSOFT | Seguimientos de Pagos");
		setSizeFrame(700, 600);
		setLocationRelativeTo(null);
		
		this.venta = venta;
		this.listFormaPagos = venta.getFormaPagoVtas();
		
		initPanelDatos();
		initPanelNav();
		initPanelTable();
		initPanelAsentarPago();
	}
	
	
	/* Inits */ 
	
	private void initPanelDatos() {
		panelDatos = new JPanel();
		panelDatos.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelDatos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelDatos.setBorder(new LineBorder(Color.white));
		panelDatos.setBounds(10, 35, getWidth()-20, 150);
		
		JLabel lblFechaVta, lblInmueble, lblComprador, lblMontoOriginal;
		JTextField txtMontoOriginal;
		
		setLabelDefault(lblFechaVta = new JLabel("Fecha de Venta: " + Utils.printFecha(venta.getFecha())));
		setLabelDefault(lblInmueble = new JLabel("Inmueble: " + venta.getInmueble().getTipoInmueble()));
		setLabelDefault(lblComprador = new JLabel("Comprador: " + venta.getComprador().getApellido() + ", " + venta.getComprador().getNombre()));
		setLabelDefault(lblMontoOriginal = new JLabel("Monto Original: "));
		lblMontoOriginal.setPreferredSize(new Dimension(150, 25));
		setTextfieldDefault(txtMontoOriginal = new JTextField());
		txtMontoOriginal.setText("$ " + venta.getPrecioVenta());
		txtMontoOriginal.setPreferredSize(new Dimension(250, 30));
		
		panelDatos.add(lblFechaVta);
		panelDatos.add(lblInmueble);
		panelDatos.add(lblComprador);
		panelDatos.add(lblMontoOriginal);
		panelDatos.add(txtMontoOriginal);
		
		add(panelDatos);
	}
	
	private void initPanelNav() {
		panelNav = new JPanel();
		panelNav.setBackground(ConfigGUI.COLOR_FONDO);
		panelNav.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelNav.setBounds(10, panelDatos.getHeight()+panelDatos.getY()+10, getWidth()-20, 50);
		
		btnAsentarPago = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAsentarPago.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAsentarPago();
			}
		});
		btnAsentarPago.setText("Asentar Pago");
		btnAsentarPago.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		
		btnModificarPago = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnModificarPago.setText("Modificar Pago");
		btnModificarPago.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		
		panelNav.add(btnAsentarPago);
		panelNav.add(btnModificarPago);
		
		add(panelNav);
	}

	private void initPanelTable() {
		panelTable = new JPanel();
		panelTable.setLayout(new BorderLayout());
		panelTable.setBounds(10, panelNav.getHeight()+panelNav.getY()+10, getWidth()-20, 250);
		
		modeloTabla = new ModeloTabla(columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {

			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				//{"Fecha de Pago", "Descripcion", "Fecha Vto.", "Monto", "Pagado"};

				FormaPagoVta formaPagoVta = (FormaPagoVta) modeloTabla.getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					if (formaPagoVta.getFechaPago() == null)
						return "Pendiente";
					else
						return Utils.printFecha(formaPagoVta.getFechaPago()) + " | " + formaPagoVta.getEstado();
					
				case 1:
					return formaPagoVta.getDescripcion();
					
				case 2:
					if (formaPagoVta.getTieneVto())
						return Utils.printFecha(formaPagoVta.getFechaVto());
					else
						return "";
					
				case 3:
					return "$ " + Utils.DECIMAL_FORMAT.format(formaPagoVta.getMontoTotal());
					
				case 4:
					if (formaPagoVta.getMontoPagado().compareTo(new BigDecimal("00.00")) == 0)
						return "$ 00.00";
					else
						return "$ " + Utils.DECIMAL_FORMAT.format(formaPagoVta.getMontoPagado());
					
					
				}
				return formaPagoVta;
			}
			
			
		};
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setViewportView(tabla);
		cargarDatos();

		
		panelTable.add(scrollPane, BorderLayout.CENTER);
		//panelTable.setVisible(false);
		add(panelTable);
	}
	
	private void initPanelAsentarPago() {
		
		panelAsentarPago = new JPanel();
		panelAsentarPago.setBackground(ConfigGUI.COLOR_FONDO);
		panelAsentarPago.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		panelAsentarPago.setBounds(10, panelNav.getHeight()+panelNav.getY()+10, getWidth()-20, 280);
		panelAsentarPago.setBorder(new LineBorder(Color.white));
		
		panelAsentarPago.setVisible(false);
		
		configLblAndTxtPago(new JLabel("Descripcion: "), txtDescripcion = new JTextField());
		txtDescripcion.setPreferredSize(new Dimension(400, 30));
		
		configLblAndTxtPago(new JLabel("Monto $: "), txtMonto = new JTextField());
		txtMonto.setEnabled(false);
		configLblAndTxtPago(new JLabel("Pagado $: "), txtMontoPagado = new JTextField());
		txtMontoPagado.setEnabled(false);
		
		configLblAndTxtPago(new JLabel("$ A Pagar: "), txtMontoPagar = new JTextField());
		configLblAndTxtPago(new JLabel("Moneda: "), txtMoneda = new JTextField());
		txtMoneda.setEnabled(false);
		
		configLblAndTxtPago(new JLabel("Fecha Pago: "), null);
		dateChoosFechaPago = new JDateChooser(new Date());
		dateChoosFechaPago.setPreferredSize(new Dimension(200, 30));
		panelAsentarPago.add(dateChoosFechaPago);
		configLblAndTxtPago(new JLabel("Honorarios: "), txtHonorarios = new JTextField());
		
		configLblAndTxtPago(lblVto = new JLabel(""), null);
		lblVto.setPreferredSize(new Dimension(getWidth()-50, 30));
		
		panelFooterPago = new JPanel();
		panelFooterPago.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelFooterPago.setBounds(10, panelAsentarPago.getY()+panelAsentarPago.getHeight(), panelAsentarPago.getWidth()-20, 50);
		panelFooterPago.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooterPago.setVisible(false);
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCancelarPago();
			}
		});
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setText("Cancelar");
		
		btnPagar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnPagar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAceptarPago();
			}
		});
		btnPagar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnPagar.setText("Aceptar");
		
		panelFooterPago.add(btnCancelar);
		panelFooterPago.add(btnPagar);
		add(panelFooterPago);
		
		add(panelAsentarPago);
	}
	
	/*
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, Color.white));
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelFooter.setBounds(10, panelTable.getHeight()+panelTable.getY()+10, getWidth()-20, 50);
		
		add(panelFooter);
	}
	*/
	
	/* Functions */
	private void cargarDatos() {
		for (int i = 0; i < listFormaPagos.size(); i++) {
			modeloTabla.agregar(listFormaPagos.get(i));
		}
	}
	
	private void setDatosPanelPagos () {
		FormaPagoVta formaPagoVta = (FormaPagoVta) modeloTabla.getObjetos().get(filaSelecc);
		
		txtDescripcion.setText(formaPagoVta.getDescripcion());
		txtMonto.setText(Utils.DECIMAL_FORMAT.format(formaPagoVta.getMontoTotal()).toString().replaceAll(",", "."));
		if (formaPagoVta.getHonorarios().compareTo(new BigDecimal(00.00)) == 0)
			txtHonorarios.setText("00.00");
		else
			txtHonorarios.setText(Utils.DECIMAL_FORMAT.format(formaPagoVta.getHonorarios()).toString().replaceAll(",", "."));
		
		if (formaPagoVta.getMontoPagado().compareTo(new BigDecimal(00.00)) == 0) {
			txtMontoPagado.setText("00.00");
		}
		else {
			txtMontoPagado.setText(Utils.DECIMAL_FORMAT.format(formaPagoVta.getMontoPagado()).toString().replaceAll(",", "."));
		}
		
		txtMontoPagar.setText(Utils.DECIMAL_FORMAT.format(formaPagoVta.getMontoTotal().subtract(formaPagoVta.getMontoPagado())).replaceAll(",", "."));
		
		txtMoneda.setText(formaPagoVta.getMoneda());
		
		lblVto.setText("Fecha de Vto: " + Utils.printFecha(formaPagoVta.getFechaVto()) + " |  Estado: " + formaPagoVta.getEstado() + 
				" | Interes diario: " + formaPagoVta.getInteresDiario() + " %");
	}
	
	/* Configs */
	@Override
	protected void setLabelDefault(JLabel label) {
		super.setLabelDefault(label);
		label.setPreferredSize(new Dimension(getWidth()-50, 25));
		label.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	private void setConfigTable() {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	private void setConfigScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setPreferredSize(new Dimension(panelTable.getWidth(), panelTable.getHeight()));
		//scrollPane.setBounds(lblAtras.getWidth(), lblAtras.getHeight(), SCREEN_SIZE.width-200, SCREEN_SIZE.height-400);
	}
	
	private void configLblAndTxtPago (JLabel lbl, JTextField txt) {
		setLabelDefault(lbl);

		lbl.setPreferredSize(new Dimension(100, 30));
		panelAsentarPago.add(lbl);
		
		if (txt != null) {
			setTextfieldDefault(txt);
			txt.setPreferredSize(new Dimension(200, 30));
			txt.setEnabled(true);
			panelAsentarPago.add(txt);
		}
		
		
		if (lbl.getText().equals("Descripcion: ")) {
			JLabel lblInvisible;
			setLabelDefault(lblInvisible = new JLabel(""));
			lblInvisible.setPreferredSize(new Dimension(100, 30));
			panelAsentarPago.add(lblInvisible);
		}
		
	}
	
	/* Validations */
	
	private boolean validarPago() {
		boolean isValido =  true;
		
			// Descripcion
		if (ValidateConstants.isTextEmpty(txtDescripcion.getText()) || txtDescripcion.getText().length() > 50) {
			txtDescripcion.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			isValido = false;
		}
		
			// Monto a Pagar
		if (! ValidateConstants.validateMoney(txtMontoPagar.getText())) {
			txtMontoPagar.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			isValido = false;
		}
		else {
			BigDecimal montoPagar = new BigDecimal(txtMontoPagar.getText());
			
			if (montoPagar.compareTo(new BigDecimal(00.00)) == 0) {
				txtMontoPagar.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
				isValido = false;
			}
			else {
				BigDecimal montoTotal = new BigDecimal(txtMonto.getText());
				BigDecimal montoPagado = new BigDecimal(txtMontoPagado.getText());
				
				BigDecimal montoFinal = montoTotal.subtract(montoPagado);
				
				if (montoPagar.compareTo(montoFinal) > 0) {
					JOptionPane.showMessageDialog(null, "El monto a pagar no puede ser mayor que el monto total.");
					txtMontoPagar.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
					isValido = false;
				}
			}
			
		}
		
			// Honorarios
		if (! ValidateConstants.validateMoney(txtHonorarios.getText())) {
			txtHonorarios.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			isValido = false;
		}
		
			// Fecha Pago
		JTextField txtFecha = ((JTextField) dateChoosFechaPago.getDateEditor().getUiComponent());
		
		if (ValidateConstants.isTextEmpty(txtFecha.getText()) || txtFecha.getForeground().equals(Color.red)) {
			((JTextField) dateChoosFechaPago.getDateEditor().getUiComponent()).setText("ERROR");;
			isValido = false;
		}
		
		return isValido;
	}
	
	/* Events */
	
	@Override
	protected void eventoCerrar() {
		dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
	}
	
	private void eventoAsentarPago() {
		filaSelecc = tabla.getSelectedRow();
		
		if (filaSelecc != -1) {
			
			FormaPagoVta formaPagoVta = (FormaPagoVta) modeloTabla.getObjetos().get(filaSelecc);
			
			if (! formaPagoVta.getEstaPagado()) {
				panelTable.setVisible(false);
				btnAsentarPago.setVisible(false);
				btnModificarPago.setVisible(false);
				
				panelAsentarPago.setVisible(true);
				panelFooterPago.setVisible(true);
				
				setDatosPanelPagos();
			}
			else {
				JOptionPane.showMessageDialog(null, "La cuota selecciona ya esta PAGA");
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	private void eventoCancelarPago() {
		panelAsentarPago.setVisible(false);
		panelFooterPago.setVisible(false);
		
		panelTable.setVisible(true);
		btnAsentarPago.setVisible(true);
		btnModificarPago.setVisible(true);
	}
	
	private void eventoAceptarPago() {
		if (validarPago()) {
			
			FormaPagoVta formaPago = listFormaPagos.get(filaSelecc);
			String descripcion = txtDescripcion.getText();
			BigDecimal montoTotal = new BigDecimal(txtMonto.getText());
			BigDecimal honorarios = new BigDecimal(txtHonorarios.getText());
			Date fechaPago = dateChoosFechaPago.getDate();
			BigDecimal montoPagar = new BigDecimal(txtMontoPagar.getText());
			
			formaPago.setDescripcion(descripcion);
			formaPago.setMontoTotal(montoTotal);
			formaPago.setHonorarios(honorarios);
			formaPago.setFechaPago(fechaPago);
			formaPago.setMontoPagado(formaPago.getMontoPagado().add(montoPagar));
			
			if (montoTotal.compareTo(formaPago.getMontoPagado()) == 0) {
				formaPago.setEstado(FormaPagoVta.ESTADOS_ARRAY[FormaPagoVta.ESTADO_PAGADO]);
				formaPago.setEstaPagado(true);
			}
			
			modeloTabla.modificarObjeto(filaSelecc, formaPago);
			CtrlVentas ctrlVentas=new CtrlVentas();
			ctrlVentas.updatePago(formaPago);
			
			JOptionPane.showMessageDialog(null, "Pago Realizado!");
			
			eventoCancelarPago();
		}
	}

}
