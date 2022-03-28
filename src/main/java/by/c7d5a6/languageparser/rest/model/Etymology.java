package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class Etymology {
    private List<WordWithTranslations> from;
    private List<WordWithTranslations> cognate;

    public List<WordWithTranslations> getFrom() {
        return from;
    }

    public void setFrom(List<WordWithTranslations> from) {
        this.from = from;
    }

    public List<WordWithTranslations> getCognate() {
        return cognate;
    }

    public void setCognate(List<WordWithTranslations> cognate) {
        this.cognate = cognate;
    }
}
