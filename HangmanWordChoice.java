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

    public HangmanWordChoice(String filepath)
    {
        pathToDictionary = filepath;
        Path path = Paths.get(pathToDictionary);
        try (BufferedReader reader =  Files.newBufferedReader(path, ENCODING)){
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                HangmanWord word = new HangmanWord(currentLine, wordDifficulty(currentLine));
                hangmanWordArrayList.add(word);
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
        do {
            i = pickANumber.nextInt(hangmanWordArrayList.size());
            loopCount++;
        }
        while ( hangmanWordArrayList.get(i).getDifficulty() != difficulty && loopCount < hangmanWordArrayList.size());
        if (loopCount == hangmanWordArrayList.size())
        {
            log("Invalid Input: difficulty");
            return null;
        }
        else {
            return hangmanWordArrayList.get(i).getWord();
        }
    }

    public int wordDifficulty(String testWord)
    {
        return testWord.length();
    }

    private static void log(Object error)
    {
        System.out.println(String.valueOf(error));
    }

    public Map sortedList(char guess, ArrayList<HangmanWord> wordArray)
    {

        Map<Integer, ArrayList<String>> wordListContainingGuess = new HashMap<>();
        int sum=0;
        for( int i=0; i < wordArray.size(); i++)
        {
            for (int j=0; j < wordArray.get(i).getWord().length(); j++)
            {
                if ( wordArray.get(i).getWord().charAt(j) == guess)
                {
                    sum += 1 << j;
                }
            }
            if (wordListContainingGuess.get(sum) == null)
            {
                wordListContainingGuess.put(sum, new ArrayList<>());
            }
            wordListContainingGuess.get(sum).add(wordArray.get(i).getWord());
            sum=0;
        }
        return wordListContainingGuess;
    }

    public int priceOfGuess(char guess, ArrayList<HangmanWord> wordArray)
    {
        int numberWithoutGuess=0;
        for ( int i=0; i < wordArray.size(); i++)
        {
            for (int j=0; j < wordArray.get(i).getWord().length(); j++)
            {
                if( wordArray.get(i).getWord().charAt(j) == guess )
                {
                    numberWithoutGuess++;
                    j++;
                }
            }
        }
        return wordArray.size()-numberWithoutGuess;
    }
    int wrong=0;
    public ArrayList wordGuesses(ArrayList<HangmanWord> wordArray, String letters)
    {
        if ( wordArray.size() == 1)
        {
            return wordArray;
        }
        else
        {

        }
        return wordArray;
    }
}
