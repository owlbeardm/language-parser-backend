package by.c7d5a6.languageparser.rest.model.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WordBorrowedListFilter extends WordListFilter {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Parameter(description = "The language from to search for", required = false)
    @Schema(description = "The language from to search for")
    private Long languageToId;

    public Long getLanguageToId() {
        return languageToId;
    }

    public void setLanguageToId(Long languageToId) {
        this.languageToId = languageToId;
    }
}
