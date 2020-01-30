import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class RadixStringSort {

    public static void main(String[] args) {
        // create array of buckets
        LinkedList<String>[] buckets = new LinkedList[27];

        // we will say that the first List in the bucket is the carryover bucket
        try {
            buckets[0] = loadWords(new File("words.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Exiting program...");
            System.exit(2);
        }

        // deem first bucket as list of all words
        LinkedList<String> words = buckets[0];

        // project spec states words are no longer than 10 letters, so we will loop 10 times
        for (int i = 1; i < 11; i++) {
            for (String word : words) {
                if (i <= word.length()) {
                    char pointer = word.charAt(word.length() - i);
                    // it seems that ASCII offsets lowercase characters by 96
                    // to place into proper bucket, subtract int value of char by 96
                    int charVal = pointer - 96;
                    buckets[charVal].add(word);
                }
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
