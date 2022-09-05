package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.format.IPAFormat;

import java.io.Serializable;

public class WordTraceResult implements Serializable {

    private String word;
    private Language from;

    public WordTraceResult(Language language, String word) {
        this.from = language;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Language getFrom() {
        return from;
    }

    public void setFrom(Language from) {
        this.from = from;
    }
}
