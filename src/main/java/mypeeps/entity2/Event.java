package mypeeps.entity2;

import java.util.Date;
import java.util.Objects;
import static mypeeps.Utils.FMT;
import static mypeeps.Utils.log;

public class Event implements Comparable<Event>
{

    private Long id;
    private Person person;
    private String title;
    private Date date;
    private String place;
    private String notes;

    public Event(Long id, Person person, String title, Date date, String place, String notes)
    {
        log(Event.class, "Event(Long, Person, String, Date, String, String)");

        this.id = id;
        this.person = person;
        this.title = title;
        this.date = date;
        this.place = place;
        this.notes = notes;
    }

    public Long getId()
    {
        log(Event.class, "getId()");

        return id;
    }

    public void setId(Long id)
    {
        log(Event.class, "setId(Long)");

        this.id = id;
    }

    public Person getPerson()
    {
        log(Event.class, "getPerson()");

        return person;
    }

    public void setPerson(Person person)
    {
        log(Event.class, "setPerson(Person)");

        this.person = person;
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

    public String getPlace()
    {
        log(Event.class, "getPlace()");

        return place;
    }

    public void setPlace(String place)
    {
        log(Event.class, "setPlace()");

        this.place = place;
    }

    public String getNotes()
    {
        log(Event.class, "getNotes()");

        return notes;
    }

    public void setNotes(String notes)
    {
        log(Event.class, "setNotes()");

        this.notes = notes;
    }

    @Override
    public boolean equals(Object obj)
    {
        log(Event.class, "equals()");

        if(this == obj)
        {
            return true;
        }
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final Event other = (Event) obj;
        if(!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(Event.class, "hashCode()");

        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(Event o)
    {
        log(Event.class, "compareTo(Event)");

        int v1 = date.compareTo(o.date);

        if(v1 == 0)
        {
            int v2 = place.compareTo(o.place);

            if(v2 == 0)
            {
                return title.compareTo(o.title);
            }

            return v2;
        }

        return v1;
    }

    @Override
    public String toString()
    {
        log(Event.class, "toString()");

        return title + " (" + FMT.format(date) + " " + place + ")";
    }
}
