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
			
			sol.traversal();

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
			
			void insert(int data) {
				//System.out.println("insert " + data );
				if (this.data > data) {
					if (left == null)
						left = new Node(data);
					else 
						left.insert(data);
				} else if (this.data < data) {
					if (right == null)
						right = new Node(data);
					else 
						right.insert(data);
				} else {
					// tree doesn't allow duplication
				}
			}
		}

		class BinTree {
			Node root;

			void insert(int data) {
				if (root == null) {
					root = new Node(data);
					return;
				}
				root.insert(data);
			}
			
			Node search(int target, Node node) {
				if (node == null) return null;
				
				if (node.data > target)
					search(target, node.left);
				else if (node.data < target)
					search(target, node.right);
				
				return node;
			}

			void preorder(Node next) {
				if (next == null)
					return;
				
				System.out.print(next.data + " ");
				preorder(next.left);
				preorder(next.right);
			}
			
			void inorder(Node next) {
				if (next == null)
					return;
				
				inorder(next.left);
				System.out.print(next.data + " ");
				inorder(next.right);
			}

			void postorder(Node next) {
				if (next == null)
					return;
				
				postorder(next.left);
				postorder(next.right);
				System.out.print(next.data + " ");
			}

		}

		BinTree tree;

		public Solution() {
			tree = new BinTree();
		}

		public void addNode(int data) {
			tree.insert(data);
		};

		public void traversal() {
			System.out.println("preorder");
			tree.preorder(tree.root);
			
			System.out.println("\ninorder");
			tree.inorder(tree.root);
			
			System.out.println("\npostorder");
			tree.postorder(tree.root);
			
		}
	}
}
