import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt(); // 선의 수.

			Solution sol = m.new Solution();
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				if (i == 0) {// 왕자 출발 좌표
					sol.setPrincePoint(p1, p2);
				}
			}

			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				sol.setPrincessPoint(p1, p2);
			}

			sol.calc();
			// File write
			wr.write(" ");
		}

		sc.close();
	}

	class Solution {
		class point {
			int x, y;

			public point(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

		int a, b;
		ArrayList<Point> list = new ArrayList<Point>;

		public Solution() {

		}

		public void setPrincePoint(int a, int b) {
			this.a = a;
			this.b = b;
		}

		int day;

		// a, b 이전 좌표, c,d 새로운 좌표, 두 좌표사이의 점을 알아내서 list에 추가
		public void setPrincessPoint(int c, int d) {
			if (day > 0) {
				Point prev = list.get(day);
				int a = prev.x;
				int b = prev.y;
				while (!(a == c && b == d)) {
					if (a != c) {
						if (a > c)
							a--;
						else
							a++;
					}
					if (b != d) {
						if (b > d)
							b--;
						else
							b++;
					}

					list.add(a, b);
				}
			}

			list.add(c, d);
			day = list.size();
		}
		
		public void calc() {
			for (int i =0; i<day; i++ ) {
				if (i == dist (a,b,))
			}
		}
		
		public int dist(int a, int b,int c,int d){
			return Math.max(Math.abs(a-c), Math.abs(b-d));
		}
	}
}
