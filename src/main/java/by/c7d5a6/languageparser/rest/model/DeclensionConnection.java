package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class DeclensionConnection extends Base {
    private Language language;
    private POS pos;
    private GrammaticalCategory grammaticalCategory;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    public GrammaticalCategory getGrammaticalCategory() {
        return grammaticalCategory;
    }

    public void setGrammaticalCategory(GrammaticalCategory grammaticalCategory) {
        this.grammaticalCategory = grammaticalCategory;
    }
}
