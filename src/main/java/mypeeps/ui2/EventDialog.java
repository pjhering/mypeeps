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
import static mypeeps.Utils.log;
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

    public final JDialog DIALOG;
    public final Event EVENT;
    public final List<File> EVENTFILES;
    public final JTextField PERSON;
    public final JTextField TITLE;
    public final JFormattedTextField DATE;
    public final JTextField PLACE;
    public final JTextArea NOTES;
    public final JList<File> FILES;
    public final JMenuItem ADDFILES;
//    public final JMenuItem EDITFILE;
    public final JMenuItem REMOVEFILES;
    public final PopupListener FILESMENU;
    private final JButton SAVE;
    private final JButton CANCEL;
    private final JPanel CONTENT;
    private boolean saved;

    public EventDialog(Frame parent, Event event, List<File> files)
    {
        this(new JDialog(parent, "event", true), event, files);
        
        log(EventDialog.class, "EventDialog(Frame,  Event, List<File>)");
    }
    
    public EventDialog(Dialog parent, Event event, List<File> files)
    {
        this(new JDialog(parent, "event", true), event, files);
        
        log(EventDialog.class, "EventDialog(Dialog,  Event, List<File>)");
    }
    
    private EventDialog(JDialog dialog, Event event, List<File> files)
    {
        log(EventDialog.class, "EventDialog(JDialog, Event, List<File>)");
        
        this.DIALOG = requireNonNull(dialog);
        
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
        
        boolean enabled = EVENT.getId() != null;
        ADDFILES = new JMenuItem("add");
        ADDFILES.setEnabled(enabled);
//        EDITFILE = new JMenuItem("edit");
//        EDITFILE.setEnabled(enabled);
        REMOVEFILES = new JMenuItem("remove");
        REMOVEFILES.setEnabled(enabled);
//        FILESMENU = popup("files", ADDFILES, EDITFILE, REMOVEFILES);
        FILESMENU = popup("files", ADDFILES, REMOVEFILES);
        FILES.addMouseListener(FILESMENU);
        
        PERSON.setText(EVENT.getPerson().toString());
        TITLE.setText(EVENT.getTitle());
        DATE.setValue(EVENT.getDate());
        PLACE.setText(EVENT.getPlace());
        NOTES.setText(EVENT.getNotes());
        FILES.setModel(toListModel(EVENTFILES));
    }

    public boolean open()
    {
        log(EventDialog.class, "open()");
        
        DIALOG.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        DIALOG.setContentPane(CONTENT);
        DIALOG.pack();
        DIALOG.setLocationRelativeTo(DIALOG.getParent());
        DIALOG.setVisible(true);
        
        return saved;
    }

    private boolean doValidation()
    {
        log(EventDialog.class, "doValidation()");
        
        return required("title", TITLE)
                && required("place", PLACE)
                && required("date", DATE);
    }
    
    public Event getUpdatedEvent()
    {
        log(EventDialog.class, "getUpdateEvent()");
        
        EVENT.setDate((Date) DATE.getValue());
        EVENT.setNotes(NOTES.getText());
        EVENT.setPlace(PLACE.getText());
        EVENT.setTitle(TITLE.getText());
        
        return EVENT;
    }

    private void doSaveAction()
    {
        log(EventDialog.class, "doSaveAction()");
        
        if(doValidation())
        {
            saved = true;
            close();
        }
    }

    private void doCancelAction()
    {
        log(EventDialog.class, "doCancelAction()");
        
        saved = false;
        close();
    }

    private void close()
    {
        log(EventDialog.class, "close()");
        
        DIALOG.setVisible(false);
        DIALOG.dispose();
    }
}
