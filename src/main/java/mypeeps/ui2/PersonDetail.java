package mypeeps.ui2;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEFT;
import java.awt.GridLayout;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static mypeeps.Utils.log;
import static mypeeps.Utils.popup;
import static mypeeps.Utils.required;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity2.File;
import mypeeps.entity2.Event;
import mypeeps.entity2.Person;

/**
 * Used to display a Person in the Top frame
 *
 * @author tinman
 */
public class PersonDetail extends JPanel
{

    public final JTextField GIVENNAME;
    public final JTextField FAMILYNAME;
    public final JComboBox<String> GENDER;
    public final JTextArea NOTES;
    public final JButton UPDATE;
    public final JButton DELETE;

    public final JList<Person> PARENTS;
    public final JMenuItem ADDPARENT;
    public final JMenuItem REMOVEPARENT;
    public final PopupListener PARENTSMENU;

    public final JList<Person> CHILDREN;
    public final JMenuItem ADDCHILD;
    public final JMenuItem REMOVECHILD;
    public final PopupListener CHILDRENMENU;

    public final JList<Event> EVENTS;
    public final JMenuItem ADDEVENT;
    public final JMenuItem EDITEVENT;
    public final JMenuItem DELETEEVENT;
    public final PopupListener EVENTMENU;

    public final JList<File> FILES;
    public final JMenuItem ADDFILE;
    public final JMenuItem EDITFILE;
    public final JMenuItem DELETEFILE;
    public final PopupListener FILESMENU;

    private Person person;

    public PersonDetail()
    {
        log(PersonDetail.class, "PersonDetail()");

        GIVENNAME = new JTextField(20);
        GIVENNAME.setBorder(createTitledBorder("given name"));
        selectOnFocus(GIVENNAME);

        FAMILYNAME = new JTextField(20);
        FAMILYNAME.setBorder(createTitledBorder("family name"));
        selectOnFocus(FAMILYNAME);

        GENDER = new JComboBox<>(new String[]
        {
            "", "female", "male", "other"
        });
        GENDER.setBorder(createTitledBorder("gender"));

        NOTES = new JTextArea(5, 20);
        JScrollPane notesScroll = new JScrollPane(NOTES);
        notesScroll.setBorder(createTitledBorder("notes"));

        UPDATE = new JButton("update");
        DELETE = new JButton("delete");

        PARENTS = new JList<>();
        PARENTS.setSelectionMode(SINGLE_SELECTION);
        JScrollPane parentsScroll = new JScrollPane(PARENTS);
        parentsScroll.setBorder(createTitledBorder("parents"));
        ADDPARENT = new JMenuItem("add");
        REMOVEPARENT = new JMenuItem("remove");
        PARENTSMENU = popup("parents", ADDPARENT, REMOVEPARENT);
        PARENTS.addMouseListener(PARENTSMENU);

        CHILDREN = new JList<>();
        CHILDREN.setSelectionMode(SINGLE_SELECTION);
        JScrollPane childrenScroll = new JScrollPane(CHILDREN);
        childrenScroll.setBorder(createTitledBorder("children"));
        ADDCHILD = new JMenuItem("add");
        REMOVECHILD = new JMenuItem("remove");
        CHILDRENMENU = popup("children", ADDCHILD, REMOVECHILD);
        CHILDREN.addMouseListener(CHILDRENMENU);

        EVENTS = new JList<>();
        EVENTS.setSelectionMode(SINGLE_SELECTION);
        JScrollPane eventsScroll = new JScrollPane(EVENTS);
        eventsScroll.setBorder(createTitledBorder("events"));
        ADDEVENT = new JMenuItem("add");
        EDITEVENT = new JMenuItem("edit");
        DELETEEVENT = new JMenuItem("delete");
        EVENTMENU = popup("events", ADDEVENT, EDITEVENT, DELETEEVENT);
        EVENTS.addMouseListener(EVENTMENU);

        FILES = new JList<>();
        FILES.setSelectionMode(SINGLE_SELECTION);
        JScrollPane attachmentsScroll = new JScrollPane(FILES);
        attachmentsScroll.setBorder(createTitledBorder("files"));
        ADDFILE = new JMenuItem("add");
        EDITFILE = new JMenuItem("edit");
        DELETEFILE = new JMenuItem("delete");
        FILESMENU = popup("files", ADDFILE, EDITFILE, DELETEFILE);
        FILES.addMouseListener(FILESMENU);

        JPanel fields = new JPanel(new GridLayout(1, 3, 5, 5));
        fields.add(GIVENNAME);
        fields.add(FAMILYNAME);
        fields.add(GENDER);

        JPanel north = new JPanel(new BorderLayout(5, 5));
        north.add(fields, NORTH);
        north.add(notesScroll, CENTER);

        JPanel center = new JPanel(new GridLayout(1, 4, 5, 5));
        center.add(parentsScroll);
        center.add(childrenScroll);
        center.add(eventsScroll);
        center.add(attachmentsScroll);

        JPanel south = new JPanel(new FlowLayout(LEFT));
        south.add(UPDATE);
        south.add(DELETE);

        init(north, center, south);
    }

    private void init(JPanel north, JPanel center, JPanel south)
    {
        log(PersonDetail.class, "init(JPanel, JPanel, JPanel, JPanel)");

        setBorder(createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout(5, 5));
        add(north, NORTH);
        add(center, CENTER);
        add(south, SOUTH);
    }

    public boolean doValidation()
    {
        return required("given name", GIVENNAME) && required("family name", FAMILYNAME);
    }

    public void updatePerson()
    {
        person.setGivenName(GIVENNAME.getText());
        person.setFamilyName(FAMILYNAME.getText());
        person.setGender((String) GENDER.getSelectedItem());
    }

    public Person getPerson()
    {
        log(PersonDetail.class, "getPerson()");

        return person;
    }

    public void setPerson(Person person)
    {
        log(PersonDetail.class, "setPerson(Person)");

        this.person = requireNonNull(person);
        this.GIVENNAME.setText(person.getGivenName());
        this.FAMILYNAME.setText(person.getFamilyName());
        this.GENDER.setSelectedItem(person.getGender());
        this.NOTES.setText(person.getNotes());
    }
}
