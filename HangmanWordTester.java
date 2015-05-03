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
        System.out.println(currentDictionary.sortedList('z', currentDictionary.hangmanWordArrayList).get(96));
        System.out.println("Expected: terrazzo");
        System.out.println(currentDictionary.priceOfGuess('e', currentDictionary.hangmanWordArrayList));
        System.out.println("Expected: 3307");
    }
}
