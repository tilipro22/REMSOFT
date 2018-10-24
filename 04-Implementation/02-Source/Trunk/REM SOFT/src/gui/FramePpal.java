package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import app.alquileres.PanelAlquiler;
import app.inmuebles.PanelInmuebles;
import app.reservas.PanelReservas;
import app.solicitudes.PanelSolicitudes;
import app.ventas.PanelVenta;
import gui.paneles.PanelPersonas;
import gui.paneles.PanelInicio;
import javax.swing.ImageIcon;

public final class FramePpal extends JFrame implements ConfigGUI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Font FONT_PANEL_FRONTAL = new Font("Calibri", Font.PLAIN, 35);
	private static final int OP_INMOUSE = 1;
	private static final int OP_OUTMOUSE = 2;
	private static final int OP_PRESSMOUSE = 3;
	
	public static final int PANEL_INICIO = 0;
	public static final int PANEL_PERSONAS = 1;
	//public static final int PANEL_SEGUIMIENTOS = ?;
	public static final int PANEL_INMUEBLES = 2;
	public static final int PANEL_ALQUILERES = 3;
	public static final int PANEL_VENTAS = 4;
	public static final int PANEL_SOLICITUDES = 5;
	public static final int PANEL_RESERVAS = 6;
	
	private static final FramePpal framePpal=new FramePpal();

	private JPanel contentPane, panelFrontal, panelCentral;
	private ArrayList<JPanel> arrayPaneles = new ArrayList<JPanel>();
	private PanelInicio panelInicio;
	private PanelPersonas panelPersonas;
	private PanelInmuebles panelInmuebles;
	private PanelAlquiler panelAlquiler;
	private PanelVenta panelVenta;
	private PanelSolicitudes panelSolicitudes;
	private PanelReservas panelReservas;
	
	private int panelActual = 0;


	/**
	 * Create the frame.
	 */
	private FramePpal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH); //MAXIMO DE PANTALLA
		//frameActual = this;
		
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmNuevo=new JMenuItem("Nuevo");
		mnArchivo.add(mntmNuevo);
		
		JMenu mnDatosGenerales = new JMenu("Datos Generales");
		menuBar.add(mnDatosGenerales);
		
		JMenu mnPersonas = new JMenu("Personas");
		mnPersonas.setIcon(new ImageIcon(FramePpal.class.getResource("/images/icon-person-16x16.png")));
		mnDatosGenerales.add(mnPersonas);
		
		JMenuItem mntmAbymDePersonas = new JMenuItem("AByM de Personas");
		mnPersonas.add(mntmAbymDePersonas);
		
		JMenuItem mntmAdministrarSeguimientos = new JMenuItem("Administrar Seguimientos");
		mnPersonas.add(mntmAdministrarSeguimientos);
		
		
		setBounds(100, 100, 450, 300); //posible a borrar
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//PANEL FRONTAL
		panelFrontal=new JPanel();
		panelFrontal.setBounds(0, 0, SCREEN_SIZE.width, 100);
		panelFrontal.setBackground(COLOR_FONDO);
		panelFrontal.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 50));
		panelFrontal.setBorder(new MatteBorder(0, 0, 1, 0, Color.white));
		contentPane.add(panelFrontal);
		
		JLabel lblInicio=new JLabel("INICIO");
		lblInicio.setFont(FONT_PANEL_FRONTAL);
		lblInicio.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				eventoMouseLabel(OP_INMOUSE, lblInicio);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				eventoMouseLabel(OP_OUTMOUSE, lblInicio);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				eventoMouseLabel(OP_PRESSMOUSE, lblInicio);
				if (panelActual != PANEL_INICIO) {
					arrayPaneles.get(panelActual).setVisible(false);
					arrayPaneles.get(PANEL_INICIO).setVisible(true);
					setPanelActual(PANEL_INICIO);
					repaint();
				}
			}
			
		});
		
		lblInicio.setForeground(COLOR_LABEL);
		lblInicio.setHorizontalAlignment(SwingConstants.CENTER);
		lblInicio.setFont(FONT_PANEL_FRONTAL);
		lblInicio.setBounds(60, 35, 111, 37);
		panelFrontal.add(lblInicio);
		
		JLabel lblAlquileres=new JLabel("ALQUILERES");
		lblAlquileres.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				eventoMouseLabel(OP_PRESSMOUSE, lblAlquileres);
				
				if (panelActual != PANEL_ALQUILERES) {
					arrayPaneles.get(panelActual).setVisible(false);
					arrayPaneles.get(PANEL_ALQUILERES).setVisible(true);
					setPanelActual(PANEL_ALQUILERES);
					((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).recargarAlquileres();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				eventoMouseLabel(OP_INMOUSE, lblAlquileres);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				eventoMouseLabel(OP_OUTMOUSE, lblAlquileres);
			}
		});
		lblAlquileres.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlquileres.setForeground(COLOR_LABEL);
		lblAlquileres.setFont(FONT_PANEL_FRONTAL);
		lblAlquileres.setBounds(231, 35, 164, 37);
		panelFrontal.add(lblAlquileres);
		// FIN PANEL FRONTAL
		
		addLabelToPanelFrontal(new JLabel("VENTAS"));
		addLabelToPanelFrontal(new JLabel("SOLICITUDES"));
		addLabelToPanelFrontal(new JLabel("RESERVAS"));
		
		
		// PANEL CENTRAL
		panelCentral = new JPanel();
		panelCentral.setBounds(0, 100, SCREEN_SIZE.width, SCREEN_SIZE.height-200);
		panelCentral.setLayout(null);
		
		panelInicio = new PanelInicio();
		panelInicio.setVisible(true);
		panelCentral.add(panelInicio);
		
		panelPersonas = new PanelPersonas();
		panelPersonas.setVisible(false);
		panelCentral.add(panelPersonas);
		
		panelInmuebles = new PanelInmuebles();
		panelInmuebles.setVisible(false);
		panelCentral.add(panelInmuebles);
		
		panelAlquiler = new PanelAlquiler();
		panelAlquiler.setVisible(false);
		panelCentral.add(panelAlquiler);
		
		panelVenta = new PanelVenta();
		panelVenta.setVisible(false);
		panelCentral.add(panelVenta);
		
		panelSolicitudes = new PanelSolicitudes();
		panelSolicitudes.setVisible(false);
		panelCentral.add(panelSolicitudes);
		
		panelReservas = new PanelReservas();
		panelReservas.setVisible(false);
		panelCentral.add(panelReservas);
		
		arrayPaneles.add(panelInicio);
		arrayPaneles.add(panelPersonas);
		arrayPaneles.add(panelInmuebles);
		arrayPaneles.add(panelAlquiler);
		arrayPaneles.add(panelVenta);
		arrayPaneles.add(panelSolicitudes);
		arrayPaneles.add(panelReservas);
		
		/*
		 * FALTA:
		 * panel Inicio
		 * panel Alquileres
		 */
		
		contentPane.add(panelCentral);
		// FIN PANEL CENTRAL
		
	}
	
	private void addLabelToPanelFrontal(JLabel lbl) {
		lbl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (lbl.getText().equals("VENTAS")) {
					if (panelActual != PANEL_VENTAS) {
						arrayPaneles.get(panelActual).setVisible(false);
						arrayPaneles.get(PANEL_VENTAS).setVisible(true);
						setPanelActual(PANEL_VENTAS);
						((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).recargarVentas();
						repaint();
					}
				}
				
				if (lbl.getText().equals("SOLICITUDES")) {
					if (panelActual != PANEL_SOLICITUDES) {
						arrayPaneles.get(panelActual).setVisible(false);
						arrayPaneles.get(PANEL_SOLICITUDES).setVisible(true);
						setPanelActual(PANEL_SOLICITUDES);
						
						// FALTA RECARGAR SOLICITUDES
						
						repaint();
					}
				}
				
				if (lbl.getText().equals("RESERVAS")) {
					if (panelActual != PANEL_RESERVAS) {
						arrayPaneles.get(panelActual).setVisible(false);
						arrayPaneles.get(PANEL_RESERVAS).setVisible(true);
						setPanelActual(PANEL_RESERVAS);
						
						// FALTA RECARGAR RESERVAS
						
						repaint();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				eventoMouseLabel(OP_INMOUSE, lbl);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				eventoMouseLabel(OP_OUTMOUSE, lbl);
			}
		});
		
		lbl.setForeground(COLOR_LABEL);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(FONT_PANEL_FRONTAL);
		
		panelFrontal.add(lbl);
	}
	
	
	public static FramePpal getFrameppal() {
		return framePpal;
	}
	
	
	private void eventoMouseLabel(int opcion, JLabel lbl) {
		switch (opcion) {
		case 1:
			lbl.setForeground(Color.WHITE);
			break;
			
		case 2:
			lbl.setForeground(COLOR_LABEL);
			break;
			
		case 3:
			
			break;
		}
	}

	public ArrayList<JPanel> getArrayPaneles() {
		return arrayPaneles;
	}



	public int getPanelActual() {
		return panelActual;
	}



	public void setPanelActual(int panelActual) {
		this.panelActual = panelActual;
	}
	
	
	
	
}
