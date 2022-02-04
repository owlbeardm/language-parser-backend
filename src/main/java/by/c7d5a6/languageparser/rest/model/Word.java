package by.c7d5a6.languageparser.rest.model;


import by.c7d5a6.languageparser.rest.model.base.Base;

public class Word extends Base {

    private Language language;
    private Boolean isForgotten;
    private POS partOfSpeech;
    private String word;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getForgotten() {
        return isForgotten;
    }

    public void setForgotten(Boolean forgotten) {
        isForgotten = forgotten;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public POS getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(POS partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
