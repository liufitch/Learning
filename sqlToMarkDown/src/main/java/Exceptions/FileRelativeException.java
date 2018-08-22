package Exceptions;


/**
 * have no  file Exception
 */
public class FileRelativeException extends Exception{
    static final long serialVersionUID = 1L;
    public FileRelativeException() { super(); }
    public FileRelativeException(String message) { super(message); }
    public FileRelativeException(String message, Throwable cause) { super(message, cause); }
    public FileRelativeException(Throwable cause) { super(cause); }
}
