package mypeeps.ui2;

import static java.lang.System.out;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JList;
import javax.swing.JScrollPane;
import static mypeeps.Utils.toListModel;
import mypeeps.entity.Person;

public class TestSelectPeopleDialog
{
    public static void main(String[] args)
    {
        List<Person> people = Arrays.asList(
            new Person(1L, "Alice", "Abernathy", "female", null),
            new Person(1L, "Betty", "Beck", "female", null),
            new Person(1L, "Carol", "Carbon", "female", null),
            new Person(1L, "Denise", "Dickens", "female", null),
            new Person(1L, "Edna", "Edwards", "female", null),
            new Person(1L, "Franny", "Foster", "female", null),
            new Person(1L, "Gertrude", "Gill", "female", null),
            new Person(1L, "Helen", "Hall", "female", null),
            new Person(1L, "Inga", "Ingersoll", "female", null),
            new Person(1L, "Jill", "James", "female", null));
        
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        SelectPeopleDialog dialog = new SelectPeopleDialog("select people", people);
        List<Person> selected = dialog.open(frame);
        
        if(selected != null)
        {
            JList<Person> list = new JList(toListModel(selected));
            frame.setContentPane(new JScrollPane(list));
            frame.setVisible(true);
        }
        else
        {
            frame.dispose();
        }
    }
}
