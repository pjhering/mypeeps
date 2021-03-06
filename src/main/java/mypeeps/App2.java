package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.sort;
import java.util.Date;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.JDialog;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static mypeeps.Utils.log;
import static mypeeps.Utils.toListModel;
import mypeeps.entity2.DAO;
import mypeeps.entity2.DAOException;
import mypeeps.entity2.Event;
import mypeeps.entity2.File;
import mypeeps.entity2.Person;
import mypeeps.ui2.EventDialog;
import mypeeps.ui2.FileDialog;
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
        TOP.PRINT.addActionListener(ae -> doPrint());
        TOP.CREATEPERSON.addActionListener(a1 -> doCreatePerson());
        TOP.DETAIL.UPDATE.addActionListener(a2 -> doUpdateDetail());
        TOP.DETAIL.DELETE.addActionListener(a3 -> doDeleteDetail());
        TOP.DETAIL.ADDPARENT.addActionListener(a4 -> doAddParent());
        TOP.DETAIL.REMOVEPARENT.addActionListener(a5 -> doRemoveParent());
        TOP.DETAIL.ADDCHILD.addActionListener(a6 -> doAddChid());
        TOP.DETAIL.REMOVECHILD.addActionListener(a7 -> doRemoveChild());
        TOP.DETAIL.ADDEVENT.addActionListener(a8 -> doAddEvent());
        TOP.DETAIL.EDITEVENT.addActionListener(a9 -> doEditEvent());
        TOP.DETAIL.DELETEEVENT.addActionListener(aa -> doDeleteEvent());
        TOP.DETAIL.ADDFILE.addActionListener(ab -> doAddPersonFile());
        TOP.DETAIL.DELETEFILE.addActionListener(ad -> doDeletePersonFile());
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

            try
            {
                List<Person> parents = new ArrayList<>(DB.findParentsOf(p));
                TOP.DETAIL.PARENTS.setModel(toListModel(parents));

                List<Person> children = new ArrayList<>(DB.findChildrenOf(p));
                TOP.DETAIL.CHILDREN.setModel(toListModel(children));

                List<Event> events = new ArrayList<>(DB.findEventsFor(p));
                TOP.DETAIL.EVENTS.setModel(toListModel(events));

                List<File> files = new ArrayList<>(DB.findFilesForPerson(p));
                TOP.DETAIL.FILES.setModel(toListModel(files));
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
        log(App2.class, "doRemoveParent()");

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
        log(App2.class, "doUpdateDetail()");

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
        log(App2.class, "doDeleteDetail()");

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

    private void doAddEvent()
    {
        log(App2.class, "doAddEvent()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            final Event e = new Event(null, p, null, new Date(), null, null);
            final EventDialog d = new EventDialog(TOP.FRAME, e, new ArrayList<>());
            d.ADDFILES.addActionListener(a0 -> doAddEventFile(d));
            d.REMOVEFILES.addActionListener(a1 -> doRemoveEventFile(d));
            boolean saved = d.open();

            if(saved)
            {
                Event ue = d.getUpdatedEvent();
                try
                {
                    ue = DB.createEvent(p, ue.getTitle(), ue.getDate(), ue.getPlace(), ue.getNotes());
                    refreshPeopleList(p);
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
            }
        }
    }

    private void doEditEvent()
    {
        log(App2.class, "doEditEvent()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            Event e = TOP.DETAIL.EVENTS.getSelectedValue();

            if(e != null)
            {
                try
                {
                    List<File> files = new ArrayList<>(DB.findFilesForEvent(e));
                    EventDialog d = new EventDialog(TOP.FRAME, e, files);
                    d.ADDFILES.addActionListener(a0 -> doAddEventFile(d));
                    d.REMOVEFILES.addActionListener(a1 -> doRemoveEventFile(d));
                    boolean saved = d.open();

                    if(saved)
                    {
                        e = d.getUpdatedEvent();

                        if(DB.updateEvent(e))
                        {
                            refreshPeopleList(p);
                        }
                    }
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
            }
        }
    }

    private void doDeleteEvent()
    {
        log(App2.class, "doDeleteEvent()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            Event e = TOP.DETAIL.EVENTS.getSelectedValue();

            if(e != null && confirm("Really confirm " + e + "?"))
            {
                try
                {
                    if(DB.deleteEvent(e))
                    {
                        refreshPeopleList(p);
                    }
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
            }
        }
    }

    private void doAddEventFile(EventDialog dialog)
    {
        log(App2.class, "doAddEventFile(EventDialog)");

        File f = new File(null, null, null);
        FileDialog d = new FileDialog(dialog.DIALOG, f);
        boolean saved = d.open();

        if(saved)
        {
            f = d.getUpdatedFile();

            try
            {
                f = DB.createFile(f.getPath(), f.getDescription());

                if(DB.addFileToEvent(f, dialog.EVENT))
                {
                    dialog.FILES.setModel(toListModel(DB.findFilesForEvent(dialog.EVENT)));
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doRemoveEventFile(EventDialog dialog)
    {
        log(App2.class, "doRemoveEventFile(EventDialog)");

        File f = dialog.FILES.getSelectedValue();

        if(f != null)
        {
            try
            {
                if(confirm("Really delete " + f + "?") && DB.deleteFile(f))
                {
                    dialog.FILES.setModel(toListModel(DB.findFilesForEvent(dialog.EVENT)));
                }
            }
            catch(DAOException ex)
            {
                error(ex);
            }
        }
    }

    private void doAddPersonFile()
    {
        log(App2.class, "doAddPersonFile()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            File file = new File(null, null, null);
            FileDialog dialog = new FileDialog(TOP.FRAME, file);
            boolean saved = dialog.open();

            if(saved)
            {
                try
                {
                    file = dialog.getUpdatedFile();
                    file = DB.createFile(file.getPath(), file.getDescription());
                    DB.addFileToPerson(file, p);
                    refreshPeopleList(p);
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
            }
        }
    }

    private void doDeletePersonFile()
    {
        log(App2.class, "doDeletePersonFile()");

        Person p = TOP.DETAIL.getPerson();

        if(p != null)
        {
            File f = TOP.DETAIL.FILES.getSelectedValue();

            if(f != null)
            {
                try
                {
                    if(confirm("Really delete " + f + "?") && DB.deleteFile(f))
                    {
                        refreshPeopleList(p);
                    }
                }
                catch(DAOException ex)
                {
                    error(ex);
                }
            }
        }
    }

    private void doPrint()
    {
        try
        {
            StringBuilder b = new StringBuilder();
            b.append("<!DOCTYPE html><html><head><title>MyPeeps</title></head><body>");
            List<Person> people = new ArrayList<>(DB.findAllPeople());
            sort(people);
            
            for(Person person : people)
            {
                b.append("<div><b>")
                        .append(person.toString())
                        .append("</b><br>");
                
                if(person.getNotes() != null && person.getNotes().length() > 0)
                {
                    b.append("&nbsp;&nbsp;&nbsp;&nbsp;<em>")
                            .append(person.getNotes())
                            .append("</em><br>");
                }
                
                List<File> pfiles = new ArrayList<>(DB.findFilesForPerson(person));
                sort(pfiles);
                
                if(!pfiles.isEmpty())
                {
                    for(File pfile : pfiles)
                    {
                        b.append("<a href=\"file_")
                                .append(pfile.getId())
                                .append("\">")
                                .append(pfile.toString())
                                .append("</a><br>");
                    }
                }
                
                List<Person> parents = new ArrayList<>(DB.findParentsOf(person));
                sort(parents);
                
                if(!parents.isEmpty())
                {
                    b.append("Parents<br>");
                    
                    for(Person parent : parents)
                    {
                        b.append("&nbsp;-&nbsp;")
                                .append(parent.toString())
                                .append("<br>");
                    }
                }
                
                List<Person> children = new ArrayList<>(DB.findChildrenOf(person));
                sort(children);
                
                if(!children.isEmpty())
                {
                    b.append("Children<br>");
                    
                    for(Person child : children)
                    {
                        b.append("&nbsp;-&nbsp;")
                                .append(child.toString())
                                .append(" [")
                                .append(child.getGender())
                                .append("]<br>");
                    }
                }
                
                List<Event> events = new ArrayList<>(DB.findEventsFor(person));
                sort(events);
                
                if(!events.isEmpty())
                {
                    b.append("Events<br>");
                    
                    for(Event event : events)
                    {
                        b.append("&nbsp;-&nbsp;")
                                .append(event.toString())
                                .append("<br>");
                        
                        if(event.getNotes() != null && event.getNotes().length() > 0)
                        {
                            b.append("&nbsp;&nbsp;&nbsp;&nbsp;<em>")
                                    .append(event.getNotes())
                                    .append("</em><br>");
                        }
                        
                        List<File> files = new ArrayList<>(DB.findFilesForEvent(event));
                        sort(files);
                        
                        for(File file : files)
                        {
                            b.append("&nbsp;-&nbsp;")
                                    .append("<a href=\"#file_")
                                    .append(file.getId())
                                    .append("\">")
                                    .append(file.toString())
                                    .append("</a><br>");
                        }
                    }
                }
                
                b.append("</div><br><br>");
            }
            b.append("</body></html>");
            
            JFXPanel jfx = new JFXPanel();
            Platform.runLater(()->
            {
                WebView web = new WebView();
                web.getEngine().loadContent(b.toString());
                jfx.setScene(new Scene(web));
            });
            JDialog dialog = new JDialog(TOP.FRAME, "preview", true);
            dialog.setContentPane(new JScrollPane(jfx));
            dialog.setSize(TOP.FRAME.getSize());
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(TOP.FRAME);
            dialog.setVisible(true);
        }
        catch(DAOException ex)
        {
            error(ex);
        }
    }
}
