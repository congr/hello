import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("p3-s01.in"));
//		 Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();

			Solution sol = m.new Solution(N);
			for (int i = 0; i < N; i++) {
				sol.addPoint(sc.nextInt(), sc.nextInt());
			}

			int result = (int)sol.solve();
			System.out.println(result);

			// File write
			wr.write(result + "\n");
		}

		sc.close();
		wr.close();
	}

	private static class XOrder implements Comparator<Point2D> {
		public int compare(Point2D p, Point2D q) {
			if (p.x() < q.x())
				return -1;
			if (p.x() > q.x())
				return +1;
			return 0;
		}
	}

	// compare points according to their y-coordinate
	private static class YOrder implements Comparator<Point2D> {
		public int compare(Point2D p, Point2D q) {
			if (p.y() < q.y())
				return -1;
			if (p.y() > q.y())
				return +1;
			return 0;
		}
	}

	class Solution {
		ArrayList<Point2D> pointsList = new ArrayList<Point2D>();
		ArrayList<Point2D> pointsListX = new ArrayList<Point2D>();

		int N;
		int ind;

		public Solution(int n) {
			N = n;
		}

		void addPoint(int x, int y) {
			pointsList.add(new Point2D(x, y));
		}

		double solve() {
			// pointsListX.addAll(pointsList);
			pointsList.sort(new YOrder());
			// pointsListX.sort(new YOrder());

			// convex hull을 통해 가장 긴 거리 점을 찾고 하나씩 먼점 위주로 삭제해감.
			int size = pointsList.size();
			int removed = 0;
			while (removed <3) {
				removeFarthestPoint();
				removed = (size - pointsList.size());
//				 System.out.println(removed + " removed");
			}

			//System.out.println(findRectangleEdge(pointsList, null));
			
			return findRectangleEdge(pointsList, null);
			
		}

		// 최대 정사각형 변의 길이를 리턴
		double findRectangleEdge(ArrayList<Point2D> al, Point2D na) {
			double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE, minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;

			for (int i = 0, len = al.size(); i < len; i++) {
				Point2D p = al.get(i);
				if (na == p)
					continue; // na 점은 제외하고 사각형 찾기

				maxX =  Math.max(p.x(), maxX);
				minX =  Math.min(p.x(), minX);

				maxY =  Math.max(p.y(), maxY);
				minY =  Math.min(p.y(), minY);
			}

			double a = (maxX - minX);
			double b = (maxY - minY);
			
//				System.out.println(a + " " + b);
			return Math.min(a, b);
		}

		double expectRectangle(ArrayList<Point2D> al, Point2D na) {
			double minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

			for (int i = 0, len = al.size(); i < len; i++) {
				Point2D p = al.get(i);
				if (na == p)
					continue; // na 점은 제외하고 사각형 찾기

				maxX =  Math.max(p.x(), maxX);
				minX =  Math.min(p.x(), minX);

				maxY =  Math.max(p.y(), maxY);
				minY =  Math.min(p.y(), minY);
			}

			double a = Math.abs(maxX - minX);
			double b = Math.abs(maxY - minY);

			double d = Math.max(a, b);
//			System.out.println(a + " " + b);
			return d * d;
		}

		private class HullDist implements Comparable<HullDist> {
			double dist;
			int hullInd;
			Point2D from;
			Point2D to;

			HullDist(double dist, int ind, Point2D from, Point2D to) {
				this.dist = dist;
				this.hullInd = ind;
				this.from = from;
				this.to = to;
			}

			@Override
			public int compareTo(HullDist o) {
				if (this.dist > o.dist)
					return -1;
				else if (this.dist < o.dist)
					return 1;
				return 0;
			}
		}

		void fillDist(ArrayList<HullDist> dist, GrahamScan gs) {
			Point2D p = null, fp = null; // firstP
			int i = 0;
			for (Point2D q : gs.hullOrg()) {
				if (p != null) { // 처음 점부터 두번째 점까지 거리를 [0]에 저장
					double d = p.distanceSquaredTo(q);
					dist.add(new HullDist(d, i++, p, q));
				} else {
					fp = q; // 처음 점을 저장.
				}
				p = q;
			}

			// 마지막 점에서 처음 점으로의 거리를 dist[i] 마지막 인덱스에 저장
			Point2D q = fp;
			double d = p.distanceSquaredTo(q);
			dist.add(new HullDist(d, i, p, q));
		}

		void removeFarthestPoint() {
			// convex hull
			GrahamScan gs = new GrahamScan(pointsList);
			ArrayList<HullDist> dist = new ArrayList<HullDist>();
			ArrayList<HullDist> distOrg = new ArrayList<HullDist>();
			// 점간 거리 계산해서 dist에 채움.
			fillDist(dist, gs);
			distOrg.addAll(dist);

			Collections.sort(dist);

			int i = 0;
			HullDist hd = dist.get(i);
			HullDist hd2 = dist.get(i + 1);
			if (hd.dist == hd2.dist) { // 가장 긴 간선 두개가 길이가 같다면, 면적 비교.
				while (hd.dist == hd2.dist) {
					double test[] = new double[4];
					double a = test[0] = expectRectangle(pointsList, hd.from);
					double b = test[1] = expectRectangle(pointsList, hd.to);
					double c = test[2] = expectRectangle(pointsList, hd2.from);
					double d = test[3] = expectRectangle(pointsList, hd2.to);

					// 면적이 가장 작아 지는 점을 제거.
					Arrays.sort(test); // 오름차순으로 소팅.

					if (test[0] == a)
						pointsList.remove(hd.from);
					if (test[0] == b)
						pointsList.remove(hd.to);
					if (test[0] == c)
						pointsList.remove(hd2.from);
					if (test[0] == d)
						pointsList.remove(hd2.to);

					i++;
					if (i >= dist.size() - 1)
						break;
					hd = dist.get(i);
					hd2 = dist.get(i + 1);
				}
			} else {
				checkDistance(gs, hd.hullInd, distOrg);
				
				

			}
		}

		void checkDistance(GrahamScan gs, int longestEdgeInd, ArrayList<HullDist> dist) {
			int distSize = gs.getHullSize();
			Point2D pToDel = dist.get(longestEdgeInd).from, qToDel = dist.get(longestEdgeInd).to;
			
			// 가장긴 간선에서 서로 이전/다음 간선 중 더 긴 간선에 연결된 점은 제거
			// 즉, 가장 멀리 떨어진 점을 제거하여 다시 convex hull루틴 실행
			int a = longestEdgeInd - 1, b = longestEdgeInd + 1; // longestEdgeInd를
																// 기준으로
																// 앞간선길이a/뒤간선길이b
			if (longestEdgeInd == 0) {
				a = distSize - 1;// 배열 마지막 인덱스
				b = 1;
			} else if (longestEdgeInd == distSize - 1) { // 배열 마지막 인덱스라면,
				a = longestEdgeInd - 1; //
				b = 0; // 다음으로 0
			}

//			if (dist.get(a).dist > dist.get(b).dist) // remove p point
//				pointsList.remove(pToDel);
//			else if (dist.get(a).dist < dist.get(b).dist) // remove q point
//				pointsList.remove(qToDel);
//			else { // 길이가 같으면 제거될 면적으로 비교
//				int expectA = expectRectangle(pointsList, pToDel);
//				int expectB = expectRectangle(pointsList, qToDel);
//				
//				if (expectA > expectB)
//					pointsList.remove(qToDel);
//				else
//					pointsList.remove(pToDel);
//			}
			
			double expectA = expectRectangle(pointsList, pToDel);
			double expectB = expectRectangle(pointsList, qToDel);
			
			if (expectA > expectB) {
//				System.out.println(qToDel + " removed");
				pointsList.remove(qToDel);
			}
			else if (expectA < expectB) {
//				System.out.println(pToDel + " removed");
				pointsList.remove(pToDel);
			}
			else {
//				System.out.println(qToDel + " removed");
//				System.out.println(pToDel + " removed");
				pointsList.remove(qToDel);
				pointsList.remove(pToDel);
			}
		}

		void removeFarthestPoint2() {
			GrahamScan gs = new GrahamScan(pointsList);
			int distSize = gs.getHullSize();
			double dist[] = new double[distSize];

			Point2D p = null, fp = null; // firstP
			int i = 0;
			double maxDist = 0;
			int maxInd = -1; // 가장 긴 거리를 저장하고 있는 dist[]배열 인덱스
			Point2D pToDel = null, qToDel = null;
			for (Point2D q : gs.hullOrg()) {
				if (p != null) { // 처음 점부터 두번째 점까지 거리를 [0]에 저장
					double d = p.distanceTo(q);
					if (d > maxDist) { // 가장 거리가 긴 간선을 저장.
						maxDist = d;
						maxInd = i;
						pToDel = p;
						qToDel = q;
					} else if (d == maxDist) {

					}
					dist[i++] = d;
				} else {
					fp = q; // 처음 점을 저장.
				}
				p = q;
			}

			// 마지막 점에서 처음 점으로의 거리를 dist[i] 마지막 인덱스에 저장
			Point2D q = fp;
			double d = p.distanceTo(q);
			if (d > maxDist) { // 가장 거리가 긴 간선을 저장.
				maxDist = d;
				maxInd = i;
				pToDel = p;
				qToDel = q;
			}
			dist[i] = d;

			// 가장긴 간선에서 서로 이전/다음 간선 중 더 긴 간선에 연결된 점은 제거
			// 즉, 가장 멀리 떨어진 점을 제거하여 다시 convex hull루틴 실행
			int a = maxInd - 1, b = maxInd + 1; // maxInd를 기준으로 앞간선길이a/뒤간선길이b
			if (maxInd == 0) {
				a = distSize - 1;// 배열 마지막 인덱스
				b = 1;
			} else if (maxInd == distSize - 1) { // 배열 마지막 인덱스라면,
				a = maxInd - 1; //
				b = 0; // 다음으로 0
			}

			if (dist[a] > dist[b]) // remove p point
				pointsList.remove(pToDel);
			else if (dist[a] < dist[b]) // remove q point
				pointsList.remove(qToDel);
			else { // 길이가 같으면 제거될 면적으로 비교
				double expectA = expectRectangle(pointsList, pToDel);
				double expectB = expectRectangle(pointsList, qToDel);

				if (expectA > expectB)
					pointsList.remove(qToDel);
				else
					pointsList.remove(pToDel);
			}
		}

	}
}
