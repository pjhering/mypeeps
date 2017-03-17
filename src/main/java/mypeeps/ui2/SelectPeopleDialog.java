package mypeeps.ui2;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static mypeeps.Utils.toListModel;
import mypeeps.entity.Person;

/**
 * Used to select one or more people (children or parents)
 *
 * @author tinman
 */
public class SelectPeopleDialog
{

    private final JList<Person> LIST;
    private final JButton SELECT;
    private final JButton CANCEL;
    private final JPanel CONTENT;
    private final String TITLE;
    private JDialog dialog;
    private boolean selected;

    public SelectPeopleDialog(String title, List<Person> people)
    {
        TITLE = requireNonNull(title);
        LIST = new JList(toListModel(people));
        JScrollPane scroll = new JScrollPane(LIST);

        SELECT = new JButton("select");
        SELECT.addActionListener(a1 -> close(true));
        CANCEL = new JButton("cancel");
        CANCEL.addActionListener(a2 -> close(false));
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(SELECT);
        buttons.add(CANCEL);
        JPanel south = new JPanel(new FlowLayout());
        south.add(buttons);

        CONTENT = new JPanel(new BorderLayout(5, 5));
        CONTENT.setBorder(createEmptyBorder(10, 10, 10, 10));
        CONTENT.add(scroll, CENTER);
        CONTENT.add(south, SOUTH);
    }

    public List<Person> open(Frame parent)
    {
        dialog = new JDialog(parent, "select people", true);
        return open();
    }

    public List<Person> open(Dialog parent)
    {
        dialog = new JDialog(parent, "select people", true);
        return open();
    }

    private List<Person> open()
    {
        dialog.setContentPane(CONTENT);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);

        return selected ? LIST.getSelectedValuesList() : null;
    }
    
    private void close(boolean value)
    {
        selected = value;
        dialog.setVisible(false);
        dialog.dispose();
    }
}
