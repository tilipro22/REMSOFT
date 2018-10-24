package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public interface ConfigGUI {
	static final Color COLOR_FONDO=new Color(38, 38, 38);
	static final Color COLOR_FONDO_ALT=new Color(0, 36, 71);
	static final Color COLOR_BUTTONS=new Color(78, 78, 78);
	static final Color COLOR_SINFOCO=new Color(192, 192, 192);
	static final Color COLOR_LABEL=new Color(195, 195, 195);
	static final Color COLOR_ERROR = new Color(255,91,91);
	static final Color COLOR_BTN_PRIMARY = new Color(40, 96, 144);
	static final Color COLOR_BTN_DANGER = new Color(212, 63, 58);
	static final Color COLOR_BTN_SUCESS = new Color(92, 184, 92);
	static final Color COLOR_BTN_INFO = new Color(91, 192, 222);
	static final Color COLOR_BTN_WARNING = new Color(240, 173, 78);
	
	static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	//static final Font FONT_PANEL_FRONTAL = new Font("Calibri", Font.PLAIN, 30);
	

	
}
