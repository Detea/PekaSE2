package deta.pk.profile;

import java.util.HashMap;
import java.util.Map;

public final class SpriteProfile {
    private Map<Integer, String> aiPatternMap = new HashMap<>();
    private Map<Integer, String> damageMap = new HashMap<>();
    private Map<Integer, String> typeMap = new HashMap<>();
    private Map<Integer, String> destructionEffects = new HashMap<>();
    
    private Map<Integer, String> colorMap = new HashMap<>();
    
    private Map<Integer, String> destructionType = new HashMap<>();
    
    private Map<Integer, String> immunityMap = new HashMap<>();
    
    private int animationsAmount = 10;
    
    public SpriteProfile() {
    }
    
    public Map<Integer, String> getAiPatternMap() {
        return aiPatternMap;
    }
    
    public Map<Integer, String> getDamageMap() {
        return damageMap;
    }
    
    public Map<Integer, String> getTypeMap() {
        return typeMap;
    }
    
    public Map<Integer, String> getDestructionEffects() {
        return destructionEffects;
    }
    
    public Map<Integer, String> getColorMap() {
        return colorMap;
    }
    
    public Map<Integer, String> getDestructionType() {
        return destructionType;
    }
    
    public Map<Integer, String> getImmunityMap() {
        return immunityMap;
    }
    
    public int getAnimationsAmount() { return animationsAmount; }
}
