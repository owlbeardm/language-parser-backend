package by.c7d5a6.languageparser.rest.model;

import by.c7d5a6.languageparser.rest.model.base.Base;

public class GrammaticalCategoryValue extends Base {

    private String name;
    private GrammaticalCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GrammaticalCategory getCategory() {
        return category;
    }

    public void setCategory(GrammaticalCategory category) {
        this.category = category;
    }
}
