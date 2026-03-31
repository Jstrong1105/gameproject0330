package game.com.domain.base;

/**
 * 각 게임의 옵션을 수정하는 메뉴
 */
public class OptionSetter
{
	public static <T, E extends Enum<E> & OptionSetterTemplate<T>> void setOption(E[] optionList, T option)
	{
		for(E list : optionList)
		{
			System.out.println(list.getName() + " : " + list.getGuide());
			list.setOption(option);
		}
	}
}
