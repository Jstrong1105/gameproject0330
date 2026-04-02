package game.com.domain.minesweeper;

import game.com.exception.PlayerException;
import game.com.util.InputUtil;

/**
 * 보드판을 격자 형태로 출력하는 클래스
 * 입력은 row , col 형태로 받는다. 
 */ 
class CellPrinter1 implements MinesweeperPrinterTemplate
{
	@Override
	public void print(CellBoardTemplate board)
	{
		Cell[][] cells = board.getBoard();
		
		int size = cells.length;
		
		StringBuilder prompt = new StringBuilder();
		
		// 타이틀
		prompt.append(getTitle(size));
		
		// 상단 번호(column)
		prompt.append(" ".repeat(5));
		prompt.append("\033[1;93m");
		
		for(int i = 1; i <= size; i++)
		{
			prompt.append(String.format(" %2d", i));
		}
		prompt.append("\033[0m\n");
		
		// 상단 
		prompt.append(" ".repeat(5));
		
		prompt.append(getTop(3,size));
		
		// 각각의 열
		for(int row = 0; row < size; row++)
		{
			// 셀 좌측의 번호(row)
			prompt.append("\033[96m");
			prompt.append(String.format("  %2d ", (row+1)));
			prompt.append("\033[0m");
			prompt.append("│");
			
			for(int col = 0; col < size; col++)
			{
				Cell cell = cells[row][col];
				
				// 각각의 셀
				String str = getShape(cell);
				
				prompt.append(str);
			}
			
			prompt.append("│\n");
		}
		
		// 하단
		prompt.append(" ".repeat(5));
		prompt.append(getBottom(3,size));
		
		System.out.print(prompt.toString());
	}

	// 셀의 상태에 따라 화면에 출력할 모양을 결정하는 메소드
	private String getShape(Cell cell)
	{
		String str;
		
		if(cell.isClosed())
		{
			str = CLOSE_SHAPE;
		}
		else if(cell.isFlagged())
		{
			str = FLAG_SHAPE;
		}
		else
		{
			if(cell.isMine())
			{
				str = MINE_SHAPE;
			}
			else
			{
				str = OPEN_SHAPE[cell.getAdjacentMines()];
			}
		}
		
		str = " " + str + " ";
		
		if(cell.isChoice())
		{
			str = "\033[1;92;103m" + str + "\033[0m";
		}
		
		return str;
	}
	
	@Override
	public int getNumber(int size) throws PlayerException
	{
		int row = InputUtil.readInt("\033[96m" + "열 번호" + "\033[0m",1,size);
		int col = InputUtil.readInt("\033[93m" + "행 번호" + "\033[0m",1,size);
		
		return ((row-1) * size) + col;
	}

	@Override
	public String failMsg(int size, int row, int col)
	{
		return String.format("%d열 %d행은 지뢰입니다.", row+1,col+1);
	}
}