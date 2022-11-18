package by.c7d5a6.languageparser.rest.model;

public class DeclensionFull extends Declension {
    private boolean deprecated;
    private boolean exist;

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
