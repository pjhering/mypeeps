package mypeeps.entity;

import java.util.List;

public interface DAO
{

    public void shutDown();

    public Attachment create(Attachment x);

    public List<Attachment> readAttachments();

    public boolean update(Attachment x);

    public boolean delete(Attachment x);

    public Event create(Event x);

    public List<Event> readEvents();

    public boolean update(Event x);

    public boolean delete(Event x);

    public Person create(Person x);

    public List<Person> readPeople();

    public boolean update(Person x);

    public boolean delete(Person x);

    public Place create(Place x);

    public List<Place> readPlaces();

    public boolean update(Place x);

    public boolean delete(Place x);
}
