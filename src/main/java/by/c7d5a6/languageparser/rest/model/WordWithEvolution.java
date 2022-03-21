package by.c7d5a6.languageparser.rest.model;


import by.c7d5a6.languageparser.entity.enums.LanguageConnectionType;

public class WordWithEvolution {

    private Word word;
    private LanguageConnection languageConnection;
    private Word wordEvolved;
    private LanguageConnectionType wordEvolvedType;
    private String calculatedEvolution;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public LanguageConnection getLanguageConnection() {
        return languageConnection;
    }

    public void setLanguageConnection(LanguageConnection languageConnection) {
        this.languageConnection = languageConnection;
    }

    public Word getWordEvolved() {
        return wordEvolved;
    }

    public void setWordEvolved(Word wordEvolved) {
        this.wordEvolved = wordEvolved;
    }

    public LanguageConnectionType getWordEvolvedType() {
        return wordEvolvedType;
    }

    public void setWordEvolvedType(LanguageConnectionType wordEvolvedType) {
        this.wordEvolvedType = wordEvolvedType;
    }

    public String getCalculatedEvolution() {
        return calculatedEvolution;
    }

    public void setCalculatedEvolution(String calculatedEvolution) {
        this.calculatedEvolution = calculatedEvolution;
    }
}
