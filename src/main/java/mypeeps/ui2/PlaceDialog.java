package mypeeps.ui2;

import java.awt.Dialog;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import mypeeps.entity.Place;

/**
 * Used to create or edit a Place.
 *
 * @author tinman
 */
public class PlaceDialog
{

    private final Place PLACE;

    public PlaceDialog(Place place)
    {
        this.PLACE = requireNonNull(place);
    }

    public Place open(Frame parent)
    {
        return PLACE;
    }

    public Place open(Dialog parent)
    {
        return PLACE;
    }

    private boolean isValid()
    {
        return false;
    }

    private void updatePerson()
    {
    }

    private void doSaveAction()
    {
    }

    private void doCancelAction()
    {
    }

    private void close()
    {
    }
}
