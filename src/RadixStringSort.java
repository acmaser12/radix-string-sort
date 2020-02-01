/*

Adam Maser
CSC 420
1.31.2020

Assignment 3
desc: Create a series of algorithms to implement a radix sort on Strings

Which Collection object did I choose and why?
I chose to use a LinkedList, simply because you can add and remove from the beginning and end of the list. When
storing each string in a bucket, it is important to maintain the order of the elements, especially for reading
those elements back into a list.

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixStringSort {

    public static void main(String[] args) {
        // output my info
        System.out.println("Submitted by Adam Maser - masera1@csp.edu");
        System.out.println("I certify that this is my own work");

        // create array of buckets
        ArrayList<LinkedList<String>> buckets = new ArrayList<>();

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
            buckets.add(new LinkedList<>());
        }

        // remove any extra space in buckets
        buckets.trimToSize();

        // project spec states words are no longer than 10 letters, so we will iterate 10 times
        for (int i = 10; i > 0; i--) {
            // perform this iteration's radix sort
            performRadixSortIteration(words, buckets, i);

            // empty words list (to allow it to be reused)
            words.clear();

            // extract words from buckets
            extractFromBuckets(buckets, words);

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
     *
     * Iterates through each word in list and places it in a bucket based on character at interation param
     */
    private static void performRadixSortIteration(LinkedList<String> words, ArrayList<LinkedList<String>> buckets, int iteration) {
        for (String word : words) {
            // pointer determines which bucket the word goes into
            int pointer = 0;
            if (word.length() >= iteration) {
                pointer = word.charAt(iteration - 1) - 96;  // offset by 96 for ASCII char values
            }
            // place the word in the particular bucket
            placeInBucket(buckets, pointer, word);
        }
    }

    /**
     * @param buckets : array of 27 LinkedLists that act as buckets for radix sort
     * @param bucketLocation : int that determines which bucket to place word in
     * @param word : String that is being added to bucket
     *
     * Receives a word and position, adds the word to the specified position in buckets param
     */
    private static void placeInBucket(ArrayList<LinkedList<String>> buckets, int bucketLocation, String word) {
        buckets.get(bucketLocation).addLast(word);
    }

    /**
     * @param buckets : array of 27 LinkedLists that act as buckets for radix sort
     * @param words : empty LinkedList that will store words in updated order from radix sort
     *
     * Receives buckets that have been updated from radix sort, iterates through each bucket and adds to list
     */
    private static void extractFromBuckets(ArrayList<LinkedList<String>> buckets, LinkedList<String> words) {
        for (LinkedList<String> bucket : buckets) {
            int size = bucket.size();
            for (int j = 0; j < size; j++) {
                // add first element in list
                words.add(bucket.getFirst());
                // pop first element out of list
                bucket.removeFirst();
            }
        }
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
        // add each word to list, ensuring they are lowercase
        while (readFile.hasNextLine()) {
            words.add(readFile.nextLine().toLowerCase());
        }
        return words;
    }
}
