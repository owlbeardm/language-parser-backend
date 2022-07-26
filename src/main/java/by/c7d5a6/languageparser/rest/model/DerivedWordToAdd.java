package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class DerivedWordToAdd extends WordToAdd {
    List<Word> derivedFrom;

    public List<Word> getDerivedFrom() {
        return derivedFrom;
    }

    public void setDerivedFrom(List<Word> derivedFrom) {
        this.derivedFrom = derivedFrom;
    }
}
