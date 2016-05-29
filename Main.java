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
			
			Solution sol = m.new Solution(N);
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				sol.addEdge(p1, p2);
			}
			
			// File write
			wr.write(" ");
		}
		
		sc.close();
		wr.close();
	}
	
	class Solution {
		int size;
		public Solution(int n) {
			this.size = n;
		}
		
		void addEdge(int x, int y) {
			
		}
	}
}
