import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		FileWriter wr = new FileWriter(new File("problem.out"));
		Main m = new Main();

		int tc = sc.nextInt();
		while (tc-- > 0) {
			// input tc #
			int N = sc.nextInt(); // vertex count
			// int K = sc.nextInt(); // query count

			Solution sol = m.new Solution();
			for (int i = 0; i < N; i++) {
				int p1 = sc.nextInt();
				// int p2 = sc.nextInt();
				sol.addNode(p1);
			}
			
			sol.travalse();

			// File write
			wr.write(" ");
		}

		sc.close();
		wr.close();
	}

	class Solution {
		class Node {
			int data;
			Node left, right;

			public Node(int data) {
				this.data = data;
			}
		}

		class BinTree {
			Node root;

			void insert(int data, Node node) {
				if (root == null) {
					root = new Node(data);
					return;
				}

				Node next = node;
				if (node.data > data) { // left go
					next = node.left;
				} else { // right
					next = node.right;
				}
				
				if (next == null) {
					next = new Node(data);
				}else 
				insert(data, next);
			}

			void inorder(Node next) {
				System.out.print(next.data + " ");

				inorder(next.left);
				inorder(next.right);
			}
		}

		BinTree tree;

		public Solution() {
			tree = new BinTree();
		}

		public void addNode(int data) {
			tree.insert(data, tree.root);
		};

		public void travalse() {
			System.out.println("Inorder");
			tree.inorder(tree.root);
		}
	}
}
