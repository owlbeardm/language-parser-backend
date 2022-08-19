package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class WordOriginSource extends Base {

    private Word word;
    private Word wordSource;
    private String sourceInitialVersion;
    private String comment;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Word getWordSource() {
        return wordSource;
    }

    public void setWordSource(Word wordSource) {
        this.wordSource = wordSource;
    }

    public String getSourceInitialVersion() {
        return sourceInitialVersion;
    }

    public void setSourceInitialVersion(String sourceInitialVersion) {
        this.sourceInitialVersion = sourceInitialVersion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
