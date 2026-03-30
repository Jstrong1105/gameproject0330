package game.com.domain.minesweeper;

/**
 * 지뢰찾기 옵션 객체
 */
class MinesweeperOption
{
	/*
	 * 사이즈
	 */
	private static final int MIN_SIZE = 10;
	private static final int MAX_SIZE = 20;
	private int size;
	
	int getMinSize() {return MIN_SIZE;}
	int getMaxSize() {return MAX_SIZE;}
	int getSize() {return size;}
	
	void setSize(int size)
	{
		if(size < MIN_SIZE || size > MAX_SIZE)
		{
			throw new IllegalArgumentException("허용하지 않는 사이즈입니다.");
		}
		
		this.size = size;
	}
	
	/*
	 * 지뢰 비율
	 */
	private static final int MIN_MINE_PERCENT = 10;
	private static final int MAX_MINE_PERCENT = 25;
	private int minePercent;
	
	int getMinMinePercent() {return MIN_MINE_PERCENT;}
	int getMaxMinePercent() {return MAX_MINE_PERCENT;}
	int getMinePercent() {return minePercent;}
	
	void setMinePercent(int minePercent)
	{
		if(minePercent < MIN_MINE_PERCENT || minePercent > MAX_MINE_PERCENT)
		{
			throw new IllegalArgumentException("허용하지 않는 지뢰 비율입니다.");
		}
		
		this.minePercent = minePercent;
	}
	
	/*
	 * 찬스 횟수
	 */
	private static final int MIN_CHANCE = 0;
	private static final int MAX_CHANCE = 10;
	private int chance;
	
	int getMinChance() {return MIN_CHANCE;}
	int getMaxChance() {return MAX_CHANCE;}
	int getChance() {return chance;}
	
	void setChance(int chance)
	{
		if(chance < MIN_CHANCE || chance > MAX_CHANCE)
		{
			throw new IllegalArgumentException("허용하지 않는 찬스 수 입니다.");
		}
		
		this.chance = chance;
	}
	
	/*
	 * 프린터 타입
	 */
	private MinesweeperPrinterTemplate printer;
	
	MinesweeperPrinterTemplate getPrinter() {return printer;}
	
	void setPrinter(MinesweeperPrinterTemplate printer)
	{
		this.printer = printer;
	}
}
