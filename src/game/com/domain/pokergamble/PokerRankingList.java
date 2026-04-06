package game.com.domain.pokergamble;

/**
 * 족보 열거
 */
enum PokerRankingList
{
	ROYAL_FLUSH("로얄플러시",12,1)
	, STRAIGHT_FLUSH("스트레이트플러시",11,1)
	, FOUR_OF_A_KIND("포카드",10,2)
	, FULL_HOUSE("풀하우스",9,2)
	, FLUSH("플러시",8,5)
	, MOUNTAIN("마운틴",7,1)
	, STRAIGHT("스트레이트",6,1)
	, BACK_STRAIGHT("백스트레이트",5,1)
	, THREE_OF_A_KIND("트리플",4,3)
	, TWO_PAIR("투페어",3,3)
	, ONE_PAIR("원페어",2,4)
	, HIGH_CARD("탑",1,5)
	;
	
	PokerRankingList(String name,int power,int kickerCount)
	{
		this.name = name;
		this.power = power;
		this.kickerCount = kickerCount;
	}
	
	private final String name;
	private final int power;
	private final int kickerCount;
	
	String getName()
	{
		return name;
	}
	
	int getPower()
	{
		return power;
	}
	
	int getKickerCount()
	{
		return kickerCount;
	}
}
