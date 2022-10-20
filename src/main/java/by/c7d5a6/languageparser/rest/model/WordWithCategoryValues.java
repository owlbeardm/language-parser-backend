package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class WordWithCategoryValues extends WordWithWritten {
    private List<GrammaticalCategoryValue> grammaticalValues;

    public List<GrammaticalCategoryValue> getGrammaticalValues() {
        return grammaticalValues;
    }

    public void setGrammaticalValues(List<GrammaticalCategoryValue> grammaticalValues) {
        this.grammaticalValues = grammaticalValues;
    }
}
