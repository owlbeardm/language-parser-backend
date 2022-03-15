package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class LanguagePOS extends Base {

    Long languageId;
    Long posId;

    public LanguagePOS(Long id, Long languageId, Long posId) {
        this.id = id;
        this.languageId = languageId;
        this.posId = posId;
    }

    public LanguagePOS() {
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }
}
