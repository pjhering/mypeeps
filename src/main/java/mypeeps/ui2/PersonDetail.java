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
import static mypeeps.Utils.log;
import static mypeeps.Utils.popup;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Attachment;
import mypeeps.entity.Event;
import mypeeps.entity.Person;
import mypeeps.ui.PopupListener;

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
    public final JMenuItem ADDPARENTS;
    public final JMenuItem REMOVEPARENTS;
    public final PopupListener PARENTSMENU;
    
    public final JList<Person> CHILDREN;
    public final JMenuItem ADDCHILDREN;
    public final JMenuItem REMOVECHILDREN;
    public final PopupListener CHILDRENMENU;

    public final JList<Event> EVENTS;
    public final JMenuItem ADDEVENT;
    public final JMenuItem EDITEVENT;
    public final JMenuItem REMOVEEVENT;
    public final JMenuItem DELETEEVENT;
    public final PopupListener EVENTMENU;

    public final JList<Attachment> ATTACHMENTS;
    public final JMenuItem ADDATTACHMENT;
    public final JMenuItem EDITATTACHMENT;
    public final JMenuItem REMOVEATTACHMENT;
    public final JMenuItem DELETEATTACHMENT;
    public final PopupListener ATTACHMENTSMENU;
    
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
        
        GENDER = new JComboBox<>(new String[]{"", "female", "male", "other"});
        GENDER.setBorder(createTitledBorder("gender"));
        
        NOTES = new JTextArea(5, 20);
        JScrollPane notesScroll = new JScrollPane(NOTES);
        notesScroll.setBorder(createTitledBorder("notes"));
        
        UPDATE = new JButton("update");
        DELETE = new JButton("delete");
        
        PARENTS = new JList<>();
        JScrollPane parentsScroll = new JScrollPane(PARENTS);
        parentsScroll.setBorder(createTitledBorder("parents"));
        ADDPARENTS = new JMenuItem("add");
        REMOVEPARENTS = new JMenuItem("remove");
        PARENTSMENU = popup("parents", ADDPARENTS, REMOVEPARENTS);
        PARENTS.addMouseListener(PARENTSMENU);
        
        CHILDREN = new JList<>();
        JScrollPane childrenScroll = new JScrollPane(CHILDREN);
        childrenScroll.setBorder(createTitledBorder("children"));
        ADDCHILDREN = new JMenuItem("add");
        REMOVECHILDREN = new JMenuItem("remove");
        CHILDRENMENU = popup("children", ADDCHILDREN, REMOVECHILDREN);
        CHILDREN.addMouseListener(CHILDRENMENU);
        
        EVENTS = new JList<>();
        JScrollPane eventsScroll = new JScrollPane(EVENTS);
        eventsScroll.setBorder(createTitledBorder("events"));
        ADDEVENT = new JMenuItem("add");
        EDITEVENT = new JMenuItem("edit");
        REMOVEEVENT = new JMenuItem("remove");
        DELETEEVENT = new JMenuItem("delete");
        EVENTMENU = popup("events", ADDEVENT, EDITEVENT, REMOVEEVENT, DELETEEVENT);
        EVENTS.addMouseListener(EVENTMENU);
        
        ATTACHMENTS = new JList<>();
        JScrollPane attachmentsScroll = new JScrollPane(ATTACHMENTS);
        attachmentsScroll.setBorder(createTitledBorder("files"));
        ADDATTACHMENT = new JMenuItem("add");
        EDITATTACHMENT = new JMenuItem("edit");
        REMOVEATTACHMENT = new JMenuItem("remove");
        DELETEATTACHMENT = new JMenuItem("delete");
        ATTACHMENTSMENU = popup("files", ADDATTACHMENT, EDITATTACHMENT, REMOVEATTACHMENT, DELETEATTACHMENT);
        ATTACHMENTS.addMouseListener(ATTACHMENTSMENU);
        
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
    
    public Person getPerson()
    {
        log(PersonDetail.class, "getPerson()");
        
        return person;
    }
    
    public void setPerson(Person person)
    {
        log(PersonDetail.class, "setPerson(Person)");
        
        this.person = requireNonNull(person);
    }
}