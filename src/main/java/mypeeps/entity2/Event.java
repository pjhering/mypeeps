package mypeeps.entity2;

import java.util.Date;
import java.util.Objects;
import static mypeeps.Utils.FMT;

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
        this.id = id;
        this.person = person;
        this.title = title;
        this.date = date;
        this.place = place;
        this.notes = notes;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    
    public Person getPerson()
    {
        return person;
    }
    
    public void setPerson(Person person)
    {
        this.person = person;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }
    
    @Override
    public boolean equals(Object obj)
    {
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
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(Event o)
    {
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
        return "(" + FMT.format(date) + " " + place + ") " + title;
    }
}
