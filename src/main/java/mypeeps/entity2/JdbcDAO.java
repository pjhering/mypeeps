package mypeeps.entity2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JdbcDAO implements DAO
{

    private final Connection JDBC;
    private final Map<String, PreparedStatement> STMTS;

    public JdbcDAO(String driverClassName, String dbUrl, String user, String pass) throws Exception
    {
        Class driverClass = Class.forName(driverClassName);
        Driver driver = (Driver) driverClass.newInstance();
        DriverManager.registerDriver(driver);
        JDBC = DriverManager.getConnection(dbUrl, user, pass);
        JDBC.setAutoCommit(true);

        STMTS = new HashMap<>();
    }
    
    public void loadStatements(String statements) throws IOException, SQLException
    {
        URL url = getClass().getResource(statements);
        Properties props = new Properties();
        props.load(url.openStream());
        
        for (String key : props.stringPropertyNames())
        {
            out.println(key);
            out.println(STMTS.get(key));
            out.println(null != STMTS.put(key, JDBC.prepareStatement(props.getProperty(key))));
        }
    }

    public void executeScript(List<String> statements)
    {
        for (String sql : statements)
        {
            try (Statement s = JDBC.createStatement())
            {
                out.println(sql);
                boolean result = s.execute(sql);
                out.println(result);
            }
            catch (SQLException ex)
            {
                ex.printStackTrace(out);
            }
        }
    }

    public List<String> loadScript(String path)
    {
        URL url = getClass().getResource(path);
        List<String> list = new ArrayList<>();
        
        try(InputStreamReader reader = new InputStreamReader(url.openStream()))
        {
            BufferedReader buffer = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = null;
            
            while((line = buffer.readLine()) != null)
            {
                line = line.trim();
                builder.append(line).append('\n');
                
                if(line.endsWith(";"))
                {
                    list.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return list;
    }

    private PreparedStatement prepare(String key) throws SQLException
    {
        PreparedStatement ps = STMTS.get("person.create");
        ps.clearParameters();
        return ps;
    }
    
    private Person person(ResultSet rs) throws SQLException
    {
        long id = rs.getLong("id");
        String gname = rs.getString("givenname");
        String fname = rs.getString("familyname");
        String gender = rs.getString("gender");
        String notes = rs.getString("notes");
        
        return new Person(id, gname, fname, gender, notes);
    }
    
    private Set<Person> people(PreparedStatement ps) throws SQLException
    {
        try(ResultSet rs = ps.executeQuery())
        {
            Set<Person> set = new HashSet<>();
            
            while(rs.next())
            {
                set.add(person(rs));
            }
            
            return set;
        }
    }
    
    private Event event(Person person, ResultSet rs) throws SQLException
    {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        Date date = rs.getDate("occurred");
        String place = rs.getString("place");
        String notes = rs.getString("notes");
        
        return new Event(id, person, title, date, place, notes);
    }
    
    private Set<Event> events(Person person, PreparedStatement ps) throws SQLException
    {
        try(ResultSet rs = ps.executeQuery())
        {
            Set<Event> set = new HashSet<>();
            
            while(rs.next())
            {
                set.add(event(person, rs));
            }
            
            return set;
        }
    }
    
    private File file(ResultSet rs) throws SQLException
    {
        long id = rs.getLong("id");
        String path = rs.getString("path");
        String descr = rs.getString("description");
        
        return new File(id, path, descr);
    }
    
    private Set<File> files(PreparedStatement ps) throws SQLException
    {
        try(ResultSet rs = ps.executeQuery())
        {
            Set<File> set = new HashSet<>();
            
            while(rs.next())
            {
                set.add(file(rs));
            }
            
            return set;
        }
    }
    
    private long id(PreparedStatement ps) throws SQLException
    {
        try(ResultSet rs = ps.getGeneratedKeys())
        {
            rs.next();
            long id = rs.getLong(1);
            return id;
        }
    }
    
    @Override
    public void shutdown() throws DAOException
    {
        try
        {
            JDBC.close();
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Person createPerson(String gname, String fname, String gender, String notes) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("createPerson");
            ps.setString(1, gname);
            ps.setString(2, fname);
            ps.setString(3, gender);
            ps.setString(4, notes);
            
            int count = ps.executeUpdate();
            
            if(count == 1)
            {
                long id = id(ps);
                return new Person(id, gname, fname, gender, notes);
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Event createEvent(Person person, String title, Date date, String place, String notes) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("createEvent");
            ps.setLong(1, person.getId());
            ps.setString(2, title);
            ps.setDate(3, new java.sql.Date(date.getTime()));
            ps.setString(4, place);
            ps.setString(5, notes == null ? "" : notes);
            
            int count = ps.executeUpdate();
            
            if(count == 1)
            {
                long id = id(ps);
                return new Event(id, person, title, date, place, notes);
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public File createFile(String path, String description) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("createFile");
            ps.setString(1, path);
            ps.setString(2, description);
            
            int count = ps.executeUpdate();
            
            if(count == 1)
            {
                long id = id(ps);
                return new File(id, path, description);
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean addChildTo(Person parent, Person child) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("addChildTo");
            ps.setLong(1, parent.getId());
            ps.setLong(2, child.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean addFileToPerson(File file, Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("addFileToPerson");
            ps.setLong(1, person.getId());
            ps.setLong(2, file.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean addFileToEvent(File file, Event event) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("addFileToEvent");
            ps.setLong(1, event.getId());
            ps.setLong(2, file.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<Person> findAllPeople() throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findAllPeople");
            return people(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<Person> findChildrenOf(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findChildrenOf");
            ps.setLong(1, person.getId());
            return people(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<Person> findParentsOf(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findParentsOf");
            ps.setLong(1, person.getId());
            return people(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<Event> findEventsFor(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findEventsFor");
            ps.setLong(1, person.getId());
            return events(person, ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<File> findAllFiles() throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findAllFiles");
            return files(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<File> findFilesForPerson(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findFilesForPerson");
            ps.setLong(1, person.getId());
            return files(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public Set<File> findFilesForEvent(Event event) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("findFilesForEvent");
            ps.setLong(1, event.getId());
            return files(ps);
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean updatePerson(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("updatePerson");
            ps.setString(1, person.getGivenName());
            ps.setString(2, person.getFamilyName());
            ps.setString(3, person.getGender());
            ps.setString(4, person.getNotes() == null ? "" : person.getNotes());
            ps.setLong(5, person.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean updateEvent(Event event) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("updateEvent");
            ps.setString(1, event.getTitle());
            ps.setDate(2, new java.sql.Date(event.getDate().getTime()));
            ps.setString(3, event.getPlace());
            ps.setString(4, event.getNotes() == null ? "" : event.getNotes());
            ps.setLong(5, event.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean updateFile(File file) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("updateFile");
            ps.setString(1, file.getPath());
            ps.setString(2, file.getDescription());
            ps.setLong(3, file.getId());
            
            int count = ps.executeUpdate();
            return count == 1;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean removeChildFrom(Person parent, Person child) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("removeChildFrom");
            ps.setLong(1, parent.getId());
            ps.setLong(2, child.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean removeFileFromPerson(File file, Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("removeFileFromPerson");
            ps.setLong(1, person.getId());
            ps.setLong(2, file.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean removeFileFromEvent(File file, Event event) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("removeFileFromEvent");
            ps.setLong(1, event.getId());
            ps.setLong(2, file.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean deletePerson(Person person) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("deletePerson");
            ps.setLong(1, person.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean deleteEvent(Event event) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("deleteEvent");
            ps.setLong(1, event.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }

    @Override
    public boolean deleteFile(File file) throws DAOException
    {
        try
        {
            PreparedStatement ps = prepare("deleteFile");
            ps.setLong(1, file.getId());
            
            int count = ps.executeUpdate();
            return count > 0;
        }
        catch(SQLException ex)
        {
            throw new DAOException(ex);
        }
    }
}
