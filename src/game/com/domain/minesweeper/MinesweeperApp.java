package game.com.domain.minesweeper;

import game.com.domain.base.GameApp;

class MinesweeperApp implements GameApp
{
	private static final String GAME_NAME = "지뢰찾기";
	private static final String GAME_GUIDE = "지뢰를 피하세요!";
	
	@Override
	public String getGameName()
	{
		return GAME_NAME;
	}

	@Override
	public String getGameGuide()
	{
		return GAME_GUIDE;
	}
	
	// 실행 흐름
	@Override
	public void run()
	{
		setOption();
		
		do
		{
			initialize();
			
			while(running)
			{
				render();		// 화면 출력
				cellChoice();	// 셀 선택
				render();		// 선택된 셀 강조된 화면 출력
				actionChoice();	// 선택된 셀에 대해 액션 선택
				clearCheck();	// 클리어 여부 확인		
			}
			
		} while (restart());
	}
	
	// 실행 흐름
	private boolean running;
	
	// 다시 시작
	private boolean restart()
	{
		return false;
	}
	
	// 옵션 처리
	private void setOption()
	{
		
	}
	
	// 초기화
	private void initialize()
	{
		
	}
	
	// 화면 출력
	private void render()
	{
		
	}
	
	// 셀 선택
	private void cellChoice()
	{
		
	}
	
	// 액션 선택
	private void actionChoice()
	{
		
	}
	
	// 종료 처리
	private void clearCheck()
	{
		
	}
}
