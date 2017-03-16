package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.WEST;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static mypeeps.Utils.log;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Place;

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
        JScrollPane scroll = new JScrollPane(notesField);
        scroll.setBorder(createTitledBorder("notes"));
        
        setLayout(new BorderLayout(5, 5));
        add(north, NORTH);
        add(scroll, CENTER);
    }

    @Override
    public void updateFields()
    {
        log(PlacePanel.class, "updateFields()");
        nameField.setText(getEntity().getName());
        notesField.setText(getEntity().getNotes());
    }

    @Override
    public void updateEntity()
    {
        log(PlacePanel.class, "updateEntity()");
        getEntity().setName(nameField.getText());
        getEntity().setNotes(notesField.getText());
    }

    @Override
    public boolean doValidation()
    {
        log(PlacePanel.class, "doValidation()");
        return required("name", nameField);
    }
}
