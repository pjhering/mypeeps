package mypeeps.ui;

import static java.util.Objects.requireNonNull;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;
import static mypeeps.Utils.log;
import mypeeps.entity.AbstractEntity;

abstract public class ValidPanel<E extends AbstractEntity> extends JPanel
{
    private final E entity;
    
    public ValidPanel(E entity)
    {
        log(ValidPanel.class, "ValidPanel(E)");
        this.entity = requireNonNull(entity);
    }
    
    abstract public void updateFields();
    
    abstract public void updateEntity();
    
    abstract public boolean doValidation();
    
    public E getEntity()
    {
        log(ValidPanel.class, "getEntity()");
        return entity;
    }
    
    protected boolean required(String name, JTextComponent jtc)
    {
        log(ValidPanel.class, "required(String, JTextComponent)");
        String s = jtc.getText();
        s = s == null ? "" : s.trim();
        jtc.setText(s);
        
        if(s.length() == 0)
        {
            warning(name + " is required");
            jtc.requestFocus();
            return false;
        }
        
        return true;
    }
    
    protected void warning(String text)
    {
        log(ValidPanel.class, "warning(String)");
        showMessageDialog(this, text, "warning", WARNING_MESSAGE);
    }
}
