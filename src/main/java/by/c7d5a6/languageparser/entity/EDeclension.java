package by.c7d5a6.languageparser.entity;


import by.c7d5a6.languageparser.entity.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity(name = EDeclension.ENTITY_NAME)
@Table(name = EDeclension.TABLE_NAME)
public class EDeclension extends BaseEntity implements Serializable {

    protected static final String ENTITY_NAME = "Declension";
    protected static final String TABLE_NAME = "declension_tbl";
    private static final long serialVersionUID = 1L;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "declension_grm_value_tbl",
            joinColumns = {@JoinColumn(name = "declension_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "value_id", referencedColumnName = "id")}
    )
    private Set<EGrammaticalCategoryValue> values = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "language_id")
    private ELanguage language;
    @ManyToOne
    @JoinColumn(name = "pos_id")
    private EPOS pos;
    @NotNull
    @Column(name = "main_declension_yn")
    private boolean mainDeclension;

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public EPOS getPos() {
        return pos;
    }

    public void setPos(EPOS pos) {
        this.pos = pos;
    }

    public Set<EGrammaticalCategoryValue> getValues() {
        return values;
    }

    public void setValues(Set<EGrammaticalCategoryValue> values) {
        this.values = values;
    }

    public boolean isMainDeclension() {
        return mainDeclension;
    }

    public void setMainDeclension(boolean mainDeclension) {
        this.mainDeclension = mainDeclension;
    }
}
