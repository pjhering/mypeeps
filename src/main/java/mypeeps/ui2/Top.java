package mypeeps.ui2;

import java.awt.Dimension;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static mypeeps.Utils.log;
import static mypeeps.Utils.popup;
import mypeeps.entity.Person;
import mypeeps.ui.PopupListener;

/**
 * The main user interface. Displays a list of people on the left and details on
 * the right.
 *
 * @author tinman
 */
public class Top
{

    public final JFrame FRAME;
    public final PersonDetail DETAIL;
    public final JList<Person> LIST;
    public final JMenuItem CREATEPERSON;
    public final PopupListener PERSONMENU;
    public final JSplitPane SPLIT;
    private final JPanel EMPTY;

    public Top()
    {
        log(Top.class, "Top()");
        LIST = new JList<>();
        JScrollPane listScroll = new JScrollPane(LIST);

        CREATEPERSON = new JMenuItem("create");
        PERSONMENU = popup("person", CREATEPERSON);
        LIST.addMouseListener(PERSONMENU);

        DETAIL = new PersonDetail();
        FRAME = new JFrame("myPeeps v1.0");

        SPLIT = new JSplitPane(HORIZONTAL_SPLIT);
        SPLIT.setBorder(createEmptyBorder(10,10,10,10));
        SPLIT.setLeftComponent(listScroll);
        SPLIT.setRightComponent(DETAIL);
        
        EMPTY = new JPanel();

        FRAME.setContentPane(SPLIT);
    }
    
    public void showDetailView()
    {
        SPLIT.setRightComponent(DETAIL);
    }
    
    public void showEmptyView()
    {
        SPLIT.setRightComponent(EMPTY);
    }

    public void show()
    {
        Dimension size = FRAME.getToolkit().getScreenSize();
        FRAME.setSize(size.width * 95 / 100, size.height * 90 / 100);
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);
    }
}
