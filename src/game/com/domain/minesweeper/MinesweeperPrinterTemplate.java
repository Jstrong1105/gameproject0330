package game.com.domain.minesweeper;


/**
 * 지뢰찾기 프린터 인터페이스
 * 콘솔창 이라는 한계로 인해서
 * 출력하는 인터페이스에서 
 * 입력도 받고 있다....
 * 이건 분리하려면 할 수 있지만
 * 분리하는게 더 코드가 더러워 질거 같다
 */
interface MinesweeperPrinterTemplate
{
	// 상수
	static final String CLOSE_SHAPE = "■";
	static final String FLAG_SHAPE = "\033[1;95mP\033[0m";
	static final String MINE_SHAPE = "※";
	static final String[] OPEN_SHAPE = {"□","\033[92m①\033[0m","\033[93m②\033[0m","\033[91m③\033[0m","\033[91m④\033[0m","\033[91m⑤\033[0m","\033[91m⑥\033[0m","\033[91m⑦\033[0m","\033[91m⑧\033[0m"};
	
	// 보드판을 화면에 그리는 메소드
	void print(CellBoardTemplate board);		
	
	// 사용자에게 입력을 받는 메소드
	// (격자식 구조라면 r : 4 , c : 2 이런식으로 받아야 하고 / 번호 구조라면 42 이런식으로 받아야함)
	int getNumber(int size);
	
	// 실패시 출력하는 메소드
	// 격자식 구조라면 4행 3열은 지뢰다 라고 해야하고
	// 번호식 구조라면 43번은 지뢰다 라고 해야함
	String failMsg(int size,int row, int col);	
	
	// 타이틀 출력
	default String getTitle(int size)
	{
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("\033[1;98m");
		prompt.append("==".repeat(size));
		prompt.append("지뢰찾기");
		prompt.append("==".repeat(size));
		prompt.append("\033[0m\n");
		
		prompt.append("\033[1;98m");
		prompt.append("==".repeat(size*2+4));
		prompt.append("\033[0m\n");
		
		return prompt.toString();
	}
	
	// 상단 출력
	default String getTop(int count,int size)
	{
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("\033[1m");
		prompt.append("┌");
		prompt.append("─".repeat(count*size));
		prompt.append("┐");
		prompt.append("\033[0m\n");
		
		return prompt.toString();
	}
	
	// 하단 출력
	default String getBottom(int count,int size)
	{
		StringBuilder prompt = new StringBuilder();
		
		prompt.append("\033[1m");
		prompt.append("└");
		prompt.append("─".repeat(count*size));
		prompt.append("┘");
		prompt.append("\033[0m\n");
		
		return prompt.toString();
	}
}
