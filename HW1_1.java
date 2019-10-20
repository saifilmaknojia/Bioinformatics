import java.util.*;

class HW1_1 {
    public static void main(String[] args) {
        HW1_1 obj = new HW1_1();
        obj.generateSequence(args);
    }

    private void generateSequence(String[] args) {
        int sequenceLength = Integer.parseInt(args[0]);
        int A = Integer.parseInt(args[1]);
        int C = Integer.parseInt(args[2]);
        int G = Integer.parseInt(args[3]);
        int T = Integer.parseInt(args[4]);
        int numberOfSequences = Integer.parseInt(args[5]);
        float probability = Float.parseFloat(args[6]);
        String outputFileName = args[7];

        StringBuilder sequence = new StringBuilder();
        int sum = A + C + G + T;
        // int C_start = A;
        int C_end = A + C;
        // int G_start = C_end;
        int G_End = C_end + G;
        // int T_Start = G_End;
        // int T_End = T_Start + T;
        Random rand = new Random();
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
        System.out.println(sequence);
    }

    private void writeToFile() {

    }
}