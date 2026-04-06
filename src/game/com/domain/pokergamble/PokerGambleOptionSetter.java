package game.com.domain.pokergamble;

import java.util.function.Consumer;

import game.com.domain.base.OptionSetterTemplate;
import game.com.util.InputUtil;

/**
 * 포커 겜블 옵션 세터
 */
enum PokerGambleOptionSetter implements OptionSetterTemplate<PokerGambleOption>
{
	COUNT("카드 수","드로우할 카드 수를 결정합니다.",(option)->
	{
		int count = InputUtil.readInt("카드 수를 입력하세요", option.getMinCount(), option.getMaxCount());
		option.setCount(count);
	}),
	TARGET("목표 코인","달성해야 하는 목표 코인을 결정합니다.",(option)->
	{
		int target = InputUtil.readInt("목표 코인을 입력하세요",option.getMinTarget(),option.getMaxTarget());
		option.setTarget(target);
	}),
	PERCENT("시작 코인","시작시 보유하는 코인의 퍼센트를 결정합니다.",(option)->
	{
		int percent = InputUtil.readInt("시작 퍼센트를 입력하세요.",option.getMinPercent(),option.getMaxPercent());
		option.setPercent(percent);
	}),
	WEIGHT("가중치","승리 시 획득하는 코인의 배율을 결정합니다",(option)->
	{
		int weight = InputUtil.readInt("가중치를 입력하세요",option.getMinWeight(),option.getMaxWeight());
		option.setWeight(weight);
	})
	;
	
	private PokerGambleOptionSetter(String name, String guide, Consumer<PokerGambleOption> setter)
	{
		this.name = name;
		this.guide = guide;
		this.setter = setter;
	}
	
	private final String name;
	private final String guide;
	private final Consumer<PokerGambleOption> setter;
	
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
	public void setOption(PokerGambleOption option)
	{
		setter.accept(option);
	}
}
