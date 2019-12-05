import java.io.*;
import java.util.*;

class AlignmentScore {

	public static void main(String[] args) throws IOException {

		try {
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			List<StringBuilder> sequenceArray = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String ip;
			StringBuilder sb = null;
			while ((ip = br.readLine()) != null) {
				if (ip.charAt(0) == '>') {
					if (sb != null) {
						sequenceArray.add(sb);
					}
					sb = new StringBuilder();
				} else {
					sb.append(ip);
				}
			}

			if (sb != null) {
				sequenceArray.add(sb);
			}

			br.close();
			System.out.println("Alignment Score = " + calculateAlignmentScore(sequenceArray));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}
	}

	static int calculateAlignmentScore(List<StringBuilder> sequenceArray) {
		int totalScore = 0;
		for (int i = 0; i < sequenceArray.size() - 1; i++) {
			for (int j = i + 1; j < sequenceArray.size(); j++) {
				// System.out.println(i+" "+j+" "+sequenceArray.get(i).length()+"
				// "+sequenceArray.get(j).length());
				totalScore += alignSequences(sequenceArray.get(i), sequenceArray.get(j));
			}
		}
		return totalScore;
	}

	private static int alignSequences(StringBuilder sb1, StringBuilder sb2) {
		int score = 0;
		int min = sb1.length() > sb2.length() ? sb2.length() : sb1.length();
		for (int i = 0; i < min; i++) {
			if (sb1.charAt(i) == '-' && sb2.charAt(i) == '-') {
				continue;
			} else if (sb1.charAt(i) == '-' || sb2.charAt(i) == '-') {
				score -= 3;
			} else if (sb1.charAt(i) == sb2.charAt(i)) {
				score++;
			} else {
				score--;
			}
		}

		return score;
	}

}