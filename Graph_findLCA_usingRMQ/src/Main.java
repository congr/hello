import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt(); // vertex cnt
			int qCnt = sc.nextInt(); // query cnt
			Dfs dfs = m.new Dfs(vCnt);
			for (int i = 0; i < vCnt - 1; i++) {
				dfs.addEdge(sc.nextInt(), sc.nextInt());
			}
			dfs.printAdjList();
			dfs.goEuler(); // euler tour - level memoization - first occurrence
							// array filled up
			for (int i = 0; i < qCnt; i++) {
				dfs.query(sc.nextInt(), sc.nextInt());
			}
		}

		sc.close();
		wr.close();
	}

	class Dfs {
		private final int vCnt;
		private ArrayList<Integer>[] adjList;// 인접 리스트.
		private boolean visited[];
		private static final int START = 1;

		public Dfs(int vCnt) {
			this.vCnt = vCnt;

			visited = new boolean[vCnt + 1];
			adjList = new ArrayList[vCnt + 1];

			for (int i = START; i < vCnt + 1; i++) {
				adjList[i] = new ArrayList<Integer>();
			}

			eulerTour = new int[vCnt * 2 + 1];
			eulerLevel = new int[vCnt * 2 + 1];
			firstVisit = new int[vCnt + 1];
		}

		// int route[] = new int[vCnt + 1];

		void addEdge(int v1, int v2) {
			adjList[v1].add(v2);
			adjList[v2].add(v1);
		}

		int eulerTour[]; // 오일러 투어를 하면서 방문한 정
		int ind; // eulerTour 배열 인덱
		int eulerLevel[]; // 트리 레
		int level;
		int firstVisit[]; // 정점을 처음 방문할 때 index

		void goDfs(int k) {
			visited[k] = true;
			eulerTour[++ind] = k;
			eulerLevel[ind] = ++level;
			firstVisit[k] = firstVisit[k] == 0 ? ind : firstVisit[k];
			// System.out.print(k + " (" + level + ") ");

			for (int i = 0, len = adjList[k].size(); i < len; i++) {
				int adjNext = adjList[k].get(i);
				if (!visited[adjNext]) {
					goDfs(adjNext);
					eulerTour[++ind] = k;
					eulerLevel[ind] = --level;
					firstVisit[k] = firstVisit[k] == 0 ? ind : firstVisit[k];
					// System.out.print(k + " (" + level + ") "); // 오일러 투어
				}
			}
		}

		// 오일러 투어를 DFS로 해서 오일러투어 Array와 level Array를 채우고, 각 정점이 처음 방문된
		// firstVisit[]을 만든다.
		SegmentTree stree;

		void goEuler() {
			ind = 0;
			Arrays.fill(eulerTour, 0);
			Arrays.fill(eulerLevel, 0);
			goDfs(1);

			System.out.println("\neulerTour ");
			for (int i = START, len = eulerTour.length; i < len; i++) {
				if (eulerTour[i] == 0)
					break;
				System.out.print(eulerTour[i] + " (" + eulerLevel[i] + ") ");
			}

			System.out.println("\nfirst occurrence");
			for (int i = START, len = firstVisit.length; i < len; i++) {
				System.out.print(firstVisit[i] + " ");
			}

			stree = new SegmentTree(eulerLevel);

		}

		int query(int from, int to) {
			System.out.println("\nquery " + from + " " + to);
			int startRange = Math.min(firstVisit[from], firstVisit[to]);
			int endRange = Math.max(firstVisit[from], firstVisit[to]);

			// Least Minimum Ancestor (lca)
			int lowestLevel = stree.RMinQ(startRange, endRange);
			int lcaIndex = findIndex(lowestLevel, startRange, endRange);

			System.out.println(
					"eulerLevel: " + lowestLevel + " => lca (" + from + ", " + to + ") = " + eulerTour[lcaIndex]);

			// distance
			// int distance = from level + to level - 2*lca level

			return -1;
		}

		// segment tree 에서 최소값을 갖는 인덱스는 찾을 수 없어서, 최소값을 갖는 배열의 인덱스를 다시 찾는다.
		int findIndex(int target, int fromInd, int toInd) {
			for (int i = fromInd; i <= toInd; i++) {
				if (target == eulerLevel[i])
					return i;
			}
			return -1;
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = START; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjList[i].size(); j++)
					System.out.print(adjList[i].get(j).toString());
				System.out.println("");
			}
		}
	}
}
