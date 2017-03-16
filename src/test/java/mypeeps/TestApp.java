package mypeeps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.getProperty;
import java.util.TreeSet;
import static mypeeps.Utils.log;
import mypeeps.entity.DAO;
import mypeeps.entity.MemoryDAO;

public class TestApp
{

    public static void main(String[] args) throws Exception
    {
        log(TestApp.class, "main(String[])");
        File home = new File(getProperty("user.home"));
        File root = new File(home, "mypeeps");
        File data = new File(root, "test.obj");

        if(!data.exists())
        {
            if(root.mkdir())
            {
                if(!data.createNewFile())
                {
                    throw new RuntimeException("failed to start TestApp");
                }
                else
                {
                    try(FileOutputStream stream = new FileOutputStream(data))
                    {
                        ObjectOutputStream out = new ObjectOutputStream(stream);
                        out.writeLong(1);
                        out.writeObject(new TreeSet<>());
                        out.writeObject(new TreeSet<>());
                        out.writeObject(new TreeSet<>());
                        out.writeObject(new TreeSet<>());
                        out.flush();
                    }
                }
            }
        }

        DAO dao = new MemoryDAO(data);
        App app = new App(dao);
        app.start();
    }
}
