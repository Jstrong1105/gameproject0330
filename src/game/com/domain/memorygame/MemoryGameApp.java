package game.com.domain.memorygame;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import game.com.domain.base.GameApp;
import game.com.domain.base.OptionSetter;
import game.com.util.ConsoleUtil;
import game.com.util.InputUtil;
import game.com.util.ThreadUtil;

/**
 * 메모리 게임 구체화 클래스
 */
public class MemoryGameApp implements GameApp
{
	// 상수
	private static final String GAME_NAME = "메모리게임";
	private static final String GAME_GUIDE = "같은 카드를 맞추세요!";
	private static final String OPTION_COMMENT = "메모리 게임 옵션 세팅입니다.";
	private static final String START_COMMENT = "메모리 게임입니다. 엔터를 누르면 카드가 보여집니다.";
	private static final String CHOICE = "번호를 입력";
	private static final String CHOICE_ERR = "이미 오픈된 카드입니다.";
	private static final String SUCCESS = "같은 카드입니다.";
	private static final String FAIL = "다른 카드입니다.";
	private static final String CLEAR_MSG = "축하합니다. 모든 카드를 맞췄습니다.";
	
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
	
	@Override
	public void run()
	{
		setOption();
		
		do
		{
			initialize();
			
			while(running)
			{
				// 페어 수 만큼 choice 반복하기 
				for(int i = 0; i < pair; i++)
				{
					render();
					choice();
				}
				
				render();
				update();
			}
		} while (restart());
	}
	
	// 실행 흐름
	private boolean running;
	
	// 옵션 객체
	private MemoryGameOption option;
	
	// 카드 수 / 페어 수 / 시간 가중치 
	private int count;
	private int pair;
	private int weight;
	
	// 보드판 객체
	private MemoryGameBoardTemplate board;
	
	// 사용자가 입력한 숫자를 담아둘 리스트
	private List<Integer> playerChoice;
	
	// 보여지는 시간 
	private static final int SHOW_TIME = 3;
	
	// 시작 시간
	private Instant startTime;
	
	// 다시 시작 
	private boolean restart()
	{
		return InputUtil.readBoolean("다시 시작하시겠습니까?", "Y", "N");
	}
	
	// 옵션 설정
	private void setOption()
	{
		ConsoleUtil.clear();
		
		InputUtil.pause(OPTION_COMMENT);
		
		option = new MemoryGameOption();
		
		// 설정하기
		OptionSetter.setOption(MemoryGameOptionSetter.values(), option);
		
		// 설정한거 가져오기
		count = option.getCount();
		pair = option.getPair();
		weight = option.getWeight();
	}

	// 초기화
	private void initialize()
	{
		ConsoleUtil.clear();
		InputUtil.pause(START_COMMENT);
		
		running = true;
		
		board = new MemoryGameBoard(count, pair);
		playerChoice = new ArrayList<>();
		
		// 보여주기
		for(int i = 0; i < count*pair; i++)
		{
			board.openCard(i);
		}
		
		render();
		
		// 기다리기
		ThreadUtil.sleepCountDown(SHOW_TIME * 3 * weight);
		
		// 숨기기
		for(int i = 0; i < count*pair; i++)
		{
			board.hiddenCard(i);
		}
		
		// 시간 측정
		startTime = Instant.now();
	}
	
	private void render()
	{
		ConsoleUtil.clear();
		board.print();
	}
	
	// 선택하기
	private void choice()
	{
		int answer;
		
		do
		{
			// 범위 내에서 입력받기
			answer = InputUtil.readInt(CHOICE,1,count*pair)-1;
			
			if(board.isOpen(answer))
			{
				InputUtil.pause(CHOICE_ERR);
			}
			
			// 이미 열린 카드 선택 시 다시 입력 받기
		} while (board.isOpen(answer));
		
		// 선택한 카드 추가하고 오픈하기
		playerChoice.add(answer);
		board.openCard(answer);
	}
	
	// 고른 카드 확인하기
	private void update()
	{
		// 같은 카드라면 0.5초 만 기다리기
		if(board.isSameCard(playerChoice))
		{
			System.out.println(SUCCESS);
			ThreadUtil.sleep(500L);
		}
		else
		{
			// 다른 카드라면 설정한 시간만큼 기다리기
			System.out.println(FAIL);
			ThreadUtil.sleepCountDown(SHOW_TIME * weight);
			
			for(int i : playerChoice)
			{
				board.hiddenCard(i);
			}
		}
		
		// 사용자 선택 초기화
		playerChoice.clear();
		
		// 클리어 체크
		if(board.isClear())
		{
			finish();
		}
	}
	
	private void finish()
	{
		running = false;
		System.out.println(CLEAR_MSG);
		System.out.println("클리어 시간 : " + Duration.between(startTime, Instant.now()).getSeconds() + "초");
	}
}
