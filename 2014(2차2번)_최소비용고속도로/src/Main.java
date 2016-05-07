import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("p2-s03.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt();
			int eCnt = sc.nextInt();
			int eCnt2 = sc.nextInt();
			Graph graph = m.new Graph(vCnt, eCnt);
			for (int i = 0; i < eCnt; i++) {
				graph.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt(), true);
			}
			for (int i = 0; i < eCnt2; i++) {
				graph.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt(), false);
			}
			//mst.printAdjList();
			graph.goPrim(); // 최소 비용 트리를 만듦.
			int cost = graph.getMstWeight();
			System.out.println(cost);
			//mst.printMst();
		}

		sc.close();
		wr.close();
	}

	// 최소비용 스패닝 트리
	class Graph {
		int vCnt;
		int eCnt;
		ArrayList<Edge> adjList[];
		boolean visited[];
		ArrayList<Edge> mst;
		int start = 1; // 입력 값이 0 혹은 1부터 시작 값으로 초기화

		class Edge {
			int from, to;
			int weight;
			boolean isC;

			Edge(int f, int t, int w, boolean isC) {
				from = f;
				to = t;
				weight = w;
				this.isC = isC;
			}

			public String toString() {
				return to + "(" + weight + ")  ";
			}
		}

		public Graph(int vCnt, int eCnt) {
			this.vCnt = vCnt;
			this.eCnt = eCnt;

			visited = new boolean[vCnt + 1];
			adjList = new ArrayList[vCnt + 1];
			mst = new ArrayList<Edge>(); // 최소비용 신장 트리를 저장할 리스트

			for (int i = start, len = vCnt + 1; i < len; i++) {
				adjList[i] = new ArrayList<Edge>();
			}
		}

		public void preparePrim() {
			ArrayList m;
			for (int i=start, len = adjList[i].size(); i<len; i++){
				goPrim();
				m = mst;
				
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

		int constructedSum;
		void addEdge(int v1, int v2, int weight, boolean isConstructed) {
			if (isConstructed) {
				constructedSum += weight;
				weight *= -1;
			}
			adjList[v1].add(new Edge(v1, v2, weight, isConstructed));
			adjList[v2].add(new Edge(v2, v1, weight, isConstructed));
		}
		
		int getMstWeight() {
			int result1 =0;
			int result2=0;
			for (Edge e : mst) {
				if (e.isC) {
					result1 += e.weight;
				}
				else 
					result2 += e.weight;
				
			}
			return constructedSum + result1 + result2;
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
