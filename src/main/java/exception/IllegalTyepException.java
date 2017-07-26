package exception;

public class IllegalTyepException extends RuntimeException  {

	private static final long serialVersionUID = -4195520315882158218L;
	public IllegalTyepException(){
		super();
	}
	
	public IllegalTyepException(String msg)
	{
		super(msg);
	}
}
