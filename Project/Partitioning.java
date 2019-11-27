package Project;

import java.util.*;

class Partitioning {
    public void partition() {
        int sequenceArraySize = 40;
        int min_cols = 40;
        int start = 0;
        for (int i = 0; i < min_cols; i++) {
            // check all characters in particular column
            char check = sequence.get(0).charAt(i);
            int count = 1;

            for (int j = 1; j < sequenceArraySize; j++) {
                if (check != sequence.get(j).charAt(i))
                    break;

                count++;
            }

            if (count == sequence.size()) {
                // call PoMSA upto this index here
                makeSubstrings(sequence, start, i);
                start = i + 1;
            }
        }
    }

    private void makeSubstrings(List<StringBuilder> sb, int left, int right) {
        int size = sb.size();
        List<StringBuilder> partitionedList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            StringBuilder current = sb.get(i);
            current = new StringBuilder(current.substring(left, right + 1));
            System.out.println(current);
            partitionedList.add(current);
        }

        executePomsa(partitionedList, 50);
    }
}
