package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class LanguagePOS extends Base {

    Long languageId;
    Long posId;
    boolean usedInLanguage;
    boolean connectedToLanguage;

    public LanguagePOS(Long id, Long languageId, Long posId, boolean usedInLanguage, boolean connectedToLanguage) {
        this.id = id;
        this.languageId = languageId;
        this.posId = posId;
        this.usedInLanguage = usedInLanguage;
        this.connectedToLanguage = connectedToLanguage;
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

    public boolean isUsedInLanguage() {
        return usedInLanguage;
    }

    public void setUsedInLanguage(boolean usedInLanguage) {
        this.usedInLanguage = usedInLanguage;
    }

    public boolean isConnectedToLanguage() {
        return connectedToLanguage;
    }

    public void setConnectedToLanguage(boolean connectedToLanguage) {
        this.connectedToLanguage = connectedToLanguage;
    }
}
