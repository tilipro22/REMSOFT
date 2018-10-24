package app.alquileres;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.paneles.PanelDefault;
import gui.tabla.ModeloTabla;
import logica.Alquiler;
import logica.FormaPagoVta;
import logica.PagoAlquiler;
import util.Utils;

public class PanelAlquiler extends PanelDefault {
	

	private static final long serialVersionUID = 1L;
	private JPanel panelTablaAlq, panelHeaderTablaAlq, panelHeaderPagos, panelFooterPagos;
	private PanelPagosAlquiler panelPagos;
	private FrameAlquiler frameAlquiler;
	private FramePagoAlquiler framePagoAlquiler;
	
	private CtrlAlquileres ctrlAlquileres=new CtrlAlquileres();
	private List<Alquiler> listAlquiler = new ArrayList<Alquiler>();
	
	private ButtonDefaultABM btnVerPagos = new ButtonDefaultABM(ButtonDefaultABM.BOTON_VER_PAGOS);
	private ButtonDefaultABM btnAsentarPago, btnCerrarPago;
	
	private Integer filaSeleccionada = -1;
	private Integer filaSeleccPagos = -1;

	private PagoAlquiler pagoAlquiler;
	
	
	public PanelAlquiler() {
		panelAbajo.setBounds(100, 780, 1300, 100);
		panelAbajo.setBackground(ConfigGUI.COLOR_FONDO);

		
		panelTablaAlq = new JPanel();
		panelTablaAlq.setLayout(null);
		panelTablaAlq.setBackground(ConfigGUI.COLOR_ERROR);
		panelTablaAlq.setBounds(lblAtras.getWidth(), lblAtras.getHeight(), 1300, getHeight()-lblAtras.getHeight()-panelAbajo.getHeight());
		
		initPanelHeaderTablaAlq();
		
		panelPagos = new PanelPagosAlquiler(null);
		panelPagos.setBounds(panelTablaAlq.getWidth()+panelTablaAlq.getX(), lblAtras.getHeight(), 500, getHeight()-lblAtras.getHeight()-panelAbajo.getHeight());
		
		initPanelHeaderPagos();
		initPanelFooterPagos();
		
		inicializarModeloTabla();
		panelTablaAlq.add(scrollPane);
		cargarListaAlquileres();
		
		btnVerPagos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada != -1) { 
					int size = listAlquiler.get(filaSeleccionada).getListPagos().size();
					System.out.println(size);
					panelPagos.setDiaVto(listAlquiler.get(filaSeleccionada).getDiaVto());
					panelPagos.setListPagos(listAlquiler.get(filaSeleccionada).getListPagos());
					panelPagos.cargarPagos();
					panelPagos.getTabla().setVisible(true);
					tabla.setEnabled(false);
					desactivarButtons();
					panelFooterPagos.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "No selecciono ningun Alquiler");
				}
			}
		});
		super.panelAbajo.add(btnVerPagos);
		
		add(panelTablaAlq);
		add(panelHeaderTablaAlq);
		add(panelPagos);

	}
	
	/* Inits */
	
	private void initPanelHeaderTablaAlq () {
		panelHeaderTablaAlq = new JPanel();
		panelHeaderTablaAlq.setBackground(ConfigGUI.COLOR_FONDO);
		//panelHeaderTablaAlq.setBorder(new LineBorder(Color.WHITE));
		panelHeaderTablaAlq.setLayout(null);
		panelHeaderTablaAlq.setBounds(panelTablaAlq.getX(), lblAtras.getY(), panelTablaAlq.getWidth(), 100);
		
		JLabel lblTitle = new JLabel("Contratos de Alquiler");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		lblTitle.setBorder(new LineBorder(Color.WHITE));
		lblTitle.setBounds(0, 50, 350, 50);
		
		JLabel lblAlDia = createLabelEstado("Al dia", "/images/flag_green.png");
		lblAlDia.setBounds(1000, 50, 100, 50);
		JLabel lblConDeuda = createLabelEstado("Con deuda", "/images/flag_red.png");
		lblConDeuda.setBounds(1100, 50, 100, 50);
		JLabel lblFinalizado = createLabelEstado("Finalizado", "/images/flag_blue.png");
		lblFinalizado.setBounds(1200, 50, 100, 50);
		
		panelHeaderTablaAlq.add(lblAlDia);
		panelHeaderTablaAlq.add(lblConDeuda);
		panelHeaderTablaAlq.add(lblFinalizado);
		panelHeaderTablaAlq.add(lblTitle);
	}
	
	private void initPanelHeaderPagos() {
		panelHeaderPagos  = new JPanel();
		panelHeaderPagos.setBackground(ConfigGUI.COLOR_FONDO);
		//panelHeaderPagos.setBorder(new LineBorder(Color.WHITE));
		panelHeaderPagos.setLayout(null);
		panelHeaderPagos.setBounds(panelPagos.getX(), lblAtras.getY(), panelPagos.getWidth(), 100);
		
		JLabel lblTitle = new JLabel("Pagos Registrados del Alquiler");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitle.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		lblTitle.setBorder(new LineBorder(Color.WHITE));
		lblTitle.setBounds(0, 50, 350, 50);
		
		panelHeaderPagos.add(lblTitle);
		add(panelHeaderPagos);
	}

	@Override
	protected void inicializarModeloTabla() {
		String[] columns = {"Estado", "Inicio contrato", "Fin contrato", "Inmueble", "Inquilino", "Proximo Vto."};
		super.modeloTabla = new ModeloTabla(columns) {
			

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Alquiler alquiler = (Alquiler) getObjetos().get(fila);
				Calendar calendar = Calendar.getInstance();
				
				switch (columna) {
				case 0:
					return alquiler.getEstadoAlquiler();
					
				case 1:
					calendar.setTime(alquiler.getFechaInicio());
					return calendar.get(Calendar.YEAR) + "-" + String.format("%02d", (calendar.get(Calendar.MONTH)+1)) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
					
				case 2:
					calendar.setTime(alquiler.getFechaFin());
					return calendar.get(Calendar.YEAR) + "-" + String.format("%02d", (calendar.get(Calendar.MONTH)+1)) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
					
				case 3:
					CtrlInmueble ctrlInmueble=new CtrlInmueble();
					
					Integer id = Integer.decode(alquiler.getInmueble().getTipoInmueble());
					
					String tipoInmueble = ctrlInmueble.getTipoInmueble(id);
					return tipoInmueble;
					
				case 4:
					return alquiler.getInquilino().getApellido()+", "+alquiler.getInquilino().getNombre()
							+" ("+alquiler.getInquilino().getDni()+")";
					
				case 5:
					//calendar.setTime(alquiler.getFechaFin());
					int diaVto = alquiler.getDiaVto();
					
					calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					int yearActual = calendar.get(Calendar.YEAR);
					int mesActual = calendar.get(Calendar.MONTH)+1;
					int diaActual = calendar.get(Calendar.DAY_OF_MONTH);
					
					if (diaActual > diaVto ) {
						// CAMBIAR EL VALOR DE AL DIA A QUE DEBE
						
						if (mesActual == 12) {
							return (yearActual+1)+"-01"+String.format("%02d", diaVto);
						}
								
						else {
							return yearActual+"-"+String.format("%02d", mesActual+1)+"-"+String.format("%02d", diaVto);
						}
						
					}
					else {
						return yearActual+"-"+String.format("%02d", mesActual)+"-"+String.format("%02d", diaVto);
					}
					
				}
				return null;
			}
		};
		
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(0, 0, panelTablaAlq.getWidth(), panelTablaAlq.getHeight());
		scrollPane.setViewportView(tabla);
	}
	
	private void initPanelFooterPagos() {
		panelFooterPagos = new JPanel();
		panelFooterPagos.setLayout(new FlowLayout());
		panelFooterPagos.setBackground(ConfigGUI.COLOR_FONDO);
		panelFooterPagos.setBounds(panelPagos.getX(), panelPagos.getY()+panelPagos.getHeight(), panelPagos.getWidth(), 100);
		
		btnAsentarPago = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ASENTAR_PAGO);
		btnAsentarPago.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoBtnAsentarPago();
			}
		});
		
		btnCerrarPago = new ButtonDefaultABM(ButtonDefaultABM.BOTON_CERRAR);
		btnCerrarPago.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelPagos.getTabla().setVisible(false);
				tabla.setEnabled(true);
				habilitarButtons();
				panelFooterPagos.setVisible(false);
			}
		});
		btnCerrarPago.setPreferredSize(new Dimension(90, 90));
		
		panelFooterPagos.add(btnAsentarPago);
		panelFooterPagos.add(btnCerrarPago);
		panelFooterPagos.setVisible(false);
		
		add(panelFooterPagos);
	}
	
	/* Configs */
	
	@SuppressWarnings("deprecation")
	private void cargarListaAlquileres() {
		listAlquiler = ctrlAlquileres.getListaAlquileres();
		
		for (Alquiler alquiler : listAlquiler) {
			
			for (int i=0; i < alquiler.getListPagos().size(); i++) {
				PagoAlquiler pagoAlquiler= alquiler.getListPagos().get(i);
				
				if (!pagoAlquiler.getEstadoPago().equals("PAGADO") && !pagoAlquiler.getEstadoPago().equals("ADEUDA")) {
					
					if (Utils.diferenciaDias(new Date(), 
							new Date(pagoAlquiler.getAnioCorrespondiente()-1900, pagoAlquiler.getMesCorrespondiente()-1, alquiler.getDiaVto()))
							.intValue() > 0) {
						alquiler.getListPagos().get(i).setEstadoPago("ADEUDA");
						alquiler.setEstadoAlquiler("CON DEUDA");
					}
				}
			}
			
			modeloTabla.agregar(alquiler);
		}
	}
	
	public void recargarAlquileres() {
		listAlquiler = new ArrayList<>();
		
		int size = modeloTabla.getObjetos().size();
		for (int i=0; i < size; i++) {
			modeloTabla.eliminar(0);
		}
		
		cargarListaAlquileres();
	}
	
	private JLabel createLabelEstado(String title, String pathIcon) {
		JLabel lbl = new JLabel(title);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setIcon(new ImageIcon(PanelAlquiler.class.getResource(pathIcon)));
		lbl.setForeground(Color.WHITE);
		
		return lbl;
	}

	public void desactivarButtons() {
		panelAbajo.setVisible(false);
	}
	
	public void habilitarButtons() {
		panelAbajo.setVisible(true);
	}
	
	/* Events */
	
	@Override
	protected void eventoAgregar() {
		
		FramePpal.getFrameppal().setEnabled(false);
		frameAlquiler = new FrameAlquiler(false);
		frameAlquiler.setVisible(true);
	}

	@Override
	protected void eventoEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			//Venta venta = ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getMo
			
			int option = JOptionPane.showConfirmDialog(null, "Esta seguro de Eliminar el Alquiler?");
			
			if (option == 0) {
				modeloTabla.eliminar(filaSeleccionada);
				listAlquiler.remove(filaSeleccionada);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Alquiler");
		}
	}
	
	public void eventoBtnAsentarPago() {
		filaSeleccPagos = panelPagos.getTabla().getSelectedRow();
		
		if (filaSeleccPagos != -1) {
			pagoAlquiler = (PagoAlquiler) panelPagos.getModeloTabla().getObjetos().get(filaSeleccPagos);
			
			if (pagoAlquiler.getPagado()) {
				JOptionPane.showMessageDialog(null, "Ya se encuentra PAGADO");
			}
			else {
				framePagoAlquiler = new FramePagoAlquiler(listAlquiler.get(filaSeleccionada), pagoAlquiler);
				framePagoAlquiler.setVisible(true);
				
				FramePpal.getFrameppal().setEnabled(false);
				
			}			
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	@Override
	protected void eventoModificar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			FramePpal.getFrameppal().setEnabled(false);
			frameAlquiler = new FrameAlquiler(true);
			frameAlquiler.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Alquiler para modificar");
		}
	}
	
	/* Getters & Setters */

	public FrameAlquiler getFrameAlquiler() {
		return frameAlquiler;
	}
	
	public void setFrameAlquiler(FrameAlquiler frameAlquiler) {
		this.frameAlquiler = frameAlquiler;
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}
	
	public JTable getTabla() {
		return tabla;
	}

	public PanelPagosAlquiler getPanelPagos() {
		return panelPagos;
	}

	public FramePagoAlquiler getFramePagoAlquiler() {
		return framePagoAlquiler;
	}

	public Integer getFilaSeleccPagos() {
		return filaSeleccPagos;
	}

	public List<Alquiler> getListAlquiler() {
		return listAlquiler;
	}

	public void setListAlquiler(List<Alquiler> listAlquiler) {
		this.listAlquiler = listAlquiler;
	}

	public Integer getFilaSeleccionada() {
		return filaSeleccionada;
	}

	
	
	

}
