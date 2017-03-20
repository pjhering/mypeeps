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
    }
    
    private Set<Person> people(PreparedStatement ps) throws SQLException
    {
    }
    
    private Event event(ResultSet rs) throws SQLException
    {
    }
    
    private Set<Event> events(PreparedStatement ps) throws SQLException
    {
    }
    
    private File file(ResultSet rs) throws SQLException
    {
    }
    
    private Set<File> files(PreparedStatement ps) throws SQLException
    {
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
    }

    @Override
    public Event createEvent(Person person, String title, Date date, String place, String notes) throws DAOException
    {
    }

    @Override
    public File createFile(String path, String description) throws DAOException
    {
    }

    @Override
    public boolean addChildTo(Person parent, Person child) throws DAOException
    {
    }

    @Override
    public boolean addFileToPerson(File file, Person person) throws DAOException
    {
    }

    @Override
    public boolean addFileToEvent(File file, Event event) throws DAOException
    {
    }

    @Override
    public Set<Person> findAllPeople() throws DAOException
    {
    }

    @Override
    public Set<Person> findChildrenOf(Person person) throws DAOException
    {
    }

    @Override
    public Set<Person> findParentsOf(Person person) throws DAOException
    {
    }

    @Override
    public Set<Event> findEventsFor(Person person) throws DAOException
    {
    }

    @Override
    public Set<File> findAllFiles() throws DAOException
    {
    }

    @Override
    public Set<File> findFilesForPerson(Person person) throws DAOException
    {
    }

    @Override
    public Set<File> findFilesForEvent(Event event) throws DAOException
    {
    }

    @Override
    public boolean updatePerson(Person person) throws DAOException
    {
    }

    @Override
    public boolean updateEvent(Event event) throws DAOException
    {
    }

    @Override
    public boolean updateFile(File file) throws DAOException
    {
    }

    @Override
    public boolean removeChildFrom(Person parent, Person child) throws DAOException
    {
    }

    @Override
    public boolean removeFileFromPerson(File file, Person person) throws DAOException
    {
    }

    @Override
    public boolean removeFileFromEvent(File file, Event event) throws DAOException
    {
    }

    @Override
    public boolean deletePerson(Person person) throws DAOException
    {
    }

    @Override
    public boolean deleteEvent(Event event) throws DAOException
    {
    }

    @Override
    public boolean deleteFile(File file) throws DAOException
    {
    }
}
