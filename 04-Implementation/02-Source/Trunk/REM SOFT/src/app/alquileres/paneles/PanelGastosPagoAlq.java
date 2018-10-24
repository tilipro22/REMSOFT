package app.alquileres.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import app.alquileres.PanelAlquiler;
import app.inmuebles.CtrlInmueble;
import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.FramePpal;
import gui.paneles.PanelConstants;
import gui.tabla.ModeloTabla;
import logica.Alquiler;
import logica.GastoFijo;
import logica.Inmueble;
import logica.PagoAlquiler;
import logica.constant.ValidateConstants;

public class PanelGastosPagoAlq extends PanelConstants {

	private static final long serialVersionUID = 1L;
	
	/* Elements */
	private JPanel panelNavBar, panelTabla;
	private int filaSeleccionada = -1;
	
	/* PanelNavBar */
	private ButtonDefaultABM btnAgregar, btnEliminar;
	
	/* PanelTable */
	private ModeloTabla modeloTabla;
	private JTable tabla;
	private JPanel panelAgregarGasto;
	
	/* PanelAgregarGasto */
	private JLabel lblCloseAgregarGasto;
	private JTextField txtDescripcion, txtMonto;
	
	
	/* Constructor */
	public PanelGastosPagoAlq(Alquiler alquiler, PagoAlquiler pagoAlquiler) {
		setLayout(null);
		setBounds(0, 0, 630, 250);
		
		initPanelTabla();
		initPanelNavBar();
		cargarGastos(alquiler, pagoAlquiler);
		initPanelAgregarGastos();
	}
	
	
	/* Inits */
	
