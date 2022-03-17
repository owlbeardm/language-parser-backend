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
public class WordListFilter extends PaginationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Parameter(description = "The word to search for", required = false)
    @Schema(description = "The word to search for")
    private String word;

    @Parameter(description = "The language to search for", required = false)
    @Schema(description = "The language to search for")
    private Long languageId;

    @Parameter(description = "The parts of speech to search for", required = false)
    @Schema(description = "The parts of speech to search for")
    private Long posId;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Nullable
    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(@Nullable Long languageId) {
        this.languageId = languageId;
    }

    @Nullable
    public Long getPosId() {
        return posId;
    }

    public void setPosId(@Nullable Long posId) {
        this.posId = posId;
    }
}
