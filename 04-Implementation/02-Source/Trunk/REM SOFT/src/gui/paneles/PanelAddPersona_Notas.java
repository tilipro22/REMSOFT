package gui.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import gui.ConfigGUI;
import logica.Persona;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.BorderLayout;

public class PanelAddPersona_Notas extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private final String[] MODELCBX_TIPO = {"CLIENTE", "MARTILLERO", "ESCRIBANO", "SOLICITANTE"};
	
	private JTextField txtProfesion;
	private JPanel panelOtrosDatos;
	private JCheckBox chckbxMasculino, chckbxFemenino;
	private JComboBox<Object> cbxTipoPersona = new JComboBox<Object>();
	private JTextArea txtAreaObs;
	private JLabel lblMaxCaracteres;
	private JScrollPane scrollPaneObs;
	private int cantCaracteres;

	public PanelAddPersona_Notas() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 450, 450);
		setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		setLayout(null);
		
		//OTROS DATOS
		JLabel lblOtrosDatos=new JLabel("Otros Datos:");
		lblOtrosDatos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOtrosDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtrosDatos.setForeground(Color.WHITE);
		lblOtrosDatos.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		lblOtrosDatos.setBounds(20, 30, 90, 15);
		add(lblOtrosDatos);
		
		panelOtrosDatos = new JPanel();
		panelOtrosDatos.setBackground(ConfigGUI.COLOR_FONDO);
		panelOtrosDatos.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		panelOtrosDatos.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		panelOtrosDatos.setBounds(0, 50, 450, 150);
		add(panelOtrosDatos);
		
		//agregarCajaDatos(new JLabel(), "Profesion 1:", new JTextField());
		inicializarLabel(new JLabel(), "Tipo:");
		inicializarComboBox(cbxTipoPersona);
		agregarCajaDatos(new JLabel(), "Profesion:", txtProfesion=new JTextField());
		inicializarLabel(new JLabel(), "Sexo:     ");
		
		chckbxMasculino=new JCheckBox("Masculino");
		chckbxMasculino.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCheckBox(chckbxMasculino);
			}
		});
		setConfigCheckBox(chckbxMasculino);
		panelOtrosDatos.add(chckbxMasculino);
		
		chckbxFemenino=new JCheckBox("Femenino");
		chckbxFemenino.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCheckBox(chckbxFemenino);
			}
		});
		setConfigCheckBox(chckbxFemenino);
		panelOtrosDatos.add(chckbxFemenino);
		
		
		//OBSERVACIONES
		JLabel lblObservaciones=new JLabel("Observaciones:");
		lblObservaciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObservaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblObservaciones.setForeground(Color.WHITE);
		lblObservaciones.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		lblObservaciones.setBounds(20, 200, 100, 15);
		add(lblObservaciones);
		
		JPanel panelObservaciones = new JPanel();
		panelObservaciones.setBounds(0, 230, getWidth(), 210);
		add(panelObservaciones);
		panelObservaciones.setLayout(new BorderLayout(0, 0));
		
			//CENTER
		scrollPaneObs=new JScrollPane();
		scrollPaneObs.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneObs.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txtAreaObs = new JTextArea();
		txtAreaObs.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				eventoFocusTxtArea();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				eventoFocusTxtArea();
			}
		});
		txtAreaObs.setWrapStyleWord(true);
		txtAreaObs.setLineWrap(true);
		txtAreaObs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPaneObs.setViewportView(txtAreaObs);
		
		panelObservaciones.add(scrollPaneObs, BorderLayout.CENTER);
		
			//EAST Y WEST
		JLabel lblRellenoWest = crearLabelRelleno();
		lblRellenoWest.setBorder(new MatteBorder(0, 2, 0, 0, Color.CYAN));
		panelObservaciones.add(lblRellenoWest, BorderLayout.WEST);
		
		JLabel lblRellenoEast = crearLabelRelleno();
		lblRellenoEast.setBorder(new MatteBorder(0, 0, 0, 2, Color.CYAN));
		panelObservaciones.add(lblRellenoEast, BorderLayout.EAST);
		
			//SOUTH
		lblMaxCaracteres = new JLabel("* Maximo permitido de caracteres (500): "+cantCaracteres+"/500");
		lblMaxCaracteres.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxCaracteres.setForeground(Color.WHITE);
		lblMaxCaracteres.setBackground(ConfigGUI.COLOR_FONDO);
		lblMaxCaracteres.setOpaque(true);
		lblMaxCaracteres.setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
		lblMaxCaracteres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelObservaciones.add(lblMaxCaracteres, BorderLayout.SOUTH);
		
		
	}
	
	private void eventoCheckBox(JCheckBox chckbx) {
		if (chckbx.getText().equals("Masculino")) {
			if (chckbxFemenino.isSelected())
				chckbxFemenino.setSelected(false);
		}
		else {
			if (chckbxMasculino.isSelected())
				chckbxMasculino.setSelected(false);
		}
	}
	
	private void setConfigCheckBox (JCheckBox chckbx) {
		chckbx.setFont(new Font("Tahoma", Font.PLAIN, 21));
		chckbx.setForeground(Color.WHITE);
		chckbx.setOpaque(false);
		chckbx.setBorder(null);
	}
	
	private void agregarCajaDatos(JLabel lbl, String nombreLbl, JTextField txt) {
		inicializarLabel(lbl, nombreLbl);
		inicializarTextField(txt);
	}
	
	private void inicializarLabel(JLabel lbl ,String nombre) {
		lbl.setText(nombre);
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		panelOtrosDatos.add(lbl);
	}
	
	private void inicializarTextField(JTextField txt) {
		txt.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				txt.setBorder(new LineBorder(Color.WHITE));
			}
		});
		
		txt.setBounds(0, 0, getWidth(), 550);
		txt.setForeground(Color.WHITE);
		txt.setFont(new Font("Tahoma", Font.PLAIN, 21));
		txt.setColumns(15);
		txt.setCaretColor(Color.WHITE);
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		txt.setBackground(ConfigGUI.COLOR_FONDO);
		txt.setHorizontalAlignment(SwingConstants.LEADING);
		txt.setPreferredSize(new Dimension(270, 30));
		
		panelOtrosDatos.add(txt);
	}
	
	private void inicializarComboBox (JComboBox<Object> cbx) {
		cbx.setModel(new DefaultComboBoxModel<Object>(MODELCBX_TIPO));
		cbx.setForeground(Color.WHITE);
		cbx.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbx.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		cbx.setBackground(ConfigGUI.COLOR_FONDO);
		cbx.setPreferredSize(new Dimension(270, 30));
		
		panelOtrosDatos.add(cbx);
	}
	
	private JLabel crearLabelRelleno () {
		JLabel lbl=new JLabel("1234");
		lbl.setBackground(ConfigGUI.COLOR_FONDO);
		lbl.setForeground(ConfigGUI.COLOR_FONDO);
		lbl.setOpaque(true);
		
		return lbl;
	}
	
	private void eventoFocusTxtArea () {
		cantCaracteres = txtAreaObs.getText().length();
		lblMaxCaracteres.setText("* Maximo permitido de caracteres (500): "+cantCaracteres+"/500");
	}
	
	public boolean esDatosValido() {
		boolean esValido = true;
		
		if (txtProfesion.getText().length() > 40)
			esValido = txtIncorrecto(txtProfesion);
		else
			txtCorrecto(txtProfesion);
		
		if (txtAreaObs.getText().length() > 500 ) {
			scrollPaneObs.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
			esValido = false;
		}
		else {
			scrollPaneObs.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		}
		
		eventoFocusTxtArea();
		
		return esValido;
	}
	
	public void setDatosNotasPersona(Persona persona) {
		String profesion = txtProfesion.getText();
		Character sexo = 'N';
		
		if (chckbxMasculino.isSelected())
			sexo = 'M';
		if (chckbxFemenino.isSelected())
			sexo ='F';
		
		String observaciones = txtAreaObs.getText();
		
		persona.setProfesion(profesion);
		persona.setSexo(sexo);
		persona.setObservaciones(observaciones);
		persona.setTipo(cbxTipoPersona.getSelectedItem().toString());
	}
	
	public boolean txtCorrecto (JTextField txt) {
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		
		return true;
	}
	
	public boolean txtIncorrecto (JTextField txt) {
		txt.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		
		return false;
	}
	
	public void updatePanel (Persona p) {
		if (p.getProfesion() != null)
			txtProfesion.setText(p.getProfesion());
		
		if (p.getSexo().equals(new Character('F')))
			chckbxFemenino.setSelected(true);
		else if (p.getSexo().equals(new Character('M')))
			chckbxMasculino.setSelected(true);
		
		if(p.getTipo() != null)
			cbxTipoPersona.setSelectedItem(p.getTipo().toUpperCase());
		
		if (p.getObservaciones() != null)
			txtAreaObs.setText(p.getObservaciones());
	}
}
