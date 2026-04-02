package game.com.domain.minesweeper;

import java.util.Random;

/**
 * 지뢰찾기 보드판 구체화 
 */
class CellBoard extends CellBoardTemplate
{
	@Override
	void init(int size, int mineCount)
	{
		// 초기화
		board = new Cell[size][size];
		this.size = size;
		this.mineCount = mineCount;
		totalCellCount = size * size;
		openCellCount = 0;
		
		// 지뢰 비율 50% 여부 확인
		boolean mineToMuch = (totalCellCount / 2) < mineCount;
		
		/*
		 * 지뢰 비율을 확인하는 이유는
		 * 지뢰 비율이 50%를 넘지 않으면 전체를 일반 셀로 만들고
		 * 지뢰를 배치하고
		 * 
		 * 지뢰 비율이 50%를 넘으면 전체를 지뢰로 매설하고 
		 * 일반 셀로 만들기 위함
		 */
		
		// 셀 초기화
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				if(!mineToMuch)
				{
					board[r][c] = new Cell(false);
				}
				else
				{
					board[r][c] = new Cell(true);
				}
 			}
		}
		
		Random rd = new Random();
		
		// 지뢰 매설 or 지뢰 해제
		if(!mineToMuch)
		{
			while(mineCount > 0)
			{
				int r = rd.nextInt(size);
				int c = rd.nextInt(size);
				
				if(!isMine(r, c))
				{
					board[r][c].setMine(true);
					mineCount--;
				}
			}
		}
		
		else
		{
			int num = totalCellCount - mineCount;
			
			while(num > 0)
			{
				int r = rd.nextInt(size);
				int c = rd.nextInt(size);
				
				if(isMine(r, c))
				{
					board[r][c].setMine(false);
					num--;
				}
			}
		}
	}
}
