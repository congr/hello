import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt();
			int eCnt = sc.nextInt();
			Mst mst = m.new Mst(vCnt, eCnt);
			for (int i = 0; i < eCnt; i++) {
				mst.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt());
			}
			mst.printAdjList();
			mst.goPrim(); // 최소 비용 트리를 만듦.
			mst.printMst();
		}

		sc.close();
		wr.close();
	}

	// 최소비용 스패닝 트리
	class Mst {
		int vCnt;
		int eCnt;
		ArrayList<Edge>[] adjList;
		boolean visited[];
		ArrayList<Edge> mst;
		int start = 1; // 입력 값이 0 혹은 1부터 시작 값으로 초기화

		class Edge {
			int from, to;
			double weight;

			Edge(int f, int t, double w) {
				from = f;
				to = t;
				weight = w;
			}

			public String toString() {
				return to + "(" + weight + ")  ";
			}
		}

		public Mst(int vCnt, int eCnt) {
			this.vCnt = vCnt;
			this.eCnt = eCnt;

			visited = new boolean[vCnt + 1];
			adjList = new ArrayList[vCnt + 1];
			mst = new ArrayList<Edge>(); // 최소비용 신장 트리를 저장할 리스트

			for (int i = start, len = vCnt + 1; i < len; i++) {
				adjList[i] = new ArrayList<Edge>();
			}
		}

		// 인접한 점들을 우선순위큐에 넣고, 최저weight로 연결된 점을 팝하면서 다시 인접한 (방문전일 경우) 큐에 넣는다.
		// 이 과정에 선택된 점을 최소비용스패닝트리에 추가함
		public void goPrim() {
			PriorityQueue<Edge> pq = new PriorityQueue<Edge>(vCnt, new Comparator<Edge>() {
				@Override
				public int compare(Edge e1, Edge e2) {
					if (e1.weight > e2.weight)
						return 1;
					else if (e1.weight < e2.weight)
						return -1;
					else
						return 0;
				}
			});

			mst.clear();

			// 우선순위 큐에 1번째 인접리스트 모두 넣기.
			for (int i = 0, len = adjList[start].size(); i < len; i++) {
				pq.add(adjList[start].get(i));
			}

			visited[start] = true;
			while (!pq.isEmpty()) {
				Edge e = pq.peek();
				pq.poll(); // 가장 작은 아이템 팝.

				if (visited[e.from] && visited[e.to]) // from, to 둘다 방문전일 때만 아래
					continue; // 코드 실행하고, 아니면 큐에서 그냥 팝

				visited[e.from] = true;
				for (Edge edge : adjList[e.to]) { // 큐에서 젤 작은 아이템을 팝하고, 팝한 점의
					if (!visited[edge.to]) {// 인접점을 큐에 추가
						pq.add(edge);
					}
				}
				visited[e.to] = true;
				mst.add(e);// 최소 비용 트리에 넣기.
			}
		}

		void addEdge(int v1, int v2, double weight) {
			adjList[v1].add(new Edge(v1, v2, weight));
			adjList[v2].add(new Edge(v2, v1, weight));
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = start; i < adjList.length; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjList[i].size(); j++)
					System.out.print(adjList[i].get(j).toString());
				System.out.println("");
			}
		}

		void printMst() {
			System.out.println("MST: ");
			for (Edge e : mst) {
				System.out.println(e.from + " - " + e.to + " : " + e.weight);
			}
		}
	}
}
