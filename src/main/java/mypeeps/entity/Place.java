package mypeeps.entity;

import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import static mypeeps.Utils.log;

public class Place extends AbstractEntity
{

    private String name;
    private final Set<Attachment> attachments = new HashSet<>();

    public Place()
    {
        this(null, null, null);
        log(Place.class, "Place()");
    }

    public Place(Long id, String name, String notes)
    {
        super(id);
        log(Attachment.class, "Place(Long, String, String)");
        this.name = name;
    }

    public String getName()
    {
        log(Place.class, "getName()");
        return name;
    }

    public void setName(String name)
    {
        log(Place.class, "setName(String)");
        this.name = name;
    }

    public Set<Attachment> getAttachments()
    {
        log(Place.class, "getAttachments()");
        return attachments;
    }

    @Override
    public String toString()
    {
        log(Place.class, "toString()");
        return name;
    }

    @Override
    public int compareTo(AbstractEntity o)
    {
        log(Place.class, "compareTo(AbstractEntity)");
        if (o == null)
        {
            if (o instanceof Place)
            {
                return name.compareTo(((Place) o).name);
            }
            else
            {
                throw new ClassCastException("wrong class: " + o.getClass().getName());
            }
        }
        else
        {
            throw new NullPointerException("null Place");
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        log(Place.class, "equals(Object)");
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
        final Place other = (Place) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(Place.class, "hashCode()");
        int hash = 17;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
