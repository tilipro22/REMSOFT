package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class REMLogger {
	
	private static final Logger LOGGER = Logger.getLogger("Remsoft");
	private static final String rutaArchivo = "C:/REMSOFT/Remsoft.log";
	private static FileHandler fhArchivo;
	
	static {
		
		try {
			fhArchivo = new FileHandler(rutaArchivo);
			LOGGER.addHandler(fhArchivo);
			
			SimpleFormatter formatter = new SimpleFormatter();
			fhArchivo.setFormatter(formatter);
			
			LOGGER.info("Init REMSOFT Logs");
			
		}
		catch (SecurityException e) {
			
		}
		catch (IOException e) {
			
		}
		
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}

}
