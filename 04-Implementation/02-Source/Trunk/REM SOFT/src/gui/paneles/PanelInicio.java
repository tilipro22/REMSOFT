package gui.paneles;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.alquileres.FrameAlquiler;
import app.alquileres.PanelAlquiler;
import app.inmuebles.FrameInmueble;
import app.inmuebles.PanelInmuebles;
import app.solicitudes.FrameSolicitud;
import app.solicitudes.PanelSolicitudes;
import app.ventas.FrameVenta;
import app.ventas.PanelVenta;
import gui.ConfigGUI;
import gui.FrameAddPersona;
import gui.FramePpal;
import logica.ConfigProgram;

public class PanelInicio extends JPanel implements ConfigGUI, RecuadroPanelInicio{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Integer DISTANCIA_PX = 350;
	private JLabel lblPersonas, lblInmuebles, lblAlquileres, lblVentas, lblSolicitudes, lblReservas;
	

	public PanelInicio() {
		setBounds(0, 0, SCREEN_SIZE.width, SCREEN_SIZE.height-200);
		setBackground(COLOR_FONDO);
		setLayout(null);
		
	//RECUADRO DE PERSONAS
		lblPersonas = new JLabel();
		lblPersonas.setBounds(DISTANCIA_PX, 35, 130, 50);
		armarRecuadro(OP_PERSONAS, "Personas", "/images/persona-128.png" ,lblPersonas);
	//FIN RECUADRO DE PERSONAS
		
	//RECUADRO DE INMUEBLES
		lblInmuebles = new JLabel();
		lblInmuebles.setBounds(lblPersonas.getX()+DISTANCIA_PX, 35, 130, 50);
		armarRecuadro(OP_INMUEBLES, "Inmuebles", "/images/inmueble-128.png", lblInmuebles);
	//FIN RECUADRO DE INMUEBLES
		
	//RECUADRO DE ALQUILERES
		lblAlquileres = new JLabel();
		lblAlquileres.setBounds(lblInmuebles.getX()+DISTANCIA_PX, 35, 130, 50);
		armarRecuadro(OP_ALQUILERES, "Alquileres", "/images/alquiler-128.png", lblAlquileres);
	//FIN RECUADRO DE ALQUILERES
		
	// RECUADRO DE VENTAS
		lblVentas = new JLabel();
		lblVentas.setBounds(lblAlquileres.getX()+DISTANCIA_PX, 35, 130, 50);
		armarRecuadro(OP_VENTAS, "Ventas", "/images/ventas-128.png", lblVentas);
	//FIN RECUADRO DE VENTAS
		
	// RECUADRO DE SOLICITUDES
		lblSolicitudes = new JLabel();
		lblSolicitudes.setBounds(DISTANCIA_PX, 450, 130, 50);
		armarRecuadro(OP_SOLICITUDES, "Solicitudes", "/images/solicitudes-128.png", lblSolicitudes);
	// FIN RECUADRO DE SOLICITUDES
		
	// RECUADRO DE RESERVAS
		lblReservas = new JLabel();
		lblReservas.setBounds(lblSolicitudes.getX()+DISTANCIA_PX, 450, 130, 50);
		armarRecuadro(OP_RESERVAS, "Reservas", "/images/reserva-128.png", lblReservas);
	// FIN RECUADRO DE RESERVAS
		
		
	}
	
	private void armarRecuadro(int opcion, String name, String urlIcon, JLabel lbl) {
		lbl.setText(name);
		lbl.setBorder(BORDE_TITULO);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(FONT_TITULO);
		lbl.setForeground(Color.WHITE);
		
		//ICONO
		JLabel lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIcon(PanelInicio.class.getResource(urlIcon)));
		lblIcon.setBorder(null);
		lblIcon.setBounds(lbl.getBounds().x, lbl.getBounds().y+63, ICON_SIZE, ICON_SIZE);
		
