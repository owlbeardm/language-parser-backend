package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class DetailedWord {
    private WordWithWritten word;
    private Etymology etymology;
    private List<String> translations;
    private List<WordWithWritten> dirived;
    private List<WordWithWritten> descendants;

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

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public List<WordWithWritten> getDirived() {
        return dirived;
    }

    public void setDirived(List<WordWithWritten> dirived) {
        this.dirived = dirived;
    }

    public List<WordWithWritten> getDescendants() {
        return descendants;
    }

    public void setDescendants(List<WordWithWritten> descendants) {
        this.descendants = descendants;
    }
}
