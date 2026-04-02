package game.com.domain.minesweeper;

import java.util.function.Consumer;

import game.com.domain.base.OptionSetterTemplate;
import game.com.util.InputUtil;

/**
 * 지뢰찾기 옵션 세터
 */
enum MinesweeperOptionSetter implements OptionSetterTemplate<MinesweeperOption>
{
	SIZE("사이즈","보드판의 가로,세로 길이를 설정합니다.",(option)->
	{
		int size = InputUtil.readInt("사이즈를 입력하세요", option.getMinSize(), option.getMaxSize());
		option.setSize(size);
	}),
	MINE_PERCENT("지뢰 비율","지뢰의 비율을 결정합니다",(option)->
	{
		int minePercent = InputUtil.readInt("지뢰 비율을 입력하세요",option.getMinMinePercent(),option.getMaxMinePercent());
		option.setMinePercent(minePercent);
	}),
	CHANCE("찬스","찬스의 횟수를 결정합니다.",(option)->
	{
		int chance = InputUtil.readInt("찬스 횟수를 입력하세요",option.getMinChance(),option.getMaxChance());
		option.setChance(chance);
	}),
	PRINTER("프린터","출력 방식을 결정합니다.",(option)->
	{
		int answer = InputUtil.readInt("1. 문자 출력 / 2. 숫자 출력",1,2);
		
		if(answer == 1)
		{
			option.setPrinter(new CellPrinter1());
		}
		else
		{
			option.setPrinter(new CellPrinter2());
		}
	})
	;
	
	private MinesweeperOptionSetter(String name, String guide, Consumer<MinesweeperOption> setter)
	{
		this.name = name;
		this.guide = guide;
		this.setter = setter;
	}
	
	private final String name;
	private final String guide;
	private final Consumer<MinesweeperOption> setter;
	
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
	public void setOption(MinesweeperOption option)
	{
		setter.accept(option);
	}
}
