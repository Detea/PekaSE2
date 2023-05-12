package deta.pk.panels.animation;

import deta.pk.panels.FrameImagePanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AnimationFramePreview extends JPanel {
    private FrameImagePanel frameImage;
    private JLabel lblFrameNumber;
    
    private int width;
    private int height;
    
    public AnimationFramePreview(BufferedImage image, int frame) {
        this(image, frame, image.getWidth(), image.getHeight());
    }
    
    public AnimationFramePreview(BufferedImage image, int frame, int width, int height) {
        frameImage = new FrameImagePanel(image);
        lblFrameNumber = new JLabel(Integer.toString(frame));
        
        this.width = width;
        this.height = height;
        
        setup();
    }
    
    private void setup() {
        setLayout(new MigLayout("flowy"));
        
        frameImage = new FrameImagePanel();

        add(frameImage, "dock center");
        add(lblFrameNumber, "align 50%");
    }
    
    public void setData(BufferedImage frame, int index) {
        frameImage.setImage(frame);
        
        lblFrameNumber.setText(Integer.toString(index));
    }
    
    public int getFrameNumber() {
        return Integer.parseInt(lblFrameNumber.getText());
    }
}
