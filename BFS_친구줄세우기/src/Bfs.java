import java.io.File;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Scanner;

public class Bfs {
	public static final int START = 1;

	private int vCnt;
	private ArrayList<Integer>[] adjList;// 인접 리스트.
	private boolean visited[];
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
	private int[] distTo; // distTo[v] = number of edges shortest s-v path
	private int[] path;

	public Bfs(int vCnt) {
		this.vCnt = vCnt;

		adjList = new ArrayList[vCnt + 1];
		visited = new boolean[vCnt + 1];
		edgeTo = new int[vCnt + 1];
		distTo = new int[vCnt + 1];
		path = new int[vCnt + 1];
		for (int i = 0; i < vCnt + 1; i++) {
			adjList[i] = new ArrayList<Integer>();
			distTo[i] = -1; // 거리가 0으로 초기화되면 안됨.-1로 초기
		}
	}

	// 모든 점이 짝수개 간선을 갖는 다면 오일러 서킷.
	int findStart() {
		// 홀수 노드 혹은 edge가 1개인 노드

		int start = 0;
		int prev = 1;
		for (int i = START; i < vCnt + 1; i++) {
			int size = adjList[i].size();
			if (!visited[i] && size > 0) {
				if (size == 1) { // 인접한 간선의 수가 1인 점에서 시작하자.
					return i;
				} else if (size % 2 == 1) { // 인접한 간선의 수가 1인 점이 없다면, 홀수 점 중에서
											// 시작.
					start = Math.min(size, adjList[prev].size());
				}
				prev = i;
			}
		}

		return start;
	}

	int goBfs(int s, int eCnt) {
		int ind = 0;

		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(s);
		visited[s] = true;
		edgeTo[s] = 0;
		distTo[s] = 0;
		int v = 0;
		while (!q.isEmpty()) {
			v = q.poll();
			print(v + "");
			path[v] = ++ind;

			for (int i = 0, len = adjList[v].size(); i < len; i++) {
				int w = adjList[v].get(i);
				if (!visited[w]) {
					q.add(w);
					visited[w] = true;
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
				}
			}
		}

		return ind; // s - e 간 path를 못찾은 경우.
	}

	void addEdge(int v1, int v2) {
		adjList[v1].add(v2);
		adjList[v2].add(v1);
		edges.add(new Edge(v1, v2));
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

	// 간선수가 작은 노드를 먼저 방문하기위해, 인접리스트의 노드를 소팅.
	void sortAdjList() {
		for (int i = START; i < vCnt + 1; i++) {
			for (int j = 0; j < adjList[i].size(); j++) {
				ArrayList<Integer> al = adjList[i];
				Collections.sort(al, new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						int a = adjList[o1].size();
						int b = adjList[o2].size();
						if (a > b)
							return 1;
						else if (a < b)
							return -1;

						return 0;
					}

				});
			}
		}
	}

	boolean debug = true;

	void println(String x) { // DFS 에 쓰면 stackoverflow주의.
		if (debug)
			System.out.println(x);
	}

	void print(String x) {
		if (debug)
			System.out.print(x + " ");
	}

	class Edge {
		int v, u;

		public Edge(int u, int v) {
			this.v = v;
			this.u = u;
		}

		public String toString() {
			return u + " " + v;
		}
	}

	ArrayList<Edge> edges = new ArrayList<Edge>();

	int query() {
		int result = 0;
		println("");
		for (Edge e : edges) {
			int a = Math.abs(path[e.u] - path[e.v]);
			println(e + " - " + a);

			result += a;
		}
		return result;
	}

	int findConnectedNodeCnt() {
		int cnt = 0;
		for (int i = START; i < vCnt + 1; i++) {
			if (adjList[i].size() > 0) {
				cnt++;
			}
		}

		return cnt;
	}

	public static void main(String[] args) throws Exception {
		// Scanner sc = new Scanner(new File("problem3.in"));
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt(); // vertex cnt
			int K = sc.nextInt(); // edge cnt

			Bfs bfs = new Bfs(N);
			for (int i = 0; i < K; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				bfs.addEdge(p1, p2);
			}

			bfs.printAdjList();

			bfs.sortAdjList();

			int cnt = 0;
			int total = bfs.findConnectedNodeCnt();
			while (cnt < total) { // disconnected graph
				int s = bfs.findStart();
				if (s == 0)
					break;
				cnt = bfs.goBfs(s, K); // visited edge count
			}

			int result = bfs.query();
			wr.write(result + "\n");
			System.out.println(result);
		}

		sc.close();
		wr.close();
	}
}
