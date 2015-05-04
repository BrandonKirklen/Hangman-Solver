/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Class to rate and manage hangman words
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
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

    ArrayList<HangmanWord> hangmanWordArrayList = new ArrayList<>();
    ArrayList<String> rawWords = new ArrayList<>();

    //Reads words from a given file and puts them into various lists
    public HangmanWordChoice(String filepath)
    {
        pathToDictionary = filepath;
        Path path = Paths.get(pathToDictionary);
        try (BufferedReader reader =  Files.newBufferedReader(path, ENCODING)){
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                HangmanWord word = new HangmanWord(currentLine);
                hangmanWordArrayList.add(word);
                rawWords.add(currentLine);
                //log(currentLine);
            }
        }
        catch (Exception e)
        {
            log(e);
        }
    }

    public String newWord(int difficulty)
    {
        int i;
        int loopCount=0;
        do
        {
            i = pickANumber.nextInt(hangmanWordArrayList.size());
            loopCount++;
        }
        while ( loopCount < hangmanWordArrayList.size());
        if (loopCount == hangmanWordArrayList.size())
        {
            log("Invalid Input: difficulty");
            return null;
        }
        else
        {
            return hangmanWordArrayList.get(i).getWord();
        }
    }

    public int wordDifficulty(String testWord)
    {
        return testWord.length();
    }

    //Used for debugging
    private static void log(Object error)
    {
        System.out.println(String.valueOf(error));
    }

    public HashMap<Integer, ArrayList<String>> sortedList(char guess, ArrayList<String> wordArray)
    {

        HashMap<Integer, ArrayList<String>> wordListContainingGuess = new HashMap<>();
        for(String word : wordArray)
        {
            int sum=0;
            for ( int j=0; j < word.length(); j++)
            {
                if ( word.charAt(j) == guess )
                {
                    sum += 1 << j;
                }
            }
            if ( wordListContainingGuess.get(sum) == null )
            {
                wordListContainingGuess.put(sum, new ArrayList<>());
            }
            wordListContainingGuess.get(sum).add(word);
        }
        return wordListContainingGuess;
    }
    //Returns the number of words which don't contain a given letter
    public int priceOfGuess(char guess, ArrayList<String> wordArray)
    {
        int numberWithoutGuess=0;
        for (String word : wordArray)
        {
            for (int j=0; j < word.length(); j++)
            {
                if( word.charAt(j) == guess )
                {
                    numberWithoutGuess++;
                    j++;
                }
            }
        }
        return wordArray.size()-numberWithoutGuess;
    }

    public char bestGuess(String guessedLetters, ArrayList<String> wordArray)
    {
        String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
        ArrayList<String> unguessedLetters = new ArrayList<>();
        for(String letter : alphabet )
        {
            if ( !guessedLetters.contains(letter) )
            {
                unguessedLetters.add(letter);
            }
        }
        System.out.println(unguessedLetters);
        int bestGuess = Integer.MAX_VALUE;
        char bestLetter='!';
        for(String letter : unguessedLetters)
        {
            int currentPrice = priceOfGuess(letter.charAt(0), wordArray);
            if ( currentPrice < bestGuess )
            {
                bestGuess = currentPrice;
                bestLetter = letter.charAt(0);
            }
        }
        return bestLetter;
    }

    ArrayList<HangmanWord> results = new ArrayList<>();

    //This function overloads the wordGuesses function to provide default values for the first call
    public void wordGuesses(ArrayList<String> wordArray)
    {
        wordGuesses(wordArray, 0, "");
    }

    public void wordGuesses(ArrayList<String> wordArray, int wrong, String letters)
    {
        if ( wordArray.size() == 1)
        {
            results.add(new HangmanWord(wordArray.get(0)));
            //Also add the number of wrong guesses and the string of guesses here
        }
        else
        {
            char bestGuess = bestGuess(letters, wordArray);
            letters += bestGuess;
            System.out.println(bestGuess);
            HashMap<Integer, ArrayList<String>> bestList = sortedList(bestGuess, wordArray);
            for ( Map.Entry<Integer, ArrayList<String>> entry : bestList.entrySet() )
            {
                Integer pattern = entry.getKey();
                ArrayList<String> words = entry.getValue();
                wordGuesses(words, wrong + ((pattern == 0) ? 1 : 0), letters);
            }
        }
    }
}
