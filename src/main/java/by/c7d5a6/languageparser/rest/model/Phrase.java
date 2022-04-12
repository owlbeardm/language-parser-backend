package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class Phrase extends Base {

    private Language language;
    private String phrase;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
