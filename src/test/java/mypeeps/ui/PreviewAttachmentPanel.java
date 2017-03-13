package mypeeps.ui;

import static mypeeps.Utils.log;
import mypeeps.entity.Attachment;
import static mypeeps.ui.Preview.showInJFrame;

public class PreviewAttachmentPanel
{
    public static void main(String[] args)
    {
        log(PreviewAttachmentPanel.class, "main(String[])");
        showInJFrame(new AttachmentPanel(new Attachment()));
    }
}
