package app.inmuebles;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import app.inmuebles.paneles.PanelDatosPrivados;
import app.inmuebles.paneles.PanelDatosPublicos;
import app.inmuebles.paneles.PanelGastos;
import app.inmuebles.paneles.PanelImagenes;
import app.inmuebles.paneles.PanelOperaciones;
import gui.ConfigGUI;
import gui.FrameDefault;
import gui.FramePpal;
import logica.GastoFijo;
import logica.Imagen;
import logica.Inmueble;
import logica.Operacion;
import logica.Persona;
import logica.Ubicacion;
import logica.constant.ValidateConstants;

public class FrameInmueble extends FrameDefault {
	

	private static final long serialVersionUID = 1L;
	/* Constant de FrameInmueble */
	private static final int LABEL_DATOS_PUBLICOS = 1;
	private static final int LABEL_DATOS_PRIVADOS = 2;
	private static final int LABEL_OPERACIONES = 3;
	private static final int LABEL_GASTOS_FIJOS = 4;
	private static final int LABEL_IMAGENES = 5;

	/* Utils de FrameInmueble */
	private JPanel panelHeader, panelNavBar, panelCentral, panelFooter;
	private boolean isModificar;
	private Inmueble inmueble;
	private CtrlInmueble ctrlInmueble = new CtrlInmueble();

	/* Elements de FrameInmueble */
	JComboBox<String> cbxTipo;
	JDateChooser dateChooserFecha;
	
	/* Panels de PanelCentral */
	PanelDatosPublicos panelDatosPublicos = new PanelDatosPublicos();
	PanelDatosPrivados panelDatosPrivados = new PanelDatosPrivados();
	PanelOperaciones panelOperaciones = new PanelOperaciones();
	PanelGastos panelGastos = new PanelGastos();
	PanelImagenes panelImagenes = new PanelImagenes();


	public FrameInmueble(boolean isModificar) {
		this.isModificar = isModificar;
		
		// Config FRAME
		setTituloBarra("   REMSOFT ~ Agregar Inmueble");
		setSizeFrame(1280, 700);
		setLocationRelativeTo(null);
		
		initPanelHeader();
		initPanelNavBar();
		initPanelCentral();
		initPanelFooter();
		
		if (this.isModificar) {
			setTituloBarra("   REMSOFT ~ Modificar Inmueble");
			completeFrame();
		}
	}
	
	private void completeFrame() {
		this.inmueble = ((PanelInmuebles)FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INMUEBLES)).getInmuebleActual();
		
		JLabel lblEstado = new JLabel("Estado: "+inmueble.getEstado());
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEstado.setForeground(Color.white);
		lblEstado.setBounds(getWidth()-250, 30, 200, 35);
		getContentPane().add(lblEstado);
		
		
		dateChooserFecha.setDate(inmueble.getFechaIngreso());
		dateChooserFecha.setEnabled(false);
		((JTextField) dateChooserFecha.getDateEditor().getUiComponent()).setDisabledTextColor(Color.BLACK);
		((JTextField) dateChooserFecha.getDateEditor().getUiComponent()).setFont(new Font("Tahoma", Font.BOLD, 14));
		
		try {
			String tipo = ctrlInmueble.getTipoInmueble(Integer.decode(inmueble.getTipoInmueble()));
			cbxTipo.setSelectedItem(tipo);
		} catch (NumberFormatException e) {
			cbxTipo.setSelectedItem(inmueble.getTipoInmueble());
		}
		
		
		/* Completar panel datos */
		panelDatosPublicos.getTxtPais().setText(inmueble.getUbicacion().getPais());
		panelDatosPublicos.getTxtProvincia().setText(inmueble.getUbicacion().getProvincia());
		panelDatosPublicos.getTxtCiudad().setText(inmueble.getUbicacion().getCiudad());
		panelDatosPublicos.getTxtBarrio().setText(inmueble.getUbicacion().getBarrio());
		panelDatosPublicos.getTxtCalle().setText(inmueble.getUbicacion().getCalle());
		panelDatosPublicos.getTxtNumero().setText(inmueble.getUbicacion().getNumero().toString());
		
