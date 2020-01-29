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

        // test
        LinkedList<String> words = buckets[0];
        for (String word : words) {
            System.out.println(word);
        }

        // it seems that ASCII offsets lowercase characters by 96
        char a = 'k';
        int aInt = a - 96;
        System.out.println("Value of 'a': " + aInt);
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
