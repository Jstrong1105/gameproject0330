package game.com.util;

/**
 * 콘솔창을 컨트롤 하는 유틸리티
 */
public final class ConsoleUtil
{
	private ConsoleUtil() {}
	
	public static final String COLOR_START = "\033[";
	
	public static final String BOLD = "1";
	
	public static final String AND = ";";
	
	public static final String TEXT_BLACK = "90";
	public static final String TEXT_RED = "91";
	public static final String TEXT_GREEN = "92";
	public static final String TEXT_YELLOW = "93";
	
	public static final String BG_YELLOW = "103";
	public static final String BG_WHITE = "107";
	
	public static final String COLOR_END = "m";
	
	public static final String STYLE_END = "\033[0m";
	
	/*
	 * 콘솔창을 지우는 메소드
	 * 환경에 따라 안될 수 있음
	 */
	public static void clear()
	{
		System.out.println("\033[H\033[2J\033[3J");
	}
}
