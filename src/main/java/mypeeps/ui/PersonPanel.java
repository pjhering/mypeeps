package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.WEST;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static mypeeps.Utils.log;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Person;

public class PersonPanel extends ValidPanel<Person>
{

    private JTextField givenNameField;
    private JTextField familyNameField;
    private JComboBox<String> genderField;
    private JTextArea notesField;
    
    public PersonPanel(Person entity)
    {
        super(entity);
        log(PersonPanel.class, "PersonPanel(Person)");
        init();
        updateFields();
    }
    
    private void init()
    {
        log(PersonPanel.class, "init()");
        givenNameField = new JTextField(20);
        selectOnFocus(givenNameField);
        familyNameField = new JTextField(20);
        selectOnFocus(familyNameField);
        genderField = new JComboBox<>(new String[]{"", "female", "male"});
        JPanel fields = new JPanel(new GridLayout(3, 1, 5, 5));
        fields.add(givenNameField);
        fields.add(familyNameField);
        fields.add(genderField);
        
        JPanel labels = new JPanel(new GridLayout(3, 1, 5, 5));
        labels.add(new JLabel("given name"));
        labels.add(new JLabel("family name"));
        labels.add(new JLabel("gender"));
        
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
        log(PersonPanel.class, "updateFields()");
        Person p = getEntity();
        givenNameField.setText(p.getGivenName());
        familyNameField.setText(p.getFamilyName());
        genderField.setSelectedItem(p.getGender());
        notesField.setText(p.getNotes());
    }

    @Override
    public void updateEntity()
    {
        log(PersonPanel.class, "updateEntity()");
        Person p = getEntity();
        p.setGivenName(givenNameField.getText());
        p.setFamilyName(familyNameField.getText());
        p.setGender((String) genderField.getSelectedItem());
        p.setNotes(notesField.getText());
    }

    @Override
    public boolean doValidation()
    {
        log(PersonPanel.class, "doValidation()");
        
        return required("given name", givenNameField) &&
                required("family name", familyNameField);
    }
}
