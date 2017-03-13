package mypeeps.ui;

import static mypeeps.Utils.log;
import mypeeps.entity.Person;
import static mypeeps.ui.Preview.showInJFrame;

public class PreviewPersonPanel
{
    public static void main(String[] args)
    {
        log(PreviewPersonPanel.class, "main(String[])");
        showInJFrame(new PersonPanel(new Person(0L, "Joe", "Blow", "male", "foobar")));
    }
}
