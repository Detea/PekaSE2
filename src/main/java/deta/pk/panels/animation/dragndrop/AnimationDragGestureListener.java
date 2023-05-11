package deta.pk.panels.animation.dragndrop;

import deta.pk.panels.animation.AnimationFramePreview;

import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

public class AnimationDragGestureListener implements DragGestureListener {
    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        var cursor = Cursor.getDefaultCursor();
        var pnl = (AnimationFramePreview) event.getComponent();
        
        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }
        
        event.startDrag(cursor, new TransferableFrameNumber(pnl.getFrameNumber() + 1));
    }
}
