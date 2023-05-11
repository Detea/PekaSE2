package deta.pk.panels;

import deta.pk.PekaSE2GUI;
import deta.pk.settings.Settings;
import deta.pk.settings.SettingsIO;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SetPathPanel extends JPanel {
    private Settings settings;
    private PekaSE2GUI mainGUI;
    
    public SetPathPanel(Settings settings, PekaSE2GUI ui) {
        this.mainGUI = ui;
        
        setup();
    }
    
    private void setup() {
        var lblPath = new JLabel("Path to the game:");
        var btnBrowse = new JButton("Browse");
        
        setLayout(new MigLayout("align 50% 50%"));
        
        add(lblPath, "cell 0 0");
        add(btnBrowse, "cell 0 1");
        
        btnBrowse.addActionListener(e -> {
            var fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                settings = new Settings();
                settings.setGamePath(fc.getSelectedFile().getAbsolutePath());
                
                SettingsIO.save(Settings.FILE, settings);
                
                mainGUI.pathSet();
            }
        });
    }
}
