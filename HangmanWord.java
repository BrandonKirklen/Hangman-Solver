/**
 * Created by Brandon Kirklen on 5/2/2015.
 * Declaration of class "HangmanWord" which holds a given word as well as a difficulty value.
 */
public class HangmanWord<Word,Difficulty> {
    private String Word;
    private int Difficulty;

    public HangmanWord(String Word, int Difficulty)
    {
        this.Word = Word;
        this.Difficulty = Difficulty;
    }

    public void setWord(String word) {
        Word = word;
    }

    public void setDifficulty(int difficulty) {
        Difficulty = difficulty;
    }
    public String getWord()
    {
        return Word;
    }

    public int getDifficulty() {
        return Difficulty;
    }
}
