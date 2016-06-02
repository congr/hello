import java.io.File;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Bfs {
	public static final int START = 1;

	private int vCnt;
	private ArrayList<Integer>[] adjList;// 인접 리스트.
	private boolean visited[];
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v
	private int[] distTo; // distTo[v] = number of edges shortest s-v path

	public Bfs(int vCnt) {
		this.vCnt = vCnt;

		adjList = new ArrayList[vCnt + 1];
		visited = new boolean[vCnt + 1];
		edgeTo = new int[vCnt + 1];
		distTo = new int[vCnt + 1];

		for (int i = START; i < vCnt + 1; i++) {
			adjList[i] = new ArrayList<Integer>();
			distTo[i] = -1; // 거리가 0으로 초기화되면 안됨.-1로 초기
		}
	}

	int goBfs(int s, int e) {
		Queue<Integer> q = new ArrayDeque<Integer>();
		q.add(s);
		visited[s] = true;
		edgeTo[s] = 0;
		distTo[s] = 0;

		while (!q.isEmpty()) {
			int v = q.poll();

			for (int i = 0, len = adjList[v].size(); i < len; i++) {
				int w = adjList[v].get(i);
				if (!visited[w]) {
					q.add(w);
					visited[w] = true;
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;

					if (w == e)
						return distTo[w];
				}
			}
		}

		return -1; // s - e 간 path를 못찾은 경우.
	}

	void addEdge(int v1, int v2) {
		adjList[v1].add(v2);
		adjList[v2].add(v1);
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

	boolean debug = true;

	void println(String x) { // DFS 에 쓰면 stackoverflow주의.
		if (debug)
			System.out.println(x);
	}

	void print(String x) {
		if (debug)
			System.out.print(x + " ");
	}

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt();
			int K = sc.nextInt();

			Bfs bfs = new Bfs(N);
			for (int i = 0; i < N - 1; i++) {
				int p1 = sc.nextInt();
				int p2 = sc.nextInt();
				bfs.addEdge(p1, p2);
			}

			// bfs.printAdjList();

			for (int i = 0; i < K; i++) {// query
				int result = bfs.goBfs(sc.nextInt(), sc.nextInt());
				wr.write(result + "\n");
				System.out.println(result);
			}
		}

		sc.close();
		wr.close();
	}
}
