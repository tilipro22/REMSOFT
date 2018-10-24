package logica.constant;

import java.awt.Color;

import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class ValidateConstants {
	
	public static boolean isTextEmpty(String text) {
		for (int i = 0; i < text.length(); i++) {
			Character character = text.charAt(i);
			
			if (! Character.isWhitespace(character))
				return false;
		}
		
		return true;
	}
	
	
	/* 
	 * ************* *
	 * Validar Plata *
	 * ************* *
	 * */
	
	public static boolean validateMoney(String money) {
		
		if (isTextEmpty(money))
			return false;
		
		StringBuilder sbMoney = new StringBuilder();
		
		for (int i = 0; i < money.length(); i++) {
			char c = money.charAt(i);
			
			if (Character.isDigit(c) || c == '.' || c == ',')
				sbMoney.append(c);
			else
				return false;
		}
		
		
		return regexMoney(sbMoney.toString());
	}
	
	private static boolean regexMoney (String money) {
		final String regExp = "[0-9]+([,.][0-9]{1,2})?";
		
		if (money.matches(regExp)) {
			if (money.split("\\.")[0].length() > 12)
				return false;
			else 
				return true;
		}
			
		
		return false;
	}
	
	
	/* 
	 * ****************** *
	 * Validar Porcentaje *
	 * ****************** *
	 * */
	
	public static boolean validatePercentage (String percentage) {
		
		if (isTextEmpty(percentage))
			return false;
		
		StringBuilder sbPercentage = new StringBuilder();
		
		for (int i = 0; i < percentage.length(); i++) {
			char c = percentage.charAt(i);
			
			if (Character.isDigit(c) || c == '.' || c == ',')
				sbPercentage.append(c);
			else
				return false;
		}
		
		
		return regexPercentage(sbPercentage.toString());
	}
	
	private static boolean regexPercentage (String percentage) {
		final String regExp = "[0-9]+([,.][0-9]{1,2})?";
		
		if (percentage.matches(regExp)) {
			if (percentage.split("\\.")[0].length() > 2)
				return false;
			else 
				return true;
		}
			
		
		return false;
	}
	
	
	/* 
	 * *************** *
	 * Validar Texto *
	 * *************** *
	 * */
	
	public static boolean validateText (String text) {
		
		if (text.length() == 0)
			return false;
		
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (!Character.isLetter(ch) && !Character.isWhitespace(ch)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean validateTextAndLength (String text, int length) {
		
		if (text.length() == 0)
			return false;
		
		if (text.length() > length)
			return false;
		
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (!Character.isLetter(ch) && !Character.isWhitespace(ch))
				return false;
		}
		
		return true;
	}
	
	
	/* 
	 * ************** *
	 * Validar Numero *
	 * ************** *
	 * */
	public static boolean validateNumber (String number) {
		
		if (number.length() == 0)
			return false;
		
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean validateNumber (String number, int length) {
		
		if (number.length() == 0)
			return false;
		
		if (number.length() > length)
			return false;
		
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	/* 
	 * *************************** *
	 * Validar Fecha (DateChooser) *
	 * *************************** *
	 * */

	public static boolean validateDateOfDateChooser (JDateChooser dateChooser) {
		
		String sDate = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
		Color color = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getForeground();
		
		if (sDate.equals("") || color.equals(Color.red)) {
			return false;
		}
		
		return true;
	}
	
}
