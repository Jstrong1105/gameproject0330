package game.com.domain.pokergamble;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Supplier;

import game.com.domain.trumpcard.Card;

/**
 * 족보 판독기
 */
class PokerRankingEvaluator
{
	// 받은 카드
	private List<Card> handCard;
	
	// 외부에서 호출하는 데이터
	PokerRankingResult getRanking(List<Card> handCard)
	{
		if(handCard.size() < 5)
		{
			throw new IllegalArgumentException("판독할 카드는 5장이 넘어야 합니다.");
		}
		
		this.handCard = handCard;
		
		PokerRankingResult result = null;
		
		prepareData();
		
		for(Supplier<PokerRankingResult> eval : evaluator)
		{
			result = eval.get();
			
			if(result != null)
			{
				return result;
			}
		}
		
		throw new IllegalStateException("족보 판독에서 오류 발생");
	}
	
	// 전처리할 데이터를 담아둘 녀석들
	
	// 모양 개수 그룹 → ♠(key(모양)) : 1(value(개수)) / ♥ : 1 / ◆ : 1 / ♣ : 2 
	// → 만약 클로버가 한장도 존재하지 않는 카드덱이라면 클로버 키 자체가 없음	 
	private HashMap<String, Integer> shapeCount;
	
	// 숫자 개수 그룹 → 2(key(숫자)) : 1(value(개수)) / 3 : 1 / 4 : 1 .... 14 : 2  
	// → 만약 특정 숫자가 한장도 존재하지 않는 카드덱이라면 해당 숫자의 키 자체가 없음
	private HashMap<Integer, Integer> numberCount;
	
	// 개수 개수 그룹 → 1(key(개수)) : 1(value(개수)) / 2 : 1 / 3 : 1 / 4 : 1 
	// 만약 특정 개수가 하나도 존재하지 않는다면 해당 키 자체가 없으며 포커 족보상 4 이상을 이루는 개수는 존재할 수 없음
	private HashMap<Integer, Integer> groupCount;
	
	// 카드의 모든 숫자를 모아서 내림차순 정렬한 것 
	private List<Integer> numberOrder;
	
	// 중복 제거 버전
	private List<Integer> numberDistinctOrder;
	
	// numberOrder 가 스트레이트를 만족한다면 스트레이트를 이루는 가장 큰 숫자
	// 스트레이트를 만족하지 않는다면 -1
	private int straightNumber;
	
	// 플러시의 유무를 판독
	private boolean flush;
	
	// 플러시일 경우 플러시인 모양의 숫자를 모아서 내림차순 정렬한 것
	private List<Integer> flushNumberOrder;
	
	// 중복 제거 버전
	private List<Integer> flushNumberDistinctOrder;
	
	// flushNumberOrder 가 스트레이트를 만족한다면 스트레이트를 이루는 가장 큰 숫자
	// 스트레이트를 만족하지 않는다면 -1
	private int flushStraightNumber;
	
	// 전처리
	private void prepareData()
	{
		// 초기화
		shapeCount = new HashMap<>();
		numberCount = new HashMap<>();
		groupCount = new HashMap<>();
		numberOrder = new ArrayList<>();
		numberDistinctOrder = new ArrayList<>();
		flushNumberOrder = new ArrayList<>();
		flushNumberDistinctOrder = new ArrayList<>();
		flush = false;
		straightNumber = -1;
		flushStraightNumber = -1;
		
		for(Card card : handCard)
		{
			shapeCount.put(card.getShape(), shapeCount.getOrDefault(card.getShape(), 0) + 1);
			numberCount.put(card.getNumber(), numberCount.getOrDefault(card.getNumber(), 0) + 1);
			numberOrder.add(card.getNumber());
		}
		
		numberDistinctOrder = numberOrder.stream().distinct().sorted(Comparator.reverseOrder()).toList();
		
		for(int group : numberCount.values())
		{
			groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);
		}
		
		Collections.sort(numberOrder,Collections.reverseOrder());
		
		straightNumber = straightNumber(numberDistinctOrder);
		
