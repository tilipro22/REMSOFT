package app.alquileres.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import app.alquileres.FrameAlquiler;
import gui.ConfigGUI;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class PanelObservacionesAlq extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	
	public PanelObservacionesAlq() {
		setBackground(ConfigGUI.COLOR_BTN_INFO);
		setBounds(0, 0, FrameAlquiler.WIDTH_PANEL_DYNAMIC, FrameAlquiler.HEIGHT_PANEL_DYNAMIC);
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.WHITE));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 22));
		scrollPane.setViewportView(textArea);

	}

	public JTextArea getTextArea() {
		return textArea;
	}

}
