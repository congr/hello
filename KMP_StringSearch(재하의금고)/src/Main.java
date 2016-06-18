import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem.in"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			sc.nextLine();

			Solution sol = m.new Solution();
			int cnt = 0;
			for (int i = 0; i < N + 1; i++) {
				String p1 = sc.nextLine();
				cnt+=sol.solve(p1);
			}

			System.out.println(cnt);
			// File write
		}

		sc.close();
	}

	class Solution {
		String prev;
		boolean direction;
		int solve(String str) {
			int ind=0, ind2 =0;
			if (prev != null) {
				if (direction) 
				ind = kmpMatcher(prev + prev, str);
				else 
				ind = kmpMatcher(str+str, prev);
				
				direction = !direction;
			}

			prev = str;

			//System.out.println(ind);
			return ind;
		}

		public int[] prefixFunction(String s) {
			int[] p = new int[s.length()];
			int k = 0;
			for (int i = 1; i < s.length(); i++) {
				while (k > 0 && s.charAt(k) != s.charAt(i))
					k = p[k - 1];
				if (s.charAt(k) == s.charAt(i))
					++k;
				p[i] = k;
			}
			return p;
		}

		public int kmpMatcher(String s, String pattern) {
			int m = pattern.length();
			if (m == 0)
				return 0;
			int[] p = prefixFunction(pattern);
			for (int i = 0, k = 0; i < s.length(); i++)
				for (;; k = p[k - 1]) {
					if (pattern.charAt(k) == s.charAt(i)) {
						if (++k == m)
							return i + 1 - m;
						break;
					}
					if (k == 0)
						break;
				}
			return -1;
		}
	}

}
