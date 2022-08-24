package by.c7d5a6.languageparser.rest.model;

import java.io.Serializable;
import java.util.List;

public class DescendantWords implements Serializable {
    WordWithTranslations word;
    List<DescendantWords> descendants;

    public WordWithTranslations getWord() {
        return word;
    }

    public void setWord(WordWithTranslations word) {
        this.word = word;
    }

    public List<DescendantWords> getDescendants() {
        return descendants;
    }

    public void setDescendants(List<DescendantWords> descendants) {
        this.descendants = descendants;
    }
}
