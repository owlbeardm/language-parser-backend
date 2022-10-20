package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class GrammaticalCategoryValueConnection extends Base {

    private GrammaticalCategoryValue value;
    private Language language;

    public GrammaticalCategoryValue getValue() {
        return value;
    }

    public void setValue(GrammaticalCategoryValue value) {
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
