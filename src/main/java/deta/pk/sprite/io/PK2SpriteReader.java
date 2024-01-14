package deta.pk.sprite.io;

import deta.pk.sprite.PK2Sprite;
import deta.pk.util.UnknownSpriteFormatException;

import java.io.File;
import java.io.IOException;

public abstract class PK2SpriteReader {
    
    public abstract PK2Sprite load(File filename, String gfxPath) throws IOException, UnknownSpriteFormatException;
    public abstract PK2Sprite load(File filename) throws IOException, UnknownSpriteFormatException;
}
