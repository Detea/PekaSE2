package deta.pk.panels.animation.dragndrop;

import deta.pk.panels.animation.preview.AnimationContainer;
import org.tinylog.Logger;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AnimationFrameDropTarget extends DropTargetAdapter {
    private final DropTarget dropTarget;
    private final AnimationContainer panel;
    
    public AnimationFrameDropTarget(AnimationContainer panel) {
        this.panel = panel;
        
        dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
    }
    
    @Override
    public void drop(DropTargetDropEvent event) {
        try {
            var tr = event.getTransferable();
            var frameNumber = (int) tr.getTransferData(TransferableFrameNumber.frameNumberFlavor);
            
            if (event.isDataFlavorSupported(TransferableFrameNumber.frameNumberFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                
                panel.setFrameNumber(frameNumber - 1);
                event.dropComplete(true);
                
                return;
            }
        } catch (Exception ex) {
            Logger.warn(ex);
            
            event.rejectDrop();
        }
    }
}
