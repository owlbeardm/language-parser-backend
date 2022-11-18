package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

import java.util.List;

public class DeclensionFull extends Base {
    private List<GrammaticalCategoryValue> values;
    private Language language;
    private POS pos;
    private boolean deprecated;
    private boolean exist;

    public List<GrammaticalCategoryValue> getValues() {
        return values;
    }

    public void setValues(List<GrammaticalCategoryValue> values) {
        this.values = values;
    }

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

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
