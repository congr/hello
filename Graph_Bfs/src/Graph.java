import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import Main.Graph.Node;

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
		int weight;

		public Node(int toV, int weight) {
			this.toVertex = toV;
			this.weight = weight;
		}

		public String toString() {
			return " " + this.toVertex + " (" + this.weight + ") \t";
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
			
			for (int j = 0, len = adj[here].size(); j < len; j++) {
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
			
			for (int j = 0, len = adj[here].size(); j < len; j++) {
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
	
	int[] minWeight;

	void findMinWeightPath(int k) {
		System.out.println("findMinWeightPath");
		Arrays.fill(visitedCost, 0, visitedCost.length, 0);
		minWeight = new int[vertexCnt + 1];

		visitedCost[k] = 1;
		q.add(new Node(k, 0)); // 시작

		while (!q.isEmpty()) {
			Node n = q.peek();
			int popV = n.toVertex; // 큐에서 제거되는 V
			// System.out.println(n.toVertex + " vCost: " + visitedCost[popV]);
			q.remove();

			// 제거되는V점(popV)의 인접한 점(ArrayLists)들을 검사.
			for (int j = 0, len = adj[popV].size(); j < len; j++) { 
				int t = adj[popV].get(j).toVertex; // 추가할 점.
				int weight = adj[popV].get(j).weight; // popV <-> t 간선의 weight

				if (visitedCost[t] == 0) {// 첫 방문.
					minWeight[t] = weight + minWeight[popV];
					visitedCost[t] = visitedCost[popV] + 1;
					parent[t] = popV;// parent
					q.add(adj[popV].get(j));
				} else {
					// minWeight 보다 작은 값이라면 갱
					if (minWeight[t] > weight + minWeight[popV]) { 
						minWeight[t] = weight + minWeight[popV];
						parent[t] = popV;
					}
				}
			}
		}

		// 최소 가중치로 인한 visited edge count
		System.out.println("edge count : " + visitedCost[visitedCost.length - 1]);
		System.out.println("minWeight : " + minWeight[minWeight.length - 1]);

		// parent 배열.
		//for (int i = 0; i < vertexCnt + 1; i++)
		//	System.out.println(i + " " + parent[i]);

		int v = vertexCnt;
		while (v != k) { // start를 만날때까지
			System.out.print(v + " ");
			v = parent[v];
		}
		System.out.println(v + " ");
	}
}
