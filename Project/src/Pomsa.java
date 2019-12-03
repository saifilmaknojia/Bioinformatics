import java.util.*;

class Pomsa {
	// static FileOutputStream output;
	
	static void executePomsa(List<StringBuilder> sequenceArray, int maxLength, int threshold) {
		// TODO Auto-generated method stub
		char dominant;
		int newMaxLength = 0;
		for(int i=0;i<maxLength;i++) {
			for(int j=0;j<sequenceArray.size();j++) {
				StringBuilder sb = sequenceArray.get(j);
				if(i==maxLength-1 && sb.length()>newMaxLength ) {
					newMaxLength = sb.length();
				}
				dominant = getDominant(sequenceArray, i, threshold);
				if(dominant != '.' && sb.charAt(i) != dominant) {
					if(i>0 && sb.charAt(i-1) == dominant) {
						sb.insert(i-1, '.');
					}
					if(i<maxLength && sb.charAt(i+1) == dominant) {
						for(int k=0;k<sequenceArray.size();k++) {
							if(k!=j) {
								sequenceArray.get(k).insert(i, '.');
							}
						}
					}
				}
			}
		}
		BioProject.insertEndGaps(sequenceArray, newMaxLength);
		for(int i=0;i<sequenceArray.size();i++)
		{
			if(BioProject.outputArray.size()<sequenceArray.size()) {
				BioProject.outputArray.add(sequenceArray.get(i));
			}
			else {
				BioProject.outputArray.get(i).append(sequenceArray.get(i));
			}
		}

	}

	private static char getDominant(List<StringBuilder> sequenceArray, int col, int threshold) {
		// TODO Auto-generated method stub
		Map<Character, Integer> count = new HashMap<Character, Integer>() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
			put('A', 0);
			put('C', 0);
			put('G', 0);
			put('T', 0);
		}};
		char curr;
		for(int i=0;i<sequenceArray.size();i++) {
			curr = sequenceArray.get(i).charAt(col);
			count.put(curr, count.getOrDefault(curr, 0)+1);
		}
		int max = threshold;
		char retChar = '.';

		for(char ch: count.keySet()) {
			if(max < count.get(ch)) {
				retChar = ch;
				max = count.get(ch);
			}
		}
		return retChar;
	}
}