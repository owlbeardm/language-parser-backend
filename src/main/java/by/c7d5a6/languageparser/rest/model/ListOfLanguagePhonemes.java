package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ListOfLanguagePhonemes implements Serializable {

    private Long langId;
    private List<String> usedMainPhonemes;
    private List<String> restUsedPhonemes;
    private List<LanguagePhoneme> selectedMainPhonemes;
    private List<LanguagePhoneme> selectedRestPhonemes;

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public List<LanguagePhoneme> getSelectedMainPhonemes() {
        return selectedMainPhonemes;
    }

    public List<LanguagePhoneme> getSelectedRestPhonemes() {
        return selectedRestPhonemes;
    }

    public List<String> getRestUsedPhonemes() {
        return restUsedPhonemes;
    }

    public List<String> getUsedMainPhonemes() {
        return usedMainPhonemes;
    }

    public void setRestUsedPhonemes(List<String> restUsedPhonemes) {
        this.restUsedPhonemes = restUsedPhonemes;
    }

    public void setSelectedMainPhonemes(List<LanguagePhoneme> selectedMainPhonemes) {
        this.selectedMainPhonemes = selectedMainPhonemes;
    }

    public void setSelectedRestPhonemes(List<LanguagePhoneme> selectedRestPhonemes) {
        this.selectedRestPhonemes = selectedRestPhonemes;
    }

    public void setUsedMainPhonemes(List<String> usedMainPhonemes) {
        this.usedMainPhonemes = usedMainPhonemes;
    }
}
