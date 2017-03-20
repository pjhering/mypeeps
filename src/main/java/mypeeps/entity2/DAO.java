package mypeeps.entity2;

import java.util.Date;
import java.util.Set;

public interface DAO
{

    public void shutdown() throws DAOException;
    
    public Person createPerson(String gname, String fname, String gender, String notes) throws DAOException;

    public Event createEvent(Person person, String title, Date date, String place, String notes) throws DAOException;

    public File createFile(String path, String description) throws DAOException;

    public boolean addChildTo(Person parent, Person child) throws DAOException;
    
    public boolean addFileToPerson(File file, Person person) throws DAOException;
    
    public boolean addFileToEvent(File file, Event event) throws DAOException;

    public Set<Person> findAllPeople() throws DAOException;

    public Set<Person> findChildrenOf(Person person) throws DAOException;

    public Set<Person> findParentsOf(Person person) throws DAOException;

    public Set<Event> findEventsFor(Person person) throws DAOException;

    public Set<File> findAllFiles() throws DAOException;

    public Set<File> findFilesForPerson(Person person) throws DAOException;

    public Set<File> findFilesForEvent(Event event) throws DAOException;

    public boolean updatePerson(Person person) throws DAOException;

    public boolean updateEvent(Event event) throws DAOException;

    public boolean updateFile(File file) throws DAOException;

    public boolean removeChildFrom(Person parent, Person child) throws DAOException;
    
    public boolean removeFileFromPerson(File file, Person person) throws DAOException;
    
    public boolean removeFileFromEvent(File file, Event event) throws DAOException;

    public boolean deletePerson(Person person) throws DAOException;

    public boolean deleteEvent(Event event) throws DAOException;

    public boolean deleteFile(File file) throws DAOException;
}
