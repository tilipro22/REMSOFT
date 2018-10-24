package gui.paneles;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public interface RecuadroPanelInicio {
	static final MatteBorder BORDE_TITULO = new MatteBorder(0, 0, 1, 0, Color.WHITE);
	static final Font FONT_TITULO = new Font("Tahoma", Font.PLAIN, 20);
	static final Font FONT_SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
	static final LineBorder BORDE_SUBTITULO = new LineBorder(Color.WHITE);
	static final int ICON_SIZE = 128;
	
	static final int OP_PERSONAS = 1;
	static final int OP_INMUEBLES = 2;
	static final int OP_ALQUILERES = 3;
	static final int OP_VENTAS = 4;
	static final int OP_SOLICITUDES = 5;
	static final int OP_RESERVAS = 6;
	static final int OP_AGENDA = 7;
	
}
