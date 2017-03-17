package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static mypeeps.Utils.log;
import static mypeeps.Utils.popup;
import static mypeeps.Utils.toListModel;
import mypeeps.entity.DAO;
import mypeeps.entity.Person;
import mypeeps.ui.PersonPanel;
import mypeeps.ui.Top;
import mypeeps.ui.ValidPanelDialog;

public class App
{

    private final DAO DB;
    private final Top TOP;

    public App(DAO dao)
    {
        log(App.class, "App(DAO)");
        this.DB = requireNonNull(dao);
        this.TOP = new Top();
        TOP.FRAME.setDefaultCloseOperation(EXIT_ON_CLOSE);
        TOP.FRAME.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                DB.shutDown();
                exit(0);
            }
        });
        TOP.PEOPLE.addListSelectionListener(a0 -> doPeopleListSelectionChanged());
        TOP.PEOPLE.addMouseListener(popup("people", TOP.CREATE));
        TOP.UPDATE.addActionListener(a1 -> doUpdatePersonAction());
        TOP.DELETE.addActionListener(a2 -> doDeletePersonAction());
        TOP.CREATE.addActionListener(a3 -> doCreatePersonAction());
    }

    public void start()
    {
        log(App.class, "start()");
        refreshPeopleList();
        TOP.show();
    }

    private void refreshPeopleList()
    {
        log(App.class, "refreshPeopleList()");
        List<Person> all = DB.readPeople();
        Person p = TOP.PEOPLE.getSelectedValue();
        TOP.PEOPLE.setModel(toListModel(all));
        if(p != null && all.contains(p))
        {
            TOP.PEOPLE.setSelectedValue(p, true);
        }
        else
        {
            TOP.PEOPLE.setSelectedIndex(0);
        }
    }

    private void doPeopleListSelectionChanged()
    {
        log(App.class, "doPeopleListSelectionChanged()");
        Person p = TOP.PEOPLE.getSelectedValue();
        
        if(p == null)
        {
            return;
        }
        
        TOP.setRightPane(new PersonPanel(p));
    }

    private void doUpdatePersonAction()
    {
        log(App.class, "doUpdatePersonAction()");
    }

    private void doDeletePersonAction()
    {
        log(App.class, "doDeletePersonAction()");
    }

    private void doCreatePersonAction()
    {
        log(App.class, "doCreatePersonAction()");
        ValidPanelDialog<PersonPanel> dialog = new ValidPanelDialog<>("new person", new PersonPanel(new Person()));
        PersonPanel panel = dialog.open(TOP.FRAME);
        
        if(panel != null)
        {
            panel.updateEntity();
            Person p = DB.create(panel.getEntity());
            refreshPeopleList();
            TOP.PEOPLE.setSelectedValue(p, true);
        }
    }
}
