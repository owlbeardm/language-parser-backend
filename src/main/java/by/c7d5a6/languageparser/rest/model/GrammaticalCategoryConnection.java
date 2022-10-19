package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class GrammaticalCategoryConnection extends Base {

    private Language language;
    private GrammaticalCategory grammaticalCategory;
    private POS pos;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public GrammaticalCategory getGrammaticalCategory() {
        return grammaticalCategory;
    }

    public void setGrammaticalCategory(GrammaticalCategory grammaticalCategory) {
        this.grammaticalCategory = grammaticalCategory;
    }

    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }
}
