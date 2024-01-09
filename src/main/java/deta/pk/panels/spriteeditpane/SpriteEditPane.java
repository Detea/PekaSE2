package deta.pk.panels.spriteeditpane;

import deta.pk.FileFormat;
import deta.pk.listener.UnsavedChangesListener;
import deta.pk.panels.PekaSE2Panel;
import deta.pk.panels.ailists.AIListPanel;
import deta.pk.panels.animation.AnimationsEditPanel;
import deta.pk.panels.imagepanel.ImagePanel;
import deta.pk.panels.attackspanel.AttacksPanel;
import deta.pk.panels.propertiespanel.PropertiesPanel;
import deta.pk.panels.soundspanel.SoundsPanel;
import deta.pk.profile.SpriteProfile;
import deta.pk.settings.Settings;
import deta.pk.sprite.PK2Sprite;
import deta.pk.util.GFXUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SpriteEditPane extends JTabbedPane {
    private Settings settings;
    
    private ImagePanel imagePanel;
    private AnimationsEditPanel animationsPanel;
    private AIListPanel aiListPanel;
    private SoundsPanel soundsPanel;
    private AttacksPanel attacksPanel;
    private PropertiesPanel propertiesPanel;
    
    private List<PekaSE2Panel> panels;
    
    private SpriteEditPaneModel model = new SpriteEditPaneModel();
    
    private final UnsavedChangesListener unsavedChangesListener = new UnsavedChangesListener(this);
    
    public SpriteEditPane(Settings settings) {
        this.settings = settings;

        setup();
    }
    
    private void setup() {
        panels = new ArrayList<>();
        
        imagePanel = new ImagePanel(model, settings);
        animationsPanel = new AnimationsEditPanel(settings);
        aiListPanel = new AIListPanel(settings, FileFormat.GRETA);
        soundsPanel = new SoundsPanel(settings);
        attacksPanel = new AttacksPanel(settings);
        propertiesPanel = new PropertiesPanel(settings);
        
        model.addSpriteFramesChangeListener(animationsPanel);
        
        addPanel(imagePanel, "Image");
        addPanel(animationsPanel, "Animations");
        addPanel(aiListPanel, "AI", true);
        addPanel(attacksPanel, "Attack");
        addPanel(soundsPanel, "Sounds");
        addPanel(propertiesPanel, "Properties", true);
        
        addUnsavedChangesListener();
    }
    
    private void addUnsavedChangesListener() {
        for (var p : panels) {
            p.setUnsavedChangesListener(unsavedChangesListener);
        }
    }
    
    public PK2Sprite setValues() {
        var sprite = new PK2Sprite();
        for (var p : panels) {
            p.setValues(sprite);
        }
        
        return sprite;
    }
    
    public void setSprite(PK2Sprite sprite) {
        unsavedChangesListener.setIgnoreChanges(true);
        
        sprite.setFramesList(GFXUtils.cutFrames(sprite.getImage(), sprite.getFramesAmount(), sprite.getFrameX(), sprite.getFrameY(), sprite.getFrameWidth(), sprite.getFrameHeight()));
        
        model.setSprite(sprite);
        
        for (var p : panels) {
            if (p != null) {
                p.setSprite(model.getSprite());
            }
        }
        
        unsavedChangesListener.setIgnoreChanges(false);
    }
    
    private void addPanel(PekaSE2Panel panel, String name, boolean wrapInScrollPane) {
        if (wrapInScrollPane) {
            var sp = new JScrollPane(panel);
            sp.getVerticalScrollBar().setUnitIncrement(8);
            
            add(sp, name);
        } else {
            add(panel, name);
        }
        
        panels.add(panel);
    }
    
    private void addPanel(PekaSE2Panel panel, String name) {
        addPanel(panel, name, false);
    }
    
    public void registerUnsavedChangesListener(ChangeListener listener) {
        unsavedChangesListener.addChangeListener(listener);
    }
    
    public void setUnsavedChangesPresent(boolean present) {
        model.setUnsavedChanges(present);
    }
    
    public boolean unsavedChangesPresent() {
        return model.unsavedChangesPresent();
    }
    
    public void resetValues() {
        unsavedChangesListener.setIgnoreChanges(true);
        
        for (var p : panels) {
            p.resetValues();
        }
        
        unsavedChangesListener.setIgnoreChanges(false);
    }
    
    public void setSpriteProfile(SpriteProfile profile) {
        for (var p : panels) {
            p.setProfileData(profile);
        }
    }
}
