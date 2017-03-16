package mypeeps;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static java.lang.String.format;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;
import mypeeps.entity.Person;
import mypeeps.ui.PopupListener;

public class Utils
{

    public static final DateFormat FMT = new SimpleDateFormat("MM-dd-yyyy");
    
    public static PopupListener popup(String title, JMenuItem ... items)
    {
        JPopupMenu menu = new JPopupMenu();
        
        if(title != null)
        {
            menu.setBorder(createTitledBorder(title));
        }
        
        for(JMenuItem item : items)
        {
            if(item == null)
            {
                menu.addSeparator();
            }
            else
            {
                menu.add(item);
            }
        }
        
        return new PopupListener(menu);
    }
    
    public static PopupListener popup(JMenuItem ... items)
    {
        return popup(null, items);
    }
    
    public static void selectOnFocus(JTextComponent jtc)
    {
        jtc.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                log(Utils.class, "focusGained(FocusEvent)");
                jtc.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                log(Utils.class, "focusLost(FocusEvent)");
                jtc.select(0, 0);
            }
        });
    }

    public static ListModel toListModel(List<Person> list)
    {
                log(Utils.class, "toListModel(List<Person>)");
        DefaultListModel model = new DefaultListModel();
        list.forEach(p -> model.addElement(p));
        return model;
    }

    public static void log(Class c, Object o)
    {
        out.print(format("%-40s", c.getName(), o).replace(' ', '.'));
        out.println(o);
        out.flush();
    }

    public static void log(Object i, Object o)
    {
        log(i.getClass(), o);
    }

    private Utils()
    {
    }
}
