package app.reservas;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import app.ventas.FrameVenta;
import gui.FramePpal;
import gui.paneles.PanelDefault;
import gui.tabla.ModeloTabla;

public class PanelReservas extends PanelDefault{
	
	private static final long serialVersionUID = 1L;

	public PanelReservas() {
		lblTitle.setText("Reservas");
		lblTitle.setVisible(true);
		
		inicializarModeloTabla();
		
		add(scrollPane);
	}

	@Override
	protected void inicializarModeloTabla() {
		// Fecha, Inmueble, Descripcion, Propietario, Contacto, Comprador, Monto Total, Moneda, Estado
		
		String[] columns =  {"Fecha", "Inmueble", "Descripcion", "Propietario", "Contacto", "Comprador", "Monto Total", "Moneda", "Estado"};
		modeloTabla = new ModeloTabla(columns) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				return null;
			}
		};
		
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setViewportView(tabla);
	}

	@Override
	protected void eventoAgregar() {
		FramePpal.getFrameppal().setEnabled(false);
		FrameVenta frameVenta = new FrameVenta(modeloTabla, false);
		frameVenta.setVisible(true);
	}

	@Override
	protected void eventoModificar() {
		
	}

	@Override
	protected void eventoEliminar() {
		
	}

}
