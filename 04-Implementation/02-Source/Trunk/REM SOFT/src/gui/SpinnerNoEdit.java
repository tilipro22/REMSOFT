package gui;

import java.awt.Color;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SpinnerNoEdit extends JSpinner{

	private static final long serialVersionUID = 1L;
	private JFormattedTextField tf;
	
	public SpinnerNoEdit(int valorInicial, int minimo, int maximo, int saltoEn) {
		setModel(new SpinnerNumberModel(valorInicial, minimo, maximo, saltoEn));
		setRequestFocusEnabled(false);
		setOpaque(false);
		setFocusable(false);
		setFocusTraversalKeysEnabled(false);
		
		tf = ( (JSpinner.DefaultEditor) this.getEditor()).getTextField();
		tf.setEditable(false);
		tf.setBackground(Color.WHITE);
		
		
		
		
	}
	
}
