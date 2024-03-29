Position-based Multiple Sequence Alignment (PoMSA) heuristic algorithm is implemented. This algorithm generates a position matrix to efficiently insert gaps into the set of sequences. To improve the alignment efficiency, it uses a partitioning scheme and correctly adds gaps in the sequences. Follow the below steps to run

First compile the java files by running
javac \*.java

Once the files are compiled, run it as below
The working of the project is divided in 3 parts

1. To generate the sequences, run - java SequenceGenerator fileName NumberOfSequences StartRange EndRange mutationRate

   E.g.

   > java SequenceGenerator sequence 40 100 150 0.03

2. To run the PoMSA algorithm, run - java BioProject inputFile outputFile Yes/No(with partitioning) threshold

   E.g

   > java BioProject sequence output Yes 5

3. To calculate the alignment score, run - java AlignmentScore inputFile

   E.g

   > java AlignmentScore output
