package game.com.domain.minesweeper;

import game.com.util.InputUtil;

class CellPrinter1 implements MinesweeperPrinterTemplate
{
	/*
	 * public static final String COLOR_START = "\033[";
	 * 
	 * public static final String BOLD = "1";
	 * 
	 * public static final String AND = ";";
	 * 
	 * public static final String TEXT_BLACK = "90"; 
	 * public static final String TEXT_RED = "91"; 
	 * public static final String TEXT_GREEN = "92"; 
	 * public static final String TEXT_YELLOW = "93";
	 * 
	 * public static final String BG_YELLOW = "103"; public static final String
	 * BG_WHITE = "107";
	 * 
	 * public static final String COLOR_END = "m";
	 * 
	 * public static final String STYLE_END = "\033[0m";
	 */
	
	private static final String CLOSE_SHAPE = "■";
	private static final String FLAG_SHAPE = "P";
	private static final String MINE_SHAPE = "※";
	private static final String[] OPEN_SHAPE = {"□","\033[92m①\033[0m","\033[93m②\033[0m","\033[91m③\033[0m","\033[91m④\033[0m","\033[91m⑤\033[0m","\033[91m⑥\033[0m","\033[91m⑦\033[0m","\033[91m⑧\033[0m"};
	
	@Override
	public void print(CellBoardTemplate board)
	{
		Cell[][] cells = board.getBoard();
		
		int size = cells.length;
		
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("\033[1;92m");
		prompt.append("==".repeat(size));
		prompt.append("지뢰찾기");
		prompt.append("==".repeat(size));
		prompt.append("\033[0m\n");
		
		prompt.append("\033[1;92m");
		prompt.append("==".repeat(size*2+4));
		prompt.append("\033[0m\n");
		
		prompt.append(" ".repeat(5));
		prompt.append("\033[1;93m");
		for(int i = 1; i <= size; i++)
		{
			prompt.append(String.format(" %2d", i));
		}
		prompt.append("\033[0m\n");
		
		prompt.append(" ".repeat(5));
		prompt.append("\033[1m");
		prompt.append("┌");
		prompt.append("───".repeat(size));
		prompt.append("┐");
		prompt.append("\033[0m\n");
		
		for(int row = 0; row < size; row++)
		{
			prompt.append("\033[96m");
			prompt.append(String.format("  %2d ", (row+1)));
			prompt.append("\033[0m");
			prompt.append("│");
			
			for(int col = 0; col < size; col++)
			{
				String str;
				
				Cell cell = cells[row][col];
				
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
				
				prompt.append(str);
			}
			
			prompt.append("│\n");
		}
		
		prompt.append(" ".repeat(5));
		prompt.append("\033[1m");
		prompt.append("└");
		prompt.append("───".repeat(size));
		prompt.append("┘");
		prompt.append("\033[0m\n");
		
		System.out.println(prompt.toString());
	}

	@Override
	public int getNumber(int size)
	{
		int row = InputUtil.readInt("\033[96m" + "열 번호" + "\033[0m",1,size);
		int col = InputUtil.readInt("\033[93m" + "행 번호" + "\033[0m",1,size);
		
		return ((row-1) * size) + col;
	}

	@Override
	public String failMsg(int size, int row, int col)
	{
		return String.format("%d열 %d행은 지뢰입니다.", row,col);
	}
}