package gui.tabla;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class Render implements TableCellRenderer{

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		JLabel etiqueta=new JLabel();
		etiqueta.setOpaque(true);
		etiqueta.setForeground(Color.WHITE);
		
		
		/*
		 * Colorear Fila
		 */
		if (row % 2 == 0)
			etiqueta.setBackground(new Color(53, 58, 65));
		else
			etiqueta.setBackground(new Color(46, 51, 56));
		
		/*
		 * Poner texto
		 */
		etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (value == null || value.equals("null"))
			etiqueta.setText("");
		else
			etiqueta.setText(value.toString());
		
		/*
		 * Para una fila seleccionada
		 */
		if (isSelected)
			etiqueta.setBackground(new Color(151, 193, 215));
		
		return etiqueta;
	}

}
