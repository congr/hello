import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("fortress.txt"));
		Solution sol = new Solution();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();

			Circle[] circles = new Circle[N];
			for (int i = 0; i < N; i++) {
				circles[i] = new Circle(sc.nextInt(), sc.nextInt(), sc.nextInt());
			}

			sol.solve(circles);
			circles = null;

		}

		sc.close();
	}

	static class Solution {
		Circle[] circles;
		int longest;

		void solve(Circle[] circles) {
			long startTime = System.currentTimeMillis();

			this.circles = circles;
			this.longest = 0;

			// tree build
			buildTree(circles);

			long endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
			
			// getLongestPath
			startTime = System.currentTimeMillis();
			getLongestPath(circles);

			endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
		}

		void getLongestPath(Circle[] circles) {

			int root = circles.length - 1;// 맨 마지막 원이 가장 크게 소팅됨.
			int rootHeight = getHeight(circles, root);
			System.out.println(Math.max(rootHeight, this.longest));
		}

		// http://cs.nyu.edu/courses/fall02/V22.0310-002/lectures/lecture-08.html
		// 루트는 0, 자식은 1+;
		// 루트에서 얼만만큼 깊이 내려온 자식 노드다.라는 의미.
		int depth(Circle[] circles, int node) {// node는 circles[] index
			if (node == circles.length - 1) // if root - base case
				return 0;
			else
				return 1 + depth(circles, circles[node].parent);
		}

		// leaf가 0이고 루트의 최대 높이가 계산되는 구조.
		// depth 와 반대임.
		int height(Circle[] circles, int node) {
			int h = 0;

			// 자식이 없다면 h =0 return; - base case
			for (int child : circles[node].children) {
				h = Math.max(h, height(circles, child) + 1);
			}

			return h;
		}

		// 종만북 21.5 (694p) 그대로 옮김. (이해가 잘 안됨)
		int getHeight(Circle[] circles, int root) {
			ArrayList<Integer> heights = new ArrayList<Integer>();

			// 루트의 자식을 루트로 하는 서브트리의 높이를 계산한다.
			for (int child : circles[root].children) {
				heights.add(getHeight(circles, child));
			}

			if (heights.isEmpty()) // 자식이 없다면 0리턴.
				return 0;

			Collections.sort(heights);

			// root를 최상위노드로 하는 경로.
			if (heights.size() >= 2)
				longest = Math.max(longest, 2 + heights.get(heights.size() - 2) + heights.get(heights.size() - 1));

			// 트리의 높이는 서브트리의 높이에 1을 더한 값.
			return heights.get(heights.size() - 1) + 1;
		}

		void buildTree(Circle[] circles) {
			// 작은 것부터/ascending order
			Arrays.sort(circles, (Circle a, Circle b) -> a.r - b.r);

			int i = 0;
			for (Circle c : circles) {
				for (int j = ++i, l = circles.length; j < l; j++) {
					if (contained(circles[j], c)) {
						c.setParent(j);
						circles[j].addChild(i - 1); // c
						break;
					}
				}
			}
		}

		// 원중심간 거리
		int sqrdist(Circle c1, Circle c2) {
			int a = c1.x - c2.x;
			int b = c1.y - c2.y;
			return a * a + b * b;
		}

		// c1: parent이고 c2: child 인가?
		boolean contained(Circle c1, Circle c2) {
			if (c1.r > c2.r && sqrdist(c1, c2) < (c1.r - c2.r) * (c1.r - c2.r))
				return true;
			else
				return false;
		}
	}

	static class Circle {
		int x, y, r;
		ArrayList<Integer> children = new ArrayList<Integer>();
		int parent;

		public Circle(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}

		// 자식은 여러개.
		void addChild(int ind) {
			children.add(ind);
		}

		// 부모는 하나.
		void setParent(int ind) {
			parent = ind;
		}

		public String toString() {
			return x + " " + y + " " + r + " ";
		}
	}
}
