package by.c7d5a6.languageparser.rest.model;


public class WordWithBorrowed {

    private Word word;
    private WordOriginSource wordEvolved;
    private String calculatedEvolution;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public WordOriginSource getWordEvolved() {
        return wordEvolved;
    }

    public void setWordEvolved(WordOriginSource wordEvolved) {
        this.wordEvolved = wordEvolved;
    }

    public String getCalculatedEvolution() {
        return calculatedEvolution;
    }

    public void setCalculatedEvolution(String calculatedEvolution) {
        this.calculatedEvolution = calculatedEvolution;
    }
}
