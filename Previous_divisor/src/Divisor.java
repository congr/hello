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
			sol.cal();
		}

		// File write
		wr.write(" ");
		// }

		sc.close();
	}

	class Solution {
		int from;
		int to;
		int memo[];

		public Solution() {
			//memo = new int[500000];
		}

		public void setRange(int from, int to) {
			this.from = from;
			this.to = to;
		}
		
		// 16 약수 - {1 2 4 8 16} 4까지만 검사하면 / 로 알수있음
		// 10 약수 - {1 2 5 10} 2까지 검사하면 / 로 알수있음
		// for loop 를 i*i <= n까지 돌림
		// 루트16 = 4 까지 루프를 돌려서 n/i + i

		public void cal() {
			int count = 0;
			for (int n = from; n <= to; n++) {
				
				int sum = 1;
				for(int i=2; i*i<=n ; i++) { 
					if (n%i == 0) {
						sum += n/i;
						if (i*i!=n)
							sum+=i;
					}
				}
				
				//System.out.println(n + " " + sum);

				if (sum >= n)
					count++;
			}
			
			System.out.println(count);
		}
		
//		public void cal() {
//			Arrays.fill(memo, 0);
//			int count = 0;
//			for (int n = from; n <= to; n++) {
//				memo[0] = memo[1] = 0;
//
//				if (n > 1) {
//					int sum = 1;
//
//					if (memo[n / 2] == 0) {
//						for (int i = 2; i <= n / 2; i++) {
//							if (n % i == 0) {
//								memo[n/2] = sum = sum + n / i;
//							}
//						}
//					} else {
//						if (n > 6 && n % 6 == 0) {// 6 배수라면
//							sum = memo[n / 2] + n / 2 + n / 3;
//						} else if (n > 2 && n % 2 == 0) // 2의 배수라면,
//							sum = memo[n / 2] + n / 2;
//						else if (n > 3 && n % 3 == 0) // 3의 배수라면,
//							sum = memo[n / 3] + n / 3;
//					}
//
//					System.out.println(n + " " + sum);
//
//					if (sum >= n)
//						count++;
//				}
//			}
//			// System.out.println(count);
//		}
	}
}
