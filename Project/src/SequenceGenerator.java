import java.io.*;
import java.util.*;

class SequenceGenerator {
    public static void main(String[] args) throws IOException {

        SequenceGenerator obj = new SequenceGenerator();
        File file = new File(args[0] + ".txt");
        int numberOfSequences = Integer.parseInt(args[1]);
        int startSequenceRange = Integer.parseInt(args[2]);
        int endSequenceRange = Integer.parseInt(args[3]);
        float mutationRate = Float.parseFloat(args[4]);
        // creates the file
        file.createNewFile();

        // creates a FileWriter Object
        FileWriter fwriter = new FileWriter(file, false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        obj.generateSequence(bwriter, numberOfSequences, startSequenceRange, endSequenceRange, mutationRate);
        bwriter.close();

    }

    private void generateSequence(BufferedWriter br, int numberOfSequences, int low, int high, float probability) {
        Random rand = new Random();
        // System.out.println("Number of Sequences = " + numberOfSequences);
        int A = 25;
        int C = 50;
        int G = 75;
        // int T = 100;

        int sequenceLength = rand.nextInt(high - low) + low;
        StringBuilder sequence = new StringBuilder();
        // Generate first sequence
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
        // System.out.println(sequence.length());
        writeToFile(sequence, br);
        // generate remaining sequences;
        for (int i = 0; i < numberOfSequences - 1; i++) {
            int count_mutate = 0;
            // generate sequences
            sequenceLength = rand.nextInt(high - low) + low;

            // If the new sequence length is greater than the previous sequence, mutate the
            // first half and then update the remaining by following normal logic
            if (sequenceLength > sequence.length()) {
                // System.out.println("new length greater "+sequenceLength);
                int remaining = sequenceLength - sequence.length();
                for (int j = 0; j < sequence.length(); j++) {
                    // StringBuilder sequence = new StringBuilder();
                    float mutate = rand.nextFloat();
                    if (mutate < probability) {
                        count_mutate++;
                        int selected = rand.nextInt(100);
                        if (selected < A)
                            sequence.setCharAt(j, 'A');
                        else if (selected >= A && selected < C)
                            sequence.setCharAt(j, 'C');
                        else if (selected >= C && selected < G)
                            sequence.setCharAt(j, 'G');
                        else
                            sequence.setCharAt(j, 'T');
                    }
                }

                for (int k = 1; k <= remaining; k++) {
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
            }
            // else part where new sequence length <= previous sequence length
            else {
                for (int j = 0; j < sequenceLength; j++) {
                    // StringBuilder sequence = new StringBuilder();
                    float mutate = rand.nextFloat();
                    if (mutate < probability) {
                        count_mutate++;
                        int selected = rand.nextInt(100);
                        if (selected < A)
                            sequence.setCharAt(j, 'A');
                        else if (selected >= A && selected < C)
                            sequence.setCharAt(j, 'C');
                        else if (selected >= C && selected < G)
                            sequence.setCharAt(j, 'G');
                        else
                            sequence.setCharAt(j, 'T');
                    }
                }
            }

            // System.out.println("Sequence Length " + sequenceLength);
            System.out.println("Mutated " + count_mutate);
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