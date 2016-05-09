import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();
		Solution sol;

		int tc = sc.nextInt();
		if (tc > 30) // large set
			sol = m.new Solution(20000); // 최대 정점수.
		else // small set
			sol = m.new Solution(500);

		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			int M = sc.nextInt();

			sol.setVCnt(N);
			for (int i = 0; i < M; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				sol.addEdge(p1, p2);
			}
			sol.getTriangleCount();

			sol.clear();

			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class Solution {
		static final int START = 1;
		int[][] adjMatrix;
		int vCnt;

		public Solution(int max) {
			adjMatrix = new int[max + 1][max + 1];
		}

		void setVCnt(int cnt) {
			this.vCnt = cnt;
		}

		void clear() {
			for (int i = 0; i < adjMatrix.length; i++)
				Arrays.fill(adjMatrix[i], 0);
		}

		void addEdge(int v1, int v2) {
			adjMatrix[v1][v2] = 1;
			adjMatrix[v2][v1] = 1;
		}

		void getTriangleCount() {
			int count = 0;
			int maxCount = 0;

			for (int i = START; i <= vCnt; i++) {
				for (int j = START; j <= vCnt; j++) {
					if (i < j && adjMatrix[i][j] == 0) {
						// System.out.println(i + " " + j);

						adjMatrix[i][j] = 2; // 임의로 간선을 연결해서 삼각형 카운트.
						count = 0; // 카운트 초기화.
						for (int k = START; k <= vCnt; k++) {
							// System.out.println(i + " " + j + " " + k);

							// (i->j) (j->k) (k->i) 로 삼각형이 되는 형상
							if (adjMatrix[i][j] > 0 && adjMatrix[j][k] > 0 && adjMatrix[k][i] > 0)
								count++;
						}

						if (count > 0) {
							System.out.println("count: " + count);
							maxCount = Math.max(count, maxCount);
						}
						
						adjMatrix[i][j] = 0;
					}
				}
			}

			System.out.println(maxCount);
		}

		void getOrginalTriangleCount() {
			int count = 0;
			int maxCount = 0;

			for (int i = START; i <= vCnt; i++) {
				for (int j = START; j <= vCnt; j++) {
					for (int k = START; k <= vCnt; k++) {
						// System.out.println(i + " " + j + " " + k);

						// (i->j) (j->k) (k->i) 로 삼각형이 되는 형상
						if (adjMatrix[i][j] > 0 && adjMatrix[j][k] > 0 && adjMatrix[k][i] > 0)
							count++;
					}

					if (count > 0) {
						// System.out.println("count: " + count);
						count /= 6;// 무방향 그래프경우 /6, 방향 그래프경우 /3 (삼각형으로 동일한것을 3번씩
									// 카운트했음)
						maxCount = Math.max(count, maxCount);
					}
					
					adjMatrix[i][j] = 0;
				}
			}

			System.out.println(maxCount);
		}
	}
}
