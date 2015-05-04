import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Tester file for HangmanWordChoice
 */
public class HangmanWordTester {
    public static void main(String[] args)
    {
        HangmanWordChoice currentDictionary =
                new HangmanWordChoice("wordlist.txt");
        System.out.println(currentDictionary.newWord(11));
        System.out.println("Word 11 char long");
        ArrayList<String> test = new ArrayList<>();
        test.add("dog");
        test.add("cat");
        test.add("pig");
        test.add("cock");
        test.add("horse");
        test.add("zebra");
        currentDictionary.wordGuesses(currentDictionary.rawWords);
        //System.out.println(currentDictionary.results);
        Collections.sort(currentDictionary.results);
        /*for (HangmanWord currentWord : currentDictionary.results)
        {
            System.out.println("[" + currentWord.getWrongGuesses().length() + ", " + currentWord.getGuessesNeeded() + ", "
            + currentWord.getWord() + ", " + currentWord.getWrongGuesses() + "]");
        }*/
        System.out.println("Easy word: " + currentDictionary.newWord(0));
        System.out.println("Med  word: " + currentDictionary.newWord(1));
        System.out.println("Hard word: " + currentDictionary.newWord(2));
    }
}
