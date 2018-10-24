package app.ventas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.FramePpal;
import gui.paneles.PanelDefault;
import gui.tabla.ModeloTabla;
import logica.FormaPagoVta;
import logica.Venta;
import util.Utils;

public class PanelVenta extends PanelDefault {

	private static final long serialVersionUID = 1L;
	private final DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	/* Elements */
	FrameVenta frameVenta;
	private FrameEscriturar frameEscriturar;
	private FramePagoVenta framePagoVenta;
	private CtrlVentas ctrlVentas = new CtrlVentas();
	private List<Venta> listVentas = new ArrayList<>();
	
	/* Selected */
	private int filaSeleccionada, filaSeleccEscriturar = -1, filaSeleccVerPagos = -1;
	
	/* Panel Abajo */
	private ButtonDefaultABM btnEscriturar, btnVerPagos;

	public PanelVenta() {
		
		lblTitle.setText("Contratos de Venta");
		lblTitle.setVisible(true);
		
		inicializarModeloTabla();
		cargarTabla();
		initPanelAbajo();

		add(scrollPane);
	}

	/* Inits */
	
	private void initPanelAbajo() {
		btnEscriturar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ESCRITURAR);
		btnEscriturar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoEscriturar();
			}
		});
		
		btnVerPagos = new ButtonDefaultABM(ButtonDefaultABM.BOTON_VER_PAGOS);
		btnVerPagos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoVerPagos();
			}
		});
		
		panelAbajo.add(btnEscriturar);
		panelAbajo.add(btnVerPagos);
	}
	
	
	/* Configs de tablas */
	
	@Override
	protected void inicializarModeloTabla() {
		String[] columns = {"Fecha", "Inmueble", "Descripcion", "Comprador", "Monto Total", "Monto Pagado", "Moneda", "Estado"};
		super.modeloTabla = new ModeloTabla(columns) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Venta venta = (Venta) modeloTabla.getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					return Utils.printFecha(venta.getFecha());
					
				case 1:
					CtrlInmueble ctrlInmueble = new CtrlInmueble();
					return ctrlInmueble.getTipoInmueble(Integer.decode(venta.getInmueble().getTipoInmueble()));
					
				case 2:
					return venta.getInmueble().getUbicacion().getCalle() + " " + venta.getInmueble().getUbicacion().getNumero();
					
				case 3:
					return venta.getComprador().getApellido() + ", " + venta.getComprador().getNombre();
					
				case 4:
					if (venta.getPrecioVenta().compareTo(new BigDecimal(00.00)) == 0)
						return "00,00";
					else
						return decimalFormat.format(venta.getPrecioVenta());
					
				case 5:
					BigDecimal pagadoFinal = generarPagadoFinal(venta.getFormaPagoVtas());
					
					if (pagadoFinal.compareTo(new BigDecimal(0.0)) == 0)
						return "00,00";
					else
						return decimalFormat.format(pagadoFinal);
					
				case 6:
					return venta.getMoneda();
					
				case 7:
					return venta.getEstado();
				}
				
				return null;
			}
		};
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		//scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		//scrollPane.setBounds(0, 0, panelTablaVta.getWidth(), panelTablaVta.getHeight());
		scrollPane.setViewportView(tabla);
	}
	
	public void cargarTabla() {
		listVentas = ctrlVentas.getListVentas();
		
		if (listVentas.size() > 0) {
			for (Venta venta : listVentas) {
				modeloTabla.agregar(venta);
			}
		}
	}
	
	public void recargarVentas() {
		listVentas = new ArrayList<>();
		
		int size = modeloTabla.getObjetos().size();
		for (int i=0; i < size; i++) {
			modeloTabla.eliminar(0);
		}
		
		cargarTabla();
	}
	
	
	/* Functions */
	
	private BigDecimal generarPagadoFinal(List<FormaPagoVta> formaPagoVtas) {
		BigDecimal pagadoFinal = new BigDecimal(00.00);
		
		for (int i=0; i < formaPagoVtas.size(); i++) {
			FormaPagoVta formaPagoVta = formaPagoVtas.get(i);
			pagadoFinal = pagadoFinal.add(formaPagoVta.getMontoPagado());
		}
		
		return pagadoFinal;
	}
	
	/* Events */

	@Override
	protected void eventoAgregar() {
		FramePpal.getFrameppal().setEnabled(false);
		frameVenta = new FrameVenta(false);
		frameVenta.setVisible(true);
	}

	@Override
	protected void eventoModificar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			FramePpal.getFrameppal().setEnabled(false);
			frameVenta = new FrameVenta(true);
			frameVenta.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Venta");
		}
	}

	@Override
	protected void eventoEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			//Venta venta = ((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getMo
			
			int option = JOptionPane.showConfirmDialog(null, "Esta seguro de Eliminar la Venta?");
			
			if (option == 0) {
				modeloTabla.eliminar(filaSeleccionada);
				listVentas.remove(filaSeleccionada);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Venta");
		}
	}
	
	private void eventoEscriturar() {
		filaSeleccEscriturar = tabla.getSelectedRow();
		
		if (filaSeleccEscriturar != -1) {
			Venta venta = listVentas.get(filaSeleccEscriturar);
			if (!venta.getEscriturizado()) {
				Date fechaEscrituracion = null;
				
				if (venta.getDiasEscriturizacion() > 0) {
					fechaEscrituracion = Utils.sumarDias(venta.getFecha(), venta.getDiasEscriturizacion());
				}
				
				frameEscriturar = new FrameEscriturar(fechaEscrituracion, venta.getMultaEscriturizacion(), venta.getPrecioVenta());
				frameEscriturar.setVisible(true);
				FramePpal.getFrameppal().setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "El Contrato de Compra-Venta ya se encuentra Escriturizado");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Venta para Escriturar");
		}
	}
	
	private void eventoVerPagos() {
		filaSeleccVerPagos = tabla.getSelectedRow();
		
		if (filaSeleccVerPagos != -1) {
			Venta venta = (Venta) modeloTabla.getObjetos().get(filaSeleccVerPagos);
			
			if (venta.getEscriturizado()) {
				framePagoVenta = new FramePagoVenta(venta);
				framePagoVenta.setVisible(true);
				FramePpal.getFrameppal().setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "El Contrato de Compra-Venta aun no esta Escriturizado");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ningun Contrato de Venta para ver sus pagos.");
		}
	}
	
	/* Getters & Setters */
	
	public FrameVenta getFrameVenta() {
		return frameVenta;
	}
	

	public void setFrameVenta(FrameVenta frameVenta) {
		this.frameVenta = frameVenta;
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

	public int getFilaSeleccEscriturar() {
		return filaSeleccEscriturar;
	}

	public Venta getVentaSeleccionada() {
		return (Venta) modeloTabla.getObjetos().get(filaSeleccEscriturar);
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}
	
	
	
}
