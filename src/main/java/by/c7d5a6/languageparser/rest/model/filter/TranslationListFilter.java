package by.c7d5a6.languageparser.rest.model.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandles;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationListFilter extends PaginationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Parameter(description = "The word language to search for", required = false)
    @Schema(description = "The word language to search for")
    private Long languageFromId;

    @Parameter(description = "The word to search for", required = false)
    @Schema(description = "The word to search for")
    private String word;

    @Parameter(description = "The translation language to search for", required = false)
    @Schema(description = "The translation language to search for")
    private Long languageToId;

    @Parameter(description = "The translation to search for", required = false)
    @Schema(description = "The translation to search for")
    private String translation;

    public Long getLanguageFromId() {
        return languageFromId;
    }

    public void setLanguageFromId(Long languageFromId) {
        this.languageFromId = languageFromId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLanguageToId() {
        return languageToId;
    }

    public void setLanguageToId(Long languageToId) {
        this.languageToId = languageToId;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
