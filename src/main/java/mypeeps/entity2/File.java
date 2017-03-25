package mypeeps.entity2;

import java.util.Objects;
import static mypeeps.Utils.log;

public class File implements Comparable<File>
{

    private Long id;
    private String path;
    private String description;

    public File(Long id, String path, String description)
    {
        log(File.class, "File(Long, String, String)");

        this.id = id;
        this.path = path;
        this.description = description;
    }

    public Long getId()
    {
        log(File.class, "getId()");

        return id;
    }

    public void setId(Long id)
    {
        log(File.class, "setId(Long)");

        this.id = id;
    }

    public String getPath()
    {
        log(File.class, "getPath()");

        return path;
    }

    public void setPath(String path)
    {
        log(File.class, "setPath(String)");

        this.path = path;
    }

    public String getDescription()
    {
        log(File.class, "getDescription()");

        return description;
    }

    public void setDescription(String description)
    {
        log(File.class, "setDescription(String)");

        this.description = description;
    }

    @Override
    public boolean equals(Object obj)
    {
        log(File.class, "equals(Object)");

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
        final File other = (File) obj;
        if(!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        log(File.class, "hashCode()");

        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public int compareTo(File o)
    {
        log(File.class, "compareTo(File)");

        return path.compareTo(o.path);
    }

    @Override
    public String toString()
    {
        log(File.class, "toString()");

        java.io.File temp = new java.io.File(path);
        return temp.getName();
    }
}
