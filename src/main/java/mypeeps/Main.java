package mypeeps;

import java.io.File;
import static java.lang.System.getProperty;
import mypeeps.entity.MemoryDAO;

public class Main
{
    public static void main(String[] args)
    {
        File home = new File(getProperty("user.home"));
        File root = new File(home, "mypeeps");
        File data = new File(root, "data.obj");
        MemoryDAO dao = new MemoryDAO(data);
        App app = new App(dao);
        app.start();
    }
}
