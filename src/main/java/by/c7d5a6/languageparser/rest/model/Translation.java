package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.entity.enums.TranslationType;

public class Translation {
    WordWithWritten wordTo;
    Phrase phraseTo;
    TranslationType type;

    public WordWithWritten getWordTo() {
        return wordTo;
    }

    public void setWordTo(WordWithWritten wordTo) {
        this.wordTo = wordTo;
    }

    public Phrase getPhraseTo() {
        return phraseTo;
    }

    public void setPhraseTo(Phrase phraseTo) {
        this.phraseTo = phraseTo;
    }

    public TranslationType getType() {
        return type;
    }

    public void setType(TranslationType type) {
        this.type = type;
    }
}
