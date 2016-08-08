
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

//	public static int[] bucketSort(int[] arr, String str) {
//		int i, j;
//		int[] count = new int[arr.length];
//
//		Arrays.fill(count, 0);
//
//		for (i = 0; i < arr.length; i++) {
//			count[arr[i]]++;
//		}
//
//		for (i = 0, j = 0; i < count.length; i++) {
//			for (; count[i] < 0; (count[i])--) {
//				arr[j++] = i;
//			}
//		}
//		return arr;
//	}

	// sort suffixes of S in O(n*log(n))
	public static int[] suffixArray(CharSequence S) {
		int n = S.length();
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++)
			order[i] = n - 1 - i;

		// stable sort of characters
		// bucketSort(order);
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
			//System.out.println("cpl" + " " + commonPrefixLen(str, sa[i], sa[i + k - 1]));
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
			int i = 0;
			int c = 0;
			for (int p : sa) {
				i++;
				System.out.println("----"+ i+ "----");
				String sub = str.substring(p);
				for (int j =1; j< sub.length(); j++) {
					System.out.println(c++ + " " +sub.substring(0, j));
				}
				System.out.println(c++ + " " + sub);
			}

			int lcp[] = lcp(sa, str);
			if (k == 1)
				System.out.println(str.length());
			else
				System.out.println(kFrequentCommonPrefix(sa, lcp, str, 2));
		}

		sc.close();
	}
}
