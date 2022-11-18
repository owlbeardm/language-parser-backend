package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

import java.util.List;

public class DeclensionRule extends Base {
    private List<GrammaticalCategoryValue> values;
    private Declension declension;
    private String name;
    private Boolean enabled;
    private String wordPattern;

    public List<GrammaticalCategoryValue> getValues() {
        return values;
    }

    public void setValues(List<GrammaticalCategoryValue> values) {
        this.values = values;
    }

    public Declension getDeclension() {
        return declension;
    }

    public void setDeclension(Declension declension) {
        this.declension = declension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getWordPattern() {
        return wordPattern;
    }

    public void setWordPattern(String wordPattern) {
        this.wordPattern = wordPattern;
    }
}
