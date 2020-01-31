import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixStringSort {

    public static void main(String[] args) {
        // create array of buckets
        // TODO: Change this to Collection object later
        LinkedList<String>[] buckets = new LinkedList[27];

        // we will say that the first List in the bucket is the carryover bucket
        try {
            buckets[0] = loadWords(new File("words.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Exiting program...");
            System.exit(2);
        }

        for (int i = 0; i < 27; i++) {
            buckets[i] = new LinkedList<>();
        }

        // create bucket to store list words
        LinkedList<String> words = new LinkedList<>();

        // project spec states words are no longer than 10 letters, so we will loop 10 times
        for (int i = 1; i < 11; i++) {
            for (String word : words) {
                if (i <= word.length()) {
                    char pointer = word.charAt(word.length() - i);
                    // it seems that ASCII offsets lowercase characters by 96
                    // to place into proper bucket, subtract int value of char by 96
                    int charVal = pointer - 96;
                    buckets[charVal].add(word);
                } else {
                    // char pointer = word.charAt(word.length() - 1);
                    // int charVal = pointer - 96;
                    buckets[0].add(word);
                }
            }
            // empty words list
            words.clear();
            for (LinkedList<String> bucket : buckets) {
                // System.out.println("Bucket " + j + " contains: ");
                for (String word : bucket) {
                    // System.out.println(word);
                    words.add(word);
                }
                // empty particular bucket
                bucket.clear();
            }
            System.out.println("\nIteration " + i + ": ");
            for (String word : words) {
                System.out.print(word + " | ");
            }
        }
    }

    private static LinkedList<String> loadWords(File wordFile) throws FileNotFoundException {
        Scanner readFile = new Scanner(wordFile);
        LinkedList<String> words = new LinkedList<>();
        while (readFile.hasNextLine()) {
            words.add(readFile.nextLine());
        }
        return words;
    }
}
