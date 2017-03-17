package mypeeps.entity;

import java.io.Serializable;
import java.util.Objects;
import static mypeeps.Utils.log;

public abstract class AbstractEntity implements Serializable, Comparable<AbstractEntity>
{

    static final long serialVersionUID = 1L;

    protected Long id;

    public AbstractEntity()
    {
        this(null);
        log(AbstractEntity.class, "AbstractEntity()");
    }

    public AbstractEntity(Long id)
    {
        log(AbstractEntity.class, "AbstractEntity(Long)");
        this.id = id;
    }

    public Long getId()
    {
        log(AbstractEntity.class, "getId()");
        return id;
    }

    public void setId(Long id)
    {
        log(AbstractEntity.class, "setId(Long)");
        this.id = id;
    }

    @Override
    public boolean equals(Object obj)
    {
        log(AbstractEntity.class, "equals(Object)");
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
        final AbstractEntity other = (AbstractEntity) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(AbstractEntity.class, "hashCode()");
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
