package by.c7d5a6.languageparser.rest.model;

import java.io.Serializable;

public class WordTraceResult implements Serializable {

    private Word word;
    private Language from;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Language getFrom() {
        return from;
    }

    public void setFrom(Language from) {
        this.from = from;
    }
}
