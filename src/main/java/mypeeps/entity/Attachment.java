package mypeeps.entity;

import java.util.Objects;
import static mypeeps.Utils.log;

public class Attachment extends AbstractEntity
{

    private String fileName;
    private String description;

    public Attachment()
    {
        this(null, null, null);
        log(Attachment.class, "Attachment()");
    }

    public Attachment(Long id, String fname, String descr)
    {
        super(id);
        log(Attachment.class, "Attachment(Long, String, String)");
        this.fileName = fname;
        this.description = descr;
    }

    public String getFileName()
    {
        log(Attachment.class, "getFileName()");
        return fileName;
    }

    public void setFileName(String fileName)
    {
        log(Attachment.class, "setFileName(String)");
        this.fileName = fileName;
    }

    public String getDescription()
    {
        log(Attachment.class, "getDescription()");
        return description;
    }

    public void setDescription(String description)
    {
        log(Attachment.class, "setDescription(String)");
        this.description = description;
    }

    @Override
    public String toString()
    {
        log(Attachment.class, "toString()");
        return fileName;
    }

    @Override
    public int compareTo(AbstractEntity o)
    {
        log(Attachment.class, "compareTo(AbstractEntity)");
        if (o != null)
        {
            if (o instanceof Attachment)
            {
                return fileName.compareTo(((Attachment) o).fileName);
            }
            else
            {
                throw new ClassCastException("wrong class: " + o.getClass().getName());
            }
        }
        else
        {
            throw new NullPointerException("null Atachment");
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        log(Attachment.class, "equals(Object)");
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
        final Attachment other = (Attachment) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(Attachment.class, "hashCode()");
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
