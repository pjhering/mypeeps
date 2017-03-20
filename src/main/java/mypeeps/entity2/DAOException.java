package mypeeps.entity2;

public class DAOException extends Exception
{

    public DAOException()
    {
    }

    public DAOException(String msg)
    {
        super(msg);
    }

    public DAOException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public DAOException(String msg, Throwable cause, boolean supress, boolean write)
    {
        super(msg, cause, supress, write);
    }

    public DAOException(Throwable cause)
    {
        super(cause);
    }
}
