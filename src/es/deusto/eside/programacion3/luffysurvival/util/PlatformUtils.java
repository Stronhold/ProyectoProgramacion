package es.deusto.eside.programacion3.luffysurvival.util;

public class PlatformUtils {
	
	static public boolean isMac() {
		return (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0);

	}

}