		//SUBTITULOS
		JLabel lblAgregar = new JLabel("Agregar");
		lblAgregar.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblAgregar.setForeground(new Color(75, 95, 131));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblAgregar.setForeground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				agregar(opcion);
			}
			
		});
		
		lblAgregar.setBorder(BORDE_SUBTITULO);
		lblAgregar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgregar.setFont(FONT_SUBTITULO);
		lblAgregar.setForeground(Color.WHITE);
		lblAgregar.setBounds(lbl.getBounds().x, lbl.getBounds().y+219, 128, 20);
		
		
		JLabel lblVerLista = new JLabel("Ver Lista");
		lblVerLista.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblVerLista.setForeground(new Color(75, 95, 131));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblVerLista.setForeground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				verLista(opcion);
			}
			
		});
		
		lblVerLista.setBorder(BORDE_SUBTITULO);
		lblVerLista.setHorizontalAlignment(SwingConstants.CENTER);
		lblVerLista.setFont(FONT_SUBTITULO);
		lblVerLista.setForeground(Color.WHITE);
		lblVerLista.setBounds(lbl.getBounds().x, lblAgregar.getBounds().y+33, 128, 20);
		
		add(lbl); add(lblIcon); add(lblAgregar); add(lblVerLista);
	}
	
	private void agregar (int opcion) {
		switch (opcion) {
		case OP_PERSONAS:
			//Agregar Persona
			ConfigProgram.getConfigProgram().setFlag("Panel.Frame.Persona.Agregar.Datos");
			FramePpal.getFrameppal().setEnabled(false);
			FrameAddPersona frameAddPersona = FrameAddPersona.getFrameAddPersona();
			frameAddPersona.setModificar(false, null);
			frameAddPersona.setVisible(true);
			break;
			
		case OP_INMUEBLES:
			//Agregar Inmueble
			ConfigProgram.getConfigProgram().setFlag("Panel.Frame.Inmueble.Agregar.DatosPub");
			//System.out.println(ConfigProgram.getConfigProgram().getFlag());
			FramePpal.getFrameppal().setEnabled(false);
			FrameInmueble frameInmueble = new FrameInmueble(false);
			frameInmueble.setVisible(true);
			break;
			
		case OP_ALQUILERES:
			//Agregar Alquiler
			verLista(OP_ALQUILERES);
			FramePpal.getFrameppal().setEnabled(false);
			FrameAlquiler frameAlquiler = new FrameAlquiler(false);
			frameAlquiler.setVisible(true);
			((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).setFrameAlquiler(frameAlquiler);
			break;
			
		case OP_VENTAS:
			//Agregar Venta
			verLista(OP_VENTAS);
			FramePpal.getFrameppal().setEnabled(false);
			FrameVenta frameVenta = new FrameVenta(false);
			frameVenta.setVisible(true);
			((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).setFrameVenta(frameVenta);
			break;
			
		case OP_SOLICITUDES:
			//Agregar Solicitud
			verLista(OP_SOLICITUDES);
			FramePpal.getFrameppal().setEnabled(false);
			FrameSolicitud frameSolicitud = new FrameSolicitud(false);
			frameSolicitud.setVisible(true);
			((PanelSolicitudes) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES)).setFrameSolicitud(frameSolicitud);
			break;
			
		}
	}
	
	private void verLista (int opcion) {
		switch (opcion) {
		case 1:
			//Ver lista de Personas
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(false);
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS).setVisible(true);
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_PERSONAS);
			repaint();
			break;
			
		case 2:
			//Ver Lista de Inmuebles
			for (int i = 0; i < FramePpal.getFrameppal().getArrayPaneles().size(); i++) {
				if (i == FramePpal.PANEL_INMUEBLES)
					FramePpal.getFrameppal().getArrayPaneles().get(i).setVisible(true);
				else
					FramePpal.getFrameppal().getArrayPaneles().get(i).setVisible(false);
			}
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_INMUEBLES);
			((PanelInmuebles) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INMUEBLES)).recargarInmuebles();
			repaint();
			break;
			
		case 3:
			//Ver Lista de Alquileres
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(false);
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES).setVisible(true);
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_ALQUILERES);
			((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).recargarAlquileres();
			repaint();
			break;
			
		case 4:
			//Ver lista de Ventas
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(false);
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS).setVisible(true);
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_VENTAS);
			((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).recargarVentas();
			repaint();
			break;
			
		case 5:
			//Ver lista de Solicitudes
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(false);
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_SOLICITUDES).setVisible(true);
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_SOLICITUDES);
			repaint();
			break;
			
		case 6:
			//Ver lista de Reservas
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INICIO).setVisible(false);
			FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_RESERVAS).setVisible(true);
			FramePpal.getFrameppal().setPanelActual(FramePpal.PANEL_RESERVAS);
			repaint();
			break;
		}
	}

}
