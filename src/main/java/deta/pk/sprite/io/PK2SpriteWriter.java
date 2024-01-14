package deta.pk.sprite.io;

import deta.pk.sprite.PK2Sprite;

import java.io.File;
import java.io.IOException;

public abstract class PK2SpriteWriter {
    public abstract void save(PK2Sprite sprite, File file) throws IOException;
}
