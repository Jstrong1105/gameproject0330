package game.com.main;

import game.com.domain.base.GameApp;
import game.com.domain.minesweeper.MinesweeperApp;

public class Launcher
{
	public static void main(String[] args)
	{
		GameApp app = new MinesweeperApp();
		
		app.run();
	}
}
