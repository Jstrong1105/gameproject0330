package game.com.domain.base;

/**
 * 각 게임의 옵션을 수정하는 enum이 가져야하는 인터페이스
 */
public interface OptionTemplate<T>
{
	String getName();
	String getGuide();
	void setOption(T option);
}
