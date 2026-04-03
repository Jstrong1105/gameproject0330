package game.com.util;

/**
 * 쓰레드 유틸리티
 */
public final class ThreadUtil
{
	private ThreadUtil() {}
	
	// 쓰레드를 입력받은 시간만큼 중단하는 메소드
	public static void sleep(long millisecond)
	{
		try
		{
			Thread.sleep(millisecond);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	// 입력받은 초만큼 남은 시간을 출력하면서 시간을 중단하는 메소드
	public static void sleepCountDown(int second)
	{
		try
		{
			for(int i = second; i > 0; i--)
			{
				System.out.printf("\r%2d초 남았습니다",i);
				Thread.sleep(1000);
			}
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}
