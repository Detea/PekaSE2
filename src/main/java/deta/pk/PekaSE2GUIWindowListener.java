package deta.pk;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PekaSE2GUIWindowListener implements WindowListener {
    private PekaSE2GUI ui;
    
    public PekaSE2GUIWindowListener(PekaSE2GUI ui) {
        this.ui = ui;
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        ui.onClose();
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
    }
    
    @Override
    public void windowIconified(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
    
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }
}
