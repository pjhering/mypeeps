package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static mypeeps.Utils.log;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Attachment;

public class AttachmentPanel extends ValidPanel<Attachment>
{
    
    public JTextField fileNameField;
    public JTextField descriptionField;

    public AttachmentPanel(Attachment entity)
    {
        super(entity);
        log(AttachmentPanel.class, "AttachmentPanel(Attachment)");
        init();
        updateFields();
    }
    
    private void init()
    {
        log(AttachmentPanel.class, "init()");
        fileNameField = new JTextField(20);
        selectOnFocus(fileNameField);
        descriptionField = new JTextField(20);
        selectOnFocus(descriptionField);
        JPanel fields = new JPanel(new GridLayout(2, 1, 5, 5));
        fields.add(fileNameField);
        fields.add(descriptionField);
        
        JPanel labels = new JPanel(new GridLayout(2, 1, 5, 5));
        labels.add(new JLabel("file name"));
        labels.add(new JLabel("description"));
        
        setLayout(new BorderLayout(5, 5));
        add(labels, WEST);
        add(fields, CENTER);
    }

    @Override
    public void updateFields()
    {
        log(AttachmentPanel.class, "updateFields()");
        fileNameField.setText(getEntity().getFileName());
        descriptionField.setText(getEntity().getDescription());
    }

    @Override
    public void updateEntity()
    {
        log(AttachmentPanel.class, "updateEntity()");
        getEntity().setFileName(fileNameField.getText());
        getEntity().setDescription(descriptionField.getText());
    }

    @Override
    public boolean doValidation()
    {
        log(AttachmentPanel.class, "doValidation()");
        String fname = fileNameField.getText();
        fname = fname == null ? "" : fname.trim();
        fileNameField.setText(fname);
        
        if(fname.length() == 0)
        {
            warning("file name is required");
            fileNameField.requestFocus();
            return false;
        }
        
        File file = new File(fname);
        
        if(!file.exists())
        {
            warning(fname + " does not exist");
            fileNameField.requestFocus();
            return false;
        }
        
        return true;
    }
}
