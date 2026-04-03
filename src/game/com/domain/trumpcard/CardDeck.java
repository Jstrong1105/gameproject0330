package game.com.domain.trumpcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.com.domain.trumpcard.Card.CardShape;

/**
 * 카드덱 클래스
 */
public class CardDeck implements CardDeckTemplate
{
	private static final String ERR_MSG = "카드덱이 비어있습니다.";
	
	// 카드덱 객체
	private List<Card> cardDeck;
	
	// 생성자
	public CardDeck()
	{
		cardDeck = new ArrayList<>();
		
		init();
	}
	
	// 초기화
	public void init()
	{
		cardDeck.clear();
		
		for(int number = Card.MIN_NUMBER; number <= Card.MAX_NUMBER; number++)
		{
			for(CardShape shape : Card.CardShape.values())
			{
				cardDeck.add(new Card(number, shape));
			}
		}
		
		Collections.shuffle(cardDeck);
	}
	
	@Override
	public Card drawCard()
	{
		if(cardDeck.isEmpty())
		{
			throw new IllegalStateException(ERR_MSG);
		}
		
		// 내부 인덱스 재정렬 안하고 처리하기 위해 뒤에서 뽑아줌
		return cardDeck.remove(cardDeck.size()-1);
	}

	@Override
	public int countCard()
	{
		return cardDeck.size();
	}
}
