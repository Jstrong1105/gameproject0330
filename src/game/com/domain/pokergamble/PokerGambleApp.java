package game.com.domain.pokergamble;

import game.com.domain.base.GameTemplate;

/**
 * 포커겜블 구체화 클래스
 */
public class PokerGambleApp extends GameTemplate
{
	private static final String GAME_NAME = "포커겜블";
	private static final String GAME_GUIDE = "포커를 승리해 목표 코인을 달성하세요";
	
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

	@Override
	protected void setOption()
	{
		
	}

	@Override
	protected void initialize()
	{
		
	}

	@Override
	protected void render()
	{
		
	}

	@Override
	protected void playerInput()
	{
		
	}

	@Override
	protected void update()
	{
		
	}
}
