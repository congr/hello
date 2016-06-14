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
			int W = sc.nextInt();

			Solution sol = m.new Solution(N, W);
			Item ia[] = new Item[N + 1];
			for (int i = 0; i < N; i++) {
				String item = sc.next();
				int weight = sc.nextInt();
				int value = sc.nextInt();
				Item it = m.new Item(item, weight, value);
				ia[i + 1] = it;
			}

			sol.solve(ia);

			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class Item {
		String item;
		int weight;
		int value;
		boolean pred;

		public Item(String item, int weight, int value) {
			this.item = item;
			this.weight = weight;
			this.value = value;
			this.pred = false;
		}
	}

	class Solution {
		int dt[][];
		int size, weight;

		public Solution(int n, int w) {
			this.size = n;
			this.weight = w;
			dt = new int[n + 1][w + 1]; // i 까지 선택한 경우 최대 value
		}

		void solve(Item[] ia) {
			int selected = 0;

			for (int i = 0; i <= size; i++) {
				for (int j = 0; j <= weight; j++) { // j: 0 ~ Max weight
					if (i == 0 || j == 0)
						dt[i][j] = 0;
					else if (ia[i].weight > j) {
						dt[i][j] = dt[i - 1][j];
						// ia[i].pred = i-1;
					} else {
						// max(ith item을 선택안하는 경우 V, ith item을 선택하는 경우)
						// 선택하는 경우: Vi + dt[i-1][j - Wi]
						dt[i][j] = Math.max(dt[i - 1][j], ia[i].value + dt[i - 1][j - ia[i].weight]);
						if (j == weight) {
							// ith item을 선택한 경우,
							if (dt[i - 1][j] < ia[i].value + dt[i - 1][j - ia[i].weight]) {
								ia[i].pred = true;
								selected++;
							}
						}
					}

				}
			}

			System.out.println(dt[size][weight] + " " + selected);

			for (int i = 1; i <= size; i++) {
				if (ia[i].pred) {
					System.out.println(ia[i].item);
				}
			}
		}
	}
}
