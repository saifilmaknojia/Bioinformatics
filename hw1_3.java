import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class hw1_3 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			int i;
			StringBuilder sb = new StringBuilder();
			int s = Integer.parseInt(args[1]), r = Integer.parseInt(args[2]), d = Integer.parseInt(args[3]);
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			file = new File(args[4] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			
			BufferedWriter bwriter = new BufferedWriter(fwriter);
			
			List<StringBuilder> sbList = new ArrayList<StringBuilder>();
			
			while ((i=br.read()) != -1) {
				if(i == '>') {
					do {
						i = br.read();
					}
					while(i != '\r' && i != '\n'); 
					if((i = br.read()) == '\n') {
						i = br.read();
					}
					sb = new StringBuilder();
					while(i != '\r' && i != '\n') {
						sb.append((char)i);
						i = br.read();
					}
					sbList.add(sb);
				}
			}
//			write sequence assembler code here
			bwriter.close();
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to locate file " + args[0]);
			e.printStackTrace();
		}
		
//		System.out.println(dovetailAlignment(new StringBuilder("ltcabddb"), new StringBuilder("cacdbdvl"), 1, -1, -1));
	}
	
	private static StringBuilder dovetailAlignment(StringBuilder seq1, StringBuilder seq2, int s, int r, int d) {
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
			return getFinalAlignment(x,n-1,matrix,s,d,r,seq1,seq2);
		} else {
			return getFinalAlignment(m-1,y,matrix,s,d,r,seq1,seq2);
		}
		
		
	}
	
	private static StringBuilder getFinalAlignment(int m, int n, int matrix[][], int s, int d, int r, StringBuilder seq1, StringBuilder seq2) {
		int i = m, j = n;
		StringBuilder st = new StringBuilder();
		while(i==0 || j==0) {
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
			output.append(seq2.substring(0, j-1));
			output.append(st);
			output.append(seq1.substring(m-1, matrix.length-2));
		} else {
			output.append(seq1.substring(0, i-1));
			output.append(st);
			output.append(seq2.substring(n-1, matrix[0].length-2));
		}
		
		return output;
	}


}
