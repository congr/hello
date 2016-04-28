import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {

	// adjacent List
	ArrayList<Node>[] adj;
	Queue<Node> q;
	int vertexCnt;
	// boolean[] visited;
	int[] visitedCost;
	int[] parent;

	class Node {
		int toVertex;
		int cost;

		public Node(int toV, int cost) {
			this.toVertex = toV;
			this.cost = cost;
		}

		public String toString() {
			return " " + this.toVertex + " (" + this.cost + ") \t";
		}
	}

	public Graph(int vertexCnt, int edgeCnt) {
		this.vertexCnt = vertexCnt;

		adj = new ArrayList[vertexCnt + 1];
		for (int i = 1; i < vertexCnt + 1; i++) {
			adj[i] = new ArrayList<Node>();
		}

		q = new LinkedList<Node>();
		visitedCost = new int[vertexCnt + 1];
		parent = new int[vertexCnt + 1];
	}

	void addEdge(int v1, int v2, int cost) {
		adj[v1].add(new Node(v2, cost));
		adj[v2].add(new Node(v1, cost));
	}

	void printBfs() {
		System.out.println("printBfs with ArrayList");
		for (int i = 1; i < vertexCnt + 1; i++) {
			System.out.print(i + " -> ");
			for (int j = 0; j < adj[i].size(); j++) {
				System.out.print(adj[i].get(j).toString());
			}
			System.out.println();
		}
	}

	// void dfs(int k) {
	// System.out.println(k);
	// visited[k] = true;
	// for (int i = 0; i < adj[k].size(); i++) {
	// int t = adj[k].get(i).toVertex;
	// if (!visited[t] && t > 0) {
	// int nextK = t;
	//
	// dfs(nextK);
	// }
	// }
	// }

	ArrayList<Integer> path = new ArrayList<Integer>();

	int findShortestPath(int v1, int v2) {
		path.clear();
		path.add(v2);

		while (parent[v2] != v1) {
			v2 = parent[v2];
			path.add(v2);
		}
		path.add(v1);

//		System.out.println("shortest path");
//		for (int i = 0; i < path.size(); i++) {
//			System.out.print(path.get(i) + " ");
//		}
//		System.out.println();

		int n = path.size();
		int result = path.get(n / 2);
		System.out.println("mid vertex: " + result);

		return result;
	}

	// queue // returns 최단 경로의 길이.
	int bfs(int v1, int v2) {
		System.out.println("bfs queue");
		// start == finish인 경우,
		if (v1 == v2) return 0;
		
		// 초기화.
		Arrays.fill(visitedCost, 0, visitedCost.length, 0);
		q.clear();

		visitedCost[v1] = 1;
		q.add(new Node(v1, 0)); // 시작.

		while (!q.isEmpty()) {
			Node node = q.peek();
			int here = node.toVertex;
			//System.out.println(node.toVertex + " vCost: " + visitedCost[here]);
			q.remove();
			
			for (int j = 0; j < adj[here].size(); j++) {
				int there = adj[here].get(j).toVertex; //인접한 점.
				if (visitedCost[there] == 0) { // 방문전이라면,
					if (visitedCost[there] == v2) // finish지점이라면,
						return visitedCost[here] + 1;
					visitedCost[there] = visitedCost[here] + 1; // 여기까지 걸린 cost +1.
					parent[there] = here;// parent - 경로를 다 찍을 경우에 필요.
					q.add(adj[here].get(j));
				}
				
			}
		}
		
		return -1;// 답을 찾지 못한 경우.
	}
	
	int bidirectional(int v1, int v2) {
		System.out.println("bidirectional");
		// start == finish인 경우,
		if (v1 == v2) return 0;
		
		// 초기
		Arrays.fill(visitedCost, 0, visitedCost.length, 0);
		q.clear();

		visitedCost[v1] = 1; // 순방향 1씩 증가.
		q.add(new Node(v1, 0)); // 시작.
		
		visitedCost[v2] = -1; // 역방향 -1씩 증
		q.add(new Node(v2, 0)); // 시작.

		while (!q.isEmpty()) {
			Node node = q.peek();
			int here = node.toVertex;
			//System.out.println(node.toVertex + " vCost: " + visitedCost[here]);
			
			q.remove();
			
			for (int j = 0; j < adj[here].size(); j++) {
				int there = adj[here].get(j).toVertex; //인접한 점.
				if (visitedCost[there] == 0) { // 방문전이라면,
					visitedCost[there] = visitedCost[here] >0 ? visitedCost[here] + 1 : visitedCost[here]-1; // 여기까지 걸린 cost +1.
					parent[there] = here;// parent - 경로를 다 찍을 경우에 필요.
					q.add(adj[here].get(j));
				} 
				else if (sgn(visitedCost[there]) != sgn(visitedCost[here])) {
					
					int edgeCnt = Math.abs(visitedCost[there]) + Math.abs(visitedCost[here]) - 1;
					System.out.println("here " + here +  " shorted paths edgeCnt " + edgeCnt) ;
					
					
					return edgeCnt; // 최단거리 간선수.
				}
				
			}
		}
		
		return -1;// 답을 찾지 못한 경우.
	}
	
	int sgn (int x) {
		if (x> 0) return 1;
		else return -1;
	}
}
