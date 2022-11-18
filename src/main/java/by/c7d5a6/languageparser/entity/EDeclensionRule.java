package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity(name = EDeclensionRule.ENTITY_NAME)
@Table(name = EDeclensionRule.TABLE_NAME)
public class EDeclensionRule extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "DeclensionRule";
    protected static final String TABLE_NAME = "declension_rule_tbl";
    private static final long serialVersionUID = 1L;
    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "declension_rule_value_tbl",
            joinColumns = {@JoinColumn(name = "declension_rule_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name = "value_id", referencedColumnName="id")}
    )
    private Set<EGrammaticalCategoryValue> values = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "declension_id")
    private EDeclension declension;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "enabled_yn")
    private Boolean enabled;
    @Column(name = "pattern")
    private String wordPattern;

    public Set<EGrammaticalCategoryValue> getValues() {
        return values;
    }

    public void setValues(Set<EGrammaticalCategoryValue> values) {
        this.values = values;
    }

    public EDeclension getDeclension() {
        return declension;
    }

    public void setDeclension(EDeclension declension) {
        this.declension = declension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getWordPattern() {
        return wordPattern;
    }

    public void setWordPattern(String wordPattern) {
        this.wordPattern = wordPattern;
    }
}
