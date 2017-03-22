package mypeeps.ui2;

import java.awt.Dialog;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mypeeps.entity2.File;

public class FileDialog
{
    
    public final File FILE;
    public final JTextField PATH;
    public final JTextField DESCRIPTION;
    private final JPanel CONTENT;
    private final JButton SAVE;
    private final JButton CANCEL;
    private final JButton SELECT;
    
    public FileDialog(File file)
    {
        this.FILE = requireNonNull(file);
    }
    
    public boolean open(Frame parent)
    {
    }
    
    public boolean open(Dialog parent)
    {
    }
    
    private boolean open()
    {
    }
    
    private void doSaveAction()
    {
    }
    
    private void doCancelAction()
    {
    }
    
    private void close()
    {
    }
    
    private boolean doValidation()
    {
    }
    
    public File getUpdatedFile()
    {
    }
}
