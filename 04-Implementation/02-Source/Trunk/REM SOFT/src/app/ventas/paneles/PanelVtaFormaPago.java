package app.ventas.paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import app.ventas.PanelVenta;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.FormaPagoVta;

public class PanelVtaFormaPago extends JPanel {

	private static final long serialVersionUID = 1L;

	/* Constants */
	private static final String[] COLUMNAS = {"Fecha Vto", "Descripcion", "Monto", "Moneda"};
	
	/* Elements */
	private JPanel panelNav, panelTable;
	
	private ModeloTabla modeloTabla;
	private JScrollPane scrollPane;
	private JTable tabla;
	
	ButtonDefaultABM btnAgregar, btnModificar, btnEliminar, btnGenerarCuotas;
	
	private int filaSeleccionada = -1;
	private boolean isAgregarPago, isModificarPago, isEliminarPago, isGenerarCuotas;
	
	public PanelVtaFormaPago() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 750, 290);
		setLayout(null);
		
		initPanelNav();
		btnGenerarCuotas = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnGenerarCuotas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().eventoGenerarCuota();
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelInfo().setVisible(false);
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNav().setVisible(false);
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelGenerarCuotas().setVisible(true);
				configButtons(0);
			}
		});
		btnGenerarCuotas.setText("Generar Cuotas");
		btnGenerarCuotas.setBounds(new Rectangle(getWidth()-200, 5, 200, 40));
		btnGenerarCuotas.setBackground(ConfigGUI.COLOR_BTN_WARNING);
		btnGenerarCuotas.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnGenerarCuotas.setVerticalAlignment(SwingConstants.CENTER);
		add(btnGenerarCuotas);
		initPanelTable();
	}
	
	public void configButtons(int option) {
		if (option == 1) {
			btnAgregar.setVisible(true);
			btnModificar.setVisible(true);
			btnEliminar.setVisible(true);
			btnGenerarCuotas.setVisible(true);
		}
		else {
			btnAgregar.setVisible(false);
			btnModificar.setVisible(false);
			btnEliminar.setVisible(false);
			btnGenerarCuotas.setVisible(false);
		}
	}
	
	private void initPanelNav() {
		panelNav = new JPanel();
		panelNav.setBackground(ConfigGUI.COLOR_FONDO);
		panelNav.setLayout(new FlowLayout(SwingConstants.TRAILING, 15, 5));
		panelNav.setBounds(0, 0, getWidth()-200, 50);
		
		btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isAgregarPago = true;
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelInfo().setVisible(false);
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNav().setVisible(false);
				((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNuevaFormaPago().setVisible(true);
				configButtons(0);
			}
		});
		
		btnModificar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada != -1) {
					isModificarPago = true;
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelInfo().setVisible(false);
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNav().setVisible(false);
					((PanelVenta) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNuevaFormaPago().setVisible(true);
					((PanelNuevaFormaPago)((PanelVenta) FramePpal.getFrameppal().getArrayPaneles()
							.get(FramePpal.PANEL_VENTAS)).getFrameVenta().getPanelNuevaFormaPago())
					.setValoresModificar((FormaPagoVta)modeloTabla.getObjetos().get(filaSeleccionada));
					configButtons(0);
				}
				else {
					JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
				}
			}
		});
		
		btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada != -1) {
					modeloTabla.eliminar(filaSeleccionada);
				}
				else {
					JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
				}
			}
		});
		
		setConfigButtons(btnAgregar, "Agregar", ConfigGUI.COLOR_BTN_PRIMARY);
		setConfigButtons(btnModificar, "Modificar", ConfigGUI.COLOR_BTN_SUCESS);
		setConfigButtons(btnEliminar, "Eliminar", ConfigGUI.COLOR_BTN_DANGER);
		
		panelNav.add(btnAgregar);
		panelNav.add(btnModificar);
		panelNav.add(btnEliminar);
		add(panelNav);
	}
	
	private void setConfigButtons(JButton btn, String title, Color color) {
		btn.setText(title);
		btn.setBackground(color);
		btn.setPreferredSize(new Dimension(100, 40));
		btn.setVerticalAlignment(SwingConstants.CENTER);
		btn.setFont(new Font("Tahoma", Font.BOLD, 12));
	}
	
	private void initPanelTable() {
		panelTable = new JPanel();
		panelTable.setBackground(ConfigGUI.COLOR_BUTTONS);
		panelTable.setBorder(new LineBorder(Color.WHITE));
		panelTable.setBounds(0, panelNav.getHeight(), getWidth(), getHeight()-panelNav.getHeight());
		panelTable.setLayout(null);
		
		initModeloTabla();
		//tabla = new JTable(modeloTabla);
		//tabla.setBounds(0, 0, panelTable.getWidth(), panelTable.getHeight());
		
		
		tabla = new JTable(modeloTabla);
		setConfigTable();
		setConfigScrollPane(this.scrollPane);
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(0, 0, panelTable.getWidth(), panelTable.getHeight());
		scrollPane.setViewportView(tabla);
		
		panelTable.add(scrollPane);
		//panelTable.add(tabla);
		add(panelTable);
	}
	
	private void initModeloTabla() {
		modeloTabla = new ModeloTabla(COLUMNAS) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				FormaPagoVta formaPagoVta = (FormaPagoVta) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					if (formaPagoVta.getFechaVto() == null)
						return "";
					else
						return (new SimpleDateFormat("dd/MM/yyyy").format(formaPagoVta.getFechaVto()));
					
				case 1:
					return formaPagoVta.getDescripcion();
					
				case 2:
					return formaPagoVta.getMontoTotal().toString();
					
				case 3:
					return formaPagoVta.getMoneda();
				}
				
				return null;
			}
		};
		
	}
	
	protected void setConfigTable() {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	protected void setConfigScrollPane(JScrollPane scrollPane) {
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.CYAN, 2));
		scrollPane.setBounds(0, 0, panelTable.getWidth(), panelTable.getHeight());
	}
	
	public void modificarFormaPago(FormaPagoVta formaPagoVta) {
		modeloTabla.modificarObjeto(filaSeleccionada, formaPagoVta);
	}
	
	
	/* Getters & Setters */

	public boolean isAgregarPago() {
		return isAgregarPago;
	}

	public boolean isModificarPago() {
		return isModificarPago;
	}

	public boolean isGenerarCuotas() {
		return isGenerarCuotas;
	}
	
	public void setAgregarPago(boolean isAgregarPago) {
		this.isAgregarPago = isAgregarPago;
	}

	public void setModificarPago(boolean isModificarPago) {
		this.isModificarPago = isModificarPago;
	}

	public void setGenerarCuotas(boolean isGenerarCuotas) {
		this.isGenerarCuotas = isGenerarCuotas;
	}

	public void setAllBotonesFalse() {
		this.isAgregarPago = false;
		this.isEliminarPago = false;
		this.isGenerarCuotas = false;
		this.isModificarPago = false;
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}
	
	
	
}
