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
        currentDictionary.wordGuesses(currentDictionary.rawWords);
        //System.out.println(currentDictionary.results);
    }
}
