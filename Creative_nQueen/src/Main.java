import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Main m = new Main();

		int size = sc.nextInt();
		placed = new int[size];
		m.placeQueen(0, size);
System.out.println( "total: "+ total);
		sc.close();
	}

	static int[] placed;
	static int total;

	// 퀀은 한줄에 한개.
	int placeQueen(int r, int size) {
		if (r == size) {

			for (int i =0; i<size; i++)
				System.out.print("("+i + ", " + placed[i] + ") ");
			
			System.out.println();
			total++;
			return 1;
		}

		for (int x = 0; x < size; x++) {
			if (canPlace(x, r, size)) {
				placed[r] = x;
				placeQueen(r + 1, size);
				
			}
		}

		return 0;
	}

	// x, y는 placed 의 점의 대각선이나 같은 열, 행인가?
	boolean canPlace(int x, int y, int size) {

		for (int i = 0; i < y; i++) {
			int d = y - i;

			if (i + d == y && placed[i] - d == x) // 같은 행의 대각선?
				return false;
			else if (i + d == y && placed[i] + d == x) // 같은 행의 대각선?
				return false;
			else if (placed[i] == x) // 같은 열?
				return false;
		}

		return true;
	}

}
