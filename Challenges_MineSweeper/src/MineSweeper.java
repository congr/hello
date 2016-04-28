import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class MineSweeper {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("sample.in"));
		PrintWriter wr = new PrintWriter(new File("sample.out"));

		int yCnt = sc.nextInt();
		int xCnt = sc.nextInt();
		int k = 1;
		sc.nextLine();

		int[][] result = null;

		while (yCnt != 0 && xCnt != 0) {
			result = new int[yCnt][xCnt];
			for (int y = 0; y < yCnt; y++) {
				String row = sc.nextLine();
				char[] input = row.toCharArray();

				for (int x = 0; x < xCnt; x++) {
					if (input[x] == '*') { // 현재 칸이 지뢰라면,
						result[y][x] = -1;
						updatePrev(result, y, x, xCnt);
					} else { // 현재 칸이 지뢰가 아니라면,
						result[y][x] = checkPrev(result, y, x, xCnt);
					}
				}
			}

			// print tc result
			System.out.println("Field #" + k + ":");
			for (int y = 0; y < yCnt; y++) {
				for (int x = 0; x < xCnt; x++) {
					if (result[y][x] == -1)
						System.out.print('*');
					else
						System.out.print(result[y][x]);
				}
				System.out.println();

			}
			System.out.println();
			k++;

			// next tc
			yCnt = sc.nextInt();
			xCnt = sc.nextInt();
			if (yCnt == 0 && xCnt == 0)
				break;
			sc.nextLine();
		}
		
		sc.close();
		wr.close();
	}

	public static void updatePrev(int[][] result, int y, int x, int xCnt) {
		if (y - 1 >= 0) {
			if (x - 1 >= 0 && result[y - 1][x - 1] >= 0)
				result[y-1][x-1]++;

			if (result[y - 1][x] >= 0)
				result[y-1][x]++;

			if (x + 1 < xCnt && result[y - 1][x + 1] >= 0)
				result[y-1][x+1]++;
		}

		if (x - 1 >= 0 && result[y][x - 1] >= 0)
			result[y][x-1]++;
	}

	public static int checkPrev(int[][] result, int y, int x, int xCnt) {
		int mineCnt = 0;

		if (y - 1 >= 0) {
			if (x - 1 >= 0 && result[y - 1][x - 1] == -1)
				mineCnt++;

			if (result[y - 1][x] == -1)
				mineCnt++;

			if (x + 1 < xCnt && result[y - 1][x + 1] == -1)
				mineCnt++;
		}

		if (x - 1 >= 0 && result[y][x - 1] == -1)
			mineCnt++;

		return mineCnt;
	}

}
