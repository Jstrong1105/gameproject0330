package game.com.domain.memorygame;

import java.util.List;

/**
 * 메모리게임보드판이 가져야하는 인터페이스
 */
public interface MemoryGameBoardTemplate
{
	// 열림 여부 확인
	boolean isOpen(int index);
	
	// 한장 열기
	void openCard(int index);
	
	// 한장 숨기기
	void hiddenCard(int index);
	
	// 같은 카드인지 확인하기
	boolean isSameCard(List<Integer> list);
	
	// 전부 열었는지 확인하기
	boolean isClear();
	
	// 출력하기
	void print();
}
