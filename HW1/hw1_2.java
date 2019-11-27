package HW1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class hw1_2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			StringBuilder sb = null;
			int x = Integer.parseInt(args[1]), y = Integer.parseInt(args[2]);
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			file = new File(args[3] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			String ip;
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			while ((ip = br.readLine()) != null) {
				if (ip.charAt(0) == '>') {
					if (sb != null) {
						sequencePartitioning(sb, x, y, bwriter);
					}
					sb = new StringBuilder();
					continue;
				}
				sb.append(ip);
			}
			if (sb != null) {
				sequencePartitioning(sb, x, y, bwriter);
			}
			bwriter.close();
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}
	}

	private static void sequencePartitioning(StringBuilder inSequence, int x, int y, BufferedWriter bwriter) {
		int i = 0, len;
		Random rand = new Random();
		hw1_1 obj = new hw1_1();
		while (inSequence.length() - i >= x) {
			len = rand.nextInt((y - x) + 1) + x;
			if (i + len > inSequence.length()) {
				continue;
			}
			obj.writeToFile(new StringBuilder(inSequence.substring(i, i + len)), bwriter);
			i = i + len;
		}
	}

}
