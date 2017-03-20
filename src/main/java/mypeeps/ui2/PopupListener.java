package mypeeps.ui2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.util.Objects.requireNonNull;
import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter
{
    private final JPopupMenu POPUP;
    
    public PopupListener(JPopupMenu popup)
    {
        this.POPUP = requireNonNull(popup);
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        check(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        check(e);
    }

    private void check(MouseEvent e)
    {
        if(e.isPopupTrigger())
        {
            POPUP.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
