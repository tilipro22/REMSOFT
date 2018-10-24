package logica;

public class ConfigProgram {
	
	private static final ConfigProgram CONFIG_PROGRAM = new ConfigProgram();
	private String flag;
	
	private ConfigProgram() {
		flag = "Panel.Inicio";
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static ConfigProgram getConfigProgram() {
		return CONFIG_PROGRAM;
	}

	
}
