package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class WordDeclension {
    private Declension declension;
    private List<String> wordDeclensions;

    public Declension getDeclension() {
        return declension;
    }

    public void setDeclension(Declension declension) {
        this.declension = declension;
    }

    public List<String> getWordDeclensions() {
        return wordDeclensions;
    }

    public void setWordDeclensions(List<String> wordDeclensions) {
        this.wordDeclensions = wordDeclensions;
    }
}
