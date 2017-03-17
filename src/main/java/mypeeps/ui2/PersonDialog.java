package mypeeps.ui2;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.WEST;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static mypeeps.Utils.buttons;
import static mypeeps.Utils.fields;
import static mypeeps.Utils.labels;
import static mypeeps.Utils.log;
import static mypeeps.Utils.required;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Person;

/**
 * Used to create or edit a Person
 *
 * @author tinman
 */
public class PersonDialog
{

    private final Person PERSON;
    private final JTextField GIVENNAME;
    private final JTextField FAMILYNAME;
    private final JComboBox<String> GENDER;
    private final JButton SAVE;
    private final JButton CANCEL;
    private final JPanel CONTENT;
    private JDialog dialog;
    private boolean saved;

    public PersonDialog(Person person)
    {
        log(PersonDialog.class, "PersonDialog(Person)");

        this.PERSON = requireNonNull(person);
        GIVENNAME = new JTextField(20);
        selectOnFocus(GIVENNAME);
        FAMILYNAME = new JTextField(20);
        selectOnFocus(FAMILYNAME);
        GENDER = new JComboBox<>(new String[]
        {
            "", "female", "male", "other"
        });

        JPanel north = new JPanel(new BorderLayout(5, 5));
        north.add(labels("given name", "family name", "gender"), WEST);
        north.add(fields(GIVENNAME, FAMILYNAME, GENDER), CENTER);

        SAVE = new JButton("save");
        SAVE.addActionListener(a0 -> doSave());
        CANCEL = new JButton("cancel");
        CANCEL.addActionListener(a1 -> doCancel());

        CONTENT = new JPanel(new BorderLayout(5, 5));
        CONTENT.add(north, NORTH);
        CONTENT.add(buttons(FlowLayout.CENTER, SAVE, CANCEL), CENTER);
    }

    public Person open(Frame parent)
    {
        log(PersonDialog.class, "open(Frame)");

        dialog = new JDialog(parent, "person", true);
        return open();
    }

    public Person open(Dialog parent)
    {
        log(PersonDialog.class, "open(Dialog)");

        dialog = new JDialog(parent, "person", true);
        return open();
    }

    private Person open()
    {
        log(PersonDialog.class, "open()");

        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setContentPane(CONTENT);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);

        return saved ? PERSON : null;
    }

    private boolean isValid()
    {
        log(PersonDialog.class, "isValid()");

        return required("given name", GIVENNAME) && required("family name", FAMILYNAME);
    }

    private void updatePerson()
    {
        log(PersonDialog.class, "updatePerson()");

        PERSON.setGivenName(GIVENNAME.getText());
        PERSON.setFamilyName(FAMILYNAME.getText());
        PERSON.setGender((String) GENDER.getSelectedItem());
    }

    private void close()
    {
        log(PersonDialog.class, "close()");

        dialog.setVisible(false);
        dialog.dispose();
    }

    private void doSave()
    {
        log(PersonDialog.class, "doSave()");

        if (isValid())
        {
            saved = true;
            updatePerson();
            close();
        }
    }

    private void doCancel()
    {
        log(PersonDialog.class, "doCancel()");

        saved = false;
        close();
    }
}
