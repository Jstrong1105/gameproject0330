package game.com.domain.memorygame;

/**
 * 메모리 게임 옵션
 */
class MemoryGameOption
{
	// 카드 개수
	private static final int MIN_COUNT = 4;
	private static final int MAX_COUNT = 8;
	private int count;
	
	// 페어 개수
	private static final int MIN_PAIR = 2;
	private static final int MAX_PAIR = 4;
	private int pair;
	
	// 시간 가중치
	private static final int MIN_WEIGHT = 1;
	private static final int MAX_WEIGHT = 3;
	private int weight;
	
	// getter
	int getMinCount() {return MIN_COUNT;}
	int getMaxCount() {return MAX_COUNT;}
	int getCount() {return count;}
	
	int getMinPair() {return MIN_PAIR;}
	int getMaxPair() {return MAX_PAIR;}
	int getPair() {return pair;}
	
	int getMinWeight() {return MIN_WEIGHT;}
	int getMaxWeight() {return MAX_WEIGHT;}
	int getWeight() {return weight;}
	
	// setter
	void setCount(int count)
	{
		if(count < MIN_COUNT || count > MAX_COUNT)
		{
			throw new IllegalArgumentException("허용하지 않는 카운트입니다.");
		}
		
		this.count = count;
	}
	
	void setPair(int pair)
	{
		if(pair < MIN_PAIR || pair > MAX_PAIR)
		{
			throw new IllegalArgumentException("허용하지 않는 페어입니다.");
		}
		
		this.pair = pair;
	}
	
	void setWeight(int weight)
	{
		if(weight < MIN_WEIGHT || weight > MAX_WEIGHT)
		{
			throw new IllegalArgumentException("허용하지 않는 가중치입니다.");
		}
		
		this.weight = weight;
	}
}
