package mypeeps.ui;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import mypeeps.entity.Person;

public class PreviewValidPanelDialog
{

    public static void main(String[] args)
    {
        Person person = new Person(0L, "Joe", "Blow", "", "foobar");
        ValidPanelDialog<PersonPanel> dialog = new ValidPanelDialog<>("person", new PersonPanel(person));
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        PersonPanel panel = dialog.open(frame);
        
        if(panel != null)
        {
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        else
        {
            frame.dispose();
        }
    }
}
