package pl.centrumpvp.cpvpduel.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum KitType {

    HARD("hardcore");

    private final String name;

    private KitType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public static String getKitTypes() {
        List<String> kits = new ArrayList<>();
        for (KitType kit : values()) {
            kits.add(kit.name);
        }
        return StringUtils.join(kits, ", ");
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
