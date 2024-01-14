package deta.pk.panels.greta;

import deta.pk.listener.UnsavedChangesListener;
import deta.pk.panels.PekaSE2Panel;
import deta.pk.profile.SpriteProfile;
import deta.pk.sprite.PK2Sprite;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class GretaPropertiesPanel extends PekaSE2Panel {
    private JCheckBox chkAlwaysActive;
    
    private JSpinner spDeadWeight;
    private JCheckBox chkUseDeadWeight;
    
    public GretaPropertiesPanel() {
        setup();
    }
    
    public void setup() {
        chkAlwaysActive = new JCheckBox("Always active");
        spDeadWeight = new JSpinner(new SpinnerNumberModel(0.0, -5.0, 5.0, 0.1));
        spDeadWeight.setEnabled(false);
        
        chkUseDeadWeight = new JCheckBox("Dead Weight:");
        chkUseDeadWeight.addActionListener(e -> {
            spDeadWeight.setEnabled(chkUseDeadWeight.isSelected());
        });
        
        generateLayout();
    }
    
    private void generateLayout() {
        setLayout(new MigLayout("flowy"));
        
        add(chkAlwaysActive);
        add(chkUseDeadWeight);
        add(spDeadWeight);
    }
    
    @Override
    public void setSprite(PK2Sprite sprite) {
        chkAlwaysActive.setSelected(sprite.isAlwaysActive());
        
        chkUseDeadWeight.setSelected(sprite.hasDeadWeight());
        spDeadWeight.setEnabled(sprite.hasDeadWeight());
        
        if (sprite.hasDeadWeight()) {
            spDeadWeight.setValue(sprite.getDeadWeight());
        }
    }
    
    @Override
    public void resetValues() {
        chkAlwaysActive.setSelected(false);
        chkUseDeadWeight.setSelected(false);
        spDeadWeight.setEnabled(false);
        spDeadWeight.setValue(0.0);
    }
    
    @Override
    public void setValues(PK2Sprite sprite) {
        sprite.setAlwaysActive(chkAlwaysActive.isSelected());
        
        boolean hasDeadWeight = chkUseDeadWeight.isSelected();
        sprite.setHasDeadWeight(hasDeadWeight);
        
        if (hasDeadWeight) {
            sprite.setDeadWeight((double) spDeadWeight.getValue());
        }
    }
    
    @Override
    public void setProfileData(SpriteProfile profile) {
        // Not used in this panel
    }
    
    @Override
    public void setUnsavedChangesListener(UnsavedChangesListener listener) {
        chkAlwaysActive.addActionListener(listener);
        spDeadWeight.addChangeListener(listener);
    }
}