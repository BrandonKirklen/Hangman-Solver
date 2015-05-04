/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Class to rate and manage hangman words
 */

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class HangmanWordChoice {
    public String pathToDictionary;
    final static Charset ENCODING = StandardCharsets.UTF_8;
    public Random pickANumber = new Random();
    public ArrayList<HangmanWord> results = new ArrayList<>();
    private ArrayList<String> rawWords = new ArrayList<>();


    //Reads words from a given file and puts them into rawWords list
    //given file must not have duplicates and each word must be on a new line
    public HangmanWordChoice(String filepath)
    {
        pathToDictionary = filepath;
        Path path = Paths.get(pathToDictionary);
        try (BufferedReader reader =  Files.newBufferedReader(path, ENCODING))
        {
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                rawWords.add(currentLine);
            }
        }
        catch (Exception e)
        {
            log(e);
        }
        wordGuesses(rawWords); //Creates the results arraylist of type HangmanWord
        Collections.sort(results); //Uses built in comparer methods in HangmanWord to sort the results list
    }

    //Returns a random word from results in a section of the list. This is currently hardcoded with 3 sections
    public String newWord(int difficulty)
    {
        int i;
        int loopCount=0;
        int numberOfDifficulties=3;
        float lowerBound = (results.size()*(difficulty))/numberOfDifficulties;
        float upperBound = (results.size()*(difficulty+1))/numberOfDifficulties;
        do
        {
            i = pickANumber.nextInt(results.size());
            loopCount++;
        }
        while ( ( lowerBound < i && i < upperBound) && loopCount < results.size() );
        //Breakcase if there is no possible return value.
        if (loopCount == results.size())
        {
            log("Invalid Input: difficulty");
            return null;
        }
        else
        {
            return results.get(i).getWord();
        }
    }

    //Used for debugging
    private static void log(Object error)
    {
        //System.out.println(String.valueOf(error));
    }

    private HashMap<Integer, ArrayList<String>> sortedList(char guess, ArrayList<String> wordArray)
    {
        /*  Apply the single letter 'guess' to the sequence 'wordArray' and return
        a dictionary mapping the pattern of occurrences of 'wordArray' in a
        word to the list of words with that pattern.

        >>> words = 'deed even eyes mews peep star'.split("")
        >>> sortedlist('e', words).items
        [(0, ['star']), (2, ['mews']), (5, ['even', 'eyes']), (6, ['deed', 'peep'])]
        */
        HashMap<Integer, ArrayList<String>> wordListContainingGuess = new HashMap<>();
        for(String word : wordArray)
        {
            int sum=0;
            for ( int j=0; j < word.length(); j++)
            {
                if ( word.charAt(j) == guess )
                {
                    sum += 1 << j; /* Uses bitwise operator to make a unique key for a given configuration of a guessed
                                    * letter.
                                    *
                                    * >>> for 'e' in "star" sum = 0
                                    * >>> for 'e' in "even" sum = 5
                                    * */
                }
            }
            // Create the word array if it does not already exist for this given key
            if ( wordListContainingGuess.get(sum) == null )
            {
                wordListContainingGuess.put(sum, new ArrayList<>());
            }
            // Add our word to the word array at this key
            wordListContainingGuess.get(sum).add(word);
        }
        return wordListContainingGuess;
    }
    //Returns the number of words which don't contain a given letter
    private int priceOfGuess(char guess, ArrayList<String> wordArray)
    {
        /* Return the cost of a guess, namely the number of words that don't
        contain the guess.

        >>> words = 'deed even eyes mews peep star'.split()
        >>> guess_cost('e', words)
        1
        >>> guess_cost('s', words)
        3
        */
        String guessLetter = Character.toString(guess);
        int numWrongGuesses = 0; // number of words where guess is wrong
        for (String word : wordArray)
        {
            // If the word doesn't contain the guess, the guess is wrong
            if ( !word.contains(guessLetter) ) {
                numWrongGuesses++;
            }
        }
        return numWrongGuesses;
    }

    private char bestGuess(String guessedLetters, ArrayList<String> wordArray)
    {
        String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split(""); //Every possible letter in word
        ArrayList<String> unguessedLetters = new ArrayList<>();
        //Creates a symmetricDifference to find all letters that haven't been guessed yet
        for(String letter : alphabet )
        {
            if ( !guessedLetters.contains(letter) )
            {
                unguessedLetters.add(letter);
            }
        }
        // Outputs an array of all the letters which are left to be guessed
        log(unguessedLetters);
        // Starts the bestGuess at the lowest possible value
        int bestGuess = Integer.MAX_VALUE;
        // Some character that wouldn't appear in the words in normal use
        char bestLetter='!';
        //Find the letter which eliminates the most words by finding the letter which has the least wrong guesses
        for(String letter : unguessedLetters)
        {
            // Records what price we are paying currently
            int currentPrice = priceOfGuess(letter.charAt(0), wordArray);
            // For each letter, check if we have a lower price.
            if ( currentPrice < bestGuess )
            {
                // Updates what the lowest cost is
                bestGuess = currentPrice;
                // Keeps track of what letter has the lowest cost
                bestLetter = letter.charAt(0);
            }
        }
        //Returns the letter which has the fewest words that it doesn't appear in
        return bestLetter;
    }

    //This function overloads the wordGuesses function to provide default values for the first call
    private void wordGuesses(ArrayList<String> wordArray)
    {
        wordGuesses(wordArray, 0, "");
    }

    /* Main recursing function which takes in a list of words and updates results arraylist of type HangmanWord
     * and finds idea sequence of guesses based on the whole list of words to find the total number of guesses and
     * wrong guesses needed to find a given word.
     */
    private void wordGuesses(ArrayList<String> wordArray, int wrong, String letters)
    {
        /*When recursion finished, or only one word possible word left in the possible list, adds that word to the
        list of HangmanWords
        */
        if ( wordArray.size() == 1)
        {
            //Sets all the various parameters associated with each word.
            results.add(new HangmanWord(wordArray.get(0)));
            results.get(results.size()-1).setWrongGuesses(letters);
            results.get(results.size()-1).setGuessesNeeded(wrong);
        }
        else
        {
            // Calculates the best guess for the current words list for each letter that hasn’t been used yet
            char bestGuess = bestGuess(letters, wordArray);
            // This records which letter we're guessing
            letters += bestGuess;
            /* Uses the best guess method to get a bestguess char which then is passed to sorted list
             * to make a hashmap of all words using that best guess */
            HashMap<Integer, ArrayList<String>> bestList = sortedList(bestGuess, wordArray);
            // For each set of words which have a given character in the same positions inside bestList run this
            for ( Map.Entry<Integer, ArrayList<String>> entry : bestList.entrySet() )
            {
                /* If the key of what we're recursing on is equal to 0 then we'll want to increase the number of wrong
                 * guesses. This is because a 0 in the key value means that for a given guess, there were no words in
                 * the current set of words which matched the previous set.
                 */
                Integer pattern = entry.getKey();
                /* The words which we're going to pass into the function again, are all the words which are at the
                 * current key value.
                 */
                ArrayList<String> words = entry.getValue();
                /* Main recurse call which takes an increasingly small set of words until they have all been added
                 * to the results file.
                 */
                wordGuesses(words, wrong + ((pattern == 0) ? 1 : 0), letters);
            }
        }
    }
}
