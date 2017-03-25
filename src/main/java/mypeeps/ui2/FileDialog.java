package mypeeps.ui2;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static mypeeps.Utils.log;
import static mypeeps.Utils.required;
import static mypeeps.Utils.selectOnFocus;
import mypeeps.entity2.File;

public class FileDialog
{
    
    public final JDialog DIALOG;
    public final File FILE;
    public final JTextField PATH;
    public final JTextField DESCRIPTION;
    private final JButton SAVE;
    private final JButton CANCEL;
    private final JButton SELECT;
    private final JPanel CONTENT;
    private final JFileChooser CHOOSER;
    private boolean saved;
    
    public FileDialog(Frame parent, File file)
    {
        this(new JDialog(parent, "file", true), file);
        
        log(FileDialog.class, "FileDialog(Frame, File)");
    }
    
    public FileDialog(Dialog parent, File file)
    {
        this(new JDialog(parent, "file", true), file);
        
        log(FileDialog.class, "FileDialog(Dialog, File)");
    }
    
    private FileDialog(JDialog dialog, File file)
    {
        log(FileDialog.class, "FileDialog(JDialog, File)");
        
        this.DIALOG = requireNonNull(dialog);
        
        this.FILE = requireNonNull(file);
        this.PATH = new JTextField(20);
        PATH.setEditable(false);
        PATH.setText(FILE.getPath());
        this.DESCRIPTION = new JTextField(20);
        DESCRIPTION.setText(FILE.getDescription());
        selectOnFocus(DESCRIPTION);
        SELECT = new JButton("select");
        SELECT.addActionListener(a0 -> doSelectAction());
        CHOOSER = new JFileChooser();
        CHOOSER.setMultiSelectionEnabled(false);
        SAVE = new JButton("save");
        SAVE.addActionListener(a1 -> doSaveAction());
        CANCEL = new JButton("cancel");
        CANCEL.addActionListener(a2 -> doCancelAction());
        
        JPanel path = new JPanel(new BorderLayout(0, 0));
        path.add(PATH, CENTER);
        path.add(SELECT, EAST);
        
        JPanel fields = new JPanel(new GridLayout(2, 1, 5, 5));
        fields.add(path);
        fields.add(DESCRIPTION);
        
        JPanel labels = new JPanel(new GridLayout(2, 1, 5, 5));
        labels.add(new JLabel("file"));
        labels.add(new JLabel("description"));
        
        JPanel center = new JPanel(new BorderLayout(5, 5));
        center.add(labels, WEST);
        center.add(fields, CENTER);
        
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(SAVE);
        buttons.add(CANCEL);
        
        JPanel south = new JPanel(new FlowLayout());
        south.add(buttons);
        
        CONTENT = new JPanel(new BorderLayout(5, 5));
        CONTENT.setBorder(createEmptyBorder(10,10,10,10));
        CONTENT.add(center, CENTER);
        CONTENT.add(south, SOUTH);
    }
    
    public boolean open()
    {
        log(FileDialog.class, "open()");
        
        DIALOG.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        DIALOG.setContentPane(CONTENT);
        DIALOG.pack();
        DIALOG.setLocationRelativeTo(DIALOG.getParent());
        DIALOG.setVisible(true);
        
        return saved;
   }
    
    private void doSaveAction()
    {
        log(FileDialog.class, "doSaveAction()");
        
        if(doValidation())
        {
            saved = true;
            close();
        }
    }
    
    private void doCancelAction()
    {
        log(FileDialog.class, "doCancelAction()");
        
        saved = false;
        close();
    }
    
    private void close()
    {
        log(FileDialog.class, "close()");
        
        DIALOG.setVisible(false);
        DIALOG.dispose();
    }
    
    private boolean doValidation()
    {
        log(FileDialog.class, "doValidation()");
        
        return required("file", PATH) && required("description", DESCRIPTION);
    }
    
    public File getUpdatedFile()
    {
        log(FileDialog.class, "getUpdateFile()");
        
        FILE.setPath(PATH.getText());
        FILE.setDescription(DESCRIPTION.getText());
        
        return FILE;
    }

    private void doSelectAction()
    {
        log(FileDialog.class, "doSelectAction()");
        
        int option = CHOOSER.showOpenDialog(DIALOG);
        
        if(option == APPROVE_OPTION)
        {
            java.io.File f = CHOOSER.getSelectedFile();
            
            if(f != null)
            {
                PATH.setText(f.getPath());
            }
        }
    }
}
