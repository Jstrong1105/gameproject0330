package game.com.domain.memorygame;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import game.com.domain.trumpcard.Card;
import game.com.domain.trumpcard.CardDeck;
import game.com.domain.trumpcard.CardDeckTemplate;
import game.com.domain.trumpcard.CardPrinter1;
import game.com.domain.trumpcard.CardPrinterTemplate;

/**
 * 메모리 게임 보드판
 */
class MemoryGameBoard implements MemoryGameBoardTemplate
{
	private final List<Card> tableCard; 	// 카드 뭉치	
	
	private final int count;	// 카드의 장수
	private final int pair;		// 카드의 페어 수
	
	private final CardPrinterTemplate printer;	// 출력 프린터	
	
	// 생성자
	MemoryGameBoard(int count, int pair)
	{
		this.count = count;
		this.pair = pair;
		printer = new CardPrinter1();
		
		tableCard = new ArrayList<>();
		
		CardDeckTemplate cardDeck = new CardDeck();
		
		// 카드 뭉치 생성 및 셔플
		for(int i = 0; i < count; i++)
		{
			Card card = cardDeck.drawCard();
			
			for(int j = 0; j < pair; j++)
			{
				tableCard.add(card.copy());
			}
		}
		
		Collections.shuffle(tableCard);
	}
	
	// 범위 체크 메소드
	private void checkIndex(int index)
	{
		if(index < 0 || index >= tableCard.size())
		{
			throw new IllegalArgumentException("잘못된 인덱스입니다.");
		}
	}
	
	// 열림 여부 반환
	@Override
	public boolean isOpen(int index)
	{
		checkIndex(index);
		
		return tableCard.get(index).isOpen();
	}

	// 한장 열기
	@Override
	public void openCard(int index)
	{
		checkIndex(index);
		
		tableCard.get(index).open();
	}

	// 한장 숨기기
	@Override
	public void hiddenCard(int index)
	{
		checkIndex(index);
		
		tableCard.get(index).hidden();
	}

	// 클리어 여부 반환하기
	@Override
	public boolean isClear()
	{
		return tableCard.stream().allMatch(card -> card.isOpen());
	}

	// 출력하기
	// count 가 4고 pair 가 2라면
	// 4장씩 2번에 나눠서 출력하기
	@Override
	public void print()
	{
		for(int i = 0; i < pair; i++)
		{
			printer.print(tableCard.subList(0 + (i*count),((i+1) * count)));
			System.out.println();
		}
	}

	// 같은 카드 인지 확인해주기
	// 0번과 1번 비교 -> 1번과 2번 비교 ..... 마지막전꺼와 마지막꺼 비교
	@Override
	public boolean isSameCard(List<Integer> list)
	{
		for(int i = 0; i < list.size()-1; i++)
		{
			Card card1 = tableCard.get(list.get(i));
			Card card2 = tableCard.get(list.get(i+1));
			
			if(!card1.equals(card2))
			{
				return false;
			}
		}
		
		return true;
	}
}
