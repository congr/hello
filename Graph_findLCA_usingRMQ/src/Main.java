import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//http://www.geeksforgeeks.org/find-lca-in-binary-tree-using-rmq/
//https://www.topcoder.com/community/data-science/data-science-tutorials/range-minimum-query-and-lowest-common-ancestor/

public class Main {

	public static void main(String[] args) throws IOException {
		// Scanner sc = new Scanner(new File("sample.in"));
		Scanner sc = new Scanner(new File("problem4.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		// while (tc-- > 0) {
		int vCnt = sc.nextInt(); // vertex cnt
		int qCnt = sc.nextInt(); // query cnt
		Dfs dfs = m.new Dfs(vCnt);
		for (int i = 0; i < vCnt - 1; i++) {
			dfs.addEdge(sc.nextInt(), sc.nextInt());
		}
		// dfs.printAdjList();
		dfs.goEuler(); // euler tour - level memoization - first occurrence
						// array filled up
		for (int i = 0; i < qCnt; i++) {
			int midNode = dfs.query(sc.nextInt(), sc.nextInt());
			wr.write(midNode + "\n");
		}
		// }

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
			parent = new int[vCnt + 1];
		}

		// int route[] = new int[vCnt + 1];

		void addEdge(int v1, int v2) {
			adjList[v1].add(v2);
			adjList[v2].add(v1);
		}

		int eulerTour[]; // 오일러투어를 하면서 방문한 정점을 기록. 실제 정점수 * 2배정도 공간필요.
		int tourInd; // eulerTour 배열 인덱스.
		int eulerLevel[]; // 오일러투어를 하면서 트리 레벨(depth)을 기록.
		int level;
		int firstVisit[]; // 정점을 처음 방문할 때 index
		int parent[]; // 각 정점의 parent를 기록. lca를 이용한 거리를 알아내서 부모방향으로 거슬러 올라가야함.

		void goDfs(int k, int p) { // 인접리스트 배열의 다음 배열 인덱스 k, k의 parent는 p
			visited[k] = true;
			eulerTour[++tourInd] = k;
			eulerLevel[tourInd] = ++level;
			firstVisit[k] = firstVisit[k] == 0 ? tourInd : firstVisit[k];
			parent[k] = p;

			for (int i = 0, len = adjList[k].size(); i < len; i++) {
				int adjNext = adjList[k].get(i);
				if (!visited[adjNext]) {

					goDfs(adjNext, k);
					eulerTour[++tourInd] = k;
					eulerLevel[tourInd] = --level;
					firstVisit[k] = firstVisit[k] == 0 ? tourInd : firstVisit[k];
				}
			}
		}

		// 오일러투어를 DFS로 수해서 오일러투어 Array와 level[]를 채우고, 각 정점이 처음 방문된
		// firstVisit[]을 만든다. level[]로 segment Tree 빌드해서 lca를 찾을때 빠름.
		SegmentTree stree;

		void goEuler() {
			goDfs(1, 1);

			// printEuler();

			// 레벨을 이용해 세그먼트 트리로 최소레벨값을 찾는 RMQ를 할 수 있도록한다.
			stree = new SegmentTree(eulerLevel);
		}

		// 오일러 투어 결과 프린트.
		void printEuler() {
			System.out.println("\neulerTour ");
			for (int i = START, len = eulerTour.length; i < len; i++) {
				if (eulerTour[i] == 0)
					break;
				System.out.print(eulerTour[i] + " (" + eulerLevel[i] + ") ");
			}

			System.out.println("\nfirst occurrence");
			for (int i = START, len = vCnt + 1; i < len; i++) {
				System.out.print(firstVisit[i] + " ");
			}

			System.out.println("\nparent");
			for (int i = START, len = vCnt + 1; i < len; i++) {
				System.out.print(i + " (" + parent[i] + ") ");
			}
		}

		int query(int from, int to) {
			// System.out.println("\nquery " + from + " " + to);
			int startRange = Math.min(firstVisit[from], firstVisit[to]);
			int endRange = Math.max(firstVisit[from], firstVisit[to]);

			// Least Minimum Ancestor (lca)
			int lowestLevel = stree.RMinQ(startRange, endRange);// 최소값 RMQ from
																// segmentTree
			int lcaIndex = findIndex(lowestLevel, startRange, endRange);
			
			// System.out.println(
			// "eulerLevel: " + lowestLevel + " => lca (" + from + ", " + to +
			// ") = " + eulerTour[lcaIndex]);

			int midNode = getMidNode(startRange, endRange, lcaIndex, from, to);
			System.out.println(midNode);
			return midNode;
		}

		// lca를 이용해 distance 구해서 중간 노드 구하기.
		int getMidNode(int startRange, int endRange, int lcaIndex, int from, int to) {

			int distance = eulerLevel[startRange] + eulerLevel[endRange] - 2 * eulerLevel[lcaIndex];
			int tripDist = 0;
			int midNode = 0;

			if (distance <= 1) // 거리가 1이면 start가 중간방으로 답이 됨.
				midNode = from;
			else {
				// startRange/endRange는 오일러투어array의 index이므로 swap된 값.
				// 중간노드 찾기 위한 비교에는 쓰면 안됨.
				if (eulerLevel[firstVisit[from]] == eulerLevel[firstVisit[to]]) {
					midNode = eulerTour[lcaIndex]; // 레벨이 같으면 lca가 중간방.
				} else {
					int index = 0; // level(depth)가 깊은 쪽에서 올라올 용도.
					if (eulerLevel[firstVisit[from]] < eulerLevel[firstVisit[to]]) {
						// 간선의 길이가 짝수라면 앞쪽방 선택을 위해 .5는 올림.
						tripDist = (int) Math.ceil((double) distance / 2);
						index = to;
					} else {
						tripDist = distance / 2;
						index = from;
					}

					// lca를 향해 tripDist카운트만큼 올라감.
					midNode = tripToParent(tripDist, index);
				}
			}

			// System.out.println("distance: " + distance + " tripDist: " +
			// tripDist);

			return midNode;
		}

		// parent[] 를 거슬러 올라 가는데, tripDist 카운트 만큼 올라간 지점을 중간방으로 리턴.
		int tripToParent(int tripDist, int sIndex) {
			// System.out.println("tripToParent " + sIndex);
			int p = parent[sIndex];
			for (int cnt = 0; cnt < tripDist - 1; cnt++) {
				p = parent[p];
			}

			return p;
		}

		// TODO : segment tree build시 leaf노드의 index를 카피하도록 하자.
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
					System.out.print(adjList[i].get(j).toString() + " ");
				System.out.println("");
			}
		}
	}
}
