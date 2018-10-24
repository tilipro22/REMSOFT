package app.alquileres;


import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import gui.ConfigGUI;
import gui.paneles.PanelConstants;
import gui.tabla.ModeloTabla;
import logica.PagoAlquiler;

public class PanelPagosAlquiler extends PanelConstants {
	
	private static final long serialVersionUID = 1L;
	private static final String[] columns = {"Num Pago", "Fecha Vto", "Total", "Pagado", "Moneda"};
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private JScrollPane scrollPane;
	
	private List<PagoAlquiler> listPagos=new ArrayList<>();
	
	private Integer diaVto;

	/**
	 * Create the panel.
	 */
	public PanelPagosAlquiler(List<PagoAlquiler> listPagos) {
		this.listPagos = listPagos;
		setLayout(null);
		//setBounds(0, 0, 500, 680);
		setBackground(ConfigGUI.COLOR_BTN_DANGER);
		
		initModeloTabla();
		add(scrollPane);
		
		if (listPagos != null)
			cargarPagos();
		
		revalidate();
	}

	private void initModeloTabla() {
		modeloTabla = new ModeloTabla(columns) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				DecimalFormat decimalFormat = new DecimalFormat("#.00");
				PagoAlquiler pagoAlquiler = (PagoAlquiler) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					if (pagoAlquiler.getPagado()) {
						return pagoAlquiler.getNumPago() + " | " + pagoAlquiler.getEstadoPago();
					}
					return pagoAlquiler.getNumPago() + " | " + pagoAlquiler.getEstadoPago();
					
				case 1:
					return diaVto + "/" + pagoAlquiler.getMesCorrespondiente() + "/" + pagoAlquiler.getAnioCorrespondiente();
					
				case 2:
					return decimalFormat.format(pagoAlquiler.getMontoTotal());
					
				case 3:
					if (pagoAlquiler.getMontoPagado().compareTo(new BigDecimal("00.00")) == 0)
						return "00.00";
					else
						return decimalFormat.format(pagoAlquiler.getMontoPagado());
					
				case 4:
					return "ARS";

				}
				return null;
			}
		};
		
		tabla = new JTable(modeloTabla);
		setConfigTable(tabla, modeloTabla);
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(0, 0, 500, 680);
		scrollPane.setViewportView(tabla);
	}
	
	public void cargarPagos() {
		
		if (listPagos.size() != 0) {
			modeloTabla.getObjetos().clear();
		}
		
		for (int i = 0; i < listPagos.size(); i++) {			
			modeloTabla.agregar(listPagos.get(i));
		}
	}
	
	public void setListPagos(List<PagoAlquiler> list) {
		this.listPagos = list;
	}

	
	/* Getters & Setters */
	
	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

	public JTable getTabla() {
		return tabla;
	}

	public List<PagoAlquiler> getListPagos() {
		return listPagos;
	}

	public Integer getDiaVto() {
		return diaVto;
	}

	public void setDiaVto(Integer diaVto) {
		this.diaVto = diaVto;
	}
	
	
}
