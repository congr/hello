
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static int[] bucketSort(int[] arr, String str) {
        int i, j;
        int[] count = new int[arr.length];
        
        Arrays.fill(count, 0);

        for (i = 0; i < arr.length; i++) {
            count[arr[i]]++;
        }

        for (i = 0, j = 0; i < count.length; i++) {
            for (; count[i] < 0; (count[i])--) {
                arr[j++] = i;
            }
        }
        return arr;
    }
	// sort suffixes of S in O(n*log(n))
	public static int[] suffixArray(CharSequence S) {
		int n = S.length();
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = n - 1 - i;

		// stable sort of characters
		//bucketSort(order);
		Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));

		int[] sa = new int[n];
		int[] classes = new int[n];
		for (int i = 0; i < n; i++) {
			sa[i] = order[i];
			classes[i] = S.charAt(i);
		}
		// sa[i] - suffix on i'th position after sorting by first len characters
		// classes[i] - equivalence class of the i'th suffix after sorting by
		// first len characters

		for (int len = 1; len < n; len *= 2) {
			int[] c = classes.clone();
			for (int i = 0; i < n; i++) {
				// condition sa[i - 1] + len < n simulates 0-symbol at the end
				// of the string
				// a separate class is created for each suffix followed by
				// simulated 0-symbol
				classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n
						&& c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
			}
			// Suffixes are already sorted by first len characters
			// Now sort suffixes by first len * 2 characters
			int[] cnt = new int[n];
			for (int i = 0; i < n; i++)
				cnt[i] = i;
			int[] s = sa.clone();
			for (int i = 0; i < n; i++) {
				// s[i] - order of suffixes sorted by first len characters
				// (s[i] - len) - order of suffixes sorted only by second len
				// characters
				int s1 = s[i] - len;
				// sort only suffixes of length > len, others are already sorted
				if (s1 >= 0)
					sa[cnt[classes[s1]]++] = s1;
			}
		}
		return sa;
	}

	// sort rotations of S in O(n*log(n))
	public static int[] rotationArray(CharSequence S) {
		int n = S.length();
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = i;
		Arrays.sort(order, (a, b) -> Character.compare(S.charAt(a), S.charAt(b)));
		int[] sa = new int[n];
		int[] classes = new int[n];
		for (int i = 0; i < n; i++) {
			sa[i] = order[i];
			classes[i] = S.charAt(i);
		}
		for (int len = 1; len < n; len *= 2) {
			int[] c = classes.clone();
			for (int i = 0; i < n; i++)
				classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]]
						&& c[(sa[i - 1] + len / 2) % n] == c[(sa[i] + len / 2) % n] ? classes[sa[i - 1]] : i;
			int[] cnt = new int[n];
			for (int i = 0; i < n; i++)
				cnt[i] = i;
			int[] s = sa.clone();
			for (int i = 0; i < n; i++) {
				int s1 = (s[i] - len + n) % n;
				sa[cnt[classes[s1]]++] = s1;
			}
		}
		return sa;
	}

	// longest common prefixes array in O(n)
	public static int[] lcp(int[] sa, CharSequence s) {
		int n = sa.length;
		int[] rank = new int[n];
		for (int i = 0; i < n; i++)
			rank[sa[i]] = i;
		int[] lcp = new int[n - 1];
		for (int i = 0, h = 0; i < n; i++) {
			if (rank[i] < n - 1) {
				for (int j = sa[rank[i] + 1], l = s.length(); Math.max(i, j) + h < l
						&& s.charAt(i + h) == s.charAt(j + h); ++h)
					;
				lcp[rank[i]] = h;
				if (h > 0)
					--h;
			}
		}
		return lcp;
	}

	public static String longestCommonPrefixStr(int[] sa, int[] lcp, String str) {
		String substr = null;

		int maxLen = 0;
		int index = 0;

		// lcp 가 가장 긴 lcp i를 찾아라.
		for (int i = 0, l = lcp.length; i < l; i++) {
			if (maxLen < lcp[i]) {
				maxLen = Math.max(maxLen, lcp[i]);
				index = i;
			}
		}

		substr = str.substring(sa[index], sa[index] + maxLen);
		System.out.println("length of longest common prefix: " + maxLen + "\nlcp index: " + index + " "
				+ str.substring(sa[index], sa[index] + maxLen));

		return substr;
	}

	public static int commonPrefixLen(CharSequence s, int i, int j) {
		int k = 0;
		int sLen = s.length();
		while (i < sLen && j < sLen && s.charAt(i) == s.charAt(j)) {
			i++;
			j++;
			k++;
		}
		return k;
	}

	public static int kFrequentCommonPrefix(int[] sa, int[] lcp, String str, int k) {
		int maxLen = 0;
		for (int i = 0, l = str.length(); i + k <= l; i++) {
			maxLen = Math.max(maxLen, commonPrefixLen(str, sa[i], sa[i + k - 1]));
			// System.out.println("cpl" +" "+ commonPrefixLen(str, sa[i],
			// sa[i+k-1]));
		}

		return maxLen;
	}

	// Usage example
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("habbit.txt"));
		int tc = sc.nextInt();

		while (tc-- > 0) {
			int k = sc.nextInt();
			String str = sc.next();

			int[] sa = suffixArray(str);
			// int i = 0;
			// for (int p : sa) {
			// System.out.println(i + " " + str.substring(p));
			// i++;
			// }

			int lcp[] = lcp(sa, str);
			if (k == 1)
				System.out.println(str.length());
			else
				System.out.println(kFrequentCommonPrefix(sa, lcp, str, k));
		}

		sc.close();

		// String s1 = "banana";
		// int[] sa1 = suffixArray(s1);
		//
		// // print suffixes in lexicographic order
		// for (int p : sa1)
		// System.out.println(s1.substring(p));
		//
		// System.out.println("lcp = " + Arrays.toString(lcp(sa1, s1)));

		// random test
		// Random rnd = new Random(1);
		// for (int step = 0; step < 100000; step++) {
		// int n = rnd.nextInt(100) + 1;
		// StringBuilder s = new StringBuilder();
		// for (int i = 0; i < n; i++)
		// s.append((char) ('\1' + rnd.nextInt(10)));
		// int[] sa = suffixArray(s);
		// int[] ra = rotationArray(s.toString() + '\0');
		// int[] lcp = lcp(sa, s);
		// for (int i = 0; i + 1 < n; i++) {
		// String a = s.substring(sa[i]);
		// String b = s.substring(sa[i + 1]);
		// if (a.compareTo(b) >= 0 || !a.substring(0,
		// lcp[i]).equals(b.substring(0, lcp[i]))
		// || (a + " ").charAt(lcp[i]) == (b + " ").charAt(lcp[i]) || sa[i] !=
		// ra[i + 1])
		// throw new RuntimeException();
		// }
		// }
		// System.out.println("Test passed");
	}
}
