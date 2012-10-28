package es.deusto.eside.programacion3.luffysurvival.language;

import java.util.ResourceBundle;

public class Locale {
	
	private static final String MESSAGES = "es.deusto.eside.programacion3.luffysurvival.language.messages";
	public static String getText(final String id) {
		return ResourceBundle.getBundle(MESSAGES).getString(id);
	}

}
