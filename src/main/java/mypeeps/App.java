package mypeeps;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.System.exit;
import static java.util.Objects.requireNonNull;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static mypeeps.Utils.log;
import mypeeps.entity.DAO;
import mypeeps.ui.Top;

public class App
{

    private final DAO DB;
    private final Top TOP;

    public App(DAO dao)
    {
        log(App.class, "App(DAO)");
        this.DB = requireNonNull(dao);
        this.TOP = new Top();
        TOP.FRAME.setDefaultCloseOperation(EXIT_ON_CLOSE);
        TOP.FRAME.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                DB.shutDown();
                exit(0);
            }
        });
    }

    public void start()
    {
        log(App.class, "start()");
        TOP.show();
    }
}
