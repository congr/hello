import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();
		
		int tc = sc.nextInt();
		while(tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			int K = sc.nextInt();
			int W = sc.nextInt();
			
			Solution sol = m.new Solution();
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
			}
			
			// File write
			wr.write(" ");
		}
		
		sc.close();
	}
	
	class Solution {
		public Solution() {
			
		}
	}
}
