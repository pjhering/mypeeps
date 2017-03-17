package mypeeps.ui2;

import java.awt.Dialog;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import mypeeps.entity.Attachment;
import mypeeps.entity.Person;

/**
 * Used to create or edit an attachment.
 * 
 * @author tinman
 */
public class AttachmentDialog
{
    
    private final Attachment ATTACHMENT;
    
    public AttachmentDialog(Attachment attachment)
    {
        this.ATTACHMENT = requireNonNull(attachment);
    }
    
    public Attachment open(Frame parent)
    {
        return ATTACHMENT;
    }
    
    public Attachment open(Dialog parent)
    {
        return ATTACHMENT;
    }
    
    private boolean isValid()
    {
        return false;
    }
    
    private void updatePerson()
    {
    }
    
    private void doSaveAction()
    {
    }
    
    private void doCancelAction()
    {
    }
    
    private void close()
    {
    }
}
