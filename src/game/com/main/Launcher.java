package game.com.main;

import java.util.ArrayList;
import java.util.List;

import game.com.domain.base.GameApp;
import game.com.domain.memorygame.MemoryGameApp;
import game.com.domain.minesweeper.MinesweeperApp;
import game.com.util.ConsoleUtil;
import game.com.util.InputUtil;

public class Launcher
{
	private static List<GameApp> games = new ArrayList<>();
	
	public static void main(String[] args)
	{
		games.add(new MinesweeperApp());
		games.add(new MemoryGameApp());
		
		while(true)
		{
			ConsoleUtil.clear();
			
			int index = 1;
			
			int exit = games.size() + 1;
			
			System.out.println("===============게임런처===============");
			System.out.println("======================================");
			
			for(GameApp app : games)
			{
				System.out.println(index++ + ". " + app.getGameName() + " : " + app.getGameGuide());
			}
		
			System.out.println(exit + ". 종료");
			
			int answer = InputUtil.readInt("번호를 입력",1,exit);
		
			if(answer == exit)
			{
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			else
			{
				games.get(answer-1).run();
			}
		}
	}
}
