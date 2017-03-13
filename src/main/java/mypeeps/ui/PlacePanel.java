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

public class PlacePanel extends ValidPanel<Place>
{
    
    public JTextField nameField;
    public JTextArea notesField;

    public PlacePanel(Place entity)
    {
        super(entity);
        log(PlacePanel.class, "PlacePanel(Place)");
        init();
        updateFields();
    }
    
    private void init()
    {
        log(PlacePanel.class, "init()");
        nameField = new JTextField(20);
        selectOnFocus(nameField);
        JPanel fields = new JPanel(new GridLayout(1, 1, 5, 5));
        fields.add(nameField);
        
        JPanel labels = new JPanel(new GridLayout(2, 1, 5, 5));
        labels.add(new JLabel("name"));
        
        JPanel north = new JPanel(new BorderLayout(5, 5));
        north.add(labels, WEST);
        north.add(fields, CENTER);
        
        notesField = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(notes);
        scroll.setBorder(createTitledBorder("notes"));
        
        setLayout(new BorderLayout(5, 5));
        add(north, NORTH);
        add(scroll, CENTER);
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
