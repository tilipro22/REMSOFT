package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JDateChooser;

import logica.Seguimiento;


public class FrameAddSeguimiento extends FrameDefault {

	private static final long serialVersionUID = 1L;
	private static final Color COLOR_FONDO=new Color(40, 120, 200);
	
	private JDateChooser dateChooser;
	private JComboBox<Object> cbxEstado;
	private JTextArea txtAreaDetalle;
	private boolean modificar = false;

	public FrameAddSeguimiento() {
		setTituloBarra("   Seguimiento");
		setColorBackground(COLOR_FONDO);
		setColorBordes(Color.WHITE);
		setSizeFrame(super.getWidth(), 400);
		super.setLocationRelativeTo(null);
		
		JPanel panelFrontal = new JPanel();
		panelFrontal.setBorder(new MatteBorder(0, 2, 0, 2, (Color) new Color(255, 255, 255)));
		panelFrontal.setBackground(COLOR_FONDO);
		panelFrontal.setBounds(0, 30, super.getWidth(), 150);
		panelFrontal.setLayout(null);
		
		JLabel lblAgregarSeg=new JLabel("Agregar Seguimiento");
		lblAgregarSeg.setBorder(new LineBorder(Color.black));
		lblAgregarSeg.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgregarSeg.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAgregarSeg.setForeground(Color.WHITE);
		lblAgregarSeg.setBounds(110, 10, 230, 30);
		panelFrontal.add(lblAgregarSeg);
		
		JLabel lblFecha=new JLabel("Fecha: ");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFecha.setForeground(Color.BLACK);
		lblFecha.setBounds(90, 50, 75, 30);
		panelFrontal.add(lblFecha);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(170, 50, 155, 30);
		panelFrontal.add(dateChooser);
		
		JLabel lblEstado=new JLabel("Estado: ");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEstado.setForeground(Color.BLACK);
		lblEstado.setBounds(90, 100, 75, 30);
		panelFrontal.add(lblEstado);
		
		cbxEstado = new JComboBox<>(new Object[]{});
		cbxEstado.setModel(new DefaultComboBoxModel<>(new String[] {"Activo", "Finalizado", "Archivado" }));
		cbxEstado.setFont(new Font("Calibri", Font.PLAIN, 14));
		cbxEstado.setForeground(Color.BLACK);
		cbxEstado.setFocusable(false);
		cbxEstado.setBounds(170, 100, 155, 30);
		panelFrontal.add(cbxEstado);
		
		getContentPane().add(panelFrontal);
		
		
		//Panel Central
		JPanel panelCentral=new JPanel();
		panelCentral.setBounds(0, 200, super.getWidth(), 170);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		//CENTER
		JScrollPane scrollPaneDet=new JScrollPane();
		scrollPaneDet.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneDet.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		txtAreaDetalle = new JTextArea();
		txtAreaDetalle.setWrapStyleWord(true);
		txtAreaDetalle.setLineWrap(true);
		scrollPaneDet.setViewportView(txtAreaDetalle);
		
		JLabel lblDetalle=new JLabel("Detalle");
		lblDetalle.setForeground(Color.BLACK);
		lblDetalle.setBackground(Color.WHITE);
		lblDetalle.setBorder(new LineBorder(Color.BLACK));
		lblDetalle.setOpaque(true);
		lblDetalle.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPaneDet.setColumnHeaderView(lblDetalle);
		
		panelCentral.add(scrollPaneDet, BorderLayout.CENTER);
		
		//EAST y WEST
		JLabel lblRellenoWest = crearLabelRelleno();
		panelCentral.add(lblRellenoWest, BorderLayout.WEST);
		
		JLabel lblRellenoEast = crearLabelRelleno();
		panelCentral.add(lblRellenoEast, BorderLayout.EAST);
		
		//SOUTH
		JPanel panelSouth=new JPanel();
		panelSouth.setBackground(COLOR_FONDO);
		panelSouth.setBorder(new MatteBorder(0, 2, 0, 2, Color.WHITE));
		
		JButton btnCancelar=new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoCancelar();
			}
		});
		panelSouth.add(btnCancelar);
		
		JButton btnAceptar=new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventoAceptar();
			}
		});
		panelSouth.add(btnAceptar);
		
		panelCentral.add(panelSouth, BorderLayout.SOUTH);
		
		getContentPane().add(panelCentral);
	}


	@Override
	protected void eventoCerrar() {
		FrameAddPersona.getFrameAddPersona().setVisible(true);
		FramePpal.getFrameppal().setVisible(true);
		FrameAddPersona.getFrameAddPersona().setEnabled(true);
		FrameAddPersona.getFrameAddPersona().setVisible(true);
	}
	
	private void eventoCancelar() {
		dispose();
		FrameAddPersona.getFrameAddPersona().setEnabled(true);
		FramePpal.getFrameppal().setVisible(true);
		FrameAddPersona.getFrameAddPersona().setVisible(true);
	}
	
	private void eventoAceptar() {
		Calendar cal = Calendar.getInstance();
		
		
		String stringDate = ((JTextField) dateChooser.getDateEditor().getUiComponent() ).getText();
		Color color = ((JTextField) dateChooser.getDateEditor().getUiComponent() ).getForeground();
		
		if (color.equals(Color.red) || color.equals(Color.RED) || stringDate.equals("") 
				|| txtAreaDetalle.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Fecha invalida: ["+stringDate+"] o "
					+ "Detalle: vacio", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			cal.setTime(dateChooser.getDate());
			if (txtAreaDetalle.getText().length() > 250) 
				JOptionPane.showMessageDialog(null, "Detalle: no se permite mas de 250 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				@SuppressWarnings("deprecation")
				Date fecha = new Date(cal.get(Calendar.YEAR)-1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
				//Date fecha = new Date(dateChooser.getDate().getYear(), dateChooser.getDate().getMonth(), dateChooser.getDate().getDay());
				Seguimiento seg = new Seguimiento(fecha, txtAreaDetalle.getText(), cbxEstado.getSelectedIndex()+1);
				if (modificar) {
					int fila = FrameAddPersona.getFrameAddPersona().getPanelSeguimientos().getFilaSeleccionada();
					FrameAddPersona.getFrameAddPersona().getPanelSeguimientos().getModeloTabla().modificarObjeto(fila, seg);
				}
				else
					FrameAddPersona.getFrameAddPersona().getPanelSeguimientos().getModeloTabla().agregar(seg);
				
				dispose();
				FrameAddPersona.getFrameAddPersona().setEnabled(true);
				FramePpal.getFrameppal().setVisible(true);
				FrameAddPersona.getFrameAddPersona().setVisible(true);
				FrameAddPersona.getFrameAddPersona().repaint();
			}
		}
	}
	
	private JLabel crearLabelRelleno () {
		JLabel lbl=new JLabel("1234");
		lbl.setBackground(COLOR_FONDO);
		lbl.setForeground(COLOR_FONDO);
		lbl.setOpaque(true);
		lbl.setBorder(new MatteBorder(0, 2, 0, 2, Color.WHITE));
		
		return lbl;
	}
	
	
	
	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}


	public void completarSeguimiento (Seguimiento seg) {
		String fecha = seg.getFecha().toString();
		String day = fecha.charAt(8)+""+fecha.charAt(9);
		String month = fecha.charAt(5)+""+fecha.charAt(6);
		String year = fecha.charAt(0)+""+fecha.charAt(1)+""+fecha.charAt(2)+""+fecha.charAt(3);
		String newFecha = day+"/"+month+"/"+year;
		
		((JTextField) dateChooser.getDateEditor().getUiComponent() ).setText(newFecha);
		txtAreaDetalle.setText(seg.getDetalle());
		cbxEstado.setSelectedIndex(seg.getEstado().getNumero()-1);
		setModificar(true);
	}
}
