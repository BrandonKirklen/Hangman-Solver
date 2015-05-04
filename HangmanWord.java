/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Declaration of class "HangmanWord" which holds a given word as well as a difficulty value.
 */
public class HangmanWord {
    private String Word;
    private int guessesNeeded;
    private String wrongGuesses;

    public HangmanWord(String Word)
    {
        this.Word = Word;
    }

    public String toString()
    {
        return this.Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public void setGuessesNeeded(int guessesNeeded)
    {
        this.guessesNeeded=guessesNeeded;
    }

    public void setWrongGuesses(String wrongGuesses)
    {
        this.wrongGuesses = wrongGuesses;
    }
    public String getWord()
    {
        return Word;
    }

    public int getGuessesNeeded()
    {
        return guessesNeeded;
    }

    public String getWrongGuesses()
    {
        return wrongGuesses;
    }
}
