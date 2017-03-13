package mypeeps.ui;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import static java.util.Objects.requireNonNull;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ValidPanelDialog<P extends ValidPanel>
{

    private final String TITLE;
    private final P PANEL;
    private final JPanel CONTENT;
    private JDialog dialog;
    private boolean saved;
    
    
    public ValidPanelDialog(String title, P panel)
    {
        this.TITLE = title == null ? "" : title;
        this.PANEL = requireNonNull(panel);
        
        JButton save = new JButton("save");
        save.addActionListener(a1 -> doSaveAction());
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(a2 -> doCancelAction());
        
        JPanel buttons = new JPanel(new GridLayout(1, 2, 5, 5));
        buttons.add(save);
        buttons.add(cancel);
        
        JPanel south = new JPanel(new FlowLayout());
        south.add(buttons);
        
        CONTENT = new JPanel(new BorderLayout(5, 5));
        CONTENT.setBorder(createEmptyBorder(10, 10, 10, 10));
        CONTENT.add(PANEL, CENTER);
        CONTENT.add(south, SOUTH);
    }
    
    public P open(Frame parent)
    {
        dialog = new JDialog(parent, TITLE, true);
        init();
        
        return saved ? PANEL : null;
    }
    
    public P open(Dialog parent)
    {
        dialog = new JDialog(parent, TITLE, true);
        init();
        
        return saved ? PANEL : null;
    }
    
    private void init()
    {
        dialog.setContentPane(CONTENT);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
    }

    private void doSaveAction()
    {
        if(PANEL.doValidation())
        {
            saved = true;
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    private void doCancelAction()
    {
        saved = false;
        dialog.setVisible(false);
        dialog.dispose();
    }
}
