import java.io.*;
import java.util.*;

class SequenceGenerator {
    public static void main(String[] args) throws IOException {

        SequenceGenerator obj = new SequenceGenerator();
        File file = new File("sequence" + ".txt");

        // creates the file
        file.createNewFile();

        // creates a FileWriter Object
        FileWriter fwriter = new FileWriter(file, false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        obj.generateSequence(bwriter);
        bwriter.close();

    }

    private void generateSequence(BufferedWriter br) {
        Random rand = new Random();
        int numberOfSequences = rand.nextInt(16);
        System.out.println("Number of Sequences = " + numberOfSequences);
        int A = 25;
        int C = 50;
        int G = 75;
        // int T = 100;
        for (int j = 0; j < numberOfSequences; j++) {
            // generate sequences
            int low = 40;
            int high = 50;
            int sequenceLength = rand.nextInt(high - low) + low;
            StringBuilder sequence = new StringBuilder();
            for (int i = 1; i <= sequenceLength; i++) {
                int selected = rand.nextInt(100);
                if (selected < A)
                    sequence.append("A");
                else if (selected >= A && selected < C)
                    sequence.append("C");
                else if (selected >= C && selected < G)
                    sequence.append("G");
                else
                    sequence.append("T");
            }
            System.out.println(sequence.length());
            writeToFile(sequence, br);
        }
    }

    public static void writeToFile(StringBuilder write, BufferedWriter br) {
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
            // br.write(
            // "--------------------------------------------------------------------------------------------------------------------------------------------------");
            // br.newLine();
            br.flush();
        } catch (Exception e) {
            System.out.println("Unable to write to file");
        }

    }

}
