package deta.pk.filefilters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class PK2SpriteFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".spr");
    }
    
    @Override
    public String getDescription() {
        return "Pekka Kana 2 Sprite file (*.spr)";
    }
}
