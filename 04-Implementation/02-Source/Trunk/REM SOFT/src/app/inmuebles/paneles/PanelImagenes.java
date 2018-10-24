package app.inmuebles.paneles;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.ButtonDefaultABM;
import gui.ConfigGUI;
import gui.tabla.ModeloTabla;
import gui.tabla.Render;
import logica.Imagen;

public class PanelImagenes extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelHeader;
	JPanel panelDivLeft, panelDivCenter, panelDivRight;

	/*PanelDivCenter*/
	JLabel lblImagePpal, lblImages, lblTitle;
	Imagen imagenPpal = null;
	
	/*PanelDivLeft*/
	JTable tabla = new JTable();
	ModeloTabla modeloTabla;
	JScrollPane scrollPane;
	private int filaSeleccionada = -1;
	
	/*PanelDivRight*/
	ButtonDefaultABM btnAgregar, btnEliminar;
	
	public PanelImagenes() {
		setBackground(ConfigGUI.COLOR_FONDO);
		setLayout(null);
		setBounds(0, 0, 1280, 480);
		setBorder(new MatteBorder(0, 2, 0, 2, Color.cyan));
		
		initPanelHeader();
		initPanelDivLeft();
		initPanelDivCenter();
		initPanelDivRight();
	}
	
	private void initPanelHeader() {
		panelHeader = new JPanel();
		panelHeader.setBackground(ConfigGUI.COLOR_FONDO);
		panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 15));
		panelHeader.setBounds(0, 20, this.getWidth(), this.getHeight()/8);
		panelHeader.setBorder(new LineBorder(Color.white, 1, true));
		
		JLabel lblImgPpal = new JLabel("Imagen Principal");
		lblImgPpal.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblImgPpal.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblImgPpal.setForeground(ConfigGUI.COLOR_SINFOCO);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lblTitle.setText("Imagen Principal");
				scrollPane.setVisible(false);
				lblImagePpal.setVisible(true);
				lblImages.setVisible(false);
			}
			
		});
		lblImgPpal.setHorizontalAlignment(SwingConstants.CENTER);
		lblImgPpal.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblImgPpal.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelHeader.add(lblImgPpal);
		
		JLabel lblImgs = new JLabel("Imagen de Muestra");
		lblImgs.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				lblImgs.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblImgs.setForeground(ConfigGUI.COLOR_SINFOCO);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				lblTitle.setText("Imagenenes de muestra");
				scrollPane.setVisible(true);
				lblImagePpal.setVisible(false);
				lblImages.setVisible(true);
			}
			
		});
		lblImgs.setHorizontalAlignment(SwingConstants.CENTER);
		lblImgs.setForeground(ConfigGUI.COLOR_SINFOCO);
		lblImgs.setFont(new Font("Tahoma", Font.PLAIN, 25));
		panelHeader.add(lblImgs);
		
		this.add(panelHeader);
	}
	
	private void initPanelDivLeft() {
		panelDivLeft = new JPanel();
		panelDivLeft.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivLeft.setLayout(null);
		panelDivLeft.setBorder(new MatteBorder(0, 2, 0, 0, Color.cyan));
		panelDivLeft.setBounds(0, panelHeader.getY()+panelHeader.getHeight(), 425, getHeight()-panelHeader.getHeight()-panelHeader.getY());
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(panelDivLeft.getWidth()/8, panelDivLeft.getHeight()/16,
				panelDivLeft.getWidth()-(panelDivLeft.getWidth()/8)*2, panelDivLeft.getHeight()-(panelDivLeft.getHeight()/16)*2);
		
		inicializarModeloTabla();
		setConfigTable();
		
		eventoTabla();
		
		scrollPane.setViewportView(tabla);
		scrollPane.setVisible(false);
		
		panelDivLeft.add(scrollPane);
		add(panelDivLeft);
	}
	
	private void eventoTabla () {
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada > -1) {
					byte[] bytesB64 = ( (Imagen) modeloTabla.getObjetos().get(filaSeleccionada)).getImage();
					bytesB64 = Base64.getDecoder().decode(bytesB64);
					ImageIcon imageIcon = new ImageIcon(bytesB64);
					Image image = imageIcon.getImage().getScaledInstance(lblImagePpal.getWidth(), lblImagePpal.getHeight(), Image.SCALE_SMOOTH);
					lblImages.setIcon(new ImageIcon(image));
				}
			}
			
		});
	}
	
	private void inicializarModeloTabla() {
		String[] columns = {"Titulo"};
		
		modeloTabla = new ModeloTabla(columns) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setValueAt(Object valor, int fila, int columna) {
				Imagen imagen = (Imagen) getObjetos().get(fila);
				
				imagen.setTitulo((String) valor);
				
			}
			
			@Override
			public Object getValueAt(int fila, int columna) {
				Imagen imagen = (Imagen) getObjetos().get(fila);
				
				return imagen.getTitulo();
			}
		};
	}
	
	private void setConfigTable() {
		tabla.setModel(modeloTabla);
		Render render=new Render();
		tabla.setDefaultRenderer(String.class, render);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setShowHorizontalLines(false);
	}
	
	private void initPanelDivCenter() {
		panelDivCenter = new JPanel();
		panelDivCenter.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivCenter.setBorder(new MatteBorder(0, 3, 0, 0, ConfigGUI.COLOR_FONDO));
		panelDivCenter.setBounds(panelDivLeft.getWidth(), panelHeader.getY()+panelHeader.getHeight(), 430, getHeight()-panelHeader.getHeight()-panelHeader.getY());
		panelDivCenter.setLayout(null);
		
		lblTitle = new JLabel("Imagen Principal");
		lblTitle.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(0, 0, panelDivCenter.getWidth(), 25);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(Color.WHITE);
		
		lblImagePpal = new JLabel("Imagen");
		lblImagePpal.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		lblImagePpal.setBackground(Color.WHITE);
		lblImagePpal.setOpaque(true);
		lblImagePpal.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagePpal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblImagePpal.setBounds(0, 25 , panelDivCenter.getWidth(), panelDivCenter.getHeight()-50);
		lblImagePpal.setVisible(true);
		
		lblImages = new JLabel("Imagen");
		lblImages.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		lblImages.setBackground(Color.WHITE);
		lblImages.setOpaque(true);
		lblImages.setHorizontalAlignment(SwingConstants.CENTER);
		lblImages.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblImages.setBounds(0, 25 , panelDivCenter.getWidth(), panelDivCenter.getHeight()-50);
		lblImages.setVisible(false);
		
		panelDivCenter.add(lblTitle);
		panelDivCenter.add(lblImagePpal);
		panelDivCenter.add(lblImages);
		add(panelDivCenter);
	}
	
	private void initPanelDivRight() {
		panelDivRight = new JPanel();
		panelDivRight.setBackground(ConfigGUI.COLOR_FONDO);
		panelDivRight.setBorder(new MatteBorder(0, 0, 0, 2, Color.cyan));
		panelDivRight.setLayout(new BoxLayout(panelDivRight, BoxLayout.Y_AXIS));
		panelDivRight.setBounds(panelDivLeft.getWidth()+panelDivCenter.getWidth(), panelHeader.getY()+panelHeader.getHeight(), 425, getHeight()-panelHeader.getHeight()-panelHeader.getY());
		
		btnAgregar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_AGREGAR);
		btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				agregarImagen();
			}
		});
		
		btnEliminar = new ButtonDefaultABM(ButtonDefaultABM.BOTON_ELIMINAR);
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminarImagen();
			}
		});
		
		panelDivRight.add(Box.createVerticalStrut(panelDivRight.getHeight()/4));
		panelDivRight.add(btnAgregar);
		panelDivRight.add(Box.createVerticalStrut(50));
		panelDivRight.add(btnEliminar);
		
		add(panelDivRight);
	}
	private void agregarImagen() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filtro=new FileNameExtensionFilter("Imagenes (*.jpg y *.png)", "jpg", "jpeg", "png");
		fileChooser.setFileFilter(filtro);
		
		int option = fileChooser.showOpenDialog(this);
		
		if (option == JFileChooser.APPROVE_OPTION) {
			String file = fileChooser.getSelectedFile().getPath();
			StringBuilder sbFile = new StringBuilder();
			
			for (int i=file.length()-1; i > 0; i--) {
				Character character = file.charAt(i);
				

				if (character != '.')
					sbFile = new StringBuilder(character+sbFile.toString());
				else 
					i = -1;
			}
			
			if (sbFile.toString().equals("jpg") || sbFile.toString().equals("png") || sbFile.toString().equals("jpeg")) {
				ImageIcon imageIcon = new ImageIcon(file);
				Image image = imageIcon.getImage();
				image = image.getScaledInstance(lblImagePpal.getWidth(), lblImagePpal.getHeight(), Image.SCALE_SMOOTH);
				
				imageIcon = new ImageIcon(image);
				
				if (lblImagePpal.isVisible()) {
					lblImagePpal.setText("");
					lblImagePpal.setIcon(imageIcon);
					BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
					try {
						bufferedImage = ImageIO.read(new File(file));
						byte[] imageBytes = bufferedImageToByteArray(bufferedImage, sbFile.toString());
						String b64Img = Base64.getEncoder().encodeToString(imageBytes);
						
						this.imagenPpal = new Imagen(null, file, false, b64Img.getBytes(), "Imagen Principal");
					} catch (IOException e) {e.printStackTrace();}
				}
				else {
					lblImages.setText("");
					lblImages.setIcon(imageIcon);
					BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
					try {
						bufferedImage = ImageIO.read(new File(file));
						byte[] imageBytes = bufferedImageToByteArray(bufferedImage, sbFile.toString());
						String b64Img = Base64.getEncoder().encodeToString(imageBytes);
						
						modeloTabla.agregar(new Imagen(null, file, false, b64Img.getBytes(), "Imagen "+(modeloTabla.getRowCount()+1)));
					} catch (IOException e) {e.printStackTrace();}
				}
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Error: solo se admiten archivos de extension: .jpg, .jpeg y .png", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public byte[] bufferedImageToByteArray(BufferedImage image, String format)
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(image, format, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return baos.toByteArray();
	}
	
	private void eliminarImagen() {
		
		if (lblImagePpal.isVisible() ) {
			if (lblImagePpal.getIcon() != null) {
				lblImagePpal.setText("Imagen");
				lblImagePpal.setIcon(null);
				imagenPpal = null;
			}
			else
				JOptionPane.showMessageDialog(null, "No hay imagen para Eliminar");
		}
		else {
			if (tabla.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "No hay imagen para Eliminar");
			}
			else {
				filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada > -1) {
					String imgTitle = ((Imagen) modeloTabla.getObjetos().get(filaSeleccionada)).getTitulo();
					modeloTabla.eliminar(filaSeleccionada);
					
					lblImages.setIcon(null);
					lblImages.setText(imgTitle+": Eliminada.");
				}
				else
					JOptionPane.showMessageDialog(null, "No selecciono ninguna Imagen");
			}
			
		}
	}
	
	/* Getters & Setters */

	public Imagen getImagenPpal() {
		return imagenPpal;
	}

	public void setImagenPpal(Imagen imagenPpal) {
		this.imagenPpal = imagenPpal;
	}

	public ModeloTabla getModeloTabla() {
		return modeloTabla;
	}

	public JLabel getLblImagePpal() {
		return lblImagePpal;
	}
	
	
	
	

}
