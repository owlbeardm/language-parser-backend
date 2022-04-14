package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.entity.enums.TranslationType;
import by.c7d5a6.languageparser.rest.model.base.Base;

public class Translation extends Base {
    Word wordFrom;
    WordWithWritten wordTo;
    Phrase phraseTo;
    TranslationType type;

    public Word getWordFrom() {
        return wordFrom;
    }

    public void setWordFrom(Word wordFrom) {
        this.wordFrom = wordFrom;
    }

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
