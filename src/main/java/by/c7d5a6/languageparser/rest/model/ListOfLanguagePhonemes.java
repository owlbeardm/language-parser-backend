package by.c7d5a6.languageparser.rest.model;

import java.io.Serializable;
import java.util.List;

public class ListOfLanguagePhonemes implements Serializable {

    private Long langId;
    private List<String> usedMainPhonemes;
    private List<String> restUsedPhonemes;
    private List<LanguagePhoneme> selectedMainPhonemes;
    private List<LanguagePhoneme> selectedRestPhonemes;

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public List<LanguagePhoneme> getSelectedMainPhonemes() {
        return selectedMainPhonemes;
    }

    public void setSelectedMainPhonemes(List<LanguagePhoneme> selectedMainPhonemes) {
        this.selectedMainPhonemes = selectedMainPhonemes;
    }

    public List<LanguagePhoneme> getSelectedRestPhonemes() {
        return selectedRestPhonemes;
    }

    public void setSelectedRestPhonemes(List<LanguagePhoneme> selectedRestPhonemes) {
        this.selectedRestPhonemes = selectedRestPhonemes;
    }

    public List<String> getRestUsedPhonemes() {
        return restUsedPhonemes;
    }

    public void setRestUsedPhonemes(List<String> restUsedPhonemes) {
        this.restUsedPhonemes = restUsedPhonemes;
    }

    public List<String> getUsedMainPhonemes() {
        return usedMainPhonemes;
    }

    public void setUsedMainPhonemes(List<String> usedMainPhonemes) {
        this.usedMainPhonemes = usedMainPhonemes;
    }
}
