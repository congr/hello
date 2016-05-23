
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("inputM004.txt"));
//		Scanner sc = new Scanner(new File("sample2.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int N = sc.nextInt();

		Solution sol = m.new Solution(N);
		for (int i = 0; i < N; i++) {
			int p1 = sc.nextInt();
			int p2 = sc.nextInt();
			int p3 = sc.nextInt();
			int p4 = sc.nextInt();
			sol.addPoint(p1, p2, p3, p4);
		}

		sol.goSweepLine2();

		// System.out.println(sol.count + " " + sol.getMaxWidth());

		// File write
		wr.write(" ");

		sc.close();
		wr.close();
	}

	class Solution {
		class Segment implements Comparable<Segment> {
			int x1, y1;
			int x2, y2;
			int width;
			Interval1D yInterval;

			ArrayList<Integer> vIntervalR = new ArrayList<Integer>();

			public Segment(int x1, int y1, int x2, int y2) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				yInterval = new Interval1D(y1, y2);
			}

			public String toString() {
				return x1 + " " + y1 + " " + x2 + " " + y2;
			}

			public int intersectCount(Segment other) {
				int count = 0;
				if (this.y1 > other.y2 || this.y2 < other.y1)
					return 0;
				else { // 교차하는 지점.

//					int min = Math.max(this.y1, other.y1);
//					int max = Math.min(this.y2, other.y2);
					if (this.y1 <= other.y1 && other.y1 <= this.y2)
						count++;
					if (this.y1 <= other.y2 && other.y2 <= this.y2)
						count++;

					return count;
				}
			}

			public boolean isIntersect(Segment other) {
				if (this.y1 > other.y2 || this.y2 < other.y1)
					return false;
				else
					return true;
			}

			public boolean isInclusion(Segment other) { // other가 parent
				if (this.y1 > other.y2 || this.y2 < other.y1)
					return false;
				if (this.y1 > other.y1 && this.y2 < other.y2) {
					if (this.x1 > other.x1 && this.x2 < other.x2)
						return true;
				}
				return false;
			}

			Segment child;

			public void addChild(Segment child) {
				this.child = child;
			}

			@Override
			public int compareTo(Segment other) {
				if (this.x1 > other.x1)
					return -1;
				else if (this.x1 < other.x1)
					return 1;
				else
					return 0;
			}
			

		}

		class Event implements Comparable<Event> {
			int time;
			Segment segment;

			public Event(int x, Segment s) {
				time = x;
				segment = s;
			}

			@Override
			public int compareTo(Event other) {
				if (this.time > other.time)
					return -1;
				else if (this.time < other.time)
					return 1;
				else
					return 0;
			}

			public String toString() {
				return "time " + time + "| " + segment;
			}
		}

		public Solution(int size) {
			this.size = size;

			pq = new PriorityQueue<Event>(size, new Comparator<Event>() {

				@Override
				public int compare(Event e1, Event e2) {
					if (e1.time > e2.time)
						return 1;
					else if (e1.time < e2.time)
						return -1;
					else
						return 0;
				}

			});
		}

		int size;
		int index;
		int count;
		int maxArea;
		PriorityQueue<Event> pq;
		void goSweepLine2(){
			int swLine =0;
			IntervalST<Integer> intervalTree = new IntervalST<Integer>();
			LinkedList<Interval1D> list = new LinkedList<Interval1D>();
			int areaCnt = 0;
			
			while(!pq.isEmpty()){
				Event e = pq.poll();
				Integer t = e.time;
				Segment s = e.segment;
				swLine = t;
				
//				System.out.println(swLine);
				int overlapCnt =0 ;
				int totalCnt =0;
				
				list = intervalTree.searchAllList(s.yInterval);
				totalCnt = list.size();
				
				// 결과에서 자기까리 겹치는 구간 검색
				for (Interval1D y : list){
					if(y.hasIntersects(list));
						overlapCnt++;
				}
				
				int notOverlapCnt = totalCnt - overlapCnt;
				if (overlapCnt > 1)
					overlapCnt = overlapCnt * 2 -1;
				areaCnt = overlapCnt + notOverlapCnt;
				count += areaCnt;
//				System.out.println(areaCnt);
				
				if (swLine == s.x1) { // start event
					intervalTree.put(s.yInterval, t);
				} else {
					intervalTree.remove(s.yInterval);
				}
			}
			System.out.println(count);
			
		}

		void goSweepLine() {
			int swLine = 0;
			TreeSet<Segment> ts = new TreeSet<Segment>();

			while (!pq.isEmpty()) {
				Event e = pq.poll();
				Integer t = e.time;
				Segment s = e.segment;
				swLine = t;

				 System.out.println(swLine);
				java.util.Iterator<Segment> it = ts.iterator();
				int newAreaCount = 0;
				while (it.hasNext()) {
					Segment activeSegment = it.next();
					//System.out.println(activeEvent);
					if (s.isIntersect(activeSegment)) {
						if (swLine != s.x2 && s.isInclusion(activeSegment)) {
							activeSegment.addChild(s);
							// System.out.println("add active "+activeEvent+ "
							// s" + s);
							break;
						} else { // 일부 영역 교차 혹은 자기자신 segment의 end event
							int intCount = s.intersectCount(activeSegment);
							newAreaCount += intCount;
							System.out.println("intersect line "+ intCount);
							if (s.isInclusion(activeSegment)) 
								break;
						}
					}
				}

				if (newAreaCount > 0)
					newAreaCount--;
				System.out.println("area count "+ newAreaCount);
				if (newAreaCount > 0) { // 분할된 각각의 면적 구해라

					it = ts.iterator();
					while (it.hasNext()) {
						Segment seg = it.next();
						if (seg.y1 == s.y1) {
							s.width = calArea(s);
							if (s.child != null)// 자식이 있다면 자식 넓이를 빼줌.
								s.width -= calArea(s.child);
							break;
						}
					}
				}

				if (maxArea < s.width)
					maxArea = s.width;

				count += newAreaCount; // 전체 면적 개수 누적 카운트

				if (swLine == s.x1) { // start event
					ts.add(s);
				} else {
					ts.remove(s);
				}
			}

			System.out.println(count + " " + maxArea);
		}

		public int calArea(Segment s) {
			return (s.x2 - s.x1) * (s.y2 - s.y1);
		}

		public void addPoint(int x1, int y1, int x2, int y2) {
			Segment s = new Segment(x1, y1, x2, y2);
			pq.add(new Event(x1, s));
			pq.add(new Event(x2, s));
			index++;
		}
	}
}