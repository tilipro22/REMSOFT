package app.alquileres.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import app.alquileres.FrameAlquiler;
import app.alquileres.PanelAlquiler;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.FrameTablaPersonas;
import logica.Persona;

public class PanelGarantiaAlq extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private JPanel panelGarante = new JPanel();
	private JButton btnSearchPersona, btnClear;
	private Persona garante;
	
	private JTextField txtGarante, txtNombre, txtDireccion, txtContacto;
	
	public PanelGarantiaAlq() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, FrameAlquiler.WIDTH_PANEL_DYNAMIC, FrameAlquiler.HEIGHT_PANEL_DYNAMIC);
		setLayout(null);
		setBorder(new LineBorder(Color.WHITE));
		
		initPanelLocatario();
		setConfigBtnClear();
		
	}
	
	private void initPanelLocatario() {
		panelGarante = new JPanel();
		panelGarante.setBackground(ConfigGUI.COLOR_FONDO);
		panelGarante.setBorder(new MatteBorder(1, 0, 1, 0, Color.WHITE));
		panelGarante.setLayout(new FlowLayout(FlowLayout.TRAILING, 20, 12));
		
		
		JLabel lblGarante = new JLabel("Datos del Garante");
		lblGarante.setHorizontalAlignment(SwingConstants.CENTER);
		lblGarante.setForeground(Color.WHITE);
		lblGarante.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGarante.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		panelGarante.add(lblGarante);
		
		JLabel lblInvisible = new JLabel("");
		lblInvisible.setPreferredSize(new Dimension(250, 30));
		panelGarante.add(lblInvisible);
		
		addCajaDeDatos(panelGarante, new JLabel("Garante:"), txtGarante=new JTextField());
		addCajaDeDatos(panelGarante, new JLabel("Nombre:"), txtNombre=new JTextField("..."));
		addCajaDeDatos(panelGarante, new JLabel("Direccion:"), txtDireccion=new JTextField("..."));
		addCajaDeDatos(panelGarante, new JLabel("Contacto:"), txtContacto=new JTextField("..."));
		
		int panelGaranteWidth = 530;
		panelGarante.setBounds((getWidth()-530)/2, 0, panelGaranteWidth, getHeight());
		this.add(panelGarante);
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
			JButton btn = new JButton("Buscar Persona");
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					FrameTablaPersonas frameTablaPersonas = new FrameTablaPersonas();
					((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFrameAlquiler().setEnabled(false);
					frameTablaPersonas.setVisible(true);
				}
			});
			btn.setPreferredSize(new Dimension(150, 35));
			panel.add(textField);
			panel.add(btn);
			return;
		}
		
		
		panel.add(textField);
		
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
		btnClear.setBounds(panelGarante.getWidth()+panelGarante.getX()+12, (panelGarante.getHeight()-128-12), 128, 96);
		
		// events
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i < panelGarante.getComponents().length; i++) {
					if (panelGarante.getComponent(i).getClass().getSimpleName().equals("JTextField"))
						((JTextField)panelGarante.getComponent(i)).setText("");
				}
				
				for (int i=0; i < panelGarante.getComponents().length; i++) {
					if (panelGarante.getComponent(i).getClass().getSimpleName().equals("JTextField"))
						((JTextField)panelGarante.getComponent(i)).setText("");
				}
			}
		});
		
		add(btnClear);
	}
	
	public void setDatosGarante(Persona p) {
		this.garante = p;
		this.txtGarante.setText(p.getDni().toString());
		this.txtGarante.setBorder(new LineBorder(Color.white));
		this.txtNombre.setText(p.getApellido() + ", " + p.getNombre());
		this.txtDireccion.setText(p.getDomicilio() + ", " + p.getCiudad() + ", " + p.getProvincia());
		
		if (p.getContacto().getCelular() != null)
			this.txtContacto.setText(p.getContacto().getCelular().toString());
		else if (p.getContacto().getTelefono() != null)
			this.txtContacto.setText(p.getContacto().getTelefono().toString());
		else if (p.getContacto().getMail() != null)
			this.txtContacto.setText(p.getContacto().getMail());
	}

	
	/* Getters & Setters */
	
	public JTextField getTxtGarante() {
		return txtGarante;
	}
	
	

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JTextField getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(JTextField txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public JTextField getTxtContacto() {
		return txtContacto;
	}

	public void setTxtContacto(JTextField txtContacto) {
		this.txtContacto = txtContacto;
	}

	public void setTxtGarante(JTextField txtGarante) {
		this.txtGarante = txtGarante;
	}

	public JButton getBtnSearchPersona() {
		return btnSearchPersona;
	}

	public Persona getGarante() {
		return garante;
	}
	
	
	

}
