package game.com.domain.minesweeper;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

/**
 * 지뢰찾기 보드판
 * Cell 객체를 컨트롤 하는 녀석임
 * 지뢰 매설 로직을 제외하고 구현해서
 * 나중에 테스트를 위해서 가짜 보드판을 구현할 수 있도록 만듬
 */
abstract class CellBoardTemplate
{
	private static final int[] D_ROW = {-1,-1,-1,0,0,1,1,1};
	private static final int[] D_COL = {-1,0,1,-1,1,-1,0,1};
	
	protected Cell[][] board; 
	
	protected int size;
	protected int totalCellCount;
	protected int mineCount;
	protected int openCellCount;
	
	// 지뢰 설치 로직
	// 인접 지뢰 수는 openFirst 에서 계산하기때문에
	// 인접 지뢰를 계산하면 안된다
	abstract void init(int size, int mineCount);
	
	Cell[][] getBoard()
	{
		return board;
	}
	
	// 유효한 범위인지 확인하는 메소드
	boolean isValid(int row, int col)
	{
		if(board == null)
		{
			throw new IllegalStateException("보드판이 초기화 되지 않았습니다");
		}
		
		return !(row < 0 || col < 0 || row >= size || col >= size);
	}
	
	// 유효한 범위가 아니라서 예외를 던지는 래퍼 메소드
	void checkArray(int row, int col)
	{
		if(!isValid(row,col))
		{
			throw new IllegalArgumentException("보드판의 범위를 벗어났습니다");
		}
	}
	
	// 선택하기
	void choiceCell(int row, int col)
	{
		checkArray(row, col);
		
		board[row][col].setChoice(true);
	}
	
	// 선택 취소하기
	void cancelCell(int row, int col)
	{
		checkArray(row, col);
		
		board[row][col].setChoice(false);
	}
	
	// 첫 입력을 받아서 해당 칸이 지뢰라면 
	// 지뢰를 다른 곳으로 옮기고 인접 지뢰 수를 계산하는 메소드
	// 자체적으로 openCell 을 수행하지 않기 때문에
	// openCell 은 별도로 호출해야 한다.
	void openFirst(int row, int col)
	{
		checkArray(row, col);
		
		// 지뢰 옮기기
		if(board[row][col].isMine())
		{
			Random rd = new Random();
			
			while(true)
			{
				int r = rd.nextInt(board.length);
				int c = rd.nextInt(board.length);
				
				if(!board[r][c].isMine())
				{
					board[row][col].setMine(false);
					board[r][c].setMine(true);
					break;
				}
			}
		}
		
		// 인접 지뢰 계산하기
		for(int r = 0 ; r < board.length; r++)
		{
			for(int c = 0; c < board.length; c++)
			{
				if(board[r][c].isMine())
				{
					addAdjacentMines(r, c);
				}
			}
		}
	}
	
	// 지뢰와 인접한 8칸의 인접 지뢰 수를 + 1 하는 메소드
	private void addAdjacentMines(int row, int col)
	{
		for(int i = 0; i < 8; i++)
		{
			int r = row + D_ROW[i];
			int c = col + D_COL[i];
			
			if(isValid(r, c))
			{
				board[r][c].addAdjacentMines();
			}
		}
	}
	
	// 닫힘 여부를 반환하는 메소드
	boolean isClosed(int row, int col)
	{
		checkArray(row, col);
		
		return board[row][col].isClosed();
	}
	
	// 지뢰 여부를 반환하는 메소드
	boolean isMine(int row, int col)
	{
		checkArray(row, col);
		
		return board[row][col].isMine();
	}
	
	// 깃발 여부를 반환하는 메소드
	boolean isFlag(int row, int col)
	{
		checkArray(row, col);
		
		return board[row][col].isFlagged();
	}
	
	// 오픈 여부를 반환하는 메소드
	boolean isOpen(int row, int col)
	{
		checkArray(row, col);
		
		return board[row][col].isOpen();
	}
	
	// 한칸 오픈하는 메소드
	void openCell(int row, int col)
	{
		checkArray(row, col);
		
		if(!isClosed(row, col))
		{
			return;
		}
		
		board[row][col].openCell();
		openCellCount++;
		
		if(board[row][col].getAdjacentMines() == 0)
		{
			openAdjacentCells(row,col);
		}
	}
	
	// 인접 지뢰가 0칸인 칸을 열어서 주변 8칸을 여는 메소드
	private void openAdjacentCells(int row, int col)
	{
		Queue<int[]> currentCell = new ArrayDeque<int[]>();
			
		currentCell.offer(new int[] {row,col});
		
		while(!currentCell.isEmpty())
		{
			int[] current = currentCell.poll();
			
			for(int i = 0; i < 8; i++)
			{
				int r = current[0] + D_ROW[i];
				int c = current[1] + D_COL[i];
				
				if(isValid(r, c) && isClosed(r, c))
				{
					board[r][c].openCell();
					openCellCount++;
					
					if(board[r][c].getAdjacentMines() == 0)
					{
						currentCell.offer(new int[] {r,c});
					}
				}
			}
		}
	}
	
	// 한칸 깃발 토글하는 메소드
	void toggleFlag(int row, int col)
	{
		checkArray(row, col);
		
		board[row][col].toggleFlag();
	}
	
	// 클리어 여부 반환하는 메소드
	boolean isClear()
	{
		return (totalCellCount - mineCount) <= openCellCount;
	}
	
	// 모든 칸을 오픈하는 메소드
	void forceOpen()
	{
		for(int row = 0; row < size; row++)
		{
			for(int col = 0; col < size; col++)
			{
				board[row][col].forceOpen();
			}
		}
	}
}
