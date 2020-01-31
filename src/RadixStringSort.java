import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixStringSort {

    public static void main(String[] args) {
        // create array of buckets
        // TODO: Change this to Collection object later
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

        for (int i = 0; i < 27; i++) {
            buckets[i] = new LinkedList<>();
        }


        // project spec states words are no longer than 10 letters, so we will loop 10 times
        for (int i = 10; i > 0; i--) {
            for (String word : words) {
                // pointer determines which bucket the word goes into
                int pointer = 0;
                if (word.length() >= i) {
                    pointer = word.charAt(i-1) - 96;  // offset by 96 for ASCII char values
                }
                buckets[pointer].addLast(word);
            }
            // empty words list
            words.clear();
            for (LinkedList<String> bucket : buckets) {
                int size = bucket.size();
                for (int j = 0; j < size; j++) {
                    // add first element in list
                    words.add(bucket.getFirst());
                    // pop first element out of list
                    bucket.removeFirst();
                }
            }
            System.out.println("\nIteration " + (11 - i) + ": ");
            for (String word : words) {
                System.out.print(word + " | ");
            }
            System.out.println();
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
