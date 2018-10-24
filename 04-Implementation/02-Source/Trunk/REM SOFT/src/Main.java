
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import gui.FramePpal;
import logger.REMLogger;

public class Main {
	
	private final static Logger LOGGER = REMLogger.getLogger();

	public static void main(String[] args) {
		
		LOGGER.log(Level.INFO, "Init REMSOFT");
		JFrame frame = FramePpal.getFrameppal();
		frame.setVisible(true);
		LOGGER.log(Level.INFO, "Mostrar FramePpal");
	}

}
