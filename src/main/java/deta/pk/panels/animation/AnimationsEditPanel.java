package deta.pk.panels.animation;

import deta.pk.listener.UnsavedChangesListener;
import deta.pk.panels.FrameImagePanel;
import deta.pk.panels.PekaSE2Panel;
import deta.pk.panels.animation.dragndrop.AnimationDragGestureListener;
import deta.pk.panels.animation.preview.AnimationContainer;
import deta.pk.panels.animation.preview.AnimationPanel;
import deta.pk.panels.spriteeditpane.SpriteFramesChangeListener;
import deta.pk.profile.SpriteProfile;
import deta.pk.settings.Settings;
import deta.pk.sprite.PK2Sprite;
import deta.pk.sprite.PK2SpriteAnimation;
import deta.pk.util.WrapLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AnimationsEditPanel extends PekaSE2Panel implements SpriteFramesChangeListener {
    private JTabbedPane tpAnimations;
    
    private List<FrameImagePanel> framePanels;
    private List<AnimationPanel> animationPanelList;
    
    private JPanel framePanelsPanel;
    
    private JSpinner spFrameRate;
    
    private Settings settings;
    
    private AnimationsEditModel model;
    
    private JSplitPane splitPane;
    
    public AnimationsEditPanel(Settings settings) {
        this.settings = settings;
        
        this.model = new AnimationsEditModel();
        
        setup();
    }
    
    private void setup() {
        animationPanelList = new ArrayList<>();
        
        tpAnimations = new JTabbedPane();
        
        framePanels = new ArrayList<>();
        
        framePanelsPanel = new JPanel();
        framePanelsPanel.setLayout(new WrapLayout());
        
        spFrameRate = new JSpinner(new SpinnerNumberModel());
        spFrameRate.addChangeListener(l -> {
            for (var ap : animationPanelList) {
                ap.setFrameRate((int) spFrameRate.getValue());
            }
        });
        
        generateLayout();
    }
    
    private void generateLayout() {
        addAnimationPanel("Still");
        addAnimationPanel("Walking");
        addAnimationPanel("Jump up");
        addAnimationPanel("Jump down");
        addAnimationPanel("Duck");
        addAnimationPanel("Damage");
        addAnimationPanel("Knock out");
        addAnimationPanel("Attack 1");
        addAnimationPanel("Attack 2");
        
        setLayout(new MigLayout());
        
        var pnlFramerate = new JPanel();
        pnlFramerate.setLayout(new MigLayout("flowx"));
        pnlFramerate.add(new JLabel("Frame Rate:"));
        pnlFramerate.add(spFrameRate);
        
        pnlFramerate.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Component.borderColor")));
        
        var scrollPane = new JScrollPane(framePanelsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(8);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(scrollPane);
        splitPane.setBottomComponent(tpAnimations);
        splitPane.resetToPreferredSizes();
        //splitPane.setDividerLocation(600);
        
        add(pnlFramerate, "dock north");
        //add(tpAnimations, "dock south");
        //add(framePanelsPanel, "dock center");
        add(splitPane, "dock center");
    }
    
    private void addAnimationPanel(String title) {
        var ap = new AnimationPanel(model, settings);
        animationPanelList.add(ap);
        
        tpAnimations.add(ap, title);
    }
    
    @Override
    public void setSprite(PK2Sprite sprite) {
        for (int i = 0; i < animationPanelList.size(); i++) {
            animationPanelList.get(i).setSprite(sprite, i);
        }
        
        updateFrames(sprite.getFramesList());
        
        spFrameRate.setValue(sprite.getFrameRate());
        
        splitPane.resetToPreferredSizes();
    }
    
    @Override
    public void framesChanged(List<BufferedImage> frames) {
        updateFrames(frames);
    }
    
    private final List<AnimationFramePreview> framesPreviewList = new ArrayList<>();
    private void updateFrames(List<BufferedImage> frames) {
        AnimationPanel.setFramesList(frames);
        
        if (frames.size() != framesPreviewList.size()) {
            if (frames.size() > framesPreviewList.size()) {
                int panelsToAdd = frames.size() - framesPreviewList.size();
                
                for (int i = 0; i < panelsToAdd; i++) {
                    framesPreviewList.add(createFrameImagePanel(frames.get(i), i));
                }
            } else if (frames.size() < framesPreviewList.size()) {
                int framesPreviews = framesPreviewList.size();
                int removedCount = 0;
                for (int i = frames.size(); i < framesPreviews; i++) {
                    framesPreviewList.remove(i - removedCount);
                    
                    removedCount++;
                }
            }
            
            framePanelsPanel.removeAll();
            
            for (var fp : framesPreviewList) {
                framePanelsPanel.add(fp);
            }
        }
        
        for (int i = 0; i < framesPreviewList.size(); i++) {
            framesPreviewList.get(i).setData(frames.get(i), i + 1);
        }
        
        for (var ap : animationPanelList) {
            ap.repaintPreviews();
        }
    }
    
    private AnimationFramePreview createFrameImagePanel(BufferedImage frame, int index) {
        var aniPreview = new AnimationFramePreview(frame, index);
        
        var ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(aniPreview, DnDConstants.ACTION_COPY, new AnimationDragGestureListener());
        
        return aniPreview;
    }
    
    @Override
    public void resetValues() {
        framePanelsPanel.removeAll();
        framesPreviewList.clear();
        
        spFrameRate.setValue(0);
        
        for (var ac : animationPanelList) {
            ac.reset();
        }
    }
    
    @Override
    public void setValues(PK2Sprite sprite) {
        sprite.setFrameRate((int) spFrameRate.getValue());
        
        for (int i = 0; i < animationPanelList.size(); i++) {
            var ap = animationPanelList.get(i);
            
            var sequence = ap.getAnimationFrames();
            var frames = ap.getFramesAmount();
            var loop = ap.loop();
            
            var anim = new PK2SpriteAnimation(sequence, frames, loop);
            sprite.getAnimationsList().set(i, anim);
        }
    }
    
    @Override
    public void setProfileData(SpriteProfile profile) {
    
    }
    
    @Override
    public void setUnsavedChangesListener(UnsavedChangesListener listener) {
        spFrameRate.addChangeListener(listener);
        
        for (var ap : animationPanelList) {
            ap.setUnsavedChangesListener(listener);
        }
    }
}
