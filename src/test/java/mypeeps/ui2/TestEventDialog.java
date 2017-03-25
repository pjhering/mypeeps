package mypeeps.ui2;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import mypeeps.entity2.Event;
import mypeeps.entity2.Person;

public class TestEventDialog
{

    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setSize(100, 100);
        f.setLocationRelativeTo(null);

        Person p = new Person(1L, "John", "Doe", "male", "reknowned arsonist");
        Event e = new Event(1L, p, "FIRE", new Date(), "Hometown", "house burned to the ground!");

        EventDialog d = new EventDialog(f, e, new ArrayList<>());

        boolean saved = d.open();

        if(saved)
        {
            out.println(d.PERSON.getText());
            out.println(d.TITLE.getText());
            out.println(d.DATE.getText());
            out.println(d.PLACE.getText());
            out.println(d.NOTES.getText());
        }

        f.dispose();
    }
}
