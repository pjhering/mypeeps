package mypeeps.entity2;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class TestJdbcDAO
{
    
    private static final List<Person> people = new ArrayList<>();
    private static final List<Event> events = new ArrayList<>();
    private static final List<File> files = new ArrayList<>();
    
    public static void main(String[] args)
    {
        try
        {
            JdbcDAO dao = new JdbcDAO("org.h2.Driver", "jdbc:h2:~/mypeeps/h2", "mypeeps", "mypeeps");
            dao.executeScript(dao.loadScript("/drop-h2.ddl"));
            dao.executeScript(dao.loadScript("/create-h2.ddl"));
            dao.loadStatements("/statements.properties");
            
            dump(dao);
            
            createPeople(dao);
            addParents(dao);
            addChildren(dao);
            addEvents(dao);
            addFiles(dao);
            
            findPeople(dao);
            findEvents(dao);
            findFiles(dao);
            
            updatePeople(dao);
            updateEvents(dao);
            updateFiles(dao);
            
            dump(dao);
            
            removeChildren(dao);
            removeFiles(dao);
            
            deleteFiles(dao);
            deleteEvents(dao);
            deletePeople(dao);
            
            dump(dao);
            
            dao.shutdown();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void createPeople(JdbcDAO dao) throws DAOException
    {
        people.add(dao.createPerson("John", "Doe", "male", null));
        people.add(dao.createPerson("Jane", "Doe", "female", null));
    }

    private static void addParents(JdbcDAO dao) throws DAOException
    {
        Person mom = dao.createPerson("Grandma", "Doe", "female", "yippee!");
        Person dad = dao.createPerson("Grandpa", "Doe", "male", "yippee!");
        
        dao.addChildTo(mom, people.get(0));
        dao.addChildTo(dad, people.get(0));
    }

    private static void addChildren(JdbcDAO dao) throws DAOException
    {
        Person child = dao.createPerson("Junior", "Doe", "male", "so sweet");
        people.add(child);
        
        dao.addChildTo(people.get(0), child);
        dao.addChildTo(people.get(1), child);
    }

    private static void addEvents(JdbcDAO dao) throws DAOException
    {
        Calendar cal = Calendar.getInstance();
        cal.set(1950, 1, 1);
        
        Event e1 = dao.createEvent(people.get(0), "marriage", cal.getTime(), "Hometown", "beautiful");
        Event e2 = dao.createEvent(people.get(1), "marriage", cal.getTime(), "Hometown", "beautiful");

        events.add(e1);
        events.add(e2);
    }

    private static void addFiles(JdbcDAO dao) throws DAOException
    {
        File f1 = dao.createFile("wedding.png", "John and Jane");
        File f2 = dao.createFile("john-doe.png", "John's Picture");
        files.add(f2);
        File f3 = dao.createFile("jane-doe.png", "Jane's Picture");
        files.add(f3);
        
        dao.addFileToEvent(f1, events.get(0));
        dao.addFileToEvent(f1, events.get(1));
        dao.addFileToPerson(f2, people.get(0));
        dao.addFileToPerson(f3, people.get(1));
    }

    private static void findPeople(JdbcDAO dao) throws DAOException
    {
        out.print("all people: ");
        out.println(dao.findAllPeople());
        
        out.print("parents: ");
        out.println(dao.findParentsOf(people.get(0)));
        
        out.print("children: ");;
        out.println(dao.findChildrenOf(people.get(1)));
    }

    private static void findEvents(JdbcDAO dao) throws DAOException
    {
        out.print("events: ");
        out.println(dao.findEventsFor(people.get(0)));
    }

    private static void findFiles(JdbcDAO dao) throws DAOException
    {
        out.print("person files: ");
        out.println(dao.findFilesForPerson(people.get(1)));
        
        out.print("event files: ");
        out.println(dao.findFilesForEvent(events.get(0)));
    }

    private static void updatePeople(JdbcDAO dao) throws DAOException
    {
        people.get(0).setGivenName("Jack");
        dao.updatePerson(people.get(0));
        
        people.get(1).setGivenName("Jill");
        dao.updatePerson(people.get(1));
    }

    private static void updateEvents(JdbcDAO dao) throws DAOException
    {
        Calendar cal = Calendar.getInstance();
        cal.set(1951, 2, 2);
        
        events.get(0).setDate(cal.getTime());
        dao.updateEvent(events.get(0));
    }

    private static void updateFiles(JdbcDAO dao) throws DAOException
    {
        Set<File> fset = dao.findAllFiles();
        
        for(File f : fset)
        {
            f.setPath(f.getPath().replaceAll("png", "jpg"));
            dao.updateFile(f);
        }
    }

    private static void removeChildren(JdbcDAO dao) throws DAOException
    {
        dao.removeChildFrom(people.get(0), people.get(2));
        dao.removeChildFrom(people.get(1), people.get(2));
    }

    private static void removeFiles(JdbcDAO dao) throws DAOException
    {
        dao.removeFileFromPerson(files.get(0), people.get(0));
        dao.removeFileFromPerson(files.get(1), people.get(1));
    }

    private static void deleteFiles(JdbcDAO dao) throws DAOException
    {
        Set<File> fset = dao.findAllFiles();
        
        for(File f : fset)
        {
            dao.deleteFile(f);
        }
    }

    private static void deleteEvents(JdbcDAO dao) throws DAOException
    {
        Set<Person> pset = dao.findAllPeople();
        
        for(Person p : pset)
        {
            Set<Event> eset = dao.findEventsFor(p);
            
            for(Event e : eset)
            {
                dao.deleteEvent(e);
            }
        }
    }

    private static void deletePeople(JdbcDAO dao) throws DAOException
    {
        Set<Person> pset = dao.findAllPeople();
        
        for(Person p : pset)
        {
            dao.deletePerson(p);
        }
    }

    private static void dump(JdbcDAO dao) throws DAOException
    {
        out.println("dump:");
        Set<Person> pset = dao.findAllPeople();
        for(Person p : pset)
        {
            out.println(p);
            out.println("\t" + dao.findFilesForPerson(p));
            
            Set<Event> eset = dao.findEventsFor(p);
            
            for(Event e : eset)
            {
                out.println("\t" + e);
                out.println("\t\t" + dao.findFilesForEvent(e));
            }
        }
    }
}
