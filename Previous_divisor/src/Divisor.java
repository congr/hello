import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Divisor {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem_1_large.in"));
		// Scanner sc = new Scanner(System.in);
		FileWriter wr = new FileWriter(new File("problem.out"));
		Divisor m = new Divisor();

		int tc = sc.nextInt();
		// while(tc-- > 0) {
		// input tc #
		// int N = sc.nextInt();

		Solution sol = m.new Solution();
		for (int i = 0; i < tc; i++) {
			int num1 = sc.nextInt();
			int num2 = sc.nextInt();

			sol.setRange(num1, num2);
			int count = sol.cal();
			System.out.println(count);
			wr.write(count + "\n");
		}

		sc.close();
		wr.close();
	}

	class Solution {
		int from;
		int to;

		public Solution() {
		}

		public void setRange(int from, int to) {
			this.from = from;
			this.to = to;
		}

		// 16 약수 - {1 2 4 8 16} 4까지만 검사하면 / 로 알수있음
		// 10 약수 - {1 2 5 10} 2까지 검사하면 / 로 알수있음
		// for loop 를 i*i <= n까지 돌림
		// 루트16 = 4 까지 루프를 돌려서 n/i + i

		public int cal() {
			int count = 0;
			for (int n = from; n <= to; n++) {

				int sum = 1;
				for (int i = 2; i * i <= n; i++) {
					if (n % i == 0) {
						sum += n / i;
						if (i * i != n)
							sum += i;
					}
				}

				// System.out.println(n + " " + sum);

				if (sum >= n)
					count++;
			}

			return count;
		}
	}
}
