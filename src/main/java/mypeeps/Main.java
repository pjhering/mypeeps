package mypeeps;

import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import mypeeps.entity2.JdbcDAO;

public class Main
{

    public static void main(String[] args)
    {
//        Login login = Login.open();
//        
//        if(login == null)
//        {
//            return;
//        }
        
        String driver = "org.h2.Driver";
        String url = "jdbc:h2:~/mypeeps/h2";
        String username = "mypeeps";
        String password = "mypeeps";
        
        try
        {
//            JdbcDAO dao = new JdbcDAO(driver, url, login.USERNAME, login.PASSWORD);
            JdbcDAO dao = new JdbcDAO(driver, url, username, password);
            dao.executeScript(dao.loadScript("/create-h2.ddl"));
            dao.loadStatements("/statements.properties");
            
            App2 app = new App2(dao);
            app.start();
        }
        catch(Exception ex)
        {
            showMessageDialog(null, ex.getMessage(), "ERROR", WARNING_MESSAGE);
        }
    }
}
