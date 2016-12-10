package pl.centrumpvp.cpvpduel.enums;

import java.util.Locale;

public enum KitType {
	
    HARD;
    
    private KitType() {
    }

    public static KitType getByName(String name) {
    	try {
    		return valueOf(name.toUpperCase(Locale.ENGLISH));
    	}
    	catch (Exception ex) {
    		return null;
    	}
    }
}
