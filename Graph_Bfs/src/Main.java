import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(new File("problem4.in"));
		FileWriter wr = new FileWriter(new File("result.out"));
		int tc = sc.nextInt();

		for (int i = 0; i < tc; i++) {
			int n = sc.nextInt();
			int k = sc.nextInt();

			Graph g = new Graph(n, 0);// adjacent list

			for (int j = 0; j < n - 1; j++) {
				int v1 = sc.nextInt();
				int v2 = sc.nextInt();

				g.addEdge(v1, v2, 0);
			}

			// g.bfs(1);
			// g.printBfs();

			for (int j = 0; j < k; j++) {
				int v1 = sc.nextInt();
				int v2 = sc.nextInt();

//				g.bfs(v1, v2);
				g.bidirectional(v1, v2);
				//g.printBfs();
				//int result = g.findShortestPath(v1, v2);
//				wr.write(result + "\n");
			}
		}

		System.out.println("done");
		sc.close();
		wr.close();
	}
}
