package mypeeps.ui2;

import java.awt.Dialog;
import java.awt.Frame;
import static java.util.Objects.requireNonNull;
import mypeeps.entity2.Event;

/**
 * Used to create or edit an event.
 *
 * @author tinman
 */
public class EventDialog
{

    private final Event EVENT;

    public EventDialog(Event event)
    {
        this.EVENT = requireNonNull(event);
    }

    public Event open(Frame parent)
    {
        return EVENT;
    }

    public Event open(Dialog parent)
    {
        return EVENT;
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
