import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt();
			int eCnt = sc.nextInt();
			Dfs dfs = m.new Dfs(vCnt, eCnt);
			for (int i = 0; i < eCnt; i++) {
				dfs.addEdge(sc.nextInt(), sc.nextInt());
			}
			dfs.printAdjList();
			dfs.getEulerCircuit(1);
		}

		sc.close();
		wr.close();
	}

	class Dfs {
		private final int vCnt;
		private final int eCnt;
		int [][] adjMatrix; // 인접 행렬
		boolean visited[];
		Stack<Integer> path;

		public Dfs(int vCnt, int eCnt) {
			this.vCnt = vCnt;
			this.eCnt = eCnt;

			visited = new boolean[vCnt + 1];
			adjMatrix = new int[vCnt + 1][vCnt+1];
			path = new Stack<Integer>();
		}

		void addEdge(int v1, int v2) {
			adjMatrix[v1][v2]++;
			adjMatrix[v2][v1]++; // 간선의 수 저장
		}
		
//		 int nonIsolatedVertex(Graph G) {
//		        for (int v = 0; v < adjMatrix.length; v++)
//		            if (G.degree(v) > 0)
//		                return v;
//		        return -1;
//		    }
		 
		void getEulerianPath() {
			int oddDegreeVertices = 0;
	        int s=0;
	        // 홀수개 간선이 있는 점에서 시작
	        for (int v = 0; v < adjMatrix.length; v++) {
	            if (adjMatrix[v].length % 2 != 0) {
	                oddDegreeVertices++;
	                s = v;
	            }
	        }

	        // graph can't have an Eulerian path
	        // (this condition is needed for correctness)
	        if (oddDegreeVertices > 2) return;

	        
	        
	        
		}
		
		/* The function returns one of the following values
	       0 --> If grpah is not Eulerian
	       1 --> If graph has an Euler path (Semi-Eulerian)
	       2 --> If graph has an Euler Circuit (Eulerian)  */
	    int isEulerian()
	    {
	        // Check if all non-zero degree vertices are connected
//	        if (isConnected() == false)
//	            return 0;
	 
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
	        return (odd==2)? 1 : 2;
	    }

		void getEulerCircuit(int k) {
			visited[k] = true;
			System.out.print(k + " ");

			for (int i = 0, len = adjMatrix[k].length; i < len; i++) {
				while (adjMatrix[k][i] > 0) {
					adjMatrix[k][i]--;//간선을 지운다
					adjMatrix[i][k]--;//무향 그래프의 경우 양쪽을 다 지움.
					getEulerCircuit(i);
				}
			}
			
			path.push(k);
		}

		void getEulerTrailOrCircuit() {
			for (int i=0, len = adjMatrix.length; i<len; i++ ){
				if ()// 방향그래프경우 : 나가는 간선 > 들어오는 간선 인 정점이 시작점
			}
		}
		
		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = 1; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjMatrix[i].length; j++)
					System.out.print(adjMatrix[i][j]);
				System.out.println("");
			}
		}
	}
}
