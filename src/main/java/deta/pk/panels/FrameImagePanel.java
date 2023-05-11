package deta.pk.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class FrameImagePanel extends JPanel {
    private int prefWidth = 0;
    private int prefHeight = 0;
    
    private int maxWidth = 160;
    private int maxHeight = 160;
    
    private int minWidth = 32;
    private int minHeight = 32;
    
    private BufferedImage image;
    
    public FrameImagePanel() {
        setup();
    }
    
    public FrameImagePanel(int prefWidth, int prefHeight, int maxWidth, int maxHeight) {
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
        
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        
        setup();
    }
    
    public FrameImagePanel(BufferedImage image) {
        this.image = image;
        
        setup();
    }
    
    private void setup() {
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
        setBackground(Color.GRAY);

        if (prefWidth > 0) {
            setPreferredSize(new Dimension(prefWidth, prefHeight));
        } /*else {
            setMinimumSize(new Dimension(128, 128));
            setPreferredSize(new Dimension(128, 128));
        }*/
    }
    
    public void setMaxSize(int width, int height) {
        this.maxWidth = width;
        this.maxHeight = height;
        
        /*
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));*/
    }
    
    public void setImage(BufferedImage img) {
        this.image = img;
        
        if (img != null) {
            setPreviewSize(image.getWidth(), image.getHeight());
        } else {
            setPreviewSize(minWidth, minHeight);
        }
        
        revalidate();
        repaint();
    }
    
    public void setPreviewSize(int width, int height) {
        if (width < minWidth) width = minWidth;
        if (height < minHeight) height = minHeight;
        
        if (width > maxWidth || height > maxHeight) {
            float ratio = Math.min((((float) maxWidth / (float) width)), ((float) maxHeight / (float) height));
  
            width = (int) (width * ratio);
            height = (int) (height * ratio);
        }
        
        int w = width + (width / 2);
        int h = height + (height / 2);

        setPreferredSize(new Dimension(w, h));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        var g2 = (Graphics2D) g;
        
        if (image != null) {
            if (image.getWidth() <= maxWidth && image.getHeight() <= maxHeight) {
                g2.drawImage(image, (getWidth() / 2) - (image.getWidth() / 2), (getHeight() / 2) - (image.getHeight() / 2), null); // Draw the image in the middle of this panel.
            } else {
                // Keep the image in visible within the maximum bounds and maintain its aspect ratio.
                int width = image.getWidth();
                int height = image.getHeight();
                
                float ratio = Math.min((((float) maxWidth / (float) width)), ((float) maxHeight / (float) height));
                
                width = (int) (width * ratio);
                height = (int) (height * ratio);
   
                g2.drawImage(image, (getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height, null);
            }
        }
    }
}
