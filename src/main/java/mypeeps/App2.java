package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import static java.util.Objects.requireNonNull;
import static mypeeps.Utils.toListModel;
import mypeeps.entity.DAO;
import mypeeps.entity.Person;
import mypeeps.ui2.Top;

public class App2
{
    
    private final DAO DB;
    private final Top TOP;
    
    public App2(DAO dao)
    {
        DB = requireNonNull(dao);
        TOP = new Top();
        TOP.LIST.addListSelectionListener(a0 -> doListSelectionChanged());
        TOP.FRAME.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                TOP.FRAME.setVisible(false);
                TOP.FRAME.dispose();
                DB.shutDown();
                exit(0);
            }
        });
    }
    
    public void start()
    {
        TOP.LIST.setModel(toListModel(DB.readPeople()));
        TOP.show();
    }

    private void doListSelectionChanged()
    {
        Person person = TOP.LIST.getSelectedValue();
        
        if(person != null)
        {
            TOP.DETAIL.setPerson(person);
        }
    }
}
