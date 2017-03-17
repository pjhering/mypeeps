package mypeeps.ui2;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static mypeeps.Utils.log;
import mypeeps.entity.Person;

/**
 * The main user interface.
 * Displays a list of people on the left and details on the right.
 * 
 * @author tinman
 */
public class Top
{
    
    public final JFrame FRAME;
    public final PersonDetail DETAIL;
    public final JList<Person> LIST;
    
    public Top()
    {
        log(Top.class, "Top()");
        LIST = new JList<>();
        JScrollPane listScroll = new JScrollPane(LIST);
        
        DETAIL = new PersonDetail();
        FRAME = new JFrame("myPeeps v1.0");
        
        JSplitPane split = new JSplitPane(HORIZONTAL_SPLIT);
        split.setLeftComponent(listScroll);
        split.setRightComponent(DETAIL);
        
        FRAME.setContentPane(split);
    }
}
