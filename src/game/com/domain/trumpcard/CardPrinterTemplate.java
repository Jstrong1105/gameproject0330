package game.com.domain.trumpcard;

import java.util.List;

/**
 * 카드 프린터가 구현해야하는 인터페이스
 */
public interface CardPrinterTemplate
{
	void print(List<Card> cards);
}
