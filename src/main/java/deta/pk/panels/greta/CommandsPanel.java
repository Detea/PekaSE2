package deta.pk.panels.greta;

import deta.pk.listener.UnsavedChangesListener;
import deta.pk.panels.PekaSE2Panel;
import deta.pk.profile.SpriteProfile;
import deta.pk.sprite.PK2Sprite;

import javax.swing.*;

import org.json.JSONArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandsPanel extends PekaSE2Panel {
    private JTextArea taCommands;
    
    public CommandsPanel() {
        setup();
    }
    
    private void setup() {
        taCommands = new JTextArea();
        
        generateLayout();
    }
    
    private void generateLayout() {
        setLayout(new BorderLayout());
        add(taCommands, BorderLayout.CENTER);
    }
    
    @Override
    public void setSprite(PK2Sprite sprite) {
        taCommands.setText(sprite.getCommands().toString());
    }
    
    @Override
    public void resetValues() {
        taCommands.setText("");
    }
    
    @Override
    public void setValues(PK2Sprite sprite) {

        sprite.setCommands(new JSONArray(taCommands.getText()));
    }
    
    @Override
    public void setProfileData(SpriteProfile profile) {
        // Not used here
    }
    
    @Override
    public void setUnsavedChangesListener(UnsavedChangesListener listener) {
        taCommands.getDocument().addDocumentListener(listener);
    }
}