		if (inmueble.getUbicacion().getPiso() == null)
			panelDatosPublicos.getTxtPiso().setText("");
		else
			panelDatosPublicos.getTxtPiso().setText(inmueble.getUbicacion().getPiso().toString());
		
		if (inmueble.getUbicacion().getDpto() == null)
			panelDatosPublicos.getTxtDpto().setText("");
		else
			panelDatosPublicos.getTxtDpto().setText(inmueble.getUbicacion().getDpto().toString());
		
		panelDatosPublicos.getTxtCP().setText(inmueble.getUbicacion().getCp().toString());
		panelDatosPublicos.getTxtaDescripcion().setText(inmueble.getDescripcion());
		
		/* Completa panel datos privados */
		panelDatosPrivados.getBtnAgregar().setEnabled(false);
		panelDatosPrivados.getBtnAgregar().setVisible(false);
		panelDatosPrivados.getBtnEliminar().setEnabled(false);
		panelDatosPrivados.getBtnEliminar().setVisible(false);
		panelDatosPrivados.getScrollPane().setVisible(false);
		panelDatosPrivados.getScrollPane().setEnabled(false);
		
		if (inmueble.getNomenclatura() == null)
			panelDatosPrivados.getTxtNomenclatura().setText("");
		else
			panelDatosPrivados.getTxtNomenclatura().setText(inmueble.getNomenclatura());
		
		panelDatosPrivados.getCbxEstado().setSelectedItem(inmueble.getCondicion());
		panelDatosPrivados.getCheckBoxCartel().setSelected(inmueble.getCartelPublicado());
		
		panelDatosPrivados.getTxtDNI().setText(""+inmueble.getPropietario().getDni());
		panelDatosPrivados.getTxtNombre().setText(inmueble.getPropietario().getNombre());
		panelDatosPrivados.getTxtApellido().setText(inmueble.getPropietario().getApellido());
		if (inmueble.getPropietario().getContacto().getTelefono() != null)
			panelDatosPrivados.getTxtTelefono().setText(""+inmueble.getPropietario().getContacto().getTelefono());
		if (inmueble.getPropietario().getContacto().getCelular() != null)
			panelDatosPrivados.getTxtCelular().setText(""+inmueble.getPropietario().getContacto().getCelular());
		panelDatosPrivados.setPersona(inmueble.getPropietario());
		
		/* Completa panel de Operaciones */
		/*if (inmueble.getOperaciones().size() > 0) {
			for (int i=0; i < inmueble.getOperaciones().size(); i++) {
				
				panelOperaciones.getModeloTabla().agregar(inmueble.getOperaciones().get(i));
			}
		}*/
		
		if (inmueble.getOperaciones().size() > 0) {
			for (int i=0; i < inmueble.getOperaciones().size(); i++) {
				Operacion op = inmueble.getOperaciones().get(i);
				try {
					op.setTipoOperacion(ctrlInmueble.getNombreTipoOperacionById(Integer.decode(op.getTipoOperacion())));
					panelOperaciones.getModeloTabla().agregar(inmueble.getOperaciones().get(i));
				} catch (NumberFormatException e) {
					panelOperaciones.getModeloTabla().agregar(inmueble.getOperaciones().get(i));
				}
			}
		}
		
		/* Completa panel de Gastos Fijos */
		if (inmueble.getGastoFijos().size() > 0) {
			for (int i=0; i < inmueble.getGastoFijos().size(); i++) {
				GastoFijo gastoFijo = inmueble.getGastoFijos().get(i);
				
				try {
					gastoFijo.setGasto(ctrlInmueble.getNombreGastoFijoById(Integer.decode(gastoFijo.getGasto())));
					panelGastos.getModeloTabla().agregar(inmueble.getGastoFijos().get(i));
				} catch (NumberFormatException e) {
					panelGastos.getModeloTabla().agregar(inmueble.getGastoFijos().get(i));
				}
				
				//gastoFijo.setGasto(ctrlInmueble.getNombreGastoFijoById(Integer.decode(gastoFijo.getGasto())));
				//panelGastos.getModeloTabla().agregar(inmueble.getGastoFijos().get(i));
			}
		}
		
