import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
// http://www.graph-magics.com/articles/euler.php
// http://virtual1409.blogspot.kr/2014/09/blog-post_22.html

public class Main {

	static final int START = 1;
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt();
			int eCnt = sc.nextInt();
			Euler euler = m.new Euler(vCnt, eCnt);
			for (int i = 0; i < eCnt; i++) {
				euler.addEdge(sc.nextInt(), sc.nextInt());
			}
			//dfs.printAdjList();
			euler.getEulerTrailOrCircuit();
		}

		sc.close();
		wr.close();
	}

	class Euler {
		private final int vCnt;
		private final int eCnt;
		int[][] adjMatrix; // 인접 행렬
		boolean visited[];

		public Euler(int vCnt, int eCnt) {
			this.vCnt = vCnt;
			this.eCnt = eCnt;

			//visited = new boolean[vCnt + 1];
			adjMatrix = new int[vCnt + 1][vCnt + 1];
		}

		void addEdge(int v1, int v2) {
			adjMatrix[v1][v2]++;
			adjMatrix[v2][v1]++; // 간선의 수 저장
		}

		// 0 --> If grpah is not Eulerian
		// 1 --> If graph has an Euler path (Semi-Eulerian)
		// 2 --> If graph has an Euler Circuit (Eulerian)
		int whichEuler() {
			// Check if all non-zero degree vertices are connected
			// if (isConnected() == false)
			// return 0;

			// Count vertices with odd degree
			int odd = 0;
			for (int v = 0; v < adjMatrix.length; v++) {
				if (adjMatrix[v].length % 2 != 0)
					odd++;
			}

			// If count is more than 2, then graph is not Eulerian
			if (odd > 2)
				return 0;

			// If odd count is 2, then semi-eulerian - eulerian path.
			// If odd count is 0, then eulerian cycle
			// Note that odd count can never be 1 for undirected graph
			return (odd == 2) ? 1 : 2;
		}

		void getEulerCircuit(int k, Stack<Integer> path) {
			for (int i = START, len = adjMatrix[k].length; i < len; i++) { // 정점은 1부터 시작!
				while (adjMatrix[k][i] > 0) {
					adjMatrix[k][i]--;// 간선을 지운다
					adjMatrix[i][k]--;// 무향 그래프의 경우 양쪽을 다 지움.
					getEulerCircuit(i, path);
				}
			}

			path.push(k);
		}

		// circuit 은 u에서 시작하면 u로 돌아오므로, 시작점은 아무거나에서 해도 됨.
		// trail의 경우 방향/무향 그래프 각각 따져야
		// 1. 방향그래프경우 :
		// 나가는 간선수-들어오는 간선수= 1 인 정점이 시작점
		// 나가는 간선수-들어오는 간선수= -1 인 정점은 도착
		// 2. 무향그래프경우 :
		// 홀수개 간선을 갖는 점이 반드시 두개여야 하고, 이 두가지 중 하나가 시작점이 됨.
		void getEulerTrailOrCircuit() {
			Stack<Integer> path = new Stack<Integer>();

			int euler = whichEuler();
			if (euler == 0) // not a Euler Path or Circuit
				return;

			if (euler == 1) { // Trail
				int start = 0;

				// 홀수개 간선이 있는 점에서 시작
				for (int v = START; v < adjMatrix.length; v++) {
					if (adjMatrix[v].length % 2 != 0) { // 홀수개 간선이 있는 점에서 시작.
						start = v;
					}
				}

				getEulerCircuit(start, path);

			} else { // Circuit
				getEulerCircuit(1, path);
			}

			// print path stack
			System.out.println(path); // 거꾸로된 상태.
			
			// 스택에서 꺼내면서 프린
//			while(!path.empty()) {
//				System.out.print(path.pop());
//			}
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = START; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = START; j < adjMatrix[i].length; j++)
					System.out.print(adjMatrix[i][j]);
				System.out.println("");
			}
		}
	}
}
