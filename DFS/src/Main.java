import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
			Dfs dfs = m.new Dfs(vCnt, eCnt);
			for (int i = 0; i < eCnt; i++) {
				dfs.addEdge(sc.nextInt(), sc.nextInt());
			}
			dfs.printAdjList();
			dfs.goDfs(1);
		}

		sc.close();
		wr.close();
	}

	class Dfs {
		private final int vCnt;
		private final int eCnt;
		private ArrayList<Integer>[] adjList;// 인접 리스트.
		private boolean visited[];

		public Dfs(int vCnt, int eCnt) {
			this.vCnt = vCnt;
			this.eCnt = eCnt;

			visited = new boolean[vCnt + 1];
			adjList = new ArrayList[vCnt + 1];

			for (int i = 0; i < vCnt + 1; i++) {
				adjList[i] = new ArrayList<Integer>();
			}
		}

		//int route[] = new int[vCnt + 1];

		void addEdge(int v1, int v2) {
			adjList[v1].add(v2);
			adjList[v2].add(v1);
		}

		void goDfs(int k) {
			visited[k] = true;
			System.out.print(k + " ");

			for (int i = 0, len = adjList[k].size(); i < len; i++) {
				int adjNext = adjList[k].get(i);
				if (!visited[adjNext]) {
					goDfs(adjNext);
				}
			}
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = 1; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjList[i].size(); j++)
					System.out.print(adjList[i].get(j).toString());
				System.out.println("");
			}
		}
	}
}