		/* Completa panel de Imagenes */
		if (inmueble.getImagenes().size() > 0) {
			for (int i = 0; i < inmueble.getImagenes().size(); i++) {
				if (inmueble.getImagenes().get(i).getPrincipal()) {
					panelImagenes.setImagenPpal(inmueble.getImagenes().get(i));
					
					JLabel lblImagePpal = new JLabel();
					lblImagePpal.setBounds(new Rectangle(0, 0, 430, 350));
					byte[] bytesB64 = inmueble.getImagenes().get(i).getImage();
					bytesB64 = Base64.getDecoder().decode(bytesB64);
					ImageIcon imageIcon = new ImageIcon(bytesB64);
					Image image = imageIcon.getImage().getScaledInstance(lblImagePpal.getWidth(), lblImagePpal.getHeight(), Image.SCALE_SMOOTH);
					lblImagePpal.setIcon(new ImageIcon(image));
					
					panelImagenes.getImagenPpal().setImage(bytesB64);
					panelImagenes.getLblImagePpal().setIcon(new ImageIcon(image));
				}else {
					panelImagenes.getModeloTabla().agregar(inmueble.getImagenes().get(i));
				}
			}
		}
		
	}
	
	private void initPanelHeader() {
		int heightElements = 30; 
		
		//Config de Header
		
		panelHeader = new JPanel();
		panelHeader.setBorder(new LineBorder(Color.WHITE, 1, true));
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setBounds((getWidth()/2)-200, 30, 400, 60);
		panelHeader.setLayout(null);
		
		
		// Tipo de inmueble
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setFont(new Font("Arial", Font.PLAIN, 14));
		lblTipo.setForeground(Color.white);
		lblTipo.setBounds(10, panelHeader.getHeight()/4, 40, heightElements);
		panelHeader.add(lblTipo);
		
		cbxTipo = new JComboBox<>();
		cbxTipo.setBackground(Color.lightGray);
		cbxTipo.setRequestFocusEnabled(false);
		cbxTipo.setBounds(60, panelHeader.getHeight()/4, 110, heightElements);
		cbxTipo.setFocusable(false);
		
		cbxTipo.setModel(new DefaultComboBoxModel<>(ctrlInmueble.getArrayTipoInmuebles()));
		cbxTipo.setSelectedIndex(1);
		
		if (isModificar) {
			//cbxTipo.setSelectedIndex(inmueble.getTipoInmueble().getNumero()-1);
			cbxTipo.setEnabled(false);
			cbxTipo.setRenderer(new DefaultListCellRenderer(){
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) {
					setForeground(Color.darkGray);
					super.paint(g);
				}
			});
		}
		
		panelHeader.add(cbxTipo);
		
		
		// Fecha de ingreso de inmueble
		
		dateChooserFecha = new JDateChooser();
		dateChooserFecha.setRequestFocusEnabled(false);
		dateChooserFecha.setDate(Calendar.getInstance().getTime());
		dateChooserFecha.setFont(new Font("Arial", Font.BOLD, 14));
		dateChooserFecha.setBounds(230, panelHeader.getHeight()/4, 160, heightElements);
		panelHeader.add(dateChooserFecha);
		
		getContentPane().add(panelHeader);
		
	}
	
	private void initPanelNavBar() {
		panelNavBar = new JPanel();
		FlowLayout flPanelNavBar = (FlowLayout) panelNavBar.getLayout();
		flPanelNavBar.setVgap(15);
		flPanelNavBar.setHgap(60);
		panelNavBar.setBackground(ConfigGUI.COLOR_FONDO);
		panelNavBar.setBorder(new MatteBorder(1, 2, 1, 2, Color.cyan));
		panelNavBar.setBounds(0, 100, getWidth(), 60);
		
		//Label Datos Publicos
		
		setJLabelNavBar(new JLabel("DATOS PUBLICOS"), LABEL_DATOS_PUBLICOS);
		setJLabelNavBar(new JLabel("DATOS PRIVADOS"), LABEL_DATOS_PRIVADOS);
		setJLabelNavBar(new JLabel("OPERACIONES"), LABEL_OPERACIONES);
		setJLabelNavBar(new JLabel("GASTOS FIJOS"), LABEL_GASTOS_FIJOS);
		setJLabelNavBar(new JLabel("IMAGENES"), LABEL_IMAGENES);
		
		
		getContentPane().add(panelNavBar);
	}
	
	private void setJLabelNavBar (JLabel label, int opcion) {
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(195, 195, 195));
		label.setFont(new Font("Calibri", Font.PLAIN, 30));
		
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				label.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setForeground(ConfigGUI.COLOR_SINFOCO);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				switch (opcion) {
				case LABEL_DATOS_PUBLICOS:
					panelDatosPublicos.setVisible(true);
					panelDatosPrivados.setVisible(false);
					panelOperaciones.setVisible(false);
					panelGastos.setVisible(false);
					panelImagenes.setVisible(false);
					break;
					
				case LABEL_DATOS_PRIVADOS:
					panelDatosPublicos.setVisible(false);
					panelDatosPrivados.setVisible(true);
					panelOperaciones.setVisible(false);
					panelGastos.setVisible(false);
					panelImagenes.setVisible(false);
					break;
					
				case LABEL_OPERACIONES:
					panelDatosPublicos.setVisible(false);
					panelDatosPrivados.setVisible(false);
					panelOperaciones.setVisible(true);
					panelGastos.setVisible(false);
					panelImagenes.setVisible(false);
					break;
					
				case LABEL_GASTOS_FIJOS:
					panelDatosPublicos.setVisible(false);
					panelDatosPrivados.setVisible(false);
					panelOperaciones.setVisible(false);
					panelGastos.setVisible(true);
					panelImagenes.setVisible(false);
					break;
					
				case LABEL_IMAGENES:
					panelDatosPublicos.setVisible(false);
					panelDatosPrivados.setVisible(false);
					panelOperaciones.setVisible(false);
					panelGastos.setVisible(false);
					panelImagenes.setVisible(true);
					break;
				}
			}
		});
		
		panelNavBar.add(label);
	}

	private void initPanelCentral() {
		panelCentral = new JPanel();
		panelCentral.setBorder(new MatteBorder(0, 2, 0, 2, Color.cyan));
		panelCentral.setLayout(null);
		panelCentral.setBounds(0, panelNavBar.getY()+panelNavBar.getHeight(), getWidth(), 480);
		
		panelDatosPublicos.setVisible(true);
		panelDatosPrivados.setVisible(false);
		panelOperaciones.setVisible(false);
		panelGastos.setVisible(false);
		panelImagenes.setVisible(false);
		
		panelCentral.add(panelDatosPublicos);
		panelCentral.add(panelDatosPrivados);
		panelCentral.add(panelOperaciones);
		panelCentral.add(panelGastos);
		panelCentral.add(panelImagenes);
		getContentPane().add(panelCentral);
	}
	
	private void initPanelFooter() {
		panelFooter = new JPanel();
		panelFooter.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooter.setBorder(new MatteBorder(0, 2, 1, 2, Color.cyan));
		panelFooter.setLayout(null);
		int footerHeight = getHeight()-(panelCentral.getHeight()+panelCentral.getY());
		panelFooter.setBounds(0, panelCentral.getY()+panelCentral.getHeight(), getWidth(), footerHeight);
		
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAceptar();
			}
		});
		btnAceptar.setHorizontalTextPosition(SwingConstants.LEADING);
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAceptar.setFocusable(false);
		btnAceptar.setBorderPainted(false);
		btnAceptar.setBackground(ConfigGUI.COLOR_BTN_SUCESS);
		btnAceptar.setBounds((panelFooter.getWidth()/2)+10, panelFooter.getHeight()/8, 200, 40);
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCancelar();
			}
		});
		btnCancelar.setHorizontalTextPosition(SwingConstants.LEADING);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setFocusable(false);
		btnCancelar.setBorderPainted(false);
		btnCancelar.setBackground(ConfigGUI.COLOR_BTN_DANGER);
		btnCancelar.setBounds((panelFooter.getWidth()/2)-10-btnAceptar.getWidth(), panelFooter.getHeight()/8, 200, 40);
		
		panelFooter.add(btnAceptar);
		panelFooter.add(btnCancelar);
		
		getContentPane().add(panelFooter);
	}
	
	private void eventoAceptar() {
		
		//if (! this.isModificar ) {
			boolean isOK = true;
			
			String sDate = ((JTextField) dateChooserFecha.getDateEditor().getUiComponent()).getText();
			Color color = ((JTextField) dateChooserFecha.getDateEditor().getUiComponent()).getForeground();
			
			if (sDate.equals("") || color.equals(Color.red)) {
				isOK = false;
				((JTextField) dateChooserFecha.getDateEditor().getUiComponent()).setText("ERROR");
			}
			
			if (!panelDatosPublicos.isValidoPanelDatosPub()) {
				((JLabel)panelNavBar.getComponent(LABEL_DATOS_PUBLICOS-1)).setForeground(ConfigGUI.COLOR_ERROR);
				isOK = false;
			}
			
			if (!panelDatosPrivados.isValidoPanelDatosPriv()) {
				((JLabel)panelNavBar.getComponent(LABEL_DATOS_PRIVADOS-1)).setForeground(ConfigGUI.COLOR_ERROR);
				isOK = false;
			}
			
			if (isOK) {
				
				//Add to Database
				String pais = panelDatosPublicos.getTxtPais().getText();
				String provincia = panelDatosPublicos.getTxtProvincia().getText();
				String ciudad = panelDatosPublicos.getTxtCiudad().getText();
				
				String barrio = null;
				if (!panelDatosPublicos.getTxtBarrio().getText().equals(""))
					barrio = panelDatosPublicos.getTxtBarrio().getText();
				
				String calle = panelDatosPublicos.getTxtCalle().getText();
				Integer numero = Integer.decode(panelDatosPublicos.getTxtNumero().getText());
				
				Integer piso = null;
				if (!panelDatosPublicos.getTxtPiso().getText().equals(""))
					piso = Integer.decode(panelDatosPublicos.getTxtPiso().getText());
				
				Character dpto = null;
				if (!panelDatosPublicos.getTxtDpto().getText().equals(""))
					dpto = panelDatosPublicos.getTxtDpto().getText().charAt(0);
				
				Integer cp = Integer.decode(panelDatosPublicos.getTxtCP().getText());
				
				Persona propietario = panelDatosPrivados.getPersona();
				String nomenclatura = panelDatosPrivados.getTxtNomenclatura().getText();
				String condicion = (String) panelDatosPrivados.getCbxEstado().getSelectedItem();
				Boolean cartelPublicado = panelDatosPrivados.getCheckBoxCartel().isSelected();
				
				Ubicacion ubicacion = new Ubicacion(pais, provincia, ciudad, barrio, calle, numero, piso, dpto, cp);
				Inmueble inmueble = new Inmueble((String)cbxTipo.getSelectedItem(), dateChooserFecha.getDate(), ubicacion, propietario);
				
				if (!ValidateConstants.isTextEmpty(nomenclatura))
					inmueble.setNomenclatura(nomenclatura);
				
				inmueble.setCondicion(condicion);
				inmueble.setCartelPublicado(cartelPublicado);
				
				Integer idInmueble = null;
				
				if (!isModificar) {
					inmueble.setEstado("DISPONIBLE");
					idInmueble = ctrlInmueble.insertIntoInmueble(inmueble);
					inmueble.setIdInmueble(idInmueble);
					inmueble.setTipoInmueble(""+ctrlInmueble.getNameTipoInmueble((String)cbxTipo.getSelectedItem()));
				}
				else {
					inmueble.setIdInmueble(this.inmueble.getIdInmueble());
					inmueble.setEstado(this.inmueble.getEstado());
					ctrlInmueble.updateInmueble(inmueble);
				}

				
				
			//Agregar Operaciones
				if (panelOperaciones.getModeloTabla().getObjetos() != null && panelOperaciones.getModeloTabla().getObjetos().size() > 0) {
					
					if (isModificar) {
						ctrlInmueble.deleteInmueble_Operacion(this.inmueble.getIdInmueble());
					}
					
					for (int i = 0; i <  panelOperaciones.getModeloTabla().getObjetos().size(); i++) {
						Operacion operacion = (Operacion) panelOperaciones.getModeloTabla().getObjetos().get(i);
						
						ctrlInmueble.insertIntoOperacion(operacion, inmueble.getIdInmueble());
						inmueble.getOperaciones().add(operacion);		
					}			
				}
				
			//Agregar Gasto Fijo
				if (panelGastos.getModeloTabla().getObjetos() != null && panelGastos.getModeloTabla().getObjetos().size() > 0) {
					
					if (isModificar) {
						ctrlInmueble.deleteInmueble_Gasto(this.inmueble.getIdInmueble());
					}
					
					for (int i = 0; i <  panelGastos.getModeloTabla().getObjetos().size(); i++) {
						GastoFijo gastoFijo = (GastoFijo) panelGastos.getModeloTabla().getObjetos().get(i);
						ctrlInmueble.insertIntoGasto(gastoFijo, inmueble.getIdInmueble());
						inmueble.getGastoFijos().add(gastoFijo);
						
					}
				
				}
				
				
				Imagen imagenPrincipal = panelImagenes.getImagenPpal();
				
				if (imagenPrincipal != null) {
					ctrlInmueble.insertIntoImagen(imagenPrincipal, inmueble.getIdInmueble());
					inmueble.getImagenes().add(imagenPrincipal);
				}
				
			//Agregar Imagenes Publicas
				if (panelImagenes.getModeloTabla().getObjetos() != null && panelImagenes.getModeloTabla().getObjetos().size() > 0) {
					
					for (int i = 0; i <  panelImagenes.getModeloTabla().getObjetos().size(); i++) {
						Imagen imagen = (Imagen) panelImagenes.getModeloTabla().getObjetos().get(i);
						ctrlInmueble.insertIntoImagen(imagen, inmueble.getIdInmueble());
						inmueble.getImagenes().add(imagenPrincipal);
					}
				}
				
				this.dispose();
				PanelInmuebles panelInmuebles= (PanelInmuebles) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_INMUEBLES);
				
				if (!isModificar) {
					panelInmuebles.getModeloTabla().agregar(inmueble);
					panelInmuebles.getInmuebles().add(inmueble);
				}
				else {
					panelInmuebles.getModeloTabla().modificarObjeto(panelInmuebles.getFilaSeleccionada(), inmueble);
					panelInmuebles.getInmuebles().set(panelInmuebles.getFilaSeleccionada(), inmueble);
				}
				
				FramePpal.getFrameppal().setEnabled(true);
				FramePpal.getFrameppal().setVisible(true);
			}
				
		//}
	}
	
	private void eventoCancelar() {
		dispose();
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
	}
	
	@Override
	protected void eventoCerrar() {
		FramePpal.getFrameppal().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		
		
		//restaurarFrame();
	}

}
