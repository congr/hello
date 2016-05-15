import java.io.File;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import Main.Solution.Event;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("problem.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
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
		wr.close();
	}

	class Solution {
		class Segment {
			int x1, x2, y1, y2;

			public Segment(int x1, int y1, int x2, int y2) {
				this.x1 = x1;
				this.x2 = x2;
				this.y1 = y1;
				this.y2 = y2;
			}

			boolean isIntersect(Segment otherSegment) {
				return false;
			}
		}

		class Event implements Comparable{
			int x;
			Segment segment;

			public Event(int x, Segment segment) {
				this.x = x;
				this.segment = segment;
			}

			@Override
			public int compareTo(Object arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
		}

		
		public Solution() {
			PriorityQueue<Event> pq = new PriorityQueue<Event>();

			

		}

	}
}
