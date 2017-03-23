package mypeeps.entity2;

import java.util.Objects;

public class File implements Comparable<File>
{
    
    private Long id;
    private String path;
    private String description;
    
    public File(Long id, String path, String description)
    {
        this.id = id;
        this.path = path;
        this.description = description;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        final File other = (File) obj;
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
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(File o)
    {
        return path.compareTo(o.path);
    }
    
    @Override
    public String toString()
    {
        java.io.File temp = new java.io.File(path);
        return temp.getName();
    }
}
