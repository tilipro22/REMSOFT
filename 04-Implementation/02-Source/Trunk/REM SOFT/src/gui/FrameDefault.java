package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public abstract class FrameDefault extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int ALTURABF_DEFAULT = 30; 
	private JPanel contentPane;
	private Point initialClick;
	protected JLabel lblTitulo, lblMinimizar, lblCerrar;


	public FrameDefault() {
		
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 550);
		

		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 2, 2, 2, (Color) new Color(0, 255, 255)));
		contentPane.setBackground(ConfigGUI.COLOR_FONDO);
		setContentPane(contentPane);
		
		setBarra("");
		
		setLocationRelativeTo(null);
	}
	
	private void setBarra (String titulo) {
		
		/*Texto Arrastre*/
		
		lblTitulo = new JLabel(titulo);
		lblTitulo.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
			
		});
		lblTitulo.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int thisX = getLocation().x;
				int thisY = getLocation().y;
				
				int xMoved = ( (thisX + e.getX()) - (thisX + initialClick.x));
				int yMoved = ( (thisY + e.getY()) - (thisY + initialClick.y));
				
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				setLocation(X, Y);
			}			
			
		});
		contentPane.setLayout(null);
		
		lblTitulo.setBorder(new MatteBorder(2, 2, 0, 0, Color.CYAN));
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(0, 0, getWidth()-50, ALTURABF_DEFAULT);
		
		contentPane.add(lblTitulo);
		
		
		/* Minimizar */
		lblMinimizar = new JLabel("_");
		lblMinimizar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblMinimizar.setOpaque(true);
				lblMinimizar.setForeground(Color.BLACK);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblMinimizar.setOpaque(false);
				lblMinimizar.setForeground(ConfigGUI.COLOR_SINFOCO);
			}
			
		});
		
		lblMinimizar.setBorder(new MatteBorder(2, 1, 1, 1, Color.CYAN));
		lblMinimizar.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblMinimizar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMinimizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinimizar.setBounds(getWidth()-50, 0, 25, ALTURABF_DEFAULT);
		
		contentPane.add(lblMinimizar);
		
		
		/* Cerrar */
		lblCerrar = new JLabel("X");
		lblCerrar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				eventoCerrar();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblCerrar.setOpaque(true);
				lblCerrar.setBackground(new Color(232, 20, 20));
				lblCerrar.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCerrar.setOpaque(false);
				lblCerrar.setForeground(ConfigGUI.COLOR_SINFOCO);
			}
			
		});
		
		lblCerrar.setBorder(new MatteBorder(2, 1, 1, 2, new Color(0, 255, 255)));
		lblCerrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCerrar.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblCerrar.setBounds(getWidth()-25, 0, 25, ALTURABF_DEFAULT);
		
		contentPane.add(lblCerrar);
	}
	
	protected void setSizeFrame(int ancho, int altura) {
		setBounds(0, 0, ancho, altura);
		lblTitulo.setBounds(0, 0, getWidth()-50, ALTURABF_DEFAULT);
		lblMinimizar.setBounds(getWidth()-50, 0, 25, ALTURABF_DEFAULT);
		lblCerrar.setBounds(getWidth()-25, 0, 25, ALTURABF_DEFAULT);
		
	}
	
	protected void setTituloBarra (String titulo) {
		lblTitulo.setText(titulo);
	}
	
	protected void setColorBackground (Color color) {
		contentPane.setBackground(color);
	}
	
	protected void setColorBordes (Color color) {
		contentPane.setBorder(new MatteBorder(1, 2, 2, 2, color));
		lblTitulo.setBorder(new MatteBorder(2, 2, 0, 0, color));
		lblMinimizar.setBorder(new MatteBorder(2, 1, 1, 1, color));
		lblCerrar.setBorder(new MatteBorder(2, 1, 1, 2, color));
	}
	
	protected void setLabelDefault(JLabel label) {
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
	}
	
	protected void setLabelToNav(JLabel lbl, int sizeFont) {
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
		lbl.setFont(new Font("Tahoma", Font.PLAIN, sizeFont));
		lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lbl.setForeground(ConfigGUI.COLOR_SINFOCO);
			}
			
			
		});
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
		textField.setFont(new Font("Tahoma", Font.PLAIN, 21));
		textField.setCaretColor(Color.white);
		textField.setBorder(new LineBorder(ConfigGUI.COLOR_SINFOCO));
		textField.setBackground(ConfigGUI.COLOR_FONDO);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEnabled(false);
		
	}
	
	protected abstract void eventoCerrar();
}
