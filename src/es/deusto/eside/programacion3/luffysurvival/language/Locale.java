package es.deusto.eside.programacion3.luffysurvival.language;

import java.util.ResourceBundle;

public enum Locale {
	INSTANCE;
	private  final String MESSAGES = "es.deusto.eside.programacion3.luffysurvival.language.messages";
	
	private ResourceBundle MESSAGE_BUNDLE = ResourceBundle.getBundle(MESSAGES);
	
	public  String getText(final String id) {
		return MESSAGE_BUNDLE.getString(id);
	}

}
