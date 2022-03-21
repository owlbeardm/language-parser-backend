package by.c7d5a6.languageparser.rest.model;

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
public class WordWithEvolutionsListFilter extends PaginationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Parameter(description = "The word to search for", required = false)
    @Schema(description = "The word from to search for")
    private String word;

    @Parameter(description = "The language from to search for", required = false)
    @Schema(description = "The language from to search for")
    private Long languageFromId;

    @Parameter(description = "The language to to search for", required = false)
    @Schema(description = "The language to to search for")
    private Long languageToId;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getLanguageFromId() {
        return languageFromId;
    }

    public void setLanguageFromId(Long languageFromId) {
        this.languageFromId = languageFromId;
    }

    public Long getLanguageToId() {
        return languageToId;
    }

    public void setLanguageToId(Long languageToId) {
        this.languageToId = languageToId;
    }
}
