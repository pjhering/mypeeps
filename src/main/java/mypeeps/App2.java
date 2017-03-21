package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;
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
import mypeeps.ui2.SelectPeopleDialog;
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
        TOP.DETAIL.UPDATE.addActionListener(a2 -> doUpdateDetail());
        TOP.DETAIL.DELETE.addActionListener(a3 -> doDeleteDetail());
        TOP.DETAIL.ADDPARENT.addActionListener(a4 -> doAddParent());
        TOP.DETAIL.REMOVEPARENT.addActionListener(a5 -> doRemoveParent());
        TOP.DETAIL.ADDCHILD.addActionListener(a6 -> doAddChid());
        TOP.DETAIL.REMOVECHILD.addActionListener(a7 -> doRemoveChild());
    }

    public void start()
    {
        log(App2.class, "start()");

        refreshPeopleList(null);
        TOP.SPLIT.setDividerLocation(0.5);
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
                if(TOP.LIST.getModel().getSize() == 0)
                {
                    TOP.showEmptyView();
                }
                else
                {
                    TOP.LIST.setSelectedIndex(0);
                }
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
            TOP.showDetailView();
            TOP.DETAIL.setPerson(p);
            //TODO: update parents, children, events, files
            try
            {
                List<Person> parents = new ArrayList<>(DB.findParentsOf(p));
                TOP.DETAIL.PARENTS.setModel(toListModel(parents));

                List<Person> children = new ArrayList<>(DB.findChildrenOf(p));
                TOP.DETAIL.CHILDREN.setModel(toListModel(children));

            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
        else
        {
            TOP.showEmptyView();
        }
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

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            try
            {
                Set<Person> all = DB.findAllPeople();
                all.remove(p);
                Set<Person> parents = DB.findParentsOf(p);
                Set<Person> children = DB.findChildrenOf(p);

                all.removeAll(parents);
                all.removeAll(children);

                SelectPeopleDialog dialog = new SelectPeopleDialog("select parents", new ArrayList<>(all));
                List<Person> selected = dialog.open(TOP.FRAME);

                if(selected != null && !selected.isEmpty())
                {
                    selected.forEach(parent ->
                    {
                        try
                        {
                            DB.addChildTo(parent, p);
                        }
                        catch(DAOException ex)
                        {
                            error(ex);
                        }
                    });
                    refreshPeopleList(p);
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doRemoveParent()
    {
        log(App2.class, "doAddParent()");

        Person child = TOP.DETAIL.getPerson();

        if(child != null)
        {
            List<Person> parents = TOP.DETAIL.PARENTS.getSelectedValuesList();

            if(parents != null && parents.size() > 0)
            {
                parents.forEach(parent ->
                {
                    try
                    {
                        DB.removeChildFrom(parent, child);
                    }
                    catch(DAOException ex)
                    {
                        error(ex);
                    }
                });
                refreshPeopleList(child);
            }
        }
    }

    private void doUpdateDetail()
    {
        if(TOP.DETAIL.doValidation())
        {
            TOP.DETAIL.updatePerson();
            Person p = TOP.DETAIL.getPerson();

            try
            {
                if(DB.updatePerson(p))
                {
                    refreshPeopleList(p);
                }
                else
                {
                    warning("update failed");
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doDeleteDetail()
    {
        Person p = TOP.DETAIL.getPerson();

        if(confirm("Really delete " + p))
        {
            try
            {
                if(DB.deletePerson(p))
                {
                    refreshPeopleList(null);
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doAddChid()
    {
        log(App2.class, "doAddChid()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            try
            {
                Set<Person> all = DB.findAllPeople();
                all.remove(p);
                Set<Person> parents = DB.findParentsOf(p);
                Set<Person> children = DB.findChildrenOf(p);

                all.removeAll(parents);
                all.removeAll(children);

                SelectPeopleDialog dialog = new SelectPeopleDialog("select children", new ArrayList<>(all));
                List<Person> selected = dialog.open(TOP.FRAME);

                if(selected != null && !selected.isEmpty())
                {
                    selected.forEach(child ->
                    {
                        try
                        {
                            DB.addChildTo(p, child);
                        }
                        catch(DAOException ex)
                        {
                            error(ex);
                        }
                    });
                    refreshPeopleList(p);
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doRemoveChild()
    {
        log(App2.class, "doRemoveChild()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            List<Person> children = TOP.DETAIL.CHILDREN.getSelectedValuesList();

            if(children != null && children.size() > 0)
            {
                children.forEach(c ->
                {
                    try
                    {
                        DB.removeChildFrom(p, c);
                    }
                    catch(DAOException ex)
                    {
                        error(ex);
                    }
                });
                refreshPeopleList(p);
            }
        }
    }
}
