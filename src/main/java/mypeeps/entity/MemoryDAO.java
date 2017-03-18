package mypeeps.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static mypeeps.Utils.log;

public class MemoryDAO implements DAO, Serializable
{

    static final long serialVersionUID = 1L;

    private final Set<Attachment> ATTACHMENTS = new HashSet<>();
    private final Set<Event> EVENTS = new HashSet<>();
    private final Set<Person> PEOPLE = new HashSet<>();
    private final Set<Place> PLACES = new HashSet<>();
    private final File file;
    private long id;

    public MemoryDAO()
    {
        log(MemoryDAO.class, "MemoryDAO()");
        file = null;
    }

    public MemoryDAO(File file)
    {
        log(MemoryDAO.class, "MemoryDAO(File)");
        this.file = file;

        if (file != null)
        {
            load();
        }
    }

    @Override
    public void shutDown()
    {
        log(MemoryDAO.class, "shutDown()");
        if (file != null)
        {
            store();
        }
    }

    private void load()
    {
        log(MemoryDAO.class, "load()");
        try (FileInputStream stream = new FileInputStream(file))
        {
            ObjectInputStream in = new ObjectInputStream(stream);
            id = in.readLong();
            ATTACHMENTS.addAll((Set) in.readObject());
            EVENTS.addAll((Set) in.readObject());
            PEOPLE.addAll((Set) in.readObject());
            PLACES.addAll((Set) in.readObject());
        }
        catch (Exception ex)
        {
            log(MemoryDAO.class, ex);
        }
    }

    private void store()
    {
        log(MemoryDAO.class, "store()");
        try (FileOutputStream stream = new FileOutputStream(file))
        {
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeLong(id);
            out.writeObject(ATTACHMENTS);
            out.writeObject(EVENTS);
            out.writeObject(PEOPLE);
            out.writeObject(PLACES);
            out.flush();
        }
        catch (Exception ex)
        {
            log(MemoryDAO.class, ex);
        }
    }

    private boolean nullId(AbstractEntity x)
    {
        log(MemoryDAO.class, "nullId(AbstractEntity)");
        if (x.getId() != null)
        {
            throw new DAOException("id != null");
        }

        return true;
    }

    private boolean nonNullId(AbstractEntity x)
    {
        log(MemoryDAO.class, "nonNullId(AbstractEntity)");
        if (x.getId() == null)
        {
            throw new DAOException("id == null");
        }

        return true;
    }

    @Override
    public Attachment create(Attachment x)
    {
        log(MemoryDAO.class, "create(Attachment)");
        if (nullId(x))
        {
            x.setId(id++);
            ATTACHMENTS.add(x);
        }

        return x;
    }

    @Override
    public Event create(Event x)
    {
        log(MemoryDAO.class, "create(Event)");
        if (nullId(x))
        {
            x.setId(id++);
            EVENTS.add(x);

            if (x.getPlace().getId() == null)
            {
                create(x.getPlace());
            }
            else
            {
                if (!PLACES.contains(x.getPlace()))
                {
                    PLACES.add(x.getPlace());
                }
            }

            x.getAttachments().forEach(a ->
            {
                if (a.getId() == null)
                {
                    create(a);
                }
                else if(!ATTACHMENTS.contains(a))
                {
                    ATTACHMENTS.add(a);
                }
            });
        }

        return x;
    }

    @Override
    public Person create(Person x)
    {
        log(MemoryDAO.class, "create(Person)");
        if (nullId(x))
        {
            x.setId(id++);
            PEOPLE.add(x);
        }

        x.getChildren().forEach(child ->
        {
            if (child.getId() == null)
            {
                create(child);
            }
            else
            {
                if (!PEOPLE.contains(child))
                {
                    PEOPLE.add(child);
                }
            }
        });

        x.getParents().forEach(parent ->
        {
            if (parent.getId() == null)
            {
                create(parent);
            }
            else
            {
                if (!PEOPLE.contains(parent))
                {
                    PEOPLE.add(parent);
                }
            }
        });

        x.getEvents().forEach(event ->
        {
            if (event.getId() == null)
            {
                create(event);
            }
            else
            {
                if (!EVENTS.contains(event))
                {
                    EVENTS.add(event);
                }
            }
        });

        x.getAttachments().forEach(file ->
        {
            if (file.getId() == null)
            {
                create(file);
            }
            else
            {
                if (!ATTACHMENTS.contains(file))
                {
                    ATTACHMENTS.add(file);
                }
            }
        });

        return x;
    }

