/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Tester file for HangmanWordChoice
 */
public class HangmanWordTester {
    public static void main(String[] args)
    {
        HangmanWordChoice currentDictionary =
                new HangmanWordChoice("C:/Users/Brandon/AeroFS/College/CPE 102/Hangman/wordlist.txt");
        System.out.println(currentDictionary.newWord(6));
    }
}
