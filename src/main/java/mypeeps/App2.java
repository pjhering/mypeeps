package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static mypeeps.Utils.log;
import static mypeeps.Utils.toListModel;
import mypeeps.entity.DAO;
import mypeeps.entity.Person;
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
        TOP.DETAIL.UPDATE.addActionListener(a1 -> doUpdatePerson());
        TOP.DETAIL.DELETE.addActionListener(a2 -> doDeletePerson());
        TOP.CREATEPERSON.addActionListener(a3 -> doCreatePerson());
        TOP.DETAIL.ADDPARENT.addActionListener(a4 -> doAddParent());
        TOP.DETAIL.REMOVEPARENT.addActionListener(a5 -> doRemoveParent());
        TOP.DETAIL.ADDCHILD.addActionListener(a6 -> doAddChild());
        TOP.DETAIL.REMOVECHILD.addActionListener(a7 -> doRemoveChild());
    }

    public void start()
    {
        log(App2.class, "start()");

        TOP.LIST.setModel(toListModel(DB.readPeople()));
        TOP.show();
        TOP.LIST.setSelectedIndex(0);
    }

    private void refreshPeopleList(Person p)
    {
        log(App2.class, "refreshPeopleList(Person)");

        TOP.LIST.setModel(toListModel(DB.readPeople()));

        if (p != null)
        {
            TOP.LIST.setSelectedValue(p, true);
        }
        else
        {
            TOP.LIST.setSelectedIndex(0);
        }
    }

    private void doListSelectionChanged()
    {
        log(App2.class, "doListSelectionChanged()");

        Person person = TOP.LIST.getSelectedValue();

        if (person != null)
        {
            TOP.DETAIL.setPerson(person);
        }
    }

    private void doUpdatePerson()
    {
        log(App2.class, "doUpdatePerson()");

        if (TOP.DETAIL.doValidation())
        {
            TOP.DETAIL.updatePerson();
            Person p = TOP.DETAIL.getPerson();
            DB.update(p);
        }
    }

    private void doDeletePerson()
    {
        log(App2.class, "doDeletePerson()");

        Person p = TOP.LIST.getSelectedValue();

        if (p != null)
        {
            DB.delete(p);
            refreshPeopleList(null);
        }
    }

    private void doCreatePerson()
    {
        log(App2.class, "doCreatePerson()");

        PersonDialog dialog = new PersonDialog(new Person());
        Person p = dialog.open(TOP.FRAME);

        if (p != null)
        {
            p = DB.create(p);
            refreshPeopleList(p);
        }
    }

    private void doAddParent()
    {
        Person child = TOP.LIST.getSelectedValue();

        if (child != null)
        {
            List<Person> all = DB.readPeople();
            all.remove(child);
            all.removeAll(child.getParents());
            all.removeAll(child.getChildren());

            SelectPeopleDialog dialog = new SelectPeopleDialog("select parents", all);
            List<Person> selected = dialog.open(TOP.FRAME);

            if (selected != null)
            {
                selected.forEach(parent ->
                {
                    child.getParents().add(parent);
                    parent.getChildren().add(child);

                    DB.update(child);
                    DB.update(parent);

                    TOP.DETAIL.PARENTS.setModel(toListModel(child.getParents()));
                    TOP.DETAIL.PARENTS.repaint();
                });
            }
        }
    }

    private void doRemoveParent()
    {
        Person child = TOP.LIST.getSelectedValue();

        if (child != null)
        {
            Person parent = TOP.DETAIL.PARENTS.getSelectedValue();

            if (parent != null)
            {
                child.getParents().remove(parent);
                parent.getChildren().remove(child);

                DB.update(child);
                DB.update(parent);

                TOP.DETAIL.PARENTS.setModel(toListModel(child.getParents()));
            }
        }
    }

    private void doAddChild()
    {
    }

    private void doRemoveChild()
    {
        Person parent = TOP.LIST.getSelectedValue();

        if (parent != null)
        {
            Person child = TOP.DETAIL.CHILDREN.getSelectedValue();

            if (child != null)
            {
                parent.getChildren().remove(child);
                child.getParents().remove(parent);

                DB.update(parent);
                DB.update(child);

                TOP.DETAIL.CHILDREN.setModel(toListModel(parent.getParents()));
            }
        }
    }
}
