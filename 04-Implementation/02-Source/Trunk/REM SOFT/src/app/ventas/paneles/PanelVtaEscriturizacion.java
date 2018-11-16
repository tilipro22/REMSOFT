package app.ventas.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import gui.ConfigGUI;

public class PanelVtaEscriturizacion extends JPanel{

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private JSpinner spinDias;
	private JTextField txtMultaDiaria;

	/* constructors */
	public PanelVtaEscriturizacion() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 750, 290);
		setLayout(null);
		
		initTitle();
		initPanelDatos();
	}
	
	/* Inits */
	private void initTitle() {
		JLabel lblTitle = new JLabel("Cantidad de dias validos para la firma del boleto de Compra-Venta para la Escriturización:");
		lblTitle.setOpaque(false);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitle.setForeground(Color.white);
		lblTitle.setBounds(50, 15, getWidth(), 50);
		
		add(lblTitle);
	}
	
	private void initPanelDatos() {
		JPanel panelCantDias = new JPanel();
		panelCantDias.setBackground(ConfigGUI.COLOR_FONDO);
		panelCantDias.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelCantDias.setBounds(0, 70, getWidth(), 70);
		
		JLabel lblCantDias = new JLabel("Cantidad de dias: ");
		lblCantDias.setOpaque(false);
		lblCantDias.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantDias.setForeground(Color.white);
		
		// Spin de dias
		spinDias = new JSpinner();
		spinDias.setModel(new SpinnerNumberModel(0, 0, 28, 1));
		setConfigSpinner(spinDias);
		spinDias.setPreferredSize(new Dimension(50, 35));
		
		
		JPanel panelMultaDiaria = new JPanel();
		panelMultaDiaria.setBackground(ConfigGUI.COLOR_FONDO);
		panelMultaDiaria.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		panelMultaDiaria.setBounds(0, panelCantDias.getHeight()+panelCantDias.getY(), getWidth(), 70);
		
		JLabel lblMultaDiaria = new JLabel("Multa diaria: ");
		lblMultaDiaria.setOpaque(false);
		lblMultaDiaria.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMultaDiaria.setForeground(Color.white);
		
		setConfigTextField(txtMultaDiaria=new JTextField());
		txtMultaDiaria.setPreferredSize(new Dimension(110, 35));
		txtMultaDiaria.setText("00.00");
		
		JLabel lblPorcentaje = new JLabel("%");
		lblPorcentaje.setOpaque(false);
		lblPorcentaje.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorcentaje.setForeground(Color.white);
		
		
		panelCantDias.add(lblCantDias);
		panelCantDias.add(spinDias);
		
		panelMultaDiaria.add(lblMultaDiaria);
		panelMultaDiaria.add(txtMultaDiaria);
		panelMultaDiaria.add(lblPorcentaje);
		
		add(panelCantDias);
		add(panelMultaDiaria);
	}

	/* Configs Components */
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
	

	/* Getters & Setters */
	
	public JSpinner getSpinDias() {
		return spinDias;
	}

	public JTextField getTxtMultaDiaria() {
		return txtMultaDiaria;
	}
	
	
	
}
