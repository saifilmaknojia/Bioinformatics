import java.io.*;
import java.util.*;

class AlignmentScore {
	
	public static void main(String[] args) throws IOException {

		try{
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
				}
				else {
					sb.append(ip);
				}
			}

			if (sb != null) {
				sequenceArray.add(sb);
			}

			br.close();
			System.out.println(calculateAlignmentScore(sequenceArray));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}
	}
	
	 static int calculateAlignmentScore(List<StringBuilder> sequenceArray) {
		 int totalScore = 0;
		 for(int i=0;i<sequenceArray.size();i++) {
			 for(int j=0;j<sequenceArray.size()-1;j++) {
				 totalScore+=alignSequences(sequenceArray.get(i), sequenceArray.get(j));
			 }
		 }
		 return totalScore;
	 }
	 
	 private static int alignSequences(StringBuilder sb1, StringBuilder sb2) {
		 int score = 0;
		 for(int i=0;i<sb1.length();i++) {
			 if(sb1.charAt(i) == '.' && sb2.charAt(i) == '.') {
				 continue;
			 }
			 else if(sb1.charAt(i) == '.' || sb2.charAt(i) == '.') {
				 score-=3;
			 }
			 else if(sb1.charAt(i)==sb2.charAt(i)) {
				 score++;
			 }
			 else {
				 score-=1;
			 }
		 }
		 
		 return score;
	 }
	 
	 
}