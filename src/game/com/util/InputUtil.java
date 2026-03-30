package game.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.com.exception.PlayerException;

/**
 * 콘솔 창에서 사용자에게 입력을 받는 메소드
 * 콘솔 창이라는 특성상 입력만 받는게 아니라
 * 사용자에게 안내 메시지도 띄워야 하는 상황
 * 즉 출력과 입력이 분리되지 못하고 있다.... 
 */
public final class InputUtil
{
	// 버퍼드리더 인스턴스
	private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	
	//  상수
	private static final String EXIT_CODE = "exit";
	private static final String EXIT_MSG = "사용자 요구로 프로그램을 종료합니다";
	
	private static final String NUMBER_ERR_MSG = "숫자만 입력해주세요.";
	private static final String NUMBER_RANGE_ERR_MSG = "%d ~ %d 사이로 입력해주세요";
	
	private static final String BOOLEAN_ERR_MSG = "%s 또는 %s 를 입력해주세요";
	
	
	// 유틸리티 특성 상 인스턴스 생성을 막기 위한 생성자 프라이빗 처리
	private InputUtil() {}
	
	/*
	 * 사용자에게 안내메시지를 띄우고 엔터를 누를때 까지 대기하는 메소드
	 * 입력 값이 null 일 경우 PlayerException 예외를 던진다
	 */
	public static void pause(String prompt) throws PlayerException
	{
		System.out.print(prompt);
		
		try
		{
			if(BR.readLine() == null)
			{
				throw new PlayerException(EXIT_MSG);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.toString());
		}
	}
	
	/*
	 * 사용자에게 안내메시지를 띄우고 문자열을 입력받는 메소드
	 * 탈출 코드 혹은 null 입력 시 PlayerException 예외를 던진다
	 */
	public static String readString(String prompt) throws PlayerException
	{
		System.out.print(prompt + " : ");
		
		try
		{
			String str = BR.readLine();
			
			if(str == null || EXIT_CODE.equals(str))
			{
				throw new PlayerException(EXIT_MSG);
			}
			
			return str;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.toString());
		}
	}
	
	/*
	 * 사용자에게 안내메시지를 띄우고 int 를 입력받는 메소드
	 * 내부적으로 readString 를 참조한다
	 * 숫자가 아닌 값을 입력한 경우 안내메시지를 띄우고
	 * 숫자를 입력할때 까지 다시 입력 받는다
	 */
	public static int readInt(String prompt) throws PlayerException
	{
		while(true)
		{
			try
			{
				return Integer.parseInt(readString(prompt));
			} 
			
			catch (NumberFormatException e)
			{
				System.out.println(NUMBER_ERR_MSG);
			}
		}
	}
	
	/*
	 * 사용자에게 안내메시지를 띄우고 int 를 범위내에서 입력받는 메소드
	 * 내부적으로 readString 을 참조한다
	 * 숫자가 아닌 값 혹은 범위를 벗어나는 값을 입력한 경우
	 * 안내 메시지를 띄우고 올바른 값을 입력받을 때 까지 반복한다
	 */
	public static int readInt(String prompt, int min, int max) throws PlayerException
	{
		prompt = String.format(prompt + " (%d~%d)", min,max);
		
		while(true)
		{
			try
			{	
				int number = Integer.parseInt(readString(prompt));
				
				if(number < min || number > max)
				{
					System.out.printf(NUMBER_RANGE_ERR_MSG + "%n",min,max);
				}
				else
				{
					return number;
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println(NUMBER_ERR_MSG);
			}
		}
	}
	
	/*
	 * 사용자에게 안내메시지를 띄우고 boolean 을 입력받는 메소드
	 * 내부적으로 readString 을 참조한다
	 * trueValue 혹은 falseValue 를 입력하지 않으면
	 * 안내메시지를 띄우고 다시 입력받는다
	 */
	public static boolean readBoolean(String prompt, String trueValue, String falseValue) throws PlayerException
	{
		prompt = String.format(prompt + " (%s/%s)", trueValue,falseValue);
		
		while(true)
		{
			String answer = readString(prompt);
			
			if(answer.equals(trueValue))
			{
				return true;
			}
			else if(answer.equals(falseValue))
			{
				return false;
			}
			else
			{
				System.out.printf(BOOLEAN_ERR_MSG + "%n",trueValue,falseValue);
			}
		}
	}
}
