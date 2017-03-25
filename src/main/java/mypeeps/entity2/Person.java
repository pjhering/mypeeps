package mypeeps.entity2;

import java.util.Objects;
import static mypeeps.Utils.log;

public class Person implements Comparable<Person>
{

    private Long id;
    private String givenName;
    private String familyName;
    private String gender;
    private String notes;

    public Person(Long id, String givenName, String familyName, String gender, String notes)
    {
        log(Person.class, "Person(Long, String, String, String, String)");

        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.notes = notes;
    }

    public Long getId()
    {
        log(Person.class, "getId()");

        return id;
    }

    public void setId(Long id)
    {
        log(Person.class, "setId(Long)");

        this.id = id;
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

        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(Person o)
    {
        log(Person.class, "compareTo(Person)");

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
        log(Person.class, "toString()");

        return givenName + " " + familyName;
    }
}
