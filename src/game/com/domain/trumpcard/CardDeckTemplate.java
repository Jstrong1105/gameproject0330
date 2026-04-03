package game.com.domain.trumpcard;

/**
 * 카드 덱 객체가 가져야하는 인터페이스
 */
public interface CardDeckTemplate
{
	Card drawCard(); 	// 카드 나눠주기
	int countCard();	// 남은 카드 장수 알려주기
}
