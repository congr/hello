import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
//http://www.geeksforgeeks.org/greedy-algorithms-set-6-dijkstras-shortest-path-algorithm/
//http://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html

public class Graph {
	public static final int START = 1;

	private int vCnt;
	private ArrayList<Edge>[] adjList;// 인접 리스트.
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v
	private double[] distTo; // distTo[v] = number of edges shortest s-v path

	public Graph(int vCnt) {
		this.vCnt = vCnt;

		adjList = new ArrayList[vCnt + 1];
		edgeTo = new int[vCnt + 1];
		distTo = new double[vCnt + 1];

		for (int i = START; i < vCnt + 1; i++) {
			adjList[i] = new ArrayList<Edge>();
			distTo[i] = Double.MAX_VALUE; // 거리가 0으로 초기화되면 안됨.-1로 초기
		}
	}

	// BFS template 차이는.: pq 사용, weight비교후 더 높은 weight를 가진 간선 방문 안함.
	double[] goDijkstra() {
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(vCnt, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				if (o1.w > o2.w)
					return 1;
				else if (o1.w > o2.w)
					return -1;
				return 0;
			}
		});

		// 시작점에 해당하는 Edge
		pq.add(new Edge( 1, 0));
		edgeTo[1] = 1; // previous edge (parent)
		distTo[1] = 0; // weight

		while (!pq.isEmpty()) {
			Edge hereE = pq.poll();
			int here = hereE.v;
			double cost = hereE.w;

			if (distTo[here] < cost) // 이미 이전에 방문했는데, 더 작은 w가 아니면 pq추가할 필요없다
				continue; // 큐에서 꺼내서 무시
			System.out.println(here + " " + distTo[here]);

			// 인접한 정점들을 검사.
			for (Edge e: adjList[here]) {
				int next = e.v;
				double nextDist = cost + e.w;
				if (distTo[next] > nextDist) {
					pq.add(new Edge(next, nextDist));
					edgeTo[next] = here;
					distTo[next] = nextDist;
				}
			}
		}

		return distTo;
	}

	void addEdge(int u, int v, double w) {
		adjList[u].add(new Edge(v, w));
		adjList[v].add(new Edge(u, w));
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

	void printShortestPath() {
		System.out.println("\nprint ShortestPath");
		for (int i = START; i < vCnt + 1; i++) {
			System.out.println(i + " " + edgeTo[i] + " " + distTo[i]);
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

	public class Edge {
		int v;
		double w;

		public Edge(int v, double w) {
			this.v = v;
			this.w = w;
		}

		public String toString() {
			return v + " (" + w + ")";
		}
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			int K = sc.nextInt();

			Graph g = new Graph(N);
			for (int i = 0; i < K; i++) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				double w = sc.nextInt();
				g.addEdge(u, v, w);
			}

			g.printAdjList();

			g.goDijkstra();
			g.printShortestPath();
		}

		sc.close();
		wr.close();
	}

}
