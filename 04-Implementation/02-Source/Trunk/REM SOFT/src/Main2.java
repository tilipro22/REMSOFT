import java.util.ArrayList;
import java.util.Date;

import control.CtrlAddPersona;
import logica.Persona;
import util.Utils;

public class Main2 {

	public static void main(String[] args) {
		System.out.println(new Date(2017-1900, 11-1, 9));
		if (Utils.diferenciaDias(new Date(), new Date(2017-1900, 10-1, 7)).intValue() > 0) {
			System.out.println("Fecha actual es > al Vto");
		}
		else {
			System.out.println("Fecha actual es <= al Vto");
		}

	}

}
