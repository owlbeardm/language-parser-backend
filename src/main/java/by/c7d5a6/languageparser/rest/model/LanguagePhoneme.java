package by.c7d5a6.languageparser.rest.model;


import by.c7d5a6.languageparser.rest.model.base.Base;
import by.c7d5a6.languageparser.rest.model.format.IPAFormat;

public class LanguagePhoneme extends Base {

    private Language language;
    private String phoneme;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getPhoneme() {
        return phoneme;
    }

    public void setPhoneme(String phoneme) {
        this.phoneme = phoneme;
    }
}
