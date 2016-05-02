import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Babilonia {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Babilonia m = new Babilonia();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			int K = sc.nextInt();
			int W = sc.nextInt();

			Solution sol = m.new Solution(N, K, W);
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				sol.addPoint(p1, p2);
			}

			// File write
			wr.write(" ");
		}

		sc.close();
	}

	class Solution {
		class Point {
			int x;
			int y;

			public Point(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}

		ArrayList<Point> al = new ArrayList<Point>();
		int minX, maxX;

		// TreeMap <Integer, ArrayList<Integer>> tm =new TreeMap<Integer,
		// ArrayList<Integer>> ();
		int size;
		int width;
		int limit;

		public Solution(int n, int k, int w) {
			size = n;
			width = w;
			limit = k;
		}

		public void addPoint(int p1, int p2) {
			al.add(new Point(p1, p2));
		}

		public void checkPoint() {
			int minXI = minXIndex();
			int maxXI = maxXIndex();
			minX = al.get(minXI).x;
			maxX = al.get(maxXI).x;
			
			// TODO: Y를 구하자
			for (int i=0; i<size; i++){
			}
			
			
			int count=0;
			for (int i = 0; i < size; i++) {

				int a = al.get(i).x;
				int b = al.get(i).y;

				if (minX <= a && minX + width >= a) {
					
				} else if (maxX >= a && maxX - width <= a) {

				} else {
					count++;
					if (count > limit)
						break;
				}
			}
			
			if (count > limit)
				System.out.println("No");
			else
				System.out.println("Yes");
		}

		int minXIndex() {
			return (al.get(0).x < al.get(size - 1).x) ? 0: size-1;
		}

		int maxXIndex() {
			return (al.get(0).x > al.get(size - 1).x) ? 0: size-1;
		}
	}
}
