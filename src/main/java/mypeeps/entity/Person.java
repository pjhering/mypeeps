package mypeeps.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import static mypeeps.Utils.log;

public class Person extends AbstractEntity
{

    private String givenName;
    private String familyName;
    private String gender;
    private String notes;
    private final Set<Person> parents = new HashSet<>();
    private final Set<Person> children = new HashSet<>();
    private final Set<Event> events = new HashSet<>();
    private final Set<Attachment> attachments = new HashSet<>();

    public Person()
    {
        this(null, null, null, null, null);
        log(Person.class, "Person()");
    }

    public Person(Long id, String gname, String fname, String gender, String notes)
    {
        super(id);
        log(Person.class, "Person(Long, String, String, String, String)");
        this.givenName = gname;
        this.familyName = fname;
        this.gender = gender;
        this.notes = notes;
    }

    public boolean isDescendantOf(Person other)
    {
        log(Person.class, "isDescendantOf(Person)");
        
        if(parents.contains(other))
        {
            return true;
        }
        else
        {
            for(Person p : parents)
            {
                if(p.isDescendantOf(other))
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    public boolean isAncestorOf(Person other)
    {
        log(Person.class, "isAncestorOf(Person)");
        

        if(children.contains(other))
        {
            return true;
        }
        else
        {
            for(Person p : children)
            {
                if(p.isAncestorOf(other))
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    public String getGivenName()
    {
        log(Person.class, "getGivenName()");
        return givenName;
    }

    public void setGivenName(String givenName)
    {
        log(Person.class, "setGivenName(String)");
        this.givenName = givenName;
    }

    public String getFamilyName()
    {
        log(Person.class, "getFamilyName()");
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        log(Person.class, "setFamilyName(String)");
        this.familyName = familyName;
    }

    public String getGender()
    {
        log(Person.class, "getGender()");
        return gender;
    }

    public void setGender(String gender)
    {
        log(Person.class, "setGender(String)");
        this.gender = gender;
    }

    public String getNotes()
    {
        log(Person.class, "getNotes()");
        return notes;
    }

    public void setNotes(String notes)
    {
        log(Person.class, "setNotes(String)");
        this.notes = notes;
    }

    public Set<Person> getParents()
    {
        log(Person.class, "getParents()");
        return parents;
    }

    public Set<Person> getChildren()
    {
        log(Person.class, "getChildren()");
        return children;
    }

    public Set<Event> getEvents()
    {
        log(Person.class, "getEvents()");
        return events;
    }

    public Set<Attachment> getAttachments()
    {
        log(Person.class, "getAttachments()");
        return attachments;
    }

    @Override
    public boolean equals(Object obj)
    {
        log(Person.class, "equals(Object)");
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
        final Person other = (Person) obj;
        if(!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(Person.class, "hashCode()");
        int hash = 13;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString()
    {
        log(Person.class, "toString()");
        return givenName + " " + familyName;
    }

    @Override
    public int compareTo(AbstractEntity o)
    {
        log(Person.class, "compareTo(AbstractEntity)");
        if(o != null)
        {
            if(o instanceof Person)
            {
                Person p = (Person) o;
                int value = this.familyName.compareTo(p.familyName);

                if(value == 0)
                {
                    return this.givenName.compareTo(p.givenName);
                }

                return value;
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
}
