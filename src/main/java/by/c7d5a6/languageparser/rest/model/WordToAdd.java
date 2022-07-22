package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.entity.enums.WordOriginType;

public class WordToAdd extends Word {
    private WordOriginType wordOriginType;

    public WordOriginType getWordOriginType() {
        return wordOriginType;
    }

    public void setWordOriginType(WordOriginType wordOriginType) {
        this.wordOriginType = wordOriginType;
    }
}
