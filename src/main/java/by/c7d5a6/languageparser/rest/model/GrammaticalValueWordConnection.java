package by.c7d5a6.languageparser.rest.model;

public class GrammaticalValueWordConnection {

    private GrammaticalCategoryValue value;
    private Word word;

    public GrammaticalCategoryValue getValue() {
        return value;
    }

    public void setValue(GrammaticalCategoryValue value) {
        this.value = value;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
