package game.com.domain.minesweeper;

/**
 * 지뢰찾기에서 한칸을 담당하는 클래스
 */
class Cell
{
	// 셀의 상태를 나열한 enum
	// 내부에서만 사용되기 때문에
	// private 으로 생성
	private enum CellStatus
	{
		CLOSED,OPEN,FLAGGED
	}
	
	private static final int MAX_ADJACENTMINES = 8;
	
	// 셀의 현재 상태
	private CellStatus status;
	
	// 지뢰 여부
	private boolean mine;
	// 사용자가 처음 오픈한 장소가 지뢰일 경우 
	// 지뢰 위치를 옮기는 로직이 있어 final 속성 부여하지 않음
	
	// 인접 지뢰
	private int adjacentMines;
	
	// 생성자
	Cell(boolean mine)
	{
		this.mine = mine;
		status = CellStatus.CLOSED;
		adjacentMines = 0;
	}
	
	// getter
	boolean isMine(){return mine;}
	
	int getAdjacentMines(){return adjacentMines;}
	
	boolean isOpen(){return status == CellStatus.OPEN;}
	
	boolean isClosed(){return status == CellStatus.CLOSED;}
	
	boolean isFlagged(){return status == CellStatus.FLAGGED;}
	
	// setter
	void setMine(boolean mine)
	{
		this.mine = mine;
	}
	
	// 인접 지뢰 계산 로직상 
	// +1 밖에 하지 않기 때문에
	// set 이 아닌 add 로 구현
	void addAdjacentMines()
	{
		if(adjacentMines >= MAX_ADJACENTMINES)
		{
			throw new IllegalStateException("인접 지뢰의 최대 값을 넘겼습니다.");
		}
		
		adjacentMines++;
	}
	
	// 닫혀 있는 셀만 열 수 있음
	void openCell()
	{
		if(isClosed())
		{
			status = CellStatus.OPEN;
		}
	}
	
	// 닫혀 있는 셀은 깃발로
	// 깃발 박힌 셀은 닫힌 셀로
	void toggleFlag()
	{
		if(isClosed())
		{
			status = CellStatus.FLAGGED;
		}
		else if(isFlagged())
		{
			status = CellStatus.CLOSED;
		}
	}
	
	/*
	 * 한번 오픈한 셀은
	 * 다시 열리지 않음
	 */
	
	// 게임이 종료되어서 모든 셀을 열때 사용하는 메소드
	// 규칙을 무시하고 열리는 메소드 이기 때문에
	// 게임 중에는 실행하지 않아야 한다
	void forceOpen()
	{
		if(!isOpen())
		{
			status = CellStatus.OPEN;
		}
	}
}
