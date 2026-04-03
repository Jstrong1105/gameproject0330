package game.com.domain.trumpcard;

/**
 * 트럼프 카드 한장 객체
 * 패키지 프라이빗
 */
public class Card
{
	/*
	 * 카드 모양 열거
	 */
	enum CardShape
	{
		SPADE("♠"),
		DIA("◆"),
		HEART("♥"),
		CLUB("♣");
		
		CardShape(String shape)
		{
			this.shape = shape;
		}
		
		private final String shape;
		
		String getShape()
		{
			return shape;
		}
	}
	
	// 상수
	static final int MIN_NUMBER = 2;
	static final int MAX_NUMBER = 14;
	private static final String ERR_MSG = "유효하지 않은 카드가 생성되었습니다.";
	
	// 속성
	private final int number;
	private final CardShape shape;
	private boolean open;
	
	// 생성자
	// 패키지 프라이빗으로 설정해서
	// 같은 패키지 내에 있는 카드덱 클래스를 통해서만 생성 가능하도록 만듬
	Card(int number, CardShape shape)
	{
		if(number < MIN_NUMBER || number > MAX_NUMBER)
		{
			throw new IllegalArgumentException(ERR_MSG);
		}
		
		this.number = number;
		this.shape = shape;
		open = false;
	}
	
	// getter
	public int getNumber() { return number; }
	public String getShape() { return shape.getShape(); }
	public boolean isOpen() { return open; } 

	// setter
	public void open() { open = true; }
	public void hidden() { open = false; }
	
	// 기타 메소드
	
	// 동일한 모양과 숫자의 카드가 중복으로 필요할 떄 사용하는 메소드
	public Card copy()
	{
		return new Card(this.number, this.shape);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null) {return false;}
		if(this == o) {return true;}
		if(this.getClass() != o.getClass()) {return false;}
		Card card = (Card)o;
		
		return (this.number == card.number) && (this.shape == card.shape);
	}
	
	@Override
	public int hashCode()
	{
		return number + shape.ordinal() * 31;
	}
}
