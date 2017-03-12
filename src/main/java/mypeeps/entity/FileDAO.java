package mypeeps.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.TreeSet;
import static mypeeps.Utils.log;

public class FileDAO implements DAO
{

    private final File FILE;
    private long id;
    private final Set<Attachment> ATTACHMENTS = new TreeSet<>();
    private final Set<Event> EVENTS = new TreeSet<>();
    private final Set<Person> PEOPLE = new TreeSet<>();
    private final Set<Place> PLACES = new TreeSet<>();

    public FileDAO(File file)
    {
        log(FileDAO.class, "FileDAO(File)");
        this.FILE = requireNonNull(file);
        load();
    }

    private void nullId(Long id)
    {
        log(FileDAO.class, "nullId(Long)");
        if(id != null)
        {
            throw new RuntimeException("id != null");
        }
    }

    private void nonNullId(Long id)
    {
        log(FileDAO.class, "nonNullId(Long)");
        if(id == null)
        {
            throw new RuntimeException("id == null");
        }
    }

    @Override
    public Attachment create(Attachment x)
    {
        log(FileDAO.class, "create(Attachment)");
        nullId(x.getId());
        x.setId(id++);
        ATTACHMENTS.add(x);
        return x;
    }

    @Override
    public List<Attachment> readAttachments()
    {
        log(FileDAO.class, "readAttachments()");
        return new ArrayList<>(ATTACHMENTS);
    }

    @Override
    public boolean update(Attachment x)
    {
        log(FileDAO.class, "update(Attachment)");
        nonNullId(x.getId());
        return true;
    }

    @Override
    public boolean delete(Attachment x)
    {
        log(FileDAO.class, "delete(Attachment)");
        ATTACHMENTS.remove(x);
        EVENTS.forEach(event ->
        {
            event.getAttachments().remove(x);
        });
        PEOPLE.forEach(person ->
        {
            person.getAttachments().remove(x);
        });
        PLACES.forEach(place ->
        {
            place.getAttachments().remove(x);
        });
        return true;
    }

    @Override
    public Event create(Event x)
    {
        log(FileDAO.class, "create(Event)");
        nullId(x.getId());
        x.setId(id++);
        EVENTS.add(x);
        return x;
    }

    @Override
    public List<Event> readEvents()
    {
        log(FileDAO.class, "readEvents()");
        return new ArrayList<>(EVENTS);
    }

    @Override
    public boolean update(Event x)
    {
        log(FileDAO.class, "update(Event)");
        nonNullId(x.getId());
        return true;
    }

    @Override
    public boolean delete(Event x)
    {
        log(FileDAO.class, "delete(Event)");
        EVENTS.remove(x);
        PEOPLE.forEach(person ->
        {
            person.getEvents().remove(x);
        });
        return true;
    }

    @Override
    public Person create(Person x)
    {
        log(FileDAO.class, "create(Person)");
        nullId(x.getId());
        x.setId(id++);
        PEOPLE.add(x);
        return x;
    }

    @Override
    public List<Person> readPeople()
    {
        log(FileDAO.class, "readPeople()");
        return new ArrayList<>(PEOPLE);
    }

    @Override
    public boolean update(Person x)
    {
        log(FileDAO.class, "update(Person)");
        nonNullId(x.getId());
        return true;
    }

    @Override
    public boolean delete(Person x)
    {
        log(FileDAO.class, "delete(Person)");
        EVENTS.removeAll(x.getEvents());
        PEOPLE.remove(x);
        PEOPLE.forEach(person ->
        {
            person.getChildren().remove(x);
            person.getParents().remove(x);
        });
        return true;
    }

    @Override
    public Place create(Place x)
    {
        log(FileDAO.class, "create(Place)");
        nullId(x.getId());
        x.setId(id++);
        PLACES.add(x);
        return x;
    }

    @Override
    public List<Place> readPlaces()
    {
        log(FileDAO.class, "readPlaces()");
        return new ArrayList<>(PLACES);
    }

    @Override
    public boolean update(Place x)
    {
        log(FileDAO.class, "update(Place)");
        nonNullId(x.getId());
        return true;
    }

    @Override
    public boolean delete(Place x)
    {
        log(FileDAO.class, "delete(Place)");
        PLACES.remove(x);
        Set<Event> dead = new HashSet<>();
        EVENTS.forEach(event ->
        {
            if(event.getPlace().equals(x))
            {
                dead.add(event);
            }
        });
        dead.forEach(e ->
        {
            PEOPLE.forEach(p ->
            {
               p.getEvents().remove(e);
            });
        });
        EVENTS.removeAll(dead);
        return true;
    }

    @Override
    public void shutDown()
    {
        log(FileDAO.class, "shutDown()");
        store();
    }

    private void load()
    {
        log(FileDAO.class, "load()");
        try(FileInputStream stream = new FileInputStream(FILE))
        {
            ObjectInputStream in = new ObjectInputStream(stream);
            id = in.readLong();
            ATTACHMENTS.addAll((Set<Attachment>) in.readObject());
            EVENTS.addAll((Set<Event>) in.readObject());
            PEOPLE.addAll((Set<Person>) in.readObject());
            PLACES.addAll((Set<Place>) in.readObject());
        }
        catch(Exception ex)
        {
            log(FileDAO.class, ex);
        }
    }

    private void store()
    {
        log(FileDAO.class, "store()");
        try(FileOutputStream stream = new FileOutputStream(FILE))
        {
            ObjectOutputStream out = new ObjectOutputStream(stream);
            out.writeLong(id);
            out.writeObject(ATTACHMENTS);
            out.writeObject(EVENTS);
            out.writeObject(PEOPLE);
            out.writeObject(PLACES);
        }
        catch(Exception ex)
        {
            log(FileDAO.class, ex);
        }
    }
}
