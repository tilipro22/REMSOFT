package gui.tabla;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

public abstract class ModeloTabla extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	
	private String[] columnsName;
	private ArrayList<Object> objetos = new ArrayList<>();
	
	public ModeloTabla(String[] columnas) {
		columnsName = columnas;
	}
	
	public void agregar (Object obj) {
		objetos.add(obj);
		fireTableDataChanged();
	}
	
	public void eliminar (int i) {
		objetos.remove(i);
		fireTableDataChanged();
	}
	
	public void modificarObjeto (int i, Object obj) {
		objetos.set(i, obj);
		fireTableDataChanged();
	}
	
	public void vaciarTabla() {
		objetos.clear();
		fireTableDataChanged();
	}
	
	

	@Override
	public String getColumnName(int column) {
		return columnsName[column];
	}

	@Override
	public int getColumnCount() {
		return columnsName.length;
	}

	@Override
	public int getRowCount() {
		return objetos.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	//ABSTRACTOS
	
	@Override
	public abstract void setValueAt(Object valor, int fila, int columna);

	@Override
	public abstract Object getValueAt(int fila, int columna);
	
	
	// OTRAS FUNCIONALIDADES
	public void setConfigTable(JTable tabla) {
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	
	//GETTERS y SETTERS
	
	public ArrayList<Object> getObjetos() {
		return this.objetos;
	}

}
