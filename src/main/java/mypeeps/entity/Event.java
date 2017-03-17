package mypeeps.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import static mypeeps.Utils.FMT;
import static mypeeps.Utils.log;

public class Event extends AbstractEntity
{

    private String title;
    private Date date;
    private Place place;
    private String notes;
    private final Set<Attachment> attachments = new TreeSet<>();

    public Event()
    {
        this(null, null, null, null, null);
        log(Event.class, "Event()");
    }

    public Event(Long id, String title, Date date, Place place, String notes)
    {
        super(id);
        log(Event.class, "Event(Long, String, Date, Place, String)");
        this.title = title;
        this.date = date;
        this.place = place;
        this.notes = notes;
    }

    public String getTitle()
    {
        log(Event.class, "getTitle()");
        return title;
    }

    public void setTitle(String title)
    {
        log(Event.class, "setTitle(String)");
        this.title = title;
    }

    public Date getDate()
    {
        log(Event.class, "getDate()");
        return date;
    }

    public void setDate(Date date)
    {
        log(Event.class, "setDate(Date)");
        this.date = date;
    }

    public Place getPlace()
    {
        log(Event.class, "getPlace()");
        return place;
    }

    public void setPlace(Place place)
    {
        log(Event.class, "setPlace(Place)");
        this.place = place;
    }

    public String getNotes()
    {
        log(Event.class, "getNotes()");
        return notes;
    }

    public void setNotes(String notes)
    {
        log(Event.class, "setNotes(String)");
        this.notes = notes;
    }

    public Set<Attachment> getAttachments()
    {
        log(Event.class, "getAttachments()");
        return attachments;
    }

    public String toString()
    {
        log(Event.class, "toString()");
        return title + " [" + place.toString() + ", " + FMT.format(date) + "]";
    }

    @Override
    public int compareTo(AbstractEntity o)
    {
        log(Event.class, "compareTo(AbstractEntity)");
        if (o != null)
        {
            if (o instanceof Event)
            {
                return date.compareTo(((Event) o).date);
            }
            else
            {
                throw new ClassCastException("wrong class: " + o.getClass());
            }
        }
        else
        {
            throw new NullPointerException("null AbstractEntity");
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        log(Event.class, "equals(Object)");
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(Event.class, "hashCode()");
        int hash = 11;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
