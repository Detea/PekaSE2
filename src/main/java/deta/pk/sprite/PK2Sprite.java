package deta.pk.sprite;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PK2Sprite {
    public static final byte[] VERSION_13 = {0x31, 0x2E, 0x33, 0x00};
    
    private final ChangeEvent changeEvent = new ChangeEvent(this);
    
    private List<ChangeListener> changeListeners = new ArrayList<>();
    
    protected String filename = "";
    
    private int type;
    
    protected String imageFile = "";
    
    protected String[] soundFiles = new String[7];
    protected List<String> soundFilesList = new ArrayList<>();
    
    protected List<PK2SpriteAnimation> animationsList = new ArrayList<>();
    
    private List<BufferedImage> framesList = new ArrayList<>();
    
    protected int framesAmount;
    
    protected int animationsAmount; // not used
    protected int frameRate;
    
    protected int frameX;
    protected int frameY;
    
    protected int frameWidth;
    protected int frameHeight;
    protected int frameDistance;
    
    protected String name;
    protected int width;
    protected int height;
    
    protected String transformationSpriteFile = "";
    protected String bonusSpriteFile = "";
    
    protected double weight;
    
    protected boolean enemy;
    protected int energy;
    protected int damage;
    private int immunityToDamageType;
    protected int damageType;
    protected int score;
    
    protected int attack1Duration;
    protected int attack2Duration;
    
    protected String attack1SpriteFile = "";
    protected String attack2SpriteFile = "";
    
    protected int attackPause;
    
    protected int[] aiList = new int[10];
    
    protected int maxJump;
    protected double maxSpeed;
    
    protected int color; // index to a color in the color palette
    
    protected boolean obstacle;
    protected boolean boss;
    protected boolean tileCheck;
    
    protected boolean wallUp;
    protected boolean wallDown;
    protected boolean wallLeft;
    protected boolean wallRight;
    
    protected int destruction; // effect?
    
    protected boolean key;
    protected boolean shakes;
    
    protected int parallaxFactor;
    
    private boolean isPlayerSprite;
    
    public int getLoadTime() {
        return loadTime;
    }
    
    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }
    
    private int loadTime;
    
    public int getSoundFrequency() {
        return soundFrequency;
    }
    
    public void setSoundFrequency(int soundFrequency) {
        this.soundFrequency = soundFrequency;
    }
    
    private int soundFrequency;
    
    public int getBonusAmount() {
        return bonusAmount;
    }
    
    public void setBonusAmount(int bonusAmount) {
        this.bonusAmount = bonusAmount;
    }
    
    private int bonusAmount;
    
    protected boolean randomSoundFrequency;
    protected boolean glide;        // Sprite can glide, like Pekka
    protected boolean alwaysBonus; // Always drop bonus
    
    protected boolean swim;
    
    protected BufferedImage image;
    
    public PK2Sprite() {
        for (int i = 0; i < 7; i++) {
            soundFilesList.add("");
        }
        
        for (int i = 0; i < 20; i++) {
            animationsList.add(new PK2SpriteAnimation(new byte[10], 0, false));
        }
    }
    
    public void setImage(BufferedImage img) {
        this.image = img;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    protected List<BufferedImage> frameImages = new ArrayList<>();
    
    public void setPlayerSprite(boolean is) {
        isPlayerSprite = is;
        
        fireChanges();
    }
    
    public boolean isPlayerSprite() {
        return isPlayerSprite;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
        
        fireChanges();
    }
    
    public String getImageFile() {
        return imageFile;
    }
    
    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
        
        fireChanges();
    }
    
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
        
        fireChanges();
    }
    
    public int getAnimationsAmount() {
        return animationsAmount;
    }
    
    public void setAnimationsAmount(int animationsAmount) {
        this.animationsAmount = animationsAmount;
        
        fireChanges();
    }
    
    public int getFrameRate() {
        return frameRate;
    }
    
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
        
        fireChanges();
    }
    
    public int getFrameX() {
        return frameX;
    }
    
    public void setFrameX(int frameX) {
        this.frameX = frameX;
        
        fireChanges();
    }
    
    public int getFrameY() {
        return frameY;
    }
    
    public void setFrameY(int frameY) {
        this.frameY = frameY;
        
        fireChanges();
    }
    
    public int getFrameWidth() {
        return frameWidth;
    }
    
    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
        
        fireChanges();
    }
    
    public int getFrameHeight() {
        return frameHeight;
    }
    
    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
        
        fireChanges();
    }
    
    public int getFrameDistance() {
        return frameDistance;
    }
    
    public void setFrameDistance(int frameDistance) {
        this.frameDistance = frameDistance;
        
        fireChanges();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        
        fireChanges();
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
        
        fireChanges();
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
        
        fireChanges();
    }
    
    public String getTransformationSpriteFile() {
        return transformationSpriteFile;
    }
    
    public void setTransformationSpriteFile(String transformationSpriteFile) {
        this.transformationSpriteFile = transformationSpriteFile;
        
        fireChanges();
    }
    
    public String getBonusSpriteFile() {
        return bonusSpriteFile;
    }
    
    public void setBonusSpriteFile(String bonusSpriteFile) {
        this.bonusSpriteFile = bonusSpriteFile;
        
        fireChanges();
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
        
        fireChanges();
    }
    
    public boolean isEnemy() {
        return enemy;
    }
    
    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
        
        fireChanges();
    }
    
    public int getEnergy() {
        return energy;
    }
    
    public void setEnergy(int energy) {
        this.energy = energy;
        
        fireChanges();
    }
    
    public int getDamage() {
        return damage;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
        
        fireChanges();
    }
    
    public int getDamageType() {
        return damageType;
    }
    
    public void setDamageType(int damageType) {
        this.damageType = damageType;
        
        fireChanges();
    }
    
    public int getImmunityToDamageType() {
        return immunityToDamageType;
    }
    
    public void setImmunityToDamageType(int immunityToDamageType) {
        this.immunityToDamageType = immunityToDamageType;
        
        fireChanges();
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
        
        fireChanges();
    }
    
    public int getAttack1Duration() {
        return attack1Duration;
    }
    
    public void setAttack1Duration(int attack1Duration) {
        this.attack1Duration = attack1Duration;
        
        fireChanges();
    }
    
    public int getAttack2Duration() {
        return attack2Duration;
    }
    
    public void setAttack2Duration(int attack2Duration) {
        this.attack2Duration = attack2Duration;
        
        fireChanges();
    }
    
    public String getAttack1SpriteFile() {
        return attack1SpriteFile;
    }
    
    public void setAttack1SpriteFile(String attack1SpriteFile) {
        this.attack1SpriteFile = attack1SpriteFile;
        
        fireChanges();
    }
    
    public String getAttack2SpriteFile() {
        return attack2SpriteFile;
    }
    
    public void setAttack2SpriteFile(String attack2SpriteFile) {
        this.attack2SpriteFile = attack2SpriteFile;
        
        fireChanges();
    }
    
    public int getAttackPause() {
        return attackPause;
    }
    
    public void setAttackPause(int attackPause) {
        this.attackPause = attackPause;
        
        fireChanges();
    }
    
    public int[] getAiList() {
        return aiList;
    }
    
    public void setAiList(int[] aiList) {
        this.aiList = aiList;
        
        fireChanges();
    }
    
    public int getMaxJump() {
        return maxJump;
    }
    
    public void setMaxJump(int maxJump) {
        this.maxJump = maxJump;
        
        fireChanges();
    }
    
    public double getMaxSpeed() {
        return maxSpeed;
    }
    
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
        
        fireChanges();
    }
    
    public int getColor() {
        return color;
    }
    
    public void setColor(int color) {
        this.color = color;
        
        fireChanges();
    }
    
    public boolean isObstacle() {
        return obstacle;
    }
    
    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
        
        fireChanges();
    }
    
    public boolean isBoss() {
        return boss;
    }
    
    public void setBoss(boolean boss) {
        this.boss = boss;
        
        fireChanges();
    }
    
    public boolean isTileCheck() {
        return tileCheck;
    }
    
    public void setTileCheck(boolean tileCheck) {
        this.tileCheck = tileCheck;
        
        fireChanges();
    }
    
    public boolean isWallUp() {
        return wallUp;
    }
    
    public void setWallUp(boolean wallUp) {
        this.wallUp = wallUp;
        
        fireChanges();
    }
    
    public boolean isWallDown() {
        return wallDown;
    }
    
    public void setWallDown(boolean wallDown) {
        this.wallDown = wallDown;
        
        fireChanges();
    }
    
    public boolean isWallLeft() {
        return wallLeft;
    }
    
    public void setWallLeft(boolean wallLeft) {
        this.wallLeft = wallLeft;
        
        fireChanges();
    }
    
    public boolean isWallRight() {
        return wallRight;
    }
    
    public void setWallRight(boolean wallRight) {
        this.wallRight = wallRight;
        
        fireChanges();
    }
    
    public int getDestruction() {
        return destruction;
    }
    
    public void setDestruction(int destruction) {
        this.destruction = destruction;
        
        fireChanges();
    }
    
    public boolean isKey() {
        return key;
    }
    
    public void setKey(boolean key) {
        this.key = key;
        
        fireChanges();
    }
    
    public boolean isShakes() {
        return shakes;
    }
    
    public void setShakes(boolean shakes) {
        this.shakes = shakes;
        
        fireChanges();
    }
    
    public int getParallaxFactor() {
        return parallaxFactor;
    }
    
    public void setParallaxFactor(int parallaxFactor) {
        this.parallaxFactor = parallaxFactor;
        
        fireChanges();
    }
    
    public boolean isRandomSoundFrequency() {
        return randomSoundFrequency;
    }
    
    public void setRandomSoundFrequency(boolean randomSoundFrequency) {
        this.randomSoundFrequency = randomSoundFrequency;
        
        fireChanges();
    }
    
    public boolean isGlide() {
        return glide;
    }
    
    public void setGlide(boolean glide) {
        this.glide = glide;
        
        fireChanges();
    }
    
    public boolean isAlwaysBonus() {
        return alwaysBonus;
    }
    
    public void setAlwaysBonus(boolean alwaysBonus) {
        this.alwaysBonus = alwaysBonus;
        
        fireChanges();
    }
    
    public boolean isSwim() {
        return swim;
    }
    
    public void setSwim(boolean swim) {
        this.swim = swim;
        
        fireChanges();
    }
    
    public int getFramesAmount() {
        return framesAmount;
    }
    
    public void setFramesAmount(int framesAmount) {
        this.framesAmount = framesAmount;
        
        fireChanges();
    }
    
    private void fireChanges() {
        for (var cl : changeListeners) {
            cl.stateChanged(changeEvent);
        }
    }
    
    public void addChangeListener(ChangeListener listener) {
        if (!changeListeners.contains(listener)) {
            changeListeners.add(listener);
        }
    }
    
    public void setSoundFile(String file, int number) {
        soundFiles[number] = file;
    }
    
    public String getSoundFile(int index) {
        return soundFiles[index];
    }
    
    public List<String> getSoundFilesList() {
        return soundFilesList;
    }
    
    public void setAnimationsList(List<PK2SpriteAnimation> animations) {
        this.animationsList = animations;
    }
    
    public List<PK2SpriteAnimation> getAnimationsList() {
        return animationsList;
    }
    
    public void setFramesList(List<BufferedImage> frames) {
        this.framesList = frames;
    }
    
    public List<BufferedImage> getFramesList() {
        return framesList;
    }
}
