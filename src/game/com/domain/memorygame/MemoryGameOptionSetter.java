package game.com.domain.memorygame;

import java.util.function.Consumer;

import game.com.domain.base.OptionSetterTemplate;
import game.com.util.InputUtil;

/**
 * 메모리 게임 옵션 세터
 */
public enum MemoryGameOptionSetter implements OptionSetterTemplate<MemoryGameOption>
{
	COUNT("카드 수","카드 수를 결정합니다",(option)->
	{
		int count = InputUtil.readInt("카드 개수를 입력하세요", option.getMinCount(), option.getMaxCount());
		option.setCount(count);
	}),
	PAIR("카드 페어 수","카드 페어 수를 결정합니다.",(option)->
	{
		int pair = InputUtil.readInt("페어 수를 입력하세요",option.getMinPair(),option.getMaxPair());
		option.setPair(pair);
	}),
	WEIGHT("시간 가중치","카드를 보여주는 시간을 결정합니다.",(option)->
	{
		int weight = InputUtil.readInt("가중치를 입력하세요",option.getMinWeight(),option.getMaxWeight());
		option.setWeight(weight);
	})
	;
	
	private MemoryGameOptionSetter(String name, String guide, Consumer<MemoryGameOption> setter)
	{
		this.name = name;
		this.guide = guide;
		this.setter = setter;
	}
	
	private final String name;
	private final String guide;
	private final Consumer<MemoryGameOption> setter;
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getGuide()
	{
		return guide;
	}

	@Override
	public void setOption(MemoryGameOption option)
	{
		setter.accept(option);
	}
}
