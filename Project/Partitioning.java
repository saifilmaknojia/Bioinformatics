// package Project;

import java.util.*;

class Partitioning {
    static void partition(List<StringBuilder> sequence, int maxLength, int min_cols, int threshold) {
        int start = 0;
        for (int i = 0; i < min_cols; i++) {
            // check all characters in particular column
            char check = sequence.get(0).charAt(i);
            int count = 1;

            for (int j = 1; j < sequence.size(); j++) {
                if (check != sequence.get(j).charAt(i))
                    break;

                count++;
            }

            if (count == sequence.size()) {
                // call PoMSA upto this index here
                makeSubstrings(sequence, start, i, maxLength, threshold);
                start = i + 1;
            }
        }
    //    System.out.println(min_cols);
        lastBatch(sequence, start, threshold);
    }

    private static void lastBatch(List<StringBuilder> oglist, int last, int threshold)
    {
        List<StringBuilder> finalList = new ArrayList<>();
        int max_length = Integer.MIN_VALUE;
    //    System.out.println("*********************************************");
        for (int i = 0; i < oglist.size(); i++) {
            StringBuilder current = oglist.get(i);
            current = new StringBuilder(current.substring(last));
            max_length = Math.max(current.length(), max_length);
      //      System.out.println(current);
            finalList.add(current);
        }
        Pomsa.executePomsa(finalList, max_length, threshold);
    }

    private static void makeSubstrings(List<StringBuilder> sb, int left, int right, int maxLength, int threshold) {
        List<StringBuilder> partitionedList = new ArrayList<>();
        for (int i = 0; i < sb.size(); i++) {
            StringBuilder current = sb.get(i);
            current = new StringBuilder(current.substring(left, right + 1));
       //     System.out.println("Hello "+current);
            partitionedList.add(current);
        }

        Pomsa.executePomsa(partitionedList, right - left + 1, threshold);
    }
}
