package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;


public class ButtonDefaultABM extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int BOTON_DEFAULT = 0;
	public static final int BOTON_AGREGAR = 1;
	public static final int BOTON_MODIFICAR = 2;
	public static final int BOTON_ELIMINAR = 3;
	public static final int BOTON_VER_PAGOS = 4;
	public static final int BOTON_ASENTAR_PAGO = 5;
	public static final int BOTON_CERRAR = 6;
	public static final int BOTON_ESCRITURAR = 7;
	
	public ButtonDefaultABM(int opcion) {
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalAlignment(SwingConstants.TOP);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setForeground(Color.WHITE);
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setFocusable(false);
		setBorderPainted(false);
		setPreferredSize(new Dimension(80, 90));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(ConfigGUI.COLOR_FONDO);
		
		setGUIButton(opcion);
		
		eventoMouse();
		
	}
	
	private void setGUIButton(int opcion) {
		switch (opcion) {
		case BOTON_AGREGAR:
			setText("Agregar");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/sign-add-64x64.png")));
			break;
			
		case BOTON_MODIFICAR:
			setText("Modificar");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/sign-change-64x64.png")));
			break;
			
		case BOTON_ELIMINAR:
			setText("Eliminar");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/sign-ban-64x64.png")));
			break;
			
		case BOTON_DEFAULT:
			setVerticalAlignment(SwingConstants.CENTER);
			setPreferredSize(new Dimension(150, 40));
			setFont(new Font("Tahoma", Font.PLAIN, 14));
			break;
			
		case BOTON_VER_PAGOS:
			setText("Ver Pagos");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/ver-pagos-64x64.png")));
			setPreferredSize(new Dimension(90, 90));
			break;
			
		case BOTON_ASENTAR_PAGO:
			setText("Asentar Pago");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/asentar-pago-64x64.png")));
			setPreferredSize(new Dimension(100, 90));
			break;
			
		case BOTON_CERRAR:
			setText("Cerrar");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/cerrar-64x64.png")));
			setPreferredSize(new Dimension(90, 90));
			break;
			
		case BOTON_ESCRITURAR:
			setText("Escriturar");
			setIcon(new ImageIcon(ButtonDefaultABM.class.getResource("/images/escriturar-64x64.png")));
			setPreferredSize(new Dimension(90, 90));
			break;
		}
	}
	
	private void eventoMouse() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				setBorderPainted(false);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorderPainted(true);
			}
		});
	}

}
