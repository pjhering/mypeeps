package mypeeps.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.util.Objects.requireNonNull;
import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter
{
    
    private final JPopupMenu POPUP;
    private boolean enabled;
    
    public PopupListener(JPopupMenu popup)
    {
        this.POPUP = requireNonNull(popup);
        enabled = true;
    }
    
    public void setEnabled(boolean value)
    {
        this.enabled = value;
    }
    
    public boolean getEnabled()
    {
        return enabled;
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
        if(enabled && e.isPopupTrigger())
        {
            POPUP.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
