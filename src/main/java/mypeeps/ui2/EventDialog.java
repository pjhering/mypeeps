package mypeeps.ui2;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static mypeeps.Utils.FMT;
import static mypeeps.Utils.popup;
import static mypeeps.Utils.required;
import static mypeeps.Utils.selectOnFocus;
import static mypeeps.Utils.toListModel;
import mypeeps.entity2.Event;
import mypeeps.entity2.File;

/**
 * Used to create or edit an event.
 *
 * @author tinman
 */
public class EventDialog
{

    private final Event EVENT;
    private final List<File> EVENTFILES;
    public final JTextField PERSON;
    public final JTextField TITLE;
    public final JFormattedTextField DATE;
    public final JTextField PLACE;
    public final JTextArea NOTES;
    public final JList<File> FILES;
    public final JMenuItem ADDFILES;
    public final JMenuItem REMOVEFILES;
    public final PopupListener FILESMENU;
    private final JButton SAVE;
    private final JButton CANCEL;
    private final JPanel CONTENT;
    private JDialog dialog;
    private boolean saved;

    public EventDialog(Event event, List<File> files)
    {
        this.EVENT = requireNonNull(event);
        this.EVENTFILES = requireNonNull(files);
        
        PERSON = new JTextField(20);
        PERSON.setEditable(false);
        TITLE = new JTextField(20);
        selectOnFocus(TITLE);
        DATE = new JFormattedTextField(FMT);
        PLACE = new JTextField(20);
        selectOnFocus(PLACE);
        
        JPanel fields = new JPanel(new GridLayout(4, 1, 5, 5));
        fields.add(PERSON);
        fields.add(TITLE);
        fields.add(DATE);
        fields.add(PLACE);
        
        JPanel labels = new JPanel(new GridLayout(4, 1, 5, 5));
        labels.add(new JLabel("person"));
        labels.add(new JLabel("title"));
        labels.add(new JLabel("date"));
        labels.add(new JLabel("place"));
        
        JPanel north = new JPanel(new BorderLayout(5, 5));
        north.add(labels, WEST);
        north.add(fields, CENTER);
        
        NOTES = new JTextArea(5, 20);
        JScrollPane notesScroll = new JScrollPane(NOTES);
        notesScroll.setBorder(createTitledBorder("notes"));
        
        FILES = new JList<>();
        JScrollPane filesScroll = new JScrollPane(FILES);
        filesScroll.setBorder(createTitledBorder("files"));
        
        JPanel center = new JPanel(new GridLayout(1, 2, 5, 5));
        center.add(notesScroll);
        center.add(filesScroll);
        
        SAVE = new JButton("save");
        SAVE.addActionListener(a0 -> doSaveAction());
        CANCEL = new JButton("cancel");
        CANCEL.addActionListener(a1 -> doCancelAction());
        
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(SAVE);
        buttons.add(CANCEL);
        
        JPanel south = new JPanel(new FlowLayout());
        south.add(buttons);
        
        CONTENT = new JPanel(new BorderLayout(5, 5));
        CONTENT.setBorder(createEmptyBorder(10,10,10,10));
        CONTENT.add(north, NORTH);
        CONTENT.add(center, CENTER);
        CONTENT.add(south, SOUTH);
        
        ADDFILES = new JMenuItem("add");
        REMOVEFILES = new JMenuItem("remove");
        FILESMENU = popup("files", ADDFILES, REMOVEFILES);
        FILES.addMouseListener(FILESMENU);
        
        PERSON.setText(EVENT.getPerson().toString());
        TITLE.setText(EVENT.getTitle());
        DATE.setValue(EVENT.getDate());
        PLACE.setText(EVENT.getPlace());
        NOTES.setText(EVENT.getNotes());
        FILES.setModel(toListModel(EVENTFILES));
    }

    public boolean open(Frame parent)
    {
        dialog = new JDialog(parent, "event", true);
        return open();
    }

    public boolean open(Dialog parent)
    {
        dialog = new JDialog(parent, "event", true);
        return open();
    }
    
    private boolean open()
    {
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setContentPane(CONTENT);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
        
        return saved;
    }

    private boolean doValidation()
    {
        return required("title", TITLE)
                && required("place", PLACE)
                && required("date", DATE);
    }
    
    public Event getUpdatedEvent()
    {
        EVENT.setDate((Date) DATE.getValue());
        EVENT.setNotes(NOTES.getText());
        EVENT.setPlace(PLACE.getText());
        EVENT.setTitle(TITLE.getText());
        
        return EVENT;
    }

    private void doSaveAction()
    {
        if(doValidation())
        {
            saved = true;
            close();
        }
    }

    private void doCancelAction()
    {
        saved = false;
        close();
    }

    private void close()
    {
        dialog.setVisible(false);
        dialog.dispose();
    }
}