	private void initPanelNavBar () {
		panelNavBar = new JPanel();
		panelNavBar.setBackground(ConfigGUI.COLOR_FONDO_ALT);
		panelNavBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelNavBar.setBorder(new LineBorder(Color.white));
		panelNavBar.setBounds(0, panelTabla.getHeight()+panelTabla.getY(), getWidth(), 60);
		
		btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAgregar();
			}
		});
		btnAgregar.setText("Agregar");
		btnAgregar.setBackground(ConfigGUI.COLOR_BTN_PRIMARY);
		
		btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoEliminar();
			}
		});
		btnEliminar.setText("Eliminar");
		btnEliminar.setBackground(ConfigGUI.COLOR_BTN_WARNING);
		
		panelNavBar.add(btnAgregar);
		panelNavBar.add(btnEliminar);
		
		add(panelNavBar);
	}
	
	private void initPanelTabla() {
		panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout());
		panelTabla.setBounds(0, 0, getWidth(), 190);
		
		JScrollPane scrollPane = new JScrollPane();
		String[] columns = {"Descripcion", "Monto"};
		
		modeloTabla = new ModeloTabla(columns) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				
				GastoFijo gasto = (GastoFijo) getObjetos().get(fila);
				
				switch (columna) {
				case 0:
					return gasto.getGasto();
					
				case 1:
					DecimalFormat decimalFormat = new DecimalFormat("#.00");
					
					if (gasto.getGasto().equals("Monto Pagado")) {
						return "-"+decimalFormat.format(gasto.getMonto());
					}
					
					if (gasto.getMonto().compareTo(new BigDecimal("00.00")) > 0)
						return decimalFormat.format(gasto.getMonto());
					else
						return "00.00";
					
				default:
					return gasto;
					
				}
				
			}
		};
		
		tabla = new JTable(modeloTabla);
		setConfigTable(tabla, modeloTabla);
		setConfigScrollPane(scrollPane);
		scrollPane.setViewportView(tabla);
		
		panelTabla.add(scrollPane, BorderLayout.CENTER);
		add(panelTabla);
	}
	
	private void initPanelAgregarGastos() {
		panelAgregarGasto = new JPanel();
		panelAgregarGasto.setBackground(ConfigGUI.COLOR_FONDO);
		panelAgregarGasto.setLayout(null);
		panelAgregarGasto.setBounds(0, 0, getWidth(), 190);
		
		JPanel panelBarFrame, panelCentral;
		
	// Panel Barra Frame
		panelBarFrame = new JPanel();
		panelBarFrame.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelBarFrame.setBackground(ConfigGUI.COLOR_FONDO);
		panelBarFrame.setBounds(0, 10, panelTabla.getWidth(), 30);
		
		setLabelDefault(lblCloseAgregarGasto = new JLabel("X"));
		lblCloseAgregarGasto.setBorder(new LineBorder(Color.white));
		lblCloseAgregarGasto.setPreferredSize(new Dimension(30, 30));
		lblCloseAgregarGasto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCloseAgregarGasto.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblCloseAgregarGasto.setOpaque(true);
				lblCloseAgregarGasto.setBackground(ConfigGUI.COLOR_ERROR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCloseAgregarGasto.setOpaque(false);
				lblCloseAgregarGasto.setBackground(ConfigGUI.COLOR_FONDO);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				eventoCerrarAddGasto();
			}
			
			
			
		});
		
		panelBarFrame.add(lblCloseAgregarGasto);
		
	// Panel Central
		JLabel lblDescripcion, lblMonto, lblInvisible;
		
		panelCentral = new JPanel();
		panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
		panelCentral.setBackground(ConfigGUI.COLOR_FONDO);
		panelCentral.setBounds(0, panelBarFrame.getHeight()+panelBarFrame.getY(), 
				panelTabla.getWidth(), 190-panelBarFrame.getHeight()+panelBarFrame.getY());
		
		setLabelDefault(lblDescripcion = new JLabel("Descripcion: "));
		lblDescripcion.setPreferredSize(new Dimension(200, 30));
		setTextfieldDefault(txtDescripcion = new JTextField());
		txtDescripcion.setPreferredSize(new Dimension(350, 30));
		
		setLabelDefault(lblMonto = new JLabel("Monto $: "));
		setTextfieldDefault(txtMonto = new JTextField());
		lblMonto.setPreferredSize(new Dimension(200, 30));
		txtMonto.setPreferredSize(new Dimension(150, 30));
		setLabelDefault(lblInvisible = new JLabel(""));
		lblInvisible.setPreferredSize(new Dimension(100, 30));
		
		ButtonDefaultABM btnOK = new ButtonDefaultABM(ButtonDefaultABM.BOTON_DEFAULT);
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoOkAddGasto();
				((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFramePagoAlquiler().cargarTotales();
			}
		});
		btnOK.setText("OK");
		btnOK.setBackground(ConfigGUI.COLOR_BTN_INFO);
		
		panelCentral.add(lblDescripcion);
		panelCentral.add(txtDescripcion);
		panelCentral.add(lblMonto);
		panelCentral.add(txtMonto);
		panelCentral.add(lblInvisible);
		panelCentral.add(btnOK);
		
	// Adds
		panelAgregarGasto.setVisible(false);
		panelAgregarGasto.add(panelBarFrame);
		panelAgregarGasto.add(panelCentral);

		add(panelAgregarGasto);
	}
	
	
	/* Functions */
	
	private void cargarGastos(Alquiler alquiler, PagoAlquiler pagoAlquiler) {
		
		if (alquiler != null) {
			Inmueble inmueble = alquiler.getInmueble();
			modeloTabla.agregar(new GastoFijo(inmueble.getIdInmueble(), null, "Precio Alquiler", pagoAlquiler.getMontoTotal(), null));
			
			if (alquiler.getMesesDeposito() != 0) {
				modeloTabla.agregar(new GastoFijo(inmueble.getIdInmueble(), null, "Deposito", alquiler.getPrecioDeposito(), null));
			}
			
			if (pagoAlquiler.getMontoPagado().compareTo(new BigDecimal(00.00)) > 0) {
				modeloTabla.agregar(new GastoFijo(inmueble.getIdInmueble(), null, "Monto Pagado", pagoAlquiler.getMontoPagado(), null));
			}
		}
		
		CtrlInmueble ctrlInmueble = new CtrlInmueble();
		List<GastoFijo> listGastoFijo = ctrlInmueble.getListGastoFijoByIdInmueble(alquiler.getInmueble().getIdInmueble());
		
		if (listGastoFijo.size() != 0) {
			for (int i=0; i < listGastoFijo.size(); i++) {
				Integer mesCorrespondiente = pagoAlquiler.getMesCorrespondiente();

				if (listGastoFijo.get(i).getMesesAplica().get(mesCorrespondiente-1) == 1) {
					modeloTabla.agregar(listGastoFijo.get(i));
				}
				
			}
		}
	}

	private boolean isDatosAddGastoValidos() {
		boolean isValido = true;
		
		if (ValidateConstants.isTextEmpty(txtDescripcion.getText())) {
			isValido = false;
			txtDescripcion.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else if (txtDescripcion.getText().length() > 35) {
			JOptionPane.showMessageDialog(null, "Descripcion demasiada extensa (Permitido: 35 caracteres)");
			isValido = false;
			txtDescripcion.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		else if (txtDescripcion.getText().toUpperCase().equals("MONTO PAGADO")) {
			JOptionPane.showMessageDialog(null, "Error. Operacion Invalida.");
			isValido = false;
			txtDescripcion.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		if (! ValidateConstants.validateMoney(txtMonto.getText())) {
			isValido = false;
			txtMonto.setBorder(new LineBorder(ConfigGUI.COLOR_ERROR));
		}
		
		return isValido;
	}
	
	/* Configs */
	
	@Override
	protected void setConfigScrollPane(JScrollPane scrollPane) {
		super.setConfigScrollPane(scrollPane);
		scrollPane.setBounds(0, 0, panelTabla.getWidth(), panelTabla.getHeight());
	}
	
	
	
	/* Events */
	private void eventoAgregar() {
		btnAgregar.setVisible(false);
		btnEliminar.setVisible(false);
		panelTabla.setVisible(false);
		panelAgregarGasto.setVisible(true);
	}
	
	private void eventoEliminar() {
		filaSeleccionada = tabla.getSelectedRow();
		
		if (filaSeleccionada != -1) {
			
			if (filaSeleccionada == 0) {
				JOptionPane.showMessageDialog(null, "No se puede eliminar el Precio del Alquiler");
				return;
			}
			else if (filaSeleccionada == 1) {
				GastoFijo gastoFijo = (GastoFijo) modeloTabla.getObjetos().get(filaSeleccionada);
				if (gastoFijo.getGasto().toUpperCase().equals("DEPOSITO")) {
					JOptionPane.showMessageDialog(null, "No se puede eliminar el Deposito");
					return;
				}
			}
			
			if ( ((GastoFijo)modeloTabla.getObjetos().get(filaSeleccionada)).getGasto().equals("Monto Pagado") ) {
				JOptionPane.showMessageDialog(null, "No se puede eliminar el Monto Pagado");
			}
			else {
				modeloTabla.eliminar(filaSeleccionada);
				((PanelAlquiler) FramePpal.getFrameppal().getArrayPaneles().get(FramePpal.PANEL_ALQUILERES)).getFramePagoAlquiler().cargarTotales();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No selecciono ninguna fila");
		}
	}
	
	private void eventoCerrarAddGasto() {
		txtDescripcion.setText("");
		txtMonto.setText("");
		panelAgregarGasto.setVisible(false);
		panelTabla.setVisible(true);
		btnAgregar.setVisible(true);
		btnEliminar.setVisible(true);
	}
	
	private void eventoOkAddGasto() {
		if (isDatosAddGastoValidos()) {
			BigDecimal monto = new BigDecimal(txtMonto.getText());
			GastoFijo gastoFijo = new GastoFijo(null, null, txtDescripcion.getText(), monto, false);
			modeloTabla.agregar(gastoFijo);
			eventoCerrarAddGasto();
		}
	}
	
	
	/* Getters & Setters */
	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}
	
	
	
}
