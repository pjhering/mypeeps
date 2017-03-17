package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.WEST;
import java.awt.GridLayout;
import java.util.Date;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static mypeeps.Utils.FMT;
import static mypeeps.Utils.log;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity.Event;

public class EventPanel extends ValidPanel<Event>
{

    private JTextField titleField;
    private JFormattedTextField dateField;
    private JTextField placeField;
    private JTextArea notesField;

    public EventPanel(Event entity)
    {
        super(entity);
        log(EventPanel.class, "EventPanel(Event)");
        init();
        updateFields();
    }

    private void init()
    {
        log(EventPanel.class, "init()");
        titleField = new JTextField(20);
        selectOnFocus(titleField);
        dateField = new JFormattedTextField(FMT);
        selectOnFocus(dateField);
        placeField = new JTextField(20);
        placeField.setEditable(false);
        JPanel fields = new JPanel(new GridLayout(3, 1, 5, 5));
        fields.add(titleField);
        fields.add(dateField);
        fields.add(placeField);

        JPanel labels = new JPanel(new GridLayout(3, 1, 5, 5));
        labels.add(new JLabel("title"));
        labels.add(new JLabel("date"));
        labels.add(new JLabel("place"));

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
        log(EventPanel.class, "updateFields()");
        Event e = getEntity();
        titleField.setText(e.getTitle());
        dateField.setValue(e.getDate());
        placeField.setText(e.getPlace().toString());
        notesField.setText(e.getNotes());
    }

    @Override
    public void updateEntity()
    {
        log(EventPanel.class, "updateEntity()");
        Event e = getEntity();
        e.setTitle(titleField.getText());
        e.setDate((Date) dateField.getValue());
        e.setNotes(notesField.getText());
    }

    @Override
    public boolean doValidation()
    {
        log(EventPanel.class, "doValidation()");
        return required("title", titleField) && required("date", dateField);
    }
}
