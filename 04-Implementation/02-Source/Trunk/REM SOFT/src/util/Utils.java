package util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
	
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

	public static Long diferenciaDias(Date fechaOrigen, Date fechaFinal) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaOrigen);
		
		Integer diaActual = calendar.get(Calendar.DAY_OF_MONTH);
		Integer mesActual = calendar.get(Calendar.MONTH) + 1;
		Integer anioActual = calendar.get(Calendar.YEAR);
		
		calendar.setTime(fechaFinal);
		Integer diaComparar = calendar.get(Calendar.DAY_OF_MONTH);
		Integer mesComparar = calendar.get(Calendar.MONTH) + 1;
		Integer anioComparar = calendar.get(Calendar.YEAR);
		
		/*System.out.println("Fecha Origen:"+diaActual+"/"+mesActual+"/"+anioActual + " > " 
		+ "Fecha Final: " + diaComparar+"/"+mesComparar+"/"+anioComparar);*/

		java.util.GregorianCalendar fechaOrigin = new java.util.GregorianCalendar(anioActual, mesActual, diaActual);
		java.util.GregorianCalendar fechaEnd = new java.util.GregorianCalendar(anioComparar, mesComparar, diaComparar);

		Long difms = fechaOrigin.getTimeInMillis() - fechaEnd.getTimeInMillis();
		Long difd = difms / (1000 * 60 * 60 * 24);

		if (difd < 1) {
			return new Long(0);
		} else {
			return difd;
		}

	}
	
	public static Date sumarDias(Date fecha, Integer diasSumar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, diasSumar);
		
		return calendar.getTime();
	}
	
	public static String printFecha (Date fecha) {
		
		if (fecha == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		
		return calendar.get(Calendar.YEAR) + "-" + String.format("%02d", (calendar.get(Calendar.MONTH)+1)) + "-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		
	}
}
