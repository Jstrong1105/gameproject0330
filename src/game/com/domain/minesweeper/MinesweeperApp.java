package game.com.domain.minesweeper;

import java.util.HashMap;

import game.com.domain.base.GameApp;
import game.com.domain.base.OptionSetter;
import game.com.exception.PlayerException;
import game.com.util.ConsoleUtil;
import game.com.util.InputUtil;

/**
 * 지뢰찾기 구체화
 */
public class MinesweeperApp implements GameApp
{
	private static final String GAME_NAME = "지뢰찾기";
	private static final String GAME_GUIDE = "지뢰를 피하세요!";
	
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
	
	// 실행 흐름
	@Override
	public void run() throws PlayerException
	{
		setOption();
		
		do
		{
			initialize();
			
			while(running)
			{
				render();		// 화면 출력
				cellChoice();	// 셀 선택
				render();		// 선택된 셀 강조된 화면 출력
				actionChoice();	// 선택된 셀에 대해 액션 선택
			}
			
		} while (restart());
	}
	
	// 실행 흐름
	private boolean running;
	
	// 옵션 객체
	private MinesweeperOption option;
	
	// 보드판 객체
	private CellBoardTemplate cellBoard;
	
	// 사이즈
	private int size;
	
	// 전체 셀의 개수 (size * size)
	private int totalCell;
	
	// 지뢰의 개수
	private int mineCount;
	
	// 찬스 횟수
	private int chance;
	
	// 출력 타입
	private MinesweeperPrinterTemplate printer;
	
	// 첫 입력 여부
	private boolean first;
	
	// 플레이어가 선택한 열
	private int pRow;
	
	// 플레이어가 선택한 행
	private int pCol;
	
	// 플레이어가 선택 가능한 액션 처리
	private HashMap<Integer, Runnable> actionList = new HashMap<>();
	
	// 다시 시작
	private boolean restart() throws PlayerException
	{
		return InputUtil.readBoolean("다시시작하시겠습니까?", "Y", "N");
	}
	
	// 옵션 처리
	private void setOption() throws PlayerException
	{
		ConsoleUtil.clear();
		InputUtil.pause("지뢰찾기 게임입니다.");
		
		option = new MinesweeperOption();
		
		OptionSetter.setOption(MinesweeperOptionSetter.values(), option);
		
		size = option.getSize();
		totalCell = size * size;
		mineCount = totalCell * option.getMinePercent() / 100;
		chance = option.getChance();
		printer = option.getPrinter();
		
		int index = 1;
		
		actionList.put(index++, this::openCell);
		actionList.put(index++, this::toggleFlag);
		actionList.put(index++, this::useChance);
		actionList.put(index++, this::cancelCell);
	}
	
	// 초기화
	private void initialize() 
	{
		running = true;
		first = true;
		pRow = -1;
		pCol = -1;
		
		cellBoard = new CellBoard();
		cellBoard.init(size, mineCount);
	}
	
	// 화면 출력
	private void render()
	{
		ConsoleUtil.clear();
		printer.print(cellBoard);
		System.out.println("지뢰의 수 : " + mineCount);
		System.out.println("남은 찬스 : " + chance);
	}
	
	// 셀 선택
	private void cellChoice() throws PlayerException
	{
		do
		{
			int number = printer.getNumber(size) - 1;
			pRow = (number / size);
			pCol = (number % size);
			
			if(cellBoard.isOpen(pRow, pCol))
			{
				InputUtil.pause("이미 오픈된 셀입니다.");
			}
		} 
		while (cellBoard.isOpen(pRow, pCol));
		
		cellBoard.choiceCell(pRow, pCol);
	}
	
	// 액션 선택
	private void actionChoice() throws PlayerException
	{
		System.out.println("1. 오픈");
		System.out.println("2. 깃발");
		System.out.println("3. 찬스");
		System.out.println("4. 취소");
		
		int answer = InputUtil.readInt("번호 선택",1,actionList.size());
		
		actionList.get(answer).run();
	}
	
	// 한칸 열기
	private void openCell()
	{
		if(first)
		{
			cellBoard.openFirst(pRow, pCol);
			first = false;
		}
		
		if(cellBoard.isMine(pRow, pCol))
		{
			cancelCell();
			
			finish(false);
			
			return;
		}
		
		cellBoard.openCell(pRow, pCol);
		cancelCell();
		
		if(cellBoard.isClear())
		{
			finish(true);
		}
	}
	
	// 깃발 토글
	private void toggleFlag() throws PlayerException
	{
		if(first)
		{
			InputUtil.pause("첫 오픈 전에는 깃발 설치 불가");
			cancelCell();
			return;
		}
		
		cellBoard.toggleFlag(pRow, pCol);
		cancelCell();
	}
	
	// 찬스 사용
	private void useChance() throws PlayerException
	{
		if(first)
		{
			InputUtil.pause("첫 오픈 전에는 찬스 사용 불가");
			cancelCell();
			return;
		}
		
		if(chance <= 0)
		{
			InputUtil.pause("찬스를 모두 소모했습니다.");
			cancelCell();
			return;
		}
		
		chance--;
		
		if(cellBoard.isMine(pRow, pCol))
		{
			InputUtil.pause("해당 칸은 지뢰입니다.");
			
			if(!cellBoard.isFlag(pRow, pCol))
			{
				toggleFlag();
			}
		}
		else
		{
			InputUtil.pause("해당 칸은 지뢰가 아닙니다.");
			
			if(cellBoard.isFlag(pRow, pCol))
			{
				cellBoard.toggleFlag(pRow, pCol);
			}
			
			openCell();
		}
	}
	
	// 선택 취소
	private void cancelCell()
	{
		cellBoard.cancleCell(pRow, pCol);
	}
	
	// 종료 처리
	private void finish(boolean clear)
	{
		running = false;
		
		cellBoard.forceOpen();
		render();
		
		if(clear)
		{
			System.out.println("축하합니다. 모든 지뢰를 피했습니다.");
		}
		else
		{
			String str = printer.failMsg(size,pRow,pCol);
			System.out.println(str);
		}
	}
}
