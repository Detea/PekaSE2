package deta.pk.panels.ailists;

import deta.pk.listener.UnsavedChangesListener;
import deta.pk.panels.PekaSE2Panel;
import deta.pk.profile.SpriteProfile;
import deta.pk.settings.Settings;
import deta.pk.sprite.PK2Sprite;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AIListPanel extends PekaSE2Panel {
    private Settings settings;
    
    private List<JComboBox<String>> cbAiList;
    private List<JSpinner> spAiList;
    
    private List<Integer> aiIdList;
    
    public AIListPanel(Settings settings) {
        this.settings = settings;
        
        setLayout(new MigLayout("flowy, alignx 50%"));
        
        cbAiList = new ArrayList<>();
        spAiList = new ArrayList<>();
        
        setup();
    }
    
    private void setup() {
        for (int i = 0; i < 10; i++) {
            addComboBox(i);
        }
    }
    
    public void setSprite(PK2Sprite sprite) {
        for (int i = 0; i < sprite.getAiList().length; i++) {
            cbAiList.get(i).setSelectedItem(settings.getSpriteProfile().getAiPatternMap().get(sprite.getAiList()[i]));
            spAiList.get(i).setValue(sprite.getAiList()[i]);
        }
    }
    
    @Override
    public void resetValues() {
        for (var c : cbAiList) {
            if (c.getItemCount() > 0) c.setSelectedIndex(0);
        }
        
        for (var s : spAiList) {
            s.setValue(0);
        }
    }
    
    @Override
    public void setValues(PK2Sprite sprite) {
        for (int i = 0; i < 10; i++) {
            sprite.getAiList()[i] = (int) spAiList.get(i).getValue();
        }
    }
    
    @Override
    public void setProfileData(SpriteProfile profile) {
        aiIdList = profile.getAiPatternMap().keySet().stream().toList();
        
        var aiList = profile.getAiPatternMap().entrySet().stream().toList();
        
        for (var cb : cbAiList) {
            cb.removeAllItems();
            
            for (var item : aiList) {
                cb.addItem(item.getValue());
            }
        }
        
        for (var sp : spAiList) {
            sp.setModel(new SpinnerListModel(aiIdList));
        }
    }
    
    @Override
    public void setUnsavedChangesListener(UnsavedChangesListener listener) {
        for (int i = 0; i < 10; i++) {
            cbAiList.get(i).addActionListener(listener);
            spAiList.get(i).addChangeListener(listener);
        }
    }
    
    private void addComboBox(int index) {
        var cbAi = new JComboBox<String>();
        
        var sp = new JSpinner();
        sp.setModel(new SpinnerListModel(List.of(0))); // Have to provide a list of one int, because SpinnerListModel's empty constructor for some reason provides one empty string???
        sp.setEditor(new CustomEditor(sp));
        sp.addChangeListener(new SpinnerListener(cbAi, sp));
        
        var pnl = new JPanel();
        pnl.setLayout(new MigLayout("flowx"));
        pnl.add(new JLabel((index + 1) + ": "), "width 5%");
        pnl.add(cbAi);
        pnl.add(sp);
        
        add(pnl);
        
        cbAi.addItemListener(l -> {
            if (cbAi.getSelectedIndex() > -1 && cbAi.getSelectedIndex() < aiIdList.size()) {
                sp.setValue(aiIdList.get(cbAi.getSelectedIndex()));
            }
        });
        
        spAiList.add(sp);
        cbAiList.add(cbAi);
    }
    
    private class SpinnerListener implements ChangeListener {
        private JComboBox<String> comboBox;
        private JSpinner spinner;
        
        public SpinnerListener(JComboBox<String> comboBox, JSpinner spinner) {
            this.comboBox = comboBox;
            
            this.spinner = spinner;
        }
        
        @Override
        public void stateChanged(ChangeEvent e) {
            comboBox.setSelectedItem(settings.getSpriteProfile().getAiPatternMap().get((int) spinner.getValue()));
        }
    }
    
    /*
        I have no idea why I can't use the fucking default editor, but Swing is being extremely annoying and this is the only way I can get the typed values to stay.
     */
    class CustomEditor extends JFormattedTextField implements ChangeListener {
        
        private final JSpinner spinner;
        
        public CustomEditor(JSpinner spinner) {
            super();
            
            this.spinner = spinner;
            
            var formatter = new DefaultFormatter();
            formatter.setOverwriteMode(true);
            
            setFormatterFactory(new DefaultFormatterFactory(formatter));
            
            updateValue();
            
            spinner.addChangeListener(this);
            
            addActionListener((ActionEvent e) -> {
                this.spinner.getModel().setValue(this.getValue());
            });
        }
        
        @Override
        public void stateChanged(ChangeEvent e) {
            updateValue();
        }
        
        private void updateValue() {
            setValue((int) spinner.getModel().getValue());
        }
    }
}
