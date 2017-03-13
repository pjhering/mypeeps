package mypeeps.ui;

import static java.util.Objects.requireNonNull;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import mypeeps.entity.AbstractEntity;

abstract public class ValidPanel<E extends AbstractEntity> extends JPanel
{
    private final E entity;
    
    public ValidPanel(E entity)
    {
        this.entity = requireNonNull(entity);
    }
    
    abstract public void updateFields();
    
    abstract public void updateEntity();
    
    abstract public boolean doValidation();
    
    public E getEntity()
    {
        return entity;
    }
    
    protected void warning(String text)
    {
        showMessageDialog(this, text, "warning", WARNING_MESSAGE);
    }
}
