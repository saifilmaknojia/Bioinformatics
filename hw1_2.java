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
			int i;
			StringBuilder sb = new StringBuilder();
			int x = Integer.parseInt(args[1]), y = Integer.parseInt(args[2]);
			// creates a FileReader Object
			File file = new File(args[0] + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			file = new File(args[3] + ".txt");

			// creates the file
			file.createNewFile();

			// creates a FileWriter Object
			FileWriter fwriter = new FileWriter(file, false);
			
			BufferedWriter bwriter = new BufferedWriter(fwriter);
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
					sequencePartitioning(sb, x, y, bwriter);	
				}
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
		HW1_1 obj = new HW1_1();
		while(inSequence.length() - i >= x) {
			len = rand.nextInt((y - x) + 1) + x;
			if(i+len >= inSequence.length()) {
				continue;
			}
			obj.writeToFile(new StringBuilder(inSequence.substring(i, i+len)), bwriter);
			i = i+len;
		}
	}

}
