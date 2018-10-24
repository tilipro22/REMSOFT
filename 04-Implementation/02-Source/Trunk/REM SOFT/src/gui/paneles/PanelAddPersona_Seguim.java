package gui.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableColumnModel;

import gui.ConfigGUI;
import gui.FrameAddPersona;
import gui.FrameAddSeguimiento;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Persona;
import logica.Seguimiento;

public class PanelAddPersona_Seguim extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private JPanel panelTable;
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private JButton btnAgregar, btnModificar, btnEliminar;
	private int filaSeleccionada = -1;

	@SuppressWarnings("serial")
	public PanelAddPersona_Seguim() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setBounds(0, 0, 450, 450);
		setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(0, 255, 255)));
		setLayout(null);
		
		//BOTONES
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FrameAddPersona.getFrameAddPersona().setEnabled(false);
				FrameAddSeguimiento frameSeg=new FrameAddSeguimiento();
				frameSeg.setVisible(true);
				
			}
		});
		
		btnAgregar.setIcon(new ImageIcon(PanelAddPersona_Seguim.class.getResource("/images/sign-add-64x64.png")));
		btnAgregar.setBounds(75, 10, 80, 90);
		setConfigButton(btnAgregar);
		add(btnAgregar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada >= 0) {
					FrameAddPersona.getFrameAddPersona().setEnabled(false);
					FrameAddSeguimiento frameSeg=new FrameAddSeguimiento();
					
					Seguimiento seg = (Seguimiento) modeloTabla.getObjetos().get(filaSeleccionada);
					frameSeg.completarSeguimiento(seg);
					frameSeg.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Seleccione un registro");		
			}
		});
		btnModificar.setIcon(new ImageIcon(PanelAddPersona_Seguim.class.getResource("/images/sign-change-64x64.png")));
		btnModificar.setBounds(185, 10, 80, 90);
		setConfigButton(btnModificar);
		add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada >= 0) {
					modeloTabla.eliminar(filaSeleccionada);
				}
				else
					JOptionPane.showMessageDialog(null, "Seleccione un registro");
			}
		});
		btnEliminar.setIcon(new ImageIcon(PanelAddPersona_Seguim.class.getResource("/images/sign-ban-64x64.png")));
		btnEliminar.setBounds(295, 10, 80, 90);
		setConfigButton(btnEliminar);
		add(btnEliminar);
		
		//FIN BOTONES
		
		//TABLA
		panelTable=new JPanel();
		panelTable.setBounds(25, 125, 400, 300);
		panelTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane=new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		String[] columnas = {"Fecha", "Detalle", "Estado"};
		modeloTabla = new ModeloTabla(columnas) {
			
			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				
				Seguimiento seg = (Seguimiento) getObjetos().get(fila);
				switch (columna) {
				
				case 0:
					seg.setFecha((Date) valor);
					
				case 1:
					seg.setDetalle((String) valor);
					
				case 2:
					seg.setEstado((Seguimiento.Estado) valor);
					
				}
				
				fireTableCellUpdated(fila, columna);
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				
				Seguimiento seg = (Seguimiento) getObjetos().get(fila);
				switch (columna) {
				
				case 0:
					return seg.getFecha();
					
				case 1:
					return seg.getDetalle();
					
				case 2:
					return seg.getEstado().getDescripcion();
					
				default:
					return seg;
				}
			}
		};
		
		tabla=new JTable();
		setConfigTable(tabla);
		
		scrollPane.setViewportView(tabla);
		panelTable.add(scrollPane);
		
		add(panelTable);
	}
	
	private void setConfigButton(JButton btn) {
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setVerticalAlignment(SwingConstants.TOP);
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn.setFocusable(false);
		btn.setBorderPainted(false);
		btn.setBackground(ConfigGUI.COLOR_FONDO);
	}
	
	private void setConfigTable (JTable tabla) {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
		
		TableColumnModel columnModel = tabla.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(1).setPreferredWidth(165);
		columnModel.getColumn(2).setPreferredWidth(30);
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}
	
	public void setSeguimientoPersona(Persona persona) {
		ArrayList<Seguimiento> seguimientos = new ArrayList<Seguimiento>();
		
		for (int i=0; i < getModeloTabla().getObjetos().size(); i++) {
			Seguimiento seg = (Seguimiento) getModeloTabla().getObjetos().get(i);
			seguimientos.add(seg);
		}
		
		persona.setSeguimientos(seguimientos);
	}
	
	public void updatePanel (ArrayList<Seguimiento> arraySeg) {
		disabledButtons();
		if (arraySeg != null && arraySeg.size() > 0) {
			for (Seguimiento seguimiento : arraySeg) {
				FrameAddPersona.getFrameAddPersona().getPanelSeguimientos().getModeloTabla().agregar(seguimiento);
			}
		}
	}
	
	public void disabledButtons () {
		btnAgregar.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
	
	public void enabledButtons () {
		btnAgregar.setEnabled(false);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
	}
}
