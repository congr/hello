import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("inputS001.txt"));
		FileWriter wr = new FileWriter(new File("sample.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			int vCnt = sc.nextInt();
			int qCnt = sc.nextInt();
			Dfs dfs = m.new Dfs(vCnt);
			for (int i = 0; i < vCnt-1; i++) {
				dfs.addEdge(sc.nextInt(), sc.nextInt());
			}
			
			//dfs.printAdjList();
			
			dfs.goDfs(1, 1);
			//dfs.printParent();
			
			for (int i = 0; i < qCnt; i++) {
				int result = dfs.checkAncestor(sc.nextInt(), sc.nextInt());
				System.out.println(result);
				wr.write(result + "\n");
			}
		}

		sc.close();
		wr.close();
	}

	class Dfs {
		private final int vCnt;
		private ArrayList<Integer>[] adjList;// 인접 리스트.
		private boolean visited[];
		private int parent[];
		private int level[];
		
		public Dfs(int vCnt) {
			this.vCnt = vCnt;

			level = new int[vCnt + 1];
			parent = new int[vCnt + 1];
			visited = new boolean[vCnt + 1];
			adjList = new ArrayList[vCnt + 1];

			for (int i = 0; i < vCnt + 1; i++) {
				adjList[i] = new ArrayList<Integer>();
			}
		}

		//int route[] = new int[vCnt + 1];

		void addEdge(int v1, int v2) {
			adjList[v1].add(v2);
			//adjList[v2].add(v1);
		}

		int levelInd;
		void goDfs(int k, int p) {
			visited[k] = true;
			parent[k] = p;
			level[k] = ++levelInd;
			//println(k + " " + p); // overflow
			
			for (int i = 0, len = adjList[k].size(); i < len; i++) {
				int adjNext = adjList[k].get(i);
				if (!visited[adjNext]) {
					goDfs(adjNext, k);
				}
			}
		}
		
		int checkAncestor (int n, int m) {
			print("check "+ n + " " + m);
			int c = 0, p = 0;
			if (level [n] > level[m]) { // n이 자식
				c = n;
				p = m;
			} 
			else { 
				c = m;
				p = n;
			}
			
			int a = c;
			while (a != 1) { // root는 1로 문제에 정의됨.
				a = parent [a];
				
				if (a == p) return 1;// 거슬러 올라가서 부모를 만났다면 1
			}
			
			return 0; // root까지 올라갔으나 부모를 못만난 경우.
		}

		void printAdjList() {
			System.out.println("\nprint adjList");
			for (int i = 1; i < vCnt + 1; i++) {
				System.out.print(i + " -> ");
				for (int j = 0; j < adjList[i].size(); j++)
					System.out.print(adjList[i].get(j).toString() + " ");
				System.out.println("");
			}
		}
		
		void printParent() {
			System.out.println("\nprint parent");
			for (int i = 1; i < vCnt + 1; i++) {
				System.out.print(i + " -> " + parent[i] + " L"+level[i]);
				System.out.println("");
			}
		}
		
		boolean debug = false;
		void println(String x) { // DFS 에 쓰면 stackoverflow주의.
			if (debug)
				System.out.println(x);
		}
		void print(String x) {
			if (debug)
				System.out.print(x + " ");
		}
	}
}
