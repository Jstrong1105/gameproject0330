package game.com.domain.pokergamble;

import java.util.List;
import java.util.ArrayList;

import game.com.domain.trumpcard.Card;
import game.com.domain.trumpcard.CardPrinter1;
import game.com.domain.trumpcard.CardPrinterTemplate;

/**
 * 각각의 플레이어의 카드를 담당하는 클래스
 */
class HandCard
{
	private final List<Card> handCard;	// 카드 목록
	
	private final CardPrinterTemplate printer;	// 카드 프린터
	
	// 생성자
	HandCard()
	{
		handCard = new ArrayList<>();
		printer = new CardPrinter1();
	}
	
	// 카드 추가하기
	void addCard(Card card)
	{
		handCard.add(card);
	}
	
	// 카드 개수 반환
	int countCard()
	{
		return handCard.size();
	}
	
	// 카드 오픈하기
	void openCard(int index)
	{
		if(index < 0 || index >= handCard.size())
		{
			throw new IllegalArgumentException("존재하지 않는 인덱스입니다.");
		}
		
		handCard.get(index).open();
	}
	
	// 카드 출력하기
	void printCard()
	{
		printer.print(handCard);
	}
	
	// 결과 반환하기
	PokerRankingResult getResult()
	{
		return null;
	}
}
