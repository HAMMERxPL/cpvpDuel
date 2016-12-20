package pl.centrumpvp.cpvpduel.enums;

public enum KitType {
	
    HARD("HARD");
    
	private final String name;
	
    private KitType(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public static KitType getKitType(String name) {
    	for (KitType kit : KitType.values()) {
    		if (kit.getName().equalsIgnoreCase(name)) {
    			return kit;
    		}
    	}
    	return null;
    }
}
