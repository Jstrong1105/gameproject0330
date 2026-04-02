package game.com.domain.base;

import game.com.util.InputUtil;

/**
 * 게임 흐름을 정의한 상위 메소드
 * 이 구조가 아닌 게임은 해당 클래스를
 * 상속 하지 말 것
 */
public abstract class GameTemplate implements GameApp
{
	@Override
	public void run()
	{
		// 옵션 수정은 처음 한번만 실행함
		setOption();
		
		do
		{
			// 초기화는 매 게임 시작마다 실행함
			initialize();
			running = true;
			
			while(running)
			{
				render();
				playerInput();
				update();
			}
			
		} while (restart());
	}
	
	private boolean running = false;
	
	/*
	 * 게임 종료
	 */
	protected void endGame()
	{
		running = false;
	}
	
	/*
	 * 다시 시작하기
	 */
	protected boolean restart()
	{
		return InputUtil.readBoolean("다시 시작하시겠습니까?", "Y", "N");
	}
	
	/*
	 * 옵션 컨트롤하기
	 */
	protected abstract void setOption();
	
	/*
	 * 초기화하기
	 */
	protected abstract void initialize();
	
	/*
	 * 화면 출력하기
	 */
	protected abstract void render();
	
	/*
	 * 사용자 입력
	 */
	protected abstract void playerInput();
	
	/*
	 * 사용자 입력을 게임에 반영
	 */
	protected abstract void update();
}
