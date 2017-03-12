package mypeeps.ui;

import java.awt.Dimension;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static mypeeps.Utils.log;
import mypeeps.entity.Person;

public class Top
{

    public final JFrame FRAME;
    public final JSplitPane SPLIT;
    public final JList<Person> PEOPLE;
    private final JScrollPane SCROLL;

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
