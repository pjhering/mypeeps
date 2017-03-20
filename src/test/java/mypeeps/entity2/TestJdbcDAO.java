package mypeeps.entity2;

public class TestJdbcDAO
{
    public static void main(String[] args)
    {
        try
        {
            JdbcDAO dao = new JdbcDAO("org.h2.Driver", "jdbc:h2:~/mypeeps/h2", "mypeeps", "mypeeps");
            dao.executeScript(dao.loadScript("/drop-h2.ddl"));
            dao.executeScript(dao.loadScript("/create-h2.ddl"));
            dao.loadStatements("/statements.properties");
            dao.shutdown();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
