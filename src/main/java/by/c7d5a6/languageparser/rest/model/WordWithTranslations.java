package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class WordWithTranslations extends Word {
    private List<String> translations;

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }
}
