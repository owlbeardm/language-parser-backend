package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.entity.enums.LanguageConnectionType;
import by.c7d5a6.languageparser.rest.model.base.Base;

public class LanguageConnection extends Base {

    Language langFrom;
    Language langTo;
    LanguageConnectionType connectionType;

    public Language getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(Language langFrom) {
        this.langFrom = langFrom;
    }

    public Language getLangTo() {
        return langTo;
    }

    public void setLangTo(Language langTo) {
        this.langTo = langTo;
    }

    public LanguageConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(LanguageConnectionType connectionType) {
        this.connectionType = connectionType;
    }
}
