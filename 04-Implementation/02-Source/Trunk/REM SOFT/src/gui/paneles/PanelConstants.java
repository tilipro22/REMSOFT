package gui.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import app.ventas.FrameVenta;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FrameTablaPersonas;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;

public class PanelConstants extends JPanel{

	private static final long serialVersionUID = 1L;
	
	/*
	 * Components Configurations
	 */
	
	protected void setLabelDefault(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
	}
	
	protected void setTextfieldDefault(JTextField textField) {
		
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
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		
	}
	
	protected void setConfigButtonSearch(JPanel panel, JButton btn) {
		btn = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mostrar tabla personas");
				setEnabled(false);
				FrameTablaPersonas frameTablaPersonas=new FrameTablaPersonas();
				frameTablaPersonas.setVisible(true);
				
			}
		});
		btn.setPreferredSize(new Dimension(55, 35));
		btn.setBackground(new Color(78, 78, 78));
		btn.setVerticalTextPosition(SwingConstants.CENTER);
		btn.setIcon(new ImageIcon(FrameVenta.class.getResource("/images/search-24x24.png")));
		
		panel.add(btn);
	}
	
	protected void setConfigCbx(JComboBox<String> cbx, String[] modelCbx) {
		cbx.setModel(new DefaultComboBoxModel<String>(modelCbx));
		cbx.setUI(new BasicComboBoxUI() {
		    @Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setBorder(new LineBorder(Color.WHITE));
		        @SuppressWarnings("rawtypes")
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
		cbx.setPreferredSize(new Dimension(220, 35));
	}
	
	protected void setConfigSpinner(JSpinner spinner) {
		spinner.setRequestFocusEnabled(false);
		spinner.setOpaque(false);
		spinner.setFocusable(false);
		spinner.setFocusTraversalKeysEnabled(false);
		
		JFormattedTextField formattedTextField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		formattedTextField.setEditable(false);
		formattedTextField.setBackground(ConfigGUI.COLOR_FONDO);
		formattedTextField.setForeground(Color.WHITE);
	}
	
	protected void setConfigCheckBox (JCheckBox checkBox) {
		checkBox.setOpaque(false);
		checkBox.setForeground(Color.white);
		checkBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBox.setPreferredSize(new Dimension(250, 35));
	}
	
	/*
	 * Adds (Templates)
	 */
	
	protected void addLabelAndTxt (JPanel panel, JLabel lbl, JTextField txt) {
		setLabelDefault(lbl);
		lbl.setPreferredSize(new Dimension(150, 35));
		setTextfieldDefault(txt);
		txt.setPreferredSize(new Dimension(250, 35));
		panel.add(lbl);
		panel.add(txt);
	}
	
	protected void addLabelAndSpinner (JPanel panel, JLabel lbl, JSpinner spinner) {
		setLabelDefault(lbl);
		lbl.setPreferredSize(new Dimension(150, 35));
		setConfigSpinner(spinner);
		spinner.setPreferredSize(new Dimension(100, 35));
		
		panel.add(lbl);
		panel.add(spinner);
	}
	
	protected void addLabelToPanelNav(JPanel panel, JLabel lbl) {
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		panel.add(lbl);
	}
	
	protected void addCajaLblAndCbx(JPanel panel, JLabel label, JComboBox<String> cbx, String[] modelCbx) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setPreferredSize(new Dimension(150, 35));
		
		setConfigCbx(cbx, modelCbx);
		
		panel.add(label);
		panel.add(cbx);
	}
	
	protected void setConfigTable(JTable tabla, ModeloTabla modeloTabla) {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	protected void setConfigScrollPane(JScrollPane scrollPane) {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
	}
	
	

}
