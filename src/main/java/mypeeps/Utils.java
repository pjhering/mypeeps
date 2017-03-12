package mypeeps;

import static java.lang.String.format;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Utils
{

    public static final DateFormat FMT = new SimpleDateFormat("MM-dd-yyyy");

    public static void log(Class c, Object o)
    {
        out.print(format("%-30s", c.getName(), o).replace(' ', '.'));
        out.println(o);
        out.flush();
    }

    public static void log(Object i, Object o)
    {
        log(i.getClass(), o);
    }

    private Utils()
    {
    }
}
