package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class WordWithFullTranslation extends WordWithWritten {

    List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
