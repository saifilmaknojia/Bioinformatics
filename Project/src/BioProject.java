import java.io.*;
import java.util.*;

class BioProject {
	// static FileOutputStream output;

	static List<StringBuilder> outputArray = new ArrayList<>();

	// static int threshold = 2;
	public static void main(String[] args) throws IOException {

		try {
			List<StringBuilder> sequenceArray = new ArrayList<>();
			int maxLength = 0;
			int minLength = Integer.MAX_VALUE;
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ip;
			StringBuilder sb = null;
			while ((ip = br.readLine()) != null) {
				if (ip.charAt(0) == '>') {
					if (sb != null) {
						sequenceArray.add(sb);
						maxLength = sb.length() > maxLength ? sb.length() : maxLength;
						minLength = sb.length() < minLength ? sb.length() : minLength;
					}
					sb = new StringBuilder();
				} else {
					sb.append(ip);
				}
			}

			if (sb != null) {
				maxLength = sb.length() > maxLength ? sb.length() : maxLength;
				sequenceArray.add(sb);
			}

			br.close();

			insertEndGaps(sequenceArray, maxLength);
			if (args[2].equalsIgnoreCase("Yes")) {
				Partitioning.partition(sequenceArray, maxLength, minLength, Integer.parseInt(args[3]));
			} else {
				Pomsa.executePomsa(sequenceArray, maxLength, Integer.parseInt(args[3]));
			}

			file = new File(args[1] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			BufferedWriter bwriter = new BufferedWriter(fwriter);

			for (int i = 0; i < outputArray.size(); i++) {
				writeToFile(outputArray.get(i), bwriter);
			}
			bwriter.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}

	}

	static void insertEndGaps(List<StringBuilder> sequenceArray, int maxLength) {
		// TODO Auto-generated method stub
		for (StringBuilder st : sequenceArray) {
			int n = st.length();
			while (n < maxLength) {
				st.append('-');
				n++;
			}
		}
	}

	static void writeToFile(StringBuilder write, BufferedWriter br) {
		try {
			br.write('>');
			br.newLine();
			int i = 0;
			while (i < write.length()) {
				if (write.length() < i + 80) {
					br.append(write.substring(i, write.length()));
					br.newLine();
					break;
				}
				br.append(write.substring(i, i + 80));
				i = i + 80;
				br.newLine();
			}
			br.flush();
		} catch (Exception e) {
			System.out.println("unable to write to file");
		}

	}

}