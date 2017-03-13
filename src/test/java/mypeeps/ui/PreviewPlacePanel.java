package mypeeps.ui;

import static mypeeps.Utils.log;
import mypeeps.entity.Place;
import static mypeeps.ui.Preview.showInJFrame;

public class PreviewPlacePanel
{
    public static void main(String[] args)
    {
        log(PreviewPlacePanel.class, "main(String[])");
        showInJFrame(new PlacePanel(new Place(0L, "Clownville", "foobar")));
    }
}
