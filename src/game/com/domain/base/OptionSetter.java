package game.com.domain.base;


/**
 * 각 게임의 옵션을 수정하는 메뉴
 */
public final class OptionSetter
{
	private OptionSetter() {}
	
	public static <T, E extends Enum<E> & OptionSetterTemplate<T>> void setOption(E[] optionList, T option)
	{
		for(E item : optionList)
		{
			System.out.println(item.getName() + " : " + item.getGuide());
			item.setOption(option);
		}
	}
}
