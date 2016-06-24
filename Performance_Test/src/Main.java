import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {
	public static int N = 1000000;

	public static void main(String[] args) throws IOException {
		long printlnT = println();
		long printerWriterT = printerWriter();
		long fileWriterT = fileWriter();
		long stringBuilderT = stringBuilder();
		long bufferedOutputStreamT = bufferedOutputStream();
		long BufferedWriterT = bufferedWriter();

		System.out.println("=== count " + N);
		System.out.println("System.out.println   : " + printlnT);
		System.out.println("printerWriter        : " + printerWriterT);
		System.out.println("FileWriter           : " + fileWriterT);
		System.out.println("StringBuilder        : " + stringBuilderT);
		System.out.println("BufferedoutputStream : " + bufferedOutputStreamT);
		System.out.println("BufferedWriter       : " + BufferedWriterT);
		System.out.println();
	}

	static long println() {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < N; i++) {
			System.out.println(i);
		}

		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

	static long printerWriter() {
		long startTime = System.currentTimeMillis();
		PrintWriter out = new PrintWriter(System.out);
		for (int i = 0; i < N; i++) {
			out.println(i);
		}
		out.flush();
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

	static long fileWriter() throws IOException {
		long startTime = System.currentTimeMillis();
		FileWriter out = new FileWriter(new File("test.out"));
		for (int i = 0; i < N; i++) {
			out.write(i + "\n");
		}
		out.close();
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

	static long stringBuilder() {
		long startTime = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append(i + "\n");
		}
		System.out.print(sb.toString());
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

	static long bufferedOutputStream() throws IOException {
		long startTime = System.currentTimeMillis();
		OutputStream out = new BufferedOutputStream(System.out);
		for (int i = 0; i < N; i++) {
			out.write((i + "\n").getBytes());
		}
		out.flush();
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

	static long bufferedWriter() throws IOException {
		long startTime = System.currentTimeMillis();
		BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
		for (int i = 0; i < N; i++) {
			log.write(i + "\n");
		}
		log.flush();
		long stopTime = System.currentTimeMillis();
		return stopTime - startTime;
	}

}