		// 플러시 체크
		for(String shape : shapeCount.keySet())
		{
			if(shapeCount.get(shape) >= 5)
			{
				flush = true;
				
				for(Card card : handCard)
				{
					if(card.getShape().equals(shape))
					{
						flushNumberOrder.add(card.getNumber());
					}
				}
				
				Collections.sort(flushNumberOrder,Collections.reverseOrder());
				
				flushNumberDistinctOrder = flushNumberOrder.stream().distinct().sorted(Collections.reverseOrder()).toList();
				
				flushStraightNumber = straightNumber(flushNumberDistinctOrder);
				
				break;
			}
		}
	}
	
	// 스트레이트 판독
	private int straightNumber(List<Integer> list)
	{
		int n = 0;
		
		for(int i = 0; i < list.size() -1; i++)
		{
			// 1씩 차이난다면
			if(list.get(i) - list.get(i+1) == 1)
			{
				n++;
			}
			else
			{
				n = 0;
			}
			if(n >= 4)
			{
				return list.get(i-3);
				// value : 14 13 12 11  9  8  7  6  5
				//  i    :  0  1  2  3  4  5  6  7  
				//  n    :  1  2  3  0  1  2  3  4
				// 이때 n 이 4가되는 시점이 스트레이트가 확정되는 시점이고
				// 이때 i - 3 의 해당하는 value 가 스트레이트를 이루는 가장 큰 숫자이다.
			}
		}
		
		// Back Straight
		if(list.contains(2) && list.contains(3) && list.contains(4) && list.contains(5) && list.contains(14))
		{
			return 5;
		}
		
		return -1;
	}
	
	private List<Supplier<PokerRankingResult>> evaluator = List.of
	(
		this::isStraightFlush,
		this::isFourOfAKind,
		this::isFullHouse,
		this::isFlush,
		this::isStraight,
		this::isThreeOfAKind,
		this::isTwoPair,
		this::isOnePair,
		this::isHighCard
	);
	
	// 스트레이트 플러시
	private PokerRankingResult isStraightFlush()
	{
		if(flush && flushStraightNumber != -1)
		{
			PokerRankingResult result;
				
			if(flushStraightNumber == 14)
			{
				result = PokerRankingResult.royalFlush();
			}
			else
			{
				result = PokerRankingResult.straightFlush();
			}
			result.addKicker(flushStraightNumber);
				
			return result;
		}
		
		return null;
	}
	
	// 포카드
	private PokerRankingResult isFourOfAKind()
	{
		if(groupCount.getOrDefault(4, 0) >= 1)
		{
			PokerRankingResult result = PokerRankingResult.fourOfAKind();
			
			for(int fourGroupNumber : numberDistinctOrder)
			{
				if(numberCount.get(fourGroupNumber) >= 4)
				{
					result.addKicker(fourGroupNumber);
					
					numberOrder.stream().filter(num -> num != fourGroupNumber).limit(1).forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	// 풀하우스
	private PokerRankingResult isFullHouse()
	{
		int three = groupCount.getOrDefault(3, 0);
		int two = groupCount.getOrDefault(2, 0);
		
		if(three >= 2 || (three >= 1 && two >= 1))
		{
			PokerRankingResult result = PokerRankingResult.fullHouse();
			
			for(int threeGroupNumber : numberDistinctOrder)
			{
				if(numberCount.get(threeGroupNumber) >= 3)
				{
					result.addKicker(threeGroupNumber);
					
					for(int twoGroupNumber : numberDistinctOrder)
					{
						if(twoGroupNumber != threeGroupNumber && numberCount.get(twoGroupNumber) >= 2)
						{
							result.addKicker(twoGroupNumber);
							
							return result;
						}
					}
				}
			}
 		}
		
		return null;
	}
	
	// 플러시
	private PokerRankingResult isFlush()
	{
		if(flush)
		{
			PokerRankingResult result = PokerRankingResult.flush();
			
			flushNumberOrder.stream().limit(5).forEach(result::addKicker);
			
			return result;
		}
		
		return null;
	}
	
	// 스트레이트
	private PokerRankingResult isStraight()
	{
		if(straightNumber != -1)
		{
			PokerRankingResult result;
			
			if(straightNumber == 14)
			{
				result = PokerRankingResult.mountain();
			}
			else if(straightNumber == 5)
			{
				result = PokerRankingResult.backStraight();
			}
			else
			{
				result = PokerRankingResult.straight();
			}
			
			result.addKicker(straightNumber);
			
			return result;
		}
		
		return null;
	}
	
	// 트리플
	private PokerRankingResult isThreeOfAKind()
	{
		if(groupCount.getOrDefault(3, 0) >= 1)
		{
			PokerRankingResult result = PokerRankingResult.threeOfAKind();
			
			for(int threeGroupNumber : numberDistinctOrder)
			{
				if(numberCount.get(threeGroupNumber) >= 3)
				{
					result.addKicker(threeGroupNumber);
					
					numberOrder.stream().filter(num -> num != threeGroupNumber).limit(2).forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
 		return null;
	}
	
	// 투페어
	private PokerRankingResult isTwoPair()
	{
		if(groupCount.getOrDefault(2, 0) >= 2)
		{
			PokerRankingResult result = PokerRankingResult.twoPair();
			
			for(int highPair : numberDistinctOrder)
			{
				if(numberCount.get(highPair) >= 2)
				{
					result.addKicker(highPair);
					
					for(int lowPair : numberDistinctOrder)
					{
						if(lowPair != highPair && numberCount.get(lowPair) >= 2)
						{
							result.addKicker(lowPair);
							
							numberOrder.stream().filter(num -> num != highPair && num != lowPair).limit(1).forEach(result::addKicker);
							
							return result;
						}
					}
				}
			}
		}
		
		return null;
	}
	
	// 원페어
	private PokerRankingResult isOnePair()
	{
		if(groupCount.getOrDefault(2, 0) >= 1)
		{
			PokerRankingResult result = PokerRankingResult.onePair();
			
			for(int pairNumber : numberDistinctOrder)
			{
				if(numberCount.get(pairNumber) >= 2)
				{
					result.addKicker(pairNumber);
					
					numberOrder.stream().filter(num -> num != pairNumber).limit(3).forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	// 하이카드
	private PokerRankingResult isHighCard()
	{
		PokerRankingResult result = PokerRankingResult.highCard();
		
		numberOrder.stream().limit(5).forEach(result::addKicker);
		
		return result;
	}
}
