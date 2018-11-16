package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import control.CtrlAddPersona;
import gui.paneles.PanelAddPersona_Datos;
import gui.paneles.PanelAddPersona_Notas;
import gui.paneles.PanelAddPersona_Seguim;
import gui.paneles.PanelPersonas;
import logica.ConfigProgram;
import logica.Persona;

public class FrameAddPersona extends FrameDefault{

	private static final long serialVersionUID = 1L;
	
	private static final int IN_MOUSE = 1;
	private static final int OUT_MOUSE = 2;
	private static final int CLICK_MOUSE = 3;
	
	private static final FrameAddPersona frame=new FrameAddPersona();
	
	private JPanel panelMenu, panelCentral, panelAbajo;
	private PanelAddPersona_Datos panelDatos;
	private PanelAddPersona_Seguim panelSeguimientos;
	private PanelAddPersona_Notas panelNotas;
	private JLabel lblInfo;
	private final int ancho;
	private boolean isModificar = false;

	public FrameAddPersona() {

			// Se definen las propiedas del titulo y barra frontal
		setTituloBarra("   Agregar Persona - REM Soft");
		setSizeFrame(getWidth(), 600);
		setLocationRelativeTo(null);
		ancho = getWidth(); //450 px
		
		lblTitulo.setSize(getWidth()-80, ALTURABF_DEFAULT);
		
		setConfigLblInfo();
		add(lblInfo);
		
		panelMenu = new PanelMenu();
		getContentPane().add(panelMenu);

			// Init panel Central
		panelCentral = new JPanel();
		panelCentral.setLayout(null);
		panelCentral.setBounds(0, panelMenu.getHeight()+lblCerrar.getHeight(), getWidth(), 450);
		
			// Inicializacion de los paneles
		inicializarPaneles();
		
		getContentPane().add(panelCentral);

			// Inicializar a botones
		panelAbajo = new PanelAbajo();
		getContentPane().add(panelAbajo);
	}
	
