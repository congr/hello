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
			int N = sc.nextInt();
			int M = sc.nextInt();

			Solution sol = m.new Solution(N, M);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					sol.mine[i][j] = sc.nextInt();
				}
			}

			sol.solve();
			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class Solution {
		int n, m;

		public Solution(int n, int m) {
			mine = new int[n][m];
			dt = new int[n][m];
			this.n = n;
			this.m = m;
		}

		int mine[][];// 실제 광석 입력 값.
		int dt[][];// 동적테이블.

		public int solve() {

			dt[0][0] = mine[0][0];

			// dt[i][j] 의 값은 i-1, j 와 i, j-1 중 큰값으로 저장.
			// route도 pred 배열을 유지하면 가능함.(미구현)
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {

					if (i > 0 && j > 0)
						dt[i][j] = mine[i][j] + Math.max(dt[i][j - 1], dt[i - 1][j]);
					else if (i == 0 && j > 0)
						dt[i][j] = mine[i][j] + dt[i][j - 1];
					else if (i > 0 && j == 0)
						dt[i][j] = mine[i][j] + dt[i - 1][j];
				}
			}

			int result = dt[n - 1][m - 1];

			System.out.println(result);
			return result;
		}

	}
}
