
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("test2.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int N = sc.nextInt();

		Solution sol = m.new Solution();
		for (int i = 0; i < N; i++) {
			int p1 = sc.nextInt();
			int p2 = sc.nextInt();
			int p3 = sc.nextInt();
			int p4 = sc.nextInt();
			sol.addPoint(p1, p2, p3, p4);
		}

		System.out.println(sol.count + " " + sol.getMaxWidth());

		// File write
		wr.write(" ");

		sc.close();
		wr.close();
	}

	class Solution {
		class Rect {
			int x1, y1;
			int x2, y2;
			int width;

			public Rect(int x1, int y1, int x2, int y2) {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
			}
		}

		ArrayList<Rect> list = new ArrayList<Rect>();

		public Solution() {

		}

		int index;
		int count;

		public void addPoint(int x1, int y1, int x2, int y2) {
			Rect curr = new Rect(x1, y1, x2, y2);
			int cWidth = area(curr);
			int xWidth = 0;

			// 처음 점은 그냥 추가.
			if (!list.isEmpty()) { // 두번째 점 부터 list 에 추가된 모든 점과 겹치는 영역이 있는지 체크.
				for (int i = index - 1; i >= 0; i--) {
					Rect prev = list.get(i);

					if (prev.x2 > curr.x1 && prev.y2 > curr.y1) {// 교차면적이 있다면,
						count++;

						xWidth = area(curr.x1, curr.y1, prev.x2, curr.y2); // 교차
																			// 면적
																			// 넓이.
						prev.width -= xWidth; // 이전 면적에서 교차 면적을 빼서 갱신.

					}
				}
			}

			list.add(curr);
			curr.width = cWidth - xWidth; // 교차면적을 뺀 면적을 세
			index++; // 리스트 인덱
			count++; // 면적의 개
		}

		// 네점의 면
		public int area(int x1, int y1, int x2, int y2) {

			//System.out.println(Math.abs(x1 - x2) * Math.abs(y1 - y2));
			return Math.abs(x1 - x2) * Math.abs(y1 - y2);
		}

		public int area(Rect r) {
			return area(r.x1, r.y1, r.x2, r.y2);
		}

		public int getMaxWidth() {
			int max = 0;
			for (Rect rect : list) {
				if (max < rect.width)
					max = rect.width;
			}

			return max;
		}

	}
}
