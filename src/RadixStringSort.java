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


    /**
     * @param words : LinkedList of unsorted words
     * @param buckets : array of 27 empty LinkedLists that act as buckets for radix sort
     * @param iteration : number of iteration currently on || determines which character is pulled from string
     * @return : the updated buckets
     *
     * Iterates through each word in list and places it in a bucket based on character at interation param
     */
    private static LinkedList<String>[] performRadixSortIteration(LinkedList<String> words, LinkedList<String>[] buckets, int iteration) {
        for (String word : words) {
            // pointer determines which bucket the word goes into
            int pointer = 0;
            if (word.length() >= iteration) {
                pointer = word.charAt(iteration - 1) - 96;  // offset by 96 for ASCII char values
            }
            // place the word in the particular bucket
            buckets = placeInBucket(buckets, pointer, word);
        }
        return buckets;
    }

    /**
     * @param buckets : array of 27 LinkedLists that act as buckets for radix sort
     * @param bucketLocation : int that determines which bucket to place word in
     * @param word : String that is being added to bucket
     * @return : the updated buckets
     *
     * Receives a word and position, adds the word to the specified position in buckets param
     */
    private static LinkedList<String>[] placeInBucket(LinkedList<String>[] buckets, int bucketLocation, String word) {
        buckets[bucketLocation].addLast(word);
        return buckets;
    }

    /**
     * @param buckets : array of 27 LinkedLists that act as buckets for radix sort
     * @param words : empty LinkedList that will store words in updated order from radix sort
     * @return : LinkedList that contains words in updated order
     *
     * Receives buckets that have been updated from radix sort, iterates through each bucket and adds to list
     */
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

    /**
     * @param words : LinkedList of words to be displayed
     *
     * Outputs each word in LinkedList followed by a space
     */
    private static void displayWords(LinkedList<String> words) {
        for (String word : words) {
            System.out.print(word + " ");
        }
        System.out.println();
    }

    /**
     * @param wordFile : File object containing words
     * @return : LinkedList of individual words to be sorted
     * @throws FileNotFoundException : if file not found, throws exception
     */
    private static LinkedList<String> loadWords(File wordFile) throws FileNotFoundException {
        Scanner readFile = new Scanner(wordFile);
        LinkedList<String> words = new LinkedList<>();
        while (readFile.hasNextLine()) {
            words.add(readFile.nextLine().toLowerCase());
        }
        return words;
    }
}
