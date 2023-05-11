package deta.pk.panels;

import deta.pk.listener.UnsavedChangesListener;
import deta.pk.profile.SpriteProfile;
import deta.pk.sprite.PK2Sprite;

import javax.swing.*;

public abstract class PekaSE2Panel extends JPanel {
    public abstract void setSprite(PK2Sprite sprite);
    public abstract void resetValues();
    public abstract void setValues(PK2Sprite sprite);
    
    public abstract void setProfileData(SpriteProfile profile);
    
    public abstract void setUnsavedChangesListener(UnsavedChangesListener listener);
}
