package by.c7d5a6.languageparser.rest.model;

public class Word extends Base {

    private Language language;
    private Boolean isForgotten;
    // TODO:
    //  partOfSpeech: PartOfSpeech;
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
}
