package logica.constant;

public interface ConstantSQL_Persona {
	
	/*
	    ******************
	    ***** SEARCH *****
	    ******************
	*/

	// Busca una persona segun su id
	
	static final String SQL_SEARCH_PERSONA_WID = "SELECT nombre, apellido, dni, domicilio, ciudad, provincia, nacionalidad, "
			+ "tipo_persona, profesion, sexo, observaciones FROM Persona WHERE idPersona=";
	
	// Buscar Contacto de Persona por idContacto
	static final String SQL_SEARCH_CONTACTO_PERSONA_WID = "SELECT telefono, celular, mail FROM Contacto WHERE idPersona = ";
	
	// Buscar contacto de Persona por idPersona
	//static final String SQL_SEARCH_CONTACTO_PERSONA_WID = "SELECT telefono, celular, mail FROM Contacto WHERE idContacto = ";

}
