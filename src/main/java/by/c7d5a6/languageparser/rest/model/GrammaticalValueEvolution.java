package by.c7d5a6.languageparser.rest.model;

public class GrammaticalValueEvolution {
    private POS pos;
    private Language languageFrom;
    private Language languageTo;
    private GrammaticalCategoryValue valueFrom;
    private GrammaticalCategoryValue valueTo;

    public POS getPos() {
        return pos;
    }

    public void setPos(POS pos) {
        this.pos = pos;
    }

    public Language getLanguageFrom() {
        return languageFrom;
    }

    public void setLanguageFrom(Language languageFrom) {
        this.languageFrom = languageFrom;
    }

    public Language getLanguageTo() {
        return languageTo;
    }

    public void setLanguageTo(Language languageTo) {
        this.languageTo = languageTo;
    }

    public GrammaticalCategoryValue getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(GrammaticalCategoryValue valueFrom) {
        this.valueFrom = valueFrom;
    }

    public GrammaticalCategoryValue getValueTo() {
        return valueTo;
    }

    public void setValueTo(GrammaticalCategoryValue valueTo) {
        this.valueTo = valueTo;
    }
}
