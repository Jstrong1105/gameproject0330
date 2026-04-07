package game.com.domain.pokergamble;

import java.util.List;
import java.util.ArrayList;

/**
 * 족보 판독 결과 클래스
 */
class PokerRankingResult implements Comparable<PokerRankingResult>
{
	private final PokerRankingList result;
	
	private final List<Integer> kicker;
	
	// 생성자
	private PokerRankingResult(PokerRankingList result)
	{
		this.result = result;
		kicker = new ArrayList<>();
	}
	
	static PokerRankingResult royalFlush(){return new PokerRankingResult(PokerRankingList.ROYAL_FLUSH);}
	static PokerRankingResult straightFlush(){return new PokerRankingResult(PokerRankingList.STRAIGHT_FLUSH);}
	static PokerRankingResult fourOfAKind(){return new PokerRankingResult(PokerRankingList.FOUR_OF_A_KIND);}
	static PokerRankingResult fullHouse(){return new PokerRankingResult(PokerRankingList.FULL_HOUSE);}
	static PokerRankingResult flush(){return new PokerRankingResult(PokerRankingList.FLUSH);}
	static PokerRankingResult mountain(){return new PokerRankingResult(PokerRankingList.MOUNTAIN);}
	static PokerRankingResult straight(){return new PokerRankingResult(PokerRankingList.STRAIGHT);}
	static PokerRankingResult backStraight(){return new PokerRankingResult(PokerRankingList.BACK_STRAIGHT);}
	static PokerRankingResult threeOfAKind(){return new PokerRankingResult(PokerRankingList.THREE_OF_A_KIND);}
	static PokerRankingResult twoPair(){return new PokerRankingResult(PokerRankingList.TWO_PAIR);}
	static PokerRankingResult onePair(){return new PokerRankingResult(PokerRankingList.ONE_PAIR);}
	static PokerRankingResult highCard(){return new PokerRankingResult(PokerRankingList.HIGH_CARD);}
	
	// 키커 추가 
	// 추가하는 순서를 비교하는 순서대로 넣어야함
	void addKicker(int kicker)
	{
		if(this.kicker.size() >= result.getKickerCount())
		{
			throw new IllegalStateException("키커 개수 제한을 넘어섰습니다.");
		}
		
		this.kicker.add(kicker);
	}

	// 비교 메소드
	@Override
	public int compareTo(PokerRankingResult o)
	{
		// 족보 비교
		int rankDiff = this.result.getPower() - o.result.getPower();
		
		// 족보가 다르다면 
		if(rankDiff != 0)
		{
			return rankDiff;
		}
		
		// 유효성 검사
		if(this.kicker.size() != o.kicker.size())
		{
			throw new IllegalStateException("같은 족보의 키커의 길이가 다릅니다.");
		}
		
		// 키커 비교
		for(int i = 0; i < this.kicker.size(); i++)
		{
			rankDiff = this.kicker.get(i) - o.kicker.get(i);
			
			// 키커가 다르다면
			if(rankDiff != 0)
			{
				return rankDiff;
			}
		}
		
		// 키커까지 똑같다면
		return 0;
	}
	
	private String[] displayString = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	
	// 결과를 화면에 출력할때 사용할 문자열 생성 메소드
	String toDisplayString()
	{
		return switch(result)
		{
			// 족보 이름만 표시
			case ROYAL_FLUSH,MOUNTAIN,BACK_STRAIGHT -> result.getName();
			
			// 키커 한장과 족보 이름 표시
			case STRAIGHT_FLUSH,FOUR_OF_A_KIND,FLUSH,STRAIGHT,THREE_OF_A_KIND,ONE_PAIR,HIGH_CARD -> displayString[kicker.get(0)-2] + " " + result.getName();
			
			// 키커 두장과 족보 이름 표시
			case FULL_HOUSE,TWO_PAIR -> displayString[kicker.get(0)-2] + ", " + displayString[kicker.get(1)-2] + " " + result.getName();
		};
	}
}