	public void setConfigLblInfo() {
		lblInfo = new JLabel("i");
		lblInfo.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblInfo.setBackground(new Color(201, 205, 211));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblInfo.setBackground(Color.WHITE);
			}
			
		});
		
		lblInfo.setBounds(ancho-80, 0, 30, ALTURABF_DEFAULT);
		lblInfo.setBorder(new MatteBorder(2, 1, 1, 1, Color.CYAN));
		lblInfo.setOpaque(true);
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInfo.setForeground(new Color(66, 134, 244));
		lblInfo.setBackground(Color.WHITE);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override
	protected void eventoCerrar() {
		/*
		 * 	- Hay que hacer un metodo que inicialize las variables, para ahorrar codigo cuando los restaure
			- Agregarselo al JButton 'Cancelar', la funcion Restaurar()
		 */
		
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		restaurarFrame();
	}
	
	class PanelAbajo extends JPanel {
		
		private static final long serialVersionUID = 1L;
		JButton btnCancelar, btnAceptar;
		
		public PanelAbajo() {
			setBounds(0, 510, 450, 90);
			setBackground(ConfigGUI.COLOR_FONDO);
			setBorder(new MatteBorder(0, 2, 2, 2, Color.CYAN));
			setLayout(null);

			
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FramePpal.getFrameppal().setEnabled(true);
					dispose();
				}
			});
			
			btnCancelar.setBounds(new Rectangle(50, 30, 150, 40));
			btnCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnCancelar.setFocusable(false);
			btnCancelar.setBorderPainted(false);
			btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
			btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(btnCancelar);
			
			btnAceptar = new JButton("Aceptar");
			btnAceptar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean isPanelDatosValido = panelDatos.esDatosValido();
					boolean isPanelNotasValido = panelNotas.esDatosValido();
					
					if (isPanelDatosValido && isPanelNotasValido) {
						//Agregar datos a la base de datos
						Persona persona = panelDatos.getDatosPersona();
						panelDatos.setContactoPersona(persona);
						panelSeguimientos.setSeguimientoPersona(persona);
						panelNotas.setDatosNotasPersona(persona);
						
						//imprimirPersona(persona);

						
						if (!isModificar) {
							agregarPersona(persona);
						}
						else {
							modificarPersona(persona);
						}
					}
					else {
						if (! isPanelDatosValido)
							((PanelMenu)panelMenu).lblDatos.setForeground(ConfigGUI.COLOR_ERROR);
						if (! isPanelNotasValido)
							((PanelMenu)panelMenu).lblNotas.setForeground(ConfigGUI.COLOR_ERROR);
							
					}
				}
			});
			
			btnAceptar.setBounds(250, 30, 150, 40);
			btnAceptar.setHorizontalTextPosition(SwingConstants.LEADING);
			btnAceptar.setForeground(Color.WHITE);
			btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAceptar.setFocusable(false);
			btnAceptar.setBorderPainted(false);
			btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
			btnAceptar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			add(btnAceptar);
		}
	}
	
	public void imprimirPersona (Persona p) {
		System.out.println(p.getNombre()+" "+p.getApellido()+", DNI: "+p.getDni()+". "+p.getDomicilio()+", "+p.getCiudad()+", "
				+p.getProvincia()+". Nacionalidad: "+p.getNacionalidad());
		
		System.out.println("//Contacto// Telefono: "+p.getContacto().getTelefono()+" / "+p.getContacto().getCelular()+" / "
				+p.getContacto().getMail());
		
		System.out.println(p.getTipo()+": "+p.getProfesion()+", Sexo: "+p.getSexo());
	}
	
	class PanelMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel lblDatos, lblSeguimientos, lblNotas;

		public PanelMenu() {
			setBounds(0, 30, ancho, 60);
			setBackground(ConfigGUI.COLOR_FONDO);
			setBorder(new MatteBorder(0, 2, 0, 2, Color.CYAN));
			setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

			
			lblDatos = new JLabel("DATOS");
			lblDatos.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					ConfigProgram.getConfigProgram().setFlag("Frame.Persona.Agregar.Datos");
					System.out.println(ConfigProgram.getConfigProgram().getFlag());
					eventoLabel(CLICK_MOUSE, lblDatos);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					eventoLabel(IN_MOUSE, lblDatos);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					eventoLabel(OUT_MOUSE, lblDatos);
				}
				
			});
			
			lblDatos.setHorizontalAlignment(SwingConstants.CENTER);
			lblDatos.setForeground(ConfigGUI.COLOR_LABEL);
			lblDatos.setFont(new Font("Calibri", Font.PLAIN, 30));
			add(lblDatos);
			
			lblSeguimientos = new JLabel("SEGUIMIENTOS");
			lblSeguimientos.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ConfigProgram.getConfigProgram().setFlag("Frame.Persona.Agregar.Seguimientos");
					System.out.println(ConfigProgram.getConfigProgram().getFlag());
					eventoLabel(CLICK_MOUSE, lblSeguimientos);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					eventoLabel(IN_MOUSE, lblSeguimientos);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					eventoLabel(OUT_MOUSE, lblSeguimientos);
				}
			});
			
			lblSeguimientos.setHorizontalAlignment(SwingConstants.CENTER);
			lblSeguimientos.setForeground(ConfigGUI.COLOR_LABEL);
			lblSeguimientos.setFont(new Font("Calibri", Font.PLAIN, 30));
			add(lblSeguimientos);
			
			
			lblNotas = new JLabel("NOTAS");
			lblNotas.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ConfigProgram.getConfigProgram().setFlag("Frame.Persona.Agregar.Notas");
					System.out.println(ConfigProgram.getConfigProgram().getFlag());
					eventoLabel(CLICK_MOUSE, lblNotas);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					eventoLabel(IN_MOUSE, lblNotas);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					eventoLabel(OUT_MOUSE, lblNotas);
				}
			});
			
			lblNotas.setHorizontalAlignment(SwingConstants.CENTER);
			lblNotas.setForeground(ConfigGUI.COLOR_LABEL);
			lblNotas.setFont(new Font("Calibri", Font.PLAIN, 30));
			add(lblNotas);
			
		}
		
		public void eventoLabel(int opcion, JLabel lbl) {
			
			switch (opcion) {
			
			case IN_MOUSE:
				lbl.setForeground(Color.WHITE);
				break;
				
			case OUT_MOUSE:
				lbl.setForeground(ConfigGUI.COLOR_LABEL);
				break;
				
			case CLICK_MOUSE:
				
				if (lbl.getText().equals("DATOS")) {
					System.out.println("Abrir panel DATOS");
					
					if (! panelDatos.isVisible()) {
						panelSeguimientos.setVisible(false);
						panelSeguimientos.setEnabled(false);
						panelNotas.setVisible(false);
						panelNotas.setEnabled(false);
						panelDatos.setVisible(true);
						panelDatos.setEnabled(true);
					}
					
					
				}
					
				
				if (lbl.getText().equals("SEGUIMIENTOS")) {
					System.out.println("Abrir panel SEGUIMIENTOS");
					
					if (! panelSeguimientos.isVisible()) {
						panelDatos.setVisible(false);
						panelDatos.setEnabled(false);
						panelNotas.setVisible(false);
						panelNotas.setEnabled(false);
						panelSeguimientos.setVisible(true);
						panelSeguimientos.setEnabled(true);
					}
				}
					
				
				if (lbl.getText().equals("NOTAS")) {
					System.out.println("Abrir panel NOTAS");
					
					if (! panelNotas.isVisible()) {
						panelDatos.setVisible(false);
						panelDatos.setEnabled(false);
						panelSeguimientos.setVisible(false);
						panelSeguimientos.setEnabled(false);
						panelNotas.setVisible(true);
						panelNotas.setEnabled(true);
					}
					
				}
				
				break;
			}
		}

		public JLabel getLblDatos() {
			return lblDatos;
		}

		public JLabel getLblSeguimientos() {
			return lblSeguimientos;
		}

		public JLabel getLblNotas() {
			return lblNotas;
		}
		
		
	}
	
	public static FrameAddPersona getFrameAddPersona () {
		return frame;
	}
	
	public boolean activarFrameModificar (int option) {
		if (option == 1)
			return true;
		else
			return false;
	}
	
	public void restaurarFrame () {
		panelCentral.removeAll();
		inicializarPaneles();
	}
	
	private void inicializarPaneles() {
		panelDatos = new PanelAddPersona_Datos();
		panelCentral.add(panelDatos);
		
		panelSeguimientos = new PanelAddPersona_Seguim();
		panelSeguimientos.setVisible(false);
		panelSeguimientos.setEnabled(false);
		panelCentral.add(panelSeguimientos);
		
		panelNotas=new PanelAddPersona_Notas();
		panelNotas.setVisible(false);
		panelNotas.setEnabled(false);
		panelCentral.add(panelNotas);
	}
	

	public PanelAddPersona_Seguim getPanelSeguimientos() {
		return panelSeguimientos;
	}

	public PanelAddPersona_Datos getPanelDatos() {
		return panelDatos;
	}

	public PanelAddPersona_Notas getPanelNotas() {
		return panelNotas;
	}

	public boolean isModificar() {
		return isModificar;
	}

	private Integer idPersona;
	
	public Integer getIdPersona() {
		return idPersona;
	}

	public void setModificar(boolean isModificar, Integer idPersona) {
		this.isModificar = isModificar;
		this.idPersona = idPersona;
	}
	
	private void agregarPersona(Persona persona) {
				
		CtrlAddPersona ctrlAddPersona=new CtrlAddPersona();
		
		if (!ctrlAddPersona.isDniRepetido(persona)) {
			
			ctrlAddPersona.agregarPersona(persona);
		
			( (PanelPersonas) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS) ).getArrayPersonas().add(persona);
			( (PanelPersonas) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS) ).actualizarLista();

			JOptionPane.showMessageDialog(null, persona.getApellido()+", "+persona.getNombre()+". DNI: "+persona.getDni()+
				"\nSe agrego satisfactoriamente", "Agregar persona", JOptionPane.INFORMATION_MESSAGE);
			eventoCerrar();
			dispose();
		}
		else {
			((PanelMenu)panelMenu).lblDatos.setForeground(ConfigGUI.COLOR_ERROR);
			JOptionPane.showMessageDialog(null, "El DNI que quiere agregar ya existe en la BD");
			System.out.println("El DNI ya existe en BD");
		}
		
	}
	
	private void modificarPersona(Persona persona) {
		persona.setIdPersona(idPersona);
		CtrlAddPersona ctrlAddPersona=new CtrlAddPersona();
		
		ctrlAddPersona.modificarPersona(persona);
		int indexPersona = ( (PanelPersonas) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS) ).getFilaSeleccionada();
		( (PanelPersonas) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS) ).getArrayPersonas().set(indexPersona, persona);
		( (PanelPersonas) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_PERSONAS) ).actualizarLista();
		
		JOptionPane.showMessageDialog(null, "Persona con DNI: "+persona.getDni()+
				"\nSe modifico satisfactoriamente", "Persona modificada", JOptionPane.INFORMATION_MESSAGE);
		
		eventoCerrar();
		dispose();
	}
	
	
}
