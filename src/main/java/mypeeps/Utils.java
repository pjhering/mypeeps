package mypeeps;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import static java.lang.String.format;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import static javax.swing.BorderFactory.createTitledBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.text.JTextComponent;
import mypeeps.ui2.PopupListener;

public class Utils
{

    public static final DateFormat FMT = new SimpleDateFormat("MM-dd-yyyy");

    public static JPanel buttons(int align, JButton... buttons)
    {
        JPanel panel = new JPanel(new GridLayout(1, buttons.length, 5, 5));

        for(JButton button : buttons)
        {
            panel.add(button);
        }

        JPanel flow = new JPanel(new FlowLayout(align));
        flow.add(panel);

        return flow;
    }

    public static JPanel fields(JComponent... fields)
    {
        JPanel panel = new JPanel(new GridLayout(fields.length, 1, 5, 5));

        for(JComponent field : fields)
        {
            panel.add(field);
        }

        return panel;
    }

    public static JPanel labels(String... titles)
    {
        JPanel panel = new JPanel(new GridLayout(titles.length, 1, 5, 5));

        for(String title : titles)
        {
            panel.add(new JLabel(title));
        }

        return panel;
    }

    public static boolean required(String name, JTextComponent jtc)
    {
        String s = jtc.getText();
        s = s == null ? "" : s.trim();
        jtc.setText(s);

        if(s.length() == 0)
        {
            showMessageDialog(jtc, name + " is required", "warning", WARNING_MESSAGE);
            jtc.requestFocus();
            return false;
        }

        return true;
    }

    public static PopupListener popup(String title, JMenuItem... items)
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

    public static PopupListener popup(JMenuItem... items)
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

    public static <E> ListModel<E> toListModel(Collection<E> list)
    {
        log(Utils.class, "toListModel(Collection<E>)");
        DefaultListModel model = new DefaultListModel();
        list.forEach(p -> model.addElement(p));
        return model;
    }

    public static boolean DEBUG = true;

    public static void log(Class c, Object o)
    {
        if(DEBUG)
        {
            out.print(format("%-40s", c.getName(), o).replace(' ', '.'));
            out.println(o);
            out.flush();
        }
    }

    private Utils()
    {
    }
}
