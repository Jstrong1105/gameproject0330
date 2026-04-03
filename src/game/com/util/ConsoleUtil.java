package game.com.util;

/**
 * 콘솔창을 컨트롤 하는 유틸리티
 */
public final class ConsoleUtil
{
	private ConsoleUtil() {}
	
	/*
	 * 콘솔창을 지우는 메소드
	 * 환경에 따라 안될 수 있음
	 */
	public static void clear()
	{
		System.out.print("\033[H\033[2J\033[3J");
		System.out.flush();
	}
}
