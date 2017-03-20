package mypeeps.entity2;

import java.util.Objects;
import java.util.Set;

public class Person implements Comparable<Person>
{

    private Long id;
    private String givenName;
    private String familyName;
    private String gender;
    private String notes;
    private Set<Person> parents;
    private Set<Person> children;
    private Set<Event> events;
    private Set<File> files;
    
    public Person(Long id, String givenName, String familyName, String gender, String notes)
    {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
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

    public String getGivenName()
    {
        return givenName;
    }

    public void setGivenName(String givenName)
    {
        this.givenName = givenName;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public Set<Person> getParents()
    {
        return parents;
    }

    public void setParents(Set<Person> parents)
    {
        this.parents = parents;
    }

    public Set<Person> getChildren()
    {
        return children;
    }

    public void setChildren(Set<Person> children)
    {
        this.children = children;
    }

    public Set<Event> getEvents()
    {
        return events;
    }

    public void setEvents(Set<Event> events)
    {
        this.events = events;
    }

    public Set<File> getFiles()
    {
        return files;
    }

    public void setFiles(Set<File> files)
    {
        this.files = files;
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(Person o)
    {
        int value = familyName.compareTo(o.familyName);
        
        if(value == 0)
        {
            return givenName.compareTo(o.givenName);
        }
        
        return value;
    }
    
    @Override
    public String toString()
    {
        return givenName + " " + familyName;
    }
}
