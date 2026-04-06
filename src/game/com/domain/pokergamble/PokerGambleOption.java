package game.com.domain.pokergamble;

/**
 * 포커 겜블 옵션 클래스
 */
class PokerGambleOption
{
	// 카드 수
	private static final int MIN_COUNT = 5;
	private static final int MAX_COUNT = 7;
	private int count;
	
	int getMinCount() { return MIN_COUNT;}
	int getMaxCount() { return MAX_COUNT;}
	int getCount() { return count; }
	
	void setCount(int count)
	{
		if(count < MIN_COUNT || count > MAX_COUNT)
		{
			throw new IllegalArgumentException("허용하지 않는 카운트입니다.");
		}
		
		this.count = count;
	}
	
	// 목표 코인
	private static final int MIN_TARGET = 1000;
	private static final int MAX_TARGET = 10000;
	private int target;
	
	int getMinTarget() { return MIN_TARGET;}
	int getMaxTarget() { return MAX_TARGET;}
	int getTarget() { return target; }
	
	void setTarget(int target)
	{
		if(target < MIN_TARGET || target > MAX_TARGET)
		{
			throw new IllegalArgumentException("허용하지 않는 타겟입니다.");
		}
		
		this.target = target;
	}
	
	// 시작 코인
	private static final int MIN_PERCENT = 10;
	private static final int MAX_PERCENT = 30;
	private int percent;
	
	int getMinPercent() { return MIN_PERCENT; }
	int getMaxPercent() { return MAX_PERCENT; }
	int getPercent() { return percent; }
	
	void setPercent(int percent)
	{
		if(percent < MIN_PERCENT || percent > MAX_PERCENT)
		{
			throw new IllegalArgumentException("허용하지 않는 퍼센트입니다.");
		}
		
		this.percent = percent;
	}
	
	private static final int MIN_WEIGHT = 1;
	private static final int MAX_WEIGHT = 3;
	private int weight;
	
	// 가중치
	int getMinWeight() { return MIN_WEIGHT;}
	int getMaxWeight() { return MAX_WEIGHT;} 
	int getWeight() { return weight;}
	
	void setWeight(int weight)
	{
		if(weight < MIN_WEIGHT || weight > MAX_WEIGHT)
		{
			throw new IllegalArgumentException("허용하지 않는 가중치입니다.");
		}
		
		this.weight = weight;
	}
}
