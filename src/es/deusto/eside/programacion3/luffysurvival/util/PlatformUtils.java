package es.deusto.eside.programacion3.luffysurvival.util;
/**
 * Indica el SO
 * @author sergio
 *
 */
public class PlatformUtils {
	
	static public boolean isMac() {
		return (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0);
		
	}

}
