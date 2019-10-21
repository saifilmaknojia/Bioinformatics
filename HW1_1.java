import java.io.*;
import java.util.*;
import java.util.regex.*;

class HW1_1 {
    // static FileOutputStream output;

    public static void main(String[] args) throws IOException {
        HW1_1 obj = new HW1_1();
        File file = new File(args[7] + ".txt");

        // creates the file
        file.createNewFile();

        // creates a FileWriter Object
        FileWriter fwriter = new FileWriter(file, false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        obj.generateSequence(args, bwriter);
        bwriter.close();
    }

    private void generateSequence(String[] args, BufferedWriter writer) {
        int sequenceLength = Integer.parseInt(args[0]);
        int A = Integer.parseInt(args[1]);
        int C = Integer.parseInt(args[2]);
        int G = Integer.parseInt(args[3]);
        int T = Integer.parseInt(args[4]);
        int numberOfSequences = Integer.parseInt(args[5]);
        float probability = Float.parseFloat(args[6]);

        StringBuilder sequence = new StringBuilder();
        int sum = A + C + G + T;
        // int C_start = A;
        int C_end = A + C;
        // int G_start = C_end;
        int G_End = C_end + G;
        // int T_Start = G_End;
        // int T_End = T_Start + T;
        // System.out.println(C_end);
        // System.out.println(G_End);
        Random rand = new Random();

        // generate first sequence
        for (int i = 1; i <= sequenceLength; i++) {
            int selected = rand.nextInt(sum);
            if (selected < A)
                sequence.append("A");
            else if (selected >= A && selected < C_end)
                sequence.append("C");
            else if (selected >= C_end && selected < G_End)
                sequence.append("G");
            else
                sequence.append("T");
        }
        // System.out.println(sequence.length());
        writeToFile(sequence, writer);

        // generate rest of the sequences
        float deleteProbability = probability / 2.0f;

        for (int i = 2; i <= numberOfSequences; i++) {
            // System.out.println("Next float value is = " + rand.nextFloat());
            int count_delete = 0;
            int count_mutate = 0;
            for (int j = 0; j < sequence.length(); j++) {
                // generate a random number between 0.0 inclusive and 1.0 exclusive
                float mutate = rand.nextFloat();

                if (mutate < deleteProbability) {
                    sequence.setCharAt(j, 'X');
                    count_delete++;
                } else if (mutate < probability) {
                    count_mutate++;
                    int selected = rand.nextInt(sum);
                    if (selected < A)
                        sequence.setCharAt(j, 'A');
                    else if (selected >= A && selected < C_end)
                        sequence.setCharAt(j, 'C');
                    else if (selected >= C_end && selected < G_End)
                        sequence.setCharAt(j, 'G');
                    else
                        sequence.setCharAt(j, 'T');
                }
            }
            replaceAll(sequence, "X", "");
            writeToFile(sequence, writer);
            /*
             * System.out.println("Number of deleted characters in iteration no " + i +
             * " =  " + count_delete);
             * System.out.println("Number of mutated characters in iteration no " + i +
             * " =  " + count_mutate);
             * System.out.println("Sequence Length after iteration no " + i + " = " +
             * sequence.length());
             */
        }
    }

    private void replaceAll(StringBuilder sb, String find, String replace) {
        // compile pattern from find string
        Pattern p = Pattern.compile(find);
        // create new Matcher from StringBuilder object
        Matcher matcher = p.matcher(sb);
        // index of StringBuilder from where search should begin
        int startIndex = 0;
        while (matcher.find(startIndex)) {
            sb.replace(matcher.start(), matcher.end(), replace);
            // set next start index as start of the last match + length of replacement
            startIndex = matcher.start() + replace.length();
        }
    }

    private void writeToFile(StringBuilder write, BufferedWriter br) {
        try {
            br.write('>');
            br.append(write);
            br.newLine();
            br.write(
                    "--------------------------------------------------------------------------------------------------------------------------------------------------");
            br.newLine();
            br.flush();
        } catch (Exception e) {
            System.out.println("unable to write to file");
        }

    }
}