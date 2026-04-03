package game.com.domain.trumpcard;

import java.util.List;

/**
 * 카드 프린터 객체
 */
public class CardPrinter1 implements CardPrinterTemplate
{
	private String[] numberShape = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	private String hidden = "?";
	
	@Override
	public void print(List<Card> cards)
	{
		// 예외 체크
		if(cards.isEmpty())
		{
			throw new IllegalStateException("카드가 비어있습니다.");
		}
		
		// 상단 출력
		for(int i = 0; i < cards.size(); i++)
		{
			System.out.print("\033[1;97m┌───┐\033[0m ");
		}
		
		System.out.println();
		
		// 모양 출력
		for(Card card : cards)
		{
			if(!card.isOpen())
			{
				System.out.printf("\033[1;97m│ %s │\033[0m ",hidden);
			}
			else
			{
				boolean red = card.getShape().equals("◆") || card.getShape().equals("♥");
				
				if(red)
				{
					System.out.printf("\033[1;97m│ \033[1;91m%s \033[1;97m│\033[0m ",card.getShape());
				}
				else
				{
					System.out.printf("\033[1;97m│ \033[1;90m%s \033[1;97m│\033[0m ",card.getShape());
				}
			}
		}
		
		System.out.println();
		
		//숫자 출력
		for(Card card : cards)
		{
			if(!card.isOpen())
			{
				System.out.printf("\033[1;97m│ %s │\033[0m ",hidden);
			}
			else
			{
				boolean red = card.getShape().equals("◆") || card.getShape().equals("♥");
				
				if(red)
				{
					System.out.printf("\033[1;97m│ \033[1;91m%s \033[1;97m│\033[0m ",numberShape[card.getNumber()-2]);
				}
				else
				{
					System.out.printf("\033[1;97m│ \033[1;90m%s \033[1;97m│\033[0m ",numberShape[card.getNumber()-2]);
				}
			}
		}
		
		System.out.println();
		
		// 하단 출력
		for(int i = 0; i < cards.size(); i++) 	
		{
			System.out.print("\033[1;97m└───┘\033[0m ");
		}
	}
	
	/*
	 * public static void main(String[] args) { List<Card> cards = new
	 * ArrayList<>();
	 * 
	 * CardDeck deck = new CardDeck();
	 * 
	 * // cards.add(deck.drawCard()); // cards.add(deck.drawCard()); //
	 * cards.add(deck.drawCard()); // cards.add(deck.drawCard()); //
	 * cards.add(deck.drawCard());
	 * 
	 * CardPrinterTemplate printer = new CardPrinter1();
	 * 
	 * printer.print(cards); }
	 */
}
