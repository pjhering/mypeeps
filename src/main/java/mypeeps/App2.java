package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static mypeeps.Utils.log;
import static mypeeps.Utils.toListModel;
import mypeeps.entity2.DAO;
import mypeeps.entity2.DAOException;
import mypeeps.entity2.Person;
import mypeeps.ui2.PersonDialog;
import mypeeps.ui2.Top;

public class App2
{

    private final DAO DB;
    private final Top TOP;

    public App2(DAO dao)
    {
        log(App2.class, "App2(DAO)");

        DB = requireNonNull(dao);
        TOP = new Top();
        TOP.FRAME.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                TOP.FRAME.setVisible(false);
                TOP.FRAME.dispose();
                try
                {
                    DB.shutdown();
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
                exit(0);
            }
        });
        TOP.LIST.addListSelectionListener(a0 -> doListSelectionChanged());
        TOP.CREATEPERSON.addActionListener(a1 -> doCreatePerson());
    }

    public void start()
    {
        log(App2.class, "start()");
        
        refreshPeopleList(null);
        TOP.show();
    }
    
    private void info(String message)
    {
        log(App2.class, "info(String)");
        
        showMessageDialog(TOP.FRAME, message, "info", INFORMATION_MESSAGE);
    }
    
    private void error(Throwable ex)
    {
        log(App2.class, "error(String)");
        
        showMessageDialog(TOP.FRAME, ex.getMessage(), "error", ERROR_MESSAGE);
    }
    
    private void warning(String message)
    {
        log(App2.class, "warning(String)");
        
        showMessageDialog(TOP.FRAME, message, "warning", WARNING_MESSAGE);
    }
    
    private boolean confirm(String message)
    {
        log(App2.class, "confirm(String)");
        
        int option = showConfirmDialog(TOP.FRAME, message, "confirm", OK_CANCEL_OPTION);
        return option == OK_OPTION;
    }

    private void refreshPeopleList(Person p)
    {
        log(App2.class, "refreshPeopleList(Person)");
        
        try
        {
            Set<Person> all = DB.findAllPeople();
            TOP.LIST.setModel(toListModel(all));
            if(p != null)
            {
                TOP.LIST.setSelectedValue(p, true);
            }
            else
            {
                TOP.LIST.setSelectedIndex(0);
            }
        }
        catch(DAOException ex)
        {
            error(ex);
        }
    }

    private void doListSelectionChanged()
    {
        log(App2.class, "doListSelectionChanged()");
        
        Person p = TOP.LIST.getSelectedValue();
        
        if(p != null)
        {
            TOP.DETAIL.setPerson(p);
        }
    }

    private void doUpdatePerson()
    {
        log(App2.class, "doUpdatePerson()");
    }

    private void doDeletePerson()
    {
        log(App2.class, "doDeletePerson()");
    }

    private void doCreatePerson()
    {
        log(App2.class, "doCreatePerson()");
        
        PersonDialog dialog = new PersonDialog(new Person(null, null, null, null, null));
        Person p = dialog.open(TOP.FRAME);
        
        if(p != null)
        {
            try
            {
                Person person = DB.createPerson(p.getGivenName(), p.getFamilyName(), p.getGender(), p.getNotes());
                refreshPeopleList(person);
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doAddParent()
    {
        log(App2.class, "doAddParent()");
    }

    private void doRemoveParent()
    {
        log(App2.class, "doRemoveParent()");
    }

    private void doAddChild()
    {
        log(App2.class, "doAddChild()");
    }

    private void doRemoveChild()
    {
        log(App2.class, "doRemoveChild()");
    }

    private void doAddEvent()
    {
        log(App2.class, "doAddEvent()");
    }

    private void doEditEvent()
    {
        log(App2.class, "doEditEvent()");
    }

    private void doDeleteEvent()
    {
        log(App2.class, "doDeleteEvent()");
    }
}
