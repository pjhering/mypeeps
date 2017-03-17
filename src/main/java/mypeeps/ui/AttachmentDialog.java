package mypeeps.ui;

import mypeeps.entity.Attachment;

public class AttachmentDialog extends ValidPanelDialog<AttachmentPanel>
{

    public AttachmentDialog()
    {
        super("attachment", new AttachmentPanel(new Attachment()));
    }
}
