package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Dimension;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEFT;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static mypeeps.Utils.log;
import mypeeps.entity.Attachment;
import mypeeps.entity.Event;
import mypeeps.entity.Person;

public class Top
{

    public final JFrame FRAME;
    public final JSplitPane SPLIT;
    public final JList<Person> PEOPLE;
    public final JMenuItem CREATE;
    public final JButton UPDATE, DELETE;
    private final JScrollPane SCROLL;
    private final JList<Person> PARENTS, CHILDREN;
    private final JList<Event> EVENTS;
    private final JList<Attachment> ATTACHMENTS;
    private final JPanel BUTTONS, FLOW, LISTS;
    private ValidPanel<Person> right;
    private final JPanel EMPTY;

    public Top()
    {
        log(Top.class, "Top()");
        this.FRAME = new JFrame("MyPeeps");
        this.SPLIT = new JSplitPane(HORIZONTAL_SPLIT);
        this.PEOPLE = new JList<>();
        this.SCROLL = new JScrollPane(PEOPLE);

        SPLIT.setBorder(createEmptyBorder(10, 10, 10, 10));
        SPLIT.setLeftComponent(SCROLL);
        SPLIT.setRightComponent(new JPanel());
        SPLIT.setDividerLocation(0.5);
        FRAME.setContentPane(SPLIT);
        EMPTY = new JPanel();

        PARENTS = new JList<>();
        CHILDREN = new JList<>();
        EVENTS = new JList<>();
        ATTACHMENTS = new JList<>();

        JScrollPane scroll0 = new JScrollPane(PARENTS);
        scroll0.setBorder(createTitledBorder("parents"));
        JScrollPane scroll1 = new JScrollPane(CHILDREN);
        scroll1.setBorder(createTitledBorder("children"));
        JScrollPane scroll2 = new JScrollPane(EVENTS);
        scroll2.setBorder(createTitledBorder("events"));
        JScrollPane scroll3 = new JScrollPane(ATTACHMENTS);
        scroll3.setBorder(createTitledBorder("files"));
        LISTS = new JPanel(new GridLayout(1, 4, 5, 5));
        LISTS.add(scroll0);
        LISTS.add(scroll1);
        LISTS.add(scroll2);
        LISTS.add(scroll3);

        CREATE = new JMenuItem("create");
        UPDATE = new JButton("update");
        DELETE = new JButton("delete");

        BUTTONS = new JPanel(new GridLayout(1, 2, 5, 5));
        BUTTONS.add(UPDATE);
        BUTTONS.add(DELETE);
        FLOW = new JPanel(new FlowLayout(LEFT));
        FLOW.add(BUTTONS);
    }

    public void setRightPane(ValidPanel<Person> right)
    {
        log(Top.class, "setRightPane(ValidPanel<Person>)");
        this.right = right;

        if (right == null)
        {
            SPLIT.setRightComponent(EMPTY);
        }
        else
        {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(right, NORTH);
            panel.add(LISTS, CENTER);
            panel.add(FLOW, SOUTH);
            SPLIT.setRightComponent(panel);
        }
    }

    public ValidPanel<Person> getRightPane()
    {
        log(Top.class, "getRightPane()");
        return right;
    }

    public void show()
    {
        log(Top.class, "show()");
        Dimension size = FRAME.getToolkit().getScreenSize();
        FRAME.setSize(size.width * 9 / 10, size.height * 9 / 10);
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);
    }
}
