package by.c7d5a6.languageparser.rest.model;


import by.c7d5a6.languageparser.entity.WordWithIdAndLanguage;
import by.c7d5a6.languageparser.enums.WordOriginType;
import by.c7d5a6.languageparser.rest.model.base.Base;
import by.c7d5a6.languageparser.rest.model.format.IPAFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Word extends Base implements WordWithIdAndLanguage {

    private Language language;
    private Boolean isForgotten;
    private POS partOfSpeech;
    @Parameter(description = "The word sounds", required = true)
    @Schema(description = "The word sounds")
    private String word;
    private String comment;
    private WordOriginType sourceType;

    public Word() {
    }

    public Word(String word, Language language, POS partOfSpeech, Boolean isForgotten) {
        this.word = word;
        this.language = language;
        this.partOfSpeech = partOfSpeech;
        this.isForgotten = isForgotten;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getForgotten() {
        return isForgotten;
    }

    public void setForgotten(Boolean forgotten) {
        isForgotten = forgotten;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public POS getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(POS partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public WordOriginType getSourceType() {
        return sourceType;
    }

    public void setSourceType(WordOriginType sourceType) {
        this.sourceType = sourceType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
