package deta.pk;

import deta.pk.dialogs.SettingsDialog;
import deta.pk.dialogs.UnsavedChangesDialog;
import deta.pk.filefilters.ImageFilter;
import deta.pk.filefilters.PK2SpriteFilter;
import deta.pk.panels.SetPathPanel;
import deta.pk.panels.spriteeditpane.SpriteEditPane;
import deta.pk.profile.ProfileReader;
import deta.pk.settings.Settings;
import deta.pk.settings.SettingsIO;
import deta.pk.sprite.PK2Sprite;
import deta.pk.sprite.io.PK2SpriteReader13;
import deta.pk.sprite.io.PK2SpriteWriter13;
import deta.pk.util.GFXUtils;
import deta.pk.util.MessageBox;
import deta.pk.util.SpriteFileChooser;
import deta.pk.util.UnknownSpriteFormatException;
import org.tinylog.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PekaSE2GUI extends JFrame implements ChangeListener {
    private static final String PROFILES_FOLDER = "profiles";
    
    private Settings settings = null;
    
    private JTabbedPane tpSprite = new JTabbedPane(JTabbedPane.BOTTOM);
    
    private SetPathPanel setPathPanel = new SetPathPanel(settings, this);
    
    private JMenuBar menuBar;
    private JMenu mFile;
    private JMenuItem miFNew;
    private JMenuItem miFOpen;
    private JMenuItem miFSave;
    private JMenuItem miFSaveAs;
    private JMenuItem miQuit;
    
    private JMenu mProfiles;
    
    private JMenu mOther;
    private JMenuItem mOSettings;
    private JMenuItem mOAbout;
    
    private File loadedFile;
    
    private SpriteEditPane editPane;
    
    private PK2SpriteReader13 spriteReader;
    private PK2SpriteWriter13 spriteWriter;
    
    private SettingsDialog settingsDialog;
    
    private List<File> profileFiles = new ArrayList<>();
    
    private String title;
    
    public void setup() {
        spriteReader = new PK2SpriteReader13();
        spriteWriter = new PK2SpriteWriter13();
        
        settingsDialog = new SettingsDialog();
        
        createMenuBar();
        setJMenuBar(menuBar);
        
        try {
            settings = SettingsIO.load(Settings.FILE);
            
            setupEditPane();
        } catch (IOException e) {
            setContentPane(setPathPanel);
        }
        
        addListeners();
        
        addWindowListener(new PekaSE2GUIWindowListener(this));
        
        setupIcon();
        
        setTitle("PekaSE2");
        setMinimumSize(new Dimension(900, 700));
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        setVisible(true);
    }
    
    private void setupIcon() {
        try {
            var iconStream = getClass().getClassLoader().getResourceAsStream("pekase.png");
            var icon = ImageIO.read(iconStream);
            
            setIconImage(icon);
        } catch (IOException e) {
            Logger.warn(e, "Unable to set frame icon!");
        }
    }
    
    private void setupEditPane() {
        settingsDialog.setSettings(settings);
        
        editPane = new SpriteEditPane(settings);
        editPane.registerUnsavedChangesListener(this);
        
        loadProfile(settings.getSpriteProfileFile());
        
        setContentPane(editPane);
        
        newFile();
    }
    
    /**
     * This method gets called after the user has set the game path.
     *
     * It sets the contentPane of the frame from the SetPathPanel to the JTabbedPane that allows the sprite editing.
     */
    public void pathSet() {
        setContentPane(tpSprite);
        
        try {
            settings = SettingsIO.load(Settings.FILE);

            setupEditPane();
        } catch (IOException e) {
            setContentPane(setPathPanel);
        }
    }
    
    private void newTab(File file) {
        var spriteReader = new PK2SpriteReader13();
        
        PK2Sprite sprite = null;
        try {
            sprite = spriteReader.load(file, settings.getSpritesPath());
            
            var spriteEditPane = new SpriteEditPane(settings);
            spriteEditPane.setSprite(sprite);
            
            tpSprite.add(spriteEditPane, file.getName());
            
            tpSprite.setSelectedIndex(tpSprite.getTabCount() - 1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load sprite file '" + file.getName() + "'!\n" + e.getMessage(), "Unable to load file", JOptionPane.ERROR_MESSAGE);
        } catch (UnknownSpriteFormatException e) {
            MessageBox.showUnknownSpriteError(file.getName());
        }
    }
    
    private void loadSprite(String filename) {
        if (editPane.unsavedChangesPresent()) {
            var result = UnsavedChangesDialog.show(this);
            
            if (result != JOptionPane.CANCEL_OPTION) {
                loadSpriteFile(filename);
            }
        } else {
            loadSpriteFile(filename);
        }
    }
    
    private void loadSpriteFile(String filename) {
        PK2Sprite sprite = null;
        
        try {
            if (!filename.endsWith(".spr")) {
                filename += ".spr";
            }
            
            var file = new File(filename);
            
            sprite = spriteReader.load(file);
            
            if (!new File(settings.getSpritesPath() + File.separatorChar + sprite.getImageFile()).exists()) {
                int result = JOptionPane.showConfirmDialog(this, "Unable to load sprite's image file '" + sprite.getImageFile() + "'.\nPlease choose a different image file.", "Unable to load image", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if (result == JOptionPane.YES_OPTION) {
                    var fc = new JFileChooser(settings.getSpritesPath());
                    fc.setFileFilter(new ImageFilter());
                    fc.setDialogTitle("Choose an image file...");
                    
                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        sprite.setImageFile(fc.getSelectedFile().getName());
                        
                        GFXUtils.loadSpriteImageSheet(sprite, settings.getSpritesPath());
                        
                        editPane.setSprite(sprite);
                        
                        loadedFile = file;
                        
                        updateTitle(filename);
                    }
                }
            } else {
                GFXUtils.loadSpriteImageSheet(sprite, settings.getSpritesPath());
                
                editPane.setSprite(sprite);
                
                loadedFile = file;
                
                updateTitle(filename);
            }
        } catch (IOException e) {
            Logger.warn(e, "Unable to load sprite '" + filename + "'!\n");
            
            JOptionPane.showMessageDialog(null, "Unable to load sprite.\n" + e.getMessage(), "Unable to load sprite!", JOptionPane.ERROR_MESSAGE);
        } catch (UnknownSpriteFormatException e) {
            Logger.warn(e, "Unable to load sprite '" + filename + "'\n");
            
            MessageBox.showUnknownSpriteError(filename, e.getMessage(), "File is not 1.3 sprite!");
        }
    }
    
    private void saveSprite(File file) {
        var sprite = editPane.setValues();
        
        try {
            if (!file.getName().endsWith(".spr")) {
                file = new File(file.getAbsolutePath() + ".spr");
            }
            
            spriteWriter.save(sprite, file);
        } catch (IOException e) {
            Logger.warn(e, "Unable to save sprite '" + file.getAbsolutePath() + "'!\n");
            
            JOptionPane.showMessageDialog(null, "Unable to save sprite!\n" + e.getMessage(), "Unable to save!", JOptionPane.ERROR_MESSAGE);
        }
        
        loadedFile = file;
        
        editPane.setUnsavedChangesPresent(false);
        updateTitle(file.getAbsolutePath(), false);
    }
    
    private void newFile() {
        editPane.resetValues();

        editPane.setSprite(new PK2Sprite());
        
        updateTitle("Unnamed.spr");
        
        editPane.setUnsavedChangesPresent(false);
    }
    
    private void newTab(String filename) {
        var spriteEditPane = new SpriteEditPane(settings);
        
        tpSprite.add(spriteEditPane, filename);
        
        tpSprite.setSelectedIndex(tpSprite.getTabCount() - 1);
    }
    
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        mFile = new JMenu("File");
        miFNew = new JMenuItem("New");
        miFOpen = new JMenuItem("Open");
        miFSave = new JMenuItem("Save");
        miFSaveAs = new JMenuItem("Save As...");
        miQuit = new JMenuItem("Quit");
        
        mFile.add(miFNew);
        mFile.add(miFOpen);
        mFile.addSeparator();
        mFile.add(miFSave);
        mFile.add(miFSaveAs);
        mFile.addSeparator();
        mFile.add(miQuit);
        
        mOther = new JMenu("Other");
        mOSettings = new JMenuItem("Settings");
        mOAbout = new JMenuItem("About");
        
        mOther.add(mOSettings);
        mOther.add(mOAbout);
        
        mProfiles = new JMenu("Profiles");
        addProfileMenuItems();
        
        menuBar.add(mFile);
        menuBar.add(mProfiles);
        menuBar.add(mOther);
    }
    
    private void addListeners() {
        miFNew.addActionListener(e -> {
            newFile();
        });
        
        miFOpen.addActionListener(e -> {
            var fc = new SpriteFileChooser(settings);
            
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                loadSprite(fc.getSelectedFile().getAbsolutePath());
            }
        });
        
        miFSave.addActionListener(e -> {
            if (loadedFile == null) {
                var fc = new SpriteFileChooser(settings);
                
                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    saveSprite(fc.getSelectedFile());
                }
            } else {
                saveSprite(loadedFile);
            }
        });
        
        miFSaveAs.addActionListener(e -> {
            var fc = new SpriteFileChooser(settings);
            if (loadedFile != null) fc.setSelectedFile(loadedFile);
            
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                saveSprite(fc.getSelectedFile());
            }
        });
        
        mOSettings.addActionListener(e -> {
            settingsDialog.setVisible(true);
        });
        
        mOAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "PekaSE2 v" + PekaSE2.VERSION, "About", JOptionPane.INFORMATION_MESSAGE);
        });
        
        miQuit.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void addProfileMenuItems() {
        var profiles = new File(PROFILES_FOLDER).listFiles();
        if (profiles != null) {
            for (var f : profiles) {
                var menuItem = new JCheckBoxMenuItem(f.getName());
                mProfiles.add(menuItem);
                
                menuItem.addActionListener(e -> {
                    if (!menuItem.getText().equals(settings.getSpriteProfileFile())) {
                        loadProfile(menuItem.getText());
                        
                        if (menuItem.getText().equals(f.getName())) {
                            menuItem.setSelected(true);
                        } else {
                            menuItem.setSelected(false);
                        }
                        
                        SettingsIO.save(Settings.FILE, settings);
                    }
                });
                
                profileFiles.add(f);
            }
        } else {
            Logger.warn("Unable to find profile files!");
        }
    }

    private void loadProfile(String file) {
        var sp = ProfileReader.loadSpriteProfile(PROFILES_FOLDER + File.separatorChar + file);
        
        settings.setSpriteProfile(sp);
        settings.setSpriteProfileFile(file);
        
        editPane.setSpriteProfile(settings.getSpriteProfile());
        
        if (mProfiles.getItemCount() > 0) {
            for (int i = 0; i < mProfiles.getItemCount(); i++) {
                if (mProfiles.getItem(i).getText().equals(settings.getSpriteProfileFile())) {
                    mProfiles.getItem(i).setSelected(true);
                } else {
                    mProfiles.getItem(i).setSelected(false);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Unable to find profile files!", "No profile files", JOptionPane.ERROR_MESSAGE);
        }
        
        Logger.info("Loaded profile file: " + file);
    }
    
    private void updateTitle(String title, boolean unsavedChanges) {
        this.title = title;
        
        if (unsavedChanges) title += "*";
        
        setTitle(title + " - PekaSE2");
    }
    
    private void updateTitle(String title) {
        this.title = title;
        
        updateTitle(title, false);
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        updateTitle(title, true);
    }
    
    public void handleUnsavedChanges() {
        if (loadedFile != null) {
            saveSprite(loadedFile);
        } else {
            var fc = new SpriteFileChooser(settings);
            
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                saveSprite(fc.getSelectedFile());
            }
        }
    }
    
    void onClose() {
        if (editPane != null) {
            if (editPane.unsavedChangesPresent()) {
                UnsavedChangesDialog.show(this, true);
            } else {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
}
