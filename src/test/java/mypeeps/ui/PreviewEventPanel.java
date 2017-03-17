package mypeeps.ui;

import java.util.Date;
import static mypeeps.Utils.log;
import mypeeps.entity.Event;
import mypeeps.entity.Place;
import static mypeeps.ui.Preview.showInJFrame;

public class PreviewEventPanel
{

    public static void main(String[] args)
    {
        log(PreviewEventPanel.class, "main(String[])");
        showInJFrame(new EventPanel(new Event(0L, "booyah", new Date(), new Place(0L, "haha", "hehe"), "woo hoo")));
    }
}
