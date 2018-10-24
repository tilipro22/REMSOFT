package app.ventas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import logica.Venta;
import util.Utils;

public class FrameEscriturar extends FrameDefault {

	private static final long serialVersionUID = 1L;

	
	/* Elements */
	private JPanel panelDatos, panelFooter;
	private Date fechaVto;
	private BigDecimal multaDiaria, totalVta, totalPagar = new BigDecimal(00.00);
	
	/* Panel Datos */
	private JTextField txtFechaVto, txtMultaDiaria, txtTotalPagar;
	private JCheckBox checkBoxPagar;
	
	/* Panel Footer */
	private ButtonDefaultABM btnCancelar, btnEscriturar;
	
	public FrameEscriturar(Date fechaVto, BigDecimal multaDiaria, BigDecimal totalVta) {
		setTituloBarra("REMSOFT | Escriturizacion de Contrato Compra-Venta");
		setColorBackground(ConfigGUI.COLOR_FONDO_ALT);
		setSizeFrame(450, 450);
		setLocationRelativeTo(null);
		this.fechaVto = fechaVto;
		this.multaDiaria = multaDiaria;
		this.totalVta = totalVta;
		
		initPanelDatos();
		initPanelFooter();
	}
	
	private void initPanelDatos() {
		panelDatos = new JPanel();
		panelDatos.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelDatos.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelDatos.setBounds(10, 35, getWidth()-20, getHeight()-95);
		
		JLabel lblFechaVto, lblEstadoVto, lblMultaDiaria, lblTotalPagar;
		
		setLabelDefault(lblFechaVto = new JLabel("Fecha de Vto: "));
		setTextfieldDefault(txtFechaVto = new JTextField());
		txtFechaVto.setPreferredSize(new Dimension(150, 35));
		
		setLabelDefault(lblEstadoVto = new JLabel("Estado Actual: AL DIA.")); 
		Integer diffDias = 0;
		if (fechaVto == null) 
			txtFechaVto.setText("NO TIENE");
		else {
			txtFechaVto.setText(Utils.printFecha(fechaVto));
			
			diffDias = Utils.diferenciaDias(new Date(), fechaVto).intValue();
			if (diffDias > 0) {
				setLabelDefault(lblEstadoVto = new JLabel("Estado Actual: VENCIDO."));
			}
		}
		
		
		lblEstadoVto.setPreferredSize(new Dimension(panelDatos.getWidth()-50, 35));
		lblEstadoVto.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		setLabelDefault(lblMultaDiaria = new JLabel("Multa Diaria: "));
		setTextfieldDefault(txtMultaDiaria = new JTextField());
		txtMultaDiaria.setPreferredSize(new Dimension(150, 35));
		txtMultaDiaria.setText(multaDiaria + " %");
		
		setLabelDefault(lblTotalPagar = new JLabel("Total a Pagar: "));
		setTextfieldDefault(txtTotalPagar = new JTextField());
		txtTotalPagar.setPreferredSize(new Dimension(150, 35));
		
		totalPagar = totalVta.multiply(multaDiaria).multiply(new BigDecimal(diffDias));
		if (totalPagar.compareTo(new BigDecimal(00.00)) == 0) {
			txtTotalPagar.setText("$ 00.00");
		}
		else {
			txtTotalPagar.setText("$ " + Utils.DECIMAL_FORMAT.format(totalPagar));
		}
		
		setConfigCheckBox(checkBoxPagar = new JCheckBox("Incluir Total ($)"));
		checkBoxPagar.setSelected(true);
		
		
		panelDatos.add(lblFechaVto);
		panelDatos.add(txtFechaVto);
		
		panelDatos.add(lblEstadoVto);
		
		panelDatos.add(lblMultaDiaria);
		panelDatos.add(txtMultaDiaria);
		
		panelDatos.add(lblTotalPagar);
		panelDatos.add(txtTotalPagar);
		
		panelDatos.add(checkBoxPagar);
		
		add(panelDatos);
	}
	
	private void initPanelFooter() {
		
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, Color.white));
		panelFooter.setBounds(10, panelDatos.getHeight()+panelDatos.getY(), getWidth()-20, 50);
		
		btnCancelar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCancelar();
			}
		});
		btnCancelar.setText("Cancelar");
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		
		btnEscriturar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnEscriturar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoEscriturar();
				
			}
		});
		btnEscriturar.setText("Escriturar");
		btnEscriturar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		
		panelFooter.add(btnCancelar);
		panelFooter.add(btnEscriturar);
		
		add(panelFooter);
	}
	
	/* Configs */
	
	@Override
	protected void setLabelDefault(JLabel label) {
		super.setLabelDefault(label);
		label.setPreferredSize(new Dimension(150, 35));
	}
	
	private void setConfigCheckBox (JCheckBox checkBox) {
		checkBox.setOpaque(false);
		checkBox.setForeground(Color.white);
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBox.setFocusable(false);
		checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
		checkBox.setPreferredSize(new Dimension(300, 35));
	}

	@Override
	protected void eventoCerrar() {
		dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
	}
	
	private void eventoCancelar() {
		eventoCerrar();
	}
	
	private void eventoEscriturar() {
		CtrlVentas ctrlVentas=new CtrlVentas();
		int filaSelecc = ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFilaSeleccEscriturar();
		Venta venta = ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getVentaSeleccionada();
		venta.setEstado(Venta.ESTADOS_ARRAY[Venta.ESTADO_ESCRITURIZADO]);
		venta.setEscriturizado(true);
		
		((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getModeloTabla().modificarObjeto(filaSelecc, venta);
		
		ctrlVentas.updateEscriturarVenta(venta.getIdVenta());
		eventoCerrar();
	}

}
