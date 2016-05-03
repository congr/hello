import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	static int[][] map;

	public static void main(String[] args) throws FileNotFoundException {
		Main m = new Main();
		// input
		Scanner sc = new Scanner(new File("sample.in"));
		int size = sc.nextInt();
		map = new int[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = sc.nextInt();
			}
		}

		Solution s = m.new Solution(size);
		s.find();
		
		// output

		sc.close();
	}

	class Solution {
		int size;

		int[] dx = { -1, 0, 1, 0 };
		int[] dy = { 0, -1, 0, 1 };
		ArrayList<Integer> result;
		int mole = 1;

		public Solution(int size) {
			this.size = size;
			result = new ArrayList<Integer>();
		}

		int find() {

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {

					if (map[i][j] == 1) { // 굴 찾았다
						total = 0;
						mole++; // 두더지
						startDfs(i, j, mole);
						if (total > 1)
							result.add(total);
					}
				}
			}
			
			Collections.sort(result, new Comp());
			for (Integer i : result) {
				System.out.println(i);
			}
			
//			System.out.println("map");
//			for (int i = 0; i < size; i++) {
//				for (int j = 0; j < size; j++) {
//					System.out.print(map[i][j] + " ");
//				}
//				System.out.println();
//			}
			
			return 1;
		}

		class Comp implements Comparator<Integer> {

			@Override
			public int compare(Integer arg0, Integer arg1) {
				// TODO Auto-generated method stub
				System.out.println(arg0 + " " + arg1);
				if (arg0 == arg1) return 0;
				else
					return arg0 - arg1 > 0 ? -1 : 1 ;
			}
			
		}
		
		int total;

		int startDfs(int x, int y, int mole) {
			map[x][y] = mole;
			total++;

			for (int d = 0; d < 4; d++) {
				int a = dx[d];
				int b = dy[d];
				if (inside(x + a, y + b) && map[x + a][y + b] == 1) {
					startDfs(x + a, y + b, mole);
				}
			}
			return total;

		}

		boolean inside(int x, int y) {
			if (x >= 0 && y >= 0 && x < size && y < size)
				return true;
			else
				return false;
		}

	}
}
