package mypeeps;

import static java.util.Objects.requireNonNull;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static mypeeps.Utils.log;
import mypeeps.entity2.DAO;
import mypeeps.entity2.Person;
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
    }

    public void start()
    {
        log(App2.class, "start()");
    }
    
    private boolean confirm(String message)
    {
        int option = showConfirmDialog(TOP.FRAME, message, "confirm", OK_CANCEL_OPTION);
        return option == OK_OPTION;
    }

    private void refreshPeopleList(Person p)
    {
        log(App2.class, "refreshPeopleList(Person)");
    }

    private void doListSelectionChanged()
    {
        log(App2.class, "doListSelectionChanged()");
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
