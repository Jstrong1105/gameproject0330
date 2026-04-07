package game.com.domain.pokergamble;

import game.com.domain.base.GameTemplate;
import game.com.domain.base.OptionSetter;
import game.com.domain.trumpcard.Card;
import game.com.domain.trumpcard.CardDeck;
import game.com.domain.trumpcard.CardDeckTemplate;
import game.com.util.ConsoleUtil;
import game.com.util.InputUtil;

/**
 * 포커겜블 구체화 클래스
 */
public class PokerGambleApp extends GameTemplate
{
	private static final String GAME_NAME = "포커겜블";
	private static final String GAME_GUIDE = "포커를 승리해 목표 코인을 달성하세요";
	
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

	// 옵션 객체
	private PokerGambleOption option = new PokerGambleOption();
	
	private int count;
	private int target;
	private int percent;
	private int weight;
	
	private HandCard playerCard;
	private HandCard cpuCard;
	
	private PokerRankingResult playerResult;
	private PokerRankingResult cpuResult;
	
	private CardDeckTemplate cardDeck;
	
	private int playerCoin;
	private int totalBetCoin;
	private int betCoin;
	
	private static final int MIN_BET_COIN = 10;
	
	@Override
	protected void setOption()
	{
		ConsoleUtil.clear();
		
		InputUtil.pause("포커 겜블 옵션 세팅입니다.");
		
		OptionSetter.setOption(PokerGambleOptionSetter.values(), option);
		
		count = option.getCount();
		target = option.getTarget();
		percent = option.getPercent();
		weight = option.getWeight();
	}

	@Override
	protected void initialize()
	{
		playerCoin = target * percent / 100;
		
		roundInit();
	}

	private void roundInit()
	{
		playerCard = new HandCard();
		cpuCard = new HandCard();
		cardDeck = new CardDeck();
		
		playerResult = null;
		cpuResult = null;
		
		totalBetCoin = 0;
		
		drawCard();
		drawCard();
		
		// 컴퓨터 카드 2개는 공개해줌
		cpuCard.openCard(0);
		cpuCard.openCard(1);
		
		// 초기 베팅
		// 최소 베팅 금액보다 적으면 올인
		if(playerCoin >= MIN_BET_COIN)
		{
			totalBetCoin += MIN_BET_COIN;
			playerCoin -= MIN_BET_COIN;
		}
		else
		{
			totalBetCoin += playerCoin;
			playerCoin = 0;
		}
	}
	
	@Override
	protected void render()
	{
		ConsoleUtil.clear();
		cpuCard.printCard();
		System.out.println("컴퓨터의 카드");
		playerCard.printCard();
		System.out.println("당신의 카드");
		System.out.println("목표 코인 : " + target);
		System.out.println("베팅 코인 : " + totalBetCoin);
		System.out.println("남은 코인 : " + playerCoin);
	}

	@Override
	protected void playerInput()
	{
		betCoin = InputUtil.readInt("베팅할 금액을 입력",0,playerCoin);
		totalBetCoin += betCoin;
		playerCoin -= betCoin;
	}

	@Override
	protected void update()
	{
		if(betCoin == 0 && playerCoin != 0)
		{
			roundFinish(ResultType.FOLD);
		}
		else if(playerCard.countCard() < count)
		{
			drawCard();
		}
		else
		{
			playerResult = playerCard.getResult();
			cpuResult = cpuCard.getResult();
			
			int result = playerResult.compareTo(cpuResult);
			
			if(result > 0)
			{
				roundFinish(ResultType.WIN);
			}
			else if(result < 0)
			{
				roundFinish(ResultType.LOSE);
			}
			else
			{
				roundFinish(ResultType.DRAW);
			}
		}
	}
	
	private void roundFinish(ResultType type)
	{
		if(type == ResultType.FOLD)
		{
			InputUtil.pause("기권 했습니다");
		}
		else
		{
			for(int i = 0; i < count; i++)
			{
				cpuCard.openCard(i);
			}
			
			ConsoleUtil.clear();
			
			cpuCard.printCard();
			System.out.println(cpuResult.toDisplayString());
			
			playerCard.printCard();
			System.out.println(playerResult.toDisplayString());
			
			if(type == ResultType.WIN)
			{
				InputUtil.pause("승리했습니다.");
				playerCoin += totalBetCoin * 2 * weight;
			}
			else if(type == ResultType.DRAW)
			{
				InputUtil.pause("무승부입니다.");
				playerCoin += totalBetCoin;
			}
			else
			{
				InputUtil.pause("패배했습니다.");
			}
		}
		
		if(playerCoin >= target)
		{
			finishGame(true);
		}
		else if(playerCoin <= 0)
		{
			finishGame(false);
		}
		else
		{
			roundInit();
		}
	}
	
	private enum ResultType
	{
		WIN,LOSE,FOLD,DRAW
	}
	
	private void finishGame(boolean win)
	{
		endGame();
		
		String str = win ? "목표를 달성했습니다." : "모든 코인을 소모했습니다.";
		
		System.out.println(str);
	}
	
	private void drawCard()
	{
		Card card = cardDeck.drawCard();
		card.open();
		playerCard.addCard(card);
		cpuCard.addCard(cardDeck.drawCard());
	}
}
