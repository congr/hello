import java.io.File;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();

			SweepLine sweepLine = m.new SweepLine(N);
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				int p3 = sc.nextInt();
				int p4 = sc.nextInt();
				sweepLine.addSegment(p1, p2, p3, p4);
			}

			sweepLine.goLineSweep();
			
			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class SweepLine {
		// Line segment
		class Segment {
			int x1, x2, y1, y2;
			boolean isVertical;

			public Segment(int x1, int y1, int x2, int y2) {
				this.x1 = x1;
				this.x2 = x2;
				this.y1 = y1;
				this.y2 = y2;
				this.isVertical = (x1 == x2);
			}

			boolean isIntersect(Segment otherSegment) {
				return false;
			}
			
			public String toString () {
				return "" + x1 + " " + y1 + " " + x2 + " " + y2;
			}
		}

		class Event implements Comparable<Event>{
			int time;
			Segment segment;

			public Event(int time, Segment segment) {
				this.time = time;
				this.segment = segment;
			}

			@Override
			public int compareTo(Event otherEvent) { // time 오름차순
				if (this.time > otherEvent.time) return 1;
				else if (this.time < otherEvent.time) return -1;
				else { // 같은 time은 y기준으로 내림차순. 위에서 sweep line이 쓸고 간다.
					if (this.segment.y1 > otherEvent.segment.y1) return -1;
					else if (this.segment.y1 < otherEvent.segment.y1) return 1;
					return 0;
				}
			}
		}

		PriorityQueue<Event> pq = new PriorityQueue<Event>();
		Segment[] segments;
		int segIndex;
		public SweepLine(int N) {
			segments = new Segment[N];
		}

		public void addSegment(int x1, int y1, int x2, int y2) {
			segments[segIndex] = new Segment(x1, y1, x2, y2);
			
			if (x1 == x2) { // vertical
				pq.add(new Event(x1, segments[segIndex]));
			} else {
				pq.add(new Event(x1, segments[segIndex]));
				pq.add(new Event(x2, segments[segIndex]));
			}
			segIndex++;
		}
		
		void goLineSweep() {
			TreeSet<Segment> ts = new TreeSet<Segment>(new Comparator<Segment>() {

				@Override
				public int compare(Segment o1, Segment o2) {// Y 큰으로 출력.
					if (o1.y2 > o2.y2) return -1;
					else if (o1.y2 < o2.y2) return 1;
					return 0;
				}});
			
			while(!pq.isEmpty()) {
				Event event = pq.peek();
				int sweepLine = event.time;
				Segment segment = event.segment;
				pq.remove();
				
				if (segment.isVertical) { // intersect check
					Iterator<Segment> it = ts.iterator();
					while(it.hasNext()) {
						Segment horizontalSeg = it.next();
						if (segment.y1 <= horizontalSeg.y1 && segment.y2 >= horizontalSeg.y1) {// line intersect
							System.out.println(segment + " intersects "+ horizontalSeg);
						}
					}
					
				} else {
					if (segment.x1 == sweepLine) {
						ts.add(segment);	
					} else if (segment.x2 == sweepLine) {
						ts.remove(segment);
					}
				}
			}
		}
		
	}
}
