import java.io.*;
import java.util.*;

class BioProject {
	// static FileOutputStream output;

	static List<StringBuilder> outputArray = new ArrayList<>();
	static int threshold = 22;
	public static void main(String[] args) throws IOException {

		try{
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
						maxLength = sb.length()>maxLength?sb.length():maxLength;
						minLength = sb.length()<minLength?sb.length():minLength;
					}
					sb = new StringBuilder();
				}
				else {
					sb.append(ip);
				}
			}

			if (sb != null) {
				maxLength = sb.length()>maxLength?sb.length():maxLength;
				sequenceArray.add(sb);
			}

			br.close();

			insertEndGaps(sequenceArray, maxLength);
			if(Integer.parseInt(args[2]) == 1) {
				Partitioning.partition(sequenceArray, maxLength, minLength);
			}
			else {
				Pomsa.executePomsa(sequenceArray, maxLength);
			}
			

			file = new File(args[1] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			
			for(int i=0;i<outputArray.size();i++) {
				writeToFile(outputArray.get(i), bwriter);
			}
			bwriter.close();
//			outputArray.add(new StringBuilder("AATGGAAATAT"));
//			outputArray.add(new StringBuilder("AGCGGAGCCAT"));
//			outputArray.add(new StringBuilder("AATAAGGAATG"));
//			outputArray.add(new StringBuilder("AACGGGGAGCT"));
			System.out.println(calculateAlignmentScore());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}

	}

	static void insertEndGaps(List<StringBuilder> sequenceArray, int maxLength) {
		// TODO Auto-generated method stub
		for(StringBuilder st:sequenceArray) {
			int n = st.length();
			while(n<maxLength) {
				st.append('.');
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
	 
	 static int calculateAlignmentScore() {
		 int totalScore = 0;
		 for(int i=0;i<outputArray.size();i++) {
			 for(int j=0;j<outputArray.size()-1;j++) {
				 totalScore+=alignSequences(outputArray.get(i), outputArray.get(j));
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