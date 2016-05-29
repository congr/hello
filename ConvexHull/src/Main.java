import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// Scanner sc = new Scanner(new File("p3-s01.in"));
		Scanner sc = new Scanner(new File("sample.in"));
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

			sol.solve();

			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class Solution {
		ArrayList<Point2D> pointsList = new ArrayList<Point2D>();

		int N;
		int ind;

		public Solution(int n) {
			N = n;
		}

		void addPoint(int x, int y) {
			pointsList.add(new Point2D(x, y));
		}

		void solve() {
			// convex hull을 통해 가장 긴 거리 점을 찾고 하나씩 먼점 위주로 삭제해감.
			removeFarthestPoint();
			removeFarthestPoint();
			removeFarthestPoint();

			System.out.println(findRectangle(pointsList, null));
		}

		// 최대 정사각형 변의 길이를 리턴
		int findRectangle(ArrayList<Point2D> al, Point2D na) {
			int minX = 0, maxX = 0, minY = 0, maxY = 0;

			for (int i = 0, len = al.size(); i < len; i++) {
				Point2D p = al.get(i);
				if (na == p) continue; // na 점은 제외하고 사각형 찾기

				maxX = (int) Math.max(p.x(), maxX);
				minX = (int) Math.min(p.x(), minX);

				maxY = (int) Math.max(p.y(), maxY);
				minY = (int) Math.min(p.y(), minY);
			}

			int a = Math.abs(maxX - minX);
			int b = Math.abs(maxY - minY);

			return Math.max(a, b);
		}

		private class HullDist{
			double dist;
			int hullInd;
			
			HullDist(double dist, int ind){
				this.dist = dist;
				this.hullInd = ind;
			}
		}
		
		void fillDist(ArrayList<HullDist> dist, GrahamScan gs) {
			Point2D p = null, fp = null; // firstP
			int i = 0;
			for (Point2D q : gs.hullOrg()) {
				if (p != null) { // 처음 점부터 두번째 점까지 거리를 [0]에 저장
					double d = p.distanceTo(q);
					dist.add(new HullDist(d, i++));
				} else {
					fp = q; // 처음 점을 저장.
				}
				p = q;
			}

			// 마지막 점에서 처음 점으로의 거리를 dist[i] 마지막 인덱스에 저장
			Point2D q = fp;
			double d = p.distanceTo(q);
			dist.add(new HullDist(d, i));
		}


		void removeFarthestPoint(){
			GrahamScan gs = new GrahamScan(pointsList);
			int distSize = gs.getHullSize();
			ArrayList<HullDist> dist = new ArrayList<HullDist>();
			fillDist(dist, gs);
			
			Collections.sort(dist, new Comparator<HullDist>() {
				@Override
				public int compare(HullDist o1, HullDist o2) {
					if(o1.dist > o1.dist) return -1;
					else if (o1.dist < o1.dist) return 1;
					
					return 0;
				}
				
			});
			
			
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
				int expectA = findRectangle(pointsList, pToDel);
				int expectB = findRectangle(pointsList, qToDel);
				
				if (expectA > expectB)
					pointsList.remove(qToDel);
				else
					pointsList.remove(pToDel);
			}
		}

	}
}
