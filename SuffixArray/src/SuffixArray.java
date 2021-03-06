import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class SuffixArray {
	private Suffix[] suffixes;

	/**
	 * Initializes a suffix array for the given <tt>text</tt> string.
	 * 
	 * @param text
	 *            the input string
	 */
	public SuffixArray(String text) {
		int N = text.length();
		this.suffixes = new Suffix[N];
		for (int i = 0; i < N; i++)
			suffixes[i] = new Suffix(text, i);
		Arrays.sort(suffixes);
	}

	private static class Suffix implements Comparable<Suffix> {
		private final String text;
		private final int index;

		private Suffix(String text, int index) {
			this.text = text;
			this.index = index;
		}

		private int length() {
			return text.length() - index;
		}

		private char charAt(int i) {
			return text.charAt(index + i);
		}

		public int compareTo(Suffix that) {
			if (this == that)
				return 0; // optimization
			int N = Math.min(this.length(), that.length());
			for (int i = 0; i < N; i++) {
				if (this.charAt(i) < that.charAt(i))
					return -1;
				if (this.charAt(i) > that.charAt(i))
					return +1;
			}
			return this.length() - that.length();
		}

		public String toString() {
			return text.substring(index);
		}
	}

	/**
	 * Returns the length of the input string.
	 * 
	 * @return the length of the input string
	 */
	public int length() {
		return suffixes.length;
	}

	/**
	 * Returns the index into the original string of the <em>i</em>th smallest
	 * suffix. That is, <tt>text.substring(sa.index(i))</tt> is the <em>i</em>th
	 * smallest suffix.
	 * 
	 * @param i
	 *            an integer between 0 and <em>N</em>-1
	 * @return the index into the original string of the <em>i</em>th smallest
	 *         suffix
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless 0 &le; <em>i</em> &lt; <Em>N</em>
	 */
	public int index(int i) {
		if (i < 0 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return suffixes[i].index;
	}

	/**
	 * Returns the length of the longest common prefix of the <em>i</em>th
	 * smallest suffix and the <em>i</em>-1st smallest suffix.
	 * 
	 * @param i
	 *            an integer between 1 and <em>N</em>-1
	 * @return the length of the longest common prefix of the <em>i</em>th
	 *         smallest suffix and the <em>i</em>-1st smallest suffix.
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless 1 &le; <em>i</em> &lt; <em>N</em>
	 */
	public int lcp(int i) {
		if (i < 1 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return lcp(suffixes[i], suffixes[i - 1]);
	}

	// longest common prefix of s and t
	private static int lcp(Suffix s, Suffix t) {
		int N = Math.min(s.length(), t.length());
		for (int i = 0; i < N; i++) {
			if (s.charAt(i) != t.charAt(i))
				return i;
		}
		return N;
	}

	/**
	 * Returns the <em>i</em>th smallest suffix as a string.
	 * 
	 * @param i
	 *            the index
	 * @return the <em>i</em> smallest suffix as a string
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless 0 &le; <em>i</em> &lt; <Em>N</em>
	 */
	public String select(int i) {
		if (i < 0 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return suffixes[i].toString();
	}

	/**
	 * Returns the number of suffixes strictly less than the <tt>query</tt>
	 * string. We note that <tt>rank(select(i))</tt> equals <tt>i</tt> for each
	 * <tt>i</tt> between 0 and <em>N</em>-1.
	 * 
	 * @param query
	 *            the query string
	 * @return the number of suffixes strictly less than <tt>query</tt>
	 */
	public int rank(String query) {
		int lo = 0, hi = suffixes.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = compare(query, suffixes[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	// compare query string to suffix
	private static int compare(String query, Suffix suffix) {
		int N = Math.min(query.length(), suffix.length());
		for (int i = 0; i < N; i++) {
			if (query.charAt(i) < suffix.charAt(i))
				return -1;
			if (query.charAt(i) > suffix.charAt(i))
				return +1;
		}
		return query.length() - suffix.length();
	}

	// lcp가 가장 큰 값. - 같은 길이로 여러개 있을 수 있음.
	public static String longestRepeatedSubString(SuffixArray suffixArray) {
		String str = "";
		int maxDiff = 0;
		for (int i = 1; i < suffixArray.length(); i++) {
			// System.out.print(suffixArray.select(i) + " ");
			// System.out.print(suffixArray.select(i-1) + " ");
			int diff = suffixArray.lcp(i);
			// System.out.println(i + " " + diff);
			if (diff >= maxDiff) {
				str = suffixArray.select(i).substring(0, diff);
				maxDiff = diff;
			}
		}

		return str;
	}

	// 부분 문자열 개수
	// eg) banana 의 경우 15개
	// prefix를 각 자리수 만큼 나
	//
//	 i ind lcp rnk select 	-> 부분문자 : 가능한 개수.
//	 ---------------------------
//	 0   5  -   0   "a" 	-> a : 6-5 = 1
//	 1   3  1   1   "ada" 	-> a da ada (a는 중복) : 6-3-1 = 2
//	 2   1  1   2   "anada" -> a an ana anad anada (a는 중복) : 6-1-1 = 4
//	 3   0  0   3   "banada"-> b ba ban bana banad banada : 6-0-0 = 6
//	 4   4  0   4   "da" 	-> d da : 6-4-0=2
//	 5   2  0   5   "nada" 	-> n na nad nada : 6-2-0=4
	public static int countSubStr(SuffixArray suffixArray, String s) {
		int sLen = s.length();
		int count = 0;
		for (int i = 0, len = suffixArray.length(); i < len; i++) {
			int cp = 0;
			if (i > 0)
				cp = suffixArray.lcp(i); // cp는 중
			count += sLen - suffixArray.index(i);// - cp; // 중복제거 안
			 System.out.println(i+ " "+ s.length() + " "+ suffixArray.index(i)
			 + " " + cp);
		}

		return count;
	}

	/**
	 * Unit tests the <tt>SuffixArray</tt> data type.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// Scanner sc = new Scanner(new File("inputL001.txt"));
		Scanner sc = new Scanner(System.in);
		FileWriter wr = new FileWriter(new File("out.txt"));
		String s = "";
		while (sc.hasNext()) {
			s = sc.next();

			SuffixArray suffix = new SuffixArray(s);

			System.out.println("  i ind lcp rnk select");
			System.out.println("---------------------------");

			for (int i = 0, len = s.length(); i < len; i++) {
				int index = suffix.index(i);
				String ith = "\"" + s.substring(index, Math.min(index + 1000, len)) + "\"";
				assert s.substring(index).equals(suffix.select(i));
				int rank = suffix.rank(s.substring(index));
				if (i == 0) {
					System.out.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
				} else {
					int lcp = suffix.lcp(i);
					System.out.printf("%3d %3d %3d %3d %s\n", i, index, lcp, rank, ith);
					wr.write(lcp + "\n");
				}
			}

			System.out.println("longested repeated substr: " + longestRepeatedSubString(suffix));
			System.out.println("rank(bin search): " + suffix.rank("ana"));
			System.out.println("substr permutation count: " + countSubStr(suffix, s));
		}
		sc.close();
		wr.close();
	}

}