    @Override
    public Place create(Place x)
    {
        log(MemoryDAO.class, "create(Place)");
        if (nullId(x))
        {
            x.setId(id++);
            PLACES.add(x);
        }

        x.getAttachments().forEach(file ->
        {
            if (file.getId() == null)
            {
                create(file);
            }
            else
            {
                if (!ATTACHMENTS.contains(file))
                {
                    ATTACHMENTS.add(file);
                }
            }
        });

        return x;
    }

    @Override
    public boolean delete(Attachment x)
    {
        log(MemoryDAO.class, "delete(Attachment)");
        if (nonNullId(x))
        {
            return ATTACHMENTS.remove(x);
        }

        return false;
    }

    @Override
    public boolean delete(Event x)
    {
        log(MemoryDAO.class, "delete(Event)");
        if (nonNullId(x))
        {
            return EVENTS.remove(x);
        }

        return false;
    }

    @Override
    public boolean delete(Person x)
    {
        log(MemoryDAO.class, "delete(Person)");
        if (nonNullId(x))
        {
            PEOPLE.forEach(p ->
            {
                p.getChildren().remove(x);
                p.getParents().remove(x);
            });
            
            return PEOPLE.remove(x);
        }

        return false;
    }

    @Override
    public boolean delete(Place x)
    {
        log(MemoryDAO.class, "delete(Place)");
        if (nonNullId(x))
        {
            PLACES.remove(x);

            Set<Event> dead = new HashSet<>();

            EVENTS.forEach(event ->
            {
                if (x.equals(event.getPlace()))
                {
                    dead.add(event);
                }
            });

            dead.forEach(event -> delete(event));
        }

        return false;
    }

    @Override
    public boolean update(Attachment x)
    {
        log(MemoryDAO.class, "update(Attachment)");
        return nonNullId(x) && ATTACHMENTS.contains(x);
    }

    @Override
    public boolean update(Event x)
    {
        log(MemoryDAO.class, "update(Event)");
        if (nonNullId(x))
        {
            if (EVENTS.contains(x))
            {
                x.getAttachments().forEach(file ->
                {
                    if (file.getId() != null)
                    {
                        update(file);
                    }
                    else
                    {
                        create(file);
                    }
                });
            }
        }

        return false;
    }

    @Override
    public boolean update(Person x)
    {
        log(MemoryDAO.class, "update(Person)");
        if (nonNullId(x))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Place x)
    {
        log(MemoryDAO.class, "update(Place)");
        if (nonNullId(x))
        {
            if (PLACES.contains(x))
            {
                x.getAttachments().forEach(file ->
                {
                    if (file.getId() != null)
                    {
                        update(file);
                    }
                    else
                    {
                        create(file);
                    }
                });
            }
        }

        return false;
    }

    @Override
    public List<Attachment> readAttachments()
    {
        log(MemoryDAO.class, "readAttachments()");
        return new ArrayList<>(ATTACHMENTS);
    }

    @Override
    public List<Event> readEvents()
    {
        log(MemoryDAO.class, "readEvents()");
        return new ArrayList<>(EVENTS);
    }

    @Override
    public List<Person> readPeople()
    {
        log(MemoryDAO.class, "readPeople()");
        return new ArrayList<>(PEOPLE);
    }

    @Override
    public List<Place> readPlaces()
    {
        log(MemoryDAO.class, "readPlaces()");
        return new ArrayList<>(PLACES);
    }
}
