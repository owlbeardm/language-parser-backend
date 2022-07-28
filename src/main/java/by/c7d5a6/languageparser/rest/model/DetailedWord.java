package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class DetailedWord {
    private WordWithWritten word;
    private Etymology etymology;
    private List<Translation> translations;
    private List<WordWithTranslations> derived;
    private List<WordWithTranslations> descendants;

    public WordWithWritten getWord() {
        return word;
    }

    public void setWord(WordWithWritten word) {
        this.word = word;
    }

    public Etymology getEtymology() {
        return etymology;
    }

    public void setEtymology(Etymology etymology) {
        this.etymology = etymology;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<WordWithTranslations> getDerived() {
        return derived;
    }

    public void setDerived(List<WordWithTranslations> derived) {
        this.derived = derived;
    }

    public List<WordWithTranslations> getDescendants() {
        return descendants;
    }

    public void setDescendants(List<WordWithTranslations> descendants) {
        this.descendants = descendants;
    }
}
