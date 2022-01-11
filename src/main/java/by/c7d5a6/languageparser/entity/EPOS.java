package by.c7d5a6.languageparser.entity;

import by.c7d5a6.languageparser.entity.base.BaseEntity;
import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = EWord.ENTITY_NAME)
@Table(name = EWord.TABLE_NAME)
public class EPOS extends BaseEntity implements Serializable, IdLongVerPossessor {

    protected static final String ENTITY_NAME = "PartOfSpeech";
    protected static final String TABLE_NAME = "pos_tbl";
    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
