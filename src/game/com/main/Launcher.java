package game.com.main;

import java.util.ArrayList;
import java.util.List;

import game.com.domain.base.GameApp;
import game.com.domain.minesweeper.MinesweeperApp;
import game.com.util.InputUtil;

public class Launcher
{
	private static List<GameApp> games = new ArrayList<>();
	
	public static void main(String[] args)
	{
		games.add(new MinesweeperApp());
		
		while(true)
		{
			int index = 1;
			
			for(GameApp app : games)
			{
				System.out.println(index++ + ". " + app.getGameName() + " : " + app.getGameGuide());
			}
		
			int answer = InputUtil.readInt("번호를 입력",1,games.size()+1);
		
			if(answer == games.size()+1)
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
