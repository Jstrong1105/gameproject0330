package game.com.domain.minesweeper;

import game.com.util.InputUtil;

/**
 * 보드판을 숫자 형태로 출력하는 클래스
 * 입력은 87 , 92 처럼 하나의 숫자로 받는다
 */
class CellPrinter2 implements MinesweeperPrinterTemplate
{
	@Override
	public void print(CellBoardTemplate board)
	{
		Cell[][] cells = board.getBoard();
		
		int size = cells.length;
		
		StringBuilder prompt = new StringBuilder();
		
		// 타이틀
		prompt.append(getTitle(size));
		
		// 상단
		prompt.append(" ".repeat(3));
		
		prompt.append(getTop(4,size));
		
		// 각각의 열
		for(int row = 0; row < size; row++)
		{
			// 셀 좌측
			prompt.append(" ".repeat(3));
			prompt.append("│");
			
			// 각각의 셀
			for(int col = 0; col < size; col++)
			{
				Cell cell = cells[row][col];
				
				String str;
				
				if(cell.isClosed())
				{
					str = String.valueOf(row * size + col + 1);
					
					str = String.format("%3s", str);
				}
				else
				{
					// 콘솔 창에서 전각문자의 크기가 일정하지 않은 이유로 이렇게 했음
					// 위에서는 숫자가 1이든 100 이든 3자리로 표시되야하지만
					// 여기서는 3s 를 넣으면 특수문자가 차지하는 공간이 한칸이 아니여서 3칸이 안됨
					str = getShape(cell);
					str = String.format(" %s ", str);
				}
				
				if(cell.isChoice())
				{
					str = "\033[1;92;103m" + str + "\033[0m";
				}
				
				prompt.append(str + " ");
			}
			
			// 셀 우측
			prompt.append("│\n");
		}
		
		// 하단
		prompt.append(" ".repeat(3));
		
		prompt.append(getBottom(4,size));
		
		System.out.print(prompt.toString());
	}

	// 셀의 상태에 따라 화면에 출력할 모양을 반환하는 메소드
	private String getShape(Cell cell)
	{
		if(cell.isFlagged())
		{
			return FLAG_SHAPE;
		}
		else
		{
			if(cell.isMine())
			{
				return MINE_SHAPE;
			}
			else
			{
				return OPEN_SHAPE[cell.getAdjacentMines()];
			}
		}
	}
	
	@Override
	public int getNumber(int size)
	{
		return InputUtil.readInt("숫자를 입력",1,size*size);
	}

	@Override
	public String failMsg(int size, int row, int col)
	{
		return String.format("%d번은 지뢰입니다.", (row*size)+col+1);
	}
}
