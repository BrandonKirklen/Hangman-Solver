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

    private static void log(Object error)
    {
        System.out.println(String.valueOf(error));
    }

    public ArrayList<HashMap> sortedList(char guess, ArrayList<String> wordArray)
    {

        ArrayList<HashMap> results = new ArrayList<>();
        HashMap<Integer, ArrayList<String>> wordListContainingGuess;
        int sum=0;
        for(String word : wordArray)
        {
            wordListContainingGuess = new HashMap<>();
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
            results.add(wordListContainingGuess);
            sum=0;
        }
        return results;
    }

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

    public char bestGuess(String letters, ArrayList<String> wordArray)
    {
        String allLetters = "abcdefghijklmnopqrstuvwxyz";
        String[] currentLetters = allLetters.split("");
        String[] lettersSplit = letters.split("");
        ArrayList<String> symmetricDifference = new ArrayList<>();
        for(String letter : currentLetters)
        {
            if ( !letters.contains(letter) )
            {
                symmetricDifference.add(letter);
            }
        }
        HashMap<Integer, Character> costedGuess = new HashMap<>();
        int bestGuess = Integer.MAX_VALUE;
        char bestLetter='!';
        for(String letter : symmetricDifference)
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

    public void wordGuesses(ArrayList<String> wordArray)
    {
        wordGuesses(wordArray, 0, "");
    }

    public void wordGuesses(ArrayList<String> wordArray, int wrong, String letters)
    {

        if ( wordArray.size() == 1)
        {
            results.add(new HangmanWord(wordArray.get(0)));
        }
        else
        {
            char bestGuess = bestGuess(letters, wordArray);
            //System.out.println(bestGuess);
            ArrayList bestList = sortedList(bestGuess, wordArray);
            for(int i=0; i < bestList.size(); i++)
            {
                Map item = (Map) bestList.get(i);
                ArrayList<String> words = new ArrayList<>();
                words.add((String) ((ArrayList) item.values().toArray()[0]).get(0));
                int pattern = (int) item.keySet().toArray()[0];
                wordGuesses(words, wrong + ((pattern == 0) ? 1 : 0), letters);
            }
        }
    }
}
