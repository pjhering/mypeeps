package mypeeps;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static mypeeps.Utils.log;

class Login
{
    
    public final String USERNAME;
    public final String PASSWORD;
    
    private static JTextField user;
    private static JPasswordField pass;
    private static final JFrame FRAME = new JFrame();
    private static JDialog dialog;
    private static boolean saved;
    
    private Login(String user, String pass)
    {
        log(Login.class, "Login(String, String)");
        
        this.USERNAME = user;
        this.PASSWORD = pass;
    }
    
    public static Login open()
    {
        log(Login.class, "open()");
        
        user = new JTextField(20);
        pass = new JPasswordField(); 
        
        JPanel fields = new JPanel(new GridLayout(2, 1, 10, 10));
        fields.add(user);
        fields.add(pass);
        
        JPanel labels = new JPanel(new GridLayout(2, 1, 10, 10));
        labels.add(new JLabel("username"));
        labels.add(new JLabel("password"));
        
        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.add(labels, WEST);
        center.add(fields, CENTER);
        
        JButton login = new JButton("login");
        login.addActionListener(a1 -> doLogin());
        
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(a2 -> doCancel());
        
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(login);
        buttons.add(cancel);
        
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(createEmptyBorder(10, 10, 10, 10));
        content.add(center, CENTER);
        content.add(buttons, SOUTH);
        
        FRAME.setSize(100, 100);
        FRAME.setLocationRelativeTo(null);
        
        dialog = new JDialog(FRAME, "login", true);
        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(FRAME);
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
        
        return saved ? new Login(user.getText(), new String(pass.getPassword())) : null;
    }
    

    private static void doLogin()
    {
        log(Login.class, "doLogin()");
        
        saved = true;
        close();
    }

    private static void doCancel()
    {
        log(Login.class, "doCancel()");
        
        saved = false;
        close();
    }
    
    private static void close()
    {
        log(Login.class, "close()");
        
        dialog.setVisible(false);
        dialog.dispose();
        FRAME.dispose();
    }
}
