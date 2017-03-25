package mypeeps.ui2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.util.Objects.requireNonNull;
import javax.swing.JPopupMenu;
import static mypeeps.Utils.log;

public class PopupListener extends MouseAdapter
{

    private final JPopupMenu POPUP;

    public PopupListener(JPopupMenu popup)
    {
        log(PopupListener.class, "PopupListener(JPopupMenu)");

        this.POPUP = requireNonNull(popup);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        log(PopupListener.class, "mousePressed(MouseEvent)");

        check(e);
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        log(PopupListener.class, "mouseReleased(MouseEvent)");

        check(e);
    }

    private void check(MouseEvent e)
    {
        log(PopupListener.class, "check(MouseEvent)");

        if(e.isPopupTrigger())
        {
            POPUP.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
