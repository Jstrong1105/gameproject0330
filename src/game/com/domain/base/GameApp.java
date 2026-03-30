package game.com.domain.base;

/**
 * 모든 게임이 상속받아야 하는 인터페이스
 */
public interface GameApp
{
	// 게임 실행
	void run();
	
	// 게임 이름 
	String getGameName();
	
	// 게임 설명
	String getGameExplain();
}
