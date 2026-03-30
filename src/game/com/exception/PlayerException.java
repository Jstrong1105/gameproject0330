package game.com.exception;

public class PlayerException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public PlayerException(String msg)
	{
		super(msg);
	}
}
