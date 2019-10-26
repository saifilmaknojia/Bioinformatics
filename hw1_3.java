import java.io.*;
import java.util.*;

public class hw1_3 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			StringBuilder sb = null;
			int s = Integer.parseInt(args[1]), r = Integer.parseInt(args[2]), d = Integer.parseInt(args[3]);
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file)); 

			file = new File(args[4] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			String ip;
			BufferedWriter bwriter = new BufferedWriter(fwriter);

			HashSet<Integer> track = new HashSet<Integer>();

			Map<Integer,StringBuilder> list =  new HashMap<>();
			int ct = 0;
			while ((ip = br.readLine()) != null) {
				if(ip.charAt(0) == '>') {
					if(sb!=null) {
						list.put(ct++, sb);
					}
					sb = new StringBuilder();
					continue;
				}
				sb.append(ip);
			}
			if(sb!=null) {
				list.put(ct++, sb);
			}
			//			write sequence assembler code here

			PriorityQueue<SequenceDetails> pq = new PriorityQueue<>();

			for(int x : list.keySet())
			{
				for(int y : list.keySet())
				{
					if(x>y)
					{
						SequenceDetails seq = dovetailAlignment(list.get(x), list.get(y), s, r, d);
						seq.setParent1(x);
						seq.setParent2(y);
						pq.add(seq);
					}
				}
			}

			while(list.size() >= 1 && !pq.isEmpty() && pq.peek().score >= 0) {
				SequenceDetails seq = pq.poll();
				if(track.contains(seq.parent1) || track.contains(seq.parent2)) {
					continue;
				} 
				track.add(seq.parent1);
				track.add(seq.parent2);
				list.remove(seq.parent1);
				list.remove(seq.parent2);
				for(int x : list.keySet()) {
					SequenceDetails seq2 = dovetailAlignment(seq.sequence, list.get(x), s, r, d);
					seq2.setParent1(ct);
					seq2.setParent2(x);
					pq.add(seq2);
				}
				list.put(ct++, seq.sequence);
			}
			hw1_1 obj = new hw1_1();
			if(list.size() == 1) {
				for(int y : list.keySet()) {
					obj.writeToFile(list.get(y), bwriter);
				}
			}
			else {
				int length = Integer.MIN_VALUE;
				StringBuilder output = new StringBuilder();
				while(!pq.isEmpty()) {
					SequenceDetails seq = pq.poll();
					if(seq.sequence.length()>length) {
						length = seq.sequence.length();
						output = seq.sequence;
					}
				}
				obj.writeToFile(output, bwriter);
			}

			bwriter.close();
			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}
	}

	private static SequenceDetails dovetailAlignment(StringBuilder seq1, StringBuilder seq2, int s, int r, int d) {
		int m = seq1.length()+1, n = seq2.length()+1;
		int matrix[][] = new int[m][n];
		for(int i = 0; i<m;i++) {
			matrix[i][0] = 0;
		}
		for(int i = 0; i<n;i++) {
			matrix[0][i] = 0;
		}
		int max, val;
		for(int i=1;i<m;i++) {
			for(int j = 1; j<n;j++) {
				max = matrix[i-1][j]<matrix[i][j-1] ? matrix[i][j-1]+d : matrix[i-1][j]+d;
				val = seq1.charAt(i-1) == seq2.charAt(j-1) ? matrix[i-1][j-1]+s : matrix[i-1][j-1]+r;
				matrix[i][j] = max>val ? max : val;
			}
		}
		int x=1,y=1;
		
		max = matrix[1][n-1];
		for(int i = 2; i<m ; i++) {
			if(max<matrix[i][n-1]) {
				x = i;
				max = matrix[i][n-1];
			}
		}
		val = matrix[m-1][1];
		for(int i = 2; i<n ; i++) {
			if(val<matrix[m-1][i]) {
				y = i;
				val = matrix[m-1][i];
			}
		}
		if(max>val) {
			return new SequenceDetails(getFinalAlignment(x,n-1,matrix,s,d,r,seq1,seq2), max, 0, 0);
		} else {
			return new SequenceDetails(getFinalAlignment(m-1,y,matrix,s,d,r,seq1,seq2), val, 0, 0);
		}


	}

	private static StringBuilder getFinalAlignment(int m, int n, int matrix[][], int s, int d, int r, StringBuilder seq1, StringBuilder seq2) {
		int i = m, j = n;
		StringBuilder st = new StringBuilder();
		while(i!=0 && j!=0) {
			if(matrix[i-1][j-1]+s == matrix[i][j] && seq1.charAt(i-1) == seq2.charAt(j-1)) {
				st.append(seq1.charAt(i-1));
				i--;
				j--;
				continue;
			} else if(matrix[i-1][j]+d == matrix[i][j]) {
				st.append(seq1.charAt(i-1));
				i--;
				continue;
			} else if(matrix[i][j-1]+d == matrix[i][j]) {
				j--;
				continue;
			}  else {
				st.append(seq1.charAt(i-1));
				i--;
				j--;
				continue;
			}
		}
		st = st.reverse();
		StringBuilder output = new StringBuilder();
		if(i==0) {
			output.append(seq2.substring(0, j));
			output.append(st);
			output.append(seq1.substring(m-1, matrix.length-1));
			
		} else {
			output.append(seq1.substring(0, i));
			output.append(st);
			output.append(seq2.substring(n-1, matrix[0].length-1));
		}
		return output;
	}


}

class SequenceDetails implements Comparable<SequenceDetails>{
	StringBuilder sequence;
	int score, parent1, parent2;

	SequenceDetails(StringBuilder sequence, int score, int parent1, int parent2){
		this.sequence = sequence;
		this.score = score;
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public void setParent1(int par) {
		this.parent1 = par;
	}

	public void setParent2(int par) {
		this.parent2 = par;
	}

	@Override
	public int compareTo(SequenceDetails seq) {
		return seq.score-this.score;
	}
	
	 @Override
	    public String toString() { 
	        return String.format(this.sequence + " " + this.score+ " "+ this.parent1 + " "+ this.parent2); 
	    } 


}
