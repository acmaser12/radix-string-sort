import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixStringSort {

    public static void main(String[] args) {
        // output my info
        System.out.println("Submitted by Adam Maser - masera1@csp.edu");
        System.out.println("I certify that this is my own work");

        // create array of buckets
        LinkedList<String>[] buckets = new LinkedList[27];

        // create list to store words
        LinkedList<String> words = new LinkedList<>();

        // load words from file into
        try {
            words = loadWords(new File("words.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Exiting program...");
            System.exit(2);
        }

        // instantiate each bucket
        for (int i = 0; i < 27; i++) {
            buckets[i] = new LinkedList<>();
        }

        // project spec states words are no longer than 10 letters, so we will iterate 10 times
        for (int i = 10; i > 0; i--) {
            // perform this iteration's radix sort
            buckets = performRadixSortIteration(words, buckets, i);

            // empty words list (to allow it to be reused)
            words.clear();

            // extract words from buckets
            words = extractFromBuckets(buckets, words);

            // output words
            System.out.println("\nIterating on index: " + i);
            System.out.print("\t");
            displayWords(words);
        }
    }
    private static LinkedList<String>[] performRadixSortIteration(LinkedList<String> words, LinkedList<String>[] buckets, int iteration) {
        for (String word : words) {
            // pointer determines which bucket the word goes into
            int pointer = 0;
            if (word.length() >= iteration) {
                pointer = word.charAt(iteration - 1) - 96;  // offset by 96 for ASCII char values
            }
            buckets = placeInBucket(buckets, pointer, word);
        }

        return buckets;
    }

    private static LinkedList<String>[] placeInBucket(LinkedList<String>[] buckets, int bucketLocation, String word) {
        buckets[bucketLocation].addLast(word);
        return buckets;
    }

    private static LinkedList<String> extractFromBuckets(LinkedList<String>[] buckets, LinkedList<String> words) {
        for (LinkedList<String> bucket : buckets) {
            int size = bucket.size();
            for (int j = 0; j < size; j++) {
                // add first element in list
                words.add(bucket.getFirst());
                // pop first element out of list
                bucket.removeFirst();
            }
        }
        return words;
    }

    private static void displayWords(LinkedList<String> words) {
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println();
    }

    private static LinkedList<String> loadWords(File wordFile) throws FileNotFoundException {
        Scanner readFile = new Scanner(wordFile);
        LinkedList<String> words = new LinkedList<>();
        while (readFile.hasNextLine()) {
            words.add(readFile.nextLine().toLowerCase());
        }
        return words;
    }
}
