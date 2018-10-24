package gui.paneles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;

public abstract class PanelDefault extends JPanel implements ConfigGUI{


	private static final long serialVersionUID = 1L;
	protected JScrollPane scrollPane;
	protected ModeloTabla modeloTabla;
	protected JTable tabla;
	protected JLabel lblAtras, lblTitle;
	protected JPanel panelAbajo;
	protected int filaSeleccionada = -1;
	
	public PanelDefault() {
		setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height-200);
		setBackground(COLOR_FONDO);
		setLayout(null);
		
		lblAtras = new JLabel("Menu Principal");
		lblAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int panelActual = FramePpal.getFrameppal().getPanelActual();
				FramePpal.getFrameppal().getArrayPaneles().get(panelActual).setVisible(false);
				//FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS).setVisible(false);
				FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(true);
				FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_INICIO);
				repaint();
			}
		});
		
		lblAtras.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAtras.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAtras.setHorizontalAlignment(SwingConstants.CENTER);
		lblAtras.setForeground(Color.WHITE);
		lblAtras.setIcon(new ImageIcon(PanelPersonas.class.getResource("/images/back_REM48x48.png")));
		lblAtras.setBounds(0, 0, 100, 100);
		
		add(lblAtras);
		
		lblTitle = new JLabel("");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		lblTitle.setBorder(new LineBorder(Color.WHITE));
		lblTitle.setBounds(lblAtras.getX()+lblAtras.getWidth(), 50, 350, 50);
		lblTitle.setVisible(false);
		
		add(lblTitle);
		
		
		panelAbajo = new JPanel();
		panelAbajo.setLayout(new FlowLayout());
		panelAbajo.setBounds(100, 780, SCREEN_SIZE.width-200, 100);
		panelAbajo.setBackground(ConfigGUI.COLOR_FONDO);
		
		ButtonDefaultABM btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_AGREGAR);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAgregar();
			}
		});
		
		ButtonDefaultABM btnModificar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_MODIFICAR);
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoModificar();
			}
		});
		
		ButtonDefaultABM btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ELIMINAR);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoEliminar();
			}
		});
		
		panelAbajo.add(btnAgregar);
		panelAbajo.add(btnModificar);
		panelAbajo.add(btnEliminar);
		
		add(panelAbajo);
		
	}

	protected abstract void inicializarModeloTabla();
	
	protected abstract void eventoAgregar();
	
	protected abstract void eventoModificar();
	
	protected abstract void eventoEliminar();
	
	protected void setConfigTable() {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	protected void setConfigScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(lblAtras.getWidth(), lblAtras.getHeight(), SCREEN_SIZE.width-200, SCREEN_SIZE.height-400);
	}
}
