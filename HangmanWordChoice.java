/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Class to rate and manage hangman words
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


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
            i = pickANumber.nextInt(hangmanWordArrayList.size()+1);
            loopCount++;
        }
        while ( hangmanWordArrayList.get(i).getDifficulty() != difficulty && loopCount < hangmanWordArrayList.size());
        if (loopCount < hangmanWordArrayList.size())
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

    private static void log(Object error){
        System.out.println(String.valueOf(error));
    }
}
