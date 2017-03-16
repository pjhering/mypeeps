package mypeeps.entity;

import static mypeeps.Utils.log;

public class DAOException extends RuntimeException
{

    public DAOException()
    {
        log(DAOException.class, "DAOException()");
    }

    public DAOException(String msg)
    {
        super(msg);
        log(DAOException.class, "DAOException(String)");
    }

    public DAOException(String msg, Throwable cause)
    {
        super(msg, cause);
        log(DAOException.class, "DAOException(String, Throwable)");
    }

    public DAOException(String msg, Throwable cause, boolean suppress, boolean writable)
    {
        super(msg, cause, suppress, writable);
        log(DAOException.class, "DAOException(String, Throwable, boolean, boolean)");
    }

    public DAOException(Throwable cause)
    {
        super(cause);
        log(DAOException.class, "DAOException(Throwable)");
    }
}
